package com.vdm.dbmsys.rep.lucene.repository.inventory.memory;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValuesHelper;
import com.vdm.dbmsys.rep.lucene.repository.inventory.hardware.MemoryAggregator;
import org.apache.lucene.document.Document;

import java.util.List;
import java.util.Map;

public class ValuesAggregator extends BaseMemoryDocAggregator {

    private ValuesHelper valuesHelper = new ValuesHelper();
    private MemoryAggregator memoryAggregator;

    public ValuesAggregator(InventoryReport inventoryReport,
                            MemoryAggregator memoryAggregator) {
        super(inventoryReport);
        this.memoryAggregator = memoryAggregator;
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
        return valuesHelper.get(new ValueBuilder(memoryAggregator.get()));
    }
}
