package inventory.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import inventory.dao.HistoryDAO;
import inventory.dao.ProductInStockDAO;
import inventory.model.ProductInStock;
import inventory.model.ProductInfo;
import inventory.model.History;
import inventory.model.Invoice;
import inventory.model.Paging;

@Service
public class HistoryService {
	@Autowired
	private HistoryDAO<History> historyDAO;
	
	private static final Logger log = Logger.getLogger(HistoryService.class);
	
	public List<History> getAll(History history , Paging paging){
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if(history!=null) {
			if(history.getProductInfo()!=null) {
				if(!StringUtils.isEmpty(history.getProductInfo().getCategory().getName()) ) {
					queryStr.append(" and model.productInfo.category.name like :cateName");
					mapParams.put("cateName","%"+history.getProductInfo().getCategory().getName()+"%");
				}
				if(!StringUtils.isEmpty(history.getProductInfo().getCode())) {
					queryStr.append(" and model.productInfo.code=:code");
					mapParams.put("code", history.getProductInfo().getCode());
				}
				if( !StringUtils.isEmpty(history.getProductInfo().getName()) ) {
					queryStr.append(" and model.productInfo.name like :name");
					mapParams.put("name", "%"+history.getProductInfo().getName()+"%");
				}
			}
			if( !StringUtils.isEmpty(history.getActionName()) ) {
				queryStr.append(" and model.actionName like :actionName");
				mapParams.put("actionName", "%"+history.getActionName()+"%");
			}
			if(history.getType()!=0) {
				queryStr.append(" and model.type = :type");
				mapParams.put("type", history.getType());
			}
		}
		return historyDAO.findAll(queryStr.toString(), mapParams,paging);
	}
	public void save(Invoice invoice, String action) {
		History history = new History();
		history.setProductInfo(invoice.getProductInfo());
		history.setQty(invoice.getQty());
		history.setType(invoice.getType());
		history.setPrice(invoice.getPrice());
		history.setActiveFlag(1);
		history.setActionName(action);
		history.setCreateDate(new Date());
		history.setUpdateDate(new Date());
		historyDAO.save(history);
	}

}
