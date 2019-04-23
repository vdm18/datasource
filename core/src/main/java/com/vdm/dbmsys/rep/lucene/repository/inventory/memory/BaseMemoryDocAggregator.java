package com.vdm.dbmsys.rep.lucene.repository.inventory.memory;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.inventory.BaseInventoryDocAggregator;

public abstract class BaseMemoryDocAggregator extends BaseInventoryDocAggregator {

    public BaseMemoryDocAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Sheet sheet() {
        return InventoryReport.Sheet.MEMORY;
    }
}