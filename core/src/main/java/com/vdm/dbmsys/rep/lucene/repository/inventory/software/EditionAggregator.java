package com.vdm.dbmsys.rep.lucene.repository.inventory.software;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.DataHelper;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.Map;

public class EditionAggregator extends BaseSoftwareDocAggregator {

    private DataHelper dataHelper = new DataHelper();

    public EditionAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.EDITION;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        dataHelper.addData(doc.get(Context.Field.MOD_ID), doc.get(Context.Field.EDITION));
    }

    @Override
    public Map<String, String> get() {
        return dataHelper.all();
    }
}
