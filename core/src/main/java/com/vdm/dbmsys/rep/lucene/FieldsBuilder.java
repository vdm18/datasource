package com.vdm.dbmsys.rep.lucene;

import org.apache.lucene.document.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FieldsBuilder {
    private String name;
    private String value;
    private Map<String, String> properties;
    private Map<String, String> defaultProperties;

    private FieldsBuilder() {

    }

    public static FieldsBuilder builder() {
        return new FieldsBuilder();
    }

    public FieldsBuilder name(String name) {
        this.name = name;
        return this;
    }

    public FieldsBuilder value(String value) {
        this.value = value;
        return this;
    }

    public FieldsBuilder properties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public FieldsBuilder defaultProperties(Map<String, String> defaultProperties) {
        this.defaultProperties = defaultProperties;
        return this;
    }

    public List<Field> build() {
        List<Field> fields = new ArrayList<>();
        String type = isNull(properties) || isNull(properties.get(Context.Field.TYPE))
            ? defaultProperties.get(Context.Field.TYPE)
            : properties.get(Context.Field.TYPE);
//        Store store = StoreFieldValue.YES.equalsIgnoreCase(properties.get(Context.Field.STORE)) ? YES : NO;
        //todo: understand how to switch on/off indexing
        boolean index = IndexFieldValue.YES.equalsIgnoreCase(isNull(properties) || isNull(properties.get(Context.Field.INDEX))
            ? defaultProperties.get(Context.Field.INDEX)
            : properties.get(Context.Field.INDEX));
        switch (type) {
            case TypeFieldValue.BIGINT:
            case TypeFieldValue.INT:
            case TypeFieldValue.TINYINT:
            case TypeFieldValue.SMALLINT:
            case TypeFieldValue.DECIMAL:
//                fields.add(new LongPoint(name, Integer.valueOf(value)));
//                fields.add(new StoredField(name, value));
//                break;
            case TypeFieldValue.DATETIME:
            case TypeFieldValue.SQL_VARIANT:
            case TypeFieldValue.BIT:
            case TypeFieldValue.NVARCHAR:
            case TypeFieldValue.VARCHAR:
            case TypeFieldValue.SYSNAME:
            default:
                fields.add(new StrField(name, value, index));
        }
        return fields;

    }

    private String getPropertyValue(String propertyName) {
        return nonNull(properties) && nonNull(properties.get(propertyName))
            ? properties.get(propertyName)
            : defaultProperties.get(propertyName);
    }

    private interface StoreFieldValue {
        String YES = "yes";
        String NO = "no";
    }

    private interface IndexFieldValue {
        String YES = "yes";
        String NO = "no";
    }

    private interface TypeFieldValue {
        String NVARCHAR = "nvarchar";
        String VARCHAR = "varchar";
        String DECIMAL = "decimal";
        String INT = "int";
        String BIGINT = "bigint";
        String TINYINT = "tinyint";
        String SMALLINT = "smallint";
        String DATETIME = "datetime";
        String BIT = "bit";
        String SQL_VARIANT = "sql_variant";
        String SYSNAME = "sysname";
    }
}
