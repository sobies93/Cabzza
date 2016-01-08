package com.pri.cabzza.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author rasgrass
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Difference in years should not exceed 1 year")
public class WrongDateRangeException extends RuntimeException {

}
