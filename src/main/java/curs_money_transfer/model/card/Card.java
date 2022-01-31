package curs_money_transfer.model.card;

import curs_money_transfer.model.amount.Amount;

public class Card {
    private final String number;
    private final String validTill;
    private final String CVV;
    private Amount amount;

    public Card(String number, String validTill, String CVV) {
        this.number = number;
        this.validTill = validTill;
        this.CVV = CVV;
        this.amount = new Amount();
    }

    public Card(String number, String validTill, String CVV, String curr) {
        this(number, validTill, CVV);
        this.amount = new Amount(curr);
    }

    public String getNumber() {
        return number;
    }

    public String getValidTill() {
        return validTill;
    }

    public String getCVV() {
        return CVV;
    }

    public int getSaldo() {
        return amount.getValue().get();
    }

    public Amount getAmount() {
        return amount;
    }
    // списание
    public int debit(int sum) {
        return amount.minus(sum);
    }
    // пополнение
    public int  credit(int sum) {
        return amount.plus(sum);
    }

}
