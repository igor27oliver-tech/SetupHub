package Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
 public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;
       private String comentario;
       private LocalDateTime data;
       private String NomeUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id")
    @JsonBackReference
    private Filme filme;



 }
