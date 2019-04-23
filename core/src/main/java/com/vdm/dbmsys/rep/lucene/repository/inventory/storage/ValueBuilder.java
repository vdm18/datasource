package com.vdm.dbmsys.rep.lucene.repository.inventory.storage;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;

import java.util.function.BiFunction;

public class ValueBuilder implements BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> {

    @Override
    public BaseDocAggregator.Value apply(String modId, BaseDocAggregator.Value value) {
        try {
            return BaseDocAggregator.Value.of(calculate(value.getValue()));
        } catch (Exception ex) {
            return BaseDocAggregator.Value.of(Context.Value.ZERO);
        }
    }

    private String calculate(String value) {
        return String.valueOf(100.0 - Double.valueOf(value).longValue());
    }
}