package com.example.giphyrates.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseBody {

    private String message;
    private LocalDateTime happenedAt;
}
