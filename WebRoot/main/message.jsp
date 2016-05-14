<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息提示</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="<%=basePath%>/CSS/main.css"/>
<link rel="stylesheet" href="<%=basePath%>/CSS/shopcar.css"/>
<link rel="stylesheet" href="<%=basePath%>/CSS/check_out.css"/>
	

  </head>
  
  <body>
 <div id="user_info">
	<p id="welcome">欢迎您 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${user!=null}">${user.user_name}</c:if><c:if test="${user==null}"><a href="main/login.jsp">请登录</a></c:if></p>
	<c:if test="${user!=null}">
    <ul id="user_bar" >
        <li><a href="AllProductSvl">主页</a></li>
    	<li><a>我的账户</a></li>
        <li><a href="ShowHistorySvl">购买历史</a></li>
        <li><a href="ShowShopCarSvl">购物车</a></li>
        <li><a href="ExitSvl">退出系统</a></li>
    </ul>
    </c:if>
  </div>
<div id="search_part">
	<form id="search" action="GetProConSvl" method="post">
	<input type="text"  name="search_content" id="search_content"/>
	<button id="search_butt">搜索</button>
    </form>
    <ul id="short_cut">
    	<li><a href="ProductDetailSvl?chicken_id=2015040309450001">韩参鸡汤王</a></li>
        <li><a  href="ProductDetailSvl?chicken_id=2015040309450005">蟹王鸡汤煲</a></li>
        <li><a  href="ProductDetailSvl?chicken_id=2015040309450002">香耳煲鸡汤</a></li>
        <li><a  href="ProductDetailSvl?chicken_id=2015040309450003">小白菜滚鸡汤</a></li>
        <li><a  href="ProductDetailSvl?chicken_id=2015040309450004">海南鸡炖盅</a></li>
    </ul>
    
</div>
<div class="pre_check" id="check_out_main">
	<p>
      ${msg} 
    <button  onclick="window.location.href='AllProductSvl'">返回主页</button> </p>
   
</div>
<div id="home_bottom">
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Copyright Site design by Fubiqi and Tangyuhang</p>
</div>
    
  </body>
</html>
