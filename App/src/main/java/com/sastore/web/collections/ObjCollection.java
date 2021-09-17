package com.sastore.web.collections;

import com.sastore.web.utils.Page;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@ToString
public abstract class ObjCollection<T> {

  private List<T> collection = new ArrayList<>();
  private Page page;

  public List<T> getCollection() {
    return collection;
  }

  public void setCollection(List<T> collection) {
    this.collection = collection;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }
}
