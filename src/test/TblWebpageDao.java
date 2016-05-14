package com.vg.module.dao;

import com.vg.exception.BaseException;
import com.vg.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收录的网页 表
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-2 10:23
 */
public class TblWebpageDao extends BaseDao {

    public TblWebpageDao() {
        db      = "news";
        table   = "tblWebpage";
    }

    /**
     * 根据title取数据库中原有的网页数据
     *
     * @param title
     * @return
     * @throws BaseException
     */
    public List<Map<String, Object>> getByTitle(String title) throws BaseException {
        if (null == title || "".equals(title)) {
            return null;
        }
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("title", title);
        List<String> fields = new ArrayList<String>();
        fields.add("id");
        fields.add("title");
        fields.add("abstract");
        fields.add("content");
        fields.add("hash");
        fields.add("time");
        fields.add("status");
        fields.add("extData");
        List<Map<String, Object>> resultList =  getByConds(conds, fields, null);
        return resultList;
    }

    /**
     * 根据hash检查是否存在
     *
     * @param   hash
     * @return  boolean     true:已存在
     * @throws BaseException
     */
    public boolean checkExistByHash(String hash) throws BaseException {
        if (null == hash || "".equals(hash)) {
            throw new NullPointerException("hash is null at TblWebpageDao.countByHash()");
        }
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("hash", hash);
        long count = countByConds(conds, null);
        return (count > 0) ? true : false;
    }

}
