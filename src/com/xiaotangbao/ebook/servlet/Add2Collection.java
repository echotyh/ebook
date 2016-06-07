package com.xiaotangbao.ebook.servlet;

import com.alibaba.fastjson.JSONObject;
import com.xiaotangbao.ebook.biz.CollectionBiz;
import com.xiaotangbao.ebook.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 收藏图书
 *
 * @author SunJianwei<327021593@qq.com>
 * @date 16-6-7 16:44
 */
@WebServlet(name = "Add2Collection", urlPatterns = "/servlet/add2collection")
public class Add2Collection extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();
        try {
            User loginUser = (User) request.getSession(true).getAttribute("user");
            if (null == loginUser) {
                throw new Exception("未登录");
            }

            String bookIdStr = request.getParameter("book");
            if (null == bookIdStr || bookIdStr.isEmpty()) {
                throw new Exception("参数错误：需要有book");
            }
            int bookId = Integer.parseInt(bookIdStr);

            CollectionBiz collectionBiz = new CollectionBiz();
            collectionBiz.addItem(loginUser.getUserid(), bookId);

            // 成功时的返回
            result.put("errno", 0);
        } catch (NumberFormatException e) {
            result.put("errno", 1);
            result.put("errmsg", "参数转换成数字出错了");
        } catch (Exception e) {
            result.put("errno", 2);
            result.put("errmsg", e.getMessage());
        } finally {
            out.print(JSONObject.toJSONString(result));
            out.flush();
            out.close();
        }
    }

}
