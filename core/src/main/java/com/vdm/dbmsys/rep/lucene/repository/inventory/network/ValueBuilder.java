package com.vdm.dbmsys.rep.lucene.repository.inventory.network;

import com.vdm.dbmsys.rep.lucene.repository.BaseDocAggregator;

import java.util.Map;
import java.util.function.BiFunction;

public class ValueBuilder implements BiFunction<String, BaseDocAggregator.Value, BaseDocAggregator.Value> {

    private Map<String, Map<String, BaseDocAggregator.Value>> bytesSent;
    private Map<String, Map<String, BaseDocAggregator.Value>> bytesReceived;

    public ValueBuilder(Map<String, Map<String, BaseDocAggregator.Value>> bytesSent,
                        Map<String, Map<String, BaseDocAggregator.Value>> bytesReceived) {
        this.bytesSent = bytesSent;
        this.bytesReceived = bytesReceived;
    }

    @Override
    public BaseDocAggregator.Value apply(String modId, BaseDocAggregator.Value value) {
        try {
            Map<String, BaseDocAggregator.Value> sentValues = bytesSent.get(modId);
            Map<String, BaseDocAggregator.Value> receivedValues = bytesReceived.get(modId);
            BaseDocAggregator.Value sent = sentValues.getOrDefault(value.getDate(), BaseDocAggregator.Value.ZERO);
            BaseDocAggregator.Value received = receivedValues.getOrDefault(value.getDate(), BaseDocAggregator.Value.ZERO);
            return BaseDocAggregator.Value.of(calculate(received.getValue(), sent.getValue(), value.getValue()),
                value.getDate());
        } catch (Exception ex) {
            return BaseDocAggregator.Value.ZERO.withDate(value.getDate());
        }
    }

    private String calculate(String received, String sent, String bandwidth) {
        return String.valueOf(
            Math.max(Double.valueOf(received), Double.valueOf(sent)) * 100 / Double.valueOf(bandwidth));
    }
}