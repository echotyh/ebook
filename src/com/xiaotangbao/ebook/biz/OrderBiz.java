package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.OrderDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author      SunJianwei<327021593@qq.com>
 * @date        16-6-1 12:12
 */
public class OrderBiz {

    private OrderDao orderDao;

    public OrderBiz() {
        orderDao = new OrderDao();
    }

    /**
     * 判断用户是否买过
     *
     * @param   userId
     * @param   bookId
     * @return  boolean
     */
    public boolean checkIfBoughtBook(int userId, int bookId) throws Exception {
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("userid", userId);
        conds.put("bookid", bookId);
        conds.put("status", 0); // 订单成功的状态
        long count = orderDao.countByConds(conds, null);
        if (count > 0) {
            return true;
        }
        return false;
    }
    
}
