package com.comp301.a09nonograms.view;

import com.comp301.a09nonograms.PuzzleLibrary;
import com.comp301.a09nonograms.controller.ControllerImpl;
import com.comp301.a09nonograms.model.Clues;
import com.comp301.a09nonograms.model.ModelImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Launch your GUI here

    ModelImpl model = new ModelImpl(PuzzleLibrary.create());
    ControllerImpl controller = new ControllerImpl(model);
    Clues clues = model.GetClues();
    PuzzelView puzzle = new PuzzelView(controller, model, stage);
    model.addObserver(puzzle);
    stage.setScene(puzzle.getScene());
    stage.show();

    // GridPane g = new GridPane();
    // Scene scene = new Scene(g, 1000, 1000);
    // scene.getStylesheets().add("style/stylesheet.css");
    // stage.setScene(scene);

  }
}
