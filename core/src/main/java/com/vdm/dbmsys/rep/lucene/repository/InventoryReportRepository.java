package com.vdm.dbmsys.rep.lucene.repository;

import com.vdm.dbmsys.rep.lucene.UnitOfWork;
import com.vdm.dbmsys.rep.lucene.object.InventoryReport;
import com.vdm.dbmsys.rep.lucene.repository.inventory.compute.ComputeRepository;
import com.vdm.dbmsys.rep.lucene.repository.inventory.databases.DatabasesRepository;
import com.vdm.dbmsys.rep.lucene.repository.inventory.hardware.HardwareRepository;
import com.vdm.dbmsys.rep.lucene.repository.inventory.memory.MemoryRepository;
import com.vdm.dbmsys.rep.lucene.repository.inventory.network.NetworkRepository;
import com.vdm.dbmsys.rep.lucene.repository.inventory.software.SoftwareRepository;
import com.vdm.dbmsys.rep.lucene.repository.inventory.storage.StorageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InventoryReportRepository {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String MIN_SUFFIX = ".0000000-00:00";
    private static final String MAX_SUFFIX = ".9999999-99:99";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:dd");

    public InventoryReport get(LocalDateTime since, LocalDateTime till) {
        String from = since.format(formatter) + MIN_SUFFIX;
        String to = till.format(formatter) + MAX_SUFFIX;
        InventoryReport report = new InventoryReport();
        HardwareRepository hardware = new HardwareRepository(report);
        SoftwareRepository software = new SoftwareRepository(report);
        DatabasesRepository databases = new DatabasesRepository(report);
        ComputeRepository compute = new ComputeRepository(report);
        MemoryRepository memory = new MemoryRepository(report, hardware.memory());
        StorageRepository storage = new StorageRepository(report);
        NetworkRepository network = new NetworkRepository(report);
        UnitOfWork.newCurrent();
        hardware.in(from, to);
        software.in(from, to);
        databases.in(from, to);
        compute.in(from, to);
        memory.in(from, to);
        storage.in(from, to);
        network.in(from, to);
        UnitOfWork.getCurrent().clear();
        return report;
    }
}
