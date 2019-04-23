package com.vdm.dbmsys.rep.lucene.object;

import java.util.HashMap;
import java.util.Map;

public class InventoryReport {

    private Hardware hardware = new Hardware();
    private Software software = new Software();
    private Software databases = new Software();
    private Compute compute = new Compute();
    private Compute memory = new Compute();
    private Compute storage = new Compute();
    private Compute network = new Compute();

    public Hardware getHardware() {
        return hardware;
    }

    public Software getSoftware() {
        return software;
    }

    public Software getDatabases() {
        return databases;
    }

    public Compute getCompute() {
        return compute;
    }

    public Compute getMemory() {
        return memory;
    }

    public Compute getStorage() {
        return storage;
    }

    public Compute getNetwork() {
        return network;
    }

    public void addColumn(Sheet sheet, Column column, String id, String value) {
        switch (sheet) {
            case HARDWARE:
                hardware.addColumnValue(column, id, value);
                break;
            case SOFTWARE:
                software.addColumnValue(column, id, value);
                break;
            case DATABASES:
                databases.addColumnValue(column, id, value);
                break;
            case COMPUTE:
                compute.addColumnValue(column, id, value);
                break;
            case MEMORY:
                memory.addColumnValue(column, id, value);
                break;
            case STORAGE:
                storage.addColumnValue(column, id, value);
                break;
            case NETWORK:
                network.addColumnValue(column, id, value);
                break;
        }
    }

    public enum Sheet {
        HARDWARE,
        SOFTWARE,
        DATABASES,
        COMPUTE,
        MEMORY,
        STORAGE,
        NETWORK
    }

    public enum Column {
        NAME,
        TYPE,
        CPU,
        CORES,
        MEMORY,
        STORAGE,
        NETWORK,
        AGE,
        SCORE,
        FROM,
        TO,
        CNT,
        MIN,
        AVG,
        MAX,
        P99,
        PRODUCT,
        VERSION,
        EDITION,
        RELEASE,
        PLATFORM,
        SUPPORT,
        DATABASES,
        NO_COLUMN
    }

    public static class Hardware {
        private Map<String, String> names = new HashMap<>();
        private Map<String, String> types = new HashMap<>();
        private Map<String, String> cpues = new HashMap<>();
        private Map<String, String> cores = new HashMap<>();
        private Map<String, String> memories = new HashMap<>();
        private Map<String, String> storages = new HashMap<>();
        private Map<String, String> networks = new HashMap<>();
        private Map<String, String> ages = new HashMap<>();
        private Map<String, String> scores = new HashMap<>();

        public Map<String, String> getNames() {
            return names;
        }

        public Map<String, String> getTypes() {
            return types;
        }

        public Map<String, String> getCpues() {
            return cpues;
        }

        public Map<String, String> getCores() {
            return cores;
        }

        public Map<String, String> getMemories() {
            return memories;
        }

        public Map<String, String> getStorages() {
            return storages;
        }

        public Map<String, String> getNetworks() {
            return networks;
        }

        public Map<String, String> getAges() {
            return ages;
        }

        public Map<String, String> getScores() {
            return scores;
        }

        public void addColumnValue(Column column, String id, String value) {
            switch (column) {
                case NAME:
                    names.put(id, value);
                    break;
                case TYPE:
                    types.put(id, value);
                    break;
                case CPU:
                    cpues.put(id, value);
                    break;
                case CORES:
                    cores.put(id, value);
                    break;
                case MEMORY:
                    memories.put(id, value);
                    break;
                case STORAGE:
                    storages.put(id, value);
                    break;
                case NETWORK:
                    networks.put(id, value);
                    break;
                case AGE:
                    ages.put(id, value);
                    break;
                case SCORE:
                    scores.put(id, value);
                    break;
                default:
                    break;
            }
        }
    }

    public static class Databases {
        private Map<String, String> names = new HashMap<>();
        private Map<String, String> products = new HashMap<>();
        private Map<String, String> versions = new HashMap<>();
        private Map<String, String> editions = new HashMap<>();
        private Map<String, String> releases = new HashMap<>();
        private Map<String, String> platforms = new HashMap<>();
        private Map<String, String> supports = new HashMap<>();
        private Map<String, String> databases = new HashMap<>();

