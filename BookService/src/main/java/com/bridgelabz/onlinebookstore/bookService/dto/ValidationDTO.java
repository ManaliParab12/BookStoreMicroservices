package com.bridgelabz.onlinebookstore.bookService.dto;

import lombok.Data;

public @Data class ValidationDTO {
	
	public String userId;
	
	public String type;
	
	public boolean isvalid;

	public ValidationDTO(String userId, String type, boolean isvalid) {
		super();
		this.userId = userId;
		this.type = type;
		this.isvalid = isvalid;
	}

	
}
