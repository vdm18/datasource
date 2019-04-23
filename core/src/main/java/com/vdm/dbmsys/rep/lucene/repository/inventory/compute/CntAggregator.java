package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.CntHelper;
import org.apache.lucene.document.Document;

public class CntAggregator extends BaseComputeDocAggregator {

    private CntHelper cntHelper;

    public CntAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        cntHelper = new CntHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.CNT;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        cntHelper.handleSheetDoc(doc);
    }
}