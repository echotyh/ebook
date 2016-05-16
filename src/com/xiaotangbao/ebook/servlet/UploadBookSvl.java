package com.xiaotangbao.ebook.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 作者上传图书
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-16 14:11
 */
public class UploadBookSvl extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("{ \"errno\" : 0, \"errmsg\" : \"OK\" }");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
