package com.tof.gateway.Gateway.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ErrorResponse(@Schema(example = "401", description = "Status code of the error response")
                            int status,
                            @Schema(example = "Unauthorized", description = "Message of the error response")
                            String message,
                            @Schema(example = "1620000000000", description = "Timestamp of the error response")
                            long timestamp) {
}
