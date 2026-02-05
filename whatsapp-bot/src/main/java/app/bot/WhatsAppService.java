package app.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsAppService {

  @Value("${whatsapp.token}")
  private String token;

  @Value("${whatsapp.phone-number-id}")
  private String phoneNumberId;

  private final RestTemplate restTemplate = new RestTemplate();

  public void sendText(String to, String text) {
    String url = "https://graph.facebook.com/v22.0/" + phoneNumberId + "/messages";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    String body = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "text",
          "text": { "body": "%s" }
        }
        """.formatted(to, text);

    HttpEntity<String> request = new HttpEntity<>(body, headers);

    ResponseEntity<String> response =
        restTemplate.postForEntity(url, request, String.class);

    log.info("Send message response: {}", response.getBody());
  }
}

