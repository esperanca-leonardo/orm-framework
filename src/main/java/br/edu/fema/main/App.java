package br.edu.fema.main;

import br.edu.fema.domain.model.Aluno;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        // Java Reflection API = Metaprogramação (paradigma)
        final Aluno aluno = new Aluno();

        // Class = .class

        Class<?> klass1 = Aluno.class;
        // Class<?> klass2 = aluno.getClass();
        Class<?> klass3 = null;

        try {
            klass3 = Class.forName("br.edu.fema.domain.model.Aluno");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(klass3);

        System.out.println(klass3.getPackage());
        System.out.println(klass3.getPackageName());

        System.out.println(klass3.getName());
        System.out.println(klass3.getSimpleName());
        System.out.println(klass3.getModifiers());
        System.out.println(Arrays.toString(
                Modifier.toString(klass3.getModifiers()).split(" ")));
        // String[] modificadoresAcesso = "public abstract".split(" ");
        System.out.println(klass3.isEnum());
        System.out.println(klass3.isInterface());

        System.out.println("--- Fields ---");

        // Field[] fields = klass3.getFields(); // somente atributos públicos
        Field[] fields = klass3.getDeclaredFields();

        System.out.println(Arrays.toString(fields));

        for (Field field : fields) {
            System.out.println(field.getName());
            System.out.println(field.getType());

            if (field.getType().equals(List.class)) { // List<String>
                Type listType =
                        ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                System.out.println("> " + listType);
            } else if (field.getType().equals(Map.class)) { // Map<String, Aluno>
                Type mapKeyType =
                        ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                Type mapValueType =
                        ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
                System.out.println("> " + mapKeyType + " - " + mapValueType);
            }

            System.out.println(Modifier.toString(field.getModifiers()));
            System.out.println("-".repeat(10));
        }

        // Field fieldRa = klass3.getField("ra"); // atributo "ra" desde que público
        try {
            Field fieldRa = klass3.getDeclaredField("ra");
            Field fieldNome = klass3.getDeclaredField("nome");

            // aluno.setRa(1000L);
            // aluno.setNome("Guilherme");

            fieldRa.setAccessible(true);
            fieldNome.setAccessible(true);
            fieldRa.set(aluno, 1000L);
            fieldNome.set(aluno, "Guilherme");

            System.out.println(fieldRa.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println(aluno);
    }
}
