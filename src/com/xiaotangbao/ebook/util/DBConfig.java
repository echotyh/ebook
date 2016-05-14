package com.xiaotangbao.ebook.util;

/**
 * MySQL数据库地址配置
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-4-9 15:55
 */
public class DBConfig {

    /**
     * 所有的数据库
     */
    public static String allHost[][] = {
            {"121.42.139.59",   "13306"},
            {"115.28.31.40",    "13306"},
    };

    /**
     * 主库
     */
    public static String masterHost[] = {
            "121.42.139.59",   "13306",
    };

    /**
     * 从库
     */
    public static String slaveHosts[][] = {
            {"121.42.139.59",   "13306"},
    };

}
