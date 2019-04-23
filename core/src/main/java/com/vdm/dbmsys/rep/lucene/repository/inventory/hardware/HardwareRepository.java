package com.vdm.dbmsys.rep.lucene.repository.inventory.hardware;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.dbbest.dbmsys.rep.lucene.repository.query.*;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import com.vdm.dbmsys.rep.lucene.repository.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HardwareRepository extends Repository {

    private static final Logger LOGGER = LogManager.getLogger();

    private Column name;
    private Column type;
    private Column cpu;
    private Column cores;
    private Column memory;
    private Column storage;
    private Column network;

    public HardwareRepository(InventoryReport report) {
        name = new Column(new NameAggregator(report));
        type = new Column(new TypeAggregator(report));
        cpu = new Column(new CpuAggregator(report));
        cores = new Column(new CoresAggregator(report));
        memory = new Column(new MemoryAggregator(report));
        storage = new Column(new StorageAggregator(report));
        network = new Column(new NetworkAggregator(report));
    }

    public Column memory() {
        return memory;
    }

    public void in(String from, String to) {
        try {
            run(new Search(new Column[] {name, cpu, cores},
                new In(Context.Value.TESTS_VW_TST_PROCESSORS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, from, to),
                        Criteria.term(Context.Field.PRODUCT, Context.Value.MICROSOFT_WINDOWS))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {type},
                new In(Context.Value.TESTS_VW_TST_SERVER_MACHINE_DETAILS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, from, to),
                        Criteria.term(Context.Field.PRODUCT, Context.Value.MICROSOFT_WINDOWS))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {memory},
                new In(Context.Value.TESTS_VW_TST_OPERATING_SYSTEM,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, from, to),
                        Criteria.term(Context.Field.PRODUCT, Context.Value.MICROSOFT_WINDOWS))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {storage},
                new In(Context.Value.TESTS_VW_TST_WINDOWS_VOLUMES,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, from, to),
                        Criteria.term(Context.Field.PRODUCT, Context.Value.MICROSOFT_WINDOWS))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {network},
                new In(Context.Value.COLLECT_VW_TST_PERFORMANCE_COUNTERS,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, from, to),
                        Criteria.and(Criteria.term(Context.Field.CATEGORY, Context.Value.NETWORK_INTERFACE), Criteria.term(Context.Field.COUNTER, Context.Value.CURRENT_BANDWIDTH)))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }
    }
}
