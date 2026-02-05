package app.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/whatsapp")
@RequiredArgsConstructor
@Slf4j
public class WhatsAppWebhookController {

  @Value("${whatsapp.verify-token}")
  private String verifyToken;

  private final WhatsAppService whatsAppService;

  @GetMapping
  public ResponseEntity<String> verify(
      @RequestParam("hub.mode") String mode,
      @RequestParam("hub.verify_token") String token,
      @RequestParam("hub.challenge") String challenge
  ) {
    log.debug("mode = " + mode + "\ntoken = " + token + "\nchallenge = " + challenge);
    if ("subscribe".equals(mode) && verifyToken.equals(token)) {
      log.debug("token = " + token);
      return ResponseEntity.ok(challenge);
    }
    log.debug("HttpStatus.FORBIDDEN");
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  @PostMapping
  public ResponseEntity<Void> receive(@RequestBody String payload) {
    log.info("Incoming WhatsApp payload:\n{}", payload);

    whatsAppService.sendText(
        "<USER_PHONE>",
        "Привет! Бот жив 👋"
    );

    return ResponseEntity.ok().build();
  }
}
