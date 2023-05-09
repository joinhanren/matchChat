package com.join.mapper;

import com.join.entity.User;
import com.join.vo.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/2/22 16:55
 */
@Mapper
public interface UserMapper {
    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    public String checkUserName(String username);

    /**
     * 查询邮箱是否存在
     * @param email
     * @return
     */
    public String checkEmail(String email);

    /**
     * 根据id查询好友列表
     * @param id
     * @return
     */
    public List<User> selectFriendsById(Long id);

    /**
     * 同通Id获取用户信息
     * @param id
     * @return
     */
    public User queryUserById(Long id);

}
