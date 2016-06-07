package com.xiaotangbao.ebook.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author      SunJianwei<327021593@qq.com>
 * @date        16-6-1 12:10
 */
public class OrderDao extends BaseDao {

    public OrderDao() {
        db      = "ebook";
        table   = "orders";
    }
    
    public long addOreder(int userId,int bookId,String time,String status,BigDecimal price) throws Exception{
    	Map<String, Object> fields = new HashMap<String, Object>();
    	
        fields.put("userid", userId);
        fields.put("bookid",bookId);
        fields.put("time",time);
        fields.put("status",status);
        fields.put("price",price);
        long result;
        result = insert(fields, null, false);
    	
    	return result;
    }
    

}
