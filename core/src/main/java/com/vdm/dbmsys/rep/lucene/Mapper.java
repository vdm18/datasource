package com.vdm.dbmsys.rep.lucene;

import com.vdm.dbmsys.rep.lucene.object.Data;

public interface Mapper {
    Data find(String id);

    void insert(Data data);

    void update(Data data);

    void delete(Data data);

    void close(Data data);
}
