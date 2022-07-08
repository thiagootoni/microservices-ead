package com.ead.course.models;

import com.ead.course.enuns.UserStatus;
import com.ead.course.enuns.UserType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable=false)
    private UserStatus userStatus;

    @Column(nullable=false)
    private UserType userType;

    @Column(length = 20)
    private String cpf;

    @Column
    private String imageUrl;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<CourseModel> courses;
}
