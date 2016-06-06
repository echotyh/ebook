package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.ShoppingCartDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车相关业务
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-6 18:04
 */
public class ShoppingCartBiz {

    private ShoppingCartDao dao;

    public ShoppingCartBiz() {
        dao = new ShoppingCartDao();
    }

    public void addItem(int userId, int bookId, int num) throws Exception {
        if (userId <= 0 || bookId <= 0) {
            throw new Exception("参数错误，用户和书的id都必须大于0");
        }
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("userid", userId);
        fields.put("bookid", bookId);
        fields.put("booknum", num);
        List<String> appends = new ArrayList<String>();
        appends.add("ON DUPLICATE KEY UPDATE booknum=booknum+" + num);
        dao.insert(fields, appends);
    }

}
