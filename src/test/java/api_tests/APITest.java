package api_tests;

import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class APITest {

    private static final Logger log = Logger.getLogger(APITest.class);

    @DataProvider
    public Object[][] dataProviderMethod() {
        return new Object[][]{
                {"start"},
                {"save"},
                {"restart"},
                {"logic?click=0"},
                {"logic?click=1"},
                {"logic?click=2"},
                {"logic?click=3"},
                {"logic?click=4"}
        };
    }

    @Test(description = "checks if client requests receive successful responses from the server", dataProvider = "dataProviderMethod")
    public void doRequestsGetSuccessfulResponses(String data) {
        log.info("testing request-  /" + data);
        RestAssured.when().get("http://localhost:8083/" + data).then().assertThat().statusCode(200);







        /*   Response response =
                given()
                        .header("Accept", "application/json")
                        .when()
                        .get("http://localhost:8083/start")
                       // .get("http://localhost:8083/logic?click="+data)
                        .then()
                        .extract()
                        .response();
        System.out.println(response.body().htmlPath().get());*/
        //RestAssured.when().get("http://localhost:8083/index.jsp").then().assertThat().statusCode(200);
  /*      HttpClient client= HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).followRedirects(HttpClient.Redirect.NORMAL).build();
        HttpRequest req= HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/start"))
         //       .GET()
          //      .uri(URI.create("http://localhost:8083/logic?click="+data))
            //    .POST(HttpRequest.BodyPublishers.ofString("logic?click="+data))
                .build();
        HttpResponse <String> resp=client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.statusCode());
//        System.out.println(resp.body());
        HttpRequest req1= HttpRequest.newBuilder()
          //     .uri(URI.create("http://localhost:8083/restart"))
                      .uri(URI.create("http://localhost:8083/logic?click="+data))
               // .uri(URI.create("http://localhost:8083/start?click="+data))
               // .POST(HttpRequest.BodyPublishers.ofString(resp.body()))
                //    .POST(HttpRequest.BodyPublishers.ofString("logic?click="+data))
                .build();

        HttpResponse <String> resp1=client.send(req1, HttpResponse.BodyHandlers.ofString());

        System.out.println(resp1.statusCode());
  //      System.out.println(resp1.body());
        HttpRequest req2= HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8083/index.jsp"))
                .GET()
                .build();

        HttpResponse <String> resp2=client.send(req1, HttpResponse.BodyHandlers.ofString());
        System.out.println(resp2.statusCode());
        System.out.println(resp2.body());*/
    }
}
