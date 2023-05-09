package com.join.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author join
 * @Description
 * @date 2023/4/2 16:34
 */
@Data
public class Activity implements Serializable {
    private Long id;

    private String content;

    private String subsidiaryType;

    private Long createTime;

    private Long userid;

    private String status;

    private String subsidiary;

    private String avatar;

    private String nickName;
}
