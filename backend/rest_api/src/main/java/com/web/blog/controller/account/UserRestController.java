package com.web.blog.controller.account;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.web.blog.model.user.User;
import com.web.blog.service.UserinfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/account")
public class UserRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserinfoService userService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void sendMail(String email) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// simpleMailMessage.setFrom("example@naver.com"); // 다른 메일은 적어줘야한다.
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("이메일 인증");
		simpleMailMessage.setText("가입을 축하드립니다.");
		javaMailSender.send(simpleMailMessage);
	}

	@ApiOperation(value = "로그인", response = String.class)
	@GetMapping("/login/{email}/{password}")
	public ResponseEntity<User> login(@PathVariable final String email, @PathVariable final String password,
			final HttpSession session) throws Exception {
		logger.debug("User 로그인");
		if (userService.login(email, password) == null) {
			return new ResponseEntity<User>(userService.login(email, password), HttpStatus.NO_CONTENT);
		} else {
			session.setAttribute("user", email);
			return new ResponseEntity<User>(userService.login(email, password), HttpStatus.OK);

		}
		// return new ResponseEntity<User>(userService.login(user.getId(),
		// user.getPw()), HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보를 조회", response = User.class)
	@GetMapping("{uid}")
	public ResponseEntity<User> getUserInfo(@PathVariable final String uid) throws Exception {
		logger.debug(uid + "의 정보 찾기");
		System.out.println(uid + "의 정보 찾기");
		return new ResponseEntity<User>(userService.selectByIsId(uid), HttpStatus.OK);
	}

	@ApiOperation(value = "해당 아이디의 유저를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{uid}")
	public ResponseEntity<String> deleteQnA(@PathVariable final String uid, final HttpSession session) {
		logger.debug("deleteUser - 호출");
		if (userService.deleteUser(uid) == 1) {
			session.invalidate();
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "유저의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{uid}")
	public ResponseEntity<String> updateQnA(@RequestBody final User user, final HttpSession session) {
		logger.debug("updateQnA - 호출");
		logger.debug("" + user);
		// user.setUid(uid);
		if (userService.updateUser(user) == 1) {
			session.setAttribute("user", user.getUid());
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원가입 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> signUp(@RequestBody final User user) {
		logger.debug("signUp - 호출");
		// 이메일, 닉네임 중복처리 필수
		// 회원가입단을 생성해 보세요.
		String uid = user.getUid();
		String email = user.getEmail();

		if (userService.checkAccount(uid, email)) { // 아이디, 이메일 중복 확인
			if (userService.insertUser(user) == 1) {
				sendMail(email);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		}

		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
