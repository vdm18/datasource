package com.vdm.dbmsys.rep.lucene.repository.inventory.databases;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.dbbest.dbmsys.rep.lucene.repository.query.*;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import com.vdm.dbmsys.rep.lucene.repository.query.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DatabasesRepository extends Repository {

    private static final Logger LOGGER = LogManager.getLogger();

    private Column name;
    private Column product;
    private Column version;
    private Column edition;
    private Column release;
    private Column platform;
    private Column databases;


    public DatabasesRepository(InventoryReport report) {
        name = new Column(new NameAggregator(report));
        product = new Column(new ProductAggregator(report));
        version = new Column(new VersionAggregator(report));
        edition = new Column(new EditionAggregator(report));
        release = new Column(new ReleaseAggregator(report));
        platform = new Column(new PlatformAggregator(report));
        databases = new Column(new DatabasesAggregator(report));
    }


    public void in(String from, String to) {
        try {
            run(new Search(new Column[] {name, product, version, edition},
                new In(Context.Value.TESTS_VW_TST_BASE_SQL_COLLECTION,
                    new Where(Criteria.termRange(Context.Field.STOPPED, from, to)))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {release, platform},
                new In(Context.Value.TESTS_VW_TST_DATABASE_INSTANCE_DETAILS,
                    new Where(Criteria.termRange(Context.Field.STOPPED, from, to)))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }

        try {
            run(new Search(new Column[] {databases},
                new In(Context.Value.TESTS_VW_TST_INSTANCE_DATABASES,
                    new Where(Criteria.termRange(Context.Field.STOPPED, from, to)))));
        } catch (IOException ex) {
            LOGGER.error("We had a problem while searching: " + ex.getMessage());
        }
    }
}
