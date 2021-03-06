package nineseven.review.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ParentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String parentException(ParentException e) {
        log.info("{}", e.getMessage());
        return "error!";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDto> basicError(MethodArgumentNotValidException e) {
        List<ErrorDto> errors = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(result -> {
            errors.add(new ErrorDto(result.getField(), result.getDefaultMessage(), String.valueOf(result.getRejectedValue())));
        });

        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorDto> notReadableError(HttpMessageNotReadableException e) {
        ErrorDto errorDto = new ErrorDto();
        log.info(e.getLocalizedMessage());
        log.info(e.getMessage());
        List<ErrorDto> list = new ArrayList<>();
        list.add(errorDto);
        return list;
    }
}
