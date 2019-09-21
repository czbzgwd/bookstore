<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
    $(function(){
    	
    	$("#pageNo").change(function(){
			var val = $(this).val();
			val = $.trim(val);
			
			//1. 校验 val 是否为数字 1, 2, 而不是 a12, b
			var flag = false;
			var reg = /^\d+$/g;
			var pageNo = 0;
			if(reg.test(val)){
				//2. 校验 val 在一个合法的范围内： 1-totalPageNumber
				pageNo = parseInt(val);
				if(pageNo >= 1 && pageNo <= parseInt("${bookpage.totalPageNumber }")){
					flag = true;
				}
			}
			if(!flag){
				alert("输入的不是合法的页码.");
				$(this).val("");
				return;
			}
			//3. 页面跳转
			var href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
			window.location.href = href;
		});
    })


</script>
<%@ include file = "/commons/queryCondition.jsp" %>
</head>
<body>
    

	<center>
		<br>
		<br>
		<!-- <form action="bookServlet?method = getBooks" method="post">
			Price:<input type="text" size = "1" name="minPrice" />- 
			<input type="text"	size = "1" name="maxPrice" /> 
			<input type="submit"  value="Submit" />
		</form> -->
		<form action="bookServlet?method=getBooks" method="post">
			Price: 
			<input type="text" size="1" name="minPrice"/> - 
			<input type="text" size="1" name="maxPrice"/>
			
			<input type="submit" value="Submit"/>
		</form>
		<br>
		<br>
		<table cellpadding="10">
			<c:forEach items="${bookpage.list }" var="book">
				<tr>
					<td><a href="bookServlet?method=getBook&pageNo=${bookpage.pageNo }&id=${book.id}">${book.title }</a>
					<br>
					${book.author}
					</td>
					<td>
					${book.price}
					</td>
					<td>
					<a href = "">加入购物车</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br><br>
		共 ${bookpage.totalPageNumber } 页&nbsp;&nbsp;
                        当前第${bookpage.pageNo }页&nbsp;&nbsp;
        <c:if test="${bookpage.hasPrev }">
        <a href = "bookServlet?method=getBooks&pageNo=1">首页</a>
        &nbsp;&nbsp;
        <!-- bookpage.prevPage不是getPrevPage,为什么get不用写？ -->
        <a href = "bookServlet?method=getBooks&pageNo=${bookpage.prevPage }">上一页</a>
        </c:if>
       
        
        &nbsp;&nbsp;
        
        <c:if test="${bookpage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }">末页</a>
		</c:if>
			&nbsp;&nbsp;
			
		转到<input type = "text" size = "1" id = "pageNo"/>页
       
        
      

	</center>
</body>
</html>