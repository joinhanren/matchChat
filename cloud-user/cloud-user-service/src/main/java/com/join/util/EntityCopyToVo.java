package com.join.util;

import com.join.entity.User;
import com.join.vo.UserVo;

/**
 * @author join
 * @Description
 * @date 2023/3/1 14:36
 */
public class EntityCopyToVo {

    public static UserVo userToUserVo(User user){
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUserName(user.getUserName());
        userVo.setNickName(user.getNickName());
        userVo.setAvatar(user.getAvatar());
        userVo.setSex(user.getSex());
        return userVo;
    }
}
