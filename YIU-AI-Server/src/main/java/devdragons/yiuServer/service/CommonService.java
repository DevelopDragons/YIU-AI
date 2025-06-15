package devdragons.yiuServer.service;

import devdragons.yiuServer.exception.CustomException;
import devdragons.yiuServer.exception.ErrorCode;

import java.util.List;
import java.util.function.Predicate;

public class CommonService {
    public static void validateRequiredFields(List<Object> fields) {
        Predicate<Object> isNullOrEmpty = field ->
                field == null || (field instanceof String && ((String) field).isEmpty());

        if(fields.stream().anyMatch(isNullOrEmpty)) {
            throw new CustomException(ErrorCode.INSUFFICIENT_DATA);
        }
    }
}
