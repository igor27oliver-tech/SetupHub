package DTO;

import java.time.LocalDateTime;

public record ImagemResponse(Long id, String url, String nomeOperador, String descricao, LocalDateTime dataUpload,
                             Long idSetup) {}