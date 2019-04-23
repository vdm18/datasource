package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class ValuesHelper {

    private Map<String, List<BaseDocAggregator.Value>> values = new HashMap<>();
    private Map<BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value>, Map<String, List<BaseDocAggregator.Value>>> results = new HashMap<>();

    public void handleSheetDoc(Document doc) {
        addValue(doc.get(Context.Field.MOD_ID), BaseDocAggregator.Value.of(doc.get(Context.Field.VALUE), doc.get(Context.Field.STOPPED)));
    }

    private void addValue(String mod, BaseDocAggregator.Value value) {
        if (nonNull(value) && nonNull(value.getValue())) {
            List<BaseDocAggregator.Value> modValues = values.get(mod);
            if (isNull(modValues)) {
                modValues = new LinkedList<>();
                values.put(mod, modValues);
            }
            modValues.add(value);
        }
    }

    public Map<String, List<BaseDocAggregator.Value>> get() {
        return values;
    }

    public Map<String, List<BaseDocAggregator.Value>> get(BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> valueBuilder) {
        Map<String, List<BaseDocAggregator.Value>> result = results.get(valueBuilder);
        if (isNull(result)) {
            result = values.entrySet().stream()
                .collect(toMap(Map.Entry::getKey,
                    entry -> entry.getValue().stream()
                        .map(value -> valueBuilder.apply(entry.getKey(), value))
                        .collect(Collectors.toList())
                ));
            results.put(valueBuilder, result);
        }
        return result;
    }
}
