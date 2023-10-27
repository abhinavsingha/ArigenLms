package com.payroll.erp.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpEduInfoReq {
    private String empCode;
    private String qualification;
    private String collage;
    private String university;
    private String course;
    private String passingYr;
    private String div;
    private String percentage;
}
