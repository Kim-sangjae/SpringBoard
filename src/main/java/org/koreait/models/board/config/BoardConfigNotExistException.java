package org.koreait.models.board.config;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

// 게시판이 존재하지 않을때 exception
public class BoardConfigNotExistException extends CommonException {


    public BoardConfigNotExistException(){
        super(bundleValidation.getString("Validation.board.notExists"), HttpStatus.BAD_REQUEST);
    }

}
