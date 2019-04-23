package com.vdm.dbmsys.rep.lucene.object;

import com.vdm.dbmsys.rep.lucene.Context;

import java.util.HashMap;

public class DataRecord extends Data {
    private final com.dbbest.dbmsys.common.model.DataRecord dataRecord;

    DataRecord(com.dbbest.dbmsys.common.model.DataRecord dataRecord) {
        this.dataRecord = dataRecord;
    }

    public static DataRecord createDataRecord(com.dbbest.dbmsys.common.model.DataRecord dataRecord) {
        DataRecord instance = new DataRecord(dataRecord);
        instance.markNew();
        return instance;
    }

    public HashMap<String, String> getHead() {
        return dataRecord.getHead();
    }

    public HashMap<String, String> getBody() {
        return dataRecord.getBody();
    }

    public String getHeadElement(String key) {
        return dataRecord.getHeadElement(key);
    }

    public String getBodyElement(String key) {
        return dataRecord.getBodyElement(key);
    }

    @Override
    public String indexName() {
        return getHeadElement(Context.DataRecordElement.STORAGE);
    }
}
