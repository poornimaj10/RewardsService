package com.application.rewards.controller;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.application.rewards.error.ErrorResponse;
import com.application.rewards.service.RewardsService;

@RestController
@RequestMapping("/")
@Validated
public class RewardsServiceController {
	
	
	@Value("${service.timeout}")
	private Long DEFERRED_TIMEOUT;
	
	@Autowired
	RewardsService svc;

	@GetMapping("rewards/{phoneNumber}/{months}")
	public DeferredResult<ResponseEntity> getRewards(@PathVariable("phoneNumber") final String phone, @PathVariable("months") final int months) {
		DeferredResult<ResponseEntity> finalResponse = new DeferredResult<>(DEFERRED_TIMEOUT);	
		String regexNumeric = "^[0-9]+$";
		ResponseEntity response;
		ErrorResponse errResp ;
		ArrayList<String> err = null;
		int validiltyMonths = (months < 0) ? 0 : months;
		
		if(!Pattern.matches(regexNumeric, phone) || phone.length() != 10){
			err = new ArrayList<>();
			err.add("Invalid Phone Number");
			errResp = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).erroritems(err).build();
			response = new ResponseEntity<>(errResp, HttpStatus.BAD_REQUEST);
			finalResponse.setErrorResult(response);
		}else {
			int svcResult = svc.fetchActiveRewards(phone, validiltyMonths);
			response = new ResponseEntity<>(svcResult, HttpStatus.OK);
			finalResponse.setResult(response);
		}
		return finalResponse;
		
	}
	
}
