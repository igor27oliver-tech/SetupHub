package DTO;

import java.util.List;

public record RegisterNewFilmeDTO(String tamanho, String espessuraFilme, String comentarioDoFilme, String codigoFilme,
                                  List<TagRequest>list,Integer maquina) {
}
