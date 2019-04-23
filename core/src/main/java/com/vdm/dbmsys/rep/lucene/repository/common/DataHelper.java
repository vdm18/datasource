package com.vdm.dbmsys.rep.lucene.repository.common;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class DataHelper {

    private Set<Data> allData = new HashSet<>();

    public void addData(String modId, String data) {
        if (nonNull(data)) {
            allData.add(new Data(modId, data));
        }
    }

    public Map<String, String> all() {
        return allData.stream()
            .collect(toMap(Data::getMobId, Data::getData));
    }

    private static class Data {
        private String mobId;
        private String data;

        Data(String mobId, String data) {
            this.mobId = mobId;
            this.data = data;
        }

        String getMobId() {
            return mobId;
        }

        String getData() {
            return data;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Data)) {
                return false;
            }
            Data anotherName = (Data) obj;
            return getMobId().equals(anotherName.getMobId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getMobId());
        }
    }
}
