package Repository;

import Entity.TagsFilter;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TagsRepository extends JpaRepository<TagsFilter, Long> {


    Optional<TagsFilter> findByNomeIgnoreCase(String nome);
}
