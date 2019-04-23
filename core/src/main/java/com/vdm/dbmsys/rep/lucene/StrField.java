package com.vdm.dbmsys.rep.lucene;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

public class StrField extends Field {
    /**
     * Indexed, not tokenized, omits norms, indexes
     * DOCS_ONLY, not stored.
     */
    public static final FieldType TYPE_NOT_INDEXED = new FieldType();

    /**
     * Indexed, not tokenized, omits norms, indexes
     * DOCS_ONLY, stored
     */
    public static final FieldType TYPE_INDEXED = new FieldType();

    static {
        TYPE_NOT_INDEXED.setOmitNorms(true);
        TYPE_NOT_INDEXED.setIndexOptions(IndexOptions.NONE);
        TYPE_NOT_INDEXED.setStored(true);
        TYPE_NOT_INDEXED.setTokenized(false);
        TYPE_NOT_INDEXED.freeze();

        TYPE_INDEXED.setOmitNorms(true);
        TYPE_INDEXED.setIndexOptions(IndexOptions.DOCS);
        TYPE_INDEXED.setStored(true);
        TYPE_INDEXED.setTokenized(false);
        TYPE_INDEXED.freeze();
    }

    /**
     * Creates a new textual StringField, indexing the provided String value
     * as a single token.
     *
     * @param name   field name
     * @param value  String value
     * @param stored Store.YES if the content should also be stored
     * @throws IllegalArgumentException if the field name or value is null.
     */
    public StrField(String name, String value, boolean indexed) {
        super(name, value, indexed ? TYPE_INDEXED : TYPE_NOT_INDEXED);
    }
}
