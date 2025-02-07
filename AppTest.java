package org.example;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private App calculatorApp; // Assume CalculatorApp is the main JavaFX application class

    @BeforeAll
    static void initToolkit() {
        // Start the JavaFX application thread
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setup() {
        calculatorApp = new App();
    }

    @Test
    void testCalculatorWithInputFileAndTextBoxOutput() throws Exception {
        // Prepare test input and output file paths
        String inputFilePath = "testInput.txt";
        String outputFilePath = "testOutput.txt";

        // Write input commands to the input file
        String inputContent = "5 + 3\n9 * 2\n15 - 7\n";
        Files.write(Paths.get(inputFilePath), inputContent.getBytes());

        // Ensure the output file does not exist before the test
        Files.deleteIfExists(Paths.get(outputFilePath));

        // Run the calculator application with the input file
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                calculatorApp.start(stage); // Start the JavaFX application

                // Assume CalculatorApp has a method to load input from a file
                calculatorApp.loadInputFromFile(inputFilePath);
            } catch (Exception e) {
                fail("Application failed to start or process input file: " + e.getMessage());
            }
        });

        // Wait for the JavaFX application to finish processing
        Thread.sleep(2000); // Adjust based on your application's processing time

        // Get the output from the TextArea
        Platform.runLater(() -> {
            TextArea outputTextArea = calculatorApp.getOutputTextArea(); // Assume the app has this method
            String outputContent = outputTextArea.getText();

            try {
                // Read the expected output file
                String expectedOutput = new String(Files.readAllBytes(Paths.get(outputFilePath)));

                // Verify the TextArea content matches the expected output
                assertEquals(expectedOutput.trim(), outputContent.trim(), "The calculator output did not match the expected results in the TextArea.");
            } catch (IOException e) {
                fail("Failed to read the expected output file: " + e.getMessage());
            }
        });

        // Cleanup test files
        Files.deleteIfExists(Paths.get(inputFilePath));
        Files.deleteIfExists(Paths.get(outputFilePath));
    }

    @AfterEach
    void tearDown() {
        calculatorApp = null;
    }
}


