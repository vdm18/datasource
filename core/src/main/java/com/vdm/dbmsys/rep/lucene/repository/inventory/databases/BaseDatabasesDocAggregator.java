package com.vdm.dbmsys.rep.lucene.repository.inventory.databases;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.inventory.BaseInventoryDocAggregator;

public abstract class BaseDatabasesDocAggregator extends BaseInventoryDocAggregator {

    public BaseDatabasesDocAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Sheet sheet() {
        return InventoryReport.Sheet.DATABASES;
    }
}
