package com.lms.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private String[] field;
    private String message;

}
