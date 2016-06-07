<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%--
  User: SunJianwei<327021593@qq.com>
  Date: 16-6-7 15:29
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script type="text/javascript" src="<%=basePath%>script/jquery-2.2.3.js"></script>
<style>
    body {
        margin: 0;
        padding: 0;
    }
    .commen_header {
        border-bottom: 2px solid black;
        width: 100%;
        height: 50px;
        overflow: visible;
        position: fixed;
        background-color: #D4b9b9;
    }
    .commen_header_placeholder {
        height: 50px;
    }
    .commen_header .header_logo_box{
        float: left;
        font-size: 30px;
        margin-right: 50px;
    }
    .commen_header .header_searbox{
        float:left;
    }
    .commen_header .header_searbox input {
        height:2em;
    }
    .commen_header .header_searbox a{
        line-height: 50px;
    }
    .commen_header .header_links a {
        line-height: 50px;
        float: right;
        margin-right: 20px;
    }
</style>

<script type="text/javascript">
    $(function() {
        $("#search_button").click(function (e) {
            e.preventDefault();
            var query = $("#search_text").val();
            window.location.href = "<%=basePath%>search?query=" + encodeURIComponent(query);
        });
    });
</script>

<div class="commen_header">
    <div class="header_logo_box"><a href="<%=basePath%>">BALALA EBOOK STORE</a></div>
    <div class="header_searbox">
        <!-- 搜索框 -->
        <input id="search_text" type="text" placeholder="搜索图书" />
        <a href="javascript:void(0);" id="search_button">搜索</a>
    </div>
    <div class="header_links">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/main/login.jsp">登录</a>
            </c:when>
            <c:otherwise>
                <span style="float: right;line-height: 50px; margin-right: 20px;">欢迎您：${sessionScope.user.name}</span>
                <c:if test="${sessionScope.user.status eq 1}">
                    <a href="<%=basePath%>user/uploadBook.jsp">上传图书</a>
                </c:if>
            </c:otherwise>
        </c:choose>
        <a href="<%=basePath%>user/showshoppingcart">购物车</a>
        <a href="<%=basePath%>servlet/GetBoughtBooks">我的图书</a>
        <a href="#">订单</a>
        <a href="<%=basePath%>user/getmycollection">收藏</a>
    </div>
</div>
<div class="commen_header_placeholder"></div>