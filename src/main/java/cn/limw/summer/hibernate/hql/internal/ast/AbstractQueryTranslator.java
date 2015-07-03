package cn.limw.summer.hibernate.hql.internal.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.QueryException;
import org.hibernate.ScrollableResults;
import org.hibernate.engine.query.spi.EntityGraphQueryHint;
import org.hibernate.engine.spi.QueryParameters;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.EventSource;
import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
import org.hibernate.hql.internal.antlr.HqlTokenTypes;
import org.hibernate.hql.internal.antlr.SqlTokenTypes;
import org.hibernate.hql.internal.ast.HqlParser;
import org.hibernate.hql.internal.ast.HqlSqlWalker;
import org.hibernate.hql.internal.ast.ParameterTranslationsImpl;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.hibernate.hql.internal.ast.SqlGenerator;
import org.hibernate.hql.internal.ast.exec.BasicExecutor;
import org.hibernate.hql.internal.ast.exec.DeleteExecutor;
import org.hibernate.hql.internal.ast.exec.MultiTableDeleteExecutor;
import org.hibernate.hql.internal.ast.exec.MultiTableUpdateExecutor;
import org.hibernate.hql.internal.ast.exec.StatementExecutor;
import org.hibernate.hql.internal.ast.tree.AggregatedSelectExpression;
import org.hibernate.hql.internal.ast.tree.FromElement;
import org.hibernate.hql.internal.ast.tree.InsertStatement;
import org.hibernate.hql.internal.ast.tree.QueryNode;
import org.hibernate.hql.internal.ast.tree.Statement;
import org.hibernate.hql.internal.ast.util.ASTPrinter;
import org.hibernate.hql.internal.ast.util.ASTUtil;
import org.hibernate.hql.internal.ast.util.NodeTraverser;
import org.hibernate.hql.spi.ParameterTranslations;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.IdentitySet;
import org.hibernate.loader.hql.QueryLoader;
import org.hibernate.param.ParameterSpecification;
import org.hibernate.persister.entity.Queryable;
import org.hibernate.type.Type;
import org.jboss.logging.Logger;

import antlr.ANTLRException;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;

/**
 * 复制QueryTranslatorImpl
 * @author li
 * @version 1 (2014年11月14日 下午4:23:41)
 * @since Java7
 * @see SqlGenerator
 * @see #generate(antlr.collections.AST)
 */
