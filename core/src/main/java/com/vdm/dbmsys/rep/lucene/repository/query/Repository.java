package com.vdm.dbmsys.rep.lucene.repository.query;

import java.io.IOException;

public class Repository {
    protected void run(Expression query) throws IOException {
        query.interpret(new Context());
    }
}
