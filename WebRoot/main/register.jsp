<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">

    <title>注册</title>
    <meta charset="utf-8">
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


        // 密码校验：两次相同，长度8～12位，字母+数字
        function checkPasswd1() {
            var passwd  = $("input[name='user_pwd']").val().trim();
            if (passwd.length < 8 || passwd.length > 12) {
                $("#message_pwd1").html("<font color='green'>密码长度为8～12位</font>");
                $("input[name='user_pwd']").focus();
                return false;
            }

            var containsLetter = false;
            var contailsNumber = false;
            for (var i = 0; i < passwd.length; i++) {
                var code = passwd.charCodeAt(i);
                if ((code >= "A".charCodeAt(0) && code <= "Z".charCodeAt(0))
                        || (code >= "a".charCodeAt(0) && code <= "z".charCodeAt(0))) {
                    containsLetter = true;
                } else if (code >= "0".charCodeAt(0) && code <= "9".charCodeAt(0)) {
                    contailsNumber = true;
                } else {
                    $("input[name='user_pwd']").val("");
                    $("#message_pwd1").html("<font color='green'>密码必须包含数字和字母哦～</font>");
                    $("input[name='user_pwd']").focus();
                    return false;
                }
            }
            if (contailsNumber && containsLetter) {
                return true;
            } else {
                $("input[name='user_pwd']").val("");
                $("#message_pwd1").html("<font color='green'>密码必须包含数字和字母哦～</font>");
                $("input[name='user_pwd']").focus();
                return false;
            }
        }

  
        function checkPasswd2() {
            var passwd  = $("input[name='user_pwd']").val().trim();
            var passwd2 = $("input[name='user_pwd2']").val().trim();
            if (passwd != passwd2) {
                $("#message_pwd2").html("<font color='green'>两个密码不相同哦～</font>");
                $(this).focus();
                return false;
            }
            return true;
        }
        function checkPhoneNumber() {
            var phoneElement = $("input[name='user_phone']");
            var phoneNumber = phoneElement.val().trim();
            if (!/[0-9]{11}/.test(phoneNumber)) {
                $("#message_phone").html("<font color='green'>手机号是11位哦～</font>");
                phoneElement.focus();
                return false;
            }
            return true;
        }
        function check() {
            return checkPasswd1() && checkPasswd2() && checkPhoneNumber();
        }

        $(function () {
            // 判断用户是否存在
            $("input[name='user_name']").change(function () {
                var nameVal = $(this).val();
                nameVal = $.trim(nameVal);
                if (nameVal != "") {
                    var url = "${pageContext.request.contextPath }/servlet/ValidateUsername";
                    var args = {"user_name": nameVal, "time": new Date()};
                    $.post(url, args, function (data) {
                        $("#message_name").html(data);
                    });
                }
            });

            $("input[name='user_pwd']").change(checkPasswd1);
            $("input[name='user_pwd2']").change(checkPasswd2);
        });




    </script>
    
</head>

<body>
<div id="login" style="background-position:25% 30px;">

    <div id="login_content">
        <form onsubmit="return check();" action="<%=basePath%>servlet/RegisterSvl" method="post">
            <table cellspacing="10px">
                <caption>~欢迎注册~</caption>
                <tr>
                    <td>用 户 名：</td>
                    <td><input type="text" name="user_name" required/></td>
                    <td>
                        <div id="message_name"></div>
                    </td>
                </tr>
                <tr>
                    <td>密  码：</td>
                    <td><input type="password" name="user_pwd" required/></td>
                     <td>
                        <div id="message_pwd1"></div>
                    </td>
                </tr>
                <tr>
                    <td>重复密码：</td>
                    <td><input type="password" name="user_pwd2" required/></td>
                     <td>
                        <div id="message_pwd2"></div>
                    </td>
                </tr>
                <tr>
                    <td>移动电话：</td>
                    <td><input type="tel" name="user_phone" required/></td>
                     <td>
                        <div id="message_phone"></div>
                    </td>
                </tr>
                
                <tr>
                    <td>电子邮箱:</td>
                    <td><input type="email" name="user_email" required/></td>
                     <td>
                        <div id="message_email"></div>
                    </td>
                </tr>
                <tr>
                    <td>生  日：</td>
                    <td><input type="date" name="user_birth" required/></td>
                     <td>
                        <div id="message_birth"></div>
                    </td>
                </tr>
                 <tr>
                    <td>身  份：</td>
                    <td>
                        <input type="radio" name="user_status" value="0" checked="checked"/>读者<input type="radio"
                                                                                                     name="user_status"
                                                                                                     value="1"/>作者               
                    </td>
                </tr>
                <tr>
                    <td>性  别：</td>
                    <td><input type="radio" name="user_gender" value="F" checked="checked"/>女<input type="radio"
                                                                                                    name="user_gender"
                                                                                                    value="M"/>男
                    </td>
                </tr>
                
               

                

                <tr>
                    <td colspan="2"><span><c:if test="${msg != null}">${msg} </c:if></span></td>
                </tr>
                <tr>
                    <td>
                        <input type="button" value="登  录" class="re"
                               onclick="window.location.href='<%=basePath%>main/login.jsp'"/></td>
                    <td>
                        <input type="submit" class="lo" value="注 册" />
                    </td>
                </tr>
            </table>
        </form>


        
  </div>
  </div>
  </body>
  
  
</html>
