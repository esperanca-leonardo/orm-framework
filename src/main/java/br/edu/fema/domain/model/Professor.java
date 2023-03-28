package br.edu.fema.domain.model;

import br.edu.fema.framework.annotations.Author;
import br.edu.fema.framework.annotations.Column;
import br.edu.fema.framework.annotations.Table;

import java.math.BigDecimal;

@Author(name = "Guilherme", since = 1, generated = true)
@Table("PROFESSORES")
public class Professor {
    @Column
    long matricula;
    @Column(value="NOME", size = 50)
    String nome;
    @Column("QTDE_ORIENTANDOS")
    int quantidadeOrientandos;
    @Column("ORIENTACOES_ANDAMENTO")
    boolean possuiOrientacoesEmAndamento;
    @Column(value="SALARIO_REAIS", size = 6, precision = 2)
    BigDecimal salario;
}
