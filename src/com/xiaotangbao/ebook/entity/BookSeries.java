package com.xiaotangbao.ebook.entity;

/**
 * 图书系列实体
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-17 21:25
 */
public class BookSeries {

    private int bookseriesid;
    private String name;
    private String authorname;

    public int getBookseriesid() {
        return bookseriesid;
    }

    public void setBookseriesid(int bookseriesid) {
        this.bookseriesid = bookseriesid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }
}
