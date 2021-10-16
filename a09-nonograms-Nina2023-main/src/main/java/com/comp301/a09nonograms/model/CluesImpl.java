package com.comp301.a09nonograms.model;

public class CluesImpl implements Clues {
  private final int height;
  private final int width;
  private final int[][] rowC;
  private final int[][] columnC;

  public CluesImpl(int[][] rowClues, int[][] colClues) {
    rowC = rowClues;
    columnC = colClues;
    height = rowClues.length;
    width = colClues.length;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public int[] getRowClues(int index) {
    return rowC[index];
  }

  @Override
  public int[] getColClues(int index) {
    return columnC[index];
  }

  @Override
  public int getRowCluesLength() {

    return rowC[0].length;
  }

  @Override
  public int getColCluesLength() {

    return columnC[0].length;
  }
}
