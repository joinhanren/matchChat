package com.join.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author join
 * @Description
 * @date 2022/11/8 15:21
 */
@Data
public class UserInfo {
    private Long U_ID;
    private String U_LoginID;
    private String U_NikeName;
    private String U_PassWord;
    private String U_Signature;
    private String U_Sex;
    private Date U_Birthday;
    private String U_Telephone;
    private String U_Name;
    private String U_Email;
    private String U_Intro;
    private String U_HeadPortrait;
    private String U_BloodType;
    private String U_SchoolTag;
    private String U_IDCard;

}
