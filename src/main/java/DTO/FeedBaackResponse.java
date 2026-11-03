package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedBaackResponse {
    private Long id;
    private String comentario;
    private LocalDateTime data;
    private String nomeUsuario;


}
