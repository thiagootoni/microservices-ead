package com.ead.authuser.specifications;

import com.ead.authuser.models.CourseModel;
import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel>{}

    public static Specification<UserModel> userCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<UserModel, CourseModel> userProd = root.join("courses");
            return cb.equal(userProd.get("id"), courseId);
        };
    }

}
