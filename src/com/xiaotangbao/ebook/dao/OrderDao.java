package com.xiaotangbao.ebook.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    public long addOrder(int userId,int bookId,String time,String status,BigDecimal price) throws Exception{
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
    //获得某用户购买的bookid
    public List<Integer> getBoughtBooks(int userid) throws Exception{
    	Map<String, Object> conds =new  HashMap<String,Object>();
    	conds.put("userid", userid);
    	conds.put("status", "y");
    	List<String> fields =new ArrayList<String>();
    	fields.add("bookid");
    	List<Map<String,Object>> items = getByConds(conds,fields, null);
    	List<Integer> books = new ArrayList<Integer>();
    	for(Map<String,Object> item : items){
    		int bookid = (int)item.get("bookid");
    		books.add(bookid);
    	}
    	return books;
    }

}
