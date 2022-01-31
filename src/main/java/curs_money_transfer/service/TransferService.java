package curs_money_transfer.service;

import curs_money_transfer.model.confirm.Confirm;
import curs_money_transfer.exceptions.ErrorMsg;
import curs_money_transfer.repository.TransferRepository;
import curs_money_transfer.model.transfer.Transfer;


public class TransferService {
    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public long makeOperation(Transfer transfer) {
        log("New " + transfer.toString());
        String cardFrom = transfer.getCardFromNumber();
        String cardTo = transfer.getCardToNumber();
        // не удалось разобрать json
        if(cardFrom == null || cardTo == null || cardFrom.isEmpty() || cardTo.isEmpty())
            transferRepository.newErrorTransfer(ErrorMsg.OTHER_ERR);
        else if(cardTo.equals(cardFrom))
            transferRepository.newErrorInputData(ErrorMsg.EQL_CRD_FROM_CRD_TO);
        // создать операцию на перевод
        return transferRepository.makeOperation(transfer);
    }

    public void confirmOperation(Confirm confirm) {
        log("New " + confirm);
        String operationId = confirm.getOperationId();
        String code = confirm.getCode();
        // не удалось разобрать json
        if( operationId == null || code == null || operationId.isEmpty() || code.isEmpty())
            transferRepository.newErrorTransfer(ErrorMsg.OTHER_ERR);
        else
            // подтверждение
            transferRepository.confirmOperation(confirm);
    }

    public void log(String msg) {
        transferRepository.log(msg);
    }
}
