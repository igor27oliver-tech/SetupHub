package Repository;

import Entity.FilmeDocumentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmeDocumentacaoRepository extends JpaRepository<FilmeDocumentacao, Long> {
    Optional<FilmeDocumentacao> findByFilmeId(Long idFilme);
}