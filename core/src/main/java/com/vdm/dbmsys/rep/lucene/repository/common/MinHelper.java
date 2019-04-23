package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.function.BiFunction;

public class MinHelper {

    private BaseDocAggregator minAggregator;
    private BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder;

    public MinHelper(BaseDocAggregator minAggregator) {
        this(minAggregator, ValueHelper.DEFAULT_VALUE_BUILDER);
    }

    public MinHelper(BaseDocAggregator minAggregator,
                     BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder) {
        this.minAggregator = minAggregator;
        this.valueBuilder = valueBuilder;
    }

    public void handleSheetDoc(Document doc) {
        BaseDocAggregator.Value value = valueBuilder.apply(doc.get(Context.Field.MOD_ID), BaseDocAggregator.Value.of(doc.get(Context.Field.VALUE)));
        minAggregator.aggregate(doc.get(Context.Field.MOD_ID), value, BaseDocAggregator.MIN);
    }
}