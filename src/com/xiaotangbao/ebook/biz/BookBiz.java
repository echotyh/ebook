package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.BookDao;
import com.xiaotangbao.ebook.dao.BookTypeDao;
import com.xiaotangbao.ebook.entity.Book;
import com.xiaotangbao.ebook.util.DBConfig;
import com.xiaotangbao.ebook.util.DBUtil;
import org.junit.Test;

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

    public static final int READ_MAX_FREE_PAGE = 30;    // 免费试读的最大页数

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
            conds.put("publishcompany", book.getPublishcompany());
            conds.put("typeid", book.getTypeid());
            conds.put("introduction", book.getIntroduction());
            conds.put("price", book.getPrice());
            conds.put("checked", "n");
            conds.put("saled", "n");
            conds.put("discount", 10);
            conds.put("commentnum", 0);
            conds.put("averagegrade", 0);
            long bookId = bookDao.insert(conds, null, false);
            if (bookId <= 0) {
                bookDao.rollback();
                //清理连个临时文件
                book.getPicture().delete();
                book.getContent().delete();
                return -1;
            }
            book.setBookid((int) bookId);
            File picture = new File(rootRealPath + "/" + "book-images/" + bookId + ".png");
            boolean movedPicture = book.getPicture().renameTo(picture);
            if (!movedPicture) {
                bookDao.rollback();
                //清理连个临时文件
                book.getPicture().delete();
                book.getContent().delete();
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

    /**
     * 根据图书id获取其File对象
     * @param   id
     * @param   rootRealPath
     * @return  不存在返回null
     */
    public File getBookById(int id, String rootRealPath) {
        File file = new File(rootRealPath + "/WEB-INF/pdf/" + id + ".pdf");
        if (!file.exists()) {
            return null;
        }
        return file;
    }

    /**
     * 获取图书信息
     *
     * @param   id
     * @return  图书信息Map | null
     */
    public Map<String, Object> getBookInfoById(int id) {
        if (id <= 0) {
            return null;
        }
        String sql = "SELECT * FROM book b,bookseries s,booktype t,user u " +
                "WHERE b.seriesid=s.bookseriesid AND b.authorid=u.userid AND b.typeid=t.typeid AND " +
                "b.checked='y' AND b.saled='y' AND b.bookid=?" +
                " ORDER BY b.publishdate DESC";
        DBUtil dbUtil = new DBUtil();
        dbUtil.init(DBConfig.masterHost[0], "13306", "ebook");
        List<Map<String, Object>> bookList = dbUtil.query(sql, new Object[] {id});
        if (null == bookList || bookList.isEmpty()) {
            return null;
        } else {
            return bookList.get(0);
        }
    }

    /**
     * 查询图书信息，按发布时间降序
     *
     * @return
     */
    public List<Map<String, Object>> getByTimeDesc(int offset, int limit) {
        String sql = "SELECT * FROM book b,bookseries s,booktype t,user u " +
                "WHERE b.seriesid=s.bookseriesid AND b.authorid=u.userid AND b.typeid=t.typeid AND b.checked='y' AND b.saled='y'" +
                " ORDER BY b.publishdate DESC";
        if (offset < 0) {
            offset = 0;
        }
        if (limit > 0) {
            sql += " LIMIT " + offset + "," + limit;
        }
        DBUtil dbUtil = new DBUtil();
        dbUtil.init(DBConfig.masterHost[0], "13306", "ebook");
        List<Map<String, Object>> bookList = dbUtil.query(sql);
        return bookList;
    }

    /**
     * 查询图书信息，按价格降序
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<Map<String, Object>> getByPriceDesc(int offset, int limit) {
        String sql = "SELECT * FROM book b,bookseries s,booktype t,user u " +
                "WHERE b.seriesid=s.bookseriesid AND b.authorid=u.userid AND b.typeid=t.typeid AND b.checked='y' AND b.saled='y'" +
                " ORDER BY b.price DESC";
        if (offset < 0) {
            offset = 0;
        }
        if (limit > 0) {
            sql += " LIMIT " + offset + "," + limit;
        }
        DBUtil dbUtil = new DBUtil();
        dbUtil.init(DBConfig.masterHost[0], "13306", "ebook");
        List<Map<String, Object>> bookList = dbUtil.query(sql);
        return bookList;
    }

    /**
     * 查询图书信息，按评分降序
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<Map<String, Object>> getByGradeDesc(int offset, int limit) {
        String sql = "SELECT * FROM book b,bookseries s,booktype t,user u " +
                "WHERE b.seriesid=s.bookseriesid AND b.authorid=u.userid AND b.typeid=t.typeid AND b.checked='y' AND b.saled='y'" +
                " ORDER BY b.price DESC";
        if (offset < 0) {
            offset = 0;
        }
        if (limit > 0) {
            sql += " LIMIT " + offset + "," + limit;
        }
        DBUtil dbUtil = new DBUtil();
        dbUtil.init(DBConfig.masterHost[0], "13306", "ebook");
        List<Map<String, Object>> bookList = dbUtil.query(sql);
        return bookList;
    }

    public List<Map<String, Object>> getComments(int bookId) {
        // TODO
        return null;
    }

}
