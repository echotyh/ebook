<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  User: SunJianwei<327021593@qq.com>
  Date: 16-6-2 14:15
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>电子书城</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/home.css">
</head>

<body>

    <!-- 好评榜 -->
    <div class="wrapper">
        <div class="module_title">   <!-- 模块头 -->
            好评榜 <a class="moreLink" href="${pageContext.request.contextPath}/servlet/GetBookByGrade">查看更多</a>
        </div>
        <div class="module_body">   <!-- 模块内容 -->
            <c:forEach items="${requestScope.grades}" var="item" varStatus="status">
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
    </div>



    <!-- 最新发布 -->
    <div class="wrapper">
        <div class="module_title">   <!-- 模块头 -->
            最新发布 <a class="moreLink" href="${pageContext.request.contextPath}/servlet/GetBookByTime">查看更多</a>
        </div>
        <div>   <!-- 模块内容 -->
            <c:forEach items="${requestScope.time}" var="item" varStatus="status">
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
    </div>


    <!-- 特价图书 -->
    <div class="wrapper">
        <div class="module_title">   <!-- 模块头 -->
            特价图书 <a class="moreLink" href="${pageContext.request.contextPath}/servlet/GetBookByPrice">查看更多</a>
        </div>
        <div>   <!-- 模块内容 -->
            <c:forEach items="${requestScope.price}" var="item" varStatus="status">
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
    </div>

</body>
</html>