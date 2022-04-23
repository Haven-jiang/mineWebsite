package com.Haven.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailContentDTO {
    private String username;
    private String textH1;
    private String textH2;
    private String namespace;
    private String service;
    private String result;
    private String timeTask;
    private String finishTime;
}
