package com.bridgelabz.onlinebookstore.bookService.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.onlinebookstore.bookService.utility.ResponseDTO;


@ControllerAdvice
public class BookExceptionHandler {
    private static final String message = "Exception while processing REST Request";
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
                                       MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMesg = errorList.stream()
                                .map(objErr -> objErr.getDefaultMessage())
                                .collect(Collectors.toList());
        ResponseDTO responseDTO =
                new ResponseDTO(message, errMesg);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
    }   
    
    @ExceptionHandler(BookException.class)
    public ResponseEntity<ResponseDTO> handleBookException(
    									BookException exception ) {
        ResponseDTO responseDTO = new ResponseDTO(exception.getMessage());
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST); 
    }
    
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ResponseDTO> handleCartException(
    									CartException exception ) {
        ResponseDTO responseDTO = new ResponseDTO(exception.getMessage());
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST); 
    }
}
