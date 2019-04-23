package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ValuesMaxHelper {

    private Map<String, List<BaseDocAggregator.Value>> values;

    public ValuesMaxHelper(Map<String, List<BaseDocAggregator.Value>> values) {
        this.values = values;
    }

    public Map<String, String> get() {
        return values.entrySet().stream()
            .collect(toMap(Map.Entry::getKey,
                entry -> max(entry.getValue())));
    }

    private String max(List<BaseDocAggregator.Value> values) {
        return String.valueOf(values.stream()
            .map(BaseDocAggregator.Value::getValue)
            .mapToDouble(Double::valueOf)
            .max()
            .orElse(0.0));
    }
}