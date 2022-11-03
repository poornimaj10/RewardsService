package com.application.rewards.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Rewards {
	String type;
	int rewardsNum;
	int value;
	int claimed;
	Date validTil;
}
