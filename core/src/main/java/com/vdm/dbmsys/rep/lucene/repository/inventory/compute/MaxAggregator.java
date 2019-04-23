package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.MaxHelper;
import org.apache.lucene.document.Document;

public class MaxAggregator extends BaseComputeDocAggregator {

    private MaxHelper maxHelper;

    public MaxAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        maxHelper = new MaxHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.MAX;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        maxHelper.handleSheetDoc(doc);
    }
}