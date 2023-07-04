package ar.com.codoacodo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.codoacodo.domain.User;
import ar.com.codoacodo.domain.dto.UserDTO;
import ar.com.codoacodo.domain.dto.UserRequestDTO;
import ar.com.codoacodo.domain.dto.UserRequestPutDTO;
import ar.com.codoacodo.domain.dto.UserResponseDTO;
import ar.com.codoacodo.domain.dto.reqres.ListResource;
import ar.com.codoacodo.services.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	// necesita service
	private final UserService userService;

	/*
	 * public UserController(UserService userService) { super(); this.userService =
	 * userService; }
	 */

	// GET
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> m1(@PathVariable("id") Long id) {

		User user = this.userService.buscarUser(id);

		UserDTO dto = UserDTO.builder().id(user.getId()).username(user.getUsername())
				.roles(user.getRoles().stream().map(x -> x.getRol()).collect(Collectors.toSet())).build();

		return ResponseEntity.ok(dto);
	}

	// GET
	@GetMapping()
	public ResponseEntity<List<User>> findAll() {

		List<User> user = this.userService.buscarTodos();

		// http status code=200
		return ResponseEntity.ok(user);
	}

	@PostMapping()
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO request) {

		// verifico si existe
		User user = this.userService.buscarUserPorUsername(request.getUsername());
		if (user != null) {
			UserResponseDTO response = UserResponseDTO.builder().username(user.getUsername()).build();

			return ResponseEntity.ok(response);
		}

		// sino lo crea
		// validacion!!!
		User newUser = User.builder().username(request.getUsername()).password(request.getPassword()).build();

		this.userService.crearUser(newUser);

		UserResponseDTO response = UserResponseDTO.builder().username(newUser.getUsername()).build();

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> actualizar(@PathVariable("id") Long id) {

		this.userService.eliminarUser(id);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> actualizarPorPut(@PathVariable(name = "id", required = true) Long id,
			@Validated @RequestBody UserRequestPutDTO request) {

		User user = this.userService.buscarUser(id);
		if (!user.getId().equals(request.getId())) {
			return ResponseEntity.badRequest().build();
		}

		user.setPassword(request.getPassword());
		this.userService.actualizar(user);
		return ResponseEntity.ok().build();
	}

}
