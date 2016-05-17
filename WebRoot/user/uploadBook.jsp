<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%--
  User: SunJianwei<327021593@qq.com>
  Date: 16-5-15 23:17
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>上传图书作品</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/CSS/uploadBook.css">

    <script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-2.2.3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery.form.js"></script>
    <script type="text/javascript">
        var data = {};
        data["loginUser"] = "${sessionScope.user.name}";
        window.data = data;

        function submitCheck() {
            var name = $("input[name=name]");
            if (name.val().trim().length == 0) {
                name.after("不能为空");
                name.focus();
                return false;
            }
            if (name.val().trim().length > 100) {
                name.after("书名太长啦");
                name.focus();
                return false;
            }
            var series = $("input[name=series]");
            if (series.val().trim().length == 0) {
                series.after("不能为空");
                series.focus();
                return false;
            }
            var publishCompany = $("input[name=publishCompany]");
            if (publishCompany.val().trim().length >= 100) {
                publishCompany.after("太长啦");
                publishCompany.focus();
                return false;
            }
            var picture = $("input[name=picture]");
            var fileList = picture[0].files;
            if (fileList.length == 0) {
                picture.after("上传个图片吧");
                picture.focus();
                return false;
            } else if (!fileList[0].type.match(/image\/(.)+/)) {
                picture.after("只能上传图片哦");
                picture.focus();
                return false;
            }
            if (fileList[0].size > 1024 * 1024) {
                picture.after("图片不能超过1M");
                picture.focus();
                return false;
            }

            var price = $("input[name=price]");
            var PriceValue = parseFloat(price.val());
            if (isNaN(PriceValue)) {
                price.after("价格需要是数字哦");
                price.val("");
                price.focus();
                return false;
            }

            var content = $("input[name=content]");
            fileList = content[0].files;
            if (fileList.length == 0) {
                content.after("请上传图书");
                content.focus();
                return false;
            } else if (!fileList[0].type.match(/application\/pdf/)) {
                content.after("只能上传PDF哦");
                content.focus();
                return false;
            }
            if (fileList[0].size > 100 * 1024 * 1024) {
                content.after("图书不能超过100M");
                content.focus();
                return false;
            }

            var introduction = $("*[name=introduction]");
            var msg = $("div[name=msg_introduction]");
            if (introduction.val().trim().length == 0) {
                msg.html("不能为空");
                introduction.focus();
                return false;
            }
            if (introduction.val().trim().length > 500) {
                msg.html("简介太长啦");
                introduction.focus();
                return false;
            }
            return true;
        }
        //注册事件
        $(function() {
            // 提交事件
            $("#submit").click(function(e) {
                e.preventDefault();
                /*if (!submitCheck()) {
                    return false;
                }*/

                // 提交
                var formData = new FormData(document.getElementById("dataForm"));
                var xhr = new XMLHttpRequest();
                xhr.open('POST', "<%=basePath%>user/UploadBookSvl");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var responseData = JSON.parse(xhr.responseText);
                        if (responseData.errno == 0) {
                            // 成功
                            alert("上传成功");
                        } else {
                            alert(responseData.errmsg);
                        }
                    }
                };
                xhr.send(formData);

                /*$(this).ajaxSubmit({
                    url: "<%=basePath%>user/UploadBookSvl",
                    type: "POST",
                    //enctype: "multipart/form-data",
                    data: formData,
                    //contentType: "multipart/form-data",
                    contentType: false,
                    dataType: "json",
                    success: function(data) {
                        console.log(data);
                        alert(data.errmsg);
                    },
                    error: function (data) {
                        console.log(data.responseText);
                    }
                });*/
            });

            // 自动填充图书系列事件
            $("input[name=name]").keyup(function() {
                var series = $("input[name=series]");
                var seriesValue = this.value;
                if (seriesValue && seriesValue.indexOf(" ") >= 0) {
                    series.val(this.value.slice(0, seriesValue.indexOf(" ")));
                } else if (seriesValue && seriesValue.indexOf("第") >= 0) {
                    series.val(this.value.slice(0, seriesValue.indexOf("第")));
                } else if (seriesValue && seriesValue.indexOf("之") >= 0) {
                    series.val(this.value.slice(0, seriesValue.indexOf("之")));
                } else {
                    series.val(this.value);
                }
            });

            // 注册图书类型切换事件
            $("#type1").change(function() {
                var type1 = $(this).val();
                var type2 = window.data.types[type1];
                var type2Ele = $("#type2");
                type2Ele.html("");
                for (var i = 0; i < type2.length; i++) {
                    var type2Name = type2[i][0];
                    var typeid = type2[i][1];
                    var nodeHtml = "<option value='" + typeid + "'>" + type2Name + "</option>";
                    type2Ele.append(nodeHtml);
                }
            });

        });

    </script>


