package com.xiaotangbao.ebook.biz;

import com.xiaotangbao.ebook.util.DBConfig;
import com.xiaotangbao.ebook.util.DBUtil;

import java.util.List;
import java.util.Map;

/**
 * 图书评论
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-6 09:31
 */
public class CommentBiz {

    /**
     * 查询图书的评论
     * 返回的结果map中包含的key有：
     * commentid, content, grade, userid, username
     *
     * @param   bookId
     * @return  参数错误 | DB操作出错
     * @throws Exception
     */
    public List<Map<String, Object>> getCommentsByBookId(int bookId) throws Exception {
        if (bookId <= 0) {
            throw new Exception("参数错误，id不能小于等于0");
        }
        DBUtil dbUtil = new DBUtil();
        dbUtil.init(DBConfig.slaveHosts[0][0], DBConfig.slaveHosts[0][1], "ebook");
        String sql = "SELECT c.commentid as commentid, c.content as content, c.grade as grade, u.userid as userid, u.username as username" +
                " FROM `comment` c, `user` u WHERE c.userid=u.userid AND c.bookid=?";
        List<Map<String, Object>> commentList = dbUtil.query(sql, new Object[]{bookId});
        return commentList;
    }

}
