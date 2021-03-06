package com.vdm.dbmsys.rep.lucene.repository.inventory.storage;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.dbbest.dbmsys.rep.lucene.repository.query.*;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import com.vdm.dbmsys.rep.lucene.repository.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class StorageRepository extends Repository {

    private static final Logger LOGGER = LogManager.getLogger();

    private Column name;
    private Column from;
    private Column to;
    private Column cnt;
    private Column min;
    private Column value;
    private Column avg;
    private Column max;
    private Column values;
    private Column p99;

    public StorageRepository(InventoryReport report) {
        ValuesAggregator valuesAggregator = new ValuesAggregator(report);
        CntAggregator cntAggregator = new CntAggregator(report);
        ValueAggregator valueAggregator = new ValueAggregator(report);
        values = new Column(valuesAggregator);
        cnt = new Column(cntAggregator);
        value = new Column(valueAggregator);
        name = new Column(new NameAggregator(report));
        from = new Column(new FromAggregator(report));
        to = new Column(new ToAggregator(report));
        min = new Column(new MinAggregator(report, valuesAggregator));
        avg = new Column(new AvgAggregator(report, cntAggregator, valueAggregator));
        max = new Column(new MaxAggregator(report, valuesAggregator));
        p99 = new Column(new PercentileAggregator(report, valuesAggregator));
    }

    public void in(String since, String till) {
        try {
            run(new Search(new Column[] {values, cnt, value, name, from, to, min, avg, max, p99},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, since, till),
                        Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.LOGICAL_DISK),
                            Criteria.and(Criteria.term(Context.Field.COUNTER, Context.Value.PERCENTS_OF_FREE_SPACE), Criteria.term(Context.Field.INSTANCE, Context.Value.TOTAL))))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }
    }
}
