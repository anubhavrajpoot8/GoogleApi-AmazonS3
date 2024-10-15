package com.assignment.FreightFox.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import com.assignment.FreightFox.model.Invoice;
import com.assignment.FreightFox.service.InvoiceService;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping(value="/generate",method=RequestMethod.POST)
	public ResponseEntity<?> generateInvoice(@RequestBody Invoice body){
		byte[] invoiceByte;
		try {
			invoiceByte = invoiceService.generateInvoice(body);
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentDispositionFormData("attachment", "Invoice.pdf");
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentLength(invoiceByte.length);
	        return ResponseEntity.ok().headers(headers).body(invoiceByte);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
