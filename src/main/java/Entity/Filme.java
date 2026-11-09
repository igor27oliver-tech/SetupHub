package Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Filme {

    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) //automatico
    Long id;
    String LarguraDoFilme;
    String EspessuraDoFilme;
    String ComentarioDoFilme;
    String codigoMaterial;
    Integer maquina;




    @OneToMany(mappedBy = "filme",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List <FeedBack> feedBack = new ArrayList<>();



    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetupUnit> listSetupUnits = new ArrayList<>();

//    @OneToMany (mappedBy = "filme",cascade = CascadeType.ALL, orphanRemoval = true)
//    List<Picture>Pictures = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "filme_tags",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagsFilter> tagsFilters = new ArrayList<>();

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmeImagem> imagens = new ArrayList<>();

    @OneToOne(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private FilmeDocumentacao documentacao;
}
