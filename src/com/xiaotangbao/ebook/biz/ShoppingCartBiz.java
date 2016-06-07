package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.BookDao;
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

    /**
     * 向购物车中添加
     *
     * @param userId
     * @param bookId
     * @param num
     * @throws Exception
     */
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

    /**
     * 获取购物车里的图书。
     *
     * @param   userId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getItems(int userId) throws Exception {
        if (userId <= 0) {
            throw new Exception("参数错误，用户id必须大于0");
        }
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("userid", userId);
        List<String> fields = new ArrayList<>();
        fields.add("bookid");
        fields.add("booknum");

        List<Map<String, Object>> cartItems = dao.getByConds(conds, fields, null);
        // 根据id批量查数据库 select ...from ..where id in()
        StringBuilder ids = new StringBuilder();
        ids.append("(");
        for (Map<String, Object> item : cartItems) {
            int id = (int) item.get("bookid");
            ids.append(id + ",");
        }
        ids.setLength(ids.length() - 1);
        ids.append(")");
        BookDao bookDao = new BookDao();
        conds.clear();
        conds.put("#", "`bookid` IN " + ids);
        conds.put("checked", "y");
        conds.put("saled", "y");

        fields.clear();
        fields.add("bookid");
        fields.add("bookname");
        fields.add("price");
        fields.add("discount");
        fields.add("introduction");
        List<Map<String, Object>> bookInfos = bookDao.getByConds(conds, fields, null);
        // id => bookInfo
        Map<Integer, Map<String, Object>> bookInfosById = new HashMap<>();
        for (Map<String, Object> bookInfo : bookInfos) {
            int id = (int) bookInfo.get("bookid");
            bookInfosById.put(id, bookInfo);
        }

        // 合并book、shoppingcart item两部分数据
        for (Map<String, Object> item : cartItems) {
            int id = (int) item.get("bookid");
            item.putAll(bookInfosById.get(id));
        }
        return cartItems;
    }

    /**
     * 从购物车中删除
     *
     * @param userId
     * @param bookId
     * @throws Exception
     */
    public void deleteItem(int userId, int bookId) throws Exception {
        if (userId <= 0 || bookId <= 0) {
            throw new Exception("参数错误，用户和书的id都必须大于0");
        }
        Map<String, Object> conds = new HashMap<>();
        conds.put("userid", userId);
        conds.put("bookid", bookId);
        dao.delByConds(conds, null);
    }


}
