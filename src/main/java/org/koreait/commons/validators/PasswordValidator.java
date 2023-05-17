package org.koreait.commons.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PasswordValidator {


    /**
     * 비밀번호 복잡성 체크 - 알파벳 체크
     *
     * @param password
     * @param caseIncentive
     *          false : 소문자 + 대문자가 반드시 포함되는 패턴
     *          true : 대소문자 상관없이 포함되는 패턴
     * @return
     * */
    default boolean alphaCheck(String password, boolean caseIncentive) {
        if(caseIncentive){// 대소문자 구분없이 체크
            return password.matches("[a-zA-Z]+"); //[a-zA-Z]+ 플러스는 한개이상 무조건들어가야한다는 것
        }


        // 대문자 , 소문자 각각 체크
        return password.matches("[a-z]+") && password.matches("[A-Z]+");

    }




    /**
     * 숫자가 포함된 패턴인지 체크
     * @param password
     * @return
     */
    default boolean numberCheck(String password){

        return password.matches("\\d+"); //[0-9]+ 와 같은 의미
    }





    /**
     * 특수문자가 포함된 패턴인지 체크
     * @param password
     * @return
     */
//    default boolean specialCharCheck(String password){
//
//        //return password.matches("[`~!#$%\\^&\\*()-_+=]+");
//
//    return true;
//    }


    default boolean specialCharsCheck(String password) {
        Pattern pattern = Pattern.compile("[`~!#$%\\^&\\*()-_+=]+");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }


}