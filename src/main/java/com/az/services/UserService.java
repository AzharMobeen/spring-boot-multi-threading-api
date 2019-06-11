package com.az.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.az.entities.User;
import com.az.repositories.UserRepository;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final TaskRoundService taskRoundService;
    private final UserRepository userRepository;
    
    @Autowired
    private RequestService requestService;
    
    public UserService(UserRepository userRepository,TaskRoundService taskRoundService) {
        this.taskRoundService = taskRoundService;
        this.userRepository = userRepository;
    }


    @Async
    public CompletableFuture<String> callAllAsyncMethod(int taskCount) throws InterruptedException, ExecutionException {
    	long startTime = System.currentTimeMillis();
    	
    	for(int index = 0; index<taskCount;index++) {
    		CompletableFuture<String> roundA = asyncRoundA();
    		CompletableFuture<String> roundB = asyncRoundB();
    		CompletableFuture<String> roundC = asyncRoundC();
    	}
    	
    	//CompletableFuture.allOf(roundA,roundB,roundC);    	
    	
    	long endTime = System.currentTimeMillis();
    	
    	logger.info("With Threading Time in ms  {}", ( endTime-startTime));    	
    	return CompletableFuture.completedFuture("Task Execution Time with threads :: "+( endTime-startTime));     	
    }
    
    @Async
    public CompletableFuture<String> callAllAsyncMethodWithChildAsync(int taskCount) throws InterruptedException, ExecutionException {
    	long startTime = System.currentTimeMillis();
    	
    	for(int index = 0; index<taskCount;index++) {
    		CompletableFuture<String> roundA = taskRoundService.asyncRoundA();    	
    		//roundA.join();
    		CompletableFuture<String> roundB = taskRoundService.asyncRoundB();
    		//roundB.join();
    		CompletableFuture<String> roundC = taskRoundService.asyncRoundC();
    		//roundC.join();
    		CompletableFuture.allOf(roundA,roundB,roundC).join();    		
    	}
    	   	
    	long endTime = System.currentTimeMillis();
    	
    	logger.info("With Threading Time in ms  {}", ( endTime-startTime));    	
    	return CompletableFuture.completedFuture("Task Execution Time with threads :: "+( endTime-startTime));     	
    }
    
    public String callAllSyncMethod(int taskCount) throws InterruptedException, ExecutionException {
    	long startTime = System.currentTimeMillis();
    	
    	for(int index = 0; index<taskCount;index++) {
    		String roundA = syncRoundA();
    		String roundB = syncRoundB();
    		String roundC = syncRoundC();
    	}
    	    	
    	long endTime = System.currentTimeMillis();
    	logger.info("Without Threading Time in ms  {}", ( endTime-startTime));
    	return "Task Execution Time without threads :: "+( endTime-startTime); 
    }    
    
    public CompletableFuture<String> asyncRoundA() {    	
    	return CompletableFuture.completedFuture(this.roundA());
    }
        
    public CompletableFuture<String>  asyncRoundB() {    	    	
    	return CompletableFuture.completedFuture(this.roundB());
    }    
	
    public CompletableFuture<String>   asyncRoundC() {    	
    	return CompletableFuture.completedFuture(this.roundC());
    }
    
    private String syncRoundA() {    	
    	return this.roundA();
    }
        
    private String  syncRoundB() {    	    	
    	return this.roundB();
    }
	
    private String  syncRoundC() {    	
    	return this.roundC();
    }
	
    private String roundA() {
    	User user = this.saveUser(new User("Azhar","Blog"));
    	this.updateUser(user.getId(), new User("Mr. Azhar", "Java"));
    	this.displayUser(user.getId());
    	
    	this.saveUser(new User("Malik","Blog"));
    	this.updateUser(user.getId(), new User("Mr. Malik", "Accounting"));
    	this.displayUser(user.getId());
    	return "Round A Completed!";
    }
    private String roundB() {
    	User user =this.saveUser(new User("Usama","Blog"));
    	this.updateUser(user .getId(), new User("Mr. Usama", "Java"));
    	this.displayUser(user.getId());
    	
    	user = this.saveUser(new User("Faisal","Blog"));
    	this.updateUser(user.getId(), new User("Mr. Faisal", "Accounting"));
    	this.displayUser(user.getId());
    	
    	user = this.saveUser(new User("Fiza","Blog"));
    	this.updateUser(user.getId(), new User("Mrs. Fiza", "Pondi"));
    	this.displayUser(user.getId());
		return "Round B Completed!";
	}
    private String roundC() {
    	User user = this.saveUser(new User("Zaeem","Blog"));
    	this.updateUser(user.getId(), new User("Mr. Zaeem", "Java"));
    	this.displayUser(user.getId());
    	
    	user = this.saveUser(new User("Adil","Blog"));
    	this.updateUser(user.getId(), new User("Mr. Adil", "Nothing"));
    	this.displayUser(user.getId());
		return "Round C Completed!";
	}
    
	private void displayUser(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		logger.info("User {}",user.isPresent()?user.get():null);	
	}
	private void updateUser(Integer userId, User user) {
		Optional<User> userTemp = userRepository.findById(userId);
		if(userTemp.isPresent()) { 
			user.setId(userTemp.get().getId());
			userRepository.save(user);
		}else
			logger.warn("User Id Not exist {} ",userId);						
	}
	private User saveUser(User user) {
		return userRepository.save(user);		
	}

	
	public CompletableFuture<String> callAllAsyncMethodWithChildOfChildAsync(int userCount, int requestCount,
			int roundCount) {
		long startTime = System.currentTimeMillis();    			
		List<CompletableFuture<String>> result = new ArrayList<>();
		for(int user= 1; user<=userCount;user++) {
			List<CompletableFuture<String>> temp;
			try {
				temp = requestService.requestCountProcess(requestCount,roundCount);
				result.addAll(temp);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		/*result.forEach(completableFuture->{
			completableFuture.join();			
		});*/
		long endTime = System.currentTimeMillis();
		return CompletableFuture.completedFuture("Task Execution Time with threads :: "+( endTime-startTime));
	}


	public String callAllSyncMethodWithChildOfChildAsync(int userCount, int requestCount,
			int roundCount) {
		long startTime = System.currentTimeMillis();    			
		List<String> result = new ArrayList<>();
		for(int user= 1; user<=userCount;user++) {
			result.addAll(requestService.requestCountProcess2(requestCount,roundCount));
		}		
		long endTime = System.currentTimeMillis();
		return "Task Execution Time with threads :: "+( endTime-startTime);
		
	}	       
}