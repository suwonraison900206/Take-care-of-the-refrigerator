package com.web.server.controller;


import com.web.server.dto.Board;
import com.web.server.dto.BoardSimpleDto;
import com.web.server.service.BoardService;
import com.web.server.service.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class BoardRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    BoardService boardService;

    @Autowired
    JwtService jwtService;

    /**
     * 전체 게시글 조회
     *
     * @param res
     * @return
     */
    @ApiOperation(value = "게시글 전체 조회")
    @GetMapping("/boards")
    public ResponseEntity<Map<String, Object>> searchAllBoards (HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            List<BoardSimpleDto> boards = null;
            boards = boardService.searchAll();

            status = HttpStatus.OK;
            // body json add
            resultMap.put("boards", boards);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 게시글 디테일
     *
     * @param res
     * @return
     */
    @ApiOperation(value = "게시글 번호로 게시글 조회", response = String.class)
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<Map<String, Object>> searchBoard (@PathVariable final String boardId,
                                                            HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Board board = null;
            // 서비스
            board = boardService.searchByBoardId(Integer.parseInt(boardId));

            status = HttpStatus.OK;
            // body json add
            resultMap.put("board", board);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            logger.info("게시글 디테일 정보 요청 실패 : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");

        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 게스글 작성
     * 
     * @param req
     * @param board
     * @param res
     * @return
     */
    @ApiOperation(value = "게시글 작성", response = String.class)
    @PostMapping("/boards/create")
    public ResponseEntity<Map<String, Object>> writeBoard (HttpServletRequest req,
                                                           @RequestBody final Board board,
                                                           HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            if (board.getTitle() == null || board.getTitle().length() == 0 || board.getGrade() == null) { // 입력값 이상
                status = HttpStatus.BAD_REQUEST;
                resultMap.put("status", status.value());
                resultMap.put("message", "잘못된 요청값");
            } else { // 서비스 실행
                status = HttpStatus.OK;

                if(boardService.write(email, board)) {
                    resultMap.put("status", status.value());
                    resultMap.put("message", "성공");
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            logger.info("ERROR : {}", e.getMessage());
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
