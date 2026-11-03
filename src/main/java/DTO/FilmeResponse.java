package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FilmeResponse {
    private Long id;
    private String tamanhoDoFilme;
    private String espessuraDoFilme;
    private String  comentarioDoFilme;
    private List<SetupUnitResponse> listSetupUnits;
    private List<FeedBaackResponse> feedBack;
    private String codigoMaterial;
    private List<TagResponse> tags;
    List<ImagemResponse> imagens;
}
//filme.setLarguraDoFilme(registerRequest.tamanhoDoFilme());
//        filme.setEspessuraDoFilme(registerRequest.espessuraDoFilme());
//        filme.setKgh(registerRequest.kgh());
//        filme.setVelocidadeDeRosca(registerRequest.VelocidadeDeRosca());
//        filme.setVelocidadeDeArraste(registerRequest.VelocidadeDeArraste());
//        filme.setTemperatura(registerRequest.Temperatura());
//        filme.setResina(registerRequest.Resina());
//        filme.setDescricaoDoSetup(registerRequest.descricaoDoSetup());
//        filme.setDataDeCadastro(LocalDateTime.now());


