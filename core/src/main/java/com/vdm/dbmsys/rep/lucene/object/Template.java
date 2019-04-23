package com.vdm.dbmsys.rep.lucene.object;

import com.vdm.dbmsys.rep.lucene.Context;

import java.util.HashMap;
import java.util.Map;

public class Template extends Data {

    private Map<String, String> properties = new HashMap<>();

    public Template() {
    }

    private Template(String doc) {
        properties.put(Context.Field.DOC, doc);
    }

    public static Template createForDoc(String doc) {
        Template template = new Template(doc);
        template.markNew();
        return template;
    }

    public static void addProperty(Template template, String propName, String propValue) {
        template.addProperty(propName, propValue);
        template.markDirty();
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void addProperty(String name, String value) {
        properties.put(name, value);
    }

    public String getDoc() {
        return properties.get(Context.Field.DOC);
    }

    public void setDoc(String doc) {
        properties.put(Context.Field.DOC, doc);
    }

    public String getField() {
        return properties.get(Context.Field.FIELD);
    }

    public void setField(String field) {
        properties.put(Context.Field.FIELD,field);
    }

    @Override
    public String indexName() {
        return Context.MAPPING;
    }
}
