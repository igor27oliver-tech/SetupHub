package Mapper;

import DTO.FilmeResponse;
import DTO.ImagemResponse;
import DTO.ViewForName;
import Entity.Filme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FilmeMapper {
        @Autowired
        RegisterMapper mapper;
    private final SetupUnitMapper setupUnitMapper;
    private final FeedBackMapper feedBackMapper;

//    private final PictureMapper pictureMapper;

    public FilmeResponse toResponse(Filme filme) {
        FilmeResponse response = new FilmeResponse();
        response.setId(filme.getId());
        response.setComentarioDoFilme(filme.getComentarioDoFilme());
        response.setEspessuraDoFilme(filme.getEspessuraDoFilme());
        response.setTamanhoDoFilme(filme.getLarguraDoFilme());
        response.setListSetupUnits(setupUnitMapper.toResponseList(filme.getListSetupUnits()));
        response.setCodigoMaterial(filme.getCodigoMaterial());
        response.setFeedBack(feedBackMapper.toResponseList(filme.getFeedBack()));
        response.setTags(mapper.toResponseTags(filme.getTagsFilters()));
        response.setMaquina(filme.getMaquina());


        List<ImagemResponse> imagens = filme.getImagens().stream()
                .map(i -> new ImagemResponse(i.getId(), "/imagem/" + i.getId(),
                        i.getNomeOperador(), i.getDescricao(), i.getDataUpload(), i.getSetup() == null ? null : i.getSetup().getId()))
                .toList();
        response.setImagens(imagens);
        response.setImagens(
                filme.getImagens().stream()
                        .filter(i -> i.getSetup() == null)      // <- só as que não são de setup
                        .map(setupUnitMapper::toResponseImagem)
                        .toList());



        return response;
    }

    public List<FilmeResponse> toResponseList(List<Filme> filmes) {
        return filmes.stream().map(this::toResponse).toList();
    }

    // Resumo direto da entidade: não carrega setups nem feedbacks só para montar a lista.
    public ViewForName toViewForName(Filme filme) {
        ViewForName view = new ViewForName();
        view.setId(filme.getId());
        view.setComentario(filme.getComentarioDoFilme());
        view.setEspessura(filme.getEspessuraDoFilme());
        view.setTamanho(filme.getLarguraDoFilme());
        return view;
    }

    public List<ViewForName> toViewForNameList(List<Filme> filmes) {
        return filmes.stream().map(this::toViewForName).toList();
    }


}