package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.function.BiFunction;

public class MaxHelper {

    private BaseDocAggregator maxAggregator;
    private BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder;

    public MaxHelper(BaseDocAggregator maxAggregator) {
        this(maxAggregator, ValueHelper.DEFAULT_VALUE_BUILDER);
    }

    public MaxHelper(BaseDocAggregator maxAggregator,
                     BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder) {
        this.maxAggregator = maxAggregator;
        this.valueBuilder = valueBuilder;
    }

    public void handleSheetDoc(Document doc) {
        BaseDocAggregator.Value value = valueBuilder.apply(doc.get(Context.Field.MOD_ID), BaseDocAggregator.Value.of(doc.get(Context.Field.VALUE)));
        maxAggregator.aggregate(doc.get(Context.Field.MOD_ID), value, BaseDocAggregator.MAX);
    }
}
