package com.vdm.dbmsys.rep.lucene.repository.inventory.compute;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.AvgHelper;
import org.apache.lucene.document.Document;

import java.util.Map;

public class AvgAggregator extends BaseComputeDocAggregator {

    private AvgHelper avgHelper;

    public AvgAggregator(InventoryReport inventoryReport,
                         CntAggregator cntAggregator,
                         ValueAggregator valueAggregator) {
        super(inventoryReport);
        avgHelper = new AvgHelper(cntAggregator, valueAggregator);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.AVG;
    }

    @Override
    public void handleSheetDoc(Document doc) {
    }

    @Override
    public Map<String, String> get() {
        return avgHelper.avgs();
    }
}
