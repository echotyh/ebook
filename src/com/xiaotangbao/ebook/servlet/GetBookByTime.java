package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.BookBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 最新发布图书
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-2 23:27
 */
@WebServlet(name = "GetBookByTime", urlPatterns = "/servlet/GetBookByTime")
public class GetBookByTime extends HttpServlet {

    private static final int MAX_PAGE_SIZE      = 200;
    private static final int DEFAULT_PAGE_SIZE  = 24;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 获取page、pageSize参数
            String pageStr = request.getParameter("page");
            int page = 1;
            if (null != pageStr && !pageStr.isEmpty()) {
                page = Integer.parseInt(pageStr);
                if (page <= 0) {
                    page = 1;
                }
            }
            String pageSizeStr = request.getParameter("pageSize");
            int pageSize = DEFAULT_PAGE_SIZE;
            if (null != pageSizeStr && !pageSizeStr.isEmpty()) {
                pageSize = Integer.parseInt(pageSizeStr);
                if (pageSize <= 0) {
                    pageSize = DEFAULT_PAGE_SIZE;
                } else if (pageSize > MAX_PAGE_SIZE) {
                    pageSize = MAX_PAGE_SIZE;
                }
            }
            int offset = (page - 1) * pageSize;

            // 取数据
            BookBiz bookBiz = new BookBiz();
            List<Map<String, Object>> grades = bookBiz.getByTimeDesc(offset, pageSize);
            if (null != grades && !grades.isEmpty()) {
                request.setAttribute("books", grades);
            }

            // 返回是带着查询的页码参数
            Map<String, Integer> queryParams = new HashMap<String, Integer>();
            queryParams.put("page", page);
            queryParams.put("pageSize", pageSize);
            request.setAttribute("queryParams", queryParams);
            request.setAttribute("title", "最新发布");
            request.getRequestDispatcher("/main/book_list.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // 参数不是整数
            request.setAttribute("errmsg", "page、pageSize参数必须是数字");
            request.getRequestDispatcher("/main/error").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errmsg", e.getMessage());
            request.setAttribute("errClass", e.getClass().getName());
            request.getRequestDispatcher("/main/error").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
