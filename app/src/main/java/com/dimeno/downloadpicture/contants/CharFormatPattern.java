package com.dimeno.downloadpicture.contants;

/**
 * CharFormat
 * Created by wangzhen on 2020/5/8.
 */
public class CharFormatPattern {
    public static final String EMAIL = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";//邮箱
    public static final String TEXT = "^([\\u4e00-\\u9fa5])*$";//中文
    public static final String ENGLISH = "^([a-zA-Z])*$";//英文
    public static final String WEBSITE = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?";//网址
    public static final String ID = "^\\d{6}(18|19|20|21)?\\d{2}(0[1-9]|1[12])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X)$";//身份证号码
    public static final String QQ = "^[1-9][0-9]{4,11}$";//QQ号
    public static final String MOBILE_PHONE = "^0?(13|14|15|17|18|19)[0-9]{9}$";//手机号
    public static final String TELEPHONE = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$";//固话
    public static final String DIR = "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$";//文件夹
}
