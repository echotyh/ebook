<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  User: SunJianwei<327021593@qq.com>
  Date: 16-6-2 23:21
  Desc: 好评榜、最新发布、特价图书等 图书列表页
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>${requestScope.title}</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/home.css">
    <script type="text/javascript">
        window.page     = ${requestScope.queryParams.page};
        window.pageSize = ${requestScope.queryParams.pageSize};
    </script>

</head>

<body>


<div class="wrapper">
    <div class="module_title">   <!-- 模块头 -->
        ${requestScope.title}
    </div>
    <div class="module_body">   <!-- 模块内容 -->
        <c:forEach items="${requestScope.books}" var="item" varStatus="status">
            <div class="book_box">   <!-- 某一本书 -->
                <div class="cover_box">   <!-- cover -->
                    <a href="${pageContext.request.contextPath}/servlet/BookDetail?book=${item.bookid}"><img src="${pageContext.request.contextPath}/book-images/${item.bookid}.png"/></a>
                </div>
                <div class="info">   <!-- info -->
                    <div class="book_name">   <!-- book name -->
                        <a href="${pageContext.request.contextPath}/servlet/BookDetail?book=${item.bookid}">${item.bookname}</a>
                    </div>
                    <div class="book_author">   <!-- author -->
                            ${item.username}
                    </div>
                    <div class="book_grade">   <!-- grade -->
                        评分：<fmt:formatNumber value="${item.averagegrade}" type="number" pattern="#0.0"/>
                    </div>
                    <div class="book_price">   <!-- price -->
                            <span>
                                <fmt:formatNumber value="${item.price * item.discount / 1000}" type="number" pattern="￥#,#00.00"/>
                            </span>
                        <c:if test="${item.discount lt 10}">    <!-- 如果有折扣显示原价 -->
                                <span class="origin_price">
                                    <fmt:formatNumber value="${item.price / 100}" type="number" pattern="￥#,#00.00"/>
                                </span>
                        </c:if>
                    </div>
                    <div class="book_introduction">   <!-- introduction -->
                            ${item.introduction}
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- 分页 -->
    <div>
        <span style="margin-right: 30px;"><a href="${pageContext.request.contextPath}">回首页</a></span>
        <c:if test="${requestScope.queryParams.page gt 1}">
            <span style="margin-right: 30px;"><a href="${requestScope.url}?page=${requestScope.queryParams.page - 1}&pageSize=${requestScope.queryParams.pageSize}">上一页</a></span>
        </c:if>
        <span style="margin-right: 30px;"><a href="${requestScope.url}?page=${requestScope.queryParams.page + 1}&pageSize=${requestScope.queryParams.pageSize}">下一页</a></span>
    </div>


</div>


</body>
</html>