package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.dbbest.dbmsys.rep.lucene.repository.query.*;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import com.vdm.dbmsys.rep.lucene.repository.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class NetworkRepository extends Repository {


    private static final Logger LOGGER = LogManager.getLogger();

    private Column bytesReceivedValues;
    private Column bytesSentValues;
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

    public NetworkRepository(InventoryReport report) {
        CntAggregator cntAggregator = new CntAggregator(report);
        BytesReceivedValuesAggregator bytesReceivedValuesAggregator = new BytesReceivedValuesAggregator(report);
        BytesSentValuesAggregator bytesSentValuesAggregator = new BytesSentValuesAggregator(report);
        ValuesAggregator valuesAggregator = new ValuesAggregator(report,
            bytesSentValuesAggregator,
            bytesReceivedValuesAggregator);
        ValueAggregator valueAggregator = new ValueAggregator(report, valuesAggregator);


        bytesReceivedValues = new Column(bytesReceivedValuesAggregator);
        bytesSentValues = new Column(bytesSentValuesAggregator);
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
            run(new Search(new Column[] {bytesReceivedValues},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, since, till),
                        Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.NETWORK_INTERFACE), Criteria.term(Context.Field.COUNTER, Context.Value.BYTES_RECEIVED)))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {bytesSentValues},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, since, till),
                        Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.NETWORK_INTERFACE), Criteria.term(Context.Field.COUNTER, Context.Value.BYTES_SENT)))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {values, cnt, from, to, min, value, avg, max, p99},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, since, till),
                        Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.NETWORK_INTERFACE), Criteria.term(Context.Field.COUNTER, Context.Value.CURRENT_BANDWIDTH)))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {name},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, since, till),
                        Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.NETWORK_INTERFACE),
                                Criteria.or(Criteria.term(Context.Field.COUNTER, Context.Value.BYTES_RECEIVED),
                                    Criteria.or(Criteria.term(Context.Field.COUNTER, Context.Value.BYTES_SENT), Criteria.term(Context.Field.COUNTER, Context.Value.CURRENT_BANDWIDTH)))))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }
    }
}
