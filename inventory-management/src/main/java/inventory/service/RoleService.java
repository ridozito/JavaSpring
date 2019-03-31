package inventory.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.RoleDAO;
import inventory.model.Role;
import inventory.model.Paging;

@Service
public class RoleService {
	@Autowired
	private RoleDAO<Role> roleDAO;
	final static Logger log = Logger.getLogger(RoleService.class);
	public List<Role> getRoleList(Role role , Paging paging){
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		return roleDAO.findAll(queryStr.toString(), mapParams,paging);
	}
	public void saveRole(Role role)  throws Exception{
		log.info("Insert role "+role.toString());
		role.setActiveFlag(1);
		role.setCreateDate(new Date());
		role.setUpdateDate(new Date());
		roleDAO.save(role);
	}
	public void updateRole(Role role) throws Exception {
		log.info("Update role "+role.toString());
		role.setUpdateDate(new Date());
		roleDAO.update(role);
	}
	public void deleteRole(Role role) throws Exception{
		role.setActiveFlag(0);
		role.setUpdateDate(new Date());
		log.info("Delete role "+role.toString());
		roleDAO.update(role);
	}
	public List<Role> findRole(String property , Object value){
		log.info("=====Find by property role start====");
		log.info("property ="+property +" value"+ value.toString());
		return roleDAO.findByProperty(property, value);
	}
	public Role findByIdRole(int id) {
		log.info("find role by id ="+id);
		return roleDAO.findById(Role.class, id);
	}
}
