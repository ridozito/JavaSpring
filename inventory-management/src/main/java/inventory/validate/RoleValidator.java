package inventory.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import inventory.model.Role;
import inventory.service.RoleService;
@Component
public class RoleValidator implements Validator{
	@Autowired
	private RoleService roleService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Role.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Role role = (Role) target;
		ValidationUtils.rejectIfEmpty(errors, "roleName", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "description", "msg.required");
		
		
		
	}
	

}
