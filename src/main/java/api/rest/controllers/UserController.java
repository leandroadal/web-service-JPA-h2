package api.rest.controllers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.rest.entities.User;
import api.rest.repository.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@Value("${token}") // pega o token de application.properties
	private String token;

	@GetMapping
	public ResponseEntity<List<User>> findAll(@RequestHeader("Authorization") String headerToken) {
		if (!headerToken.equals(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<User> user = repository.findAll();
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findbyId(@PathVariable Long id, @RequestHeader("Authorization") String headerToken) {
		if (!headerToken.equals(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		User user = repository.findById(id).get();
		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User user, @RequestHeader("Authorization") String headerToken) {
		if (!headerToken.equals(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		User newUser = repository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User updateUser,
			@RequestHeader("Authorization") String headerToken) {
		if (!headerToken.equals(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		User user = repository.findById(id).get();
		user.setName(updateUser.getName());
		user.setEmail(updateUser.getEmail());

		repository.save(user);

		return ResponseEntity.ok(user);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestHeader("Authorization") String headerToken) {
		if (!headerToken.equals(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<User> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> reqBodyMap,
			@RequestHeader("Authorization") String headerToken) {
		if (!headerToken.equals(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		User user = repository.findById(id).get();
		reqBodyMap.forEach((key, value) -> { // para cada chave e valor
			Field field = ReflectionUtils.findField(User.class, key); // encontre encontre o campo que equivale
																		// a key na classe User
			field.setAccessible(true); // permite acesso a campos privados
			ReflectionUtils.setField(field, user, value); // com base no campo e id de usuário atribuir o valor
		});
		user.setId(id);
		repository.save(user);
		return ResponseEntity.ok(user);
	}

	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> options() {
		HttpHeaders headers = new HttpHeaders();
		Set<HttpMethod> allowed = new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT,
				HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.OPTIONS, HttpMethod.HEAD));
		headers.setAllow(allowed);
		return ResponseEntity.ok().headers(headers).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
	public ResponseEntity<Void> head(@PathVariable Long id) {
		HttpHeaders headers = new HttpHeaders();
		Optional<User> user = repository.findById(id);
		if (user.isPresent()) {
			headers.set("id", id.toString());
			return ResponseEntity.ok().headers(headers).build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}