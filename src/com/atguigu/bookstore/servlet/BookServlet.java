package com.atguigu.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.service.BookService;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private BookService bookService = new BookService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//?
		String methodName = request.getParameter("method");
		try {
			//注意是getDeclaredMethod，位于视频5 20分20秒
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			/*throw new RuntimeException(e);*/
		}

	}
	
    //获取书的详细信息
	protected void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if(id > 0)
		 book = bookService.getBook(id);
		
		//如果该书不存在
		if(book == null){
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book",book);
		request.getRequestDispatcher("WEB-INF/pages/book.jsp").forward(request, response);
		
	}
	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1、获取三个请求参数
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");

		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}

		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);

		request.setAttribute("bookpage", page);

		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);

	}
	/*protected void getBooks1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		int minPrice= 0;
		int maxPrice = Integer.MAX_VALUE;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {}
		
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {}
		
		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);
		
		request.setAttribute("bookpage", page);
		
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}*/

}
