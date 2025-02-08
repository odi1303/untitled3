package org.example;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


@ExtendWith(ApplicationExtension.class)
class ArithAppTest {
    private static final String ACTUAL_OUTPUT_FILE = "actual-output.txt";
    private ArithmeticApp calculatorApp; // Assume CalculatorApp is the main JavaFX application class

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Start
    private void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeAll
    static void initToolkit() {
        // Start the JavaFX application thread
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    void setup() {
        calculatorApp = new ArithmeticApp();
    }

    @Test
    public void testReadInput1() throws Exception {
        runTest("src/test/java/org/example/in1.txt", "src/test/java/org/example/out1.txt");
    }

    @Test
    public void testReadInput2() throws Exception {
        runTest("src/test/java/org/example/in2.txt", "src/test/java/org/example/out2.txt");
    }

    @Test
    public void testReadInput3() throws Exception {
        runTest("src/test/java/org/example/in3.txt", "src/test/java/org/example/out3.txt");
    }

    @Test
    public void testReadInput4() throws Exception {
        runTest("src/test/java/org/example/in4.txt", "src/test/java/org/example/out4.txt");
    }

    @Test
    public void testReadInput5() throws Exception {
        runTest("src/test/java/org/example/in5.txt", "src/test/java/org/example/out5.txt");
    }

    @Test
    public void testReadInput6() throws Exception {
        runTest("src/test/java/org/example/in6.txt", "src/test/java/org/example/out6.txt");
    }

    @Test
    public void testReadInput7() throws Exception {
        runTest("src/test/java/org/example/in7.txt", "src/test/java/org/example/out7.txt");
    }

    @Test
    public void testReadInput8() throws Exception {
        runTest("src/test/java/org/example/in8.txt", "src/test/java/org/example/out8.txt");
    }

    @Test
    public void testReadInput9() throws Exception {
        runTest("src/test/java/org/example/in9.txt", "src/test/java/org/example/out9.txt");
    }

    @Test
    public void testReadInput10() throws Exception {
        runTest("src/test/java/org/example/in10.txt", "src/test/java/org/example/out10.txt");
    }

    @Test
    public void testReadInput11() throws Exception {
        runTest("src/test/java/org/example/in11.txt", "src/test/java/org/example/out11.txt");
    }

    @Test
    public void testReadInput12() throws Exception {
        runTest("src/test/java/org/example/in12.txt", "src/test/java/org/example/out12.txt");
    }

    @Test
    public void testReadInput13() throws Exception {
        runTest("src/test/java/org/example/in13.txt", "src/test/java/org/example/out13.txt");
    }

    @Test
    public void testReadInput14() throws Exception {
        runTest("src/test/java/org/example/in14.txt", "src/test/java/org/example/out14.txt");
    }

    @Test
    public void testReadInput15() throws Exception {
        runTest("src/test/java/org/example/in15.txt", "src/test/java/org/example/out15.txt");
    }

    void runTest(String inputFile, String outputFile) throws Exception {
        Files.deleteIfExists(Paths.get(ACTUAL_OUTPUT_FILE));
        FxRobot robot = new FxRobot();
        Set<Node> nodes = robot.lookup("#choseBase").queryAll();
        System.out.println("Nodes found: " + nodes.size());
        // Redirect System.in and System.out
        try (InputStream inputStream = new FileInputStream(inputFile);
             PrintStream outputStream = new PrintStream(new FileOutputStream(ACTUAL_OUTPUT_FILE))) {

            System.setIn(inputStream);
            System.setOut(outputStream);
            Scanner scanner = new Scanner(System.in);
            boolean validBase = false;
            int base = 0;
            System.out.println("Enter base (2/8/10/16):");
            base = scanner.nextInt(); //does not clean the enter key. 1
            scanner.nextLine();
            while (!validBase) {
                if (calculatorApp.checkBase(base)) {
                    validBase = true;
                } else {
                    System.out.println("Error – this base isn’t supported. Please enter a base (2/8/10/16):");
                    base = scanner.nextInt(); //does not clean the enter key. 1
                    scanner.nextLine();
                }
            }
            System.out.println("Enter expression:");
            String expression = scanner.nextLine();
            String original_expression = expression;
            expression = expression.replace(" ", "");
            robot.clickOn("#choseBase");
            if (base == 2) {
                robot.clickOn("BIN");
            } else if (base == 8) {
                robot.clickOn("OCT");
            } else if (base == 16) {
                robot.clickOn("HEX");
            } else {
                robot.clickOn("DEC");
            }
            writeTheExpression(robot,expression+"=");
            String result = robot.lookup("#expression").queryAs(TextField.class).getText();
            if (result.equals("Error: invalid expression"))
                System.out.println("Error: invalid expression");
            else if (result.equals("Error: trying to divide by 0 (evaluated: \"0\")")) {
                System.out.println("Error: trying to divide by 0 (evaluated: \"0\")");
            } else
                System.out.println("The value of expression " + original_expression + " is : " + result);
        } finally {
            // Restore System.in and System.out
            System.setIn(System.in);
            System.setOut(System.out);
        }

        // Read expected and actual output
        String expectedOutput = new String(Files.readAllBytes(Paths.get(outputFile))).trim();
        String actualOutput = new String(Files.readAllBytes(Paths.get(ACTUAL_OUTPUT_FILE))).trim();

        // Compare outputs
        assertEquals(expectedOutput, actualOutput, "The actual output does not match the expected output.");
        Files.deleteIfExists(Paths.get(ACTUAL_OUTPUT_FILE));
    }


    @AfterEach
    void tearDown() {
        calculatorApp = null;
    }

    void writeTheExpression(FxRobot robot, String expression) {
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+') {
                robot.clickOn("#PLUSbutton");
            } else if (expression.charAt(i) == '-') {
                robot.clickOn("#MINUSbutton");
            }
            else if (expression.charAt(i) == '*')
                robot.clickOn("#MULTbutton");
            else if (expression.charAt(i) == '/')
                robot.clickOn("#DIVbutton");
            else if (expression.charAt(i) == '|')
                robot.clickOn("#ORbutton");
            else if (expression.charAt(i) == '^')
                robot.clickOn("#XORbutton");
            else if (expression.charAt(i) == '=')
                robot.clickOn("#equals");
            else if (expression.charAt(i) == '~')
                robot.clickOn("#NOTbutton");
            else if (expression.charAt(i) == '&')
                robot.clickOn("#ANDbutton");
            else if (expression.charAt(i) == '0')
                robot.clickOn("#ZERO");
            else if (expression.charAt(i) == '1')
                robot.clickOn("#ONE");
            else if (expression.charAt(i) == '2')
                robot.clickOn("#TWO");
            else if (expression.charAt(i) == '3')
                robot.clickOn("#THREE");
            else if (expression.charAt(i) == '4')
                robot.clickOn("#FOUR");
            else if (expression.charAt(i) == '5')
                robot.clickOn("#FIVE");
            else if (expression.charAt(i) == '6')
                robot.clickOn("#SIX");
            else if (expression.charAt(i) == '7')
                robot.clickOn("#SEVEN");
            else if (expression.charAt(i) == '8')
                robot.clickOn("#EIGHT");
            else if (expression.charAt(i) == '9')
                robot.clickOn("#NINE");
            else if (expression.charAt(i) == 'A')
                robot.clickOn("#A");
            else if (expression.charAt(i) == 'B')
                robot.clickOn("#B");
            else if (expression.charAt(i) == 'C')
                robot.clickOn("#C");
            else if (expression.charAt(i) == 'D')
                robot.clickOn("#D");
            else if (expression.charAt(i) == 'E')
                robot.clickOn("#E");
            else if (expression.charAt(i) == 'F')
                robot.clickOn("#F");
            else {
                System.out.println("Error: invalid expression");
                return;
            }

        }
    }
}

