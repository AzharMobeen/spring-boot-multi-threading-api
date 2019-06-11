package com.az.services;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.az.entities.User;
import com.az.repositories.UserRepository;

@Transactional
@Service
public class TaskRoundService {

	private static final Logger logger = LoggerFactory.getLogger(TaskRoundService.class);
	private final UserRepository userRepository;

	public TaskRoundService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Async
	public CompletableFuture<String> asyncRoundA() {
		return CompletableFuture.completedFuture(this.roundA());
	}

	@Async
	public CompletableFuture<String> asyncRoundB() {
		return CompletableFuture.completedFuture(this.roundB());
	}

	@Async
	public CompletableFuture<String> asyncRoundC() {
		return CompletableFuture.completedFuture(this.roundC());
	}

	public String roundA() {
		User user = this.saveUser(new User("Azhar", "Blog"));
		this.updateUser(user.getId(), new User("Mr. Azhar", "Java"));
		this.displayUser(user.getId());

		this.saveUser(new User("Malik", "Blog"));
		this.updateUser(user.getId(), new User("Mr. Malik", "Accounting"));
		this.displayUser(user.getId());
		return "Round A Completed!";
	}

	public String roundB() {
		User user = this.saveUser(new User("Usama", "Blog"));
		this.updateUser(user.getId(), new User("Mr. Usama", "Java"));
		this.displayUser(user.getId());

		user = this.saveUser(new User("Faisal", "Blog"));
		this.updateUser(user.getId(), new User("Mr. Faisal", "Accounting"));
		this.displayUser(user.getId());

		user = this.saveUser(new User("Fiza", "Blog"));
		this.updateUser(user.getId(), new User("Mrs. Fiza", "Pondi"));
		this.displayUser(user.getId());
		return "Round B Completed!";
	}

	public String roundC() {
		User user = this.saveUser(new User("Zaeem", "Blog"));
		this.updateUser(user.getId(), new User("Mr. Zaeem", "Java"));
		this.displayUser(user.getId());

		user = this.saveUser(new User("Adil", "Blog"));
		this.updateUser(user.getId(), new User("Mr. Adil", "Nothing"));
		this.displayUser(user.getId());
		return "Round C Completed!";
	}

	private void displayUser(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		logger.info("User {}", user.isPresent() ? user.get() : null);
	}

	private void updateUser(Integer userId, User user) {
		Optional<User> userTemp = userRepository.findById(userId);
		if (userTemp.isPresent()) {
			user.setId(userTemp.get().getId());
			userRepository.save(user);
		} else
			logger.warn("User Id Not exist {} ", userId);
	}

	private User saveUser(User user) {
		return userRepository.save(user);
	}
}
