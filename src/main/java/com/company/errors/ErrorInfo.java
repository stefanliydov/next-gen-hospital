package com.company.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {

    private HttpStatus status;
    private String url;
    private String error;

    public ErrorInfo(HttpStatus status, String url, Throwable ex){
        this.status = status;
        this.url = url;
        this.error = ex.getMessage();
    }
}
