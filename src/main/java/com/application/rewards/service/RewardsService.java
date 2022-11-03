package com.application.rewards.service;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.application.rewards.utils.RewardsUtil;

@Service
public class RewardsService {

	private static final Logger LOG = LoggerFactory.getLogger(RewardsService.class);

	@Autowired
	RewardsUtil util;

	@Autowired
	RestTemplate restTemplate;

	@Value("${service.order.host}")
	private String orderSvc;

	@Value("${service.order.total.api}")
	private String totalApi;

	@Value("${rewards.minamount}")
	private int floorAmount;

	@Value("${rewards.minamount.points}")
	private int rewardsPoint;
	
	@Value("${rewards.addonamount}")
	private int incrementAmount;

	@Value("${rewards.addonamount.points}")
	private int addonPoint;
	
	public int fetchActiveRewards(String phone, int months) {
		LOG.debug("In fetchActiveRewards()");
		StopWatch watch = new StopWatch();
		Instant start = Instant.now();
		Map pathParams = new HashMap<>();
		pathParams.put("phoneNumber", phone);
		pathParams.put("months", months);
		URI ordSvcUri = UriComponentsBuilder.fromUriString(orderSvc.concat(totalApi))
		        .buildAndExpand(pathParams)
		        .toUri();
		 ResponseEntity<Float> response = restTemplate.exchange(ordSvcUri,
                HttpMethod.GET, null,Float.class);
		Instant end = Instant.now();
		LOG.info("Time taken to get the Purchase Total in nanosec {}",Duration.between(end,start).getNano());
		Integer activeRewards;
		if(response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
			//Object tmp = response.getBody();
			Float tot = response.getBody();
			//Rounding aggregate to the closest integer
			activeRewards = calcRewards(Math.round(tot), floorAmount, rewardsPoint, incrementAmount, addonPoint);
		}else {
			LOG.error("Time taken to get the Purchase Total in nanosec {}",Duration.between(end,start).getNano());
			activeRewards = -1;
		}
		
		return activeRewards;
	}


	public int calcRewards(int total, int floorAmount, int rewardsPoint, int incrementAmount, int addonPoint) {
		return util.calcRewards(total, floorAmount, rewardsPoint, incrementAmount, addonPoint);
	}

}
