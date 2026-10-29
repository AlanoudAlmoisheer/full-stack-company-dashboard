package org.example.orgflow.exceptions.handler;

import jakarta.validation.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.example.orgflow.common.utils.ObjectChecker;
import org.example.orgflow.exceptions.model.ApiResponse;
import org.example.orgflow.exceptions.model.ErrorPayload;
import org.example.orgflow.exceptions.model.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    // Used for Arabic and English messages
    // Reads from messages_ar.properties and messages_en.properties
    private MessageSource messageSource;


    // ====================================================================
    //  1. REQUEST VALIDATION ERRORS (@Valid , DTO validation)
    // ====================================================================


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
     //This method: 1-Gets validation errors 2-Converts them to custom format 3-Returns structured response
    public ResponseEntity<ApiResponse> handleBindException(BindException ex) {

        // Get default validation message
        String defaultMessage = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        // Main API response
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);

        ErrorPayload payload = new ErrorPayload();
        // List of validation errors
        List<FieldError> errors = new ArrayList<>();
        // Loop through validation errors
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            //build error object
            errors.add(org.example.orgflow.exceptions.model.FieldError.builder()
                   //the following tells which field has error
                    .filedName(field)

                          // Arabic message
                         .nameAr(
                                 ObjectChecker.isNotEmptyOrNull(
                                         messageSource.getMessage(errorMessage, null, new Locale("ar")))
                                         ? messageSource.getMessage(errorMessage, null, new Locale("ar"))
                                         : errorMessage
                         )
                    //English message
                    .nameEn(
                            ObjectChecker.isNotEmptyOrNull(
                                    messageSource.getMessage(errorMessage, null, new Locale("en")))
                                    ? messageSource.getMessage(errorMessage, null, new Locale("en"))
                                    : errorMessage
                    )

                    .build());
        });
        //Put Errors Inside Payload
        payload.setFieldErrors(errors);
        //Put Payload in Response
        apiResponse.setPayload(payload);

        //Return Response
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // =====================================================
    // 2. DATABASE CONSTRAINT VALIDATION
    // =====================================================


    @ExceptionHandler(ConstraintViolationException.class)
    //This tells Spring: "Return HTTP status 400 when this exception happens"
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleDuplicatedException(ConstraintViolationException ex) {
        ex.printStackTrace();
        String msgE = ex.getSQLException().getMessage();
        List<FieldError> errors = new ArrayList<>();
        String messageAr = "";
        String messageEn = "";

        String erm;
        if (msgE.contains("child record found")) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setSuccess(false);
            apiResponse.setCode(400);
            erm = "ERROR_CHILDREN_CONSTRAINS_EXCEPTION";
            ErrorPayload payload = new ErrorPayload();
            messageAr = ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(
                    erm, null, new Locale("ar"))) ? messageSource.getMessage(erm, null, new Locale("ar")) : erm;
            messageEn = ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(
                    erm, null, new Locale("en"))) ? messageSource.getMessage(erm, null, new Locale("en")) : erm;
            errors.add(org.example.orgflow.exceptions.model.FieldError.builder()
                    .filedName(null)
                    .nameAr(messageAr)
                    .nameEn(messageEn)
                    .build());
        } else if (msgE.contains("unique")) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setSuccess(false);
            apiResponse.setCode(400);
            erm = "ERROR_UNIQUE_CONSTRAINS_EXCEPTION";
            ErrorPayload payload = new ErrorPayload();
            messageAr = ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(
                    erm, null, new Locale("ar"))) ? messageSource.getMessage(erm, null, new Locale("ar")) : erm;
            messageEn = ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(
                    erm, null, new Locale("en"))) ? messageSource.getMessage(erm, null, new Locale("en")) : erm;
            errors.add(org.example.orgflow.exceptions.model.FieldError.builder()
                    .filedName(null)
                    .nameAr(messageAr)
                    .nameEn(messageEn)
                    .build());
        }
        final StringBuilder builder = new StringBuilder();


        final ErrorPayload errorPayload = new ErrorPayload(messageEn, messageAr, null);

        ApiResponse apiResponse = new ApiResponse();
        errorPayload.setFieldErrors(errors);
        errorPayload.setMessageAr(messageAr);
        errorPayload.setMessageEn(messageEn);
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);



        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}
