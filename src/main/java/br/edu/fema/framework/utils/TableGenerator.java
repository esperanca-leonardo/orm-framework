package br.edu.fema.framework.utils;

import br.edu.fema.framework.annotations.Column;
import br.edu.fema.framework.annotations.Table;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class TableGenerator {

    static final Map<Class, String> mappingTypes = new HashMap<>();

    static {
        mappingTypes.put(long.class, "NUMERIC");
        mappingTypes.put(int.class, "NUMERIC");
        mappingTypes.put(BigDecimal.class, "NUMERIC");
        mappingTypes.put(String.class, "VARCHAR2");
        mappingTypes.put(boolean.class, "VARCHAR2(1)");
    }

    // String sql = TableGenerator.toCreate(Professor.class);
    public static String toCreate(Class<?> klass) {
        // CREATE TABLE ? ( ? ?, ? ?, ? ? ... );
        final StringBuilder createSql = new StringBuilder("");

        if (klass.isAnnotationPresent(Table.class)) {
            final Table tableAnnotation = klass.getDeclaredAnnotation(Table.class);

            if (tableAnnotation.enabled()) {
                createSql.append("CREATE TABLE ").append(tableAnnotation.value());
                createSql.append(" (");

                final StringBuilder sqlColumns = new StringBuilder("");

                for (Field field : klass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Column.class)) {
                        final Column columnAnnotation =
                                field.getDeclaredAnnotation(Column.class);
                        final String columnName = getColumnName(field, columnAnnotation);
                        final String columnType = getColumnType(field);

                        if (sqlColumns.length() > 0) {
                            sqlColumns.append(", ");
                        }

                        sqlColumns.append(columnName).append(" ");
                        sqlColumns.append(columnType);

                        if (columnAnnotation.size() != -1) {
                            sqlColumns.append("(").append(columnAnnotation.size());
                            if (columnAnnotation.precision() != -1) {
                                sqlColumns.append(",").append(columnAnnotation.precision());
                            }
                            sqlColumns.append(")");
                        }
                    }
                }

                // ??? fields = columns
                createSql.append(sqlColumns);

                createSql.append(");");
            }
        }

        return createSql.toString();
    }

    private static String getColumnType(Field field) {
        return mappingTypes.getOrDefault(field.getType(), "???");
    }

    private static String getColumnName(Field field, Column columnAnnotation) {
        return !"".equals(columnAnnotation.value()) ?
                columnAnnotation.value() : field.getName().toUpperCase();
    }
}
