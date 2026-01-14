//package test.java; // Sesuaikan package-nya

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features", // Lokasi file .feature
        glue = "stepdefinitions", // PENTING: Lokasi package file java @Given @When
//        plugin = {"pretty", "html:target/cucumber-report.html"}
        plugin = {"pretty", "json:target/cucumber.json"}
)
public class RunCucumberTest {
    // Kosongkan saja isinya
}