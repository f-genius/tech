package ru.shev.daoServices;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.shev.entities.Cat;
import ru.shev.entities.Owner;
import ru.shev.util.CatSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAOServiceImpl implements OwnerDAOService {
    @Override
    public Integer addOwner(Owner newOwner) {
        Integer id = null;
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            session.beginTransaction();
            id = (Integer) session.save(newOwner);
            newOwner.setId(id);
            session.update(newOwner);
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List getAllOwners() {
        List owners = new ArrayList();
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Owner> criteria = builder.createQuery(Owner.class);
            criteria.from(Owner.class);
            owners = session.createQuery(criteria).getResultList();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return owners;
    }

    @Override
    public Owner getOwner(Integer id) {
        Owner owner = null;
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            owner = session.get(Owner.class, id);
            if (owner == null)
                System.out.println("Can't find the cat");
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return owner;
    }

    @Override
    public void updateOwner(Integer id, String name) {
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            Owner owner = session.get(Owner.class, id);
            if (owner != null) {
                owner.setName(name);
                session.beginTransaction();

                session.update(owner);
                session.getTransaction().commit();
            }
            else {
                throw new NullPointerException();
            }
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOwner(Integer id) {
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            Owner owner = session.get(Owner.class, id);
            for (Cat cat : owner.getCats()) {
                cat.setOwner(null);
                session.beginTransaction();
                session.update(cat);
                session.getTransaction().commit();
            }
            session.beginTransaction();
            session.delete(owner);
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
