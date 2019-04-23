package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.dbbest.dbmsys.rep.lucene.repository.query.*;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import com.vdm.dbmsys.rep.lucene.repository.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ComputeRepository extends Repository {

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

    public ComputeRepository(InventoryReport report) {
        CntAggregator cntAggregator = new CntAggregator(report);
        ValueAggregator valueAggregator = new ValueAggregator(report);
        ValuesAggregator valuesAggregator = new ValuesAggregator(report);
        name = new Column(new NameAggregator(report));
        from = new Column(new FromAggregator(report));
        to = new Column(new ToAggregator(report));
        cnt = new Column(new CntAggregator(report));
        min = new Column(new MinAggregator(report));
        value = new Column(new ValueAggregator(report));
        avg = new Column(new AvgAggregator(report, cntAggregator, valueAggregator));
        max = new Column(new MaxAggregator(report));
        values = new Column(new ValuesAggregator(report));
        p99 = new Column(new PercentileAggregator(report, valuesAggregator));
    }

    public void in(String since, String till) {
        try {
            run(new Search(new Column[] {name, from, to, cnt, min, value, avg, max, values, p99},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, since, till),
                            Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.PROCESSOR),
                                Criteria.and(Criteria.term(Context.Field.COUNTER, Context.Value.PERCENTS_OF_PROCESSOR_TIME), Criteria.term(Context.Field.INSTANCE, Context.Value.TOTAL))))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }
    }
}