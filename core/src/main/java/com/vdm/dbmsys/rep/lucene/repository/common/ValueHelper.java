package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.function.BiFunction;

public class ValueHelper {

    private BaseDocAggregator valueAggregator;
    private BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder;

    public ValueHelper(BaseDocAggregator valueAggregator) {
        this(valueAggregator, DEFAULT_VALUE_BUILDER);
    }

    public ValueHelper(BaseDocAggregator valueAggregator,
                       BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder) {
        this.valueAggregator = valueAggregator;
        this.valueBuilder = valueBuilder;
    }

    public void handleSheetDoc(Document doc) {
        BaseDocAggregator.Value value = valueBuilder.apply(doc.get(Context.Field.MOD_ID), BaseDocAggregator.Value.of(doc.get(Context.Field.VALUE)));
        valueAggregator.aggregate(doc.get(Context.Field.MOD_ID), value, BaseDocAggregator.VAL);
    }

    public static final BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> DEFAULT_VALUE_BUILDER
        = (modId, value) -> value;
}
