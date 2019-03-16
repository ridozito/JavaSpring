package inventory.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import inventory.dao.InvoiceDAO;
import inventory.model.Invoice;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.util.Constant;

@Service
public class InvoiceService {
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProductInStockService productInStockService;
	@Autowired
	private InvoiceDAO<Invoice> invoiceDAO;
	static final Logger log = Logger.getLogger(InvoiceService.class);

	public void save(Invoice invoice) throws Exception {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);
		invoice.setActiveFlag(1);
		invoice.setCreateDate(new Date());
		invoice.setUpdateDate(new Date());
		invoiceDAO.save(invoice);
		historyService.save(invoice, Constant.ACTION_ADD);
		
		productInStockService.saveOrUpdate(invoice);
	}

	public void update(Invoice invoice) throws Exception {
		int originQty = invoiceDAO.findById(Invoice.class, invoice.getId()).getQty();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);
		invoice.setUpdateDate(new Date());
		Invoice invoice2 = new Invoice();
		invoice2.setProductInfo(invoice.getProductInfo());
		invoice2.setQty(invoice.getQty()-originQty);// 2-5 = -3
		invoice2.setPrice(invoice.getPrice());
		invoiceDAO.update(invoice);
		historyService.save(invoice, Constant.ACTION_EDIT);
		productInStockService.saveOrUpdate(invoice2);
		
	}

	public List<Invoice> find(String property, Object value) {
		return invoiceDAO.findByProperty(property, value);
	}

	public List<Invoice> getList(Invoice invoice, Paging paging) {
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if (invoice != null) {
			if (invoice.getType() != 0) {
				queryStr.append(" and model.type=:type");
				mapParams.put("type", invoice.getType());
			}
			if (!StringUtils.isEmpty(invoice.getCode())) {
				queryStr.append(" and model.code =:code ");
				mapParams.put("code", invoice.getCode());
			}
			if(invoice.getFromDate()!=null) {
				queryStr.append(" and model.updateDate >= :fromDate");
				mapParams.put("fromDate", invoice.getFromDate());
			}
			if(invoice.getToDate()!=null) {
				queryStr.append(" and model.updateDate <= :toDate");
				mapParams.put("toDate", invoice.getToDate());
			}
		}
		return invoiceDAO.findAll(queryStr.toString(), mapParams, paging);

	}
}
