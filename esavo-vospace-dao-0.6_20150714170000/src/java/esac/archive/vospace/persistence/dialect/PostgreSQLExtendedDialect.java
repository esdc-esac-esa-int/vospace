package esac.archive.vospace.persistence.dialect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.dialect.PostgreSQL82Dialect;

/**
 * Based on web page (http://www.znetdevelopment.com/blogs/tag/hql/), I extend the default
 * PostgreSQL dialect understood by Hibernate so that it allows additional methods and operators.
 * Note: See http://esavo02.esac.esa.int/trac/ABSI/ticket/116
 * @author nfajersz
 */
public class PostgreSQLExtendedDialect extends PostgreSQL82Dialect {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(PostgreSQLExtendedDialect.class);

    /**
     * Default constructor, which adds new functions.
     */
    public PostgreSQLExtendedDialect() {
        super();
        log.debug("Loading PostgreSQLExtendedDialect()...");

        /*registerFunction(EhstFunctionKeywords.CONTAINS_FUNCTION_NAME, new StandardSQLFunction(
                EhstFunctionKeywords.CONTAINS_FUNCTION_NAME,
                 Hibernate.BOOLEAN BooleanType.INSTANCE));

        registerFunction(EhstFunctionKeywords.OVERLAPS_FUNCTION_NAME, new StandardSQLFunction(
                EhstFunctionKeywords.OVERLAPS_FUNCTION_NAME,
                 Hibernate.BOOLEAN BooleanType.INSTANCE));*/

    }

}
