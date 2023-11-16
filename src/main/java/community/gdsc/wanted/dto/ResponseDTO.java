package community.gdsc.wanted.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDTO<T> {
    private final T result;

    public static ResponseDTO<String> success() {
        return new ResponseDTO<>("success");
    }
}