package com.join.service;

import com.join.vo.RegisterVo;
import com.join.vo.Result;


/**
 * @author join
 * @Description
 * @date 2023/2/24 16:43
 */
public interface RegisterService {
    /**
     * 注册
     * @param registerVo
     * @return
     */
    public Result register( RegisterVo registerVo);
}
