package com.xiaotangbao.ebook.biz;


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaotangbao.ebook.dao.OrderDao;
import com.xiaotangbao.ebook.dao.UserDao;
import com.xiaotangbao.ebook.entity.User;
import com.xiaotangbao.ebook.util.PdfUtil;


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

    /**
     * 阅读
     *
     * @param   user            登录用户
     * @param   bookId          要读的书的id
     * @param   page            要读的页数，如果为0，按照阅读记录接着读
     * @param   rootRealPath    工程根目录（“/”）所在的绝对路径
     * @return                  返回对应也的图像数据
     * @throws  Exception
     */
    public BufferedImage readBook(User user, int bookId, int page, String rootRealPath) throws Exception {
        ReadRecordBiz readRecordBiz = new ReadRecordBiz(user.getUserid(), bookId);
        page = canReadBook(user, bookId, page);
        if (page < 0) {
            // 没有读权限
            throw new Exception("要购买才等读后面的内容");
        }
        // 有权限阅读，取出对应页码的内容
        BookBiz bookBiz = new BookBiz();
        File file = bookBiz.getBookById(bookId, rootRealPath);
        if (null == file) {
            throw new Exception("图书不存在");
        }
        BufferedImage imageDatas = PdfUtil.getPage(file, page);

        // 记录此时读到的页数
        readRecordBiz.record(page);

        return imageDatas;
    }

    /**
     * 判断用户是否有权限读page页
     *
     * @param   user
     * @param   bookId
     * @param   page
     * @return  返回页数，如果没有权限返回-1
     * @throws  Exception
     */
    public int canReadBook(User user, int bookId, int page) throws Exception {
        ReadRecordBiz readRecordBiz = new ReadRecordBiz(user.getUserid(), bookId);
        if (0 == page) {
            // 获取阅读记录
            page = readRecordBiz.getRecord();
        }
        if (page <= 0) {
            page = 1;
        }
        // 判断是否已买此书，如果没买过根据试读限制页数
        OrderBiz orderBiz = new OrderBiz();
        boolean bought = orderBiz.checkIfBoughtBook(user.getUserid(), bookId);
        if (!bought && page > BookBiz.READ_MAX_FREE_PAGE) {
            return -1;
        }
        return page;
    }
    //修改余额
    public int updateExpend(int userid,int expend) throws Exception{
    	try{
    		UserDao userdao = new UserDao();
    		return userdao.updateExpend(userid, expend);
    	}catch(Exception e){
    		throw new Exception("小服有點累，請掃后再試哦。");
    		
    	}
    	
    }
    //獲得用戶已經购买的书
    public List<Map<String,Object>> getBoughtBooks(int userid) throws Exception{
    	OrderDao orderdao = new OrderDao();
    	List<Integer> bookids = orderdao.getBoughtBooks(userid);
    	List<Map<String,Object>> books = new ArrayList<Map<String,Object>>();
    	Map<String,Object> book = new HashMap<String,Object>();
    	if(bookids.isEmpty()){
    		return books;
    	}
    	BookBiz bookbiz = new BookBiz();
    	
    	for(int bookid : bookids){
    		 book = bookbiz.getBookInfoById(bookid);
    		 books.add(book);
    	}
    	return books;
    }
    //获取用户余额
    public int getExpend(int userid) throws Exception{
    	UserDao userdao = new UserDao();
    	return userdao.getExpend(userid);
    	
    }
    
}
