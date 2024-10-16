package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
//      TypedQuery<User> query=sessionFactory.getCurrentSession()
//              //.createQuery("from User");
//                .createQuery("SELECT DISTINCT u FROM User u JOIN FETCH u.car", User.class);
//      return query.getResultList();

      Session session = sessionFactory.getCurrentSession();

      EntityGraph entityGraph = session.createEntityGraph(User.class);
      entityGraph.addAttributeNodes("car");

      TypedQuery<User> query = session.createQuery("FROM User", User.class)
              .setHint("javax.persistence.loadgraph", entityGraph);

      return query.getResultList();
   }
   @Override
   public void deleteAllUsers() {
      Session session = sessionFactory.getCurrentSession();
      session.createQuery("DELETE FROM User").executeUpdate();
   }

   @Override
   public void deleteAllCars() {
      Session session = sessionFactory.getCurrentSession();
      session.createQuery("DELETE FROM Car").executeUpdate();
   }

   @Override
   public User findOwner(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      return session.createQuery(
              //"FROM User u WHERE u.car.model = :model AND u.car.series = :series", User.class)
              "SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult();
   }

}
