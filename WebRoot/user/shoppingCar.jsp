<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>购物车</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div id="shoppingCar">
       <form action="ConfirmSvl" method="post">
           <table cellspacing="10px" >
               <tr>
                <td width="30%">商品详情</td>
                <td width="20%">原价（元）</td>
                <td width="35%">简介</td>
                <td>操作</td>
               </tr>
               
               <c:forEach var="book" items="${books}">
                   <tr class="shop_content">
        	       <td height="80px"><img src="images/${book.bookid}.jpg " alt="商品图片" width="70" height="50" tag="商品展示"/><p>${book.bookname} </p></td>
        	       
                   <td>${book.price} </td>
                   <td>${book.introduction }</td>
                   <td><a href="${pageContext.request.contextPath}/servlet/ShoppingCarDelet?bookid=${book.bookid}">删除</a>
                       <a href="${pageContext.request.contextPath}/servlet/ShoppingCarCollect?bookid=${book.bookid}">移入收藏</a>
                   </td>
                   </tr>
               </c:forEach>
        
                 <tr class="pre_check">
        	     <td colspan="4">
                      <div>
            	         <p>共${num}件商品金额总计:${money} 元</p>
                          <p>应付金额为:${money} 元</p>
                         <button >结&nbsp;&nbsp;&nbsp;&nbsp;算</button>
                      </div>
                 </td>
                 </tr>
           </table>
       </form>
    </div>
  
    <div id="home_bottom">
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Copyright Site design by Tang Yuhang</p>
    </div>
    
  </body>
</html>
