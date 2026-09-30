//package com.example.blog.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotNull;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDate;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Entity
//@Table(name = "posts")
//@Getter
//@Setter
//@EntityListeners(AuditingEntityListener.class)
//@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//public class Post implements BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    @ToString.Include
//    @EqualsAndHashCode.Include
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @NotNull
//    private User author;
//
//    @Column(unique = true)
//    @NotNull
//    @ToString.Include
//    private String slug;
//
//    @NotNull
//    @ToString.Include
//    private String name;
//
//    @NotNull
//    @Column(columnDefinition = "TEXT")
//    @ToString.Include
//    private String body;
//
//    @LastModifiedDate
//    private LocalDate updatedAt;
//
//    @CreatedDate
//    private LocalDate createdAt;
//}
package com.example.blog.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "posts")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post implements BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @NotNull
    private User author;

    @Column(unique = true)
    @NotNull
    @ToString.Include
    private String slug;

    @NotNull
    @ToString.Include
    private String name;

    @NotNull
    @Column(columnDefinition = "TEXT")
    @ToString.Include
    private String body;

    @LastModifiedDate
    private LocalDate updatedAt;

    @CreatedDate
    private LocalDate createdAt;
}