package com.zed.infrastructure.exceptions;




import com.zed.domain.exceptions.ExceptionMessage;
import com.zed.infrastructure.result.IResultCode;
import com.zed.infrastructure.result.SystemResultCode;

import java.text.MessageFormat;

/**
 * 业务异常类
 *
 * @author zed
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7344350359780330620L;

    protected IResultCode resultCode;

    private Object data;

    public BusinessException() {
        super();
        this.resultCode = SystemResultCode.SERVICE_INNER_ERROR;
    }

    public BusinessException(int code, String msg) {
        this();

        IResultCode resultCode = new IResultCode() {
            @Override
            public String getMessage() {
                return msg;
            }

            @Override
            public int getCode() {
                return code;
            }
        };
        this.resultCode = resultCode;
    }

    public BusinessException(IResultCode resultCode) {
        this();
        this.resultCode = resultCode;
    }

    /**
     * 处理模板填充
     *
     * @param resultCode 结果
     * @param params     需要替换的内容
     */
    public BusinessException(IResultCode resultCode, Object... params) {
        this();
        final String formatResult = String.format(resultCode.getMessage(), params);
        this.resultCode = new IResultCode() {
            @Override
            public String getMessage() {
                return formatResult;
            }

            @Override
            public int getCode() {
                return resultCode.getCode();
            }
        };
    }

    public BusinessException(Object data) {
        this();
        this.data = data;
    }

    public BusinessException(SystemResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public BusinessException(String message) {
        this(ExceptionMessage.getInstance(message));
    }

    public BusinessException(String message, Object... args) {
        this(MessageFormat.format(message, args));
    }

    public IResultCode getResultCode() {
        return resultCode;
    }

    public Object getData() {
        return data;
    }

    /**
     * 提高性能
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

    /**
     * 直接往外抛错误枚举
     *
     * @param resultCode 错误枚举
     * @return BusinessException
     */
    public static BusinessException throwOut(IResultCode resultCode) {
        throw new BusinessException(resultCode);
    }

    /**
     * 直接往外抛错误枚举模板
     *
     * @param resultCode 错误枚举模板
     * @return BusinessException
     */
    public static BusinessException throwOutTemplate(IResultCode resultCode, Object... objects) {
        throw new BusinessException(resultCode, objects);
    }


    /**
     * 直接往外抛错误枚举,并自定义错误信息
     *
     * @param resultCode 错误枚举
     * @param errorMsg   自定义错误信息
     * @return BusinessException
     */
    public static BusinessException throwOut(IResultCode resultCode, String errorMsg) {
        throw new BusinessException(resultCode.getCode(), errorMsg);
    }

}
