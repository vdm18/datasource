package com.vdm.dbmsys.rep.lucene.repository.inventory.software;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.dbbest.dbmsys.rep.lucene.repository.query.*;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import com.vdm.dbmsys.rep.lucene.repository.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SoftwareRepository extends Repository {

    private static final Logger LOGGER = LogManager.getLogger();

    private Column name;
    private Column product;
    private Column version;
    private Column edition;
    private Column release;
    private Column platform;

    public SoftwareRepository(InventoryReport report) {
        name = new Column(new NameAggregator(report));
        product = new Column(new ProductAggregator(report));
        version = new Column(new VersionAggregator(report));
        edition = new Column(new EditionAggregator(report));
        release = new Column(new ReleaseAggregator(report));
        platform = new Column(new PlatformAggregator(report));
    }

    public void in(String from, String to) {
        try {
            run(new Search(new Column[] {name, product, release, version, edition, platform},
                new In(Context.Value.TESTS_VW_TST_OPERATING_SYSTEM,
                    new Where(Criteria.and(Criteria.termRange(Context.Field.STOPPED, from, to),
                        Criteria.term(Context.Field.PRODUCT, Context.Value.MICROSOFT_WINDOWS))))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }
    }
}
