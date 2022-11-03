package com.application.rewards.domain;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Customer {

	@NotEmpty
	@Pattern(regexp = "^[0-9]*$", message = "invalid phone number")
	String phone;
	String firstName;
	String lastName;
	@NotEmpty
	@Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", message = "invalid email address")
	String email;
	List<Purchase> orderHistory;
	List<Rewards> rewardsHist;
}
