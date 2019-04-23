package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;

import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class AvgHelper {

    private BaseDocAggregator cntAggregator;
    private BaseDocAggregator valueAggregator;

    public AvgHelper(BaseDocAggregator cntAggregator, BaseDocAggregator valueAggregator) {
        this.cntAggregator = cntAggregator;
        this.valueAggregator = valueAggregator;
    }

    public Map<String, String> avgs() {
        Map<String, String> values = valueAggregator.get();
        return cntAggregator.get().entrySet().stream()
            .collect(
                toMap(Map.Entry::getKey,
                    entry -> avg(entry.getValue(),
                        values.get(entry.getKey()))));
    }

    private String avg(String cnt, String value) {
        double avg = 0.0;
        if (nonNull(value) && !value.isEmpty()) {
            avg = Double.valueOf(value) / Double.valueOf(cnt);
        }
        return String.valueOf(avg);
    }
}