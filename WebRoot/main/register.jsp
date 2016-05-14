<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>注册</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="<%=basePath%>/CSS/login.css"/>
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-2.2.3.js"></script>
    <script>
        $(function () {
            $("input[name='user_name']").change(function () {
                var nameVal = $(this).val();
                nameVal = $.trim(nameVal);
                if (nameVal != "") {
                    var url = "${pageContext.request.contextPath }/servlet/ValidateUsername";
                    var args = {"user_name": nameVal, "time": new Date()};
                    $.post(url, args, function (data) {
                        $("#message").html(data);
                    });
                }

            });
        });

    </script>
</head>

<body>
<div id="login" style="background-position:25% 30px;">

    <div id="login_content">
        <form action="<%=basePath%>servlet/RegisterSvl" method="post">
            <table cellspacing="10px">
                <caption>~欢迎注册~</caption>
                <tr>
                    <td>用户名：</td>
                    <td><input type="text" name="user_name"/></td>
                    <td>
                        <div id="message"></div>
                    </td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td><input type="password" name="user_pwd"/></td>
                </tr>
                <tr>
                    <td>重复密码：</td>
                    <td><input type="password"/></td>
                </tr>
                <tr>
                    <td>移动电话：</td>
                    <td><input type="text" name="user_phone"/></td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td><input type="radio" name="user_gender" value="F" checked="checked"/>女<input type="radio"
                                                                                                    name="user_gender"
                                                                                                    value="M"/>男
                    </td>
                </tr>
                <tr>
                    <td>生日：</td>
                    <td><input type="input" name="user_birth"/></td>
                </tr>
                <tr>
                    <td>身份：</td>
                    <td>
                        <input type="radio" name="user_status" value="0" checked="checked"/>读者<input type="radio"
                                                                                                     name="user_status"
                                                                                                     value="1"/>作者
                    </td>
                </tr>

                <tr>
                    <td>电子邮箱:</td>
                    <td><input type="text" name="user_email"/></td>
                </tr>

                <tr>
                    <td colspan="2"><span><c:if test="${msg != null}">${msg} </c:if></span></td>
                </tr>
                <tr>
                    <td>
                        <input type="button" value="登  录" class="re"
                               onclick="window.location.href='<%=basePath%>main/login.jsp'"/></td>
                    <td>
                        <button class="lo">注 册</button>
                    </td>
                </tr>
            </table>
        </form>

    </div>
</div>

</body>
</html>
