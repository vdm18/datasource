package com.vdm.dbmsys.rep.lucene.repository.inventory.hardware;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

public class StorageAggregator extends BaseHardwareDocAggregator {

    public StorageAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.STORAGE;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        BaseDocAggregator.Value value = BaseDocAggregator.Value.of(doc.get(Context.Field.CAPACITY), doc.get(Context.Field.STOPPED));
        aggregate(doc.get(Context.Field.MOD_ID), value, BaseDocAggregator.SUM_OF_LAST);
    }
}
