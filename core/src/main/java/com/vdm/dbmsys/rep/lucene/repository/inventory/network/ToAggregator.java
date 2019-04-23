package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ToHelper;
import org.apache.lucene.document.Document;

public class ToAggregator extends BaseNetworkDocAggregator {

    private ToHelper toHelper;

    public ToAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        toHelper = new ToHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.TO;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        toHelper.handleSheetDoc(doc);
    }
}
