package com.vdm.dbmsys.rep.lucene.repository;

public class Context {
    public interface Field {
        String MOD_ID = "_mob_id";
        String ADDRESS = "Address";
        String SUBJECT = "Subject";
        String MANUFACTURER = "Manufacturer";
        String MODEL = "Model";
        String NAME = "Name";
        String NUMBER_OF_CORES = "NumberOfCores";
        String TOTAL_VISIBLE_MEMORY_SIZE = "TotalVisibleMemorySize";
        String CAPACITY = "Capacity";
        String CATEGORY = "Category";
        String COUNTER = "Counter";
        String VALUE = "Value";
        String STOPPED = "Stopped";
        String PRODUCT = "Product";
        String INSTANCE = "Instance";
        String CAPTION = "Caption";
        String VERSION = "Version";
        String EDITION = "Edition";
        String CSD_VERSION = "CSDVersion";
        String OS_ARCHITECTURE = "OSArchitecture";
        String INSTANCE_NAME = "InstanceName";
        String VERSION_NAME = "VersionName";
        String PRODUCT_LEVEL = "ProductLevel";
        String ARCHITECTURE = "Architecture";
        String STORAGE = "Storage";
    }

    public interface Value {
        String ZERO = "0";
        String PERFORMANCE_COUNTERS = "Performance Counters (10 Minute Interval)";
        String DISK_PERFORMANCE_COUNTERS = "Disk Performance Counters";
        String LOGICAL_DISK = "LogicalDisk";
        String PERCENTS_OF_FREE_SPACE = "% Free Space";
        String TOTAL = "_Total";
        String NETWORK_INTERFACE = "Network Interface";
        String MEMORY = "Memory";
        String AVAILABLE_MBYTES = "Available MBytes";
        String SYSTEM_DIAGNOSTICS_PERF_COUNTER_LIB = "systemdiagnosticsperfcounterlibsingleinstance";
        String WINDOWS_DISK_DRIVES = "Windows Disk Drives";
        String MICROSOFT_WINDOWS = "Microsoft Windows";
        String WINDOWS_OS_DETAILS = "Windows OS Details";
        String CURRENT_BANDWIDTH = "Current Bandwidth";
        String BASE_WINDOWS_COLLECTION = "Base Windows collection";
        String SERVER_MACHINE_DETAILS = "Server Machine Details";
        String WINDOWS_PROCESSORS = "Windows Processors";
        String PROCESSOR = "Processor";
        String PERCENTS_OF_PROCESSOR_TIME = "% Processor Time";
        String BASE_SQL_COLLECTION = "Base SQL collection";
        String DATABASE_INSTANCE_DETAILS = "Database Instance Details";
        String INSTANCE_DATABASES = "Instance Databases";
        String BYTES_SENT = "Bytes Sent/sec";
        String BYTES_RECEIVED = "Bytes Received/sec";
        String TESTS_VW_TST_SERVER_MACHINE_DETAILS = "Tests.VW_TST_ServerMachineDetails";
        String TESTS_VW_TST_PROCESSORS = "Tests.VW_TST_Processors";
        String TESTS_VW_TST_OPERATING_SYSTEM = "Tests.VW_TST_OperatingSystem";
        String TESTS_VW_TST_WINDOWS_VOLUMES = "Tests.VW_TST_WindowsVolumes";
        String COLLECT_VW_TST_PERFORMANCE_COUNTERS = "Collect.VW_TST_PerformanceCounters";
        String TESTS_VW_TST_BASE_SQL_COLLECTION = "Tests.VW_TST_BaseSQLcollection";
        String TESTS_VW_TST_DATABASE_INSTANCE_DETAILS = "Tests.VW_TST_DatabaseInstanceDetails";
        String TESTS_VW_TST_INSTANCE_DATABASES = "Tests.VW_TST_InstanceDatabases";
    }

}
