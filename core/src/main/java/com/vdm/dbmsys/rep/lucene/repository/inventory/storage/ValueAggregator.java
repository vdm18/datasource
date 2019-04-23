package com.vdm.dbmsys.rep.lucene.repository.inventory.storage;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValuesHelper;
import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;
import org.apache.lucene.document.Document;

import java.util.List;
import java.util.Map;

public class ValueAggregator extends BaseStorageDocAggregator {

    private ValuesHelper valuesHelper = new ValuesHelper();

    public ValueAggregator(InventoryReport inventoryReport) {
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

    @Override
    public Map<String, String> get() {
        Map<String, List<BaseDocAggregator.Value>> values = valuesHelper.get(new ValueBuilder());
        values.entrySet().stream()
            .forEach(entry -> entry.getValue().stream()
                .forEach(value -> aggregate(entry.getKey(), value, BaseDocAggregator.VAL)));
        return super.get();
    }
}