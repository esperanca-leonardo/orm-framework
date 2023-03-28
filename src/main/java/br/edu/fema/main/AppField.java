package br.edu.fema.main;

import br.edu.fema.domain.model.Aluno;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;


public class AppField {

    public static void main(String[] args) {
        try {
            Class<?> klassAluno = Class.forName("br.edu.fema.domain.model.Aluno");

            final Aluno aluno = (Aluno) klassAluno.newInstance(); // new Aluno();

            System.out.println(aluno);

            // final Field[] fields = klassAluno.getDeclaredFields();
            final List<Field> fields = Arrays.asList(klassAluno.getDeclaredFields());

            final Field fieldRa = klassAluno.getDeclaredField("ra");
            final Field fieldNome = klassAluno.getDeclaredField("nome");

            System.out.println(fieldRa);
            System.out.println(fieldNome);

            fieldRa.setAccessible(true);
            fieldNome.setAccessible(true);

            // aluno.setRa(1000L);
            fieldRa.set(aluno, 1000L); // introspectado
            // aluno.setNome("Guilherme");
            fieldNome.set(aluno, "Guilherme");

            // long ra = aluno.getRa();
            final long ra = (long) fieldRa.get(aluno);
            // aluno.getNome();
            final String nome = (String) fieldNome.get(aluno);

            System.out.println(aluno);
            System.out.println(ra);
            System.out.println(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
