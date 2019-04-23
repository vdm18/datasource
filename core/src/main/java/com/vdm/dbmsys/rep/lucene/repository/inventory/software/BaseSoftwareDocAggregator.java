package com.vdm.dbmsys.rep.lucene.repository.inventory.software;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.inventory.BaseInventoryDocAggregator;


public abstract class BaseSoftwareDocAggregator extends BaseInventoryDocAggregator {

    public BaseSoftwareDocAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Sheet sheet() {
        return InventoryReport.Sheet.SOFTWARE;
    }
}
