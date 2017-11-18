package com.atguigu.exceltest.utils;

/**
 * @Description: 分页对象
 * @Author: xiaopeng
 * @CreateDate: 2017/11/9 19:13
 * @Version: 1.0
 */
public class Page {

    private int currentPage; //当前页
    private int pageSize; // 每页记录数
    private int totalPage; //总页数

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Page() {
    }
}
