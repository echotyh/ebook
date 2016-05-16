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
    <!-- <link rel="stylesheet" type="text/css" href="styles.css"> -->

</head>

<body>
    <form method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>书名</td>
                <td>
                    <input type="text" name="name" placeholder="请在这里输入书名" autofocus required />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>系列</td>
                <td>
                    <input type="text" name="series" placeholder="请在这里输入所属系列"  required />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>出版社</td>
                <td>
                    <input type="text" name="publishCompany" placeholder="请在这里输入出版社"  required />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>出版日期</td>
                <td>
                    <input type="date" name="publishDate" required />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>类别</td>
                <td>
                    <select name="type1">
                        <option value="id1">测试大类别1</option>
                        <option value="id2">测试大类别2</option>
                    </select>
                    <select name="type2">
                        <option value="id1">测试小类别1</option>
                        <option value="id2">测试小类别2</option>
                    </select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>展示图片</td>
                <td>
                    <input type="file" name="picture" required />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>简介</td>
                <td>
                    <textarea name="introduction"></textarea>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>价格</td>
                <td>
                    <input type="number" name="price" required />
                </td>
                <td></td>
            </tr>
            <tr>
                <td>文件</td>
                <td>
                    <input type="file" name="content" required />
                </td>
                <td></td>
            </tr>
        </table>
        <input type="submit" value="上传" />

    </form>
</body>
</html>