package com.xiaotangbao.ebook.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.xiaotangbao.ebook.entity.User;
import com.xiaotangbao.ebook.util.MD5tool;


public class UserDao extends BaseDao {
	public UserDao(){
		db = "ebook";
		table = "user";
	}
	//获取所有用户的名字
	public List<String> getAllNames() throws Exception{
		List<String> fields = new ArrayList<String>();
		fields.add("username");
		Map<String, Object> conds = new HashMap<String,Object>();
		List<Map<String, Object>> resultList = getByConds(conds, fields, null);
		List<String> names = new ArrayList<String>();
		for (Map<String, Object> row : resultList){
			names.add((String) row.get("username"));
		}
		
		return names;
	}
	@Test
	public void testGetAllNames() throws Exception{
		List<String> names = new ArrayList<String>();
		names = getAllNames();
		System.out.println("names" + names);
		
	}
	//插入一条数据
	public int register(String name,String password,String phone,String gender,String status,String email,String birth) throws Exception{
		
		 Map<String, Object> conds = new HashMap<String, Object>();
	        long count = countByConds(conds, null);
	        
	        Map<String, Object> fields = new HashMap<>();
	        
	        fields.put("username", name);
	        fields.put("password", MD5tool.MD5(password));
	        fields.put("status", status);
	        fields.put("gender", gender);
	        fields.put("birth", birth);
	        fields.put("phone", phone);
	        fields.put("email", email);
	        fields.put("memberid", 0);
	        fields.put("Expenditure", 0);
	        
	        List list =null ;
	        int newId =(int) insert(fields, list,true);
			return newId;
	}
	@Test
	public void testRegister() throws Exception{
		int i = register("fubiqi1","fubiqi2","18844195315","F","1","1298731@qq.com","1994-2-18");
	    System.out.println("i  "+i);
	}
	//根据用户名和密码查找一条数据
	public User login(String userName,String password) throws Exception{
		User user = null;
		
		 Map<String, Object> conds = new HashMap<String, Object>();
	        conds.put("username", userName);
	        conds.put("password", MD5tool.MD5(password));
	        List<String> fields = new ArrayList<String>();
	        fields.add("userid");
	        fields.add("gender");
	        fields.add("status");
	        fields.add("birth");
	        fields.add("phone");
	        fields.add("email");
	        fields.add("Expenditure");
	        List<Map<String, Object>> resultList = getByConds(conds, fields, null);
	        if (resultList.size()!=0){
	        	user = new User();
	        for (Map<String, Object> row : resultList) {
				user.setUserid((int) row.get("userid"));
    			user.setGender((String) row.get("gender"));
    			user.setStatus((String) row.get("status"));
    			user.setEmail((String) row.get("email"));
    			user.setExpenditure((int) row.get("Expenditure"));
    			//user.setExpenditure((double) row.get("expenditure"));
    			user.setName(userName);
    			user.setPassword(password);
    			user.setPhone((String) row.get("phone"));
    			user.setBirth((String) row.get("birth"));
	        }
	        }
	        return user;    
	}
	//修改余额
	public int updateExpend(int userid,int expend) throws Exception{
		int result = 0;
		Map<String, Object> conds = new HashMap<String, Object>();
		conds.put("userid", userid);
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("Expenditure", expend);
		result = updateByConds(conds, fields);
		return result;
	}
	//查询一用户余额
	public int getExpend(int userid) throws Exception{
		List<Map<String,Object>> users = new ArrayList<Map<String,Object>>();
		Map<String, Object> conds = new HashMap<String,Object>();
		conds.put("userid", userid);
		List<String> fields =new ArrayList<String>();
		fields.add("Expenditure");
		users = getByConds(conds,  fields, 
                null, true);
		if(!users.isEmpty()){
			return (int)users.get(0).get("Expenditure");
		}
		
		return 0;
	}

	@Test
	public void test() throws Exception{
		
		
		User user = new User();
		 Map<String, Object> conds = new HashMap<String, Object>();
	        long count = countByConds(conds, null);
	        System.out.println("count"+count);
	       
		
	     
	        
	}
}


