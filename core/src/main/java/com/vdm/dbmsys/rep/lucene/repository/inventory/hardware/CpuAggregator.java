package com.vdm.dbmsys.rep.lucene.repository.inventory.hardware;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.HashMap;
import java.util.Map;

public class CpuAggregator extends BaseHardwareDocAggregator {

    private Map<String, String> cpu = new HashMap<>();

    public CpuAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.CPU;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        cpu.put(doc.get(Context.Field.MOD_ID), doc.get(Context.Field.NAME));
    }

    public Map<String, String> get() {
        return cpu;
    }
}
