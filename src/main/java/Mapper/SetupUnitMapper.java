package Mapper;

import DTO.SetupUnitResponse;
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
        return response;
    }

    public List<SetupUnitResponse> toResponseList(List<SetupUnit> setupUnits) {
        if (setupUnits == null) {
            return Collections.emptyList();
        }
        return setupUnits.stream().map(this::toResponse).toList();
    }
}