package com.xiaotangbao.ebook.biz;


import java.util.List;

import com.xiaotangbao.ebook.dao.UserDao;
import com.xiaotangbao.ebook.entity.User;


public class UserBiz {
	//验证名字是否在数据库中存在
	public boolean onlyname(String name) throws Exception{
		UserDao userdao = new UserDao();
		List<String> names = userdao.getAllNames();
		if(names.contains(name)){
			return false;
		}else{
		return true;
		}
	}
	
	public User login(String userName,String userPwd) throws Exception{
		
        User user = null;
		
		if(userName != null && userPwd != null && !userName.equals("") && !userPwd.equals("")){
			UserDao userdao = new UserDao();
		
				user = userdao.login(userName, userPwd);
			
		}
		
		return user;
		
	}
	
	public int register(User user) throws Exception{
		String name= user.getName();
		String password = user.getPassword();
		String phone = user.getPhone();
		String gender = user.getGender();
		String status = user.getStatus();
		String email = user.getEmail();
		String birth = user.getBirth();
		 UserDao userdao = new UserDao();
		 int result;
		 if(name == null || name =="" || password == null || password == "" || phone ==null ||
				 phone == "" || email == null || email==""){
			 System.out.println("请输入非空的字段");
			 result = 0;
			 
		 }else{
			 result = userdao.register(name, password, phone, gender, status, email, birth);
		 }
		
		return result;
	}

}
