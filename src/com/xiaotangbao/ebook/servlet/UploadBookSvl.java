package com.xiaotangbao.ebook.servlet;

import com.alibaba.fastjson.JSONObject;
import com.xiaotangbao.ebook.biz.BookBiz;
import com.xiaotangbao.ebook.biz.BookSeriesBiz;
import com.xiaotangbao.ebook.entity.Book;
import com.xiaotangbao.ebook.entity.BookSeries;
import com.xiaotangbao.ebook.entity.User;
import com.xiaotangbao.ebook.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 作者上传图书
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-16 14:11
 */
public class UploadBookSvl extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> params = null;
        try {
            params = parseRequest(request);
            validateParams(params);
            User user = checkLogin(request);

            Book book = new Book();
            book.setBookname((String) params.get("name"));
            book.setPublishcompany((String) params.get("publishCompany"));
            book.setPublishdate((String) params.get("publishDate"));
            book.setTypeid((int) params.get("type2"));
            book.setPrice((int) ((float) params.get("price") * 100));
            book.setIntroduction((String) params.get("introduction"));
            book.setPicture((File) params.get("picture"));
            book.setContent((File) params.get("content"));
            book.setAuthorid(user.getUserid());

            String series = (String) params.get("series");
            BookSeriesBiz bookSeriesBiz = new BookSeriesBiz();
            BookSeries bookSeries = bookSeriesBiz.setAndGetSeriesNx(series, user.getName());
            // 判断是否是系列作者是否是自己
            if (!bookSeries.getAuthorname().equals(user.getName())) {
                throw new Exception("此系列不属于自己");
            }
            book.setSeriesid(bookSeries.getBookseriesid());
            BookBiz bookBiz = new BookBiz();
            String rootRealPath = request.getRealPath("/");
            long bookId = bookBiz.addBook(book, rootRealPath);
            if (bookId > 0) {
                result.put("error", 0);
                result.put("errmsg", "成功！");
                result.put("bookId", bookId);
                out.print(JSONObject.toJSON(result));
            }
            throw new Exception("失败");
        } catch (Exception e) {
            result.put("errno", 1);
            result.put("errmsg", e.getMessage());
            out.print(JSONObject.toJSON(request));
        } finally {
            out.flush();
            out.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private Map<String, Object> parseRequest(HttpServletRequest request)
            throws FileUploadException,UnsupportedEncodingException,IOException,Exception {
        Map<String, Object> params = new HashMap<String, Object>();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> items = upload.parseRequest(request);
        Iterator<FileItem> itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = itr.next();
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = item.getString("UTF-8");
                params.put(fieldName, fieldValue);
            } else {
                if (item.getName() != null && !item.getName().equals("")) {
                    // item.getName()返回上传文件在客户端的完整路径名称
                    //System.out.println("上传文件的名称:" + item.getName());
                    //System.out.println("上传文件的大小:" + item.getSize());
                    //System.out.println("上传文件的类型:" + item.getContentType());
                    String contentType = item.getContentType();
                    if ("picture".equals(item.getFieldName())) {
                        if (null == StringUtil.regexMatch("^image/(.*)$", contentType, 0)) {
                            throw new Exception("图片文件格式不对");
                        }
                    }
                    if ("content".equals(item.getFieldName())) {
                        if (null == StringUtil.regexMatch("^application/pdf$", contentType, 0)) {
                            throw new Exception("图书文件只支持PDF");
                        }
                    }

                    String tmpFileName = new Random().nextInt(Integer.MAX_VALUE) + "";
                    File tmpFile = new File(tmpFileName);
                    while (!tmpFile.createNewFile()) {
                        tmpFileName = new Random().nextInt(Integer.MAX_VALUE) + "";
                        tmpFile = new File(tmpFileName);
                    }
                    item.write(tmpFile);
                    params.put(item.getFieldName(), tmpFile);
                } else {
                    //params.put(item.getFieldName(), null);
                }
            }
        }
        return params;
    }

    private void validateParams(Map<String, Object> params) throws Exception {
        String name = (String) params.get("name");
        String series = (String) params.get("series");
        String publishCompany = (String) params.get("publishCompany");
        String publishDate = (String) params.get("publishDate");
        String type2 = (String) params.get("type2");
        File picture = (File) params.get("picture");
        File content = (File) params.get("content");
        String price = (String) params.get("price");
        String introduction = (String) params.get("introduction");
        if (null == name || name.isEmpty()) {
            throw new Exception("书名不能为空");
        } else if (name.length() > 100) {
            throw new Exception("书名太长");
        }
        if (null == series || name.isEmpty()) {
            throw new Exception("系列不能为空");
        } else if (series.length() > 45) {
            throw new Exception("系列太长");
        }
        if (null == publishCompany || publishCompany.isEmpty()) {
            throw new Exception("出版公司不能为空");
        } else if (name.length() > 100) {
            throw new Exception("出版公司太长");
        }
        if (null == StringUtil.regexMatch("^\\d{4}-\\d{2}-\\d{2}", publishDate, 0)) {
            throw new Exception("出版日期不对");
        }
        try {
            int typeid = Integer.parseInt(type2);
            params.put("type2", typeid);
        } catch (NumberFormatException e) {
            throw new Exception("类型不存在");
        }
        if (picture.length() > 1024 * 1024) {
            throw new Exception("图片太大");
        }
        if (content.length() > 100 * 1024 * 1024) {
            throw new Exception("图书太大");
        }
        if (null == introduction || introduction.isEmpty()) {
            throw new Exception("简介不能为空");
        } else if (introduction.length() > 500) {
            throw new Exception("简介太长");
        }
        try {
            float priceFloat = Float.parseFloat(price);
            params.put("price", priceFloat);
        } catch (NumberFormatException e) {
            throw new Exception("价格必须是数字");
        }
    }

    /**
     * 检测登录，未登录直接抛出异常
     *
     * @param request
     * @return 返回登录用户实体
     * @throws Exception
     */
    private User checkLogin(HttpServletRequest request) throws Exception {
        User user = (User) request.getSession(true).getAttribute("user");
        if (null == user) {
            throw new Exception("未登录");
        }
        return user;
    }
}
