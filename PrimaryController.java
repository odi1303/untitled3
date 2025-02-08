/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.StandardSocketOptions;


public class PrimaryController {

    @FXML // fx:id="A"
    private Button A; // Value injected by FXMLLoader

    @FXML // fx:id="ANDbutton"
    private Button ANDbutton; // Value injected by FXMLLoader

    @FXML // fx:id="B"
    private Button B; // Value injected by FXMLLoader

    @FXML // fx:id="Backround"
    private AnchorPane Backround; // Value injected by FXMLLoader

    @FXML // fx:id="C"
    private Button C; // Value injected by FXMLLoader

    @FXML // fx:id="D"
    private Button D; // Value injected by FXMLLoader

    @FXML // fx:id="DIVbutton"
    private Button DIVbutton; // Value injected by FXMLLoader

    @FXML // fx:id="E"
    private Button E; // Value injected by FXMLLoader

    @FXML // fx:id="EIGHT"
    private Button EIGHT; // Value injected by FXMLLoader

    @FXML // fx:id="F"
    private Button F; // Value injected by FXMLLoader

    @FXML // fx:id="FIVE"
    private Button FIVE; // Value injected by FXMLLoader

    @FXML // fx:id="FOUR"
    private Button FOUR; // Value injected by FXMLLoader

    @FXML // fx:id="MINUSbutton"
    private Button MINUSbutton; // Value injected by FXMLLoader

    @FXML // fx:id="MULTbutton"
    private Button MULTbutton; // Value injected by FXMLLoader

    @FXML // fx:id="NINE"
    private Button NINE; // Value injected by FXMLLoader

    @FXML // fx:id="NOTbutton"
    private Button NOTbutton; // Value injected by FXMLLoader

    @FXML // fx:id="ONE"
    private Button ONE; // Value injected by FXMLLoader

    @FXML // fx:id="ORbutton"
    private Button ORbutton; // Value injected by FXMLLoader

    @FXML // fx:id="PLUSbutton"
    private Button PLUSbutton; // Value injected by FXMLLoader

    @FXML // fx:id="SEVEN"
    private Button SEVEN; // Value injected by FXMLLoader

    @FXML // fx:id="SIX"
    private Button SIX; // Value injected by FXMLLoader

    @FXML // fx:id="THREE"
    private Button THREE; // Value injected by FXMLLoader

    @FXML // fx:id="TWO"
    private Button TWO; // Value injected by FXMLLoader

    @FXML // fx:id="XORbutton"
    private Button XORbutton; // Value injected by FXMLLoader

    @FXML // fx:id="Zero"
    private Button ZERO; // Value injected by FXMLLoader

    @FXML // fx:id="choseBase"
    private ComboBox<String> choseBase;// Value injected by FXMLLoader

    @FXML // fx:id="clear"
    private Button clear; // Value injected by FXMLLoader

    @FXML // fx:id="equals"
    private Button equals; // Value injected by FXMLLoader

    @FXML // fx:id="expression"
    private TextField expression; // Value injected by FXMLLoader

    private String base="";
    private String exp="";
    public String getExp(){
        return exp;
    }
    @FXML
    void initialize(){
        assert choseBase!=null:"fx:id=\"choseBase\" was not injected: check your FXML file 'primary.fxml'.";
        // Add items to the ComboBox
        exp="0";
        choseBase.getItems().addAll("DEC", "BIN", "HEX", "OCT");
        choseBase.setValue("DEC");
        changingBaseToDEC();
        base="DEC";
        exp="";
    }

    @FXML
    void calculating(ActionEvent event) {
        int basis=baseToNumber(base);
        ArithmeticApp app=new ArithmeticApp();
        exp=app.main(basis,exp);
        expression.setText(exp);
    }

