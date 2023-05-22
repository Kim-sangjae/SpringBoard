package org.koreait.models.board.config;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberUtil;
import org.koreait.commons.constants.Role;
import org.koreait.entities.Board;
import org.koreait.repositories.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {


    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

    public Board get(String bId , String location){ // 프론트 , 접근권한 체크
        return get(bId , false, location);
    }



    /**
     * 게시판 설정 조회
     *
     * @param bId
     * @param isAdmin : true -> 권한체크 x
     *                : false -> 권한체크 , location 으로 목록, 보기 , 글쓰기 , 답글 , 댓글
     *
     * @param location : 기능 위치(list, view , write , reply , comment)
     *
     * @return
     */
    public Board get(String bId, boolean isAdmin, String location){

        Board board = boardRepository.findById(bId).orElseThrow(BoardConfigNotExistException::new);

        if(!isAdmin){ // admin 이 아닐때 권한체크
            accessCheck(board, location);
        }


        return board;
    }



    public Board get(String bId,boolean isAdmin){
        return get(bId,isAdmin,null);
    }





    /**
     * 접근권한 체크메서드
     * @param board
     */
    private void accessCheck(Board board, String location){
        Role role = Role.ALL;
        if(location.equals("list")){ // 목록에 접근권한
            role = board.getListAccessRole();

        } else if(location.equals("view")){ // 게시글 보기 접근권한
            role = board.getViewAccessRole();

        } else if(location.equals("write")){ // 글쓰기 권한
            role = board.getWriteAccessRole();


        } else if(location.equals("reply")){ // 답글권한
            role = board.getReplyAccessRole();


        } else if(location.equals("comment")){ // 댓글권한
            role = board.getCommentAccessRole();

        }

        if( (role == Role.USER && !memberUtil.isLogin()) || (role == Role.ADMIN && !memberUtil.isAdmin()) ){

            throw new BoardNotAllowAccessException();

        }


    }






}



