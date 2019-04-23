package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValuesHelper;
import org.apache.lucene.document.Document;

import java.util.List;
import java.util.Map;

public class ValuesAggregator extends BaseNetworkDocAggregator {

    private ValuesHelper valuesHelper = new ValuesHelper();
    private BytesSentValuesAggregator bytesSent;
    private BytesReceivedValuesAggregator bytesReceived;

    public ValuesAggregator(InventoryReport inventoryReport,
                            BytesSentValuesAggregator bytesSent,
                            BytesReceivedValuesAggregator bytesReceived) {
        super(inventoryReport);
        this.bytesSent = bytesSent;
        this.bytesReceived = bytesReceived;
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
        return valuesHelper.get(
            new ValueBuilder(
                bytesSent.getValues(),
                bytesReceived.getValues()
            ));
    }
}
