package com.web.server.controller;

import com.web.server.dto.User;
import com.web.server.service.JwtService;
import com.web.server.service.UserinfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private JwtService jwtService;

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

    /**
     * 로그인 성공 : 202
     * 로그인 실패 : 401
     *
     * @param user
     * @param res
     */
    @ApiOperation(value = "로그인", response = String.class)
    @PostMapping("/users/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody final User user, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            logger.debug("User 로그인");
            status = HttpStatus.ACCEPTED; // status code : 202

            // 로그인 정보 확인
            User loginUser = userService.login(user.getEmail(), user.getPassword());
            // 로그인 성공했다면 토큰 생성
            String token = jwtService.create(loginUser);
            // 토큰 정보는 request header & body 에 보낸다.
            // header set
            res.setHeader("jwt-auth-token", token); // header에 "jwt-auth-token" 추가
            // body json add
            resultMap.put("accessToken", token); // body에 "auth_token" 추가
            resultMap.put("status", status.value());
            resultMap.put("success", true);
            resultMap.put("message", "로그인 성공");

            logger.info("로그인 성공: " + loginUser.toString());
            // return new ResponseEntity<User>(userService.login(email, password), HttpStatus.NO_CONTENT);
            // session.setAttribute("user", email);
            // return new ResponseEntity<User>(userService.login(email, password), HttpStatus.OK);

        } catch (RuntimeException e) {
            logger.debug("로그인 실패", e.getMessage());
            status = HttpStatus.UNAUTHORIZED; // status code : 401
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("success", false);
            resultMap.put("message", "로그인 실패");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
        // return new ResponseEntity<User>(userService.login(user.getId(),
        // user.getPw()), HttpStatus.OK);
    }

    /**
     * 회원가입 성공 : 201
     * 이메일 또는 닉네임 중복 : 409
     * 회원가입 실패 : 403
     *
     * @param user
     */
    @ApiOperation(value = "회원가입 한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PostMapping("/users/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody final User user) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        // sub1 필수 조건. 회원가입시 이메일 인증

        try {
            logger.debug("User 회원가입");
            String email = user.getEmail();
            String nickname = user.getNickname();

            // 이메일, 닉네임 중복처리 필수
            if (userService.checkAccount(email, nickname)) {
                status = HttpStatus.CREATED; // status code : 201
                // 회원가입 service
                userService.insertUser(user);
                System.out.println("test");
                // body json add
                resultMap.put("status", status.value());
                resultMap.put("success", true);
                resultMap.put("message", "회원가입 성공");

                // 이메일 확인 메일 전송
                sendMail(email);
            } else {
                status = HttpStatus.CONFLICT; // status code : 409
                // body json add
                resultMap.put("status", status.value());
                resultMap.put("success", false);
                resultMap.put("message", "이메일 또는 닉네임 중복");
            }

        } catch (RuntimeException | SQLException e) {
            logger.debug("회원가입 실패", e.getMessage());
            System.out.println("error:"+e.getMessage());
            status = HttpStatus.FORBIDDEN; // status code : 403
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("success", false);
            resultMap.put("message", "회원가입 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 개인 정보 수정 요청
     *
     * @param req
     * @param res
     * @return
     */
    @GetMapping("/users/info")
    public ResponseEntity<Map<String, Object>> getUserInfo(final HttpServletRequest req,
                                                           HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            // 서비스 실행
//            userService.searchByEmail(email);


        } catch (RuntimeException e) {

        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }



    @ApiOperation(value = "회원 마이페이지 조회", response = User.class)
    @GetMapping("/users/mypage")
    public ResponseEntity<Map<String, Object>> getUserProfile(final HttpServletRequest req,
                                               HttpServletResponse res) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
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
            session.setAttribute("user", user.getUserId());
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }


}
