package com.web.server.repo;

import com.web.server.dto.Board;
import com.web.server.dto.BoardSimpleDto;
import com.web.server.dto.StepOfBoardDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;


@Repository
public class BoardDaoImpl implements BoardDao{
    private String ns = "com.web.server.repo.BoardDao.";

    static Logger logger = LoggerFactory.getLogger(BoardDaoImpl.class);

    @Autowired
    SqlSessionTemplate template;

    @Override
    public List<BoardSimpleDto> searchAll() throws SQLException {
        return template.selectList(ns + "selectAll");
    }

    @Override
    public Board searchByBoardId(int boardId) throws SQLException {
        return template.selectOne(ns + "selectOneBoardByBoardId", boardId);
    }

    @Override
    public List<StepOfBoardDto> searchStepsInBoard(int boardId) throws SQLException {
        return template.selectList(ns + "selectStepsInBoardByBoardId", boardId);
    }

    @Override
    public int insertBoard(Board board) throws SQLException {
        return template.insert(ns + "insertBoard", board);
    }

    @Override
    public int insertStep(StepOfBoardDto step) throws SQLException {
        return template.insert(ns + "insertStep", step);
    }

}
