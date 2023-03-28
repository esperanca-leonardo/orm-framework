package br.edu.fema.main;

import br.edu.fema.domain.model.Aluno;
import br.edu.fema.framework.utils.TableGenerator;
import br.edu.fema.framework.utils.TableGeneratorV2;

public class AppTableGenerator {
  public static void main(String[] args) {

    var aluno = Aluno.class;

    System.out.println(TableGeneratorV2.createTable(aluno));
    System.out.println();
    System.out.println(TableGeneratorV2.primaryKey(aluno));
    System.out.println();
    System.out.println(TableGeneratorV2.foreignKey(aluno));
    System.out.println();
    System.out.println(TableGeneratorV2.unique(aluno));
    System.out.println();
    System.out.println(TableGeneratorV2.check(aluno));
    System.out.println();
    System.out.println(TableGeneratorV2.insert(aluno));
    System.out.println();
    System.out.println(TableGeneratorV2.delete(aluno, "ra",
      "=", "1")
    );
    System.out.println();
    System.out.println(TableGeneratorV2.update(aluno, "ra", ">",
      "1")
    );
    System.out.println();
    System.out.println(TableGeneratorV2.select(aluno, "ra",
      "% 2 ==", "0")
    );
  }
}
