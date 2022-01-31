package curs_money_transfer.model.database;

import curs_money_transfer.model.amount.Amount;
import curs_money_transfer.model.card.Card;
import curs_money_transfer.model.operation.Operation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DataBase {
    private final ConcurrentHashMap<String, Card> cards = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Operation> operations = new ConcurrentHashMap<>();
    private final static AtomicLong operationID = new AtomicLong(0);
    private static DataBase instance = null;

    private void init() {
        cards.put("7777777777777777", new Card("7777777777777777","10/21","777"));
        Card card = new Card("8888888888888888","10/25","888");
        card.credit(10000);
        cards.put("8888888888888888", card);
        Card card2 = new Card("9999999999999999","10/25","999");
        cards.put("9999999999999999", card2);
        Operation operation = new Operation(card, card2, new Amount());
        operation.confirme();
        addOperation(operation);
        addOperation(new Operation(card2, card, new Amount()));
    }

    // конструктор
    private DataBase() {
        init();
    }

    public static DataBase get() {
        if(instance == null) instance = new DataBase();
        return instance;
    }

    // получить карту по номеру
    public Card getCard(String cardNum) {
        return cards.get(cardNum);
    }

    // получить операцию по id
    public Operation getOperation(String id) {
        return operations.get(id);
    }

    // добавить операцию в мап
    public long addOperation(Operation operation) {
        long newID = operationID.incrementAndGet();
        operations.put(String.valueOf(newID), operation);
        return newID;
    }

}
