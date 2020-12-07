package com.web.server.repo;

import com.web.server.dto.Board;
import com.web.server.dto.BoardSimpleDto;
import com.web.server.dto.StepOfBoardDto;

import java.sql.SQLException;
import java.util.List;

public interface BoardDao {
    // 게시글 전체 조회
    public List<BoardSimpleDto> searchAll() throws SQLException;

    // 게시글 번호로 조회
    public Board searchByBoardId(int boardId) throws SQLException;

    // 게시글 조리 단계 조회
    public List<StepOfBoardDto> searchStepsInBoard(int boardId) throws SQLException;

    // 게시글 등록
//    public int insertByEmail(String email, Board board) throws SQLException;
    public int insertBoard(Board board) throws SQLException;

    // 단계 등록
    public int insertStep(StepOfBoardDto step) throws SQLException;
}

