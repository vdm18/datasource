package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.MinHelper;
import org.apache.lucene.document.Document;

public class MinAggregator extends BaseComputeDocAggregator {

    private MinHelper minHelper;

    public MinAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        minHelper = new MinHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.MIN;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        minHelper.handleSheetDoc(doc);
    }
}
