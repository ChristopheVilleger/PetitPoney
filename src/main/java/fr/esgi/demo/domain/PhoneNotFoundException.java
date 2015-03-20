package fr.esgi.demo.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Phone")  // 404
public class PhoneNotFoundException extends RuntimeException {

}
