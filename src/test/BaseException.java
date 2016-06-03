package test;

import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-4-6 23:03
 */
public class BaseException extends Exception {

    private int errno;      //错误编号
    private int level;      //记录日志的等级
    private Map<String, String> params = null;  //出错时的参数

    public BaseException(){
        this("unknown exception", Errno.UNKNOWN, null, LogLevel.ERROR);
    }

    public BaseException(int errno) {
        this("unknown exception", errno, null, LogLevel.ERROR);
    }

    public BaseException(String message) {
        this(message, Errno.UNKNOWN, null, LogLevel.ERROR);
    }

    public BaseException(String message, int errno) {
        this(message, errno, null, LogLevel.ERROR);
    }

    public BaseException(String message, int errno, Map<String, String> params) {
        this(message, errno, null, LogLevel.ERROR);
    }

    public BaseException(String message, int errno, int level) {
        this(message, errno, null, level);
    }

    /**
     * 构造方法
     *
     * @param message   异常的文字描述
     * @param errno     异常编号
     * @param params    抛异常时的参数信息
     * @param level     异常记录日志的等级
     */
    public BaseException(String message, int errno, Map<String, String> params, int level) {
        super(message);
        this.errno = errno;
        this.level = level;
        this.params = params;
    }

    /**
     * 获取异常编号
     * @return  int     编号定义见{@link Errno}
     */
    public int getErrno() {
        return errno;
    }

    /**
     * 获取异常级别
     *
     * @return  int     各种级别定义见{@link LogLevel}
     */
    public int getLevel() {
        return level;
    }

    /**
     * 获取异常参数
     *
     * @return  Map<String, String>
     */
    public Map<String, String> getParams() {
        return params;
    }

}
