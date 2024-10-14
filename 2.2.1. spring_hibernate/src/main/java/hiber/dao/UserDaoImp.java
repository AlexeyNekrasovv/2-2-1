package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findOwner(String car_name, String car_series) {
      // Создаем HQL-запрос для поиска пользователя по модели и серии автомобиля
      String hql = "SELECT u FROM User u JOIN u.car c WHERE c.name = :car_name AND c.series = :car_series";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("car_name", car_name);
      query.setParameter("car_series", car_series);

      // Выполняем запрос и получаем результат
      List<User> users = query.getResultList();
      return users.isEmpty() ? null : users.get(0);
   }

}
