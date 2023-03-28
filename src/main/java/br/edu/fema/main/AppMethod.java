package br.edu.fema.main;

import br.edu.fema.domain.model.Aluno;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AppMethod {

    public static void main(String[] args) {
        try {
            // Class<?> klassAluno = Aluno.class;
            // Class<?> klassAluno = new Aluno().getClass();
            Class<?> klassAluno = Class.forName("br.edu.fema.domain.model.Aluno");

            // Class, Field, Constructor + Method

            // somente métodos públicos
            // Method[] methods = klassAluno.getMethods();

            Method[] methods = klassAluno.getDeclaredMethods();

            // Proxy = interceptor = Programação Orientada Aspectos
            for (Method method : methods) {
                System.out.println(method.getName());
                System.out.println(Modifier.toString(method.getModifiers()));
                System.out.println(method.getReturnType());
                System.out.println(Arrays.toString(method.getParameterTypes()));
                System.out.println(Arrays.toString(method.getExceptionTypes()));
                System.out.println();
            }

            // Method methodSetRa = klassAluno.getMethod(...)
            Method methodSetRa = klassAluno.getDeclaredMethod("setRa",
                    new Class[] {long.class});
            methodSetRa.setAccessible(true);
            Method methodSetNome = klassAluno.getDeclaredMethod("setNome",
                    String.class);
            methodSetNome.setAccessible(true);

            // final Aluno aluno = new Aluno();
            final Aluno aluno = (Aluno) klassAluno.getDeclaredConstructor().newInstance();

            // aluno.setRa(1000L);
            methodSetRa.invoke(aluno, 1000L);
            // aluno.setNome("Guilherme");
            methodSetNome.invoke(aluno, "Guilherme");

            Method methodAdicionarDisciplina =
                    klassAluno.getDeclaredMethod("adicionarDisciplina", String.class);
            methodAdicionarDisciplina.setAccessible(true);
            methodAdicionarDisciplina.invoke(aluno, "Tópicos Avançados");
            methodAdicionarDisciplina.invoke(aluno, "Engenharia de Software");
            // GSON

            Method methodGetDisciplinas = klassAluno.getDeclaredMethod("getDisciplinas");
            methodGetDisciplinas.setAccessible(true);
            List<String> disciplinas = (List<String>) methodGetDisciplinas.invoke(aluno);

            System.out.println(disciplinas);

            System.out.println(aluno);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
