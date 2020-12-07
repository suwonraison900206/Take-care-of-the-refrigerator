package com.web.blog.repo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.user.User;

public interface UserinfoDao {
	public User login(String email, String password);

	// c: 유저생성
	public int insertUser(User userinfo);

	// r: 유저정보 읽기
	public User selectByIsId(String uid);

	public User selectByIsEmail(String email);

	public List<User> search(String by, String keyword);

	public List<User> selectAll();

	// u: 회원정보 수정
	public int updateUser(User userinfo);

	// d: 회원탈퇴
	public int deleteUser(String uid);
}
