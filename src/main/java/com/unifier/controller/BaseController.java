package com.unifier.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.unifier.dto.ExceptionDTO;
import com.unifier.exception.GetAllComparisonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController {

    static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(GetAllComparisonException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionDTO getAllReportsExceptionHandler(GetAllComparisonException e) {
        String msg = "Error while comparing 'getAll' reports";
        logger.error(msg, e);
        return new ExceptionDTO(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionDTO illegalArgumentExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return new ExceptionDTO("The request contained an invalid format or contained invalid values");
    }
}
