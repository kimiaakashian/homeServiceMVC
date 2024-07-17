package ir.home_service.service;


import ir.home_service.exception.*;
import ir.home_service.helper.DateConvertorNew;
import ir.home_service.model.Customer;
import ir.home_service.model.Expert;
import ir.home_service.model.Users;
import ir.home_service.model.enums.ExpertStatus;
import ir.home_service.repository.ExpertRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertService {
    private final ExpertRepository expertRepository;
    private final EntityManager entityManager;

    public Expert findById(int expertId) {
        return expertRepository.findById(expertId).orElseThrow(
                () -> new NotFoundException("متخصص با این شناسه یافت نشد"));
    }

    public List<Expert> findExperts(String firstName, String lastName, String email , Double score) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expert> cq = cb.createQuery(Expert.class);

        Root<Expert> expert = cq.from(Expert.class);
        List<Predicate> predicates = new ArrayList<>();

        if (firstName!= null) {
            predicates.add(cb.like(expert.get("firstName"), "%" + firstName + "%"));
        }
        if (lastName!= null) {
            predicates.add(cb.like(expert.get("lastName"), "%" + lastName + "%"));
        }
        if (email!= null) {
            predicates.add(cb.equal(expert.get("email"), email));
        }
        if (score!= null) {
            predicates.add(cb.greaterThan(expert.get("averageScore"), score));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }

    public Expert ExpertRegistration(Expert expert)  {

        if (checkData("firstName", expert.getFirstName(), "^[a-zA-Z]+$")
                && checkData("lastName", expert.getLastName(), "^[a-zA-Z]+$")
                && checkData("email", expert.getEmail(), "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
                && checkData("password", expert.getPassword(), "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")

        ) {
            expert.setDateOfRegistration(DateConvertorNew.todayDate());
            try {
                expert.setExpertStatus(ExpertStatus.جدید);
                expertRepository.save(expert);

            }
            catch (Exception e) {
                String message = e.getMessage().split("\\[")[1];
                if (message.indexOf("email") > 0)
                    throw new InformationDuplicateException("کاربر تکراری است");

                else
                    throw new InformationDuplicateException(message);
            }

            }
        return expert;
    }

    private boolean checkData(String fieldName, String fieldValue, String regx) {
        boolean isCorrect = false;
        if (fieldValue == null || !fieldValue.matches(regx) || fieldValue.isEmpty()) {
            throw new InvalidInformationException("اطلاعات وارد شده برای فیلد "
                    + fieldName + " صحیح نمی باشد!");
        } else {
            isCorrect = true;
        }
        return isCorrect;
    }

    public boolean isValidPicture(String picturePath)  {
        boolean isValid = false;
        if (picturePath.indexOf(".") <= 0 || picturePath.split("\\.")[1].equalsIgnoreCase("JPG")) {
            File imageFile = new File(picturePath);
            if (imageFile.exists() && imageFile.length() <= 300000) {
                isValid = true;
            } else {
                if (!imageFile.exists()) {
                    throw new NotFoundException("فایل مورد نظر یافت نشد!");
                } else if (imageFile.length() > 300000) {
                    throw new InvalidSizeException("حجم فایل بیشتر از  300K می باشد!");
                }
            }
        } else {
            throw new InvalidTypeException("فقط فایل با پسوند JPG مورد قبول می باشد!");
        }
        return isValid;
    }

    public void updateExpertStatusToVerified(Integer expertId) {
        Expert expert = expertRepository.findById(expertId).orElseThrow(() -> new NotFoundException("کاربر با این شناسه موجود نیست"));
        if (expert.getExpertStatus().equals("تایید_شده")) {
            throw new InvalidStatusException("این کاربر قبلا تایید شده است");
        }
        expert.setExpertStatus(ExpertStatus.تایید_شده);
        expertRepository.save(expert);
    }

}
