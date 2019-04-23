package com.vdm.dbmsys.rep.lucene.object;

import com.vdm.dbmsys.rep.lucene.UnitOfWork;
import com.vdm.dbmsys.rep.lucene.Context;

import java.util.UUID;

public abstract class Data {
    private String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected void markNew() {
        UnitOfWork.getCurrent().registerNew(this);
    }

    protected void markClean() {
        UnitOfWork.getCurrent().registerClean(this);
    }

    protected void markDirty() {
        UnitOfWork.getCurrent().registerDirty(this);
    }

    protected void markRemoved() {
        UnitOfWork.getCurrent().registerRemoved(this);
    }

    public String indexName() {
        return Context.EMPTY;
    }
}
