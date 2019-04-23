package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.Data;
import com.vdm.dbmsys.rep.lucene.object.Template;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class TemplateMapper extends DataMapper {

    private IndexWriterConfig iwc;

    public TemplateMapper() {
        Analyzer analyzer = new StandardAnalyzer();
        iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        iwc.setRAMBufferSizeMB(256.0);
    }

    @Override
    protected IndexWriterConfig indexWriterConfig() {
        return iwc;
    }

    @Override
    protected Query findQuery(String id) {
        Term t = new Term(Context.Field.ID, id);
        return new TermQuery(t);
    }

    public Template find(String id) {
        return (Template) super.find(id);
    }

    private Query docQuery(String doc) {
        Term t = new Term(Context.Field.DOC, doc);
        return new TermQuery(t);
    }

    public List<Template> docFind(String doc) {
        List<Data> allData = super.findAll(docQuery(doc));
        return allData.stream()
            .map(data -> (Template) data)
            .collect(Collectors.toList());
    }

    public Template findDefault(String fieldId) {
        Data result = UnitOfWork.getCurrent().getDefaultFieldData(fieldId);
        if (nonNull(result)) {
            return (Template) result;
        }
        List<Data> allData = super.findAll(docQuery(Context.DocId.DEFAULT));
        allData.stream()
            .map(data -> (Template) data)
            .forEach(template -> UnitOfWork.getCurrent().registerDefaultFieldData(template.getField(), template));
        return (Template) UnitOfWork.getCurrent().getDefaultFieldData(fieldId);
    }

    public Template findDefault() {
        return findDefault(Context.FieldId.DEFAULT);
    }

    @Override
    protected Template doLoad(String id, ScoreDoc[] scoreDocs, IndexSearcher searcher) throws IOException {
        Template template = new Template();
        template.setId(id);
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            for (IndexableField field : doc.getFields()) {
                if (Context.Field.ID.equals(field.name())) {
                    continue;
                }
                template.addProperty(field.name(), field.stringValue());
            }
        }
        return template;
    }

    @Override
    protected Document insertDocument(Data subject) {
        return createDoc((Template) subject);
    }

    @Override
    protected Document updateDocument(Data subject) {
        return createDoc((Template) subject);
    }

    private Document createDoc(Template template) {
        Document doc = new Document();
        doc.add(new StringField(Context.Field.ID, template.getId(), org.apache.lucene.document.Field.Store.YES));
        template.getProperties().entrySet().stream()
            .map(mapEntry -> new StringField(mapEntry.getKey(), mapEntry.getValue(), org.apache.lucene.document.Field.Store.YES))
            .forEach(doc::add);
        return doc;
    }

    @Override
    protected List<String> allCreatedIndexNames() {
        return Arrays.asList(Context.MAPPING);
    }

    public static void main(String[] args) {
        final String valueId = "Tests.VM_TST_DBMILimitingFeatures1";
        TemplateMapper mapper = new TemplateMapper();
        Template template = mapper.find(valueId);
        System.out.println(template.getId());
    }
}
