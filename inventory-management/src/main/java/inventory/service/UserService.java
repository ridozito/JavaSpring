package inventory.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import inventory.dao.UserDAO;
import inventory.dao.UserRoleDAO;
import inventory.model.Paging;
import inventory.model.Role;
import inventory.model.UserRole;
import inventory.model.Users;
import inventory.util.HashingPassword;

@Service
public class UserService {
	final static Logger log = Logger.getLogger(UserService.class);
	@Autowired
	private UserDAO<Users> userDAO;
	@Autowired
	private UserRoleDAO<UserRole> userRoleDAO;

	public List<Users> findByProperty(String property, Object value) {
		log.info("Find user by property start ");
		return userDAO.findByProperty(property, value);

	}
	public Users findById(Integer id) {
		log.info("Find user by id ");
		return userDAO.findById(Users.class, id);
	}

	public void save(Users user) {
		user.setActiveFlag(1);
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		user.setPassword(HashingPassword.encrypt(user.getPassword()));
		userDAO.save(user);
		UserRole userRole = new UserRole();
		userRole.setUsers(user);
		Role role = new Role();
		role.setId(user.getRoleID());
		userRole.setRole(role);
		userRole.setActiveFlag(1);
		userRole.setCreateDate(new Date());
		userRole.setUpdateDate(new Date());
		userRoleDAO.save(userRole);

	}

	public void update(Users users) {
		Users user = findById(users.getId());
		if(user!=null) {
			UserRole userRole =(UserRole) user.getUserRoles().iterator().next();
			Role role = userRole.getRole();
			role.setId(users.getRoleID());
			userRole.setRole(role);
			userRole.setUpdateDate(new Date());
			user.setName(users.getName());
			user.setEmail(users.getEmail());
			user.setUserName(users.getUserName());
			user.setUpdateDate(new Date());
			userRoleDAO.update(userRole);
		}
		
		userDAO.update(user);
	}

	public void deleteUser(Users user) {
		user.setActiveFlag(0);
		user.setUpdateDate(new Date());
		userDAO.update(user);
	}

	public List<Users> getUsersList(Users users, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if (users != null) {
			if (!StringUtils.isEmpty(users.getName())) {
				queryStr.append(" and model.name like :name");
				mapParams.put("name", "%" + users.getName() + "%");
			}
			if (!StringUtils.isEmpty(users.getUserName())) {
				queryStr.append(" and model.userName like :userName");
				mapParams.put("userName", "%" + users.getUserName() + "%");
			}
		}
		return userDAO.findAll(queryStr.toString(), mapParams, paging);
	}

}
