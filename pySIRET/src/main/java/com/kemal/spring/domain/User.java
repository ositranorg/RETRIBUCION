package com.kemal.spring.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Created by Keno&Kemo on 30.09.2017..
 */
@NamedStoredProcedureQuery(
	    name = "cambiarClave", 
	    procedureName = "PK_USER.PRC_CAMBIARCLAVE", 	
	    //resultClasses = {User.class},
	    resultSetMappings = "CambiarClaveResult",
	    parameters = { 
	    	@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "CLAVEANTERIOR"), 
	        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "NUEVACLAVE"),
	        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "CONFIRMARCLAVE"),	        
	        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class, name = "cUsuario")
	    }
	)
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_APP")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_SequenceUser")
    @SequenceGenerator(name = "id_SequenceUser", sequenceName = "SQ_RET_USER", allocationSize= 1)
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(length = 60)
    private String password;

    private boolean enabled;
    
    @ManyToOne
	@JoinColumn(name="ncodigoPerfil")
	private Perfil perfil;
	
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ncodigocn")
	private Concesionario concesionario;
	
	
    @Column(length = 1)
    private String sTipoUsuario="E";
        
   
    
    private String sTelefono;
    
    @JsonBackReference
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

   
  
}
