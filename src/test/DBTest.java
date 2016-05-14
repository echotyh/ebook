package test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.xiaotangbao.ebook.util.DBUtil;

public class DBTest {
    
    @Test
    public void excute(){
    	try {
    		DBUtil dbutil = new DBUtil("121.42.139.59", "13306", "ebook");
    		//DBUtil dbutil = new DBUtil("121.42.139.59", "13306", "test_db");
    		String sql = "insert into booktype values(?,?,?)";
        	
        	boolean i = dbutil.insert(sql,new Object[]{1,"xiaoshuo","wenyi"},true );
        	System.out.println(i);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	

    }
    
    @Test
    public void test() {
    	try {
    		DBUtil db = new DBUtil("121.42.139.59", "13306", "ebook");
    		List<Map<String, Object>> list = db.query("select * from booktype", new Object[]{}, true);
    		for (Map<String, Object> row : list) {
    			Set<String> keySet = row.keySet();
    			for (String key : keySet) {
    				System.out.println(key + "   " + row.get(key));
    			}
    			
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
