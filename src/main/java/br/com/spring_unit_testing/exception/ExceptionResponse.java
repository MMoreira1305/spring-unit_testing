package br.com.spring_unit_testing.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class ExceptionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    public ExceptionResponse(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    private Date timeStamp;
    private String message;
    private String details;
}
