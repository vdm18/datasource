package com.vdm.dbmsys.rep.init.lucene;

import com.vdm.dbmsys.rep.lucene.UnitOfWork;
import com.vdm.dbmsys.rep.lucene.object.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vdm.dbmsys.rep.lucene.Context.JsonElement;

public class Mapping {

    private static final Logger LOGGER = LogManager.getLogger();

    private String mappingJsonFilePath;

    public Mapping(String mappingJsonFilePath) {
        this.mappingJsonFilePath = mappingJsonFilePath;
    }

    /**
     * Parse a Json file.
     */
    private JSONObject parseJSONFile() {
        InputStream mappingJsonFile = getClass().getResourceAsStream(mappingJsonFilePath);
        Reader reader = new InputStreamReader(mappingJsonFile);
        return (JSONObject) JSONValue.parse(reader);
    }

    public void createTemplates() {
        try {
            UnitOfWork.newCurrent();
            JSONObject jsonObject = parseJSONFile();
            createTemplatesFrom(jsonObject);
            UnitOfWork.getCurrent().commit();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private void createTemplatesFrom(JSONObject jsonObjects) {
        JSONArray mappings = (JSONArray) jsonObjects.get(JsonElement.DOCS);
        for (JSONObject object : (List<JSONObject>) mappings) {
            String docValue = (String) object.get(JsonElement.DOC);
            try {
                addTemplateDocs(docValue, (JSONArray) object.get(JsonElement.FIELDS));
            } catch (IOException ex) {
                LOGGER.error("Error adding documents to the index. " + ex.getMessage());
            }
        }
    }

    private void addTemplateDocs(String docValue, JSONArray fieldsArray) throws IOException {
        for (JSONObject fields : (List<JSONObject>) fieldsArray) {
            Template template = Template.createForDoc(docValue);
            for (Map.Entry<String, String> entry : (Set<Map.Entry<String, String>>) fields.entrySet()) {
                Template.addProperty(template, entry.getKey(), entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        final String mappingJsonFilePath = "mappings.json";
        Mapping mapping = new Mapping(mappingJsonFilePath);
        mapping.createTemplates();
    }
}