        public Map<String, String> getNames() {
            return names;
        }

        public Map<String, String> getProducts() {
            return products;
        }

        public Map<String, String> getVersions() {
            return versions;
        }

        public Map<String, String> getEditions() {
            return editions;
        }

        public Map<String, String> getReleases() {
            return releases;
        }

        public Map<String, String> getPlatforms() {
            return platforms;
        }

        public Map<String, String> getSupports() {
            return supports;
        }

        public Map<String, String> getDatabases() {
            return databases;
        }

        public void addColumnValue(Column column, String id, String value) {
            switch (column) {
                case NAME:
                    names.put(id, value);
                    break;
                case PRODUCT:
                    products.put(id, value);
                    break;
                case VERSION:
                    versions.put(id, value);
                    break;
                case EDITION:
                    editions.put(id, value);
                    break;
                case RELEASE:
                    releases.put(id, value);
                    break;
                case PLATFORM:
                    platforms.put(id, value);
                    break;
                case SUPPORT:
                    supports.put(id, value);
                    break;
                case DATABASES:
                    databases.put(id, value);
                    break;
                default:
                    break;
            }
        }
    }


    public static class Software {
        private Map<String, String> names = new HashMap<>();
        private Map<String, String> products = new HashMap<>();
        private Map<String, String> versions = new HashMap<>();
        private Map<String, String> editions = new HashMap<>();
        private Map<String, String> releases = new HashMap<>();
        private Map<String, String> platforms = new HashMap<>();
        private Map<String, String> supports = new HashMap<>();

        public Map<String, String> getNames() {
            return names;
        }

        public Map<String, String> getProducts() {
            return products;
        }

        public Map<String, String> getVersions() {
            return versions;
        }

        public Map<String, String> getEditions() {
            return editions;
        }

        public Map<String, String> getReleases() {
            return releases;
        }

        public Map<String, String> getPlatforms() {
            return platforms;
        }

        public Map<String, String> getSupports() {
            return supports;
        }

        public void addColumnValue(Column column, String id, String value) {
            switch (column) {
                case NAME:
                    names.put(id, value);
                    break;
                case PRODUCT:
                    products.put(id, value);
                    break;
                case VERSION:
                    versions.put(id, value);
                    break;
                case EDITION:
                    editions.put(id, value);
                    break;
                case RELEASE:
                    releases.put(id, value);
                    break;
                case PLATFORM:
                    platforms.put(id, value);
                    break;
                case SUPPORT:
                    supports.put(id, value);
                    break;
                default:
                    break;
            }
        }
    }

    public static class Compute {
        private Map<String, String> names = new HashMap<>();
        private Map<String, String> from = new HashMap<>();
        private Map<String, String> to = new HashMap<>();
        private Map<String, String> cnt = new HashMap<>();
        private Map<String, String> min = new HashMap<>();
        private Map<String, String> avg = new HashMap<>();
        private Map<String, String> max = new HashMap<>();
        private Map<String, String> p99 = new HashMap<>();

        public Map<String, String> getNames() {
            return names;
        }

        public Map<String, String> getFrom() {
            return from;
        }

        public Map<String, String> getTo() {
            return to;
        }

        public Map<String, String> getCnt() {
            return cnt;
        }

        public Map<String, String> getMin() {
            return min;
        }

        public Map<String, String> getAvg() {
            return avg;
        }

        public Map<String, String> getMax() {
            return max;
        }

        public Map<String, String> getP99() {
            return p99;
        }

        public void addColumnValue(Column column, String id, String value) {
            switch (column) {
                case NAME:
                    names.put(id, value);
                    break;
                case FROM:
                    from.put(id, value);
                    break;
                case TO:
                    to.put(id, value);
                    break;
                case CNT:
                    cnt.put(id, value);
                    break;
                case MIN:
                    min.put(id, value);
                    break;
                case AVG:
                    avg.put(id, value);
                    break;
                case MAX:
                    max.put(id, value);
                    break;
                case P99:
                    p99.put(id, value);
                    break;
                default:
                    break;
            }
        }
    }
}
