package stepdefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static org.junit.Assert.*;

public class Products {

    public RequestSpecification httpRequest;
    public Response response;
    public int ResponseCode;
    public ResponseBody body;
    public JSONObject requestParams;
    String s;

    @Given("I hit the url of get products api endpoint")
    public void i_hit_the_url_of_get_products_api_endpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
    }

    @When("I pass the url of products in the request")
    public void i_pass_the_url_of_products_in_the_request() {
        httpRequest = RestAssured.given();
        response = httpRequest.get("products");
    }

    @Then("I receive the response code as {}")
    public void i_receive_the_response_code_as(Integer int1) {
        ResponseCode = response.getStatusCode();
        assertEquals(200, ResponseCode);
    }

    @Then("I verify that the rate of the first product is {}")
    public void i_verify_that_the_rate_of_the_first_product_is(String rate) {
        body = response.getBody();

        // convert response body to string
        String responseBody = body.asString();

        JsonPath jsonPath = response.jsonPath();
        s = jsonPath.getJsonObject("rating[0].rate").toString();
        assertEquals(s, rate);
    }

    @Given("I hit the url of post products api endpoint")
    public void iHitTheUrlOfPostProductsApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        httpRequest = RestAssured.given();
        requestParams = new JSONObject();

    }

    @And("I pass the request body of product title {}")
    public void iPassTheRequestBodyOfProductTitle(String title) {
        requestParams.put("title", title);
        requestParams.put("price", 9.1);
        requestParams.put("description", "test description");
        requestParams.put("category", "test category");
        requestParams.put("image", "http://example.com");
        httpRequest.body(requestParams.toJSONString());
        Response response = httpRequest.post("products");
        ResponseBody body = response.getBody();
        JsonPath jsonPath = response.jsonPath();

        s = jsonPath.getJsonObject("id").toString();

        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }

    @Then("I receive the response body with id as {}")
    public void iReceiveTheResponseBodyWithIdAs(String id) {
        assertEquals(id, s);
    }

    @Given("I hit the url of put products api endpoint")
    public void iHitTheUrlOfPutProductsApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        requestParams = new JSONObject();
    }

    @When("I pass the url of products in the request with {}")
    public void iPassTheUrlOfProductsInTheRequestWithProductNumber(String productNumber) {
        // TAMBAHAN PENTING: Menyamar jadi Browser Chrome & set tipe data JSON
        httpRequest = RestAssured.given()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*");

        // Bagian ini tetap sama seperti kodemu
        requestParams.put("title", "test product");
        requestParams.put("price", 9.1);
        requestParams.put("description", "test description");
        requestParams.put("category", "test category");
        requestParams.put("image", "http://example.com");

        httpRequest.body(requestParams.toJSONString());

        response = httpRequest.put("products/" + productNumber);

        // Debugging (Bagus, pertahankan ini)
        ResponseBody body = response.getBody();
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + body.asString());

        // Hati-hati: Baris di bawah ini akan error kalau responnya bukan JSON (misal masih 403 HTML)
        // Jadi sebaiknya ditaruh di dalam try-catch atau assert status code dulu
        if (response.getStatusCode() == 200) {
            JsonPath jsonPath = response.jsonPath();
            s = jsonPath.getJsonObject("id").toString();
        }
    }

    @Given("I hit the url of delete products api endpoint")
    public void iHitTheUrlOfDeleteProductsApiEndpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        requestParams = new JSONObject();
    }

    @When("I pass the url of delete products in the request with {}")
    public void iPassTheUrlOfDeleteProductsInTheRequestWithProductNumber(String productNumber) {
        httpRequest = RestAssured.given();
        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.delete("products/" + productNumber);
        ResponseBody body = response.getBody();
        JsonPath jsonPath = response.jsonPath();

        s = jsonPath.getJsonObject("id").toString();

        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }
}
