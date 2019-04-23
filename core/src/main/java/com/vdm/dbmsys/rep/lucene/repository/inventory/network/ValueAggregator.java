package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;

import java.util.Map;

public class ValueAggregator extends BaseNetworkDocAggregator {

    private ValuesAggregator valuesAggregator;

    public ValueAggregator(InventoryReport inventoryReport,
                           ValuesAggregator valuesAggregator) {
        super(inventoryReport);
        this.valuesAggregator = valuesAggregator;
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.NO_COLUMN;
    }

    @Override
    public Map<String, String> get() {
        valuesAggregator.getValues().entrySet().stream()
            .forEach(entry -> entry.getValue().stream()
                .forEach(value -> aggregate(entry.getKey(), value, VAL)));
        return super.get();
    }
}
