package com.xiaotangbao.ebook.servlet;

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
        // response.getWriter().print("{ \"errno\" : 0, \"errmsg\" : \"OK\" }");

        request.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("/uploadResult.jsp").forward(request, response);

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
                    System.out.println("上传文件的名称:" + item.getName());
                    System.out.println("上传文件的大小:" + item.getSize());
                    System.out.println("上传文件的类型:" + item.getContentType());

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
}
