package inventory.validate;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import inventory.model.Category;
import inventory.model.Invoice;
import inventory.service.InvoiceService;

@Component
public class InvoiceValidator implements Validator {
	@Autowired
	private InvoiceService invoiceService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Invoice.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Invoice invoice = (Invoice) target;
		ValidationUtils.rejectIfEmpty(errors, "code", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "qty", "msg.required");
		ValidationUtils.rejectIfEmpty(errors, "price", "msg.required");
		if (invoice.getCode() != null) {
			List<Invoice> results = invoiceService.find("code", invoice.getCode());
			if (results != null && !results.isEmpty()) {
				if (invoice.getId() != null && invoice.getId() != 0) {
					if (results.get(0).getId() != invoice.getId()) {
						errors.rejectValue("code", "msg.code.exist");
					}
				} else {
					errors.rejectValue("code", "msg.code.exist");
				}
			}
		}
		if (invoice.getQty() <= 0) {
			errors.rejectValue("qty", "msg.wrong.format");
		}
		if (invoice.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.rejectValue("price", "msg.wrong.format");
		}
		if (invoice.getFromDate() != null && invoice.getToDate() != null) {
			if (invoice.getFromDate().after(invoice.getToDate())) {
				errors.rejectValue("fromDate", "msg.wrong.date");
			}
		}

	}

}
