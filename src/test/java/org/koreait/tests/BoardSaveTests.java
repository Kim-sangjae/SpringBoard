package org.koreait.tests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.boards.BoardForm;
import org.koreait.controllers.members.JoinForm;
import org.koreait.entities.Board;
import org.koreait.entities.Member;
import org.koreait.models.board.BoardDataSaveService;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.models.board.config.BoardConfigSaveService;
import org.koreait.models.member.MemberSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
@DisplayName("게시글 등록, 수정테스트")
public class BoardSaveTests {

    @Autowired
    private BoardDataSaveService saveService;
    @Autowired
    private MemberSaveService memberSaveService; // 회원가입
    @Autowired
    private BoardConfigSaveService configSaveService;
    @Autowired
    private BoardConfigInfoService configInfoService;

    private Board board;

    private JoinForm joinForm;

    @BeforeEach
    @Transactional
    void init(){
        // 게시판 설정 추가
        org.koreait.controllers.admins.BoardForm boardForm = new org.koreait.controllers.admins.BoardForm();
        boardForm.setBId("freetalk");
        boardForm.setBName("자유게시판");
        configSaveService.save(boardForm);

        board = configInfoService.get(boardForm.getBId(), true);

        // 회원가입 추가
         joinForm = JoinForm.builder()
                .userId("user01")
                .userPw("_aA123456")
                .userPwRe("_aA123456")
                .email("user01@test.org")
                .userNm("사용자1")
                .mobile("010-1234-1234")
                .agrees(new boolean[]{true})
                .build();

        memberSaveService.save(joinForm);

    }



    private BoardForm getGuestBoardForm(){

        return BoardForm.builder()
                .bId(board.getBId())
                .guestPw("12345678")
                .poster("비회원")
                .subject("제목")
                .content("내용")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();
    }


  // @WithMockUser(username = "user01", password = "_aA123456")
    private BoardForm getMemberBoardForm(){
        return BoardForm.builder()
                .bId(board.getBId())
                .poster(joinForm.getUserNm())
                .subject("제목1")
                .content("내용1")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();

    }



    @Test
    @DisplayName("게시글 등록(비회원)성공시 예외 없음")
    void registerGuestSuccessTest(){
        assertDoesNotThrow(()->{
            saveService.save(getGuestBoardForm());
        });
    }


}
