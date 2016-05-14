package com.xiaotangbao.ebook.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 构造sql语句
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-4-9 20:04
 */
public class SqlBuilder {

    public static final int SQL_TYPE_SELECT = 0;
    public static final int SQL_TYPE_UPDATE = 1;
    public static final int SQL_TYPE_INSERT = 2;


    /**
     * 生成 select 语句，包含？的预处理语句，where参数为？，需要执行时和值对应
     *
     * @param table
     * @param fields
     * @param conds     String => String | Number | String[2]
     * @param appends
     * @return  String
     * @throws BaseException    当table，fields为空时，conds参数个数或类型不对时
     */
    public static String buildSelect(String table, List<String> fields,
                                     Map<String, Object> conds, List<String> appends) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (null == fields || 0 == fields.size()) {
            System.out.println("build sql error:empty fields");
        }
        //fields
        for (String field : fields) {
            if (field.contains("(")) {
                // 如果传入的不是列名，而是像count(col)这样，就不需要``
                sql.append(field + ",");
            } else {
                sql.append("`" + field + "`,");
            }
        }
        sql.setLength(sql.length() - 1);
        //from
        if (null == table || 0 == table.length()) {
            System.out.println("build sql error:empty table");
        }
        sql.append(" FROM `" + table + "`");
        //where
        if (null != conds && 0 < conds.size()) {
            sql.append(" WHERE ");
            Set<String> keySet = conds.keySet();
            for (String key : keySet) {
                sql.append("(`" + key + "`");
                Object value = conds.get(key);
                if (value instanceof String || value instanceof Number) {
                    sql.append("=?) AND ");
                } else if (value instanceof Object[]) {
                    Object[] arrValue = (Object[]) value;
                    if (2 != arrValue.length) {
               
                           System.out.println("build sql error:conds value's length should be 2");
                    }
                    sql.append(arrValue[1] + "?) AND ");
                } else {
                    System.out.println("build sql error:conds' value should be String or Object[] or Number");
                }
            }
            sql.setLength(sql.length() - 5);
        }
        //appends
        if (null != appends && 0 < appends.size()) {
            for (String append : appends) {
                sql.append(" " + append);
            }
        }

        return sql.toString();
    }

    /**
     * @see     SqlBuilder#buildSelect(String, List, Map, List)
     * @throws  BaseException
     */
    public static String buildUpdate(String table, Map<String, Object> fields,
                                     Map<String, Object> conds) throws Exception {
        StringBuilder sql = new StringBuilder("UPDATE ");
        if (null == table || 0 == table.length()) {
            System.out.println("build sql error:empty table");
        }
        sql.append("`" + table + "` ");
        if (null == fields || 0 == fields.size()) {
            System.out.println("build sql error:empty fields");
        //set
        sql.append("SET");
        Set<String> keySet = fields.keySet();
        for (String key : keySet) {
            sql.append(" `" + key + "`");
            Object value = fields.get(key);
            if (value instanceof String || value instanceof Number) {
                sql.append("=?,");
            } else {
                System.out.println("build sql error:fields' value should be String or Number when update");
            }
        }
        sql.setLength(sql.length() - 1);

        //WHERE
        if (null != conds && 0 < conds.size()) {
            sql.append(" WHERE ");
            keySet = conds.keySet();
            for (String key : keySet) {
                sql.append("(`" + key + "`");
                Object value = conds.get(key);
                if (value instanceof String || value instanceof Number) {
                    sql.append("=?) AND ");
                } else if (value instanceof Object[]) {
                    Object[] arrValue = (Object[]) value;
                    if (2 != arrValue.length) {
                        System.out.println("build sql error:conds value's length should be 2");
                    }
                    sql.append(" " + arrValue[1] + " ?) AND ");
                } else {
                System.out.println("build sql error:conds' value should be String or Object[] or Number");
                }
            }
            sql.setLength(sql.length() - 5);
        }
        }
        return sql.toString();
        
        }
       
		
        
    /**
     * @see     SqlBuilder#buildSelect(String, List, Map, List)
     * @throws  BaseException
     */
    public static String buildInsert(String table, Map<String, Object> fields,
                               List<String> appends) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT ");
        if (null == table || 0 == table.length()) {
            System.out.println("build sql error:empty table");
        }
        sql.append("`" + table + "` ");
        if (null == fields || 0 == fields.size()) {
            System.out.println("build sql error:empty fields");
        }
        //set
        sql.append("SET");
        Set<String> keySet = fields.keySet();
        for (String key : keySet) {
            sql.append(" `" + key + "`");
            Object value = fields.get(key);
            if (value instanceof String || value instanceof Number) {
                sql.append("=?,");
            } else {
                System.out.println("build sql error:fields' value should be String or Number when update");
            }
        }
        sql.setLength(sql.length() - 1);

        //appends
        if (null != appends && 0 < appends.size()) {
            for (String append : appends) {
                sql.append(" " + append);
            }
        }

        return sql.toString();
    }

    /**
     * @see     SqlBuilder#buildSelect(String, List, Map, List)
     * @throws  BaseException
     */
    public static String buildDelete(String table, Map<String, Object> conds, List<String> appends)
            throws Exception {
        StringBuilder sql = new StringBuilder("DELETE");

        //from
        if (null == table || 0 == table.length()) {
            System.out.println("build sql error:empty table");
        }
        sql.append(" FROM `" + table + "`");
        //where
        if (null != conds && 0 < conds.size()) {
            sql.append(" WHERE ");
            Set<String> keySet = conds.keySet();
            for (String key : keySet) {
                sql.append("(`" + key + "`");
                Object value = conds.get(key);
                if (value instanceof String || value instanceof Number) {
                    sql.append("=?) AND ");
                } else if (value instanceof Object[]) {
                    Object[] arrValue = (Object[]) value;
                    if (2 != arrValue.length) {
                        System.out.println("build sql error:conds value's length should be 2");
                    }
                    sql.append(arrValue[1] + "?) AND ");
                } else {
                    System.out.println("build sql error:conds' value should be String or Object[] or Number");
                }
            }
            sql.setLength(sql.length() - 5);
        }
        //appends
        if (null != appends && 0 < appends.size()) {
            for (String append : appends) {
                sql.append(" " + append);
            }
        }

        return sql.toString();
    }
}
