package com.zed.domain.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 统一返回状态码
 *
 * @author zed
 */
@Getter
@AllArgsConstructor
public enum SystemResultCode implements IResultCode {

    /**
     * 成功状态码
     */
    SUCCESS(0, "成功"),

    /**
     * 网关和请求相关异常
     */
    GATEWAY_INNER_ERROR(100, "网关内部错误。"),
    UN_LOGIN(401, "未登录,请登录后再试！"),
    LOGIN_FAIL(402, "登陆失败,请重试!"),
    FORBIDDEN(403, "参数校验不通过"),
    TOO_MANY_REQUEST(429, "系统繁忙了,请稍后重试!"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    SERVICE_DEGRADE(505, "服务降级"),


    /**
     * 系统错误：1000-1999
     */
    SYSTEM_INNER_ERROR(1000, "系统内部错误。"),
    SYSTEM_INNER_RUNTIME_ERROR(1002, "系统内部运行期错误。"),
    SYSTEM_OTHER_ERROR(1003, "其它系统错误。"),
    SERVICE_INNER_ERROR(1004, "服务内部错误。"),
    BZI_DEFINE_NUM_ERROR(1005, "业务定义数存在异常"),
    GET_NACOS_BZI_ERROR(1006, "获取nacos业务配置异常"),
    INIT_NACOS_BZI_ERROR(1007, "初始化nacos业务配置异常"),
    PUBLISH_NACOS_BZI_ERROR(1008, "发布nacos业务配置失败"),


    /**
     * 接口错误：2000-2999
     */
    INTERFACE_INNER_INVOKE_ERROR(2001, "内部系统接口调用异常,请重试当前操作"),
    INTERFACE_OUTTER_INVOKE_ERROR(2002, "外部系统接口调用异常,请重试当前操作"),
    INTERFACE_FORBID_VISIT(2003, "该接口禁止访问。"),
    INTERFACE_ADDRESS_INVALID(2004, "接口地址无效。"),
    INTERFACE_REQUEST_TIMEOUT(2005, "接口请求超时。"),
    INTERFACE_EXCEED_LOAD(2006, "接口负载过高。"),


    /**
     * 权限错误：3000-3999
     */
    PERMISSION_NO_ACCESS(3001, "无访问权限。"),
    RESOURCE_ALREADY_EXISTED(3002, "请求资源已存在。"),
    RESOURCE_NOT_EXISTED(3003, "请求资源不存在。"),
    ROLE_NOT_EXIST(3004, "当前用户无%s访问权限"),
    ROLE_TENANT_BOSS_NOT_EXIST(3005, "当前租户成员无企业主角色权限"),
    ROLE_TENANT_SYSTEM_ADMIN_NOT_EXIST(3006, "当前租户成员无系统管理员角色权限"),
    ROLE_TENANT_ADMIN_NOT_EXIST(3007, "当前租户成员无普通管理员角色权限"),
    ROLE_TENANT_NORMAL_NOT_EXIST(3008, "当前租户成员无普通角色权限"),
    ROLE_GOD_TRIASCLOUD_NOT_EXIST(3009, "无三叠云系统管理员权限"),
    NOT_TENANT_BINDING(3010, "当前用户无任何绑定的租户企业"),
    GET_TENANT_USER_INFO_FAIL(3011, "获取用户信息失败"),
    OPERATION_NOT_EXISTED(3012, "当前用户缺少模块操作权限,请向管理员授权"),

    /**
     * 数据错误：4000-4999
     */
    DATA_NOT_FOUND(4001, "数据未找到。"),

    DATA_IS_WRONG(4002, "数据有误。"),

    DATA_ALREADY_EXISTED(4003, "数据已存在。"),

    DATA_STORAGE_ERROR(4004, "数据保存失败。"),

    //并发抢占资源锁定
    RESOURCE_LOCK(4005, "请求失败,请稍后再试。"),

    CREATE_FAIL(4006, "添加失败,请重试"),

    UPDATE_FAIL(4007, "更新失败,请重试"),

    DELETE_FAIL(4008, "删除失败,请重试"),


    /**
     * 参数错误：5000-5999
     */
    PARAM_IS_INVALID(5000, "参数无效。"),

    PARAM_IS_BLANK(5001, "参数为空。"),

    PARAM_TYPE_BIND_ERROR(5002, "参数类型错误。"),

    PARAM_NOT_COMPLETE(5003, "缺少请求参数。"),

    MESSAGE_NOT_READABLE(5004, "请求信息无法读取。"),

    MESSAGE_NOT_WRITABLE(5005, "返回信息无法写入。"),

    METHOD_NOT_ALLOWED(5006, "不支持当前请求方法。"),

    MEDIA_TYPE_UNSUPPORTED(5007, "不支持当前媒体类型。"),

    MEDIA_TYPE_NOT_ACCEPTABLE(5008, "不允许当前媒体类型。"),

    /**
     * 鉴权错误： 6000-6999
     */
    ACCOUNT_PASSWORD_ERROR(6000, "账号或者密码错误");


    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;

}
