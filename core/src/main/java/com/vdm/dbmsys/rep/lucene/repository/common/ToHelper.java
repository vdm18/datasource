package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

public class ToHelper {

    private BaseDocAggregator toAggregator;

    public ToHelper(BaseDocAggregator toAggregator) {
        this.toAggregator = toAggregator;
    }

    public void handleSheetDoc(Document doc) {
        String stoppedDate = doc.get(Context.Field.STOPPED);
        BaseDocAggregator.Value value = BaseDocAggregator.Value.of(stoppedDate, stoppedDate);
        toAggregator.aggregate(doc.get(Context.Field.MOD_ID), value, BaseDocAggregator.TO);
    }
}
