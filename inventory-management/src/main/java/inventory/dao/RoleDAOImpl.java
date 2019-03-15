package inventory.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Category;
import inventory.model.Role;
@Repository
@Transactional(rollbackFor=Exception.class)
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO<Role>{

}
