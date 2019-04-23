package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.inventory.BaseInventoryDocAggregator;

public abstract class BaseComputeDocAggregator extends BaseInventoryDocAggregator {

    public BaseComputeDocAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Sheet sheet() {
        return InventoryReport.Sheet.COMPUTE;
    }
}
