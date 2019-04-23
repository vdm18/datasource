package com.vdm.dbmsys.rep.lucene;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Infrastructure {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Directory indexDir(String indexName) {
        Directory dir = UnitOfWork.getCurrent().getDirectory(indexName);
        if (isNull(dir)) {
            try {
                dir = FSDirectory.open(indexPath(indexName));
            } catch (IOException ex) {
                System.err.println("An exception occurred while opening index dir");
            }
            UnitOfWork.getCurrent().registerDirectory(indexName, dir);
        }
        return dir;
    }

    private static Path indexPath(String indexName) {
        Path path = Paths.get(Context.INDEX_PATH.toString(), indexName);
        try {
            if (Files.notExists(path)) {
                Files.createDirectory(path);
            }
        } catch (Exception ex) {
            LOGGER.warn("We had a problem while creating an index dir/reader/writer: " + ex.getMessage());
            path = Context.INDEX_PATH;
        }
        return path;
    }

    public static IndexReader indexReader(String indexName) throws IOException {
        if (isNull(UnitOfWork.getCurrent().getReader(indexName))) {
            UnitOfWork.getCurrent()
                .registerReader(indexName, DirectoryReader.open(indexDir(indexName)));
        }
        return UnitOfWork.getCurrent().getReader(indexName);
    }

    public static IndexWriter indexWriter(String indexName, IndexWriterConfig config) throws IOException {
        if (isNull(UnitOfWork.getCurrent().getWriter(indexName))) {
            UnitOfWork.getCurrent().registerWriter(indexName,
//                new ThreadedIndexWriter(indexDir(indexName), config, 4, 20));
                new IndexWriter(indexDir(indexName), config));
        }
        return UnitOfWork.getCurrent().getWriter(indexName);
    }

    public static IndexSearcher indexSearcher(String indexName) throws IOException {

        if (isNull(UnitOfWork.getCurrent().getSearcher(indexName))) {
            IndexReader reader = Infrastructure.indexReader(indexName);
            UnitOfWork.getCurrent().registerSearcher(indexName, new IndexSearcher(reader));
        }
        return UnitOfWork.getCurrent().getSearcher(indexName);
    }

    public static void unregister(String indexName) {
        UnitOfWork.getCurrent().unregisterSearcher(indexName);
        IndexReader reader = UnitOfWork.getCurrent().unregisterReader(indexName);
        IndexWriter writer = UnitOfWork.getCurrent().unregisterWriter(indexName);
        Directory dir = UnitOfWork.getCurrent().unregisterDirectory(indexName);
        try {
            if (nonNull(reader)) {
                reader.close();
            }
            if (nonNull(writer)) {
                writer.close();
            }
            if (nonNull(dir)) {
                dir.close();
            }
        } catch (IOException ex) {
            LOGGER.warn("We had a problem while closing an index dir/reader/writer: " + ex.getMessage());
        }
    }

}
