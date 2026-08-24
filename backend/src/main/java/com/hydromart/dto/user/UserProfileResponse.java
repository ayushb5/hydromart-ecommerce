package com.hydromart.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
	 	private Long id;

	    private String email;

	    private String role;

	    private String phone;

	    private String addressLine1;

	    private String addressLine2;

	    private String city;

	    private String state;

	    private String postalCode;

	    private String country;
}
