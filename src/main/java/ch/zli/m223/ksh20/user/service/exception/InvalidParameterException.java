package ch.zli.m223.ksh20.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Empty email or password")
public class InvalidParameterException extends NoOutputException {
}
