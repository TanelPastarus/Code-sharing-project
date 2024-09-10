package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Service
public interface CodeService {
    public ModelAndView Code();
    public ModelAndView CodeId(UUID id);
    public ModelAndView CodeLatest();
    public String codeJson() throws JsonProcessingException;
    public String codeJsonId(UUID id) throws JsonProcessingException;
    public String codeLatestJson() throws JsonProcessingException;
    public String postNewCode(Code c);
    public ModelAndView newCodeHtml();
}
