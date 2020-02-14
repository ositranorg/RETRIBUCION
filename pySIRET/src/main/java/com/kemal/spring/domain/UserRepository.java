package com.kemal.spring.domain;

import java.util.List;

import javax.persistence.NamedStoredProcedureQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kemal.spring.domain.nonentity.CambiarClave;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndEnabled(String username,boolean enable);
    User findByUsernameAndPasswordAndEnabled(String username,String password,boolean enable);
    User findByEmailAndIdNot(String email, Long id);
    User findByEmailAndConcesionarioAndEnabled(String email, Concesionario concesionario,boolean enable);
    User findByUsernameAndIdNot(String username, Long id);

    Page<User> findByUsernameContainingOrderByIdAsc(String username, Pageable pageable);

    Page<User> findByEmailContainingOrderByIdAsc(String email, Pageable pageable);

    Page<User> findByNameContainingOrderByIdAsc(String name, Pageable pageable);

    Page<User> findBySurnameContainingOrderByIdAsc(String surname, Pageable pageable);

    //region Find eagerly
    //==========================================================================
    @Query("SELECT u FROM User u JOIN FETCH u.roles")
    List<User> findAllEagerly();

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = (:email)")
    User findByEmailEagerly(@Param("email") String email);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = (:id)")
    User findByIdEagerly(@Param("id") Long id);
    //==========================================================================
    //endregion

    @Procedure(name = "cambiarClave")
    CambiarClave cambiarClave(String CLAVEANTERIOR,String NUEVACLAVE,String CONFIRMARCLAVE);
 
}
