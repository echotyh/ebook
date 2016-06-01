package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.ReadRecordDao;

import java.text.DateFormat;
import java.util.*;

/**
 * @author      SunJianwei<327021593@qq.com>
 * @date        16-6-1 10:56
 */
public class ReadRecordBiz {

    private int userId;
    private int bookId;
    private ReadRecordDao dao;

    public ReadRecordBiz(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
        dao = new ReadRecordDao();
    }


    /**
     * 获取阅读记录
     *
     * @return  如果没有相关的记录，返回0
     * @throws  Exception   读数据库出错拋异常
     */
    public int getRecord() throws Exception {
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("userid", userId);
        conds.put("bookid", bookId);
        List<String> fields = new ArrayList<String>();
        fields.add("page");
        List<Map<String, Object>> recordList = dao.getByConds(conds, fields, null);
        // 如果没有阅读记录，返回0
        if (null == recordList || recordList.isEmpty()) {
            return 0;
        }
        Map<String, Object> record = recordList.get(0);
        int page = (int) record.get("page");
        return page;
    }

    /**
     * 记录阅读记录，插入，存在则更新
     * 示例sql:   insert readrecord set `userid`=1, `bookid`=1, `page`=1, `time`='1994-11-24' on duplicate key update `page`=2
     *
     * @param   page
     * @throws  Exception   操作数据出错拋异常
     */
    public void record(int page) throws Exception {
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("userid", userId);
        fields.put("bookid", bookId);
        fields.put("page", page);
        // 记录更新时间
        Date now = new Date();
        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        fields.put("time", format.format(now));

        List<String> appends = new ArrayList<String>();
        appends.add("ON DUPLICATE KEY UPDATE `page`=" + page);
        dao.insert(fields, appends);
    }

}
