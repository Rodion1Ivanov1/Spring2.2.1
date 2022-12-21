package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserServiceImp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

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
   public List<User> getUser(String model, int series) {
      TypedQuery<Car> findCarQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :car_name and series = :car_series")
              .setParameter("car_name", model)
              .setParameter("car_series", series);
      List<Car> findCarList = findCarQuery.getResultList();
      if (!findCarList.isEmpty()) {
         Car findCar = findCarList.get(0);
         TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where car = :car_nomer")
                 .setParameter("car_nomer", findCar);
         return query.getResultList();
      }
      return null;
   }

}
