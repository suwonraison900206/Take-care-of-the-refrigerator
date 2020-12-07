package com.web.server.repo;

import com.web.server.dto.CommentDto;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface CommentDao {
    public List<CommentDto> selectByBoardId(int boardId) throws SQLException;
}
