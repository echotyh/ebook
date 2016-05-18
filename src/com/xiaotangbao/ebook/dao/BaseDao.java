package com.xiaotangbao.ebook.dao;




import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.xiaotangbao.ebook.util.DBUtil;
import com.xiaotangbao.ebook.util.MySQLDBSelector;
import com.xiaotangbao.ebook.util.SqlBuilder;


public class BaseDao {

    protected String db     = "";

    protected String table  = "";

    protected DBUtil dbUtil = null;

    public BaseDao() {
        dbUtil = new DBUtil();
    }

    /**
     * 条件查询
     *
     * @param conds
     * @param fields
     * @param appends
     * @param closeConn
     * @return
     * @throws BaseException
     */
    public List<Map<String,Object>> getByConds(Map<String, Object> conds, List<String> fields, 
                                               List<String> appends, boolean closeConn) throws Exception {
        MySQLDBSelector selector = new MySQLDBSelector();
        String[] hostport = selector.select(false, MySQLDBSelector.RANDOM);
        dbUtil.init(hostport[0], hostport[1], db);
        String sql = SqlBuilder.buildSelect(table, fields, conds, appends);
        Object[] params = null;
        if (null != conds && 0 < conds.size()) {
            int len = conds.size();
            params = new Object[len];
            Set<String> keySet = conds.keySet();
            int i = 0;
            for (String key : keySet) {
                Object cond = conds.get(key);
                if (cond instanceof String || cond instanceof Number) {
                    params[i++] = cond;
                } else if (cond instanceof Object[]) {
                    Object[] arrValue = (Object[]) cond;
                    if (2 != arrValue.length) {
                        System.out.println("build sql error:conds value's length should be 2");
                    }
                    params[i++] = arrValue[0];
                } else {
                    System.out.println("build sql error:conds' value should be String or Object[] or Number");
                }
            }
        }
        List<Map<String, Object>> result = dbUtil.query(sql, params, closeConn);
        return result;
    }
    public List<Map<String,Object>> getByConds(Map<String, Object> conds, List<String> fields,
                                               List<String> appends) throws Exception {
        return getByConds(conds, fields, appends, true);
    }

    /**
     * count查询
     *
     * @param conds
     * @param appends
     * @param closeConn
     * @return
     * @throws BaseException
     */
    public long countByConds(Map<String, Object> conds, List<String> appends, boolean closeConn) throws Exception {
        List<String> fields = new ArrayList<String>();
        fields.add("COUNT(*) AS CNT");
        List<Map<String, Object>> result = getByConds(conds, fields, appends, closeConn);
        if (null == result) {
            return 0;
        } else {
            return (long)result.get(0).get("CNT");
        }
    }
    public long countByConds(Map<String, Object> conds, List<String> appends) throws Exception {
        return countByConds(conds, appends, true);
    }


    /**
     * 更新
     *
     * @param   conds
     * @param   fields
     * @param   closeConn
     * @return
     * @throws BaseException
     */
    public int updateByConds(Map<String, Object> conds, Map<String, Object> fields, boolean closeConn) throws Exception {
        MySQLDBSelector selector = new MySQLDBSelector();
        String[] hostport = selector.select(true, MySQLDBSelector.RANDOM);
        dbUtil.init(hostport[0], hostport[1], db);
        String sql = SqlBuilder.buildUpdate(table, fields, conds);

        //预处理语句的传入值
        int len = 0;
        if (null != fields) {
            len += fields.size();
        }
        if (null != conds) {
            len += conds.size();
        }
        Object[] params = new Object[len];
        int index = 0;
        if (null != fields && 0 < fields.size()) {
            Set<String> keySet = fields.keySet();
            int i = 0;
            for (String key : keySet) {
                params[index++] = fields.get(key);
            }
        }

        if (null != conds && 0 < conds.size()) {
            Set<String> keySet = conds.keySet();
            int i = 0;
            for (String key : keySet) {
                Object cond = conds.get(key);
                if (cond instanceof String || cond instanceof Number) {
                    params[index++] = cond;
                } else if (cond instanceof Object[]) {
                    Object[] arrValue = (Object[]) cond;
                    if (2 != arrValue.length) {
                        System.out.println("build sql error:conds value's length should be 2");
                    }
                    params[index++] = arrValue[0];
                } else {
                    System.out.println("build sql error:conds' value should be String or Object[] or Number");;
                }
            }
        }
        int result = dbUtil.executeUpdate(sql, params, closeConn);
        return result;
    }

