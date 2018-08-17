package com.zpp.springboot.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private String type;
    private String reason;
    private String message;

    public Error(String type, String reason, String message) {
        this.type = type;
        this.reason = reason;
        this.message = message;
    }
}
