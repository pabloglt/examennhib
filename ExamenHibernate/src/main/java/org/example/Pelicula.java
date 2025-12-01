package org.example;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pelicula")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Opinion> opiniones = new ArrayList<>();

    public Pelicula(String titulo) {
        this.titulo = titulo;
    }

    public void anadirOpinion(Opinion opinion){
        opinion.setPelicula(this);
        this.opiniones.add(opinion);
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                " , titulo='" + titulo + '\'' +
                " , opiniones_count=" + (opiniones != null ? opiniones.size() : 0) +
                '}';
    }
}