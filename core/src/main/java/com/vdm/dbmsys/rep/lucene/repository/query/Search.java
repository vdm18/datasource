package com.vdm.dbmsys.rep.lucene.repository.query;

import java.io.IOException;

public class Search implements Expression {

    private Column[] columns;
    private In in;

    public Search(Column[] columns, In in) {
        this.columns = columns;
        this.in = in;
    }

    @Override
    public void interpret(Context ctx) throws IOException {
        ctx.setColumns(columns);
        in.interpret(ctx);
    }
}
