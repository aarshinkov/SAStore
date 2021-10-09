package com.sastore.web.integration.econt.profile;

import com.sastore.web.integration.econt.EcontWeekday;
import com.sastore.web.integration.econt.nomenclatures.EcontAddress;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontCDPayOptions implements Serializable {

  private String num;
  private EcontClientProfile client;
  private Boolean moneyTransfer;
  private Boolean express;
  private String method;
  private EcontAddress address;
  private String officeCode;
  private String IBAN;
  private String BIC;
  private String bankCurrency;
  private List<Integer> payDays;
  private List<EcontWeekday> payWeekdays;
  private String additionalInstructions;

  public EcontCDPayOptions() {
  }

  public EcontCDPayOptions(String num, EcontClientProfile client, Boolean moneyTransfer, Boolean express, String method, EcontAddress address, String officeCode, String IBAN, String BIC, String bankCurrency, List<Integer> payDays, List<EcontWeekday> payWeekdays, String additionalInstructions) {
    this.num = num;
    this.client = client;
    this.moneyTransfer = moneyTransfer;
    this.express = express;
    this.method = method;
    this.address = address;
    this.officeCode = officeCode;
    this.IBAN = IBAN;
    this.BIC = BIC;
    this.bankCurrency = bankCurrency;
    this.payDays = payDays;
    this.payWeekdays = payWeekdays;
    this.additionalInstructions = additionalInstructions;
  }

  @Override
  public String toString() {
    return "EcontCDPayOptions{" + "num=" + num + ", client=" + client + ", moneyTransfer=" + moneyTransfer + ", express=" + express + ", method=" + method + ", address=" + address + ", officeCode=" + officeCode + ", IBAN=" + IBAN + ", BIC=" + BIC + ", bankCurrency=" + bankCurrency + ", payDays=" + payDays + ", payWeekdays=" + payWeekdays + ", additionalInstructions=" + additionalInstructions + '}';
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public EcontClientProfile getClient() {
    return client;
  }

  public void setClient(EcontClientProfile client) {
    this.client = client;
  }

  public Boolean getMoneyTransfer() {
    return moneyTransfer;
  }

  public void setMoneyTransfer(Boolean moneyTransfer) {
    this.moneyTransfer = moneyTransfer;
  }

  public Boolean getExpress() {
    return express;
  }

  public void setExpress(Boolean express) {
    this.express = express;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public EcontAddress getAddress() {
    return address;
  }

  public void setAddress(EcontAddress address) {
    this.address = address;
  }

  public String getOfficeCode() {
    return officeCode;
  }

  public void setOfficeCode(String officeCode) {
    this.officeCode = officeCode;
  }

  public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public String getBIC() {
    return BIC;
  }

  public void setBIC(String BIC) {
    this.BIC = BIC;
  }

  public String getBankCurrency() {
    return bankCurrency;
  }

  public void setBankCurrency(String bankCurrency) {
    this.bankCurrency = bankCurrency;
  }

  public List<Integer> getPayDays() {
    return payDays;
  }

  public void setPayDays(List<Integer> payDays) {
    this.payDays = payDays;
  }

  public List<EcontWeekday> getPayWeekdays() {
    return payWeekdays;
  }

  public void setPayWeekdays(List<EcontWeekday> payWeekdays) {
    this.payWeekdays = payWeekdays;
  }

  public String getAdditionalInstructions() {
    return additionalInstructions;
  }

  public void setAdditionalInstructions(String additionalInstructions) {
    this.additionalInstructions = additionalInstructions;
  }
}
