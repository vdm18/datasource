package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValueHelper;
import org.apache.lucene.document.Document;

public class ValueAggregator extends BaseComputeDocAggregator {

    private ValueHelper valueHelper;

    public ValueAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
        valueHelper = new ValueHelper(this);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.NO_COLUMN;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        valueHelper.handleSheetDoc(doc);
    }
}