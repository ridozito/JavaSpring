package inventory.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Category;
@Repository
@Transactional(rollbackFor=Exception.class)
public class CategoryDAOImpl extends BaseDAOImpl<Category> implements CategoryDAO<Category>{

}
