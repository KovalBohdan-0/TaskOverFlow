package com.gft.taskoverflow.customer.token;

import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.email.EmailSender;
import com.gft.taskoverflow.exception.EmailAlreadyConfirmedException;
import com.gft.taskoverflow.exception.EmailAlreadySentException;
import com.gft.taskoverflow.exception.ResourceNotFoundException;
import com.gft.taskoverflow.exception.TokenExpiredException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final CustomerService customerService;
    private final EmailSender emailSender;


    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public String generateAndSaveTokenForCustomer(String email) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), customerService.getCustomerByEmail(email));

        confirmationTokenRepository.save(confirmationToken);

        return token;
    }

    public void sendConfirmationEmail(String host) {
        if (this.confirmationTokenRepository.findByCustomerId(customerService.getCurrentCustomerEntity().getId()).isPresent()) {
            throw new EmailAlreadySentException();
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String token = generateAndSaveTokenForCustomer(email);
        String link = "https://" + host + "/api/v1/confirmation?token=" + token;
        emailSender.send(email, emailSender.buildEmail(email, link), "Confirm your email");
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = getToken(token).orElseThrow(() -> new ResourceNotFoundException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException();
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(token);
        }

        setConfirmedAt(token);
        customerService.confirmEmail(confirmationToken.getCustomer().getEmail());
        return "confirmed";
    }
}
