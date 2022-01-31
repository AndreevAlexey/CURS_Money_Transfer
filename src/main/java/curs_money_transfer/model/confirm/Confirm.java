package curs_money_transfer.model.confirm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Confirm {
    private final String operationId;
    private final String code;
}
