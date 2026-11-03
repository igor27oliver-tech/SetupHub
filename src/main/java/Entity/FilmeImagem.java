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
public class FilmeImagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @Column(nullable = false)
    private String nomeArquivo;      // nome gerado (UUID) no disco
    private String nomeOriginal;
    @Column(nullable = false)
    private String contentType;

    private String nomeOperador;     // quem fez o upload
    @Column(length = 300)
    private String descricao;
    private LocalDateTime dataUpload;
}