package com.vdm.dbmsys.rep.lucene.select;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.search.SimpleCollector;

import java.io.IOException;
import java.util.List;

public class DocValuesCollector extends SimpleCollector {

    private final String fieldName;
    private final List<DocValueAggregator> aggregators;
    private SortedDocValues docValues;

    public DocValuesCollector(final String fieldName, final List<DocValueAggregator> aggregators) {
        this.fieldName = fieldName;
        this.aggregators = aggregators;
    }

    @Override
    protected void doSetNextReader(final LeafReaderContext context) throws IOException {
        docValues = context.reader().getSortedDocValues(fieldName);
    }

    @Override
    public void collect(final int docId) throws IOException {
//        for (DocValueAggregator aggregator : aggregators) {
//            aggregator.handle(docValues.get(docId));
//        }
    }

    @Override
    public boolean needsScores() {
        return false;
    }
}
