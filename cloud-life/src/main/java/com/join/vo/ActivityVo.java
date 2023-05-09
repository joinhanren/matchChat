package com.join.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author join
 * @Description
 * @date 2023/4/3 20:51
 */
@Data
public class ActivityVo implements Serializable {
    private Long id;

    private String content;

    private String subsidiaryType;

    private Long createTime;

    private Long userid;

    private String subsidiary;

    private String avatar;

    private String nickName;
}
