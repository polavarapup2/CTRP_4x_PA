package gov.nih.nci.pa.util;

import java.sql.Types;

public class CustomHSQLDialect extends org.hibernate.dialect.HSQLDialect {
    public CustomHSQLDialect() {
        registerColumnType(Types.BOOLEAN, "boolean");
        registerHibernateType(Types.BOOLEAN, "boolean");
    }
}