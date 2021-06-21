package com.zed.domain.result;

import com.zed.domain.constants.Constant;
import com.zed.domain.exceptions.BusinessException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author zed
 */
@Data
@ToString
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 默认请求成功业务码
     */
    public static final int SUCCESS = 0;

    /**
     * 默认请求失败业务码
     */
    public static final int FAIL = -1;

    /**
     * 状态码
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 承载数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String message;

    private Result(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private Result(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
        this.success = SUCCESS == code;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable Result<?> result) {
        return Optional.ofNullable(result)
                .map(x -> ObjectUtils.nullSafeEquals(SUCCESS, x.code))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable Result<?> result) {
        return !Result.isSuccess(result);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> data(T data) {
        return data(data, Constant.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> data(T data, String msg) {
        return data(SUCCESS, data, msg);
    }

    /**
     * 返回R
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> data(int code, T data, String msg) {
        return new Result<>(code, data, data == null ? Constant.DEFAULT_NULL_MESSAGE : msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(SUCCESS, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 请求成功，无需返回值
     *
     * @return Result
     */
    public static Result success() {
        return new Result<>(SystemResultCode.SUCCESS);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(FAIL, null, msg);
    }

    /**
     * 返回R
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> fail(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 降级操作,返回默认降级数据
     *
     * @param degradeData
     * @param <T>
     * @return
     */
    public static <T> Result<T> degrade(T degradeData) {
        return new Result<>(FAIL, degradeData, Constant.DEFAULT_FAILURE_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param flag 成功状态
     * @return Result
     */
    public static <T> Result<T> status(boolean flag) {
        return flag ? success(Constant.DEFAULT_SUCCESS_MESSAGE) : fail(Constant.DEFAULT_FAILURE_MESSAGE);
    }

    /**
     * 向上抛出服务调用的错误
     *
     * @return
     */
    public Result popError() {
        if (code == SystemResultCode.SERVICE_DEGRADE.getCode()) {
            throw new BusinessException(SystemResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        throw new BusinessException(this.code, this.message);
    }


}
