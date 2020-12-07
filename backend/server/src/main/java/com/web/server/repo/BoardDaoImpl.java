package com.web.server.repo;

import com.web.server.dto.*;
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
    public List<Steps> searchStepsInBoard(int boardId) throws SQLException {
        return template.selectList(ns + "selectStepsInBoardByBoardId", boardId);
    }

    @Override
    public int insertBoard(Board board) throws SQLException {
        return template.insert(ns + "insertBoard", board);
    }

    @Override
    public int insertStep(Steps step) throws SQLException {
        return template.insert(ns + "insertStep", step);
    }

    @Override
    public int insertTag(Tags tagName) throws SQLException {
        return template.insert(ns + "insertTag", tagName);
    }

    @Override
    public Tags selectTagExist(Tags tag) throws SQLException {
        return template.selectOne(ns + "selectTagExist", tag);
    }

    @Override
    public int insertStepTags(StepTags stepTags) throws SQLException {
        return template.insert(ns+"insertStepTags",stepTags);
    }

    @Override
    public List<Tags> getTagList(int selectedStepId) throws SQLException {
        return template.selectList(ns+"getTagList",selectedStepId);
    }

}
