package com.vdm.dbmsys.rep.lucene.repository.query;

public class Values {

    private int size;
    private String[] values;

    public Values(int size) {
        this.size = size;
        this.values = new String[size];
    }

    public String[] getValues() {
        return values;
    }

    public String getValue(int index) {
        if (index < size) {
            return values[index];
        } else {
            return null;
        }
    }

    public void addValue(int index, String value) {
        if (index < size) {
            values[index] = value;
        }
    }
}
