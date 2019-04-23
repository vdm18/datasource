package com.vdm.dbmsys.rep.lucene.repository.inventory.memory;


import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;

import java.util.Map;
import java.util.function.BiFunction;

public class ValueBuilder implements BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> {

    private Map<String, String> allMemory;

    ValueBuilder(Map<String, String> allMemory) {
        this.allMemory = allMemory;
    }

    @Override
    public BaseDocAggregator.Value apply(String modId, BaseDocAggregator.Value value) {
        try {
            String total = allMemory.get(modId);
            return BaseDocAggregator.Value.of(calculate(value.getValue(), total));
        } catch (Exception ex) {
            return BaseDocAggregator.Value.of(Context.Value.ZERO);
        }
    }

    private String calculate(String value, String total) {
        return String.valueOf(100.0 - (100 * Double.valueOf(value) / Double.valueOf(total)));
    }
}
