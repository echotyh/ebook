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
        margin-left: 20px;
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
    <div class="header_logo_box"><a href="<%=basePath%>">不啦不啦 电子书城</a></div>
    <div class="header_searbox">
        <!-- 搜索框 -->
        <input id="search_text" type="text" placeholder="搜索图书" />
        <a href="javascript:void(0);" id="search_button">搜索</a>
    </div>
    <div class="header_links">
        <a href="<%=basePath%>user/showshoppingcart">购物车</a>
        <a href="#">我的图书</a>
        <a href="#">订单</a>
        <a href="#">收藏</a>
    </div>
</div>
<div class="commen_header_placeholder"></div>