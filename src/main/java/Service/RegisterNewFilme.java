package Service;

import DTO.*;
import Entity.Filme;
import Entity.SetupUnit;
import Entity.TagsFilter;
import Mapper.RegisterMapper;
import Repository.RegistersDeFilmes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Repository.TagsRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewFilme {
    @Autowired
    RegistersDeFilmes registersDeFilmes;
    @Autowired
    RegisterMapper mapper;
    @Autowired
    TagsRepository tagsRepository;


    public FilmeResponse registerNewSetup (@NonNull RegisterNewSetup  registerNewSetup) {
       Filme filme = registersDeFilmes.findById(registerNewSetup.idFilme()).orElseThrow(() -> new RuntimeException("erro"));
      SetupUnit setupUnit = mapper.toEntitySetup(registerNewSetup);
      setupUnit.setFilme(filme);
      filme.getListSetupUnits().add(setupUnit);
        registersDeFilmes.save(filme);
       return mapper.toResponseFilme(filme);
    }
    @Transactional
    public FilmeResponse registerNewFilme(@NonNull RegisterNewFilmeDTO registerNewFilmeDTO) {
        Filme filme = new Filme();
        filme.setEspessuraDoFilme(registerNewFilmeDTO.espessuraFilme());
        filme.setLarguraDoFilme(registerNewFilmeDTO.tamanho());
        filme.setComentarioDoFilme(registerNewFilmeDTO.comentarioDoFilme());
        filme.setCodigoMaterial(registerNewFilmeDTO.codigoFilme());
        filme.setMaquina(registerNewFilmeDTO.maquina());

        List<TagsFilter> tags = new ArrayList<>();
        if (registerNewFilmeDTO.list() != null) {
            for (TagRequest req : registerNewFilmeDTO.list()) {
                if (req == null || req.nameForTag() == null) continue;
                String nome = req.nameForTag().trim();
                if (nome.isEmpty()) continue;

                // reaproveita a tag existente; só cria se ainda não existir
                TagsFilter tag = tagsRepository.findByNomeIgnoreCase(nome)
                        .orElseGet(() -> tagsRepository.save(mapper.toEntityTag(new TagRequest(nome))));

                if (tags.stream().noneMatch(t -> t.getId().equals(tag.getId()))) {
                    tags.add(tag);
                }
            }
        }
        filme.setTagsFilters(tags);

        Filme salvo = registersDeFilmes.save(filme);
        return mapper.toResponseFilme(salvo);
    }
}
