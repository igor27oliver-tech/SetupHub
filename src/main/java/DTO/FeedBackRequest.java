package DTO;

import Entity.Filme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FeedBackRequest {
    private String comentario;
    private LocalDateTime data;
    private String nomeUsuario;
    private Long idFilme;
}
