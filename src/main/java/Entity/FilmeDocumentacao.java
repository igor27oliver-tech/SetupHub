package Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FilmeDocumentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filme_id", unique = true)
    private Filme filme;

    @Column(columnDefinition = "LONGTEXT")
    private String conteudo;

    private String atualizadoPor;
    private LocalDateTime atualizadoEm;

    @Version
    private Long versao;           // controla edição simultânea
}