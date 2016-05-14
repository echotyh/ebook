package com.xiaotangbao.ebook.util;


import java.util.Random;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-4-9 16:13
 */
public class MySQLDBSelector extends BaseDBSelector {

    private static int last = 0;

    /**
     * 选择数据库
     *
     * @param   master      boolean     是否是主机
     * @param   method      int         选择方式（随机|轮询）
     * @return String[] 第一个元素是主机IP，第二个是端口号
     */
    @Override
    public String[] select(boolean master, int method) {
        if (master) {
            return DBConfig.masterHost;
        } else {
            if (RANDOM == method) {
                int rand = new Random().nextInt(DBConfig.allHost.length);
                return DBConfig.allHost[rand];
            } else {
                last = last++ % DBConfig.allHost.length;
                return DBConfig.allHost[last];
            }
        }
    }
}
