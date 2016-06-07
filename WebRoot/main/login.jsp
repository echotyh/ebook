<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath%>CSS/login.css"/>
	<script src="<%=basePath%>script/jquery-2.2.3.js"></script>
  </head>
  <script type="text/javascript">
  $(function(){
	  $("input[name='user_name']").change(function(){
  				if($(this).val()==""||$(this).val()==null){
  						$("#msg").html("用户名不能为空");
  				}
  				
  			});
  			
	  $("input[name='user_pwd']").change(function(){
		  		if($(this).val()==""||$(this).val()==null){
  						$("#msg").html("密码不能为空！！！！！！！");
  				}
		  	});
		});
  function _checkAll(){
	  if($("input[name='user_pwd']").val()==""||$("input[name='user_pwd']").val()==null||$("input[name='user_name']").val()==""||$("input[name='user_name']").val()==null){
		  return false; 
	  }else{
		  return true;
	  }
  }
  
  		
  </script>
  
  <body>

  <jsp:include page="header.jsp"/>

    <div id="login">
    
 	<div id="login_content">
     <form action="<%=basePath%>servlet/LoginServlet" method="post" onsubmit="return _checkAll();">
     <table cellspacing="10px">
     <caption>~欢迎登录~</caption>
     	<tr>
        	<td>用户名：</td>
            <td><input type="text" name="user_name"/></td>
        </tr>
        <tr>
        	<td>密码：</td>
            <td><input type="password" name="user_pwd"/></td>
        </tr>
        <tr>
        	<td colspan="2"><span id="msg"><c:if test="${msg!=null}">${msg}</c:if></span></td>
        </tr>
         <tr>
        	<td><input type="button" value="注  册" class="re" onclick="window.location.href='main/register.jsp'"/></td>
            <td><button class="lo"  >登  录</button></td>
        </tr>
     </table>
     </form>
     
    </div>
 </div>
  </body>
</html>
