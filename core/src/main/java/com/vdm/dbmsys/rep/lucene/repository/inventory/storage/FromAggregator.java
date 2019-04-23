package com.vdm.dbmsys.rep.lucene.repository.inventory.storage;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.FromHelper;
import org.apache.lucene.document.Document;

public class FromAggregator extends BaseStorageDocAggregator {

    private FromHelper fromHelper;

    public FromAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        fromHelper = new FromHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.FROM;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        fromHelper.handleSheetDoc(doc);
    }
}