package dev.ohhoonim.component.container;

import jakarta.validation.Valid;

public final class Search<T> implements Container{

    @Valid private T req;
    private Page page;
    public Search(@Valid T req, Page page) {
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