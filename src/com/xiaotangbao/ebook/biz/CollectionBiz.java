package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.CollectionDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-7 16:59
 */
public class CollectionBiz {

    private CollectionDao collectionDao;

    public CollectionBiz() {
        collectionDao = new CollectionDao();
    }

    /**
     * 收藏
     *
     * @param userId
     * @param bookId
     * @throws Exception
     */
    public void addItem(int userId, int bookId) throws Exception {
        if (userId <= 0 || bookId <= 0) {
            throw new Exception("参数错误，用户和书的id都必须大于0");
        }
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("userid", userId);
        fields.put("bookid", bookId);
        collectionDao.insert(fields, null);
    }

    /**
     * 获取用户收藏的图书
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCollectionByUser(int userId) throws Exception {
        BookBiz bookBiz = new BookBiz();
        Map<String, Object> conds = new HashMap<>();
        conds.put("userid", userId);
        List<String> fields = new ArrayList<>();
        fields.add("bookid");
        List<Map<String, Object>> collectionList = collectionDao.getByConds(conds, fields, null);
        List<Map<String, Object>> bookList = new ArrayList<>();
        for (Map<String, Object> collection: collectionList) {
            int bookId = (int) collection.get("bookid");
            Map<String, Object> bookInfo = bookBiz.getBookInfoById(bookId);
            bookList.add(bookInfo);
        }
        return bookList;
    }

}
