package com.application.rewards.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Purchase {
	int orderNumber;
	String itemIdentifier;
	int quantity;
	int amount;
	Date purchaseDate;
}
