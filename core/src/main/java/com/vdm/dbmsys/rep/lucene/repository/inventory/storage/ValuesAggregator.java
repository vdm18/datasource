package com.vdm.dbmsys.rep.lucene.repository.inventory.storage;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValuesHelper;
import org.apache.lucene.document.Document;

import java.util.List;
import java.util.Map;

public class ValuesAggregator extends BaseStorageDocAggregator {

    private ValuesHelper valuesHelper = new ValuesHelper();

    public ValuesAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.NO_COLUMN;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        valuesHelper.handleSheetDoc(doc);
    }

    Map<String, List<Value>> getValues() {
        return valuesHelper.get(new ValueBuilder());
    }
}
