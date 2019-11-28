package com.kemal.spring.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Created by Keno&Kemo on 04.11.2017..
 */
@Entity
@Table(name = "ROL_APP")
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceRole")
    @SequenceGenerator(name = "id_SequenceRole", sequenceName = "SQ_RET_ROLE", allocationSize= 1)
    private Long id;

    @com.kemal.spring.customAnnotations.ValidRoleName
    @Column(unique = true)
    private String name;

    @JsonManagedReference
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String name) {
        super();
        this.name = name;
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
