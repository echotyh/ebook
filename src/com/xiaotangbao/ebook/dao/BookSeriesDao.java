package com.xiaotangbao.ebook.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-17 20:51
 */
public class BookSeriesDao extends BaseDao {

    public BookSeriesDao() {
        db      = "ebook";
        table   = "bookseries";
    }

    /**
     * 根据系列名检测重复，执行完不关闭连接
     *
     * @param seriesName
     * @return true:存在
     * @throws Exception
     */
    public boolean checkExist(String seriesName) throws Exception {
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("name", seriesName);
        long count = countByConds(conds, null, false);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public long addSeries(String series, String author) throws Exception {
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("name", series);
        fields.put("authorname", author);
        return insert(fields, null, false);
    }



}
