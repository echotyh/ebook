package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.BookSeriesDao;
import com.xiaotangbao.ebook.entity.BookSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-17 21:18
 */
public class BookSeriesBiz {

    /**
     * 如果不存在则插入（考虑同一个用户，不严格的并发控制）
     *
     * @param series
     * @param author
     * @return 返回系列实体
     * @throws Exception
     */
    public BookSeries setAndGetSeriesNx(String series, String author) throws Exception {
        BookSeries bookSeries = new BookSeries();
        BookSeriesDao dao = new BookSeriesDao();
        Map<String, Object> conds = new HashMap<String, Object>();
        conds.put("name", series);
        List<String> fields = new ArrayList<String>();
        fields.add("bookseriesid");
        fields.add("authorname");
        List<Map<String, Object>> list = dao.getByConds(conds, fields, null);
        if (!list.isEmpty()) {
            Map<String, Object> row = list.get(0);
            bookSeries.setName(series);
            bookSeries.setAuthorname((String) row.get("authorname"));
            bookSeries.setBookseriesid((int) row.get("bookseriesid"));
            return bookSeries;
        }
        conds.put("authorname", author);
        long newId = dao.insert(conds, null);
        bookSeries.setName(series);
        bookSeries.setAuthorname(author);
        bookSeries.setBookseriesid((int) newId);
        return bookSeries;
    }


}
