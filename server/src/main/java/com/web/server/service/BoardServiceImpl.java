package com.web.server.service;

import com.web.server.dto.Board;
import com.web.server.dto.BoardSimpleDto;
import com.web.server.dto.StepOfBoardDto;
import com.web.server.dto.User;
import com.web.server.repo.BoardDao;
import com.web.server.repo.CommentDao;
import com.web.server.repo.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardDao boardDao;

    @Autowired
    UserinfoDao userinfoDao;

    @Autowired
    CommentDao commentDao;

    @Override
    public List<BoardSimpleDto> searchAll() throws SQLException {
        return boardDao.searchAll();
    }

    @Override
    public Board searchByBoardId(int boardId) throws SQLException {
        Board board = null;
        board = boardDao.searchByBoardId(boardId);              // 1. 게시글 조회
        board.setSteps(boardDao.searchStepsInBoard(boardId));   // 2. 단계 조회
        board.setComments(commentDao.selectByBoardId(boardId)); // 3. 댓글 조회
        return board;
    }

    @Override
    @Transactional
    public boolean write(String email, Board board) throws SQLException{
        User user = userinfoDao.selectByIsEmail(email);
        board.setUserId(user.getUserId());
        int resultBoardId = boardDao.insertBoard(board);              // 1. 게시글 로우 생성
        if(board.getSteps() != null && board.getSteps().size() > 0) { // 2. 게시글에 저장되는 단계 저장
            for(StepOfBoardDto step : board.getSteps()) {
                step.setBoardId(resultBoardId);
                boardDao.insertStep(step);
            }
        }
        return resultBoardId != 0 ? true : false;
    }
}
