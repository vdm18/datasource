package com.vdm.dbmsys.rep.lucene.repository.inventory.databases;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.DataHelper;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.Map;

public class PlatformAggregator extends BaseDatabasesDocAggregator {

    private DataHelper dataHelper = new DataHelper();

    public PlatformAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.PLATFORM;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        dataHelper.addData(doc.get(Context.Field.MOD_ID), doc.get(Context.Field.ARCHITECTURE));
    }

    @Override
    public Map<String, String> get() {
        return dataHelper.all();
    }
}
