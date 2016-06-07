package com.xiaotangbao.ebook.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDao extends BaseDao {
	public CommentDao(){
        db = "ebook";
        table = "comment";
    }

	public long addComment(int userid, int bookid, String comment,int grade,String time) throws Exception{
		Map<String, Object> fields = new HashMap<String,Object>();
		fields.put("userid", userid);
		fields.put("bookid", bookid);
		fields.put("content", comment);
		fields.put("grade", grade);
		fields.put("time", time);
		
		long insert = insert(fields, null, true);
		
		return insert;
	}
}
