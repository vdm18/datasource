package com.vdm.dbmsys.rep.lucene.repository.query;

import com.vdm.dbmsys.rep.lucene.select.DocAggregator;

public class Column {
    private DocAggregator aggregator;

    public Column(DocAggregator aggregator) {
        this.aggregator = aggregator;
    }

    public DocAggregator getAggregator() {
        return aggregator;
    }
}
