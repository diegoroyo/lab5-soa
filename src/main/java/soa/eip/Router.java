package soa.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Router extends RouteBuilder {

  @Override
  public void configure() {
    from("direct:search")
            .to("twitter-search://search?consumerKey={{twitter.consumerKey}}&"
                    + "consumerSecret={{twitter.consumerSecret}}&"
                    + "accessToken={{twitter.accessToken}}&"
                    + "accessTokenSecret={{twitter.accessTokenSecret}}");
  }

}
