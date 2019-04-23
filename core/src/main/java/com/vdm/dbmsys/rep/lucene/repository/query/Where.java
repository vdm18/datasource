package com.vdm.dbmsys.rep.lucene.repository.query;

import java.io.IOException;

public class Where implements Expression {

    private Criteria criteria;

    public Where(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public void interpret(Context ctx) throws IOException {
        ctx.setCriterias(criteria);
        ctx.search();
    }
}
