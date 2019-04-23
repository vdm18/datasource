package com.vdm.dbmsys.rep.lucene.repository.inventory.hardware;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.Context;
import org.apache.lucene.document.Document;

import java.util.HashMap;
import java.util.Map;

public class TypeAggregator extends BaseHardwareDocAggregator {

    private static final String[] VM_POSSIBLE_HINT = {"virtual", "vm", "xen"};
    private static final String PM = "PM";
    private static final String VM = "VM";

    private Map<String, String> types = new HashMap<>();

    public TypeAggregator(InventoryReport inventoryReport) {
        super(inventoryReport);
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.TYPE;
    }

    @Override
    public void handleSheetDoc(Document doc) {
        String type = isVM(doc.get(Context.Field.MANUFACTURER).toLowerCase(),
            doc.get(Context.Field.MODEL).toLowerCase()) ? VM : PM;
        types.put(doc.get(Context.Field.MOD_ID), type);
    }

    private Boolean isVM(String manufacture, String model) {
        for (var hint : VM_POSSIBLE_HINT) {
            if (manufacture.contains(hint) || model.contains(hint)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> get() {
        return types;
    }
}
