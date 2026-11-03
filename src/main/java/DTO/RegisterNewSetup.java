package DTO;

import Entity.Formulacao;
import Entity.Resina;

import java.util.List;

public record RegisterNewSetup (Long  idFilme, String tamanhoDoFilme, String espessuraDoFilme, String kgh, String velocidadeDeRosca, String velocidadeDeArraste, String temperatura,
                                Formulacao formulacao, String descricaoDoSetup, String dataDeCadastro, String nomeOperador) {

}
/// exemplo de como ficaria o json:
/// L