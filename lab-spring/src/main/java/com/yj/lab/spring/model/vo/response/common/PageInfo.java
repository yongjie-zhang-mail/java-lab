package com.yj.lab.spring.model.vo.response.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageInfo<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -344310947022925108L;
    /**
     * 页码
     */
    private Long pageNum;
    /**
     * 每页条数
     */
    private Long pageSize;
    /**
     * 页数
     */
    private Long pages;
    /**
     * 总数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> list;

    /**
     * 分页构造
     *
     * @param page plus分页对象
     */
    public PageInfo(IPage<T> page) {
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.total = page.getTotal();
        this.list = page.getRecords();
        this.pages = page.getPages();
    }
}
