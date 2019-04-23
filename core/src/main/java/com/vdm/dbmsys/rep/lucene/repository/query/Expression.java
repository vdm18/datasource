package com.vdm.dbmsys.rep.lucene.repository.query;

import java.io.IOException;

public interface Expression {
    void interpret(Context ctx) throws IOException;
}
