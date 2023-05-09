package com.join.service.impl;

import com.join.entity.User;
import com.join.mapper.RegisterMapper;
import com.join.service.RegisterService;
import com.join.util.FormatCheck;
import com.join.util.NicknameUtil;
import com.join.vo.RegisterVo;
import com.join.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author join
 * @Description
 * @date 2023/2/24 16:44
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注册
     *
     * @param registerVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result register(RegisterVo registerVo) {
        String email = registerVo.getEmail();
        String username = registerVo.getUsername();
        String verification = registerVo.getVerification();
        if (FormatCheck.checkEmail(email)
                && FormatCheck.checkUsername(username)
                && FormatCheck.checkVerification(verification)) {
            String code = redisTemplate.opsForValue().get(email);
            redisTemplate.delete(email);
            if (code.equals(verification)) {
                User user = new User();
                user.setUserName(username);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode(registerVo.getPassword()));
                user.setNickName(NicknameUtil.getRandomNickname());
                user.setStatus("0");
                user.setSex("2");
                user.setDelFlag(0);
                user.setUserType("1");
                Long id = registerMapper.register(user);
                if (!Objects.isNull(id) && !id.equals(0)) {
                    return new Result(200, "注册成功！", true);
                }
            }
        }
        return new Result(400, "注册失败！", false);
    }
}
