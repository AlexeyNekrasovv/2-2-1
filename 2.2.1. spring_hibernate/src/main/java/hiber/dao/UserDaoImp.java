package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   public void deleteAllUsers() {
      Session session = sessionFactory.getCurrentSession();
      session.createQuery("DELETE FROM User").executeUpdate();
   }

   @Override
   public User findOwner(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      return session.createQuery("FROM User u WHERE u.car.model = :model AND u.car.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult();
   }
}
