package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.CollectionDao;

import java.util.HashMap;
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

}
