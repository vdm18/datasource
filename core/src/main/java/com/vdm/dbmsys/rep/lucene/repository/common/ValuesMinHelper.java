package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ValuesMinHelper {

    private Map<String, List<BaseDocAggregator.Value>> values;

    public ValuesMinHelper(Map<String, List<BaseDocAggregator.Value>> values) {
        this.values = values;
    }

    public Map<String, String> get() {
        return values.entrySet().stream()
            .collect(toMap(Map.Entry::getKey,
                entry -> min(entry.getValue())));
    }

    public String min(List<BaseDocAggregator.Value> values) {
        return String.valueOf(values.stream()
            .map(BaseDocAggregator.Value::getValue)
            .mapToDouble(Double::valueOf)
            .min()
            .orElse(0.0));
    }
}
