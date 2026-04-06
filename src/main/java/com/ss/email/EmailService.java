package com.ss.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendStudentCredentials(String to, String username, String password) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("HMS Student Login Credentials");

            helper.setText(
                    """
                    <h2>Welcome to HMS</h2>
                    <p><b>Username:</b> """ + username + """
                    </p>
                    <p><b>Password:</b> """ + password + """
                    </p>
                    <p>Please change password after login</p>
                    """,
                    true
            );

            mailSender.send(message);

            System.out.println("Email sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
