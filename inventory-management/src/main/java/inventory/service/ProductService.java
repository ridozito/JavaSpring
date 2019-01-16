package inventory.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import inventory.dao.CategoryDAO;
import inventory.dao.ProductInfoDAO;
import inventory.model.Category;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.util.ConfigLoader;

@Service
public class ProductService {
	@Autowired
	private CategoryDAO<Category> categoryDAO;
	@Autowired
	private ProductInfoDAO<ProductInfo> productInfoDAO;
	private static final Logger log = Logger.getLogger(ProductService.class);
	public void saveCategory(Category category)  throws Exception{
		log.info("Insert category "+category.toString());
		category.setActiveFlag(1);
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		categoryDAO.save(category);
	}
	public void updateCategory(Category category) throws Exception {
		log.info("Update category "+category.toString());
		category.setUpdateDate(new Date());
		categoryDAO.update(category);
	}
	public void deleteCategory(Category category) throws Exception{
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
	public List<Category> getAllCategory(Category category,Paging paging){
		log.info("show all category");
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if(category!=null) {
			if(category.getId()!=null && category.getId()!=0) {
				queryStr.append(" and model.id=:id");
				mapParams.put("id", category.getId());
			}
			if(category.getCode()!=null && !StringUtils.isEmpty(category.getCode())) {
				queryStr.append(" and model.code=:code");
				mapParams.put("code", category.getCode());
			}
			if(category.getName()!=null && !StringUtils.isEmpty(category.getName()) ) {
				queryStr.append(" and model.name like :name");
				mapParams.put("name", "%"+category.getName()+"%");
			}
		}
		return categoryDAO.findAll(queryStr.toString(), mapParams,paging);
	}
	public Category findByIdCategory(int id) {
		log.info("find category by id ="+id);
		return categoryDAO.findById(Category.class, id);
	}
	
	// PRODUCT INFO
	
	public void saveProductInfo(ProductInfo productInfo)  throws Exception{
		log.info("Insert productInfo "+productInfo.toString());
		productInfo.setActiveFlag(1);
		productInfo.setCreateDate(new Date());
		productInfo.setUpdateDate(new Date());
		String fileName = System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
		processUploadFile(productInfo.getMultipartFile(),fileName);
		productInfo.setImgUrl("/upload/"+fileName);
		productInfoDAO.save(productInfo);
	}
	public void updateProductInfo(ProductInfo productInfo) throws Exception {
		log.info("Update productInfo "+productInfo.toString());
		
		
		if(!productInfo.getMultipartFile().getOriginalFilename().isEmpty()) {
			
			String fileName =  System.currentTimeMillis()+"_"+productInfo.getMultipartFile().getOriginalFilename();
			processUploadFile(productInfo.getMultipartFile(),fileName);
			productInfo.setImgUrl("/upload/"+fileName);
		}
		productInfo.setUpdateDate(new Date());
		productInfoDAO.update(productInfo);
	}
	public void deleteProductInfo(ProductInfo productInfo) throws Exception{
		productInfo.setActiveFlag(0);
		productInfo.setUpdateDate(new Date());
		log.info("Delete productInfo "+productInfo.toString());
		productInfoDAO.update(productInfo);
	}
	public List<ProductInfo> findProductInfo(String property , Object value){
		log.info("=====Find by property productInfo start====");
		log.info("property ="+property +" value"+ value.toString());
		return productInfoDAO.findByProperty(property, value);
	}
	public List<ProductInfo> getAllProductInfo(ProductInfo productInfo,Paging paging){
		log.info("show all productInfo");
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if(productInfo!=null) {
			if(productInfo.getId()!=null && productInfo.getId()!=0) {
				queryStr.append(" and model.id=:id");
				mapParams.put("id", productInfo.getId());
			}
			if(productInfo.getCode()!=null && !StringUtils.isEmpty(productInfo.getCode())) {
				queryStr.append(" and model.code=:code");
				mapParams.put("code", productInfo.getCode());
			}
			if(productInfo.getName()!=null && !StringUtils.isEmpty(productInfo.getName()) ) {
				queryStr.append(" and model.name like :name");
				mapParams.put("name", "%"+productInfo.getName()+"%");
			}
		}
		return productInfoDAO.findAll(queryStr.toString(), mapParams,paging);
	}
	public ProductInfo findByIdProductInfo(int id) {
		log.info("find productInfo by id ="+id);
		return productInfoDAO.findById(ProductInfo.class, id);
	}
	private void processUploadFile(MultipartFile multipartFile,String fileName) throws IllegalStateException, IOException {
		if(!multipartFile.getOriginalFilename().isEmpty()) {
			File dir = new File(ConfigLoader.getInstance().getValue("upload.location"));
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(ConfigLoader.getInstance().getValue("upload.location"),fileName);
			multipartFile.transferTo(file);
		}
	}
	
	
}
