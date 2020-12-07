package com.web.blog.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.web.blog.model.user.User;
import com.web.blog.repo.UserinfoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserinfoServiceImpl implements UserinfoService {

	@Autowired
	UserinfoDao uDao;

	@Override
	public User login(String email, String password) {
		return uDao.login(email, password);
	}

	@Override
	public int insertUser(User user) {
		return uDao.insertUser(user);
	}

	@Override
	public User selectByIsId(String id) {
		return uDao.selectByIsId(id);
	}

	@Override
	public boolean checkAccount(String id, String email) {
		boolean result = false;
		if (uDao.selectByIsId(id) == null && uDao.selectByIsEmail(email) == null)
			result = true;
		return result;
	}

	@Override
	public Collection<User> search(String by, String keyword) {
		return uDao.search(by, keyword);
	}

	@Override
	public Collection<User> list() {
		return uDao.selectAll();
	}

	@Override
	public int updateUser(User user) {
		return uDao.updateUser(user);
	}

	@Override
	public int deleteUser(String id) {
		return uDao.deleteUser(id);
	}

}
