package com.vdm.dbmsys.rep.lucene.repository.inventory.databases;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.CntHelper;
import org.apache.lucene.document.Document;

public class DatabasesAggregator extends BaseDatabasesDocAggregator {

    private CntHelper cntHelper;

    public DatabasesAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        cntHelper = new CntHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.DATABASES;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        cntHelper.handleSheetDoc(doc);
    }
}
