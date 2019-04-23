package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.Data;
import com.vdm.dbmsys.rep.lucene.object.Info;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;

public class InfoMapper extends DataMapper {

    private IndexWriterConfig iwc;

    public InfoMapper() {
        Analyzer analyzer = new StandardAnalyzer();
        iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setRAMBufferSizeMB(256.0);
    }

    @Override
    protected IndexWriterConfig indexWriterConfig() {
        return iwc;
    }

    @Override
    protected Query findQuery(String id) {
        return null;
    }

    protected Query subjectQuery(String subject) {
        return new TermQuery(new Term(Context.Field.SUBJECT, subject));
    }

    @Override
    protected Data doLoad(String id, ScoreDoc[] scoreDocs, IndexSearcher searcher) throws IOException {
        return null;
    }

    @Override
    protected Document insertDocument(Data data) {
        Document doc = new Document();
        Info info = (Info) data;
        doc.add(new StringField(Context.Field.SUBJECT, info.getSubject(), org.apache.lucene.document.Field.Store.YES));
        doc.add(new StringField(Context.Field.STORAGE, info.getStorage(), org.apache.lucene.document.Field.Store.YES));
        return doc;
    }

    @Override
    protected Document updateDocument(Data subject) {
        return null;
    }
}
