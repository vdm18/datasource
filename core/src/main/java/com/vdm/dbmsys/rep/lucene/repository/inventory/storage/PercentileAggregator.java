package com.vdm.dbmsys.rep.lucene.repository.inventory.storage;

import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.common.PercentileHelper;

import java.util.Map;

public class PercentileAggregator extends BaseStorageDocAggregator {

    private ValuesAggregator valuesAggregator;

    public PercentileAggregator(InventoryReport inventoryReport,
                                ValuesAggregator valuesAggregator) {
        super(inventoryReport);
        this.valuesAggregator = valuesAggregator;
    }

    @Override
    public InventoryReport.Column column() {
        return InventoryReport.Column.P99;
    }

    @Override
    public Map<String, String> get() {
        PercentileHelper percentileHelper
            = new PercentileHelper(valuesAggregator.getValues());
        return percentileHelper.percentiles();
    }
}