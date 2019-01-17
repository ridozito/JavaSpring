package inventory.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Category;
import inventory.model.ProductInStock;
@Repository
@Transactional(rollbackFor=Exception.class)
public class ProductInStockImpl extends BaseDAOImpl<ProductInStock> implements CategoryDAO<ProductInStock>{

}
