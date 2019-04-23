package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.LockObtainFailedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

abstract class DataMapper implements Mapper {

    private static final Logger LOGGER = LogManager.getLogger();

    DataMapper() {
        if (!Context.isInited()) {
            Context.init();
        }
    }

    @Override
    public Data find(String id) {
        Data result = UnitOfWork.getCurrent().getClean(id);
        if (nonNull(result)) {
            return result;
        }
        Query findQuery = findQuery(id);
        //todo: move index a reader initializing to one place as it is a very cost operation (p.82)
        List<Data> allData = findAll(findQuery);
        return allData.size() > 0 ? allData.get(0) : null;
    }

    protected abstract IndexWriterConfig indexWriterConfig();

    protected abstract Query findQuery(String id);

    private List<Data> loadAll(ScoreDoc[] scoreDocs, IndexSearcher searcher) throws IOException {
        List<Data> result = new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String id = doc.get(Context.Field.ID);
            Data data = UnitOfWork.getCurrent().getClean(id);
            if (isNull(data)) {
                data = doLoad(id, scoreDocs, searcher);
                UnitOfWork.getCurrent().registerClean(data);
            }
            result.add(data);
        }
        return result;
    }

    protected abstract Data doLoad(String id, ScoreDoc[] scoreDocs, IndexSearcher searcher) throws IOException;

    public List<Data> findAll(Query findQuery) {
        List<Data> result = new ArrayList<>();
        //todo: move index a reader initializing to one place as it is a very cost operation (p.82)
        try {
            for (var indexName : allCreatedIndexNames()) {
                IndexSearcher searcher = Infrastructure.indexSearcher(indexName);
                TopDocs docs = searcher.search(findQuery, 1);
                result.addAll(loadAll(docs.scoreDocs, searcher));
            }
        } catch (IOException ex) {
            //todo: throw an RepException
            LOGGER.warn("We had a problem while opening the templates index: " + ex.getMessage());
        }
        return result;
    }

    protected List<String> allCreatedIndexNames() {
        File[] indexDirs = Context.INDEX_PATH.toFile().listFiles(File::isDirectory);
        return isNull(indexDirs)
            ? Collections.emptyList()
            : Arrays.stream(indexDirs).map(File::getName).collect(Collectors.toList());
    }

    @Override
    public void insert(Data data) {
        try {
            IndexWriter writer = Infrastructure.indexWriter(data.indexName(), indexWriterConfig());
            writer.addDocument(insertDocument(data));
        } catch (LockObtainFailedException ex) {
            LOGGER.error("We had a problem with a lock object: " + ex.getMessage());
        } catch (IOException ex) {
            //todo: throw an RepException
            LOGGER.warn("We had a problem while opening the templates index: " + ex.getMessage());
        }
    }

    protected abstract Document insertDocument(Data subject);

    @Override
    public void update(Data data) {
        try {
            IndexWriter writer = Infrastructure.indexWriter(data.indexName(), indexWriterConfig());
            writer.updateDocument(new Term(Context.Field.ID, data.getId()), updateDocument(data));
        } catch (LockObtainFailedException ex) {
            LOGGER.error("We had a problem with a lock object: " + ex.getMessage());
        }catch (IOException ex) {
            //todo: throw an RepException
            LOGGER.warn("We had a problem while opening the templates index: " + ex.getMessage());
        }
    }

    protected abstract Document updateDocument(Data subject);

    @Override
    public void delete(Data data) {
    }

    @Override
    public void close(Data data) {
        Infrastructure.unregister(data.indexName());
    }
}
