package com.user.management.validationImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationErrorHandling {
    public ResponseEntity<?> validateErrors(BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> resultErrorsMap=new HashMap<>();
            for(FieldError fieldError:bindingResult.getFieldErrors()){
                resultErrorsMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            for(ObjectError objectError:bindingResult.getAllErrors()){
                resultErrorsMap.put(objectError.getObjectName(),objectError.getDefaultMessage());
            }
            return new ResponseEntity<Map<String,String>>(resultErrorsMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
