package com.atozdev.uptimemoniter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private boolean isSuccess;
    private Object data;
    private String msg;
}
