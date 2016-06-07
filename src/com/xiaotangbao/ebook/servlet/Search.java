package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.BookBiz;
import com.xiaotangbao.ebook.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书搜索
 *
 * @author SunJianwei<327021593@qq.com>
 * @date 16-6-7 12:36
 */
@WebServlet(name = "Search", urlPatterns = "/search")
public class Search extends HttpServlet {

    private static final int MAX_PAGE_SIZE      = 200;
    private static final int DEFAULT_PAGE_SIZE  = 24;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            String query = request.getParameter("query");
            List<Map<String, Object>> bookList = new ArrayList<>();
            int page = 1;
            int pageSize = DEFAULT_PAGE_SIZE;

            if (null != query && !query.isEmpty()) {
                // TODO 处理空
                // 获取page、pageSize参数
                String pageStr = request.getParameter("page");

                if (null != pageStr && !pageStr.isEmpty()) {
                    page = Integer.parseInt(pageStr);
                    if (page <= 0) {
                        page = 1;
                    }
                }
                String pageSizeStr = request.getParameter("pageSize");

                if (null != pageSizeStr && !pageSizeStr.isEmpty()) {
                    pageSize = Integer.parseInt(pageSizeStr);
                    if (pageSize <= 0) {
                        pageSize = DEFAULT_PAGE_SIZE;
                    } else if (pageSize > MAX_PAGE_SIZE) {
                        pageSize = MAX_PAGE_SIZE;
                    }
                }
                int offset = (page - 1) * pageSize;

                BookBiz bookBiz = new BookBiz();
                bookList = bookBiz.queryLike(query, offset, pageSize);

            }

            if (null != bookList && !bookList.isEmpty()) {
                request.setAttribute("books", bookList);
            }

            // 返回是带着查询的页码参数
            Map<String, Integer> queryParams = new HashMap<String, Integer>();
            queryParams.put("page", page);
            queryParams.put("pageSize", pageSize);
            request.setAttribute("queryParams", queryParams);
            request.setAttribute("title", "查询 " + StringUtil.htmlEscape(query) + " 的结果");
            request.setAttribute("url", request.getContextPath() + "/search?query=" + query + "&page=" + page + "&pageSize=" + pageSize);
            request.getRequestDispatcher("/main/book_list.jsp").forward(request, response);

        } finally {

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
