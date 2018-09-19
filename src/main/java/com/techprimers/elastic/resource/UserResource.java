package com.techprimers.elastic.resource;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techprimers.elastic.model.User;
import com.techprimers.elastic.service.UserService;

@RestController
@RequestMapping("/rest/users")
public class UserResource {
	private UserService userService;

	public UserResource(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public String saveUser(@RequestBody User user) throws IOException {
		return userService.saveUser(user);
	}

	@PutMapping("/{id}/{gender}")
	public String updateUser(@PathVariable long id, @PathVariable String gender) throws Exception, ExecutionException, InterruptedException {
		return userService.updateUser(id, gender);
	}
	
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable long id)  {
		return userService.deleteUser(id);
	}
	
	@GetMapping("/{id}")
	public Map<String, Object> getUser(@PathVariable long id) {
		return userService.getUser(id);
	}
}
