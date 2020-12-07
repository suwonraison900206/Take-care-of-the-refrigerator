package com.web.server.service;

import com.web.server.dto.*;
import com.web.server.repo.BoardDao;
import com.web.server.repo.CommentDao;
import com.web.server.repo.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

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
        List<String> tempList = new ArrayList<>();
        for(int i=0;i<board.getSteps().size();i++){
            System.out.println(board.getSteps().get(i).getStepId());
            int selectedStepId = board.getSteps().get(i).getStepId();
            List<Tags> tagList = boardDao.getTagList(selectedStepId);
            StringBuilder sb = new StringBuilder();
            for(Tags tag : tagList){
                System.out.println(tag.toString());
                sb.append(tag.getTagName()+",");
            }
            tempList.add(sb.substring(0,sb.length()-1));
        }
        board.setTags(tempList);

        board.setComments(commentDao.selectByBoardId(boardId)); // 3. 댓글 조회
        return board;
    }

    @Override
    @Transactional
    public boolean write(String email, Board board) throws SQLException{
        User user = userinfoDao.selectByIsEmail(email);
        board.setUserId(user.getUserId());
        boardDao.insertBoard(board);              // 1. 게시글 로우 생성
        int resultBoardId = board.getBoardId();

        List<String> listTag = board.getTags();
        if(board.getSteps() != null && board.getSteps().size() > 0) { // 2. 게시글에 저장되는 단계 저장
            for(int i=0;i<board.getSteps().size();i++) {
                board.getSteps().get(i).setBoardId(resultBoardId);
                boardDao.insertStep(board.getSteps().get(i));
                System.out.println(board.getSteps().get(i).getStepId());
                String[] arrTags = listTag.get(i).split(",");
                for(String tag : arrTags){
                    System.out.println(tag);
                    Tags temp = new Tags(-1,"");
                    temp.setTagName(tag);
                    Tags selectedTag = new Tags(-1,"");
                    selectedTag =boardDao.selectTagExist(temp);
                    if(selectedTag==null){
                        System.out.println("null!!");
                        boardDao.insertTag(temp);
                        System.out.println(temp.getTagId());
                        StepTags stepTags = new StepTags(-1,-1);
                        stepTags.setTagId(temp.getTagId());
                        stepTags.setStepId(board.getSteps().get(i).getStepId());
                        boardDao.insertStepTags(stepTags);
                    }else{
                    System.out.println("id : "+ selectedTag.getTagId());
                    System.out.println("name : "+ selectedTag.getTagName());
                        StepTags stepTags = new StepTags(-1,-1);
                        stepTags.setTagId(selectedTag.getTagId());
                        stepTags.setStepId(board.getSteps().get(i).getStepId());
                        boardDao.insertStepTags(stepTags);
                    }
                }

            }
        }
        return resultBoardId != 0 ? true : false;
    }

    @Override
    public void writeComment(CommentDto comment) throws  SQLException{
        commentDao.createCommentByBoardId(comment);
    }

    @Override
    public  void updateComment(CommentDto comment) throws SQLException{
        commentDao.updateCommentByBoardId(comment);
    }

    @Override
    public  int deleteComment(Integer commentId) throws SQLException{
       return commentDao.deleteCommentByBoardId(commentId);
    }
}
