package curs_money_transfer.model.amount;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Amount {
    private AtomicInteger value = new AtomicInteger(0);
    private final String currency;

    public Amount() {
        this.currency = "RUR";
    }

    public Amount(String currency) {
        this.currency = currency;
    }

    public int plus(int sum) {
        return value.addAndGet(sum);
    }

    public int minus(int sum) {
        return value.addAndGet(-sum);
    }

}
