package com.comp301.a09nonograms.view;

import com.comp301.a09nonograms.controller.Controller;
import com.comp301.a09nonograms.model.Model;
import com.comp301.a09nonograms.model.ModelImpl;
import com.comp301.a09nonograms.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PuzzelView implements FXComponent, ModelObserver {
  private final ModelImpl model;
  private final Controller controller;
  Stage stage;
  private Scene scene;

  public PuzzelView(Controller c, ModelImpl m, Stage st) {
    controller = c;
    model = m;
    stage = st;
  }

  @Override
  public void update(Model model) {
    scene.getStylesheets().add("style/stylesheet.css");
    scene.setRoot(render());
    stage.setScene(scene);
  }

  public Scene getScene() {
    scene = new Scene(render(), 1000, 1000);
    scene.getStylesheets().add("style/stylesheet.css");
    return scene;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();

    for (int h = 0; h < model.getHeight(); h++) {
      for (int w = 0; w < model.getWidth(); w++) {

        Button b = makeNonogramButton(h, w);
        if (controller.isEliminated(h, w)) {
          b.setStyle("-fx-background-color: #FFFFFF");
          b.setText("X");
        } else if (controller.isShaded(h, w)) {
          b.setStyle("-fx-background-color: #ABFFE0");
          b.setText("");
        } else {
          b.setStyle("-fx-background-color: #FFFFFF");
          b.setText("");
        }
        if (controller.isSolved()) {
          Banner();
          if (controller.isShaded(h, w)) {
            b.setStyle("-fx-background-color: #ffc0cb");
          } else b.setStyle("-fx-background-color: #ff748c");
        }
        grid.add(b, w, h, 1, 1);
      }
    }
    grid.setAlignment(Pos.CENTER);
    VBox vo = new VBox();
    vo.getChildren().addAll(makeVboxLabel(), grid);
    vo.setAlignment(Pos.CENTER);
    HBox ho = new HBox();
    ho.getChildren().addAll(makeHboxLabel(), vo);
    ho.setAlignment(Pos.CENTER);
    VBox h = new VBox();
    h.getChildren().add(Banner());
    h.getChildren().add(ho);
    h.getChildren().add(MakeButtonBlock());
    h.setAlignment(Pos.CENTER);
    return h;
  }

  // Label Helper Methods
  // *********************************************************************************

  private Node makeHboxLabel() {
    int zerocount = 0;
    VBox v = new VBox();
    Rectangle gap = new Rectangle();
    gap.setWidth(150);
    gap.setHeight(150);
    gap.setFill(Color.WHITE);
    StackPane pancakes = new StackPane();
    pancakes.getChildren().addAll(gap, instructions());
    v.getChildren().add(pancakes);
    for (int i = 0; i < model.getHeight(); i++) {
      Rectangle r = new Rectangle();
      r.setWidth(145);
      r.setHeight(48);
      r.setStrokeWidth(2);
      r.setFill(Color.DARKCYAN);
      r.setStroke(Color.DARKSLATEGREY);
      StackPane Stack = new StackPane();

      HBox text = new HBox();
      int total = 0;
      for (int g = 0; g < controller.getClues().getRowClues(i).length; g++) {
        total = total + controller.getClues().getRowClues(i)[g];
      }
      if (total == 0) {
        Label innerLabel = new Label(0 + "   ");
        innerLabel.setAlignment(Pos.CENTER);
        text.getChildren().add(innerLabel);
        text.setAlignment(Pos.CENTER);

      } else {
        for (int g = 0; g < controller.getClues().getRowClues(i).length; g++) {

          int value = controller.getClues().getRowClues(i)[g];
          if (value != 0) {
            Label innerLabel = new Label(value + "   ");
            innerLabel.setAlignment(Pos.CENTER);
            text.getChildren().add(innerLabel);
            text.setAlignment(Pos.CENTER);
          }
        }
      }
      Stack.getChildren().addAll(r, text);
      v.getChildren().add(Stack);
    }

    return v;
  }

  private Node makeVboxLabel() {
    HBox v = new HBox();
    for (int i = 0; i < model.getWidth(); i++) {
      Rectangle r = new Rectangle();
      r.setWidth(48);
      r.setHeight(145);
      r.setStrokeWidth(2);
      r.setFill(Color.DARKCYAN);
      r.setStroke(Color.DARKSLATEGREY);
      StackPane Stack = new StackPane();

      VBox text = new VBox();
      int total = 0;
      for (int g = 0; g < controller.getClues().getColClues(i).length; g++) {
        total = total + controller.getClues().getColClues(i)[g];
      }
      if (total == 0) {
        Label innerLabel = new Label(0 + "   ");
        innerLabel.setAlignment(Pos.CENTER);
        text.getChildren().add(innerLabel);
        text.setAlignment(Pos.CENTER);

      } else {

        for (int g = 0; g < controller.getClues().getColClues(i).length; g++) {
          int value = controller.getClues().getColClues(i)[g];
          if (value != 0) {
            Label innerLabel = new Label(value + "");
            innerLabel.setAlignment(Pos.CENTER);
            text.getChildren().add(innerLabel);
            text.setAlignment(Pos.CENTER);
          }
        }
      }
      Stack.getChildren().addAll(r, text);
      v.getChildren().add(Stack);
    }

    return v;
  }

  // Button Grid code
  // *********************************************************************************

  private Button makeNonogramButton(int h, int w) {
    Button b = new Button("");
    b.setStyle("-fx-background-color: #FFFFFF");
    b.setPrefHeight(50);
    b.setPrefWidth(50);

    b.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            controller.toggleShaded(h, w);
          }
          if (event.getButton() == MouseButton.SECONDARY) {
            controller.toggleEliminated(h, w);
          }
        });
    return b;
  }

  // Button Bar Code
  // ********************************************************************************

  private HBox MakeButtonBlock() {
    HBox h = new HBox();
    h.getChildren().addAll(NextButton(), ClearButton(), BackButton(), RandomButton());
    h.setAlignment(Pos.CENTER);
    return h;
  }

  private Button ClearButton() {
    Button b = new Button("Clear");
    b.setStyle(
        "-fx-pref-width: 100px;" + "-fx-background-color: #ff748c;" + "-fx-font-size: 15px;");
    b.setPrefHeight(50);
    b.setPrefWidth(100);
    b.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            controller.clearBoard();
          }
        });

    return b;
  }

  private Button NextButton() {
    Button b = new Button("Next");
    b.setStyle(
        "-fx-pref-width: 100px;" + "-fx-background-color: #ff748c;" + "-fx-font-size: 15px;");

    b.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            controller.nextPuzzle();
          }
        });

    return b;
  }

  private Button BackButton() {
    Button b = new Button("Previous");
    b.setStyle(
        "-fx-pref-width: 100px;" + "-fx-background-color: #ff748c;" + "-fx-font-size: 15px;");
    b.setPrefHeight(50);
    b.setPrefWidth(100);

    b.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            controller.prevPuzzle();
          }
        });

    return b;
  }

  private Button RandomButton() {
    Button b = new Button("Random");
    b.setStyle(
        "-fx-pref-width: 100px;" + "-fx-background-color: #ff748c;" + "-fx-font-size: 15px;");
    b.setPrefHeight(50);
    b.setPrefWidth(100);

    b.setOnMouseClicked(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            controller.randPuzzle();
          }
        });

    return b;
  }

  // Banner for IS SOLVED

  private Node Banner() {
    Label l = new Label("The Puzzle is not solved.");
    if (controller.isSolved()) {
      l = new Label(" You solved it!");
    }
    l.setAlignment(Pos.CENTER);
    l.setStyle("-fx-font-size: 20px;");
    return (l);
  }

  // Instrcutions

  private Node instructions() {
    VBox V = new VBox();
    Label L = new Label("Green = shaded");
    Label L2 = new Label("X = Eliminated");
    Label L3 = new Label("Blank = empty");
    Label L4 = new Label("Pink = Solved");
    Label L5 =
        new Label(
            "Puzzle  " + (controller.getPuzzleIndex() + 1) + "/" + controller.getPuzzleCount());
    L5.setStyle("-fx-font-size: 15px;");
    V.getChildren().addAll(L5, L, L2, L3, L4);
    V.setAlignment(Pos.CENTER);
    return V;
  }
}
