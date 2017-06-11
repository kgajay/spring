package com.spring3.demo.web.service;

import com.spring3.demo.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ajay.kg created on 04/06/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {


    private static final AtomicLong counter = new AtomicLong();

    private static List<User> users;

    @Autowired
    private String appName;

    static{
        users= populateDummyUsers();
    }

    // return app name from autowired configuration
    @Override
    public String returnAppName() {

        return appName;
    }

    @Override
    public User findById(long id) {
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        for(User user : users){
            if(user.getName().equalsIgnoreCase(name)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }

    @Override
    public void deleteUserById(long id) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }

    @Override
    public boolean isUserExist(User user) {
        return findByName(user.getName())!=null;
    }

    private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam",30, 70000d));
        users.add(new User(counter.incrementAndGet(),"Tom",40, 50000d));
        users.add(new User(counter.incrementAndGet(),"Jerome",45, 30000d));
        users.add(new User(counter.incrementAndGet(),"Silvia",50, 40000d));
        return users;
    }
}
