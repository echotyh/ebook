package com.xiaotangbao.ebook.util;


import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL数据库链接工具
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-4-6 16:25
 */
public class DBUtil {

    private String              host;
    private String              port;
    private String              db;
    private String              user;
    private String              password;

    private Connection          conn;
    private PreparedStatement   ps;
    private ResultSet           rs;

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
            //new LogUtil().exception("Class not found:com.mysql.jdbc.Driver", Errno.UNKNOWN, null, LogLevel.ERROR);
        }
    }

    /**
     * @param host  String  IP
     * @param port  String  端口号
     * @param db    String  数据库名
     */
    public DBUtil(String host, String port, String db) {
        this.host = host;
        this.port = port;
        this.db   = db;
        this.user = "root";
        this.password = "MySQLPasswd666!";
    }

    /**
     * 用此构造方法生成的对象使用前需要调用{@link DBUtil#init(String, String, String)}初始化
     */
    public DBUtil() {}

    /**
     * 用默认构造函数生成的对象需要先调用此方法初始化
     *
     * @param host  String  IP
     * @param port  String  端口号
     * @param db    String  数据库名
     */
    public synchronized void init(String host, String port, String db) {
        this.host = host;
        this.port = port;
        this.db   = db;
        this.user = "root";
        this.password = "MySQLPasswd666!";
    }

    /**
     * 获取链接
     *
     * @return  Connection
     */
    private synchronized Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://"+ host +":"+ port +"/" + db + "?useUnicode=true&characterEncoding=UTF-8", user, password);
            }
        } catch (SQLException e) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("host", host);
            params.put("port", port);
            params.put("db", db);
            params.put("user", user);
            params.put("password", "password");
            e.printStackTrace();
          //  new LogUtil().exception(e.getMessage(), Errno.UNKNOWN, params, LogLevel.ERROR);
        }
        return conn;
    }

    /**
     * 查询数据库，默认查询完关闭连接
     *
     * @param   sql     sql语句
     * @return          返回查询结果，List的一项代表一行，某一行的某一列对应Map中的一项
     */
    public synchronized List<Map<String, Object>> query(String sql) {
        return query(sql, new Object[] {});
    }

    /**
     * 查询数据库，支持预处理语句，默认查询完关闭连接
     *
     * @param   sql         sql语句
     * @param   objs        预处理语句中？的传入值
     * @return              返回查询结果，List的一项代表一行，某一行的某一列对应Map中的一项
     */
    public synchronized List<Map<String, Object>> query(String sql, Object[] objs) {
        return query(sql, objs, true);
    }

    /**
     * 查询数据库，支持预处理语句，可以设置是否关闭链接（谨慎）
     *
     * @param   sql         sql语句
     * @param   objs        预处理语句中？的传入值
     * @param   closeConn   查询结束是否关闭连接
     * @return              返回查询结果，List的一项代表一行，某一行的某一列对应Map中的一项
     */
    public synchronized List<Map<String, Object>> query(String sql, Object[] objs, boolean closeConn) {
        ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try {
            ps = getConn().prepareStatement(sql);
            ParameterMetaData pmd = ps.getParameterMetaData();
            int count = pmd.getParameterCount();
            for(int i = 0 ; i < count ; i++){
                ps.setObject(i+1, objs[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            count = rsmd.getColumnCount();
            while(rs.next()){
                Map<String,Object> map = new HashMap<String,Object>();
                for(int i = 0 ; i < count ; i++){
                    String key = rsmd.getColumnName(i+1);
                    Object value = rs.getObject(key);
                    map.put(key, value);
                }
                list.add(map);
            }
        } catch (SQLException e) {
            Map<String, String> logParams = new HashMap<String, String>();
            logParams.put("sql", sql);
            StringBuilder sb = new StringBuilder();
            if (null != objs) {
                for (Object param : objs) {
                    sb.append(param);
                    sb.append(",");
                }
                logParams.put("sqlParam", sb.toString());
            }
            //new LogUtil("mysql").exception("query error", Errno.UNKNOWN, logParams, LogLevel.ERROR);
        } finally {
            if (closeConn) {
                close();
            }
        }
        return list;
    }

    /**
     * 插入（必须使用预处理语句，防止sql注入），默认插入完关闭链接
     *
     * @param   sql           带？的预处理语句
     * @param   params        不能为空
     * @return  boolean
     */
    public synchronized boolean insert(String sql, Object[] params) {
        return insert(sql, params, true);
    }

    /**
     * 插入（必须使用预处理语句，防止sql注入）
     *
     * @param   sql             带？的预处理语句
     * @param   params          不能为空
     * @param   closeConn       插入完是否关闭链接
     * @return  boolean
     */
    public synchronized boolean insert(String sql, Object[] params, boolean closeConn) {
        if (null == params || 0 == params.length) {
        	System.out.println("empty params when insert");
            //new LogUtil("mysql").exception("empty params when insert", Errno.UNKNOWN, null, LogLevel.ERROR);
        }
        boolean result = false;
        try {
            ps = getConn().prepareStatement(sql);
            ParameterMetaData pmd = ps.getParameterMetaData();
            int count = pmd.getParameterCount();
            for(int i = 0 ; i < count ; i++){
                ps.setObject(i+1, params[i]);
            }
            int rows = ps.executeUpdate();
            if (0 < rows) {
                result = true;
            }
        } catch (SQLException e) {
            Map<String, String> logParams = new HashMap<String, String>();
            logParams.put("sql", sql);
            StringBuilder sb = new StringBuilder();
            for (Object param : params) {
                sb.append(param);
                sb.append(",");
            }
            logParams.put("sqlParam", sb.toString());
            //new LogUtil("mysql").exception("insert error", Errno.UNKNOWN, logParams, LogLevel.ERROR);
            System.out.println("insert error");
        } finally {
            if (closeConn) {
                close();
            }
        }
        return result;
    }

    /**
     * 查询本次链接内上次插入数据的id
     *
     * @return  long    id
     */
    public synchronized long lastInsertId(boolean closeConn) {
        long id;
        List<Map<String, Object>> list = query("SELECT LAST_INSERT_ID() as `id`", new Object[] {}, closeConn);
        if (null == list || 0 == list.size()) {
            return 0L;
        } else {
            Map<String, Object> row = list.get(0);
            BigInteger idBig = (BigInteger) row.get("id");
            id = idBig.longValue();
        }
        return id;
    }
    public synchronized long lastInsertId() {
        return lastInsertId(true);
    }
    
    public synchronized boolean execute(String sql, Object[] params, boolean closeConn){
    	boolean result = false;
        try {
            ps = getConn().prepareStatement(sql);
            ParameterMetaData pmd = ps.getParameterMetaData();
            int count = pmd.getParameterCount();
            for(int i = 0 ; i < count ; i++){
                ps.setObject(i+1, params[i]);
            }
            result = ps.execute();
        } catch (SQLException e) {
            Map<String, String> logParams = new HashMap<String, String>();
            logParams.put("sql", sql);
            StringBuilder sb = new StringBuilder();
            for (Object param : params) {
                sb.append(param);
                sb.append(",");
            }
            logParams.put("sqlParam", sb.toString());
            //new LogUtil("mysql").exception("insert error", Errno.UNKNOWN, logParams, LogLevel.ERROR);
            System.out.println("insert error");
        } finally {
            if (closeConn) {
                close();
            }
        }
        return result;
    }

    /**
     * 删除，默认关闭链接
     *
     * @param sql       预处理sql
     * @param params    非空
     * @return          影响行数
     */
    public synchronized int executeUpdate(String sql, Object[] params) {
        return executeUpdate(sql, params, true);
    }

    /**
     * 删除，可设置是否关闭链接
     *
     * @param sql
     * @param params
     * @param closeConn
     * @return
     */
    public synchronized int executeUpdate(String sql, Object[] params, boolean closeConn) {
        if (null == params || 0 == params.length) {
            //new LogUtil("mysql").exception("empty params when executeUpdate", Errno.UNKNOWN, null, LogLevel.ERROR);
        	System.out.println("empty params when executeUpdate");
        }
        int result = 0;
        try {
            ps = getConn().prepareStatement(sql);
            ParameterMetaData pmd = ps.getParameterMetaData();
            int count = pmd.getParameterCount();
            for(int i = 0 ; i < count ; i++){
                ps.setObject(i+1, params[i]);
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            Map<String, String> logParams = new HashMap<String, String>();
            logParams.put("sql", sql);
            StringBuilder sb = new StringBuilder();
            for (Object param : params) {
                sb.append(param);
                sb.append(",");
            }
            logParams.put("sqlParam", sb.toString());
            //new LogUtil("mysql").exception("insert error", Errno.UNKNOWN, logParams, LogLevel.ERROR);
            System.out.println("insert error");
        } finally {
            if (closeConn) {
                close();
            }
        }
        return result;
    }

    /**
     * 开启事务
     *
     * @throws SQLException
     */
    public synchronized void startTransaction() throws SQLException {
        getConn().setAutoCommit(false);
    }

    /**
     * 事务提交
     *
     * @throws SQLException
     */
    public synchronized void commit() throws SQLException {
        getConn().commit();
    }

    /**
     * 事务回滚
     *
     * @throws SQLException
     */
    public synchronized void rollback() throws SQLException {
        getConn().rollback();
    }

    /**
     * 关闭资源
     */
    public synchronized void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断连接是否关闭
     *
     * @return
     * @throws SQLException
     */
    public synchronized boolean isClosed() throws SQLException {
        return getConn().isClosed();
    }

}
