package Service;

import DTO.TagRequest;
import DTO.TagResponse;
import Entity.Filme;
import Entity.TagsFilter;
import Mapper.RegisterMapper;
import Repository.RegistersDeFilmes;
import Repository.TagsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class TagsService {
    @Autowired
    private TagsRepository tagsRepository;


    @Autowired
    private RegistersDeFilmes registersDeFilmes;

    @Autowired
    private RegisterMapper registerMapper;

    public TagResponse newTag (TagRequest tagRequest) {
        return registerMapper.toResponseTag(registerMapper.toEntityTag(tagRequest));
    }
    public List<TagResponse> listTags() {
        List<TagsFilter> tagsResponse = tagsRepository.findAll();
        return registerMapper.toResponseTags(tagsResponse);

    }
    // adiciona tag a filme ja existente
    @Transactional
    public TagResponse addTag(Long id, TagRequest tagRequest) {
        Filme filme = registersDeFilmes.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado: " + id));

        String nome = tagRequest.nameForTag().trim();

        // reaproveita a tag existente; só cria se ainda não existir
        TagsFilter tag = tagsRepository.findByNomeIgnoreCase(nome)
                .orElseGet(() -> tagsRepository.save(registerMapper.toEntityTag(new TagRequest(nome))));

        List<TagsFilter> tags = filme.getTagsFilters();
        boolean jaTem = tags.stream().anyMatch(t -> t.getId().equals(tag.getId()));
        if (!jaTem) {
            tags.add(tag);
            registersDeFilmes.save(filme);
        }

        return registerMapper.toResponseTag(tag);
    }

}
