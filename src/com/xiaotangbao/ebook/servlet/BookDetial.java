package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.BookBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进入图书详情页面之前取出相关数据的servlet
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-3 12:43
 */
@WebServlet(name = "BookDetial", urlPatterns = "/servlet/BookDetail")
public class BookDetial extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        try {
            // 处理参数
            String idStr = request.getParameter("book");
            int id = Integer.parseInt(idStr);
            // 查图书数据
            BookBiz bookBiz = new BookBiz();
            Map<String, Object> bookInfo = bookBiz.getBookInfoById(id);
            // 获取图书的评论      commentid, content, userid, username
            List<Map<String, Object>> comments = bookBiz.getComments(id);
            // 将评论分为好评（good）、中评（middle）、差评（bad）
            Map<String, List<Map<String, Object>>> commentSplited = splitComments(comments);
            // 处理结果 跳转
            if (null == bookInfo) {
                request.setAttribute("errmsg", "图书已经不存在了");
                request.getRequestDispatcher("/main/error.jsp").forward(request, response);
            } else {
                request.setAttribute("book", bookInfo);
                request.setAttribute("comments", commentSplited);
                request.getRequestDispatcher("/main/book_detail.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errmsg", "参数类型错误，book参数应该为数字形式的id");
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errmsg", e.getMessage());
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 将评论分为好评（good）、中评（middle）、差评（bad）
     *
     * @param comments
     * @return
     */
    private Map<String, List<Map<String, Object>>> splitComments (List<Map<String, Object>> comments) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        if (null == comments || comments.isEmpty()) {
            return result;
        }
        List<Map<String, Object>> l = new ArrayList<>();
        List<Map<String, Object>> m = new ArrayList<>();
        List<Map<String, Object>> h = new ArrayList<>();
        for (Map<String, Object> comment : comments) {
            int grade = Integer.parseInt((String) comment.get("grade"));
            if (grade < 3) {
                // 差评
                l.add(comment);
            } else if (grade < 5) {
                m.add(comment);
            } else {
                h.add(comment);
            }
        }
        result.put("bad", l);
        result.put("middle", m);
        result.put("good", h);
        return result;
    }
}
