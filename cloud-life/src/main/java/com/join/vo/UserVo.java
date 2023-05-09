package com.join.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author join
 * @Description 服务端向页面发送的用户信息
 * @date 2023/2/27 21:04
 */
@Data
public class UserVo implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 账号状态（0正常 1停用）
     */
    private String status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 好友列表
     */
    private List<UserVo> friends=null;


}
