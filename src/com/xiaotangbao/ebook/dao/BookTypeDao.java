package com.xiaotangbao.ebook.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-17 10:44
 */
public class BookTypeDao extends BaseDao {

    public BookTypeDao(){
        db = "ebook";
        table = "booktype";
    }

    /**
     * 获取所有类型
     *
     * @return  strType1 : [
     *                          [ type2 , typeid ],
     *                          [ type2 , typeid ]
     *                      ]
     */
    public Map<String, List<Object[]>> getAllType() {
        Map<String, List<Object[]>> result = new HashMap<String, List<Object[]>>();
        List<Map<String, Object>> typeList = null;

        List<String> fields = new ArrayList<String>();
        fields.add("typeid");
        fields.add("type1");
        fields.add("type2");
        try {
            typeList = getByConds(null, fields, null);
        } catch (Exception e) {
            return null;
        }
        if (null == typeList) {
            return null;
        }
        for (Map<String, Object> row : typeList) {
            int typeid = (int)row.get("typeid");
            String type1 = (String) row.get("type1");
            String type2 = (String) row.get("type2");
            Object[] type2typeidItem = new Object[]{type2, typeid};

            List<Object[]> type2TypeidList0 = result.get(type1);
            if (null == type2TypeidList0) {
                List<Object[]> type2TypeidList = new ArrayList<Object[]>();
                type2TypeidList.add(type2typeidItem);
                result.put(type1, type2TypeidList);
            } else {
                type2TypeidList0.add(type2typeidItem);
            }
        }
        return result;
    }



}
