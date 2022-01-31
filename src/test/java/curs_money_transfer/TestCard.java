package curs_money_transfer;

import curs_money_transfer.model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;


public class TestCard {
    final Card card = new Card("9999999999", "10/25", "999");

    @Test
    public void Test_credit() {
        // given
        int sum = 1000;
        int expected = 1000;
        // when
        card.credit(sum);
        int result = card.getSaldo();
        // then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void Test_debit() {
        // given
        int sum = 100;
        card.getAmount().setValue(new AtomicInteger(1000));
        int expected = 900;
        // when
        card.debit(sum);
        int result = card.getSaldo();
        // then
        Assertions.assertEquals(expected, result);
    }
}