    @FXML
        //when the user switched base the expression is changed accordingly
    void chosingBase(ActionEvent event) {
        String temp_base = choseBase.getSelectionModel().getSelectedItem();//the base of the current expression if existed
        if (exp.equals("")){
            exp="0";
            if (temp_base.equals("BIN"))
                changingBaseToBIN();
            else if (temp_base.equals("OCT"))
                changingBaseToOCT();
            else if (temp_base.equals("DEC"))
                changingBaseToDEC();
            else{
                changingBaseToHEX();
            }
            base=temp_base;//updating the actual base
            exp="";
            expression.setText("");
            return;
        }
        if (temp_base.equals("BIN"))
            changingBaseToBIN();
        else if (temp_base.equals("OCT"))
            changingBaseToOCT();
        else if (temp_base.equals("DEC"))
            changingBaseToDEC();
        else{
            changingBaseToHEX();
        }
        base=temp_base;//updating the actual base
        expression.setText(exp);//updating the expression on the textBox

    }
    //change to hexadecimal base
    void changingBaseToHEX(){
        A.setDisable(false);
        B.setDisable(false);
        C.setDisable(false);
        D.setDisable(false);
        E.setDisable(false);
        F.setDisable(false);
        NINE.setDisable(false);
        EIGHT.setDisable(false);
        SEVEN.setDisable(false);
        SIX.setDisable(false);
        FIVE.setDisable(false);
        FOUR.setDisable(false);
        THREE.setDisable(false);
        TWO.setDisable(false);
        PLUSbutton.setDisable(false);
        MINUSbutton.setDisable(false);
        MULTbutton.setDisable(false);
        DIVbutton.setDisable(false);
        ZERO.setDisable(false);
        ONE.setDisable(false);
        XORbutton.setDisable(true);
        ANDbutton.setDisable(true);
        NOTbutton.setDisable(true);
        ORbutton.setDisable(true);
        equals.setDisable(false);
        int basis=baseToNumber(base);
        if (exp.contains("+")||exp.contains("-")||exp.contains("*")||exp.contains("/")){
            ArithmeticApp app=new ArithmeticApp();
            exp=app.main(basis,exp);
            if (exp.equals("Error: trying to divide by 0 (evaluated: \"0\")")||
            exp.equals("invalid expression")){
             return;
            }
        }
        int num = 0;
        switch(basis) {
            case 2:
                num = Integer.parseInt(exp, 2);
                break;
            case 8:
                num = Integer.parseInt(exp, 8);
                break;
            case 10:
                num = Integer.parseInt(exp, 10);
                break;
            case 16:
                num = Integer.parseInt(exp, 16);
                break;
        }
        exp = Integer.toHexString(num);
        exp=exp.toUpperCase();
    }
    //change to decimal base
    void changingBaseToDEC(){
        NINE.setDisable(false);
        EIGHT.setDisable(false);
        SEVEN.setDisable(false);
        SIX.setDisable(false);
        FIVE.setDisable(false);
        FOUR.setDisable(false);
        THREE.setDisable(false);
        TWO.setDisable(false);
        PLUSbutton.setDisable(false);
        MINUSbutton.setDisable(false);
        MULTbutton.setDisable(false);
        DIVbutton.setDisable(false);
        ZERO.setDisable(false);
        ONE.setDisable(false);
        XORbutton.setDisable(true);
        ANDbutton.setDisable(true);
        NOTbutton.setDisable(true);
        ORbutton.setDisable(true);
        equals.setDisable(false);
        A.setDisable(true);
        B.setDisable(true);
        C.setDisable(true);
        D.setDisable(true);
        E.setDisable(true);
        F.setDisable(true);
        int basis=baseToNumber(base);
        if (exp.contains("+")||exp.contains("-")||exp.contains("*")||exp.contains("/")){
            ArithmeticApp app=new ArithmeticApp();
            exp=app.main(basis,exp);
            if (exp.equals("Error: trying to divide by 0 (evaluated: \"0\")")||
                    exp.equals("invalid expression")){
                return;
            }
        }
        int num = 0;
        switch(basis) {
            case 2:
                num = Integer.parseInt(exp, 2);
                break;
            case 8:
                num = Integer.parseInt(exp, 8);
                break;
            case 10:
                num = Integer.parseInt(exp, 10);
                break;
            case 16:
                num = Integer.parseInt(exp, 16);
                break;
        }
        exp = Integer.toString(num);

    }
    //change to octal base
    void changingBaseToOCT(){
        SEVEN.setDisable(false);
        SIX.setDisable(false);
        FIVE.setDisable(false);
        FOUR.setDisable(false);
        THREE.setDisable(false);
        TWO.setDisable(false);
        PLUSbutton.setDisable(false);
        MINUSbutton.setDisable(false);
        MULTbutton.setDisable(false);
        DIVbutton.setDisable(false);
        ZERO.setDisable(false);
        ONE.setDisable(false);
        XORbutton.setDisable(true);
        ANDbutton.setDisable(true);
        NOTbutton.setDisable(true);
        ORbutton.setDisable(true);
        equals.setDisable(false);
        NINE.setDisable(true);
        EIGHT.setDisable(true);
        int basis=baseToNumber(base);
        if (exp.contains("+")||exp.contains("-")||exp.contains("*")||exp.contains("/")){
            ArithmeticApp app=new ArithmeticApp();
            exp=app.main(basis,exp);
            if (exp.equals("Error: trying to divide by 0 (evaluated: \"0\")")||
                    exp.equals("invalid expression")){
                return;
            }
        }
        int num = 0;
        switch(basis) {
            case 2:
                num = Integer.parseInt(exp, 2);
                break;
            case 8:
                num = Integer.parseInt(exp, 8);
                break;
            case 10:
                num = Integer.parseInt(exp, 10);
                break;
            case 16:
                num = Integer.parseInt(exp, 16);
                break;
            default:
                exp= "Invalid base provided";
        }
        // Convert the parsed number to base 8
        exp = Integer.toOctalString(num);
    }
    //change to binary base
    void changingBaseToBIN(){
        NINE.setDisable(true);
        EIGHT.setDisable(true);
        SEVEN.setDisable(true);
        SIX.setDisable(true);
        FIVE.setDisable(true);
        FOUR.setDisable(true);
        THREE.setDisable(true);
        TWO.setDisable(true);
        XORbutton.setDisable(false);
        ANDbutton.setDisable(false);
        NOTbutton.setDisable(false);
        ORbutton.setDisable(false);
        PLUSbutton.setDisable(false);
        MINUSbutton.setDisable(false);
        MULTbutton.setDisable(false);
        DIVbutton.setDisable(false);
        equals.setDisable(false);
        int basis=baseToNumber(base);
        if (exp.contains("+")||exp.contains("-")||exp.contains("*")||exp.contains("/")){
            ArithmeticApp app=new ArithmeticApp();
            exp=app.main(basis,exp);
            if (exp.equals("Error: trying to divide by 0 (evaluated: \"0\")")||
                    exp.equals("Error: invalid expression")){
                return;
            }
        }
        int num = 0;
        switch(basis) {
            case 2:
                num = Integer.parseInt(exp, 2);
                break;
            case 8:
                num = Integer.parseInt(exp, 8);
                break;
            case 10:
                num = Integer.parseInt(exp, 10);
                break;
            case 16:
                num = Integer.parseInt(exp, 16);
                break;
            default:
                exp= "Invalid base provided";
        }
        // Convert the parsed number to binary with 8 bits
        exp = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
        /*if (exp.length()>8)
            exp="Error: invalid expression";*/
    }
    //convert base in String to int
    int baseToNumber(String base){
        int basis=0;
        if (base.equals("DEC"))
            basis=10;
        else if (base.equals("BIN"))
            basis=2;
        else if (base.equals("HEX")) {
            basis=16;
        }
        else
            basis=8;
        return basis;
    }

