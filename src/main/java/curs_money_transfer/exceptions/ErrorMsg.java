package curs_money_transfer.exceptions;

public class ErrorMsg {
    // отправитель
    public static final String NOT_FND_CRD_FRM = "Не найдена карта отправителя!";
    public static final String WRN_DT_CRD_FRM = "Неверно указан срок действия карты отправителя!";
    public static final String WRN_CVV_CRD_FRM = "Неверно указан CVV карты!";
    // получатель
    public static final String NOT_FND_CRD_TO = "Не найдена карта получателя!";
    public static final String EQL_CRD_FROM_CRD_TO = "Номер карты отправителя и получателя совпадает!";

    // сумма
    public static final String NOT_ENOUGH_MNY = "Недостаточно средств для списания!";
    public static final String DIFF_CURR_FRM_TO = "Валюта карты и валюта перевода не совпадают!";
    // операция
    public static final String NOT_FND_OPER = "Операция не найдена!";
    public static final String ALRDY_CONFIRM = "Операция уже подтверждена!";
    public static final String WRN_CONF_CODE = "Неверный код подтверждения!";
    // подтверждение
    public static final String NO_CONF_OPER_ID = "Не передан идентификатор операции для подтверждения!";
    public static final String NO_CONF_CODE = "Не передан код для подтверждения!";
    // прочие
    public static final String OTHER_ERR = "Ошибка сервера: не удалось выполнить перевод.";
}
