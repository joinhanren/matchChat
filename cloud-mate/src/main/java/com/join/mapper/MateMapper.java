package com.join.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author join
 * @Description
 * @date 2023/4/13 22:05
 */
@Mapper
public interface MateMapper {
    /**
     * 添加好友
     * @param myID
     * @param friendID
     * @return
     */
    @Insert("insert into sys_friends (user_id, friend_id) values (#{myID},#{friendID})")
    public int addFriend(@Param("myID") Long myID,@Param("friendID") Long friendID);
}
