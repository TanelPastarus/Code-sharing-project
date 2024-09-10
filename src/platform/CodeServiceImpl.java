package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final ObjectMapper o = new ObjectMapper().findAndRegisterModules();
    private final Code snippet = new Code("print('Hello World!')", 0, 0);

    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public ModelAndView Code() {
        ModelAndView model = new ModelAndView("codePage");

        model.addObject("codeBody", snippet.getCode());
        model.addObject("date", snippet.getDate().format(Code.formatter));
        model.addObject("views", -1);
        model.addObject("time", -1);

        return model;
    }

    public ModelAndView CodeId(UUID id) {
        ModelAndView model = new ModelAndView("codePage");
        Optional<Code> code = codeRepository.findById(id);

        if (code.isEmpty()) {
            model.setStatus(HttpStatus.NOT_FOUND);
            return model;
        }

        Code c = code.get();
        int t;
        if (!c.isTimeRestricted() && !c.isViewRestricted()) {
            model.addObject("codeBody", c.getCode());
            model.addObject("date", c.getDate().format(Code.formatter));
            model.addObject("views", -1);
            model.addObject("time", -1);
            return model;
        }

        if (c.isTimeRestricted()) {
            long sub = c.getDate().until(LocalDateTime.now(), ChronoUnit.SECONDS);
            if ((int) sub >= c.getTime()) {
                model.setStatus(HttpStatus.NOT_FOUND);
                return model;
            } else {
                t = c.getTime() - (int) sub;
                model.addObject("time", t);
            }
        } else model.addObject("time", -1);

        if (c.isViewRestricted()) {
            if (c.getViews() <= 0) {
                model.setStatus(HttpStatus.NOT_FOUND);
                return model;
            } else {
                c.setViews(c.getViews() - 1);
                model.addObject("views", c.getViews());
            }
        } else model.addObject("views", -1);


        codeRepository.save(c);
        model.addObject("codeBody", c.getCode());
        model.addObject("date", c.getDate().format(Code.formatter));

        return model;
    }

    public ModelAndView CodeLatest() {
        ModelAndView model = new ModelAndView("latest");
        List<Code> codes = codeRepository.
                findTop10CodesByTimeRestrictedIsFalseAndViewRestrictedIsFalseOrderByDateDesc();

        model.addObject("codeSnippets", codes);

        return model;
    }

    public String codeJson() throws JsonProcessingException {
        return o.writerWithDefaultPrettyPrinter().writeValueAsString(snippet);
    }

    public String codeJsonId(UUID id) throws JsonProcessingException {

        Optional<Code> code = codeRepository.findById(id);

        if (code.isEmpty())
            return "{}";


        Code c = code.get();
        int t = 0;
        if (!c.isTimeRestricted() && !c.isViewRestricted())
            return o.writerWithDefaultPrettyPrinter().writeValueAsString(c);


        if (c.isTimeRestricted()) {
            t = c.getTime();
            long sub = c.getDate().until(LocalDateTime.now(), ChronoUnit.SECONDS);
            if ((int) sub >= c.getTime())
                return "{}";
            else c.setTime(c.getTime() - (int) sub);
        }

        if (c.isViewRestricted()) {
            if (c.getViews() <= 0)
                return "{}";
            else c.setViews(c.getViews() - 1);
        }

        String response = o.writerWithDefaultPrettyPrinter().writeValueAsString(c);
        c.setTime(t);
        codeRepository.save(c);
        return response;
    }

    public String codeLatestJson() throws JsonProcessingException {
        List<Code> codes = codeRepository.
                findTop10CodesByTimeRestrictedIsFalseAndViewRestrictedIsFalseOrderByDateDesc();
        return o.writerWithDefaultPrettyPrinter().writeValueAsString(codes);
    }

    public String postNewCode(Code c) {
        Code n = new Code(c.getCode(), c.getTime(), c.getViews());
        if (n.getTime() > 0) n.setTimeRestricted(true);
        if (n.getViews() > 0) n.setViewRestricted(true);

        if (!n.isTimeRestricted() || !n.isViewRestricted()) {
            snippet.setCode(c.getCode());
            snippet.setDate(n.getDate());
            snippet.setTime(n.getTime());
            snippet.setViews(n.getViews());
        }

        n = codeRepository.save(n);
        return "{\"id\" : \"" + (n.getId().toString()) + "\"}";
    }

    public ModelAndView newCodeHtml() {
        return new ModelAndView("buttonPage");
    }


}
