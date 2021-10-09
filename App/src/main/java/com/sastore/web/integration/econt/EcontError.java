package com.sastore.web.integration.econt;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontError implements Serializable {

  private String type;
  private String message;
  private String fields;
  private List<EcontError> innerErrors;

  public EcontError() {
  }

  public EcontError(String type, String message, String fields, List<EcontError> innerErrors) {
    this.type = type;
    this.message = message;
    this.fields = fields;
    this.innerErrors = innerErrors;
  }

  @Override
  public String toString() {
    return "EcontError{" + "type=" + type + ", message=" + message + ", fields=" + fields + ", innerErrors=" + innerErrors + '}';
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getFields() {
    return fields;
  }

  public void setFields(String fields) {
    this.fields = fields;
  }

  public List<EcontError> getInnerErrors() {
    return innerErrors;
  }

  public void setInnerErrors(List<EcontError> innerErrors) {
    this.innerErrors = innerErrors;
  }
}
