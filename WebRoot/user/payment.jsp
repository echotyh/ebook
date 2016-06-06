<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>支付</title>
    
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
    <p>确认订单信息</p>
    <form action="PaymentSvl" method="post">
    
         您的账户余额为：${user.user_count }元<br />
       
        <input type="hidden" value="${AllPrice}" name="AllPrice"/>
        应付金额为：${AllPrice }元<br />
    
    
       <input type="button" value="返回购物车" class="re" onclick="#。。。。'"/><!-- 这个就是返回刚刚的购物车界面，我不知道怎么返回 -->
       <button>立即支付</button>
    
    
    </form>
    
    
    
    
    
    <div id="home_bottom">
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Copyright Site design by Tang Yuhang</p>
    </div>

    
  </body>
</html>
