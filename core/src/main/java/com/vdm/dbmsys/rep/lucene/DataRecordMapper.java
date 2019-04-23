package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.Data;
import com.vdm.dbmsys.rep.lucene.object.DataRecord;
import com.vdm.dbmsys.rep.lucene.object.Template;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class DataRecordMapper extends DataMapper {

    private IndexWriterConfig iwc;
    private TemplateMapper templateMapper = new TemplateMapper();

    public DataRecordMapper() {
        Analyzer analyzer = new StandardAnalyzer();
        iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setRAMBufferSizeMB(256.0);
//        iwc.setRAMBufferSizeMB(512.0);
//        iwc.setMaxBufferedDocs(IndexWriterConfig.DISABLE_AUTO_FLUSH);
////        iwc.setCodec(new Lucene70Codec());
//        iwc.setCodec(new Lucene70Codec(Lucene50StoredFieldsFormat.Mode.BEST_COMPRESSION));
    }

    @Override
    protected IndexWriterConfig indexWriterConfig() {
        return iwc;
    }

    @Override
    protected Query findQuery(String id) {
        return null;
    }

    @Override
    protected DataRecord doLoad(String id, ScoreDoc[] scoreDocs, IndexSearcher searcher) throws IOException {
        return null;
    }

    @Override
    protected Document insertDocument(Data data) {
        Document doc = new Document();
        DataRecord dataRecord = (DataRecord) data;
        doc.add(new StringField(Context.Field.ID, dataRecord.getId(), Field.Store.YES));
        String docFieldValue = dataRecord.getHeadElement(Context.DataRecordElement.STORAGE);
        List<Template> templates = templateMapper.docFind(docFieldValue);
        Stream.concat(dataRecord.getHead().entrySet().stream(), dataRecord.getBody().entrySet().stream())
            .map(record -> getFields(record, templates))
            .flatMap(Collection::stream)
            .forEach(doc::add);
        return doc;
    }

    private List<Field> getFields(Map.Entry<String, String> record, List<Template> templates) {
        String name = record.getKey();
        String value = record.getValue();
        try {
            Map<String, Map<String, String>> templatesProperties = templates.stream()
                .collect(toMap(Template::getField, Template::getProperties));
            Map<String, String> properties = templatesProperties.get(name);
            Map<String, String> defaultProperties = Optional.ofNullable(templateMapper.findDefault(name))
                .orElse(templateMapper.findDefault()).getProperties();
            return FieldsBuilder.builder()
                .name(name)
                .value(value)
                .properties(properties)
                .defaultProperties(defaultProperties)
                .build();
        } catch(Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    protected Document updateDocument(Data subject) {
        return null;
    }
}
