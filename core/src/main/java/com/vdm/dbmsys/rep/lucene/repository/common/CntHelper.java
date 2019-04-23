package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

public class CntHelper {

    private static final String ONE = "1";

    private BaseDocAggregator cntAggregator;

    public CntHelper(BaseDocAggregator cntAggregator) {
        this.cntAggregator = cntAggregator;
    }

    public void handleSheetDoc(Document doc) {
        cntAggregator.aggregate(doc.get(Context.Field.MOD_ID), BaseDocAggregator.Value.of(ONE), BaseDocAggregator.CNT);
    }
}
