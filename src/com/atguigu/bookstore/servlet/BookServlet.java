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
			// ע����getDeclaredMethod��λ����Ƶ5 20��20��
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

	// ������֤���û������˺�
	protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1������֤:��֤�����ֵ�Ƿ���Ϲ淶�����Ƿ�Ϊ�գ��Ƿ����תΪint���ͣ��Ƿ���һ��email.�ص㣺����Ҫ��ѯ���ݿ������ҵ�񷽷���
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");

		StringBuffer errors = validateFormField(username, accountId);
		
		//��
		//����֤ͨ��
		if(errors.toString().equals("")){
			errors = validateUser(username, accountId);
			
			//�û������˺���֤ͨ��
			  if(errors.toString().equals("")){
				  errors = validateBookStoreNumber(request);
					  
				      //�����֤ͨ��
					  if(errors.toString().equals("")){
						  errors = validateBalance(request,accountId);
					  }
				  
			  }
		}
		// ����û������˺Ų�Ϊ��
		if (!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}

		//��֤ͨ��ִ�о�����߼�����
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}

	private AccountService accountService = new AccountService();
	//��֤���
	public StringBuffer validateBalance(HttpServletRequest request,String accountId){
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		if(cart.getTotalMoney() > account.getBalance()){
			errors.append("����");
		}
		return errors;
	}
	//��֤��Ŀ��
	public StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		for(ShoppingCartItem sci: cart.getItems()){
			//�������׳����̲߳���������
			int quantity = sci.getQuantity();
			//int storeNumber = sci.getBook().getStoreNumber();
			//��ȡ������µĿ��
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle() + "��治��<br>");
			}
		}
			
		return errors;
	}
	// ��������֤���û���֤����װ��һ������
	public StringBuffer validateUser(String username, String accountId) {
		// 2��������֤
		boolean flag = false;
		User user = userService.getUserByUserName(username);
		if (user != null) {
			int accountId2 = user.getAccountId();
			// ����""��Ϊ�˽�int�͵�accountId2ת��ΪString���͵�accountId2
			if (accountId.trim().equals("" + accountId2)) {
				flag = true;
			}
		}

		StringBuffer errors = new StringBuffer("");
		if (!flag) {
			errors.append("�û������˺Ų�ƥ��");
		}
		return errors;
	}

	// ������֤������֤����װ��һ������
	public StringBuffer validateFormField(String username, String accountId) {
		StringBuffer errors = new StringBuffer("");
		if (username == null || username.trim().equals("")) {
			errors.append("�û�������Ϊ��<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("�˺Ų���Ϊ��");
		}

		return errors;
	}

	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ���id
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
	 * //ע�⣺toCartPage��toCashPage������������һģһ�����ͻ�������࣬���ǲ�����ġ� protected void
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

	// �޸Ĺ��ﳵ����Ʒ������
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 4����updateItemQuantity�����У���ȡquantity��id,�ٻ�ȡ���ﳵ���󣬵���service�ķ����޸�
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		int id = -1;
		int quantity = -1;
		// һ���ȡ����������������һ�𣬵��Ǳ����������id���쳣����ôquantityҲû��Ҫ�޸��ˣ�����Ҫ����һ��
		// λ�ã�10. �й��_١��_JavaWEB����_Ajax�޸Ĺ��ﳵ��Ʒ���� 16��35��
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {
		}

		if (id > 0 && quantity > 0) {
			bookService.updateItemQuantity(sc, id, quantity);
		}
		// 5������Json���ݣ�bookNumber ��totalMoney
		Map<String, Object> result = new HashMap<>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());

		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

	// ��չ��ﳵ
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}

	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1����ȡ��Ʒ��id
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		if (id > 0) {
			// 2����ȡ���ﳵ�����
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			// 3������BookService��addToCart��������Ʒ��ӵ����ﳵ��
			flag = bookService.addToCart(id, sc);
		}

		if (flag) {
			// 4��ֱ�ӵ���getBooks����
			getBooks(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/error-1.jsp");

	}

	// ��ȡ�����ϸ��Ϣ
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

		// ������鲻����
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);
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
