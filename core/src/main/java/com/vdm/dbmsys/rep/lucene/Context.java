package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.init.lucene.Mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Context {

    public interface JsonElement {
        String DOCS = "docs";
        String DOC = "doc";
        String FIELDS = "fields";
    }

    public interface Field {
        String ID = "id";
        String DOC = "doc";
        String FIELD = "field";
        String TYPE = "type";
        String STORE = "store";
        String INDEX = "index";
        String STORAGE = "Storage";
        String SUBJECT = "Info";
    }

    public interface DataRecordElement {
        String STORAGE = "Storage";
    }

    public interface DocId {
        String DEFAULT = "default";
    }

    public interface FieldId {
        String DEFAULT = "default";
    }

    private static final String MAPPING_JSON_FILE_PATH = "mappings.json";
    public static final Path INDEX_PATH = Paths.get(System.getProperty("user.dir"), "indexes");
    public static final String EMPTY = "";
    public static final String MAPPING = "mapping";

    static {
        try {
            if (Files.notExists(INDEX_PATH)) {
                Files.createDirectory(INDEX_PATH);
            }
        } catch (IOException ex) {
            System.err.println("We had a problem creating the folder: " + ex.getMessage());
        }
    }

    private static boolean isInited = false;

    static void init() {
        isInited = true;
        Mapping mapping = new Mapping(MAPPING_JSON_FILE_PATH);
        mapping.createTemplates();
    }

    static boolean isInited() {
        return isInited;
    }
}
