package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.inventory.BaseInventoryDocAggregator;

public abstract class BaseNetworkDocAggregator extends BaseInventoryDocAggregator {

    public BaseNetworkDocAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Sheet sheet() {
        return InventoryReport.Sheet.NETWORK;
    }
}