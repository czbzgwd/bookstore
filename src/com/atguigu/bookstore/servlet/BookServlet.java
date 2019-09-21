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

import com.atguigu.bookstore.domain.Account;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCart;
import com.atguigu.bookstore.domain.ShoppingCartItem;
import com.atguigu.bookstore.domain.User;
import com.atguigu.bookstore.service.AccountService;
import com.atguigu.bookstore.service.BookService;
import com.atguigu.bookstore.service.UserService;
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
		// ?
		String methodName = request.getParameter("method");
		try {
			// 注意是getDeclaredMethod，位于视频5 20分20秒
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			/* throw new RuntimeException(e); */
		}

	}

	private UserService userService = new UserService();

	// 结账验证：用户名和账号
	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1、简单验证:验证表单域的值是否符合规范，如是否为空，是否可以转为int类型，是否是一个email.特点：不需要查询数据库或其他业务方法。
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");

		StringBuffer errors = validateFormField(username, accountId);
		
		//☆
		//表单验证通过
		if(errors.toString().equals("")){
			errors = validateUser(username, accountId);
			
			//用户名和账号验证通过
			  if(errors.toString().equals("")){
				  errors = validateBookStoreNumber(request);
					  
				      //库存验证通过
					  if(errors.toString().equals("")){
						  errors = validateBalance(request,accountId);
					  }
				  
			  }
		}
		// 如果用户名和账号不为空
		if (!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}

		//验证通过执行具体的逻辑操作
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}

	private AccountService accountService = new AccountService();
	//验证余额
	public StringBuffer validateBalance(HttpServletRequest request,String accountId){
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		if(cart.getTotalMoney() > account.getBalance()){
			errors.append("余额不足");
		}
		return errors;
	}
	//验证书的库存
	public StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		for(ShoppingCartItem sci: cart.getItems()){
			//这里容易出现线程并发的问题
			int quantity = sci.getQuantity();
			//int storeNumber = sci.getBook().getStoreNumber();
			//获取书的最新的库存
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
		}
			
		return errors;
	}
	// 将复杂验证（用户验证）封装成一个方法
	public StringBuffer validateUser(String username, String accountId) {
		// 2、复杂验证
		boolean flag = false;
		User user = userService.getUserByUserName(username);
		if (user != null) {
			int accountId2 = user.getAccountId();
			// 加上""是为了将int型的accountId2转换为String类型的accountId2
			if (accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		}

		StringBuffer errors = new StringBuffer("");
		if (!flag) {
			errors.append("用户名和账号不匹配");
		}
		return errors;
	}

	// 将简单验证（表单验证）封装成一个方法
	public StringBuffer validateFormField(String username, String accountId) {
		StringBuffer errors = new StringBuffer("");
		if (username == null || username.trim().equals("")) {
			errors.append("用户名不能为空<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("账号不能为空");
		}

		return errors;
	}

	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取书的id
		String idStr = request.getParameter("id");
		int id = -1;

		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(sc, id);

		if (sc.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);

	}
	/*
	 * //注意：toCartPage和toCashPage两个方法几乎一模一样，就会存在冗余，这是不允许的。 protected void
	 * toCartPage(HttpServletRequest request, HttpServletResponse response)
	 * throws ServletException, IOException {
	 * 
	 * request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,
	 * response);
	 * 
	 * } protected void toCashPage(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * 
	 * request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request,
	 * response);
	 * 
	 * }
	 */

	protected void ForwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page).forward(request, response);

	}

	// 修改购物车中商品的数量
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 4、在updateItemQuantity方法中，获取quantity、id,再获取购物车对象，调用service的方法修改
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		int id = -1;
		int quantity = -1;
		// 一般获取的两个参数不放在一起，但是本方法中如果id出异常，那么quantity也没必要修改了，所以要放在一起。
		// 位置：10. 尚硅谷_佟刚_JavaWEB案例_Ajax修改购物车单品数量 16分35秒
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {
		}

		if (id > 0 && quantity > 0) {
			bookService.updateItemQuantity(sc, id, quantity);
		}
		// 5、传回Json数据：bookNumber 、totalMoney
		Map<String, Object> result = new HashMap<>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());

		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

	// 清空购物车
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}

	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1、获取商品的id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		if (id > 0) {
			// 2、获取购物车对象☆
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			// 3、调用BookService的addToCart方法把商品添加到购物车中
			flag = bookService.addToCart(id, sc);
		}

		if (flag) {
			// 4、直接调用getBooks方法
			getBooks(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/error-1.jsp");

	}

	// 获取书的详细信息
	protected void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		if (id > 0)
			book = bookService.getBook(id);

		// 如果该书不存在
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);
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
	/*
	 * protected void getBooks1(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { String pageNoStr =
	 * request.getParameter("pageNo"); String minPriceStr =
	 * request.getParameter("minPrice"); String maxPriceStr =
	 * request.getParameter("maxPrice");
	 * 
	 * int pageNo = 1; int minPrice= 0; int maxPrice = Integer.MAX_VALUE;
	 * 
	 * try { pageNo = Integer.parseInt(pageNoStr); } catch
	 * (NumberFormatException e) {}
	 * 
	 * try { minPrice = Integer.parseInt(minPriceStr); } catch
	 * (NumberFormatException e) {}
	 * 
	 * try { maxPrice = Integer.parseInt(maxPriceStr); } catch
	 * (NumberFormatException e) {}
	 * 
	 * CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
	 * Page<Book> page = bookService.getPage(criteriaBook);
	 * 
	 * request.setAttribute("bookpage", page);
	 * 
	 * request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request,
	 * response); }
	 */

}
