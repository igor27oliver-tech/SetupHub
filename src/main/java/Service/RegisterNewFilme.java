package Service;

import DTO.*;
import Entity.Filme;
import Entity.SetupUnit;
import Entity.TagsFilter;
import Mapper.FilmeMapper;
import Mapper.RegisterMapper;
import Repository.RegistersDeFilmes;

import java.util.ArrayList;
import java.util.List;

import Repository.SetupUnitRepository;
import Repository.TagsRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegisterNewFilme {
    @Autowired
    RegistersDeFilmes registersDeFilmes;
    @Autowired
    RegisterMapper mapper;
    @Autowired
    FilmeMapper filmeMapper;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    SetupUnitRepository setupUnitRepository;


    public FilmeResponse registerNewSetup (@NonNull RegisterNewSetup  registerNewSetup) {
       Filme filme = registersDeFilmes.findById(registerNewSetup.idFilme())
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado: " + registerNewSetup.idFilme()));
      SetupUnit setupUnit = mapper.toEntitySetup(registerNewSetup);
      setupUnit.setFilme(filme);
      filme.getListSetupUnits().add(setupUnit);
        registersDeFilmes.save(filme);
       return mapper.toResponseFilme(filme);
    }

    @Transactional
    public FilmeResponse registerNewFilme(@NonNull RegisterNewFilmeDTO dto) {
        String codigo = codigoObrigatorio(dto.codigoFilme());
        if (registersDeFilmes.existsByCodigoMaterialIgnoreCaseAndMaquina(codigo, dto.maquina())) {
            throw filmeRepetido(codigo, dto.maquina());
        }

        Filme filme = new Filme();
        preencherFilme(filme, dto, codigo);
        filme.setTagsFilters(resolverTags(dto.list()));

        Filme salvo = registersDeFilmes.save(filme);
        return mapper.toResponseFilme(salvo);
    }

    /** Edita os dados do filme. Se {@code list} vier nulo, as tags atuais são mantidas. */
    @Transactional
    public FilmeResponse editarFilme(Long idFilme, @NonNull RegisterNewFilmeDTO dto) {
        Filme filme = registersDeFilmes.findById(idFilme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado: " + idFilme));

        String codigo = codigoObrigatorio(dto.codigoFilme());
        if (registersDeFilmes.existsByCodigoMaterialIgnoreCaseAndMaquinaAndIdNot(codigo, dto.maquina(), idFilme)) {
            throw filmeRepetido(codigo, dto.maquina());
        }

        preencherFilme(filme, dto, codigo);
        if (dto.list() != null) {
            filme.getTagsFilters().clear();
            filme.getTagsFilters().addAll(resolverTags(dto.list()));
        }
        return filmeMapper.toResponse(registersDeFilmes.save(filme));
    }

    /** Edita um setup existente. Data de cadastro e imagens do setup são mantidas. */
    @Transactional
    public FilmeResponse editarSetup(Long idSetup, @NonNull RegisterNewSetup dto) {
        SetupUnit setup = setupUnitRepository.findById(idSetup)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setup não encontrado: " + idSetup));

        setup.setNome(dto.nomeOperador());
        setup.setKgh(dto.kgh());
        setup.setVelocidadeDeRosca(dto.velocidadeDeRosca());
        setup.setVelocidadeDeArraste(dto.velocidadeDeArraste());
        setup.setTemperatura(dto.temperatura());
        setup.setFormulacao(dto.formulacao());
        setup.setDescricaoDoSetup(dto.descricaoDoSetup());

        setupUnitRepository.save(setup);
        return filmeMapper.toResponse(setup.getFilme());
    }

    private void preencherFilme(Filme filme, RegisterNewFilmeDTO dto, String codigo) {
        filme.setCodigoMaterial(codigo);
        filme.setMaquina(dto.maquina());
        filme.setLarguraDoFilme(dto.tamanho());
        filme.setEspessuraDoFilme(dto.espessuraFilme());
        filme.setComentarioDoFilme(dto.comentarioDoFilme());
    }

    private String codigoObrigatorio(String codigo) {
        String c = codigo == null ? "" : codigo.trim();
        if (c.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o código do filme.");
        }
        return c;
    }

    private ResponseStatusException filmeRepetido(String codigo, Integer maquina) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Já existe um filme com o código " + codigo
                        + (maquina != null ? " na máquina " + maquina : "") + ".");
    }

    private List<TagsFilter> resolverTags(List<TagRequest> pedidos) {
        List<TagsFilter> tags = new ArrayList<>();
        if (pedidos == null) return tags;
        for (TagRequest req : pedidos) {
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
        return tags;
    }
}