    //deleting the current expression
    @FXML
    void clearExpression(ActionEvent event) {
        exp="";
        expression.setText(exp);
    }
    //adding 0 to the expression
    @FXML
    void writing0(ActionEvent event) {
        exp=exp+"0";
        expression.setText(exp);
    }
    //adding 1 to the expression
    @FXML
    void writing1(ActionEvent event) {
        exp=exp+"1";
        expression.setText(exp);
    }
    //adding 2 to the expression
    @FXML
    void writing2(ActionEvent event) {
        exp=exp+"2";
        expression.setText(exp);
    }
    //adding 3 to the expression
    @FXML
    void writing3(ActionEvent event) {
        exp=exp+"3";
        expression.setText(exp);
    }
    //adding 4 to the expression
    @FXML
    void writing4(ActionEvent event) {
        exp=exp+"4";
        expression.setText(exp);
    }
    //adding 5 to the expression
    @FXML
    void writing5(ActionEvent event) {
        exp=exp+"5";
        expression.setText(exp);
    }
    //adding 6 to the expression
    @FXML
    void writing6(ActionEvent event) {
        exp=exp+"6";
        expression.setText(exp);
    }
    //adding 7 to the expression
    @FXML
    void writing7(ActionEvent event) {
        exp=exp+"7";
        expression.setText(exp);
    }
    //adding 8 to the expression
    @FXML
    void writing8(ActionEvent event) {
        exp=exp+"8";
        expression.setText(exp);
    }
    //adding 9 to the expression
    @FXML
    void writing9(ActionEvent event) {
        exp=exp+"9";
        expression.setText(exp);
    }
    //adding A to the expression
    @FXML
    void writingA(ActionEvent event) {
        exp=exp+"A";
        expression.setText(exp);
    }
    //adding & to the expression
    @FXML
    void writingAND(ActionEvent event) {
        exp=exp+"&";
        expression.setText(exp);
    }
    //adding B to the expression
    @FXML
    void writingB(ActionEvent event) {
        exp=exp+"B";
        expression.setText(exp);
    }
    //adding C to the expression
    @FXML
    void writingC(ActionEvent event) {
        exp=exp+"C";
        expression.setText(exp);
    }
    //adding D to the expression
    @FXML
    void writingD(ActionEvent event) {
        exp=exp+"D";
        expression.setText(exp);
    }
    //adding / to the expression
    @FXML
    void writingDIV(ActionEvent event) {
        exp=exp+"/";
        expression.setText(exp);
    }
    //adding E to the expression
    @FXML
    void writingE(ActionEvent event) {
        exp=exp+"E";
        expression.setText(exp);
    }
    //adding F to the expression
    @FXML
    void writingF(ActionEvent event) {
        exp=exp+"F";
        expression.setText(exp);
    }
    //adding * to the expression
    @FXML
    void writingMULT(ActionEvent event) {
        exp=exp+"*";
        expression.setText(exp);
    }
    //adding ~ to the expression
    @FXML
    void writingNOT(ActionEvent event) {
        exp=exp+"~";
        expression.setText(exp);
    }
    //adding | to the expression
    @FXML
    void writingOR(ActionEvent event) {
        exp=exp+"|";
        expression.setText(exp);
    }
    //adding + to the expression
    @FXML
    void writingPLUS(ActionEvent event) {
        exp=exp+"+";
        expression.setText(exp);
    }
    //adding - to the expression
    @FXML
    void MINUS(ActionEvent event) {
        exp=exp+"-";
        expression.setText(exp);
    }
    //adding ^ to the expression
    @FXML
    void writingXOR(ActionEvent event) {
        exp=exp+"^";
        expression.setText(exp);
    }

}
