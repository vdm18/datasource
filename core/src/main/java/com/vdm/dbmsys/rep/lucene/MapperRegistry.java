package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.DataRecord;
import com.vdm.dbmsys.rep.lucene.object.Template;

public class MapperRegistry {
    public static DataMapper getMapper(Class clazz) {
        if(Template.class == clazz) {
            return new TemplateMapper();
        } else if(DataRecord.class == clazz) {
            return new DataRecordMapper();
        } else {
            //todo: update default behaviour
            return new DataRecordMapper();
        }
    }
}
