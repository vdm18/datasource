package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.ValuesMinHelper;

import java.util.Map;

public class MinAggregator extends BaseNetworkDocAggregator {

    private ValuesAggregator valuesAggregator;

    public MinAggregator(InventoryReport inventoryReport,
                         ValuesAggregator valuesAggregator) {
        super(inventoryReport);
        this.valuesAggregator = valuesAggregator;
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.MIN;
    }

    @Override
    public Map<String, String> get() {
        ValuesMinHelper valuesMinHelper
            = new ValuesMinHelper(valuesAggregator.getValues());
        return valuesMinHelper.get();
    }

}
