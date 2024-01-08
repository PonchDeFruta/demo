package com.pxnch.demo.services;

import com.pxnch.demo.config.PuertoLoadBalancerConfig;
import com.pxnch.demo.models.Saldo;
import com.pxnch.demo.models.Status;
import com.pxnch.demo.repository.StatusRepository;
import com.pxnch.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.pxnch.demo.models.User;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl {
	/*ADMINISTRCION DE USUARIOS*/
	private final Faker faker;
	private final UserRepository userRepository;
	@Autowired
	private Status statusEntity;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	StatusServiceImpl implStatus;
	@Autowired
	private Saldo saldo;
	@Autowired
	private PuertoLoadBalancerConfig puerto;


	int estatusFilesServicio = 1; // 1 online, 2 en transferencia, 0 offline

	@Autowired
	public UserServiceImpl(Faker faker, UserRepository userRepository) {
		this.faker = faker;
		this.userRepository = userRepository;
	}

	@PostConstruct
	public void init() {
		for (int i = 0; i <= 5; i++) {
			User userEntity = User.builder()
					.nombre(faker.gameOfThrones().character())
					.nombreUsuario(faker.name().username())
					.password(faker.crypto().sha256())
					.subscripcion("Activa")
					.saldo(faker.number().numberBetween(100, 1000))
					.build();

			userRepository.save(userEntity);
		}
		/*STATUS*/
		implStatus.setEstatusService(estatusFilesServicio, "Files");


	}

	public List<User> getUsers() {
		puerto.imprimirInformacion();
		return userRepository.findAll();
	}

	public User getUserByUsername(String username) {
		return userRepository.buscarPorNombreUsuario(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User %s no encontrado.", username)));
	}

	public User createUser(User user) {
		if (userRepository.existsByNombreUsuario(user.getNombreUsuario())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Usuario %s ya existe", user.getNombreUsuario()));
		}
		return userRepository.save(user);
	}

	public User updateUser(User user, String userName) {
		User userUpdated = getUserByUsername(userName);
		userUpdated.setNombre(user.getNombreUsuario());
		userUpdated.setNombreUsuario(user.getNombre());
		userUpdated.setPassword(user.getPassword());
		userUpdated.setSubscripcion(user.getSubscripcion());
		userUpdated.setSaldo(user.getSaldo());

		return userRepository.save(userUpdated);
	}

	public User agregarSaldoUser(Saldo saldo, String userName) {
		// Validar la entrada
		if (saldo == null || userName == null || userName.isEmpty()) {
			throw new IllegalArgumentException("Entrada inválida");
		}

		User userUpdated = getUserByUsername(userName);
		if (userUpdated == null) {
			// Manejar el caso en el que no se encuentra el usuario
			throw new NoSuchElementException("Usuario no encontrado");
		}

		int newSaldo = userUpdated.getSaldo() + saldo.getSaldo();
		userUpdated.setSaldo(newSaldo);

		return userRepository.save(userUpdated);
	}

	public User restarSaldoUser(Saldo saldo, String userName) {
		if (saldo == null || userName == null || userName.isEmpty()) {
			throw new IllegalArgumentException("Entrada inválida");
		}
		User userUpdated = getUserByUsername(userName);
		int currentSaldo = userUpdated.getSaldo();
		int saldoToSubtract = saldo.getSaldo();

		if (currentSaldo < saldoToSubtract) {
			throw new IllegalStateException("Saldo insuficiente para realizar la operación");
		}
		int newSaldo = currentSaldo - saldoToSubtract;
		userUpdated.setSaldo(newSaldo);

		return userRepository.save(userUpdated);
	}

	public void deleteUser(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Nombre de usuario inválido");
		}

		User userToDelete = getUserByUsername(username);

		if (userToDelete != null) {
			userRepository.delete(userToDelete);
		} else {
			throw new NoSuchElementException("Usuario no encontrado");
		}
	}

}