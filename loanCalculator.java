package org.example.loancalc_github_submit;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class loanCalculator extends Application {

    //declare text fields for the application;
    private TextField annualInterestRateField;
    private TextField numberOfYearsField;
    private TextField loanAmountField;
    private TextField monthlyPaymentField;
    private TextField totalPaymentField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //set stage/application title
        primaryStage.setTitle("LoanCalculator");

        //instantiate object grid pane, set grid pane padding, v + h gap
        GridPane myGridPane = new GridPane();
        myGridPane.setPadding(new Insets(10, 10, 10, 10));
        myGridPane.setHgap(10);
        myGridPane.setVgap(5);

        //instantiate label objects for each field
        Label annualInterestRateLabel = new Label("Annual Interest Rate:");
        Label numberOfYearsLabel = new Label("Number of Years:");
        Label loanAmountLabel = new Label("Loan Amount:");
        Label monthlyPaymentLabel = new Label("Monthly Payment:");
        Label totalPaymentLabel = new Label("Total Payment:");

        //instantiate text field objects
        annualInterestRateField = new TextField();
        numberOfYearsField = new TextField();
        loanAmountField = new TextField();
        monthlyPaymentField = new TextField();
        totalPaymentField = new TextField();

        //prevent GUI/application user from typing input into output fields monthly payment and total payment
        monthlyPaymentField.setEditable(false);
        totalPaymentField.setEditable(false);

        //instantiate calculate button object
        Button calculateButton = new Button("Calculator");

        //set action for calculate button when clicked
        calculateButton.setOnAction(e -> calculateLoanPayment());

        //place labels and fields in correct position in grid pane to get desired application layout
        myGridPane.add(annualInterestRateLabel, 0, 0);
        myGridPane.add(annualInterestRateField, 1, 0);
        myGridPane.add(numberOfYearsLabel, 0, 1);
        myGridPane.add(numberOfYearsField, 1, 1);
        myGridPane.add(loanAmountLabel, 0, 2);
        myGridPane.add(loanAmountField, 1, 2);
        myGridPane.add(monthlyPaymentLabel, 0, 3);
        myGridPane.add(monthlyPaymentField, 1, 3);
        myGridPane.add(totalPaymentLabel, 0, 4);
        myGridPane.add(totalPaymentField, 1, 4);
        myGridPane.add(calculateButton, 1, 5);

        //set the calculate button to the right of the grid pane
        GridPane.setHalignment(calculateButton, HPos.RIGHT);

        //set scene and show the primary stage
        Scene scene = new Scene(myGridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //method for calculating monthly and total loan payments
    private void calculateLoanPayment() {

        //check to make sure inputs are valid with alert dialog and error message
        String errorMessage = validInput();
        if(errorMessage != null) {
            showErrorMessage(errorMessage);
            return;
        }

        //try block in case of invalid inputs
        try {

            //get input values from text fields
            double annualInterestRate = Double.parseDouble(annualInterestRateField.getText());
            int numberOfYears = Integer.parseInt(numberOfYearsField.getText());
            double loanAmount = Double.parseDouble(loanAmountField.getText());

            //calculate monthly interest rate
            double monthlyInterestRate;
            monthlyInterestRate = annualInterestRate / 1200;

            //calculate monthly payment
            double monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - 1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12));

            //calculate total payment
            double totalPayment = monthlyPayment * numberOfYears * 12;

            //output monthly and total payments in appropriate text fields and formatted with $ and to 2 decimal places
            monthlyPaymentField.setText(String.format("$%.2f", monthlyPayment));
            totalPaymentField.setText(String.format("$%.2f", totalPayment));

            //catch block for handling unexpected exceptions with error dialog and message
        } catch (Exception e) {

            showErrorMessage("Error calculating loan");
        }
    }

    //method to make sure inputs are valid and the corresponding message when invalid
    private String validInput() {

        //try block in case of invalid input
        try{

            //check to make sure annual interest rate input is greater than 0 and message returned if not
            double annualInterestRate = Double.parseDouble(annualInterestRateField.getText());
            if(annualInterestRate <= 0) {
                return "Interest rate must be positive";
            }

            //check to make sure number of years input is greater than 0 and message returned if not
            double numberOfYears = Double.parseDouble(numberOfYearsField.getText());
            if(numberOfYears <= 0) {
                return "Number of years must be positive";
            }

            //check to make sure the loan amount input is greater than 0 and message returned if not
            double loanAmount = Double.parseDouble(loanAmountField.getText());
            if(loanAmount <= 0) {
                return "Loan amount must be positive";
            }
        }

        //catch block for handling exceptions and the corresponding message for exceptions
        catch(NumberFormatException e){
            return "Enter a valid number";
        }

        //all inputs are valid, return null
        return null;
    }

    //method to show error alert and message
    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);

        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}