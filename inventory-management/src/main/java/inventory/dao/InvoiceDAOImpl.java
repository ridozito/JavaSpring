package inventory.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Category;
import inventory.model.Invoice;
@Repository
@Transactional(rollbackFor=Exception.class)
public class InvoiceDAOImpl extends BaseDAOImpl<Invoice> implements InvoiceDAO<Invoice>{

}
