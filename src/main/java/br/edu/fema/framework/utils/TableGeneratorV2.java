package br.edu.fema.framework.utils;

import br.edu.fema.domain.model.Professor;
import br.edu.fema.domain.model.Sala;
import br.edu.fema.framework.annotations.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TableGeneratorV2 {

  private static Map<Class<?>, String> toSql;

  static {
    toSql = new HashMap<>();

    toSql.put(String.class, "VARCHAR");
    toSql.put(Long.class, "SERIAL");
    toSql.put(BigDecimal.class, "REAL");
    toSql.put(Professor.class, "SERIAL");
    toSql.put(Sala.class, "SERIAL");
  }

  public static String createTable(Class<?> aClass) {
    var stringBuilder = new StringBuilder();

    stringBuilder.append("CREATE TABLE ")
      .append(aClass.getSimpleName())
      .append(" (")
      .append("\n");

    var columns = new StringBuilder();

    for (Field field : aClass.getDeclaredFields()) {
      if (field.isAnnotationPresent(Column.class)) {
        if (columns.length() > 0) {
          columns.append(",\n");
        }
        columns.append("\t")
          .append(field.getName())
          .append(" ")
          .append(toSql.get(field.getType()));
      }
    }
    stringBuilder.append(columns)
      .append("\n);");

    return stringBuilder.toString();
  }

  public static String primaryKey(Class<?> aClass) {
    Field[] fields = aClass.getDeclaredFields();

    var primaryKeyAnnotation = PrimaryKey.class;
    var fieldsThatContainPrimaryKey = new StringBuilder();
    var builderDdl = new StringBuilder("ALTER TABLE ")
      .append(aClass.getSimpleName());

    boolean constraintDefined = false;

    for (var field : fields) {
      if (field.isAnnotationPresent(primaryKeyAnnotation)) {
        var primaryKey = field.getAnnotation(primaryKeyAnnotation);

        if (!constraintDefined) {
          builderDdl.append(" ADD CONSTRAINT ")
            .append(primaryKey.constraintName())
            .append(" PRIMARY KEY (");

          constraintDefined = true;
        }
        if (fieldsThatContainPrimaryKey.length() > 0) {
          fieldsThatContainPrimaryKey.append(", ");
        }
        fieldsThatContainPrimaryKey.append(field.getName());
      }
    }
    fieldsThatContainPrimaryKey.append(");");
    return builderDdl.append(fieldsThatContainPrimaryKey).toString();
  }

  public static String foreignKey(Class<?> aClass) {
    Field[] fields = aClass.getDeclaredFields();

    var foreignKeyAnnotation = ForeignKey.class;
    var fieldsThatContainForeignKey = new StringBuilder();
    var builderDdl = new StringBuilder("ALTER TABLE ")
      .append(aClass.getSimpleName());

    for (var field : fields) {
      if (field.isAnnotationPresent(foreignKeyAnnotation)) {
        var foreignKey = field.getAnnotation(foreignKeyAnnotation);

        if (fieldsThatContainForeignKey.length() != 0) {
          fieldsThatContainForeignKey.append(",");
        }
        fieldsThatContainForeignKey.append("\n\tADD CONSTRAINT ")
          .append(foreignKey.constraintName())
          .append(" FOREIGN KEY (")
          .append(field.getName())
          .append(")")
          .append(" REFERENCES ")
          .append(foreignKey.table())
          .append(" (")
          .append(foreignKey.column())
          .append(")");
      }
    }
    fieldsThatContainForeignKey.append(";");
    return builderDdl.append(fieldsThatContainForeignKey).toString();
  }

  public static String unique(Class<?> aClass) {
    Field[] fields = aClass.getDeclaredFields();

    var uniqueAnnotation = Unique.class;
    var fieldsThatContainUnique = new StringBuilder();
    var builderDdl = new StringBuilder("ALTER TABLE ")
      .append(aClass.getSimpleName());

    boolean constraintDefined = false;

    for (var field : fields) {
      if (field.isAnnotationPresent(uniqueAnnotation)) {
        var unique = field.getAnnotation(uniqueAnnotation);

        if (!constraintDefined) {
          builderDdl.append(" ADD CONSTRAINT ")
            .append(unique.constraintName())
            .append(" UNIQUE (");

          constraintDefined = true;
        }
        if (fieldsThatContainUnique.length() != 0) {
          fieldsThatContainUnique.append(", ");
        }
        fieldsThatContainUnique.append(field.getName());
      }
    }
    fieldsThatContainUnique.append(");");
    return builderDdl.append(fieldsThatContainUnique).toString();
  }

  public static String check(Class<?> aClass) {
    Field[] fields = aClass.getDeclaredFields();

    var checkAnnotation = Check.class;
    var fieldsThatContainCheck = new StringBuilder();
    var builderDdl = new StringBuilder("ALTER TABLE ")
      .append(aClass.getSimpleName());

    for (var field : fields) {
      if (field.isAnnotationPresent(checkAnnotation)) {
        var check = field.getAnnotation(checkAnnotation);

        if (fieldsThatContainCheck.length() > 0) {
          fieldsThatContainCheck.append(",");
        }
        fieldsThatContainCheck.append("\n\tADD CONSTRAINT ")
          .append(check.constraintName())
          .append(" CHECK (")
          .append(field.getName())
          .append(" ")
          .append(check.operation())
          .append(" ")
          .append(check.value())
          .append(")");
      }
    }
    fieldsThatContainCheck.append(";");
    return builderDdl.append(fieldsThatContainCheck).toString();
  }

  public static String insert(Class<?> aClass) {
    Field[] fields = aClass.getDeclaredFields();

    var valuesToInsert = new StringBuilder();
    var fieldsThatAreColumn = new StringBuilder();
    var builderDml = new StringBuilder("INSERT INTO ")
      .append(aClass.getSimpleName())
      .append(" (");

    for (var field : fields) {
      if (field.isAnnotationPresent(Column.class)) {
        if (fieldsThatAreColumn.length() > 0) {
          fieldsThatAreColumn.append(", ");
          valuesToInsert.append(", ");
        }
        fieldsThatAreColumn.append(field.getName());
        valuesToInsert.append("?");
      }
    }
    fieldsThatAreColumn.append(")")
      .append(" VALUES (")
      .append(valuesToInsert)
      .append(");");

    return builderDml.append(fieldsThatAreColumn).toString();
  }

  public static String update(Class<?> aClass, String column, String operation,
      String value) {

    Field[] fields = aClass.getDeclaredFields();

    var fieldsThatAreColumn = new StringBuilder();
    var builderDml = new StringBuilder("UPDATE ")
      .append(aClass.getSimpleName())
      .append(" SET ");

    for (var field : fields) {
      if (field.isAnnotationPresent(Column.class)) {
        if (fieldsThatAreColumn.length() > 0) {
          fieldsThatAreColumn.append(", ");
        }
        fieldsThatAreColumn.append(field.getName())
          .append(" = ")
          .append("?");
      }
    }
    fieldsThatAreColumn.append(" WHERE ")
      .append(column)
      .append(" ")
      .append(operation)
      .append(" ")
      .append(value)
      .append(";");

    return builderDml.append(fieldsThatAreColumn).toString();
  }

  public static String delete(Class<?> aClass, String column, String operation,
      String value) {

    var builderDml = new StringBuilder("DELETE FROM ")
      .append(aClass.getSimpleName())
      .append(" WHERE ")
      .append(column)
      .append(" ")
      .append(operation)
      .append(" ")
      .append(value)
      .append(";");

    return builderDml.toString();
  }

  public static String select(Class<?> aClass, String column, String operation,
      String value) {

    var builderDml = new StringBuilder("SELECT ");
    var columns = new StringBuilder();
      // colunas
    Field[] fields = aClass.getDeclaredFields();

    for (var field : fields) {
      if (field.isAnnotationPresent(Column.class)) {
        if (columns.length() > 0) {
          columns.append(", ");
        }
        columns.append(field.getName());
      }
    }
    builderDml.append(columns);
    builderDml.append(" FROM ")
      .append(aClass.getSimpleName())
      .append(" WHERE ")
      .append(column)
      .append(" ")
      .append(operation)
      .append(" ")
      .append(value)
      .append(";");

    return builderDml.toString();
  }
}