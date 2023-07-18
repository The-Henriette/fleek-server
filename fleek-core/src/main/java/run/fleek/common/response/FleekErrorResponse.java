package run.fleek.common.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekErrorResponse {
    private String message;
    private String detail;

    public static FleekErrorResponse from(String message, String detail) {
        return FleekErrorResponse.builder()
            .message(message)
            .detail(detail)
            .build();
    }
}
