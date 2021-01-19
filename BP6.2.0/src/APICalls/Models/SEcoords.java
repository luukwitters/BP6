package APICalls.Models;

public class SEcoords {

    private int StartRow;
    private int StartColumn;
    private int EndRow;
    private int EndColumn;

    public SEcoords(int startRow, int startColumn, int endRow, int endColumn) {
        StartRow = startRow;
        StartColumn = startColumn;
        EndRow = endRow;
        EndColumn = endColumn;
    }

    public int getStartRow() {
        return StartRow;
    }

    public void setStartRow(int startRow) {
        StartRow = startRow;
    }

    public int getStartColumn() {
        return StartColumn;
    }

    public void setStartColumn(int startColumn) {
        StartColumn = startColumn;
    }

    public int getEndRow() {
        return EndRow;
    }

    public void setEndRow(int endRow) {
        EndRow = endRow;
    }

    public int getEndColumn() {
        return EndColumn;
    }

    public void setEndColumn(int endColumn) {
        EndColumn = endColumn;
    }

    @Override
    public String toString() {
        return "ModelValues{" +
                "StartRow=" + StartRow +
                ", StartColumn=" + StartColumn +
                ", EndRow=" + EndRow +
                ", EndColumn=" + EndColumn +
                '}';
    }
}
