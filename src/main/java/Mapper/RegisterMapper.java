package Mapper;

import DTO.*;
import Entity.FeedBack;
import Entity.Filme;
import Entity.SetupUnit;
import Entity.TagsFilter;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component

public class RegisterMapper {


    public SetupUnit toEntitySetup(RegisterNewSetup registerNewSetup) {
        SetupUnit setupUnit = new SetupUnit();
        setupUnit.setDescricaoDoSetup(registerNewSetup.descricaoDoSetup());
        setupUnit.setKgh(registerNewSetup.kgh());
        setupUnit.setNome(registerNewSetup.nomeOperador());
        setupUnit.setFormulacao(registerNewSetup.formulacao());
        setupUnit.setTemperatura(registerNewSetup.temperatura());
        setupUnit.setVelocidadeDeArraste(registerNewSetup.velocidadeDeArraste());
        setupUnit.setVelocidadeDeRosca(registerNewSetup.velocidadeDeRosca()); // adicionar se existir no record
        setupUnit.setDataDeCadastro(LocalDateTime.now()); // se quiser preencher automaticamente
        return setupUnit;

    }

    public SetupUnitResponse toResponseSetupUnit(@NotNull SetupUnit setupUnit) {
        SetupUnitResponse setupUnitResponse = new SetupUnitResponse();
        setupUnitResponse.setId(setupUnit.getId());
        setupUnitResponse.setNome(setupUnit.getNome());
        setupUnitResponse.setKgh(setupUnit.getKgh());
        setupUnitResponse.setVelocidadeDeRosca(setupUnit.getVelocidadeDeRosca());
        setupUnitResponse.setVelocidadeDeArraste(setupUnit.getVelocidadeDeArraste());
        setupUnitResponse.setTemperatura(setupUnit.getTemperatura());
        setupUnitResponse.setFormulacao(setupUnit.getFormulacao());
        setupUnitResponse.setDescricaoDoSetup(setupUnit.getDescricaoDoSetup());
        return setupUnitResponse;


    }

    public FilmeResponse toResponseFilme(Filme filme) {
        FilmeResponse filmeResponse = new FilmeResponse();
        filmeResponse.setId(filme.getId());
        filmeResponse.setTamanhoDoFilme(filme.getLarguraDoFilme());
        filmeResponse.setEspessuraDoFilme(filme.getEspessuraDoFilme());
        filmeResponse.setId(filme.getId());
        filmeResponse.setFeedBack(toResponseFeedBack(filme.getFeedBack()));
        filmeResponse.setComentarioDoFilme(filme.getComentarioDoFilme());
        List<SetupUnitResponse> listSetupUnitResponse = new ArrayList<>();
        for (SetupUnit setupUnit : filme.getListSetupUnits()) {
            SetupUnitResponse setupUnitResponse = toResponseSetupUnit(setupUnit);
            listSetupUnitResponse.add(setupUnitResponse);
        }
        filmeResponse.setListSetupUnits(listSetupUnitResponse);
        return filmeResponse;

    }

    public List<FeedBaackResponse> toResponseFeedBack(List<FeedBack> feedBacks) {
        List<FeedBaackResponse> feedBackResponses = new ArrayList<>();
        FeedBaackResponse feedBaackResponse = new FeedBaackResponse();
        for (FeedBack feedBack : feedBacks) {
            feedBaackResponse.setId(feedBack.getId());
            feedBaackResponse.setNomeUsuario(feedBack.getNomeUsuario());
            feedBaackResponse.setData(feedBack.getData());
            feedBaackResponse.setComentario(feedBack.getComentario());
            feedBackResponses.add(feedBaackResponse);
        }
        return feedBackResponses;


    }

    public TagsFilter toEntityTag(TagRequest request) {
        TagsFilter tagsFilter = new TagsFilter();
        tagsFilter.setNome(request.nameForTag());
        return tagsFilter;


    }

    public TagResponse toResponseTag(TagsFilter tagsFilter) {
        TagResponse tagResponse = new TagResponse();
        tagResponse.setId(tagsFilter.getId());
        tagResponse.setName(tagsFilter.getNome());
        return tagResponse;


    }

    public List<TagResponse> toResponseTags(List<TagsFilter> tagsFilters) {
        List<TagResponse> tagsResponse = new ArrayList<>();
       for (TagsFilter tagsFilter : tagsFilters) {
           TagResponse tagResponse = new TagResponse();
           tagResponse.setId(tagsFilter.getId());
           tagResponse.setName(tagsFilter.getNome());
           tagsResponse.add(tagResponse);
       }
       return tagsResponse;

       }





      }