</head>

<body>
    <div  class="wrapper">
        <form id="dataForm" enctype="multipart/form-data" onsubmit="return submitCheck();">
            <table>
                <tr>
                    <td>书名</td>
                    <td>
                        <input type="text" name="name" maxlength="100" placeholder="请在这里输入书名" autofocus required />
                    </td>
                    <td><div class="errmsg" name="msg_name"></div></td>
                </tr>
                <tr>
                    <td>系列</td>
                    <td>
                        <input type="text" name="series" placeholder="请在这里输入所属系列"  required />
                    </td>
                    <td><div class="errmsg" name="msg_series"></div></td>
                </tr>
                <tr>
                    <td>出版社</td>
                    <td>
                        <input type="text" name="publishCompany" placeholder="请在这里输入出版社"  required />
                    </td>
                    <td><div class="errmsg" name="msg_publishCompany"></div></td>
                </tr>
                <tr>
                    <td>出版日期</td>
                    <td>
                        <input type="date" name="publishDate" required />
                    </td>
                    <td><div class="errmsg" name="msg_publishDate"></div></td>
                </tr>
                <tr>
                    <td>类别</td>
                    <td>
                        <select id="type1" name="type1"></select>
                        <select id="type2" name="type2"></select>
                        <script type="text/javascript">
                            // 加载 图书类型信息
                            $.ajax({
                                type: "get",
                                url: "${pageContext.request.contextPath}/servlet/GetAllBookTypeSvl",
                                async: false,
                                dataType: "json",
                                success: function(response) {
                                    window.data['types'] = response;
                                    var type1Ele = $("#type1");
                                    // type1
                                    for (var type1 in response) {
                                        var nodeHtml = "<option value='" + type1 + "'>" + type1 + "</option>";
                                        type1Ele.append(nodeHtml);
                                    }

                                    type1Ele[0].firstChild.selected = true;
                                    var firstType1Name = type1Ele[0].firstChild.value;
                                    // type2
                                    var type2Ele = $("#type2");
                                    var type2List = response[firstType1Name];
                                    for (var i = 0; i < type2List.length; i++) {
                                        var type2 = type2List[i][0];
                                        var typeid = type2List[i][1];
                                        var nodeHtml = "<option value='" + typeid + "'>" + type2 + "</option>";
                                        type2Ele.append(nodeHtml);
                                    }

                                }
                            });
                        </script>
                    </td>
                    <td><div class="errmsg" name="msg_type"></div></td>
                </tr>
                <tr>
                    <td>展示图片</td>
                    <td>
                        <input type="file" name="picture" required />
                    </td>
                    <td><div class="errmsg" name="msg_picture"></div></td>
                </tr>
                <tr>
                    <td>价格</td>
                    <td>
                        <input type="num" name="price" placeholder="单位为元" required />
                    </td>
                    <td><div class="errmsg" name="msg_price"></div></td>
                </tr>
                <tr>
                    <td>文件</td>
                    <td>
                        <input type="file" name="content" required />
                    </td>
                    <td><div class="errmsg" name="msg_content"></div></td>
                </tr>
                <tr>
                    <td>简介</td>
                    <td colspan="2">
                        <div><textarea name="introduction" required></textarea></div>
                        <div class="errmsg" name="msg_introduction"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <button id="submit">上 传</button>
                        <%--<input type="submit" id="submit" value="上 传"/>--%>
                    </td>
                </tr>
            </table>


        </form>
    </div>

</body>
</html>