package com.sastore.web.integration.econt.profile;

import com.sastore.web.integration.econt.nomenclatures.EcontAddress;
import com.sastore.web.integration.econt.shipment.EcontInstruction;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontProfilesResponseElement implements Serializable {

  private EcontClientProfile client;
  private List<EcontAddress> addresses;
  private List<EcontCDPayOptions> cdPayOptions;
  private List<EcontInstruction> instructionTemplates;

  public EcontProfilesResponseElement() {
  }

  public EcontProfilesResponseElement(EcontClientProfile client, List<EcontAddress> addresses, List<EcontCDPayOptions> cdPayOptions, List<EcontInstruction> instructionTemplates) {
    this.client = client;
    this.addresses = addresses;
    this.cdPayOptions = cdPayOptions;
    this.instructionTemplates = instructionTemplates;
  }

  @Override
  public String toString() {
    return "EcontProfilesResponseElement{" + "client=" + client + ", addresses=" + addresses + ", cdPayOptions=" + cdPayOptions + ", instructionTemplates=" + instructionTemplates + '}';
  }

  public EcontClientProfile getClient() {
    return client;
  }

  public void setClient(EcontClientProfile client) {
    this.client = client;
  }

  public List<EcontAddress> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<EcontAddress> addresses) {
    this.addresses = addresses;
  }

  public List<EcontCDPayOptions> getCdPayOptions() {
    return cdPayOptions;
  }

  public void setCdPayOptions(List<EcontCDPayOptions> cdPayOptions) {
    this.cdPayOptions = cdPayOptions;
  }

  public List<EcontInstruction> getInstructionTemplates() {
    return instructionTemplates;
  }

  public void setInstructionTemplates(List<EcontInstruction> instructionTemplates) {
    this.instructionTemplates = instructionTemplates;
  }
}
