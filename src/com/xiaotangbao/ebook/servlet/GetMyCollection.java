package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.CollectionBiz;
import com.xiaotangbao.ebook.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 显示我的收藏
 *
 * @author SunJianwei<327021593@qq.com>
 * @date 16-6-7 18:53
 */
@WebServlet(name="GetMyCollection", urlPatterns = "/user/getmycollection")
public class GetMyCollection extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User loginUser = (User) request.getSession(true).getAttribute("user");
            if (null == loginUser) {
                request.getRequestDispatcher("/main/login.jsp").forward(request, response);
                return;
            }
            CollectionBiz collectionBiz = new CollectionBiz();
            List<Map<String, Object>> books = collectionBiz.getCollectionByUser(loginUser.getUserid());
            request.setAttribute("books", books);
            request.getRequestDispatcher("/user/mybooks.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errmsg", "服务器出错啦");
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
            return;
        } finally {

        }
    }
}
