package Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetupUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 private String nome;
 private String descrition;
  private  String kgh;
  private String VelocidadeDeRosca;
   private String VelocidadeDeArraste;
  private  String Temperatura;
   private String descricaoDoSetup;
 private  LocalDateTime dataDeCadastro;



    @ManyToOne
    @JoinColumn(name = "filme_id")
    private Filme filme;

    @OneToMany(mappedBy = "setup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmeImagem> imagens = new ArrayList<>();



    @Convert(converter = FormulacaoConverter.class)
    @Column(columnDefinition = "CLOB")
    private Formulacao formulacao;

}
