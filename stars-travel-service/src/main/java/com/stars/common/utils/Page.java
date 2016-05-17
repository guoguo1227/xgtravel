package com.stars.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Description :分页对象
 * Author : guo
 * Date : 2016/5/17 0:25
 */
public class Page<T> implements Serializable {
    public static final int DEFAULT_PAGE_SIZE = 30;
    private int totalCount;
    private int totalPage;
    private int currentPage = 1;
    private int pageSize = 30;
    private Object searchParam;
    private List<T> pageData;
    private boolean hasNextPage;

    public Page() {
    }

    public Object getSearchParam() {
        return this.searchParam;
    }

    public void setSearchParam(Object searchParam) {
        this.searchParam = searchParam;
    }

    public List<T> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalPage = (totalCount + this.pageSize - 1) / this.pageSize;
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return this.currentPage > this.getTotalPage()?this.totalCount:this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNextPage() {
        this.hasNextPage = this.totalCount > this.currentPage * this.pageSize;
        return this.hasNextPage;
    }
}

