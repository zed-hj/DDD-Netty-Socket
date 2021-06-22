package com.zed.domain.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统常量
 *
 * @author zed
 */
public interface Constant {

    /**
     * 编码
     */
    String UTF_8 = "UTF-8";

    /**
     * contentType
     */
    String CONTENT_TYPE_NAME = "Content-type";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "service/json;charset=utf-8";

    /**
     * 主键字段名
     */
    String DB_PRIMARY_KEY = "id";

    /**
     * 业务状态[1:正常]
     */
    int DB_STATUS_NORMAL = 1;


    /**
     * 删除状态[0:正常,1:删除]
     */
    int DB_NOT_DELETED = 0;
    int DB_IS_DELETED = 1;

    /**
     * 用户锁定状态
     */
    int DB_ADMIN_NON_LOCKED = 0;
    int DB_ADMIN_LOCKED = 1;

    /**
     * 日志默认状态
     */
    String LOG_NORMAL_TYPE = "1";

    int FIRST_INDEX = 0;

    int MIN_SIZE = 1;

    /**
     * 默认为空消息
     */
    String DEFAULT_NULL_MESSAGE = "暂无承载数据";
    /**
     * 默认成功消息
     */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /**
     * 默认失败消息
     */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";
    /**
     * 默认未授权消息
     */
    String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";

    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 默认密码
     */
    String DEF_USER_PASSWORD = "12345678";

    /**
     * 日志链路追踪id日志标志
     */
    String LOG_TRACE_ID = "traceId";

    /**
     * 存储到nacos的权限前缀
     */
    String AUTH_PREFIX = "AUTH-";

    /**
     * 固定操作集合
     */
    Set<String> FIX_OPERATION = new HashSet<>(Arrays.asList(Operation.CREATE, Operation.READ, Operation.UPDATE, Operation.DELETE));

    /**
     * 默认操作
     */
    class Operation {
        public static String READ = "READ";
        static String CREATE = "CREATE";
        static String UPDATE = "UPDATE";
        static String DELETE = "DELETE";
    }
}
