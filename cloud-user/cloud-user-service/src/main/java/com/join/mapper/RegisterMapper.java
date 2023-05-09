package com.join.mapper;

import com.join.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author join
 * @Description
 * @date 2023/2/24 16:45
 */
@Mapper
public interface RegisterMapper {
    /**
     * 注册
     * @param user
     * @return
     */
    public long register(User user);
}
