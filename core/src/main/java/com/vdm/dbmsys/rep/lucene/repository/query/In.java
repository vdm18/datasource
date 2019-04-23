package com.vdm.dbmsys.rep.lucene.repository.query;

import java.io.IOException;

public class In implements Expression {

    private String index;
    private Where where;

    public In(String index, Where where) {
        this.index = index;
        this.where = where;
    }

    @Override
    public void interpret(Context ctx) throws IOException {
        ctx.setIndex(index);
        where.interpret(ctx);
    }
}
