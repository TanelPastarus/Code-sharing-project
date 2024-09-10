package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@RestController
public class Controller {

    private final CodeService codeService;

    public Controller(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping(value = "/code", produces = "text/html")
    public ModelAndView getCode() {
        return codeService.Code();
    }

    @GetMapping(value = "/code/{id}", produces = "text/html" )
    public ModelAndView getCodeId(@PathVariable UUID id) {
        return codeService.CodeId(id);
    }

    @GetMapping(value = "/code/latest", produces =  "text/html" )
    public ModelAndView getCodeLatest() {
        return codeService.CodeLatest();
    }

    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCodeJson() throws JsonProcessingException {
        return new ResponseEntity<>(codeService.codeJson(), HttpStatus.OK);
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCodeJsonId(@PathVariable UUID id) throws JsonProcessingException {
        String response = codeService.codeJsonId(id);
        System.out.println("{}".equals(response));
        if ("{}".equals(response)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getCodeLatestJson() throws JsonProcessingException {
        return new ResponseEntity<>(codeService.codeLatestJson(), HttpStatus.OK);
    }

    @PostMapping(value = "/api/code/new", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postCode(@RequestBody Code c) {
        return new ResponseEntity<>(codeService.postNewCode(c), HttpStatus.OK);
    }


    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView getNewCode() {
        return codeService.newCodeHtml();
    }
}
