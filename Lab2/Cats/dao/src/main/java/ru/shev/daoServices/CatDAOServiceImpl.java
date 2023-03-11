package ru.shev.daoServices;

import com.sun.istack.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import ru.shev.entities.Cat;
import ru.shev.entities.Owner;
import ru.shev.util.CatSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CatDAOServiceImpl implements CatDAOService{
    @Override
    public Integer addCat(Cat newCat) {
        Integer id = null;
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            session.beginTransaction();
            id = (Integer) session.save(newCat);
            newCat.setId(id);
            session.update(newCat);
            session.getTransaction().commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<Cat> getAllCats() {
        List cats = new ArrayList();
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cat> criteria = builder.createQuery(Cat.class);
            criteria.from(Cat.class);
            cats = session.createQuery(criteria).getResultList();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return cats;
    }

    @Override
    @Nullable
    public Cat getCat(Integer id) {
        Cat cat = null;
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            cat = session.get(Cat.class, id);
            if (cat == null)
                System.out.println("Can't find the cat");
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return cat;
    }

    @Override
    public void updateCat(Integer id, Owner owner) {
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()) {
            Cat cat = session.get(Cat.class, id);
            if (cat != null) {
                cat.setOwner(owner);
                session.beginTransaction();

                session.update(cat);
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
    public void deleteCat(Integer id) {
        try (Session session = CatSessionFactory.getSession().getSessionFactory().openSession()){
            Cat cat = session.get(Cat.class, id);
            if (cat != null) {
                session.beginTransaction();
                session.delete(cat);
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
}
