package com.exam.rest.webservices.restfulwebservice.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

//@JsonIgnoreProperties("field1") ignore fields here instead of JsonIgnore
@JsonFilter("SomeBeanFilter") //dynamic filtering with name
public class SomeBean {


    private String field1;

    //@JsonIgnore // static filtering to remove from response all the time
    private String field2;

    private String field3;

    public SomeBean(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;

    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

}
