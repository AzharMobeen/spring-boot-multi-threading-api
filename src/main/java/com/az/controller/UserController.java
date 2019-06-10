package com.az.controller;


import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.az.services.UserService;

@RestController("/")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("async")
	public String callAsyncMethos(@RequestParam int taskCount) throws InterruptedException, ExecutionException {
		return userService.callAllAsyncMethod(taskCount).get();
	}
	
	@GetMapping("sync")
	public String callSyncMethos(@RequestParam int taskCount) throws InterruptedException, ExecutionException {
		return userService.callAllSyncMethod(taskCount);
	}
	
	// In this method I'm calling @Async with an other server (TaskRoundService) that also have @Async method
	@GetMapping("async2")
	public String callAsyncMethosWithChildAsync(@RequestParam int taskCount) throws InterruptedException, ExecutionException {
		return userService.callAllAsyncMethodWithChildAsync(taskCount).get();
	}
}
