package com.boot.demo1.mapper;

import com.boot.demo1.Do.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
	
	@Select("SELECT id,user_name,pwd,avalibale,note FROM user WHERE user_name=#{username}")
	User findByUsername(String username);
	
}
