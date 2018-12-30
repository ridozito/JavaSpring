package inventory.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Users;
@Repository
@Transactional(rollbackFor=Exception.class)
public class UserDAOImpl extends BaseDAOImpl<Users>  implements UserDAO<Users>{

}
