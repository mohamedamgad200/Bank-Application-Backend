package com.example.Bank.Application.service;

import com.example.Bank.Application.dto.EmailDetails;
import com.example.Bank.Application.entity.Transaction;
import com.example.Bank.Application.entity.User;
import com.example.Bank.Application.repository.TransactionRepository;
import com.example.Bank.Application.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankStatement {
    /**
     * retrieve list of transactions within data range given and account number
     * generate a pdf file for transactions
     * send the file via email
     */
    private final TransactionRepository transactionRepository;
    private final String FILE="C:\\Users\\legion\\Desktop\\Bank-Application\\src\\main\\resources\\static\\statementsfiles\\MyStatement.pdf";
    private final UserRepository userRepository;
    private final EmailService emailService;
    public List<Transaction> generateStatment(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException
    {
        LocalDate fromDate = LocalDate.parse(startDate);
        LocalDate toDate = LocalDate.parse(endDate);
        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = toDate.atTime(LocalTime.MAX);

        List<Transaction>transactions=transactionRepository.findAllByAccountNumberAndCreatedAtBetween(accountNumber, from, to);

        User user = userRepository.findByAccountNumber(accountNumber);
        String customerName= user.getFirstName()+" "+user.getLastName()+" "+user.getOtherName();

        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize, 36, 36, 36, 36); // Add margins (left, right, top, bottom)
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        PdfPTable bankInfoTable = new PdfPTable(1);
        bankInfoTable.setWidthPercentage(100);

        PdfPCell bankName = new PdfPCell(new Phrase("Bank Name", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.WHITE)));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(12f);
        bankName.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell bankAddress = new PdfPCell(new Phrase("72, Some Address, Cairo, Egypt"));
        bankAddress.setBorder(0);
        bankAddress.setPadding(8f);
        bankAddress.setHorizontalAlignment(Element.ALIGN_CENTER);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);


        document.add(bankInfoTable);
        document.add(Chunk.NEWLINE);

        PdfPTable statementInfo = new PdfPTable(2);
        statementInfo.setWidthPercentage(100);
        statementInfo.setSpacingBefore(10f);
        statementInfo.setSpacingAfter(10f);
        statementInfo.setWidths(new int[]{1, 2});

        Font infoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        statementInfo.addCell(createCell("Start Date:", infoFont));
        statementInfo.addCell(createCell(startDate.toString(), infoFont));
        statementInfo.addCell(createCell("End Date:", infoFont));
        statementInfo.addCell(createCell(endDate.toString(), infoFont));
        statementInfo.addCell(createCell("Customer Name:", infoFont));
        statementInfo.addCell(createCell(customerName, infoFont));
        statementInfo.addCell(createCell("Address:", infoFont));
        statementInfo.addCell(createCell(user.getAddress(), infoFont));

        document.add(statementInfo);


        Paragraph title = new Paragraph("STATEMENT OF ACCOUNT", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(10f);
        title.setSpacingAfter(10f);
        document.add(title);


        PdfPTable transactionTable = new PdfPTable(4);
        transactionTable.setWidthPercentage(100);
        transactionTable.setSpacingBefore(10f);
        transactionTable.setWidths(new int[]{2, 3, 3, 2});

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

        transactionTable.addCell(createHeaderCell("Date", headerFont));
        transactionTable.addCell(createHeaderCell("Transaction Type", headerFont));
        transactionTable.addCell(createHeaderCell("Transaction Amount", headerFont));
        transactionTable.addCell(createHeaderCell("Status", headerFont));

        Font rowFont = new Font(Font.FontFamily.HELVETICA, 11);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        transactions.forEach(transaction -> {
            String formattedDate = transaction.getCreatedAt().toLocalDate().format(formatter);
            transactionTable.addCell(new PdfPCell(new Phrase(formattedDate, rowFont)));
            transactionTable.addCell(new PdfPCell(new Phrase(transaction.getTransactionType(), rowFont)));
            transactionTable.addCell(new PdfPCell(new Phrase(transaction.getAmount().toString(), rowFont)));
            transactionTable.addCell(new PdfPCell(new Phrase(transaction.getStatus(), rowFont)));
        });

        document.add(transactionTable);
        document.close();

        EmailDetails emailDetails=EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested account statement attachment")
                .attachment(FILE)
                .build();
        emailService.sendEmailWithAttachment(emailDetails);

        return transactions;
    }
    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(0);
        cell.setPadding(5f);
        return cell;
    }

    private PdfPCell createHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8f);
        cell.setBorder(0);
        return cell;
    }
}
