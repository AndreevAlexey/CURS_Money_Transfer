package curs_money_transfer;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import curs_money_transfer.exceptions.ErrorMsg;
import curs_money_transfer.model.amount.Amount;
import curs_money_transfer.model.confirm.Confirm;
import curs_money_transfer.model.transfer.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.testcontainers.containers.GenericContainer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CursMoneyTransferApplicationTests {
    private static String urlTransfer;
    private static String urlConfirm;
    private static HttpHeaders headers;
    private static Gson gson;
    private static Amount amount;


    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> app = new GenericContainer<>("moneytransfer1.0")
            .withExposedPorts(5500);

    @BeforeAll
    public static void init() {
        app.start();
        urlTransfer = "http://localhost:" + app.getMappedPort(5500) + "/transfer";
        urlConfirm = "http://localhost:" + app.getMappedPort(5500) + "/confirmOperation";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        amount = new Amount();
        amount.plus(10000);
    }

    @Test
    void test_transfer_WRN_DT_CRD_FRM() {
        Transfer transfer = new Transfer("8888888888888888","12/25","888","9999999999999999", amount);
        String json = gson.toJson(transfer);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlTransfer, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.WRN_DT_CRD_FRM;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_transfer_WRN_CVV_CRD_FRM() {
        Transfer transfer = new Transfer("8888888888888888","10/25","777","9999999999999999", amount);
        String json = gson.toJson(transfer);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlTransfer, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.WRN_CVV_CRD_FRM;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_transfer_NOT_FND_CRD_FRM() {
        Transfer transfer = new Transfer("5555555555555555","10/25","888","9999999999999999", amount);
        String json = gson.toJson(transfer);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlTransfer, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.NOT_FND_CRD_FRM;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_transfer_NOT_ENOUGH_MNY() {
        Transfer transfer = new Transfer("7777777777777777","10/21","777","9999999999999999", amount);
        String json = gson.toJson(transfer);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlTransfer, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.NOT_ENOUGH_MNY;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_transfer_EQL_CRD_FROM_CRD_TO() {
        Transfer transfer = new Transfer("8888888888888888","10/25","888","8888888888888888", amount);
        String json = gson.toJson(transfer);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlTransfer, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.EQL_CRD_FROM_CRD_TO;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_confirm_NOT_FND_OPER() {
        Confirm confirm = new Confirm("111", "0000");
        String json = gson.toJson(confirm);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlConfirm, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.NOT_FND_OPER;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_confirm_ALRDY_CONFIRM() {
        Confirm confirm = new Confirm("1", "0000");
        String json = gson.toJson(confirm);
        System.out.println("json = "+ json);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlConfirm, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.ALRDY_CONFIRM;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }

    @Test
    void test_confirm_WRN_CONF_CODE() {
        Confirm confirm = new Confirm("2", "9999");
        String json = gson.toJson(confirm);
        System.out.println("json = "+ json);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        String resultJsonStr = restTemplate.postForObject(urlConfirm, request, String.class);
        System.out.println("resultJsonStr = " + resultJsonStr);
        String expected = ErrorMsg.WRN_CONF_CODE;
        Assertions.assertTrue(resultJsonStr.contains(expected));
    }


}
