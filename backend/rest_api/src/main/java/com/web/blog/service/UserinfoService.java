package com.web.blog.service;

import java.sql.SQLException;
import java.util.Collection;

import com.web.blog.model.user.User;

public interface UserinfoService {

	public User login(String email, String password);

	// c
	public int insertUser(User user);

	// r
	public User selectByIsId(String id);

	public Collection<User> search(String by, String keyword);

	public Collection<User> list();

	public boolean checkAccount(String id, String email);

	// u
	public int updateUser(User user);

	// d
	public int deleteUser(String id);
}
