package DTO;

import java.time.LocalDateTime;

public record DocumentacaoResponse(Long idFilme, String conteudo, String atualizadoPor,
                                   LocalDateTime atualizadoEm, Long versao) {}