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
    		/* 代表的是jQuery对象 */
    		var $tr = $(this).parent().parent();
    		var title = $.trim($tr.find("td:first").text());
    		var flag = confirm("确定要删除" + title + "的信息吗");
    		if(flag){
    			return true;
    		}
    		return false;
    	});
    	//ajax修改商品的数量
    	//1、获取页面所有的text,并为其添加onchange响应函数.弹出确认对话框：确认要修改吗？
    	$(":text").change(function(){
    	var quantityVal = $.trim(this.value);
    	//验证商品数量的合法性
    	var flag = false;
		
		var reg = /^\d+$/g;
		var quantity = -1;
		if(reg.test(quantityVal)){
			quantity = parseInt(quantityVal);
			if(quantity >= 0){
				flag = true;
			}
		}
		
		if(!flag){
			alert("输入的数量不合法!");
			//一旦输入的数量值不是合法数据，那么就呈现输入之前的数据
			$(this).val($(this).attr("class"));
			return;
		}
    	
    		var $tr = $(this).parent().parent();
    		var title = $.trim($tr.find("td:first").text());
    		
    	if(quantity == 0){
    		var flag2 = confirm("确定要删除" + title + "吗");
    		if(flag2){
				//得到了 a 节点
				var $a = $tr.find("td:last").find("a");
				//执行 a 节点的 onclick 响应函数. 
				$a[0].onclick();
				
				return;
			}
    	}
    		
    	var flag = confirm("确定要修改" + title + "的数量吗");
    		if(!flag){
    			$(this).val($(this).attr("class"));
    			return;
    		}
    	//2、请求地址为bookServlet
    	var url = "bookServlet";
    	//3、请求参数为：method;updateItemQuantity,id:name属性值,quantity:val,time:new Date()
    	var idVal = $.trim(this.name);
    	var args = {"method":"updateItemQuantity",
    			"id":idVal,
    			"quantity":quantityVal,
    			"time":new Date()
    	};
    	//4、在updateItemQuantity方法中，获取quantity、id,再获取购物车对象，调用service的方法修改
    	//5、传回Json数据：bookNumber 、totalMoney
    	//6、更新当前页面的bookNumber和totalMoney
    		$.post(url,args,function(data){
    			var bookNumber = data.bookNumber;
    			var totalMoney = data.totalMoney;
    			$("#totalMoney").text("总金额：￥"+ totalMoney)
    			$("#bookNumber").text("您的购物车中共有" + bookNumber +"本书")
    		},"Json");
    	});
    });
</script>
</head>
<body>

	<center>
	<br><br>
	<div id = "bookNumber">
	您的购物车中共有${sessionScope.ShoppingCart.bookNumber }本书
	</div>
	<table cellpadding="10">
	    <tr>
	    <td>Title</td>&nbsp;&nbsp;<td>Quantity</td>&nbsp;&nbsp;<td>price</td>
	    </tr>
		<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
		<tr>
        <td>${item.book.title }</td> &nbsp;&nbsp;
        <td><input type="text" class = "${item.quantity }" value = "${item.quantity }" name = "${item.book.id }" size = "2"/></td> &nbsp;&nbsp;
        <td>${item.book.price }</td>
         <td><a href="bookServlet?method=remove&pageNo=${param.pageNo }&id=${item.book.id }" class= "delete">删除</a></td>
         </tr>
		</c:forEach>
		
         <tr>
         <td colspan="4" id = "totalMoney">
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