package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

//import static jdk.internal.org.jline.utils.InfoCmp.Capability.user1;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.deleteAllUsers();

      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      user1.setCar(new Car("LADA", 2101));
      userService.add(user1);
      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      user2.setCar(new Car("LADA", 2102));
      userService.add(user2);
      User user3 = new User("User3", "Lastname3", "user3@mail.ru");
      user3.setCar(new Car("LADA", 2103));
      userService.add(user3);
      User user4 = new User("User4", "Lastname4", "user4@mail.ru");
      user4.setCar(new Car("LADA", 2104));
      userService.add(user4);


      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println(user.getCar());
         System.out.println();
      }

      User findUser = userService.findOwner("LADA", 2103);
      System.out.println("Владелец автомобиля LADA 2103");
      System.out.println(findUser.getFirstName());
      System.out.println(findUser.getLastName());

      context.close();
   }
}
