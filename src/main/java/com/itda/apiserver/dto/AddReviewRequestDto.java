package com.itda.apiserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddReviewRequestDto {

    private List<String> images;
    private String contents;
}
