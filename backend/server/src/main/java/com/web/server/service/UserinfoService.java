package com.web.server.service;

import com.web.server.dto.User;

import java.sql.SQLException;
import java.util.Collection;


public interface UserinfoService {

    public User login(String email, String password);

    // c
    public int insertUser(User user);

    // r
    public User selectByIsId(String id);

    public User searchByEmail(String email);

    public Collection<User> search(String by, String keyword);

    public boolean checkAccount(String email, String nickname) throws SQLException;

    public boolean compareAccount(String email, String nickname) throws SQLException;

    // u
    public int updateUser(User user);

    // d
    public int deleteUser(String id);
}
