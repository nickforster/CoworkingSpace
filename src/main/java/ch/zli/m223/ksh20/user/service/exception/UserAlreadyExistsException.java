package ch.zli.m223.ksh20.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email in use")
public class UserAlreadyExistsException extends NoOutputException {
}
