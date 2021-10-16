package com.comp301.a09nonograms.model;

// 0 = space 1 = shaded 2 = eliminated

public class BoardImpl implements Board {
  private final int[][] board;
  private final int height;
  private final int width;

  public BoardImpl(Clues c) {
    board = new int[c.getHeight()][c.getWidth()];
    height = c.getHeight();
    width = c.getWidth();

    for (int i = 0; i < c.getHeight(); i++) {
      for (int a = 0; a < c.getWidth(); a++) {
        board[i][a] = 0;
      }
    }
  }

  @Override
  public boolean isShaded(int row, int col) {

    return (board[row][col] == 1);
  }

  @Override
  public boolean isEliminated(int row, int col) {
    return (board[row][col] == 2);
  }

  @Override
  public boolean isSpace(int row, int col) {
    return (board[row][col] == 0);
  }

  @Override
  public void toggleCellShaded(int row, int col) {
    if (board[row][col] == 1) {
      board[row][col] = 0;
    } else {
      board[row][col] = 1;
    }
  }

  @Override
  public void toggleCellEliminated(int row, int col) {
    if (board[row][col] == 2) {
      board[row][col] = 0;
    } else {
      board[row][col] = 2;
    }
  }

  @Override
  public void clear() {
    for (int i = 0; i < height; i++) {
      for (int a = 0; a < width; a++) {
        board[i][a] = 0;
      }
    }
  }
}
