package com.E_POC2.Utility;
import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




public class EmailOfTestExecution {
	
    public static void mail(int totalTestCases,int passCount,int failedCount) {
        // Recipient email addresses
        String[] recipients = {"jeetendra.yadavmh11@gmail.com"};

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.debug", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        

        final String senderEmail = "jeet.yadav4555@gmail.com";
        final String senderPassword = "ujxolxsayhgxcvks";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));

            // Add recipients
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            } 
            message.setSubject("Report POC2");
            
            
         

            MimeBodyPart textPart = new MimeBodyPart();
            String emailContent = "<html><body><h1 style=\"font-size: 16px;\">Hello,</h1><p>Please refer to the attached document for the Ellicium test report.</p><p>Here is an update on your project:</p><table border='1'><tr><th>Total number of Testcases</th><th>Total number of Testcase passed</th><th>Total number of Testcase failed</th></tr><tr><td style=\"text-align: center\"> "+(totalTestCases-1) +" </td><td style=\"text-align: center\"> "+(passCount-1)+"</td><td style=\"text-align: center\"> "+(failedCount)+"</td></tr></table></body></html>";
            textPart.setContent(emailContent, "text/html");

            // Attach the Excel file
            MimeBodyPart attachmentPart = new MimeBodyPart();
            File file = new File("E:\\Ellicium_Updated_Poc\\E_POC2\\TestDataFiles\\TestcaseReport_Dashboard.xlsx"); 
            DataSource source = new FileDataSource(file);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(file.getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);
            multipart.addBodyPart(textPart);
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
