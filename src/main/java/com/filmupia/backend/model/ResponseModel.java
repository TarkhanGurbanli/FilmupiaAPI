package com.filmupia.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    @Schema(description = "Status code in the response")
    private String statusCode;
    @Schema(description = "Status message in the response")
    private String statusMsg;
}
