package kong.tues.mail.application;

import kong.tues.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSendService {

    private final JavaMailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String FROM_ADDRESS;

    public void mailSend(MailDto mailDto) {
        log.info("mailDTO : {}" ,mailDto);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
