package com.atguigu.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCart;
import com.atguigu.bookstore.service.BookService;
import com.atguigu.bookstore.web.BookStoreWebUtils;
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
			//ע����getDeclaredMethod��λ����Ƶ5 20��20��
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			/*throw new RuntimeException(e);*/
		}

	}
	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ���id
		String idStr = request.getParameter("id");
		int id = -1;
				
		try {
			id=	Integer.parseInt(idStr);
		} catch (Exception e) {}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(sc,id);
		
		if(sc.isEmpty()){
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
		
	}
	protected void toCartPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
		
	}
	//��չ��ﳵ
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
	    bookService.clearShoppingCart(sc);	
        request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1����ȡ��Ʒ��id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if(id > 0){
			//2����ȡ���ﳵ�����
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			//3������BookService��addToCart��������Ʒ��ӵ����ﳵ��
			flag = bookService.addToCart(id,sc);
		}
		
		if(flag){
			//4��ֱ�ӵ���getBooks����
			getBooks(request,response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
		
	}
    //��ȡ�����ϸ��Ϣ
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
		
		//������鲻����
		if(book == null){
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book",book);
		request.getRequestDispatcher("WEB-INF/pages/book.jsp").forward(request, response);
		
	}
	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1����ȡ�����������
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
