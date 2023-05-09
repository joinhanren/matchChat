package com.join.feign;

import com.join.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.join.vo.UserVo;

/**
 * @author join
 * @Description
 * @date 2023/4/2 23:06
 */
@FeignClient(value = "cloud-user",configuration = {FeignConfig.class})
public interface UserFeign {

    @GetMapping("/user/user")
    public UserVo getUser(@RequestParam("id") String id);
}
