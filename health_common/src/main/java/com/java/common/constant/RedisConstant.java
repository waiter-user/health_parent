package com.java.common.constant;

public class RedisConstant {
    //套餐图片所有图片名称（Redis的set集合的 key）
    public static final String SETMEAL_PIC_RESOURCES="pic_rescources";
    //套餐图片保存在数据库中图片名称（Redis的set集合的 key）
    public static final String SETMEAL_PIC_DB_RESOURCES="pic_db_rescources";
    public static final String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "002";//用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
}
