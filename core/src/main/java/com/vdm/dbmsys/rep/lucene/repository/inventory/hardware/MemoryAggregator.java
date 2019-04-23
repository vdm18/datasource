package com.vdm.dbmsys.rep.lucene.repository.inventory.hardware;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

public class MemoryAggregator extends BaseHardwareDocAggregator {

    public MemoryAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.MEMORY;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        Value value =
            Value.of(doc.get(Context.Field.TOTAL_VISIBLE_MEMORY_SIZE), doc.get(Context.Field.STOPPED));
        aggregate(doc.get(Context.Field.MOD_ID), value, SUM_OF_LAST);
    }
}
