package org.koreait.commons.validators;

import org.koreait.controllers.boards.BoardForm;

public interface Validator<T> {


    void check(T t);
}
