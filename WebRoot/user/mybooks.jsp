<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我的图书</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="<%=basePath%>CSS/home.css">


  </head>
  
  <body>
  <jsp:include page="../main/header.jsp"/>
  <div class="module_body">   <!-- 模块内容 -->
      <c:forEach items="${requestScope.books}" var="item" varStatus="status">
          <div class="book_box">   <!-- 某一本书 -->
              <div class="cover_box">   <!-- cover -->
                  <a href="${pageContext.request.contextPath}/user/ReadBookSvl?book=${item.bookid}"><img
                          src="${pageContext.request.contextPath}/book-images/${item.bookid}.png"/></a>
              </div>
              <div class="info">   <!-- info -->
                  <div class="book_name">   <!-- book name -->
                      <a href="${pageContext.request.contextPath}/user/ReadBookSvl?book=${item.bookid}">${item.bookname}</a>
                  </div>
                  <div class="book_author">   <!-- author -->
                          ${item.username}
                  </div>
                  <div class="book_grade">   <!-- grade -->
                      评分：<fmt:formatNumber value="${item.averagegrade}" type="number" pattern="#0.0"/>
                  </div>

              </div>
              <div class="book_introduction">   <!-- introduction -->
                      ${item.introduction}
              </div>
          </div>
      </c:forEach>
  </div>


  </div>
    
    
  </body>
</html>
