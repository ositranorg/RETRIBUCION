package com.kemal.spring.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Concesionario;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.UserRepository;
import com.kemal.spring.domain.procedures.AlertaCambiarClave;
import com.kemal.spring.web.dto.Util;

@Service
public class UsuarioService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	Util util;
	@Autowired
	MessageByLocaleService messageByLocaleService;
	@Autowired
	AlertaCambiarClave alertacambiarclave;
//		@Autowired
//		private PkUserMapper pkUserMapper;
	
	public void save(User user) {
		userRepository.save(user);
	}
	public User findByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPasswordAndEnabled(username, password, true);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsernameAndEnabled(username, true);
	}

	public HashMap<String, Object> cambiarClave(long idUsuario, String claveAnterior, String nuevaClave) {
		HashMap<String, Object> n = new HashMap<String, Object>();
		User us = userRepository.findById(idUsuario).get();
		n.put("mensaje", messageByLocaleService.getMessage("general.mensaje.registroCorrectamente"));
		n.put("tipomensaje", 1);
		if (util.parseDecrypt(us.getPassword()).equals(claveAnterior)) {
			us.setPassword(nuevaClave);
			userRepository.save(us);

		} else {
			n.put("mensaje", messageByLocaleService.getMessage("usuario.mensaje.claveanteriorinvalida"));
			n.put("tipomensaje", 0);
		}

		return n;

	}

	public HashMap<String, Object> recuperarClave(int idEntidadPrestadora, String correo) throws Exception {
		HashMap<String, Object> n = new HashMap<String, Object>();
		n.put("mensaje","SE ENVIO CORREO");// messageByLocaleService.getMessage("general.mensaje.registroCorrectamente"));
		n.put("tipomensaje", 1);
		Concesionario c = new Concesionario();
		c.setId(idEntidadPrestadora);
		User user = userRepository.findByEmailAndConcesionarioAndEnabled(correo, c, true);
		String passenc = util.encrypt("omar");
		user.setPassword(passenc);
		userRepository.save(user);
		alertacambiarclave.enviarclave(user.getConcesionario().getSnombre(), "omar", correo);
		return n;
	}

	public static Integer genererNombre() {
		return (int) (1000000 * Math.random());
	}
}
