package inventory.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.CategoryDAO;
import inventory.model.Category;

@Service
public class ProductService {
	@Autowired
	private CategoryDAO<Category> categoryDAO;
	private static final Logger log = Logger.getLogger(ProductService.class);
	public void saveCategory(Category category) {
		log.info("Insert category "+category.toString());
		category.setActiveFlag(1);
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		categoryDAO.save(category);
	}
	public void updateCategory(Category category) {
		log.info("Update category "+category.toString());
		category.setUpdateDate(new Date());
		categoryDAO.update(category);
	}
	public void deleteCategory(Category category) {
		category.setActiveFlag(0);
		category.setUpdateDate(new Date());
		log.info("Delete category "+category.toString());
		categoryDAO.update(category);
	}
	public List<Category> findCategory(String property , Object value){
		log.info("=====Find by property category start====");
		log.info("property ="+property +" value"+ value.toString());
		return categoryDAO.findByProperty(property, value);
	}
	public List<Category> getAllCategory(){
		log.info("show all category");
		return categoryDAO.findAll();
	}
	public Category findByIdCategory(int id) {
		log.info("find category by id ="+id);
		return categoryDAO.findById(Category.class, id);
	}
	
}
