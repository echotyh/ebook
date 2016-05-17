package com.xiaotangbao.ebook.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaotangbao.ebook.biz.BookBiz;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 进入上传图书页面之前，查出所有的图书类型
 *
 * @author SunJianwei<327021593@qq.com>
 * @date 16-5-17 10:29
 */
public class GetAllBookTypeSvl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookBiz bookBiz = new BookBiz();
        Map<String, List<Object[]>> datas = bookBiz.getAllBookTypes();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(JSONObject.toJSON(datas));
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
