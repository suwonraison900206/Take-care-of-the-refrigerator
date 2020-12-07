package com.web.server.repo;

import com.web.server.dto.*;

import java.sql.SQLException;
import java.util.List;

public interface BoardDao {
    // 게시글 전체 조회
    public List<BoardSimpleDto> searchAll() throws SQLException;

    // 게시글 번호로 조회
    public Board searchByBoardId(int boardId) throws SQLException;

    // 게시글 조리 단계 조회
    public List<Steps> searchStepsInBoard(int boardId) throws SQLException;

    // 게시글 등록
//    public int insertByEmail(String email, Board board) throws SQLException;
    public int insertBoard(Board board) throws SQLException;

    // 단계 등록
    public int insertStep(Steps step) throws SQLException;

    // 게시글 작성 시 태그 등록
    int insertTag(Tags tag) throws SQLException;

    // 태그이름이 존재하는 지 확인
    Tags selectTagExist(Tags tag) throws SQLException;

    int insertStepTags(StepTags stepTags) throws SQLException;

    List<Tags> getTagList(int selectedStepId) throws SQLException;
}

