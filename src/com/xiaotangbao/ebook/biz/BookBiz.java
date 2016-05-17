package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.BookDao;
import com.xiaotangbao.ebook.dao.BookTypeDao;
import com.xiaotangbao.ebook.entity.Book;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-17 11:44
 */
public class BookBiz {

    /**
     *
     * @return 不为null 格式：
     *          strType1 : [
     *                          [ type2 , typeid ],
     *                          [ type2 , typeid ]
     *                      ]
     *
     */
    public Map<String, List<Object[]>> getAllBookTypes() {
        BookTypeDao dao = new BookTypeDao();
        Map<String, List<Object[]>> result = dao.getAllType();
        if (null == result) {
            result = new HashMap<String, List<Object[]>>();
        }
        return result;
    }

    /**
     * 添加图书
     * @param book
     * @param rootRealPath
     * @return 成功返回新加的id，失败返回-1
     * @throws Exception
     */
    public long addBook(Book book, String rootRealPath) throws Exception {
        // 插入数据库，获取id
        BookDao bookDao = new BookDao();
        try {
            bookDao.startTransaction();
            Map<String, Object> conds = new HashMap<String, Object>();
            conds.put("seriesid", book.getSeriesid());
            conds.put("bookname", book.getBookname());
            conds.put("authorid", book.getAuthorid());
            conds.put("publishdate", book.getPublishdate());
            conds.put("pulishcompany", book.getPublishcompany());
            conds.put("typeid", book.getTypeid());
            conds.put("introduction", book.getIntroduction());
            conds.put("price", book.getPrice());
            conds.put("checked", "n");
            conds.put("saled", "n");
            conds.put("discount", 10);
            conds.put("conmmentnum", 0);
            conds.put("averagegrade", 0);
            long bookId = bookDao.insert(conds, null, false);
            book.setBookid((int) bookId);
            File picture = new File(rootRealPath + "/" + "book-images/" + bookId + ".png");
            boolean movedPicture = book.getPicture().renameTo(picture);
            if (!movedPicture) {
                bookDao.rollback();
                book.getPicture().delete();
                return -1;
            }
            File content = new File(rootRealPath + "/WEB-INF/pdf/" + bookId + ".pdf");
            boolean movedContent = book.getContent().renameTo(content);
            if (movedContent) {
                bookDao.commit();
                return bookId;
            } else {
                bookDao.rollback();
                book.getPicture().delete();
                book.getContent().delete();
                return -1;
            }
        } finally {
            bookDao.close();
        }
    }
}
