package com.xiaotangbao.ebook.biz;


import com.xiaotangbao.ebook.dao.UserDao;
import com.xiaotangbao.ebook.entity.User;


public class UserBiz {
	
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
		 int i = userdao.register(name, password, phone, gender, status, email, birth);
		return i;
	}

}
