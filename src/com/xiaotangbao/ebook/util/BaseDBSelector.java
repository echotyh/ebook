package com.xiaotangbao.ebook.util;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-4-9 16:07
 */
public abstract class BaseDBSelector {

    public static final int RANDOM = 0;     //随机方式
    public static final int ROBIN  = 0;     //轮询方式

    /**
     * 选择数据库
     *
     * @param   master      boolean     是否是主机
     * @param   method      int         选择方式（随机|轮询）
     * @return  String[] 第一个元素是主机IP，第二个是端口号
     */
    public abstract String[] select(boolean master, int method);

}
