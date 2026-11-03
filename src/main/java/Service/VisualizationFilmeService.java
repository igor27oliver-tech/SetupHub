package Service;

import DTO.FilmeResponse;
import DTO.TagRequest;
import DTO.ViewForName;
import Entity.Filme;
import Entity.TagsFilter;
import Mapper.FilmeMapper;
import Mapper.RegisterMapper;
import Repository.RegistersDeFilmes;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisualizationFilmeService {

    private final RegistersDeFilmes registersDeFilmes;
    private final FilmeMapper filmeMapper;
    private final RegisterMapper registerMapper;

    @Transactional(readOnly = true)
    public List<FilmeResponse> getAllFilmes() {
        return filmeMapper.toResponseList(registersDeFilmes.findAll());
    }

    @Transactional(readOnly = true)
    public List<ViewForName> getAllViewForName() {
        return filmeMapper.toViewForNameList(registersDeFilmes.findAll());
    }

    @Transactional(readOnly = true)
    public FilmeResponse getUnit(Long idFilme) {
        Filme filme = registersDeFilmes.findById(idFilme)
                .orElseThrow(() -> new EntityNotFoundException("Filme não encontrado"));
        return filmeMapper.toResponse(filme);
    }
    @Transactional (readOnly = true)
    public List<FilmeResponse> findByTag(TagRequest tagRequest) {
        return registersDeFilmes
                .findByTagsFiltersNomeIgnoreCase(tagRequest.nameForTag().trim())
                .stream()
                .map(registerMapper::toResponseFilme)   // use o método de mapeamento que você já tem para Filme -> FilmeResponse
                .toList();
    }

}