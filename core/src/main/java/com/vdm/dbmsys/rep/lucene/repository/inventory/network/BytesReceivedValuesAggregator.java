package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.DatedValuesHelper;
import org.apache.lucene.document.Document;

import java.util.Map;

public class BytesReceivedValuesAggregator extends BaseNetworkDocAggregator {

    private DatedValuesHelper valuesHelper = new DatedValuesHelper();

    public BytesReceivedValuesAggregator(InventoryReport inventoryReport) {
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

    Map<String, Map<String, Value>> getValues() {
        return valuesHelper.get();
    }
}