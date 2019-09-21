package com.atguigu.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
import com.google.gson.Gson;

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
	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取书的id
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
	//修改购物车中商品的数量
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//4、在updateItemQuantity方法中，获取quantity、id,再获取购物车对象，调用service的方法修改
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		int id = -1;
		int quantity = -1;
		//一般获取的两个参数不放在一起，但是本方法中如果id出异常，那么quantity也没必要修改了，所以要放在一起。
		//位置：10. 尚硅谷_佟刚_JavaWEB案例_Ajax修改购物车单品数量 16分35秒
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {}
		
		if(id > 0 && quantity > 0){
		    bookService.updateItemQuantity(sc,id,quantity);
		}
    	//5、传回Json数据：bookNumber 、totalMoney	
		Map<String,Object> result = new HashMap<>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}
	//清空购物车
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
	    bookService.clearShoppingCart(sc);	
        request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1、获取商品的id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if(id > 0){
			//2、获取购物车对象☆
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			//3、调用BookService的addToCart方法把商品添加到购物车中
			flag = bookService.addToCart(id,sc);
		}
		
		if(flag){
			//4、直接调用getBooks方法
			getBooks(request,response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
		
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
