package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class OpinionRepository {

    private final SessionFactory sessionFactory;

    public OpinionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Opinion save(Opinion entity) {
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

    public List<Opinion> findByUsuarioEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Opinion o JOIN FETCH o.pelicula WHERE o.usuarioEmail = :email", Opinion.class)
                    .setParameter("email", email)
                    .list();
        }
    }

    public List<Opinion> findOpinionesPuntuacionBajo(int score) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Opinion o JOIN FETCH o.pelicula WHERE o.puntuacion <= :score", Opinion.class)
                    .setParameter("score", score)
                    .list();
        }
    }
}
