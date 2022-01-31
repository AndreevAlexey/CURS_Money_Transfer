package curs_money_transfer;

import curs_money_transfer.model.amount.Amount;
import curs_money_transfer.model.card.Card;
import curs_money_transfer.model.operation.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestOperation {
    static final Card cardFrom = new Card("99999999", "10/25", "999");
    static final Card cardTo = new Card("88888888", "10/25", "888");
    static final Amount amount = new Amount();
    static Operation operation;

    @BeforeAll
    public static void init() {
        cardFrom.credit(1000);
        amount.plus(100);
        operation = new Operation(cardFrom, cardTo, amount);
    }

    @Test
    public void Test_execute() {
        // given
        int expected1 = 900;
        int expected2 = 100;
        // when
        operation.execute();
        int result1 = cardFrom.getSaldo();
        int result2 = cardTo.getSaldo();
        // then
        Assertions.assertEquals(expected1, result1);
        Assertions.assertEquals(expected2, result2);
    }
}
