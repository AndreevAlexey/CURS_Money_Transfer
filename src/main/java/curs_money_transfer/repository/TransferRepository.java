package curs_money_transfer.repository;

import curs_money_transfer.model.amount.Amount;
import curs_money_transfer.model.card.Card;
import curs_money_transfer.model.confirm.Confirm;
import curs_money_transfer.exceptions.ErrorInputData;
import curs_money_transfer.exceptions.ErrorMsg;
import curs_money_transfer.exceptions.ErrorTransfer;
import curs_money_transfer.logger.FileLogger;
import curs_money_transfer.logger.ILogger;
import curs_money_transfer.model.database.DataBase;
import curs_money_transfer.model.operation.Operation;
import curs_money_transfer.model.transfer.Transfer;

public class TransferRepository {
    private final ILogger logger  = FileLogger.get();
    private final DataBase db = DataBase.get();

    // конструктор
    public TransferRepository() {}

    // добавить в лог
    public void log(String msg) {
        logger.log(msg);
    }

    public void newErrorInputData(String msg) {
        log("Ошибка: " + msg);
        throw new ErrorInputData(msg);
    }

    public void newErrorTransfer(String msg) {
        log("Ошибка: " + msg);
        throw new ErrorTransfer(msg);
    }

    // добавить операцию в мап
    private long addOperation(Operation operation) {
        long newID = db.addOperation(operation);
        log("New operationID = " + newID);
        return newID;
    }

    /*** создать перевод ***/
    public long makeOperation(Transfer transfer) {
        /* карта отправителя */
        Card cardFrom = db.getCard(transfer.getCardFromNumber());
        // проверка карты отправителя
        chkCardFrom(cardFrom, transfer);

        /* карта получателя **/
        Card cardTo = db.getCard(transfer.getCardToNumber());
        // проверка карты получателя
        chkCardTo(cardTo);

        /* сумма и валюта списания */
        Amount amount = transfer.getAmount();
        // проверка списания
        chkAmount(cardFrom, amount);

        /* операция */
        Operation operation = new Operation(cardFrom, cardTo, amount);

        // добавить в мап
        return addOperation(operation);
    }

    /*** подтверждение перевода ***/
    public void confirmOperation(Confirm confirm) {
        // операция для подтверждения
        Operation operation = db.getOperation(confirm.getOperationId());
        // проверка операции
        chkOperation(operation, confirm);
        // выполнить перевод
        operation.execute();
        log("Operation ID = " + confirm.getOperationId() + " confirmed successfully");
    }

    /*** ПРОВЕРКИ ***/
    // проверка карты отправителя
    private void chkCardFrom(Card card, Transfer transfer) {
        // карта не найдена
        if(card == null) newErrorInputData(ErrorMsg.NOT_FND_CRD_FRM);
        else {
            // не совпадает срок действия
            if (!card.getValidTill().equals(transfer.getCardFromValidTill()))
                newErrorInputData(ErrorMsg.WRN_DT_CRD_FRM);
            // не совпадает CVV
            if (!card.getCVV().equals(transfer.getCardFromCVV())) newErrorInputData(ErrorMsg.WRN_CVV_CRD_FRM);
        }
    }

    // проверка карты отправителя
    private void chkCardTo(Card card) {
        // карта не найдена
        if(card == null) newErrorInputData(ErrorMsg.NOT_FND_CRD_TO);
    }

    // проверка возможности списания
    private void chkAmount(Card card, Amount amount) {
        // сумма списания
        int transSum = amount.getValue().get();
        if(transSum <= 0) newErrorTransfer("Некорректная сумма списания(" + transSum + ")!");
        // остаток на карте
        int saldo = card.getAmount().getValue().get();
        // остаток меньше суммы списания
        if(saldo < transSum) newErrorTransfer(ErrorMsg.NOT_ENOUGH_MNY);

        // валюта карты
        String cardCurr = card.getAmount().getCurrency();
        // валюта перевода
        String transCurr = amount.getCurrency();
        // валюты не совпадают
        if(!cardCurr.equals(transCurr)) newErrorTransfer("Валюта карты(" + cardCurr + ") и валюта перевода(" + transCurr + ") не совпадают!");
    }

    // получить и проверить операцию для подтверждения
    private void chkOperation(Operation operation, Confirm confirm) {
        // операция не найдена
        if(operation == null) newErrorInputData(ErrorMsg.NOT_FND_OPER);
        else {
            // операция уже подтверждена
            if (operation.isConfirmed()) newErrorInputData(ErrorMsg.ALRDY_CONFIRM);
            // пришел неверный код подтверждения
            if (!operation.getConfirmCode().equals(confirm.getCode())) newErrorInputData(ErrorMsg.WRN_CONF_CODE);
        }
    }

}
