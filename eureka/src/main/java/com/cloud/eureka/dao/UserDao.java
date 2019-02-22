package com.cloud.eureka.dao;


import com.cloud.eureka.entiry.User;

import java.util.List;

public interface UserDao {

	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> getAllUsers();
}