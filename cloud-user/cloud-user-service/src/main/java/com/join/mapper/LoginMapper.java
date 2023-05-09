package com.join.mapper;

import com.join.entity.User;
import com.join.entity.UserInfo;
import com.join.vo.LoginUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author join
 * @Description
 * @date 2023/2/16 15:18
 */
@Mapper
public interface LoginMapper {

//    @Select("select" +
//            " u_id, u_loginid, u_nikename, u_signature, u_sex, u_birthday, u_telephone, u_name, u_email, u_intro, u_headportrait, u_bloodtype, u_schooltag, u_idcard " +
//            "from User_info " +
//            "where U_LoginID=#{loginID} and U_PassWord=#{password}")
//    public UserInfo login(LoginUserVo loginUserVo);



    public User login(String username);
}
