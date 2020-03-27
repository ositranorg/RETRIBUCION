package com.kemal.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

//    @JsonManagedReference
//    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
//    private Set<User> users = new HashSet<>();

    
}