    public int updateByConds(Map<String, Object> conds, Map<String, Object> fields) throws Exception {
        return updateByConds(conds, fields, true);
    }

    /**
     * 插入，返回新插入的id
     *
     * @param   fields          插入的数据
     * @param   appends
     * @param   closeConn
     * @return                  新插入的id
     * @throws BaseException
     */
    //fields 是键值，直接插入，第二个参数基本没有用，第三个参数为true表示查询完直接关闭连接
    public long insert(Map<String, Object> fields, List<String> appends, boolean closeConn) throws Exception {
        MySQLDBSelector selector = new MySQLDBSelector();
        String[] hostport = selector.select(true, MySQLDBSelector.RANDOM);
        dbUtil.init(hostport[0], hostport[1], db);
        String sql = SqlBuilder.buildInsert(table, fields, appends);

        //预处理语句的传入值
        Object[] params = null;
        if (null != fields && 0 < fields.size()) {
            int len = fields.size();
            params = new Object[len];
            Set<String> keySet = fields.keySet();
            int i = 0;
            for (String key : keySet) {
                params[i++] = fields.get(key);
            }
        }

        long insertId = 0;
        try {
            //放在try块中确保可以关闭链接
            boolean addResult = dbUtil.insert(sql, params, false);
            if (!addResult) {
                System.out.println("insert failed");
            }
            insertId = dbUtil.lastInsertId(closeConn);
        } finally {
            if (closeConn) {
                dbUtil.close();
            }
        }
        return insertId;
    }
    public long insert(Map<String, Object> fields, List<String> appends) throws Exception {
        return insert(fields, appends, true);
    }

    public int delByConds(Map<String, Object> conds, List<String> appends, boolean closeConn) throws Exception {
        MySQLDBSelector selector = new MySQLDBSelector();
        String[] hostport = selector.select(true, MySQLDBSelector.RANDOM);
        dbUtil.init(hostport[0], hostport[1], db);
        String sql = SqlBuilder.buildDelete(table, conds, appends);

        Object[] params = null;
        if (null != conds && 0 < conds.size()) {
            int len = conds.size();
            params = new Object[len];
            Set<String> keySet = conds.keySet();
            int i = 0;
            for (String key : keySet) {
                Object cond = conds.get(key);
                if (cond instanceof String || cond instanceof Number) {
                    params[i++] = cond;
                } else if (cond instanceof Object[]) {
                    Object[] arrValue = (Object[]) cond;
                    if (2 != arrValue.length) {
                        System.out.println("build sql error:conds value's length should be 2");
                    }
                    params[i++] = arrValue[0];
                } else {
                    System.out.println("build sql error:conds' value should be String or Object[] or Number");
                }
            }
        }
        return dbUtil.executeUpdate(sql, params, closeConn);
    }
    public int delByConds(Map<String, Object> conds, List<String> appends) throws Exception {
        return delByConds(conds, appends, true);
    }

    // 事务控制相关

    public synchronized void startTransaction() throws SQLException {
        init();
        dbUtil.startTransaction();
    }
    public synchronized void commit() throws SQLException {
        dbUtil.commit();
    }
    public synchronized void rollback() throws SQLException {
        dbUtil.rollback();
    }
    public synchronized void close() {
        dbUtil.close();
    }

    private synchronized void init() {
        MySQLDBSelector selector = new MySQLDBSelector();
        String[] hostport = selector.select(true, MySQLDBSelector.RANDOM);
        dbUtil.init(hostport[0], hostport[1], db);
    }

    // Getter and Setter

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }
}
