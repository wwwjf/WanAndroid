package com.wengjianfeng.wanandroid.model.pojovo;

import com.wengjianfeng.wanandroid.model.pojo.ArticleBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wengjianfeng on 2018/4/6.
 */

public class ArticleListBean implements Serializable {

    private static final long serialVersionUID = 4996627817615322281L;
    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"MrTangFB","chapterId":343,"chapterName":"TV","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":2740,"link":"https://www.jianshu.com/p/cf818a09f756","niceDate":"7小时前","origin":"","projectLink":"","publishTime":1523002725000,"superChapterId":343,"superChapterName":"TV相关","tags":[],"title":"Android TV开发笔记(一) TV导航菜单","type":0,"visible":1,"zan":0},{"apkLink":"","author":"网易考拉移动端团队","chapterId":332,"chapterName":"嵌套滑动","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2738,"link":"https://kaolamobile.github.io/2018/04/03/vertical-nested-scroll-layout/","niceDate":"2天前","origin":"","projectLink":"","publishTime":1522814282000,"superChapterId":193,"superChapterName":"5.+高新技术","tags":[],"title":"嵌套滚动设计和源码分析 VerticalNestedScrollLayout 的使用","type":0,"visible":1,"zan":0}]
     * offset : 0
     * over : false
     * pageCount : 60
     * size : 20
     * total : 1188
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleBean> datas) {
        this.datas = datas;
    }

}
