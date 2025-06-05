package itk_test.util;

//import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UnknownOperationException.class, WalletNotFoundException.class, NotEnoughMoneyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnknownOperationException(RuntimeException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleArgumentException(MethodArgumentNotValidException e) {
        return Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
    }

//    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public String handleConstraintViolationException(JdbcSQLIntegrityConstraintViolationException e) {
//        return "Не удалось выполнить операцию";
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return "Отправлены некорректные данные";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return "Отправлены некорректные данные";
    }
}
