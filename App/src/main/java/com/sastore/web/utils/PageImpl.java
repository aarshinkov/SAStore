package com.sastore.web.utils;

import lombok.ToString;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@ToString
public class PageImpl extends Page {

    @Override
    public Long getTotalPages() {
        noPagedTotal = getNoPagedTotal();
        maxElementsPerPage = getMaxElementsPerPage();

        if (noPagedTotal % maxElementsPerPage != 0) {
            return (noPagedTotal / maxElementsPerPage) + 1;
        }

        return noPagedTotal / maxElementsPerPage;
    }

    @Override
    public boolean isFirst() {
        return getCurrentPage() == 1;
    }

    @Override
    public boolean isLast() {
        return getCurrentPage().equals(getTotalPages());
    }

    @Override
    public boolean hasNext(Integer currentPage) {
        long totalPages = getTotalPages();

        if (totalPages == 1) {
            return false;
        }

        if (totalPages > 1) {
            return currentPage < totalPages;
        }

        return currentPage == totalPages;
    }

    @Override
    public boolean hasPrevious(Integer currentPage) {
        long totalPages = getTotalPages();

        if ((currentPage == totalPages) || (currentPage < totalPages && currentPage > 1)) {
            return true;
        }

        return currentPage != 1;
    }
}
