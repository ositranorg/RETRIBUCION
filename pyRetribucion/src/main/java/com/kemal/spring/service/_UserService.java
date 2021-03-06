package com.kemal.spring.service;

import org.springframework.stereotype.Service;

/**
 * Created by Keno&Kemo on 18.10.2017..
 */

@Service
public class _UserService {

   /* private BCryptPasswordEncoder bCryptPasswordEncoder;
    //@Autowired
    private UserRepository userRepository;
    @Autowired
    MessageByLocaleService messageByLocaleService;
    
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PkUserMapper pkUserMapper;
	
    //private CambiarClaveRepository cambiarClaveRepository;
    //@Autowired
    //private CambiarClaveMapper cambiarClaveMapper;
    private _RoleService roleService;
    public _UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, _RoleService
            roleService, CacheManager cacheManager,
            CambiarClaveRepository cambiarClaveRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        //this.cambiarClaveRepository = cambiarClaveRepository;
    }

    //region find methods
    //==============================================================================================
    @Cacheable(value = "cache.allUsers")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "cache.allUsersPageable")
    public Page<User> findAllPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Cacheable (value = "cache.userByEmail", key = "#email", unless="#result == null")
    public User findByEmail(String email) {
        return userRepository.findByEmailEagerly(email);
    }

    @Cacheable (value = "cache.userById", key = "#id", unless="#result == null")
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> findByIdPageable(Long id, Pageable pageRequest){
        Optional<User> user = userRepository.findById(id);
        List<User> users = user.isPresent() ? Collections.singletonList(user.get()) : Collections.emptyList();
        return new PageImpl<>(users, pageRequest, users.size());
    }

    public User findByEmailAndIdNot (String email, Long id){
        return userRepository.findByEmailAndIdNot(email, id);
    }

    public User findByUsernameAndIdNot(String username, Long id){
        return userRepository.findByUsernameAndIdNot(username, id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //region Find eagerly
    public User findByIdEagerly (Long id){
        return userRepository.findByIdEagerly(id);
    }

    @Cacheable(value = "cache.allUsersEagerly")
    public List<User> findAllEagerly() {
        return userRepository.findAllEagerly();
    }
    //endregion

    //region Find by containing
    @Cacheable (value = "cache.byNameContaining")
    public Page<User> findByNameContaining (String name, Pageable pageable){
        return userRepository.findByNameContainingOrderByIdAsc(name, pageable);
    }

    @Cacheable (value = "cache.bySurnameContaining")
    public Page<User> findBySurnameContaining(String surname, Pageable pageable) {
        return userRepository.findBySurnameContainingOrderByIdAsc(surname, pageable);
    }

    @Cacheable (value = "cache.byUsernameContaining")
    public Page<User> findByUsernameContaining(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingOrderByIdAsc(username, pageable);
    }

    @Cacheable (value = "cache.byEmailContaining")
    public Page<User> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContainingOrderByIdAsc(email, pageable);
    }
    //endregion

    //==============================================================================================
    //endregion


    @Transactional
    @CacheEvict(value = {"cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById",
                    "cache.allUsersEagerly", "cache.byNameContaining", "cache.bySurnameContaining",
                    "cache.byUsernameContaining", "cache.byEmailContaining"}, allEntries = true)
    public void save(User user) {
        userRepository.save(user);
    }

    @CacheEvict(value = {"cache.allUsersPageable", "cache.allUsers", "cache.userByEmail", "cache.userById",
            "cache.allUsersEagerly", "cache.byNameContaining", "cache.bySurnameContaining",
            "cache.byUsernameContaining", "cache.byEmailContaining"}, allEntries = true)
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User createNewAccount(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singletonList(roleService.findByName("ROLE_USER")));
        return user;
    }

    public User getUpdatedUser(User persistedUser, UserUpdateDto userUpdateDto) {
        persistedUser.setName(userUpdateDto.getName());
        persistedUser.setSurname(userUpdateDto.getSurname());
        persistedUser.setUsername(userUpdateDto.getUsername());
        persistedUser.setEmail(userUpdateDto.getEmail());
        persistedUser.setRoles(getAssignedRolesList(userUpdateDto));
        persistedUser.setEnabled(userUpdateDto.isEnabled());
        return persistedUser;
    }

    public List<Role> getAssignedRolesList(UserUpdateDto userUpdateDto) {
        Map<Long, Role> assignedRoleMap = new HashMap<>();
        List<Role> roles = userUpdateDto.getRoles();
        for (Role role : roles) {
            assignedRoleMap.put(role.getId(), role);
        }

        List<Role> userRoles = new ArrayList<>();
        List<Role> allRoles = roleService.findAll();
        for (Role role : allRoles) {
            if (assignedRoleMap.containsKey(role.getId())) {
                userRoles.add(role);
            } else {
                userRoles.add(null);
            }
        }
        return userRoles;
    }
    public Resultado cambiarClave(int idUsuario, String claveAnterior, String nuevaClave, String confirmarClave) {
    	//return userRepository.cambiarClave(claveAnterior, nuevaClave, confirmarClave);
    	//return cambiarClaveRepository.cambiarClave(claveAnterior, nuevaClave, confirmarClave);
    	//CambiarClaveFiltro cambiarClaveFiltro = new CambiarClaveFiltro();
    	Optional<User> user = userRepository.findById((long) idUsuario);
    	Resultado resultado = new Resultado();
    	//System.out.println("nuevaClave: "+nuevaClave);
    	//System.out.println("confirmarClave: "+confirmarClave);
    	//System.out.println("idUsuario: "+idUsuario);
    	if(!nuevaClave.equals(confirmarClave)) {
    		resultado.setResultado(0);
    		resultado.setMensaje(messageByLocaleService.getMessage("usuario.mensaje.campararNuevaClave"));
    		return resultado;
    	}
    	//System.out.println("getPassword: "+user.get().getPassword());
    	//PasswordEncoder encoder = new BCryptPasswordEncoder();
    	String password = passwordEncoder.encode(confirmarClave); 
    	//System.out.println("password: "+password);
    	claveAnterior = claveAnterior.toLowerCase();
    	//System.out.println("claveAnterior: "+claveAnterior);
    	if(!passwordEncoder.matches(claveAnterior,user.get().getPassword())) {
    		resultado.setResultado(0);
    		resultado.setMensaje(messageByLocaleService.getMessage("usuario.mensaje.campararClave"));
    		return resultado;
    	}
    	
    	user.get().setPassword(password);
    	userRepository.save(user.get());
    	
    	resultado.setResultado(1);
		resultado.setMensaje(messageByLocaleService.getMessage("usuario.mensaje.actualizarCorrectoClave"));
		return resultado;
	}
    public PkUserMapperResultado recuperarClave(int idEntidadPrestadora, String correo) throws Exception {
    	PkUserMapperFilter filtro = new PkUserMapperFilter();
    	filtro.setIdentidadPrestadora(idEntidadPrestadora);
    	filtro.setCorreo(correo);
    	String codigoDecode = genererNombre().toString();
    	System.out.println("clave 1 es: " + codigoDecode);
    	String codigoEncode = passwordEncoder.encode(codigoDecode);
    	System.out.println("clave 2 es: " + codigoEncode);
    	filtro.setClaveDecode(codigoDecode);
    	filtro.setClaveEncode(codigoEncode);
    	pkUserMapper.recuperarClave(filtro);
    	return filtro.getRespuesta().get(0);
    }
    public static Integer genererNombre(){
    	  return (int)(1000000 * Math.random());
    }*/
}
