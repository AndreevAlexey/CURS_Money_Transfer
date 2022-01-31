package curs_money_transfer.controller;

import curs_money_transfer.model.confirm.Confirm;
import curs_money_transfer.exceptions.ErrorInputData;
import curs_money_transfer.exceptions.ErrorMsg;
import curs_money_transfer.exceptions.ErrorTransfer;
import curs_money_transfer.model.transfer.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import curs_money_transfer.service.TransferService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    private String makeErrorJson(String msg) {
        return "{\"message\":\"" + msg + "\",\"id\":\"0\"}";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody Transfer transfer) {
        return "{\"operationId\":\"" + transferService.makeOperation(transfer) + "\"}";
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody Confirm confirm) {
        transferService.confirmOperation(confirm);
        return "{\"operationId\":\"" + confirm.getOperationId() + "\"}";
    }

    @ExceptionHandler(ErrorTransfer.class)
    ResponseEntity<String> handlerErrorTransfer(ErrorTransfer exp) {
        return new ResponseEntity<>(makeErrorJson(exp.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorInputData.class)
    ResponseEntity<String> handlerErrorInputData(ErrorInputData exp) {
        return new ResponseEntity<>(makeErrorJson(exp.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handlerException(Exception exp) {
        transferService.log(exp.getMessage());
        return new ResponseEntity<>(makeErrorJson(ErrorMsg.OTHER_ERR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
