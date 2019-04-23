package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

public class PercentileHelper {

    private Map<String, List<BaseDocAggregator.Value>> values;

    public PercentileHelper(Map<String, List<BaseDocAggregator.Value>> values) {
        this.values = values;
    }

    public Map<String, String> percentiles() {
        return values.entrySet().stream()
            .collect(toMap(Map.Entry::getKey,
                entry -> percentile(entry.getValue())));
    }

    private String percentile(List<BaseDocAggregator.Value> values) {
        values.sort(ASC);
        return values.get(percentileIndex(values.size())).getValue();
    }

    private int percentileIndex(int size) {
        return Double.valueOf(Math.round(size * 99.0 / 100) - 1).intValue();
    }

    private static final Comparator<BaseDocAggregator.Value> ASC = (val1, val2) -> {
        String str1 = val1.getValue();
        String str2 = val2.getValue();
        double long1 = isNull(str1) ? 0 : Double.valueOf(str1).longValue();
        double long2 = isNull(str2) ? 0 : Double.valueOf(str2).longValue();
        return Double.compare(long1, long2);
    };
}
