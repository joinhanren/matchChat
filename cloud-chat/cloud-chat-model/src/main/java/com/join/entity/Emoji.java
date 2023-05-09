package com.join.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author join
 * @Description
 * @date 2023/3/29 16:11
 */
@Data
public class Emoji implements Serializable {
    private Long id;
    private String emoji;
    private Long createTime;

}
