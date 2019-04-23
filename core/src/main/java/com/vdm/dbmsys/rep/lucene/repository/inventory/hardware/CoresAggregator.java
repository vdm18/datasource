package com.vdm.dbmsys.rep.lucene.repository.inventory.hardware;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

public class CoresAggregator extends BaseHardwareDocAggregator {

    public CoresAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.CORES;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        BaseDocAggregator.Value value = BaseDocAggregator.Value.of(doc.get(Context.Field.NUMBER_OF_CORES), doc.get(Context.Field.STOPPED));
        aggregate(doc.get(Context.Field.MOD_ID), value, BaseDocAggregator.SUM_OF_LAST);
    }
}
