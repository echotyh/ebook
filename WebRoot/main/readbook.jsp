<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%--
  User: SunJianwei<327021593@qq.com>
  Date: 16-6-6 13:14
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>免费试读</title>
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

<jsp:include page="header.jsp"/>

<div>
    <img src="<%=basePath%>user/pagephoto?book=${requestScope.bookId}&page=${requestScope.page}" />
</div>
<div>
    <c:if test="${requestScope.page gt 1}">
        <a href="<%=basePath%>user/ReadBookSvl?book=${requestScope.bookId}&page=${requestScope.page - 1}">上一页</a>
    </c:if>
    <a href="<%=basePath%>user/ReadBookSvl?book=${requestScope.bookId}&page=${requestScope.page + 1}">下一页</a>
</div>

</body>
</html>