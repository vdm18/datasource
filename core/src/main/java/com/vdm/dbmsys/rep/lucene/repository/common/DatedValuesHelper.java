package com.vdm.dbmsys.rep.lucene.repository.common;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DatedValuesHelper {

    private Map<String, Map<String, BaseDocAggregator.Value>> values = new HashMap<>();
    private Map<BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value>, Map<String, List<BaseDocAggregator.Value>>> results = new HashMap<>();

    public void handleSheetDoc(Document doc) {
        addValue(doc.get(Context.Field.MOD_ID), BaseDocAggregator.Value.of(doc.get(Context.Field.VALUE), doc.get(Context.Field.STOPPED)));
    }

    private void addValue(String mod, BaseDocAggregator.Value value) {
        if (nonNull(value) && nonNull(value.getValue())) {
            Map<String, BaseDocAggregator.Value> modValues = values.get(mod);
            if (isNull(modValues)) {
                modValues = new HashMap<>();
                values.put(mod, modValues);
            }
            modValues.put(value.getDate(), value);
        }
    }

    public Map<String, Map<String, BaseDocAggregator.Value>> get() {
        return values;
    }
}
