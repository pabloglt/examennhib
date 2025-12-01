package org.example;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="opinion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    @Column(name="usuario")
    private String usuarioEmail;
    private Integer puntuacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pelicula_id")
    private Pelicula pelicula;


    @Override
    public String toString() {
        return "Opinión {" +
                "id=" + id +
                ", usuario='" + usuarioEmail + '\'' +
                ", puntuación=" + puntuacion +
                ", descripción='" + descripcion  +
                ", película_id=" + (pelicula != null ? pelicula.getId() : "null") +
                '}';
    }
}
