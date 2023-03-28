package br.edu.fema.domain.model;

import br.edu.fema.framework.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Aluno {

  public static final Long COUNTER = 1L;

  @Column
  @PrimaryKey(constraintName = "Pk_Aluno", name = "ra")
  @Unique(constraintName = "UC_Aluno")
  private Long ra;

  @Column
  @Unique(constraintName = "UC_Aluno")
  @PrimaryKey(constraintName = "Pk_Aluno", name = "ra")
  private String nome;

  private LocalDate dataNascimento;

  @Column
  @Check(operation = ">=", value ="0", constraintName = "Check_Aluno")
  private BigDecimal mensalidade;

  @Check(operation = "!=", value ="null", constraintName = "Check_Aluno_Matriculado")
  private boolean matriculado;

  private List<String> disciplinas;

  private Map<String, List<BigDecimal>> notas;

  @Column
  @ForeignKey(table = "Professor", column = "ProfessorId", constraintName = "FK_Aluno_Professor")
  private Professor professor;

  @Column
  @ForeignKey(table = "Sala", column = "salaId", constraintName = "FK_Aluno_Sala")
  private Sala sala;

  public Aluno() {}

  public Aluno(long ra, String nome) {
    this.ra = ra;
    this.nome = nome;
  }

  public Aluno(long ra, String nome, BigDecimal mensalidade)
      throws NullPointerException, IllegalArgumentException {

    Objects.requireNonNull(mensalidade);

    if (mensalidade.compareTo(BigDecimal.valueOf(100.00)) < 0) {
      throw new IllegalArgumentException("Mensalidade deve ser maior que R$ 100.00");
    }

    this.ra = ra;
    this.nome = nome;
    this.mensalidade = mensalidade;
  }

  public long getRa() {
    return ra;
  }

  public void setRa(long ra) {
    this.ra = ra;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public BigDecimal getMensalidade() {
    return mensalidade;
  }

  public void setMensalidade(BigDecimal mensalidade) {
    this.mensalidade = mensalidade;
  }

  public Professor getProfessor() {
    return professor;
  }

  public void setProfessor(Professor professor) {
    this.professor = professor;
  }

  public void adicionarDisciplina(String disciplina)
      throws NullPointerException, IllegalArgumentException {

    Objects.requireNonNull(disciplina);

    if ("".equals(disciplina.trim())) {
      throw new IllegalArgumentException("Disciplina não pode ser vazia.");
    }
    if (Objects.isNull(disciplinas)) {
      disciplinas = new ArrayList<>();
    }
    if (disciplinas.contains(disciplina)) {
      throw new IllegalArgumentException("Disciplina não pode ser repetida.");
    }
    disciplinas.add(disciplina);
  }

  public List<String> getDisciplinas() {
    return Collections.unmodifiableList(disciplinas);
  }

  @Override
  public String toString() {
    return ra + " - " + nome + " - " + mensalidade;
  }
}
