package org.example;

import java.util.List;
import java.util.Optional;

public class DataService {

    private final PeliculaRepository peliculaRepository;
    private final OpinionRepository opinionRepository;

    public DataService(PeliculaRepository peliculaRepository, OpinionRepository opinionRepository) {
        this.peliculaRepository = peliculaRepository;
        this.opinionRepository = opinionRepository;
    }

    public Pelicula registrarNuevaPelicula(String titulo) {
        Pelicula nuevaPelicula = new Pelicula(titulo);
        return peliculaRepository.save(nuevaPelicula);
    }

    public List<Opinion> obtenerOpinionesPorUsuario(String email) {
        return opinionRepository.findByUsuarioEmail(email);
    }

    public Optional<Opinion> anadirOpinionAPelicula(Long peliculaId, String email, int puntuacion, String descripcion) {

        Optional<Pelicula> peliculaOpt = peliculaRepository.findById(peliculaId);

        if (peliculaOpt.isPresent()) {
            Pelicula pelicula = peliculaOpt.get();

            Opinion nuevaOpinion = new Opinion(null, descripcion, email, puntuacion, null);
            pelicula.anadirOpinion(nuevaOpinion);

            Opinion opinionGuardada = opinionRepository.save(nuevaOpinion);

            return Optional.of(opinionGuardada);
        } else {
            return Optional.empty();
        }
    }

    public List<Opinion> listarOpinionesConPuntuacionBaja() {
        return opinionRepository.findOpinionesPuntuacionBajo(3);
    }
}