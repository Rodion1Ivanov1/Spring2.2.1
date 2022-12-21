package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      CarService carService = context.getBean(CarService.class);
      UserService userService = context.getBean(UserService.class);
      Car car1 = new Car("Car1", 11);
      Car car2 = new Car("Car2", 12);
      Car car3 = new Car("Car3", 13);
      Car car4 = new Car("Car4", 14);

      
      userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", car3));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", car4));


      List<Car> cars = carService.listCars();
      List<User> users = userService.listUsers();
      int i = 0;
      for (User user : users) {
         user.setCar(cars.get(i++));
         System.out.println();
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car: "+user.getCar());
      }
      System.out.println();

      //метод для поиска юзера по номеру и серии машины
      List<User> users2 = userService.getUser("Car2", 12);
      for (User user : users2) {
         System.out.println();
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car: "+user.getCar());
      }

      context.close();
   }
}
