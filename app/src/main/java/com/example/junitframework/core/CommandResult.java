package com.example.junitframework.core;

public class CommandResult {

    /**
     * 执行成功
     */
    public static final int CODE_SUCCESS = 0;
    /**
     * 执行异常，在执行命令过程中，有Exception抛出。
     */
    public static final int CODE_ERROR = -1;
    /**
     * 指令执行失败
     */
    public static final int CODE_FAILURE = 1;

    private int code;
    private String message;

    public CommandResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
