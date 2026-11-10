package Repository;

import Entity.Filme;
import Entity.TagsFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistersDeFilmes extends JpaRepository<Filme, Long> {


    @Override
    Optional<Filme> findById(Long aLong);

    List <Filme> findByLarguraDoFilmeAndEspessuraDoFilme(String tamanhoDoFilme, String espessuraDoFilme);
   
    List <Filme> findByLarguraDoFilme(String tamanhoDoFilme);



     Optional <Filme >findByEspessuraDoFilme(String espessuraDoFilme);

    // FilmeRepository
    List<Filme> findByTagsFiltersNomeIgnoreCase(String nome);

    // usados para impedir filme repetido (mesmo código na mesma máquina)
    boolean existsByCodigoMaterialIgnoreCaseAndMaquina(String codigoMaterial, Integer maquina);

    boolean existsByCodigoMaterialIgnoreCaseAndMaquinaAndIdNot(String codigoMaterial, Integer maquina, Long id);

}
