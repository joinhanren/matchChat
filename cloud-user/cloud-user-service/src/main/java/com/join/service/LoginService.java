package com.join.service;

import com.join.vo.LoginUserVo;
import com.join.vo.Result;

/**
 * @author join
 * @Description
 * @date 2023/2/16 17:20
 */
public interface LoginService {
    /**
     * 登录
     * @return 返回token
     */
    public Result login(LoginUserVo loginUserVo);

}
