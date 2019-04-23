package com.vdm.dbmsys.rep.lucene.repository.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

public class Criteria {

    private static int FIELD = 0;
    private static int VALUE = 1;
    private static int FROM = 1;
    private static int TO = 2;

    public enum Type {
        TERM,
        TERM_RANGE,
        AND,
        OR,
        ALL
    }

    private Type type;
    private String[] args;
    private Criteria first;
    private Criteria second;

    public Criteria(Type type, String... args) {
        this.type = type;
        this.args = args;
    }

    public Criteria(Type type, Criteria first, Criteria second) {
        this.type = type;
        this.first = first;
        this.second = second;
    }

    public static Criteria term(String field, String value) {
        return new Criteria(Type.TERM, field, value);
    }

    public static Criteria termRange(String field, String from, String to) {
        return new Criteria(Type.TERM_RANGE, field, from, to);
    }

    public static Criteria and(Criteria first, Criteria second) {
        return new Criteria(Type.AND, first, second);
    }

    public static Criteria or(Criteria first, Criteria second) {
        return new Criteria(Type.OR, first, second);
    }

    public static Criteria all() {
        return new Criteria(Type.ALL);
    }

    public Query generateQuery() {
        switch (type) {
            case TERM:
                return new TermQuery(new Term(args[FIELD], args[VALUE]));
            case TERM_RANGE:
                return TermRangeQuery
                    .newStringRange(args[FIELD], args[FROM], args[TO], true, true);
            case AND:
                BooleanQuery.Builder and = new BooleanQuery.Builder();
                and.add(first.generateQuery(), BooleanClause.Occur.MUST);
                and.add(second.generateQuery(), BooleanClause.Occur.MUST);
                return and.build();
            case OR:
                BooleanQuery.Builder or = new BooleanQuery.Builder();
                or.add(first.generateQuery(), BooleanClause.Occur.SHOULD);
                or.add(second.generateQuery(), BooleanClause.Occur.SHOULD);
                return or.build();
            case ALL:
                return new MatchAllDocsQuery();
            default:
                return null;
        }
    }
}
