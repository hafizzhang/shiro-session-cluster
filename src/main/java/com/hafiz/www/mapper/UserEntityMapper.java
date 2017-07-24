package com.hafiz.www.mapper;

import com.hafiz.www.po.UserEntity;

import java.util.List;

/**
 * Desc:用户表实体mapper接口类
 * Created by hafiz.zhang on 2016/8/27.
 */
public interface UserEntityMapper {

    /**
     * 查找所有的用户信息
     *
     * @return
     */
    List<UserEntity> getAllUsers();

    /**
     * 通过用户名获取用户信息
     *
     * @param userName 用户名
     *
     * @return
     */
    List<UserEntity> getByUserName(String userName);
}
