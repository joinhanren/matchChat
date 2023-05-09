package com.join.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/2/28 21:54
 */
@Mapper
public interface MenuMapper {
    /**
     * 根据用户id 查询权限信息
     * @param id
     * @return
     */
    public List<String> selectPermsByUserId(Long id);
}
