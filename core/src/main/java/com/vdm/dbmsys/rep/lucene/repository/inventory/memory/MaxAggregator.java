package com.vdm.dbmsys.rep.lucene.repository.inventory.memory;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValuesMaxHelper;

import java.util.Map;

public class MaxAggregator extends BaseMemoryDocAggregator {

    private ValuesAggregator valuesAggregator;

    public MaxAggregator(InventoryReport inventoryReport,
                         ValuesAggregator valuesAggregator) {
        super(inventoryReport);
        this.valuesAggregator = valuesAggregator;
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.MAX;
    }

    @Override
    public Map<String, String> get() {
        ValuesMaxHelper valuesMaxHelper
            = new ValuesMaxHelper(valuesAggregator.getValues());
        return valuesMaxHelper.get();
    }
}