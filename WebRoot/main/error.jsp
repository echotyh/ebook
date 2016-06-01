<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%--
  User: SunJianwei<327021593@qq.com>
  Date: 16-5-29 22:49
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!-- <link rel="stylesheet" type="text/css" href="styles.css"> -->

</head>

<body>
    <center>
        <h1>啊哦～～～出错啦</h1>
        <div>ExceptionClass:${requestScope.errClass}</div>
        <div>ExceptionMsg:${requestScope.errmsg}</div>
        <div>${requestScope.errmsg}</div>
    </center>
</body>
</html>