package DTO;

import Entity.Formulacao;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetupUnitResponse {
    private Long id;
    private String nome;
    private String kgh;
    private String velocidadeDeRosca;
    private String velocidadeDeArraste;
    private String temperatura;
    private Formulacao  formulacao;
    private String descricaoDoSetup;
    private List<ImagemResponse> imagens;


}