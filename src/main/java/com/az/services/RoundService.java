package com.az.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class RoundService {
	
	private final TaskRoundService taskRoundService;
	
	public RoundService(TaskRoundService taskRoundService) {
		this.taskRoundService= taskRoundService;
	}
	
	
	public List<CompletableFuture<String>> roundCountProcess(int roundCount) {
		List<Integer> randomMethod = Arrays.asList(1,2,3);
		Random random = new Random();
		List<CompletableFuture<String>> result = new ArrayList<>();
		for(int round=1;round<=roundCount;round++) {			
			int randomIndex = random.nextInt(randomMethod.size());						
			CompletableFuture<String> temp = null;
			switch(randomIndex) {			
				case 0:
					temp = taskRoundService.asyncRoundA();
					result.add(temp);
					break;
				case 1:
					temp =taskRoundService.asyncRoundB();
					result.add(temp);
					break;
				case 2:
					temp =taskRoundService.asyncRoundC();
					result.add(temp);
					break;
			}			
		}
		return result;
	}
	
	
	public List<String> roundCountProcess2(int roundCount) {
		List<Integer> randomMethod = Arrays.asList(1,2,3);
		Random random = new Random();
		List<String> result = new ArrayList<>();
		for(int round=1;round<=roundCount;round++) {			
			int randomIndex = random.nextInt(randomMethod.size());						
			switch(randomIndex) {
				case 0:
					result.add(taskRoundService.roundA());
					break;
				case 1:
					result.add(taskRoundService.roundB());
					break;
				case 2:
					result.add(taskRoundService.roundC());
					break;
			}
		}
		return result;
	}
	
}
