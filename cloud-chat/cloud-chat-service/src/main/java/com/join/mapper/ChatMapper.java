package com.join.mapper;

import com.join.entity.Emoji;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author join
 * @Description
 * @date 2023/3/29 16:09
 */
@Mapper
public interface ChatMapper {
    /**
     * 查询所有表情
     * @return
     */
    public List<Emoji> queryAllEmoji();

}
