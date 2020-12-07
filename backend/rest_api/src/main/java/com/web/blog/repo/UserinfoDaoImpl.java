package com.web.blog.repo;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.blog.model.user.User;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserinfoDaoImpl implements UserinfoDao {

	private static String ns = "com.web.blog.model.user.User.";

	@Autowired
	SqlSessionTemplate template;

	@Override
	public User login(String email, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", password);
		return template.selectOne(ns + "login", map);
	}

	@Override
	public int insertUser(User user) {
		return template.insert(ns + "insert", user);
	}

	@Override
	public User selectByIsId(String id) {
		return template.selectOne(ns + "selectbyid", id);
	}

	@Override
	public User selectByIsEmail(String email) {
		return template.selectOne(ns + "selectbyemail", email);
	}

	@Override
	public List<User> search(String by, String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("by", by);
		map.put("keyword", keyword);
		System.out.println(by);
		System.out.println(keyword);
		return template.selectList(ns + "search", map);
	}

	@Override
	public List<User> selectAll() {
		return template.selectList(ns + "selectAll");
	}

	@Override
	public int updateUser(User user) {
		return template.update(ns + "update", user);
	}

	@Override
	public int deleteUser(String id) {
		return template.delete(ns + "delete", id);
	}

}
