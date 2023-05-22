package org.koreait.models.board.config;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

// 접속권한없을때 exception
public class BoardNotAllowAccessException extends CommonException {


    public BoardNotAllowAccessException(){
        super(bundleValidation.getString("Validation.board.notAllowAccess"), HttpStatus.UNAUTHORIZED);
    }

}
