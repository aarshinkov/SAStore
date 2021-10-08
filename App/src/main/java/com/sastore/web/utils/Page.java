package com.sastore.web.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public abstract class Page {

  protected Integer currentPage;
  protected Long noPagedTotal;
  protected Long pagedTotal;
  protected Long total;
  protected Integer maxElementsPerPage;
  protected Integer startPage;
  protected Integer endPage;

  protected abstract Long getTotalPages();

  protected abstract boolean isFirst();

  protected abstract boolean isLast();

  protected abstract boolean hasNext(Integer currentPage);

  protected abstract boolean hasPrevious(Integer currentPage);
}
