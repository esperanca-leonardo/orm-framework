package br.edu.fema.main;

import br.edu.fema.domain.model.Aluno;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Arrays;

public class AppConstructor {

    public static void main(String[] args) {
        try {
            final Class<?> klassAluno =
                    Class.forName("br.edu.fema.domain.model.Aluno");

            // final Constructor<?>[] constructors =
            //      klassAluno.getConstructors(); // somente construtores públicos

            final Constructor<?>[] constructors = klassAluno.getDeclaredConstructors();

            for (Constructor constructor : constructors) {
                System.out.println(constructor.getName());
                System.out.println(Arrays.toString(constructor.getParameterTypes()));
                System.out.println(Arrays.toString(constructor.getExceptionTypes()));
                System.out.println("---");
            }

            // public BigDecimal somar(BigDecimal n1, BigDecimal n2, BigDecimal... otherNumbers) { ... }
            // somar(n1, n2);
            // somar(n1, n2, n3);
            // somar(n1, n2, n3, n4, n5, n6);

            // Class<?>... = varargs (argumentos variáveis)
            // BigDecimal...
            // Aluno...
            final Constructor<?> defaultConstructor = klassAluno.getDeclaredConstructor();
            final Constructor<?> constructor1 =
                    klassAluno.getDeclaredConstructor(long.class, String.class);
            final Constructor<?> constructor2 =
                    klassAluno.getDeclaredConstructor(long.class, String.class, BigDecimal.class);

            System.out.println(defaultConstructor);
            System.out.println(constructor1);
            System.out.println(constructor2);

            // final Aluno aluno = new Aluno(1000L, "Guilherme", new BigDecimal("967.23"));
            final Object[] initargs = new Object[] {1000L, "Guilherme", new BigDecimal("967.23")};
            final Aluno aluno = (Aluno) constructor2.newInstance(initargs);

            // Aluno:1,Guilherme,100
            // Professor:1,Guilherme,guilherme.farto@gmail.com,Mestre

            System.out.println(aluno);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
