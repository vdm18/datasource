package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitOfWork {

    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, Data> cleanData = new HashMap<>();
    private Map<String, Data> defaultFieldData = new HashMap<>();
    private List<Data> newData = new ArrayList<>();
    private List<Data> dirtyData = new ArrayList<>();
    private List<Data> removedData = new ArrayList<>();
    private Map<String, Directory> directories = new HashMap<>();
    private Map<String, IndexWriter> writers = new HashMap<>();
    private Map<String, IndexReader> readers = new HashMap<>();
    private Map<String, IndexSearcher> searchers = new HashMap<>();

    private static ThreadLocal current = new ThreadLocal();

    public static void newCurrent() {
        setCurrent(new UnitOfWork());
    }

    public static void setCurrent(UnitOfWork uow) {
        current.set(uow);
    }

    public static UnitOfWork getCurrent() {
        return (UnitOfWork) current.get();
    }

    public void registerNew(Data data) {
        newData.add(data);
    }

    public void registerDirty(Data data) {
        if (!dirtyData.contains(data) && !newData.contains(data)) {
            dirtyData.add(data);
        }
    }

    public void registerRemoved(Data data) {
        if (newData.remove(data)) {
            return;
        }
        dirtyData.remove(data);
        if (!removedData.contains(data)) {
            removedData.add(data);
        }
    }

    public void registerClean(Data data) {
        cleanData.put(data.getId(), data);
    }

    public Data getClean(String id) {
        return cleanData.get(id);
    }

    public void registerDefaultFieldData(String field, Data data) {
        defaultFieldData.put(field, data);
    }

    public Data getDefaultFieldData(String field) {
        return defaultFieldData.get(field);
    }

    public void commit() throws IOException {
        insertNew();
        updateDirty();
        deleteRemoved();
        //todo: look for better solution aka reader/writer commits, p.68
        close();
        clearData();
    }

    private void insertNew() {
        newData
            .forEach(data -> MapperRegistry.getMapper(data.getClass()).insert(data));
    }

    private void updateDirty() {
        dirtyData
            .forEach(data -> MapperRegistry.getMapper(data.getClass()).update(data));
    }

    private void deleteRemoved() {
        removedData
            .forEach(data -> MapperRegistry.getMapper(data.getClass()).delete(data));
    }

    private void close() {
        newData
            .forEach(data -> MapperRegistry.getMapper(data.getClass()).close(data));
        dirtyData
            .forEach(data -> MapperRegistry.getMapper(data.getClass()).close(data));
        removedData
            .forEach(data -> MapperRegistry.getMapper(data.getClass()).close(data));
    }

    public void clear() {
        clearData();
        writers.entrySet().forEach(writer -> close(writer.getValue()));
        readers.entrySet().forEach(reader -> close(reader.getValue()));
        directories.entrySet().forEach(directory -> close(directory.getValue()));
    }

    private void clearData() {
        cleanData.clear();
        defaultFieldData.clear();
        newData.clear();
        dirtyData.clear();
        removedData.clear();
    }

    private void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException ex) {
            LOGGER.error("We had a problem while closing: " + ex.getMessage());
        }
    }

    public IndexWriter getWriter(String indexName) {
        return writers.get(indexName);
    }

    public void registerWriter(String indexName, IndexWriter writer) {
        writers.put(indexName, writer);
    }

    public IndexWriter unregisterWriter(String indexName) {
        return writers.remove(indexName);
    }


    public IndexReader getReader(String indexName) {
        return readers.get(indexName);
    }

    public void registerReader(String indexName, IndexReader reader) {
        readers.put(indexName, reader);
    }

    public IndexReader unregisterReader(String indexName) {
        return readers.remove(indexName);
    }

    public IndexSearcher getSearcher(String indexName) {
        return searchers.get(indexName);
    }

    public void registerSearcher(String indexName, IndexSearcher reader) {
        searchers.put(indexName, reader);
    }

    public IndexSearcher unregisterSearcher(String indexName) {
        return searchers.remove(indexName);
    }

    public Directory getDirectory(String indexName) {
        return directories.get(indexName);
    }

    public void registerDirectory(String indexName, Directory dir) {
        directories.put(indexName, dir);
    }

    public Directory unregisterDirectory(String indexName) {
        return directories.remove(indexName);
    }

}
