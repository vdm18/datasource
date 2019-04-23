package com.vdm.dbmsys.rep.lucene.select;

import org.apache.lucene.document.Document;

import java.io.IOException;

public interface DocAggregator {
    void handle(Document doc) throws IOException;

    void addColumnValues();
}