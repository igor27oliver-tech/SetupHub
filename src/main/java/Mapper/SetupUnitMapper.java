package Mapper;

import DTO.ImagemResponse;
import DTO.SetupUnitResponse;
import Entity.FilmeImagem;
import Entity.SetupUnit;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SetupUnitMapper {

    public SetupUnitResponse toResponse(SetupUnit setupUnit) {
        SetupUnitResponse response = new SetupUnitResponse();
        response.setId(setupUnit.getId());
        response.setNome(setupUnit.getNome());
        response.setFormulacao(setupUnit.getFormulacao());
        response.setKgh(setupUnit.getKgh());
        response.setTemperatura(setupUnit.getTemperatura());
        response.setVelocidadeDeRosca(setupUnit.getVelocidadeDeRosca());
        response.setVelocidadeDeArraste(setupUnit.getVelocidadeDeArraste()); // faltava no método original
        response.setDescricaoDoSetup(setupUnit.getDescricaoDoSetup());
        response.setImagens(setupUnit.getImagens().stream().map(this::toResponseImagem).toList());
        return response;
    }

    public ImagemResponse toResponseImagem(FilmeImagem i) {
        return new ImagemResponse(i.getId(), "/imagem/" + i.getId(), i.getNomeOperador(),
                i.getDescricao(), i.getDataUpload(),
                i.getSetup() == null ? null : i.getSetup().getId());
    }


    public List<SetupUnitResponse> toResponseList(List<SetupUnit> setupUnits) {
        if (setupUnits == null) {
            return Collections.emptyList();
        }
        return setupUnits.stream().map(this::toResponse).toList();
    }
}