package com.ead.authuser.models;

import com.ead.authuser.enuns.UserStatus;
import com.ead.authuser.enuns.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_users")
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String userName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 255)
    private String fullName;

    private String phoneNumber;
    private String cpf;
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime latsUpdateTime;

}
