package org.example;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Optional;

public class PeliculaRepository {

    private final SessionFactory sessionFactory;

    public PeliculaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Pelicula save(Pelicula entity) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            if(entity.getId() == null) {
                session.persist(entity);
            } else {
                session.merge(entity);
            }
            session.getTransaction().commit();
            return entity;
        }
    }

    public Optional<Pelicula> findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            Integer intId = id.intValue();
            Pelicula pelicula = session.find(Pelicula.class, intId);

            if (pelicula != null) {
                Hibernate.initialize(pelicula.getOpiniones());
            }

            return Optional.ofNullable(pelicula);
        }
    }
}