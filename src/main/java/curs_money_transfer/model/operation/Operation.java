package curs_money_transfer.model.operation;

import curs_money_transfer.model.amount.Amount;
import curs_money_transfer.model.card.Card;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Operation {
    private final Card cardFrom;
    private final Card cardTo;
    private final Amount amount;
    private final String confirmCode;
    private final Date date;
    private boolean confirmed;

    private static final Calendar calendar = new GregorianCalendar();

    public Operation(Card cardFrom, Card cardTo, Amount amount) {
        this.cardFrom = cardFrom;
        this.cardTo = cardTo;
        this.amount = amount;
        this.confirmed = false;
        this.confirmCode = "0000";
        this.date = calendar.getTime();
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void confirme() {
        confirmed = true;
    }

    public void execute() {
        /* сумма перевода */
        int sum = amount.getValue().get();

        /* списание */
        cardFrom.debit(sum);

        /* пополнение */
        cardTo.credit(sum);

        // отметка операции
        confirme();
    }
}
