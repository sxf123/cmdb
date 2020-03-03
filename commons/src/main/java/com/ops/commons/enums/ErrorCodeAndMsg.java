package com.ops.commons.enums;

public enum ErrorCodeAndMsg {

    Network_error("9999","网络错误，待会重试"),
    Host_already_exist("0004","主机名已经存在"),
    Host_does_not_exist("0005","主机不存在"),
    User_password_error("0001","密码错误"),
    User_does_not_exist("0002","用户不存在"),
    User_already_exist("0003","用户已经存在"),
    Token_is_null("0006","TOKEN为空"),
    Token_is_error("0007","TOKEN错误"),
    Token_is_expired("0008","TOKEN过期"),
    Node_is_already_exist("0010","节点已存在"),
    HostNode_is_already_exist("0011","主机节点已存在")
    ;

    ErrorCodeAndMsg(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String code;
    private String msg;


}