public class AbstractQueryTranslator extends org.hibernate.hql.internal.ast.QueryTranslatorImpl {
    protected static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, AbstractQueryTranslator.class.getName());

    private SessionFactoryImplementor factory;

    private final String queryIdentifier;

    private String hql;

    private boolean shallowQuery;

    private Map tokenReplacements;

    private Map enabledFilters;

    private boolean compiled;

    private QueryLoader queryLoader;

    private StatementExecutor statementExecutor;

    private Statement sqlAst;

    private String sql;

    private ParameterTranslations paramTranslations;

    private List<ParameterSpecification> collectedParameterSpecifications;

    private EntityGraphQueryHint entityGraphQueryHint;

    /**
     * Creates a new AST-based query translator.
     * @param queryIdentifier The query-identifier (used in stats collection)
     * @param query The hql query to translate
     * @param enabledFilters Currently enabled filters
     * @param factory The session factory constructing this translator instance.
     */
    public AbstractQueryTranslator(String queryIdentifier, String query, Map enabledFilters, SessionFactoryImplementor factory) {
        super(queryIdentifier, query, enabledFilters, factory);
        this.queryIdentifier = queryIdentifier;
        this.hql = query;
        this.compiled = false;
        this.shallowQuery = false;
        this.enabledFilters = enabledFilters;
        this.factory = factory;
    }

    public AbstractQueryTranslator(String queryIdentifier, String query, Map enabledFilters, SessionFactoryImplementor factory, EntityGraphQueryHint entityGraphQueryHint) {
        this(queryIdentifier, query, enabledFilters, factory);
        this.entityGraphQueryHint = entityGraphQueryHint;
    }

    /**
     * Compile a "normal" query. This method may be called multiple
     * times. Subsequent invocations are no-ops.
     * @param replacements Defined query substitutions.
     * @param shallow Does this represent a shallow (scalar or entity-id) select?
     * @throws QueryException There was a problem parsing the query string.
     * @throws MappingException There was a problem querying defined mappings.
     */

    public void compile(Map replacements, boolean shallow) throws QueryException, MappingException {
        doCompile(replacements, shallow, null);
    }

    /**
     * Compile a filter. This method may be called multiple
     * times. Subsequent invocations are no-ops.
     * @param collectionRole the role name of the collection used as the basis for the filter.
     * @param replacements Defined query substitutions.
     * @param shallow Does this represent a shallow (scalar or entity-id) select?
     * @throws QueryException There was a problem parsing the query string.
     * @throws MappingException There was a problem querying defined mappings.
     */

    public void compile(String collectionRole, Map replacements, boolean shallow) throws QueryException, MappingException {
        doCompile(replacements, shallow, collectionRole);
    }

    /**
     * Performs both filter and non-filter compiling.
     * @param replacements Defined query substitutions.
     * @param shallow Does this represent a shallow (scalar or entity-id) select?
     * @param collectionRole the role name of the collection used as the basis for the filter, NULL if this
     *            is not a filter.
     */
    private synchronized void doCompile(Map replacements, boolean shallow, String collectionRole) {
        if (compiled) {
            LOG.debug("compile() : The query is already compiled, skipping...");
            return;
        }

        this.tokenReplacements = replacements;
        if (tokenReplacements == null) {
            tokenReplacements = new HashMap();
        }
        this.shallowQuery = shallow;

        try {
            final HqlParser parser = parse(true);

            final HqlSqlWalker w = analyze(parser, collectionRole);

            sqlAst = (Statement) w.getAST();

            if (sqlAst.needsExecutor()) {
                statementExecutor = buildAppropriateStatementExecutor(w);
            } else {
                generate((QueryNode) sqlAst);
                queryLoader = new QueryLoader(this, factory, w.getSelectClause());
            }
            compiled = true;
        } catch (QueryException qe) {
            if (qe.getQueryString() == null) {
                throw qe.wrapWithQueryString(hql);
            }
            else {
                throw qe;
            }
        } catch (RecognitionException e) {
            LOG.trace("Converted antlr.RecognitionException", e);
            throw QuerySyntaxException.convert(e, hql);
        } catch (ANTLRException e) {
            LOG.trace("Converted antlr.ANTLRException", e);
            throw new QueryException(e.getMessage(), hql);
        }

        this.enabledFilters = null;
    }

    public void generate(AST sqlAst) throws QueryException, RecognitionException {
        if (sql == null) {
            final SqlGenerator gen = new SqlGenerator(factory);
            gen.statement(sqlAst);
            sql = gen.getSQL();
            if (LOG.isDebugEnabled()) {
                LOG.debugf("HQL: %s", hql);
                LOG.debugf("SQL: %s", sql);
            }
            gen.getParseErrorHandler().throwQueryException();
            collectedParameterSpecifications = gen.getCollectedParameters();
        }
    }

    private static final ASTPrinter SQL_TOKEN_PRINTER = new ASTPrinter(SqlTokenTypes.class);

    private HqlSqlWalker analyze(HqlParser parser, String collectionRole) throws QueryException, RecognitionException {
        final HqlSqlWalker w = new HqlSqlWalker(this, factory, parser, tokenReplacements, collectionRole);
        final AST hqlAst = parser.getAST();

        w.statement(hqlAst);

        if (LOG.isDebugEnabled()) {
            LOG.debug(SQL_TOKEN_PRINTER.showAsString(w.getAST(), "--- SQL AST ---"));
        }

        w.getParseErrorHandler().throwQueryException();
        return w;
    }

    private HqlParser parse(boolean filter) throws TokenStreamException, RecognitionException {
        final HqlParser parser = HqlParser.getInstance(hql);
        parser.setFilter(filter);

        LOG.debugf("parse() - HQL: %s", hql);
        parser.statement();

        final AST hqlAst = parser.getAST();

        final NodeTraverser walker = new NodeTraverser(new JavaConstantConverter());
        walker.traverseDepthFirst(hqlAst);

        showHqlAst(hqlAst);

        parser.getParseErrorHandler().throwQueryException();
        return parser;
    }

    private static final ASTPrinter HQL_TOKEN_PRINTER = new ASTPrinter(HqlTokenTypes.class);

    void showHqlAst(AST hqlAst) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(HQL_TOKEN_PRINTER.showAsString(hqlAst, "--- HQL AST ---"));
        }
    }

    private void errorIfDML() throws HibernateException {
        if (sqlAst.needsExecutor()) {
            throw new QueryExecutionRequestException("Not supported for DML operations", hql);
        }
    }

    private void errorIfSelect() throws HibernateException {
        if (!sqlAst.needsExecutor()) {
            throw new QueryExecutionRequestException("Not supported for select queries", hql);
        }
    }

    public String getQueryIdentifier() {
        return queryIdentifier;
    }

    public Statement getSqlAST() {
        return sqlAst;
    }

    private HqlSqlWalker getWalker() {
        return sqlAst.getWalker();
    }

    /**
     * Types of the return values of an <tt>iterate()</tt> style query.
     * @return an array of <tt>Type</tt>s.
     */

    public Type[] getReturnTypes() {
        errorIfDML();
        return getWalker().getReturnTypes();
    }

    public String[] getReturnAliases() {
        errorIfDML();
        return getWalker().getReturnAliases();
    }

    public String[][] getColumnNames() {
        errorIfDML();
        return getWalker().getSelectClause().getColumnNames();
    }

    public Set<Serializable> getQuerySpaces() {
        return getWalker().getQuerySpaces();
    }

    public List list(SessionImplementor session, QueryParameters queryParameters) throws HibernateException {
        // Delegate to the QueryLoader...
        errorIfDML();

        final QueryNode query = (QueryNode) sqlAst;
        final boolean hasLimit = queryParameters.getRowSelection() != null && queryParameters.getRowSelection().definesLimits();
        final boolean needsDistincting = (query.getSelectClause().isDistinct() || hasLimit) && containsCollectionFetches();

        QueryParameters queryParametersToUse;
        if (hasLimit && containsCollectionFetches()) {
            LOG.firstOrMaxResultsSpecifiedWithCollectionFetch();
            RowSelection selection = new RowSelection();
            selection.setFetchSize(queryParameters.getRowSelection().getFetchSize());
            selection.setTimeout(queryParameters.getRowSelection().getTimeout());
            queryParametersToUse = queryParameters.createCopyUsing(selection);
        }
        else {
            queryParametersToUse = queryParameters;
        }

        List results = queryLoader.list(session, queryParametersToUse);

        if (needsDistincting) {
            int includedCount = -1;
            // NOTE : firstRow is zero-based
            int first = !hasLimit || queryParameters.getRowSelection().getFirstRow() == null
                    ? 0
                    : queryParameters.getRowSelection().getFirstRow();
            int max = !hasLimit || queryParameters.getRowSelection().getMaxRows() == null
                    ? -1
                    : queryParameters.getRowSelection().getMaxRows();
            List tmp = new ArrayList();
            IdentitySet distinction = new IdentitySet();
            for (final Object result : results) {
                if (!distinction.add(result)) {
                    continue;
                }
                includedCount++;
                if (includedCount < first) {
                    continue;
                }
                tmp.add(result);
                // NOTE : ( max - 1 ) because first is zero-based while max is not...
                if (max >= 0 && (includedCount - first) >= (max - 1)) {
                    break;
                }
            }
            results = tmp;
        }

        return results;
    }

    /**
     * Return the query results as an iterator
     */

    public Iterator iterate(QueryParameters queryParameters, EventSource session) throws HibernateException {
        // Delegate to the QueryLoader...
        errorIfDML();
        return queryLoader.iterate(queryParameters, session);
    }

    /**
     * Return the query results, as an instance of <tt>ScrollableResults</tt>
     */

    public ScrollableResults scroll(QueryParameters queryParameters, SessionImplementor session) throws HibernateException {
        // Delegate to the QueryLoader...
        errorIfDML();
        return queryLoader.scroll(queryParameters, session);
    }

    public int executeUpdate(QueryParameters queryParameters, SessionImplementor session) throws HibernateException {
        errorIfSelect();
        return statementExecutor.execute(queryParameters, session);
    }

    /**
     * The SQL query string to be called; implemented by all subclasses
     */
    public String getSQLString() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> collectSqlStrings() {
        ArrayList<String> list = new ArrayList<String>();
        if (isManipulationStatement()) {
            String[] sqlStatements = statementExecutor.getSqlStatements();
            Collections.addAll(list, sqlStatements);
        }
        else {
            list.add(sql);
        }
        return list;
    }

    public boolean isShallowQuery() {
        return shallowQuery;
    }

    public String getQueryString() {
        return hql;
    }

    public Map getEnabledFilters() {
        return enabledFilters;
    }

    public int[] getNamedParameterLocs(String name) {
        return getWalker().getNamedParameterLocations(name);
    }

    public boolean containsCollectionFetches() {
        errorIfDML();
        List collectionFetches = ((QueryNode) sqlAst).getFromClause().getCollectionFetches();
        return collectionFetches != null && collectionFetches.size() > 0;
    }

    public boolean isManipulationStatement() {
        return sqlAst.needsExecutor();
    }

    public void validateScrollability() throws HibernateException {
        // Impl Note: allows multiple collection fetches as long as the
        // entire fecthed graph still "points back" to a single
        // root entity for return

        errorIfDML();

        final QueryNode query = (QueryNode) sqlAst;

        // If there are no collection fetches, then no further checks are needed
        List collectionFetches = query.getFromClause().getCollectionFetches();
        if (collectionFetches.isEmpty()) {
            return;
        }

        // A shallow query is ok (although technically there should be no fetching here...)
        if (isShallowQuery()) {
            return;
        }

        // Otherwise, we have a non-scalar select with defined collection fetch(es).
        // Make sure that there is only a single root entity in the return (no tuples)
        if (getReturnTypes().length > 1) {
            throw new HibernateException("cannot scroll with collection fetches and returned tuples");
        }

        FromElement owner = null;
        for (Object o : query.getSelectClause().getFromElementsForLoad()) {
            // should be the first, but just to be safe...
            final FromElement fromElement = (FromElement) o;
            if (fromElement.getOrigin() == null) {
                owner = fromElement;
                break;
            }
        }

        if (owner == null) {
            throw new HibernateException("unable to locate collection fetch(es) owner for scrollability checks");
        }

        AST primaryOrdering = query.getOrderByClause().getFirstChild();
        if (primaryOrdering != null) {
            String[] idColNames = owner.getQueryable().getIdentifierColumnNames();
            String expectedPrimaryOrderSeq = StringHelper.join(", ", StringHelper.qualify(owner.getTableAlias(), idColNames));
            if (!primaryOrdering.getText().startsWith(expectedPrimaryOrderSeq)) {
                throw new HibernateException("cannot scroll results with collection fetches which are not ordered primarily by the root entity's PK");
            }
        }
    }

    private StatementExecutor buildAppropriateStatementExecutor(HqlSqlWalker walker) {
        final Statement statement = (Statement) walker.getAST();
        if (walker.getStatementType() == HqlSqlTokenTypes.DELETE) {
            final FromElement fromElement = walker.getFinalFromClause().getFromElement();
            final Queryable persister = fromElement.getQueryable();
            if (persister.isMultiTable()) {
                return new MultiTableDeleteExecutor(walker);
            }
            else {
                return new DeleteExecutor(walker, persister);
            }
        }
        else if (walker.getStatementType() == HqlSqlTokenTypes.UPDATE) {
            final FromElement fromElement = walker.getFinalFromClause().getFromElement();
            final Queryable persister = fromElement.getQueryable();
            if (persister.isMultiTable()) {
                // even here, if only properties mapped to the "base table" are referenced
                // in the set and where clauses, this could be handled by the BasicDelegate.
                // TODO : decide if it is better performance-wise to doAfterTransactionCompletion that check, or to simply use the MultiTableUpdateDelegate
                return new MultiTableUpdateExecutor(walker);
            }
            else {
                return new BasicExecutor(walker, persister);
            }
        }
        else if (walker.getStatementType() == HqlSqlTokenTypes.INSERT) {
            return new BasicExecutor(walker, ((InsertStatement) statement).getIntoClause().getQueryable());
        }
        else {
            throw new QueryException("Unexpected statement type");
        }
    }

    public ParameterTranslations getParameterTranslations() {
        if (paramTranslations == null) {
            paramTranslations = new ParameterTranslationsImpl(getWalker().getParameters());
        }
        return paramTranslations;
    }

    public List<ParameterSpecification> getCollectedParameterSpecifications() {
        return collectedParameterSpecifications;
    }

    public Class getDynamicInstantiationResultType() {
        AggregatedSelectExpression aggregation = queryLoader.getAggregatedSelectExpression();
        return aggregation == null ? null : aggregation.getAggregationResultType();
    }

    public static class JavaConstantConverter implements NodeTraverser.VisitationStrategy {
        private AST dotRoot;

        public void visit(AST node) {
            if (dotRoot != null) {
                // we are already processing a dot-structure
                if (ASTUtil.isSubtreeChild(dotRoot, node)) {
                    return;
                }
                // we are now at a new tree level
                dotRoot = null;
            }

            if (node.getType() == HqlTokenTypes.DOT) {
                dotRoot = node;
                handleDotStructure(dotRoot);
            }
        }

        private void handleDotStructure(AST dotStructureRoot) {
            final String expression = ASTUtil.getPathText(dotStructureRoot);
            final Object constant = ReflectHelper.getConstantValue(expression);
            if (constant != null) {
                dotStructureRoot.setFirstChild(null);
                dotStructureRoot.setType(HqlTokenTypes.JAVA_CONSTANT);
                dotStructureRoot.setText(expression);
            }
        }
    }

    public EntityGraphQueryHint getEntityGraphQueryHint() {
        return entityGraphQueryHint;
    }

    public void setEntityGraphQueryHint(EntityGraphQueryHint entityGraphQueryHint) {
        this.entityGraphQueryHint = entityGraphQueryHint;
    }

    public SessionFactoryImplementor getFactory() {
        return factory;
    }

    public String getHql() {
        return hql;
    }

    public void setCollectedParameterSpecifications(List<ParameterSpecification> collectedParameterSpecifications) {
        this.collectedParameterSpecifications = collectedParameterSpecifications;
    }
}