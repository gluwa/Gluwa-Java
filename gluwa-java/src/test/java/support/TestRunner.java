package support;

import com.gluwa.sdk.GluwaResponse;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Cucumber.class)
@CucumberOptions(
        publish = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber/report.json"},
        features = "src/test/resources/features",
        glue = {"definitions", "support"},
        tags = "@gluwaSdk" // same as VM option -Dcucumber.options="--tags @gluwaSdk"
)
public class TestRunner {

    TransactionTests txTests = new TransactionTests();
    @BeforeClass
    public static void setup() {
        //System.out.println("BeforeAll");
    }

    @AfterClass
    public static void teardown() {
        //System.out.println("AfterAll");
    }
}