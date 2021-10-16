package com.comp301.a09nonograms.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final List<Clues> Library;
  private final List<ModelObserver> Observers = new ArrayList<>();
  private Clues CurrentClue;
  private int CurrentClueIndex;
  private BoardImpl CurrentBoard;

  public ModelImpl(List<Clues> clues) {
    // Constructor code her//paramter validation
    Library = clues;
    CurrentClueIndex = 0;
    CurrentClue = Library.get(0);
    CurrentBoard = new BoardImpl(CurrentClue);
  }

  public Clues GetClues() {
    return CurrentClue;
  }

  @Override
  public int getPuzzleCount() {
    return Library.size();
  }

  @Override
  public int getPuzzleIndex() {
    return CurrentClueIndex;
  }

  @Override
  public void setPuzzleIndex(int index) {
    CurrentClueIndex = index;
    CurrentClue = Library.get(index);
    CurrentBoard = new BoardImpl(CurrentClue);
    Notify();
  }

  @Override
  public void addObserver(ModelObserver observer) {
    Observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    Observers.remove(observer);
  }

  @Override
  public boolean isSolved() {

    for (int i = 0; i < CurrentClue.getHeight(); i++) {
      if (!CheckRows(i)) {
        return false;
      }
    }

    for (int i = 0; i < CurrentClue.getWidth(); i++) {
      if (!CheckColums(i)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isShaded(int row, int col) {
    return CurrentBoard.isShaded(row, col);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    return CurrentBoard.isEliminated(row, col);
  }

  @Override
  public boolean isSpace(int row, int col) {
    return CurrentBoard.isSpace(row, col);
  }

  @Override
  public void toggleCellShaded(int row, int col) {
    CurrentBoard.toggleCellShaded(row, col);
    Notify();
  }

  @Override
  public void toggleCellEliminated(int row, int col) {
    CurrentBoard.toggleCellEliminated(row, col);
    Notify();
  }

  @Override
  public void clear() {
    CurrentBoard.clear();
    Notify();
  }

  @Override
  public int getWidth() {
    return CurrentClue.getWidth();
  }

  @Override
  public int getHeight() {
    return CurrentClue.getHeight();
  }

  @Override
  public int[] getRowClues(int index) {
    return CurrentClue.getRowClues(index);
  }

  @Override
  public int[] getColClues(int index) {
    return CurrentClue.getColClues(index);
  }

  @Override
  public int getRowCluesLength() {
    return CurrentClue.getRowCluesLength();
  }

  @Override
  public int getColCluesLength() {
    return CurrentClue.getColCluesLength();
  }

  public void Notify() {
    for (ModelObserver O : Observers) {
      O.update(this);
    }
  }

  public boolean CheckColums(int columnnumber) {

    int rownumber = 0;
    int[] Clues = CurrentClue.getColClues(columnnumber);
    int Actualshadedcells = 0;
    int CurrentShade = 0;

    // breaking down the clue into one int
    for (int i = 0; i < CurrentClue.getColCluesLength(); i++) {
      Actualshadedcells = Actualshadedcells + Clues[i];
    }

    // finding first shaded cell
    while (!CurrentBoard.isShaded(rownumber, columnnumber)) {
      rownumber++;
      if (Actualshadedcells == 0) {
        break;
      }

      if (rownumber == CurrentClue.getHeight()) {
        return false;
      }
    }

    // checking if space left is correct
    if (rownumber + Actualshadedcells > CurrentClue.getHeight()) {
      return false;
    }

    // checking amount shaded
    while (rownumber < CurrentClue.getHeight()) {
      if (CurrentBoard.isShaded(rownumber, columnnumber)) {
        CurrentShade++;
      }
      rownumber++;
    }

    // checking if amount shaded equal the amount that is supposed to be shaded
    return (CurrentShade == Actualshadedcells);
  }

  public boolean CheckRows(int Rownumber) {

    int ColNumber = 0;
    int[] Clues = CurrentClue.getRowClues(Rownumber);
    int Actualshadedcells = 0;
    int CurrentShade = 0;

    // breaking down the clue into one int
    for (int i = 0; i < CurrentClue.getRowCluesLength(); i++) {
      Actualshadedcells = Actualshadedcells + Clues[i];
    }

    // finding first shaded cell
    while (!CurrentBoard.isShaded(Rownumber, ColNumber)) {
      if (Actualshadedcells == 0) {
        break;
      }
      ColNumber++;
      if (ColNumber == CurrentClue.getWidth()) {
        return false;
      }
    }

    if (getRowClues(Rownumber)[0] == 1) {
      if (isShaded(Rownumber, ColNumber + 1)) {
        return false;
      }
    }

    // checking if space left is correct
    if (ColNumber + Actualshadedcells > CurrentClue.getWidth()) {
      return false;
    }

    // checking amount shaded
    while (ColNumber < CurrentClue.getWidth()) {
      if (CurrentBoard.isShaded(Rownumber, ColNumber)) {
        CurrentShade++;
      }
      ColNumber++;
    }

    // checking if amount shaded equal the amount that is supposed to be shaded
    return (CurrentShade == Actualshadedcells);
  }
}
