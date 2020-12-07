package com.web.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepOfBoardDto {
    private int stepId;         // 단계 고유 번호
    private int boardId;        // 게시글에 저장되는 단계
    private int stepNumber;     // 게시글 내 단계 번호
    private String image;       // 이미지 경로
    private String description; // 글 내용
}
