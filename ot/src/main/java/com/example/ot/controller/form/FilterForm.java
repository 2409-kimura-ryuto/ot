package com.example.ot.controller.form;

import com.example.ot.controller.validator.PastAndFuture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PastAndFuture(start="start", end="end")
public class FilterForm {
    private String start = "";
    private String end = "";
    private String category = "";
}
