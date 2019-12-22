package soa.web;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchController {

  private final ConsumerTemplate consumerTemplate;
  private final ProducerTemplate producerTemplate;

  @Autowired
  public SearchController(ConsumerTemplate consumerTemplate, ProducerTemplate producerTemplate) {
    this.consumerTemplate = consumerTemplate;
    this.producerTemplate = producerTemplate;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }


  @RequestMapping(value = "/twitter")
  @ResponseBody
  public Object searchTwitter(@RequestParam("q") String q,
                       @RequestParam(name = "max", defaultValue = "10", required = false) int max) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("CamelTwitterKeywords", q);
    headers.put("CamelTwitterCount", max);
    return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
  }

  
  @RequestMapping(value = "/github")
  @ResponseBody
  public Object searchGithub(@RequestParam("user") String user,
                             @RequestParam("repo") String repo) {
    return consumerTemplate.receiveBody(
        "github://pullRequest?oauthToken={{github.oauthToken}}&"
        + "repoName=" + repo + "&"
        + "repoOwner=" + user);
  }
}