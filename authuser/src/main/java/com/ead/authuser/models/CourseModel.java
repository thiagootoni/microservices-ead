package com.ead.authuser.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_courses")
public class CourseModel {

    @Id
    private UUID id;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private Set<UserModel> users;
}
