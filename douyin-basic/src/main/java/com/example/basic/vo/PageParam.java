package com.example.basic.vo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author chenlianghao5
 */
@Getter
@Setter
@ToString
public class PageParam implements Serializable {

    /**
     * 默认页面编号
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码，从1开始
     */
    private int pageNum;

    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;

    /**
     * 构造使用默认的参数.
     *
     * @return
     */
    public PageParam() {
        this(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
    }

    /**
     * 实例.
     *
     * @param pageNum
     * @param pageSize
     */
    public PageParam(int pageNum, int pageSize) {
        setPageNum(pageNum);
        setPageSize(pageSize);
    }

    /**
     * 分页参数
     *
     * @param pageNum  页面编号
     * @param pageSize 页面大小
     * @param total    总
     * @return
     */
    public PageParam(int pageNum, int pageSize, long total) {
        setPageNum(pageNum);
        setPageSize(pageSize);
        setTotal(total);
    }

    /**
     * 默认设定页数 >= 1.
     *
     * @param pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = Math.max(pageNum, DEFAULT_PAGE_NUM);
    }

    /**
     * 默认设定页面大小 >= 0条.
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 设置总条数.
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
        if (total <= DEFAULT_PAGE_SIZE) {
            pages = 1;
            return;
        }
        pages = (int)(total / pageSize + ((total % pageSize == 0) ? 0 : 1));
    }

    /**
     * 查询页面编号
     *
     * @return int
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 查询页面大小
     *
     * @return int
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 查询总
     *
     * @return long
     */
    public long getTotal() {
        return total;
    }

    /**
     * 查询页面
     *
     * @return int
     */
    public int getPages() {
        return pages;
    }

    /**
     * 功能描述:判断当前页是否是最后一页
     *
     * @return boolean
     */
    public boolean currentPageLast() {
        int pageNum = getPageNum();
        int totalPages = getPages();
        if (pageNum == totalPages) {
            return true;
        }
        return false;
    }
}

