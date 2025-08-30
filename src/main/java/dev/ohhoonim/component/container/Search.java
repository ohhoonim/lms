package dev.ohhoonim.component.container;

public final class Search<T> implements Container{

    private T req;
    private Page page;
    public Search(T req, Page page) {
        this.req = req;
        this.page = page;
    }
    public Search() {
    }
    public T getReq() {
        return req;
    }
    public void setReq(T para) {
        this.req = para;
    }
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }

}