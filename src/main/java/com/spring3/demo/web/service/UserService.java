package com.spring3.demo.web.service;

import com.spring3.demo.modal.User;

import java.util.List;

/**
 * @author ajay.kg created on 04/06/17.
 */
public interface UserService {

    String returnAppName();

    User findById(long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    List<User> findAllUsers();

    void deleteAllUsers();

    public boolean isUserExist(User user);
}
