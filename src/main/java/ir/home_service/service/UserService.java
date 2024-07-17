package ir.home_service.service;


import ir.home_service.exception.InvalidInformationException;
import ir.home_service.exception.InvalidPasswordException;
import ir.home_service.exception.NotFoundException;
import ir.home_service.model.Users;
import ir.home_service.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public Users userSignUp(Users users) {
        if (userRepository.findUserByEmail(users.getEmail()).isPresent())
            throw new NotFoundException(users.getEmail() + "تکراری است ");
        else
            userRepository.save(users);
        return users;
    }

    public Users signIn(String email, String passWord) {
        Optional<Users> userByEmail = userRepository.findUserByEmail(email);
        Optional<Users> userByPassword = userRepository.findByPassword(passWord);

        if (userByEmail.isPresent() && userByPassword.isPresent()) {
            if (userByEmail.get().getPassword().equals(passWord)) {

            } else {
                throw new InvalidInformationException("رمز عبور اشتباه است");
            }
        } else {
            throw new NotFoundException("کاربر پیدا نشد");
        }
        return userByPassword.get();
    }

    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        Users user = userRepository.findById(userId).orElseThrow();
        if (!user.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException("پسورد قبلی اشتباه است");
        }
        if (!isValidPassword(newPassword)) {
            throw new InvalidPasswordException("پسورد جدید معتبر نیست");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$");
    }


    public List<Users> findUsers(String firstName, String lastName, String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);

        Root<Users> user = cq.from(Users.class);
        List<Predicate> predicates = new ArrayList<>();

        if (firstName!= null) {
            predicates.add(cb.like(user.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName!= null) {
            predicates.add(cb.like(user.get("lastName"), "%" + lastName + "%"));
        }
        if (email!= null) {
            predicates.add(cb.equal(user.get("email"), email));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}

