package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.dao.BookTypeDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-17 11:44
 */
public class BookBiz {

    /**
     *
     * @return 不为null 格式：
     *          strType1 : [
     *                          [ type2 , typeid ],
     *                          [ type2 , typeid ]
     *                      ]
     *
     */
    public Map<String, List<Object[]>> getAllBookTypes() {
        BookTypeDao dao = new BookTypeDao();
        Map<String, List<Object[]>> result = dao.getAllType();
        if (null == result) {
            result = new HashMap<String, List<Object[]>>();
        }
        return result;
    }

}
