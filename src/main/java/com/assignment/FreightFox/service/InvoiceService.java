package com.assignment.FreightFox.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.assignment.FreightFox.model.Invoice;
import com.assignment.FreightFox.model.Item;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
@Service
public class InvoiceService {

	 public byte[] generateInvoice(Invoice body) throws IOException, DocumentException {
	        // Initialize PDF writer
		 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		    Document document = new Document();
		 PdfWriter writer = PdfWriter.getInstance(document, outputStream);
	        document.open();

	        // Fetch seller and buyer information
	        String seller = (String) body.getSeller();
	        String sellerGstin = "GSTIN :"+ body.getSellerGstin();
	        String sellerAddress = body.getSellerAddress();
	        String buyer = body.getBuyer();
	        String buyerGstin = "GSTIN :"+body.getBuyerGstin();
	        String buyerAddress = body.getBuyerAddress();

	        // Add company (seller) name
	        PdfPTable table = new PdfPTable(2);
	        float[] columnWidth1 = new float[] { 50f, 50f };
	        table.setWidths(columnWidth1);
	        table.setWidthPercentage(100); 
	        table.setHorizontalAlignment(Element.ALIGN_CENTER);
	        // Seller Information
	        PdfPCell sellerCell = new PdfPCell();
	        sellerCell.addElement(new Paragraph("Seller: " + seller));
	        sellerCell.addElement(new Paragraph(sellerAddress));
	        sellerCell.addElement(new Paragraph("GSTIN: " + sellerGstin));
	        table.addCell(sellerCell);

	        // Buyer Information
	        PdfPCell buyerCell = new PdfPCell();
	        buyerCell.addElement(new Paragraph("Buyer: " + buyer));
	        buyerCell.addElement(new Paragraph(buyerAddress));
	        buyerCell.addElement(new Paragraph("GSTIN: " + buyerGstin));
	        table.addCell(buyerCell);
	        
	        PdfPTable table1 = new PdfPTable(4);
	        table1.setWidthPercentage(100); // Set the table width to 100%
	        float[] columnWidth = new float[] { 40f, 20f, 20f, 20f };
	        table1.setWidths(columnWidth); // Set column widths
	        table1.setHorizontalAlignment(Element.ALIGN_CENTER);

	        // Add table headers
	        table1.addCell(new PdfPCell(new Phrase("Item Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
	        table1.addCell(new PdfPCell(new Phrase("Quantity", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
	        table1.addCell(new PdfPCell(new Phrase("Rate", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
	        table1.addCell(new PdfPCell(new Phrase("Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

	        // Add items to the table
	        for (Item item : body.getItems()) {
	            // Item name
	            table1.addCell(new PdfPCell(new Phrase(item.getName())));

	            // Quantity
	            table1.addCell(new PdfPCell(new Phrase(item.getQuantity())));

	            // Rate
	            table1.addCell(new PdfPCell(new Phrase(String.valueOf(item.getRate()))));

	            // Amount
	            table1.addCell(new PdfPCell(new Phrase(String.valueOf(item.getAmount()))));
	        }

	        // Add table to the document
	        document.add(table);
	        document.add(table1);
	        document.close();
	        return outputStream.toByteArray();
	 }
}
