package com.example.irctc.irctc_backend;

import com.example.irctc.irctc_backend.Entity.Role;
import com.example.irctc.irctc_backend.Entity.User;
import com.example.irctc.irctc_backend.repositories.RoleRepository;
import com.example.irctc.irctc_backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@EnableAspectJAutoProxy
public class IrctcBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IrctcBackendApplication.class, args);
	}


	private final RoleRepository roleRepository;
	private  final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public IrctcBackendApplication(RoleRepository roleRepository,UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {

		if(!roleRepository.existsByName("ROLE_ADMIN")){
			Role adminRole = new Role();
			adminRole.setId(UUID.randomUUID().toString());
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);
		}

		if(!roleRepository.existsByName("ROLE_NORMAL")){
			Role userRole = new Role();
			userRole.setName("ROLE_NORMAL");
			userRole.setId(UUID.randomUUID().toString());
			roleRepository.save(userRole);
		}

		// Check if admin user exists
		if (!userRepository.findByEmail("admin@example.com").isPresent()) {
			// Fetch ROLE_ADMIN from DB
			Role adminRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new RuntimeException("Admin role not configured"));

			// Create admin user
			User adminUser = new User();
			adminUser.setName("adminuser");
			adminUser.setEmail("admin@example.com");
			adminUser.setPhone("+911234567890");
			adminUser.setPassword(passwordEncoder.encode("Admin@123"));
			adminUser.setCreated(LocalDateTime.now());

			// Assign existing role from DB
			adminUser.getRoles().add(adminRole);

			// Save user
			userRepository.save(adminUser);
			System.out.println("First admin user created successfully!");
		}

		System.out.println("Roles initialized successfully");




	}
}
