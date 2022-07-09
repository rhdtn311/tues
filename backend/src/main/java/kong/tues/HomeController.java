package kong.tues;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class HomeController {

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping
    public ResponseEntity<Data> home() {
        System.out.println("하하하");
        return ResponseEntity.ok(new Data("ㅋㅋㅋ좀 돼라!!"));
    }

    class Data {
        private String data;

        public Data(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}