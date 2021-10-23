package com.itda.apiserver.dto;

import com.itda.apiserver.domain.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewsOfProductDto {
    private Long id;
    private Writer writer;
    private LocalDateTime date;
    private List<ReviewImage> images;
    private String contents;

    public ReviewsOfProductDto(Long id, String name, String imgURL, LocalDateTime date, List<ReviewImage> images, String contents) {
        this.id = id;
        this.writer = new Writer(name, imgURL);
        this.date = date;
        this.images = images;
        this.contents = contents;
    }
}

@AllArgsConstructor
class Writer {
    String name;
    String imgUrl;
}
