package com.az.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

	@Autowired
	private RoundService roundService;
	
	
	public List<CompletableFuture<String>> requestCountProcess(int requestCount,int roundCount) throws InterruptedException, ExecutionException {
		List<CompletableFuture<String>> result = new ArrayList<>();
		for(int request=1; request<=requestCount;request++) {
			List<CompletableFuture<String>> temp  = roundService.roundCountProcess(roundCount);
			result.addAll(temp);			
		}
		return result;
	}

	
	public List<String> requestCountProcess2(int requestCount,int roundCount) {
		List<String> result = new ArrayList<>();
		for(int request=1; request<=requestCount;request++) {
			result.addAll(roundService.roundCountProcess2(roundCount));
		}
		return result;
	}

	
}
