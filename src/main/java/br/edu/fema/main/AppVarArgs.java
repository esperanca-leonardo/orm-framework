package br.edu.fema.main;

import java.math.BigDecimal;

public class AppVarArgs {

    public static BigDecimal somar(BigDecimal n1, BigDecimal n2,
                                   BigDecimal... numbers) {
        BigDecimal resultado = n1.add(n2);
        if (numbers.length > 0) {
            for (BigDecimal number : numbers) {
                resultado = resultado.add(number);
            }
        }
        return resultado;
    }
    public static void main(String[] args) {
        final BigDecimal resultado =
                somar(BigDecimal.valueOf(10), BigDecimal.valueOf(20),
                        BigDecimal.valueOf(30), BigDecimal.valueOf(40));
        System.out.println(resultado);
    }
}
