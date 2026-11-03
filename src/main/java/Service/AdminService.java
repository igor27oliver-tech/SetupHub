package Service;

import DTO.FilmeResponse;
import Entity.Filme;
import Mapper.FilmeMapper;
import Repository.RegistersDeFilmes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
            @Autowired
            FilmeMapper mapper;
            @Autowired
    RegistersDeFilmes registersDeFilmes;

  public   FilmeResponse deletFilme (Long id) {
      Filme filme = registersDeFilmes.findById(id).orElse(null);
      mapper.toResponse(filme);
      registersDeFilmes.delete(filme);
      return mapper.toResponse(filme);

    }






}
