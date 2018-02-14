package pl.edu.agh.mownit.lab10;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    @FXML
    private AnchorPane ap;

    @FXML
    private TextField
            nField,
            hField,
            gField,
            lambdaField,
            TField,
            errField;

    @FXML
    private void calculate() {
        int n;
        double coefficient;
        double maxTemp;
        double absoluteError;
        boolean moreIterations;
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

        try {   //get values from fields
            n = Integer.parseInt(nField.getText());
            double h = Double.parseDouble(hField.getText());
            double g = Double.parseDouble(gField.getText());
            double lambda = Double.parseDouble(lambdaField.getText());
            coefficient = -(h * h * g) / (4 * lambda);
            maxTemp = Double.parseDouble(TField.getText());
            absoluteError = Double.parseDouble(errField.getText());
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data type!",
                    ButtonType.OK);
            alert.setHeaderText("Error occured");
            alert.showAndWait();
            return;
        }

        for (int i = 0; i < n; i++) {   //init list of lists
            matrix.add(new ArrayList<>());
        }


        for (int i = 0; i < n; i++) {           //loop through matrix
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == n - 1) {
                    matrix.get(i).add(0.0);     //set border values to 0
                } else {
                    Random random = new Random();
                    double value = random.nextDouble() * maxTemp;
                    matrix.get(i).add(value);   //set random inner values
                }
            }
        }

        do {    //loop while error > input error
            moreIterations = false;
            for (int i = 1; i < n - 1; i++) {
                for (int j = 1; j < n - 1; j++) {
                    double Tg = matrix.get(i - 1).get(j);
                    double Tl = matrix.get(i).get(j - 1);
                    double Tp = matrix.get(i).get(j + 1);
                    double Td = matrix.get(i + 1).get(j);
                    double newValue = (Tg + Tl + Tp + Td) / 4 - coefficient;
                    if (matrix.get(i).get(j) - newValue > absoluteError) {
                        moreIterations = true;
                    }
                    matrix.get(i).set(j, newValue);
                }
            }
        } while (moreIterations);

        double currentMaxTemp = getMaxTemp(n, matrix);
        drawChart(n, currentMaxTemp, matrix);
    }

    private double getMaxTemp(int n, ArrayList<ArrayList<Double>> matrix) {
        double maxTemp = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix.get(i).get(j) > maxTemp) {
                    maxTemp = matrix.get(i).get(j);
                }
            }
        }
        return maxTemp;
    }

    private void drawChart(int n, double maxTemp, ArrayList<ArrayList<Double>> matrix) {
        final NumberAxis xAxis = new NumberAxis(0, n-1, 1);
        final NumberAxis yAxis = new NumberAxis(0, n-1, 1);
        final ScatterChart<Number,Number> sc = new
                ScatterChart<>(xAxis,yAxis);
        xAxis.setLabel("X");
        yAxis.setLabel("Y");
        sc.setTitle("Heat distribution");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("0.0 - 0.2 Max Temp");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("0.2 - 0.4 Max Temp");
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("0.4 - 0.6 Max Temp");
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("0.6 - 0.8 Max Temp");
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("0.8 - 1.0 Max Temp");

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(matrix.get(i).get(j) < 0.2*maxTemp) {
                    series1.getData().add(new XYChart.Data(i, j));
                } else if(matrix.get(i).get(j) < 0.4*maxTemp) {
                    series2.getData().add(new XYChart.Data(i, j));
                }  else if(matrix.get(i).get(j) < 0.6*maxTemp) {
                    series3.getData().add(new XYChart.Data(i, j));
                } else if(matrix.get(i).get(j) < 0.8*maxTemp) {
                    series4.getData().add(new XYChart.Data(i, j));
                } else {
                    series5.getData().add(new XYChart.Data(i, j));
                }
            }
        }

        sc.getData().addAll(series1, series2, series3, series4, series5);
        sc.setLayoutX(400);
        sc.setLayoutY(250);
        ap.getChildren().add(sc);
    }

}
