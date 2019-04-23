package com.vdm.dbmsys.rep.lucene.repository.query;

import com.vdm.dbmsys.rep.lucene.Infrastructure;
import com.vdm.dbmsys.rep.lucene.select.DocAggregator;
import com.vdm.dbmsys.rep.lucene.select.DocCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Context {

    private static final Logger LOGGER = LogManager.getLogger();

    private Column[] columns;
    private String index;
    private Criteria criteria;

    public void setColumns(Column[] columns) {
        this.columns = columns;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setCriterias(Criteria criteria) {
        this.criteria = criteria;
    }

    private List<DocAggregator> aggregators() {
        return Arrays.stream(columns)
            .map(Column::getAggregator)
            .collect(Collectors.toList());
    }

    public void search() throws IOException {
        IndexReader reader = Infrastructure.indexReader(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        DocCollector collector = new DocCollector(aggregators());
        searcher.search(criteria.generateQuery(), collector);
        aggregators().forEach(DocAggregator::addColumnValues);
    }
}
