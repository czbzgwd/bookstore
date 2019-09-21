<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<%@ include file = "/commons/queryCondition.jsp" %> 
<script type="text/javascript">
    $(function(){
    	$(".delete").click(function(){
    		var $tr = $(this).parent().parent();
    		var title = $.trim($tr.find("td:first").text());
    		var flag = confirm("确定要删除" + title + "的信息吗");
    		if(flag){
    			return true;
    		}
    		return false;
    	});
    });
</script>
</head>
<body>

	<center>
	<br><br>
	您的购物车中共有${sessionScope.ShoppingCart.bookNumber }本书
	<table cellpadding="10">
	    <tr>
	    <td>Title</td>&nbsp;&nbsp;<td>Quantity</td>&nbsp;&nbsp;<td>price</td>
	    </tr>
		<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
		<tr>
        <td>${item.book.title }</td> &nbsp;&nbsp;<td>${item.quantity }</td> &nbsp;&nbsp;<td>${item.book.price }</td>
         <td><a href="bookServlet?method=remove&pageNo=${param.pageNo }&id=${item.book.id }" class= "delete">删除</a></td>
         </tr>
		</c:forEach>
		
         <tr>
         <td colspan="4">
                           总金额：￥${sessionScope.ShoppingCart.totalMoney}
         </td>
         </tr>
         <tr>
         <td>
         <a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
         &nbsp;&nbsp;
         <a href= "bookServlet?method=clear">清空购物车</a>
         &nbsp;&nbsp;
         <a href= "#">结账</a>
         </td>
         </tr>
    </table>
	</center>
</body>
</html>