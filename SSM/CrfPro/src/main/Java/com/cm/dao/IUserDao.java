package com.cm.dao;

import com.cm.entity.User;

public interface IUserDao {

    User selectUser(long id);

}