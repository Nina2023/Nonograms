package com.comp301.a09nonograms.controller;

import com.comp301.a09nonograms.model.Clues;
import com.comp301.a09nonograms.model.Model;
import com.comp301.a09nonograms.model.ModelImpl;

public class ControllerImpl implements Controller {
  ModelImpl CurrentModel;

  public ControllerImpl(Model model) {
    CurrentModel = (ModelImpl) model;
  }

  @Override
  public Clues getClues() {
    return CurrentModel.GetClues();
  }

  @Override
  public boolean isSolved() {
    return CurrentModel.isSolved();
  }

  @Override
  public boolean isShaded(int row, int col) {
    return CurrentModel.isShaded(row, col);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    return CurrentModel.isEliminated(row, col);
  }

  @Override
  public void toggleShaded(int row, int col) {
    CurrentModel.toggleCellShaded(row, col);
  }

  @Override
  public void toggleEliminated(int row, int col) {
    CurrentModel.toggleCellEliminated(row, col);
  }

  @Override
  public void nextPuzzle() {
    if (CurrentModel.getPuzzleIndex() == CurrentModel.getPuzzleCount() - 1) {
      CurrentModel.setPuzzleIndex(0);

    } else {
      CurrentModel.setPuzzleIndex(CurrentModel.getPuzzleIndex() + 1);
    }
  }

  @Override
  public void prevPuzzle() {
    if (CurrentModel.getPuzzleIndex() == 0) {
      CurrentModel.setPuzzleIndex(CurrentModel.getPuzzleCount() - 1);

    } else {
      CurrentModel.setPuzzleIndex(CurrentModel.getPuzzleIndex() - 1);
    }
  }

  @Override
  public void randPuzzle() {
    int min = 0;
    int max = CurrentModel.getPuzzleCount() - 1;
    int currentpuzzle = CurrentModel.getPuzzleIndex();
    int random = CurrentModel.getPuzzleIndex();
    while (random == currentpuzzle) {
      random = (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
    CurrentModel.setPuzzleIndex(random);
  }

  @Override
  public void clearBoard() {
    CurrentModel.clear();
  }

  @Override
  public int getPuzzleIndex() {
    return CurrentModel.getPuzzleIndex();
  }

  @Override
  public int getPuzzleCount() {
    return CurrentModel.getPuzzleCount();
  }
}
