package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> listUsers();
   User findOwner(String car_name, int car_series);

   void deleteAllUsers();
   void deleteAllCars();

}
