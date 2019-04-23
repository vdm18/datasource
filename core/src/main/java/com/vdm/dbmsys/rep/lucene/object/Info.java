package com.vdm.dbmsys.rep.lucene.object;

import com.vdm.dbmsys.rep.lucene.Context;

public class Info extends Data {

    private String subject;
    private String storage;


    public Info(String subject, String storage) {
        this.subject = subject;
        this.storage = storage;
    }

    public static Info create(String subject, String storage) {
        Info obj = new Info(subject, storage);
        obj.markNew();
        return obj;
    }

    public String getSubject() {
        return subject;
    }

    public String getStorage() {
        return storage;
    }

    @Override
    public String indexName() {
        return Context.DataRecordElement.STORAGE;
    }
}
