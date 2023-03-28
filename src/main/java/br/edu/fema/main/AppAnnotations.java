package br.edu.fema.main;

import br.edu.fema.domain.model.Professor;
import br.edu.fema.framework.annotations.Author;
import br.edu.fema.framework.utils.TableGenerator;

import java.math.BigDecimal;

public class AppAnnotations {

    public static void main(String[] args) {
        final Class<?> klassProfessor = Professor.class;
        // class field method constructor
        if (klassProfessor.isAnnotationPresent(Author.class)) {
            final Author authorAnnotation =
                    klassProfessor.getDeclaredAnnotation(Author.class);
            System.out.println(authorAnnotation.name()); // Guilherme
            System.out.println(authorAnnotation.since()); // 1
            System.out.println(authorAnnotation.generated()); // true
        }

        final String createProfessor = TableGenerator.toCreate(Professor.class);
        System.out.println(createProfessor);

        // 1. Implementar anotação(ões) para Primary Key;
        // 2. Implementar anotação(ões) para Unique Key;
        // 3. Implementar anotação(ões) para Check Key;
        // 4. Implementar anotação(ões) para Foreign Key;
        // 5. Implementar anotação(ões) Insert, Update e Delete;
        // 6. Implementar anotação(ões) para Select / Filters;

    }

    @Deprecated
    public BigDecimal somar(BigDecimal n1,
                            BigDecimal n2) {
        return n1.add(n2);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
