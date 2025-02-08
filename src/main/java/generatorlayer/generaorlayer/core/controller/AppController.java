package generatorlayer.generaorlayer.core.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().body("OK");
    }
}
