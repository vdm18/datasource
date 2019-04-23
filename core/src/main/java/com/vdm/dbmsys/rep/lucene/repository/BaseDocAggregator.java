package com.vdm.dbmsys.rep.lucene.repository;

import com.vdm.dbmsys.rep.lucene.select.DocAggregator;
import com.vdm.dbmsys.rep.util.StrDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public abstract class BaseDocAggregator implements DocAggregator {

    private static final String EMPTY_STRING = "";

    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, Value> aggregatedValues = new HashMap<>();

    public void aggregate(String key, Value candidate, BiFunction<Value, Value, Value> aggregator) {
        aggregatedValues.put(key, aggregator.apply(candidate, aggregatedValues.get(key)));
    }

    @Override
    public void handle(Document doc) throws IOException {
        if (isSheetDoc(doc)) {
            handleSheetDoc(doc);
        }
    }

    public boolean isSheetDoc(Document doc) {
        return true;
    }

    public void handleSheetDoc(Document doc) {
    }

    public Map<String, String> get() {
        return aggregatedValues.entrySet().stream()
            .collect(toMap(Map.Entry::getKey,
                entry -> isNull(entry.getValue()) ? EMPTY_STRING : entry.getValue().getValue()));
    }

    public static class Value {

        public static final Value EMPTY = Value.of(EMPTY_STRING);
        public static final Value ZERO = Value.of("0");

        private String value;
        private String date;

        public Value(String value, String date) {
            this.value = value;
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDate() {
            return date;
        }

        public Value withDate(String date) {
            this.date = date;
            return this;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public static Value of(String value, String date) {
            return nonNull(value) ? new Value(value, date) : null;
        }

        public static Value of(String value) {
            return nonNull(value) ? new Value(value, EMPTY_STRING) : null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Value)) {
                return false;
            }
            Value value = (Value) o;
            return Objects.equals(getValue(), value.getValue())
                && Objects.equals(date, value.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getValue(), date);
        }
    }

    public static final BiFunction<Value, Value, Value> SUM_OF_LAST = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        } else if (isNull(old) || StrDate.after(candidate.getDate(), old.getDate())) {
            return candidate;
        } else if (StrDate.identical(candidate.getDate(), old.getDate())) {
            long val = 0;
            try {
                val = Double.valueOf(candidate.getValue()).longValue();
            } catch (Exception ex) {
                LOGGER.error("A problem while parsing a value = " + candidate.getValue()
                    + ": " + ex.getMessage());
            }
            val += Double.valueOf(old.getValue()).longValue();
            old.setValue(String.valueOf(val));
        }
        return old;
    };

    public static final BiFunction<Value, Value, Value> TO = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        } else if (isNull(old) || StrDate.after(candidate.getDate(), old.getDate())) {
            return candidate;
        }
        return old;
    };

    public static final BiFunction<Value, Value, Value> FROM = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        } else if (isNull(old) || StrDate.before(candidate.getDate(), old.getDate())) {
            return candidate;
        }
        return old;
    };

    public static final BiFunction<Value, Value, Value> CNT = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        }
        long val = isNull(old) ? 1 : Double.valueOf(old.getValue()).longValue() + 1;
        candidate.setValue(String.valueOf(val));
        return candidate;
    };

    public static final BiFunction<Value, Value, Value> MIN = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        } else if (isNull(old)) {
            return candidate;
        }
        if (Double.valueOf(old.getValue()).longValue() < Double.valueOf(candidate.getValue()).longValue()) {
            return old;
        }
        return candidate;
    };

    public static final BiFunction<Value, Value, Value> MAX = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        } else if (isNull(old)) {
            return candidate;
        }
        if (Double.valueOf(old.getValue()).longValue() > Double.valueOf(candidate.getValue()).longValue()) {
            return old;
        }
        return candidate;
    };


    public static final BiFunction<Value, Value, Value> VAL = (candidate, old) -> {
        if (isNull(candidate)) {
            return old;
        } else if (isNull(old)) {
            return candidate;
        }
        double val = Double.valueOf(old.getValue()) + Double.valueOf(candidate.getValue());
        candidate.setValue(String.valueOf(val));
        return candidate;
    };
}
