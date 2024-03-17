package com.miranda.orderapi.validator;

import com.miranda.orderapi.entity.User;
import com.miranda.orderapi.exceptions.ValidateServiceException;

public class UserValidator {

	public static void signup(User user) {

		if (user.getUserName() == null || user.getUserName().isEmpty())
			throw new ValidateServiceException("El nombre de usuario es requerido");

		if (user.getUserName().length() > 30)
			throw new ValidateServiceException("El nombre de usuario es muy largo (max 30)");
		
		if (user.getPassword() == null || user.getPassword().isEmpty())
			throw new ValidateServiceException("El password es requerido");
		
		if (user.getPassword().length() > 30)
			throw new ValidateServiceException("El password es muy largo (max 30)");

	}

}
