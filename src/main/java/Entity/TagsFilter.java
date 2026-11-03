package Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TagsFilter {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "tagsFilters")   // aponta para o atributo da classe Filme
    private List<Filme> filmes = new ArrayList<>();
}
