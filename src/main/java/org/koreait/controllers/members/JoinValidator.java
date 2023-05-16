package org.koreait.controllers.members;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.validators.MobileValidator;
import org.koreait.commons.validators.PasswordValidator;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator, PasswordValidator { // 스프링프레임워크에있는 벨리데이터로 임포트해야함

    private final MemberRepository memberRepository;



    @Override
    public boolean supports(Class<?> clazz) { // JoinForm 이라는 클래스만 검증하겠다는 것
        return JoinForm.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 아이디 중복 여부
         * 2. 비밀번호 복잡성 체크(알파벳,대문자,소문자,숫자,특수문자)
         * 3. 비밀번호와 비밀번호확인이 일치하는지
         * 4. 휴대전화번호(선택사항) - 입력된 경우에는 형식 체크
         * 5. 휴대전화번호가 입력된 경우에는 숫자만 추출해서 다시 커맨드객체에 저장
         * 6. 필수 약관동의 만 체크
         */

        //target 가져오기
        JoinForm joinForm = (JoinForm) target;
        String userId = joinForm.getUserId();
        String userPw = joinForm.getUserPw();
        String userPwRe = joinForm.getUserPwRe();
        String mobile = joinForm.getMobile();
        boolean[] agrees = joinForm.getAgrees(); // 필수 약관

        // 1. 아이디 중복 여부
        if(userId != null && !userId.isBlank() && memberRepository.exists(userId)){
            errors.rejectValue("userId","Validation.duplicate.userId");
        }


/**
        // 2. 비밀번호 복잡성 체크(알파벳,대문자,소문자,숫자,특수문자)
        if(userPw != null && !userPw.isBlank()
            && (!alphaCheck(userPw,false)
                || !numberCheck(userPw)
                || !specialCharCheck(userPw) ) ) {

            errors.rejectValue("userPw","Validation.complexity.password");
        }
*/



        // 3. 비밀번호와 비밀번호 확인 일치
        if(userPw != null && !userPw.isBlank()
                && userPwRe != null && !userPwRe.isBlank()
                && !userPw.equals(userPwRe)){

            errors.rejectValue("userPwRe","Validation.incorrect.userPwRe");

        }


        // 4. 휴대전화번호(선택) - 입력된 경우 형식 체크
        // 5. 휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
        if (mobile != null && !mobile.isBlank()) {
            if (!mobileNumCheck(mobile)) {
                errors.rejectValue("mobile", "Validation.mobile");
            }

            mobile = mobile.replaceAll("\\D", "");
            joinForm.setMobile(mobile);
        }

        // 6. 필수 약관 동의 체크
        if (agrees != null && agrees.length > 0) {
            for (boolean agree : agrees) {
                if (!agree) {
                    errors.reject("Validation.joinForm.agree");
                    break;
                }
            }
        }






    } // validate()












}
