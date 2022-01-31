package curs_money_transfer;

import curs_money_transfer.exceptions.ErrorInputData;
import curs_money_transfer.exceptions.ErrorTransfer;
import curs_money_transfer.model.amount.Amount;
import curs_money_transfer.model.confirm.Confirm;
import curs_money_transfer.model.transfer.Transfer;
import curs_money_transfer.repository.TransferRepository;
import curs_money_transfer.service.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;

public class TestTransferService {
    private final Amount amount = new Amount();
    private TransferService transferService;

    @BeforeEach
    public void init() {
        TransferRepository transferRepository = mock(TransferRepository.class);
        when(transferRepository.makeOperation(Mockito.any())).thenReturn(999L);
        Mockito.doThrow(ErrorTransfer.class).when(transferRepository).newErrorTransfer(Mockito.any());
        Mockito.doThrow(ErrorInputData.class).when(transferRepository).newErrorInputData(Mockito.any());
        Mockito.doNothing().when(transferRepository).confirmOperation(Mockito.any());
        transferService = new TransferService(transferRepository);
    }


    @Test
    public void test_makeOperation_null_cardFrom_thrown_ErrorTransfer() {
        // given
        Transfer transfer = new Transfer("","10/25","888","99999", amount);
        // expect
        Assertions.assertThrows(ErrorTransfer.class, () -> transferService.makeOperation(transfer));
    }

    @Test
    public void test_makeOperation_null_cardTo_thrown_ErrorTransfer() {
        // given
        Transfer transfer = new Transfer("88888888","10/25","888","", amount);
        // expect
        Assertions.assertThrows(ErrorTransfer.class, () -> transferService.makeOperation(transfer));
    }

    @Test
    public void test_makeOperation_equal_cardFrom_cardTo_thrown_ErrorInputData() {
        // given
        Transfer transfer = new Transfer("88888888","10/25","888","88888888", amount);
        // expect
        Assertions.assertThrows(ErrorInputData.class, () -> transferService.makeOperation(transfer));
    }

    @Test
    public void test_confirmOperation_null_OperationId_thrown_ErrorTransfer() {
        // given
        Confirm confirm = new Confirm("", "9999");
        // expect
        Assertions.assertThrows(ErrorTransfer.class, () -> transferService.confirmOperation(confirm));
    }

    @Test
    public void test_confirmOperation_null_code_thrown_ErrorTransfer() {
        // given
        Confirm confirm = new Confirm("1", "");
        // expect
        Assertions.assertThrows(ErrorTransfer.class, () -> transferService.confirmOperation(confirm));
    }
}
