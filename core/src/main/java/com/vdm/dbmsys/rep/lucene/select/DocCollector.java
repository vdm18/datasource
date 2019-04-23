package com.vdm.dbmsys.rep.lucene.select;

import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.SimpleCollector;

import java.io.IOException;
import java.util.List;

public class DocCollector extends SimpleCollector {

    private LeafReader currReader;
    private List<DocAggregator> aggregators;

    public DocCollector(List<DocAggregator> aggregators) {
        this.aggregators = aggregators;
    }

    @Override
    protected void doSetNextReader(final LeafReaderContext context) throws IOException {
        currReader = context.reader();
    }

    @Override
    public void collect(final int docId) throws IOException {
        for (DocAggregator aggregator : aggregators) {
            aggregator.handle(currReader.document(docId));
        }
    }

    @Override
    public boolean needsScores() {
        return false;
    }
}
