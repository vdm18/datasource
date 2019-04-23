package com.vdm.dbmsys.rep.lucene.repository.inventory;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;

public abstract class BaseInventoryDocAggregator extends BaseDocAggregator {

    private InventoryReport inventoryReport;

    public BaseInventoryDocAggregator(InventoryReport inventoryReport) {
        this.inventoryReport = inventoryReport;
    }

    public InventoryReport getInventoryReport() {
        return inventoryReport;
    }

    public abstract InventoryReport.Sheet sheet();

    public abstract InventoryReport.Column column();

    public void addColumnValues() {
        get().entrySet().stream()
            .forEach(columnValue ->
                getInventoryReport().addColumn(sheet(), column(),
                    columnValue.getKey(), columnValue.getValue()));
    }
}
