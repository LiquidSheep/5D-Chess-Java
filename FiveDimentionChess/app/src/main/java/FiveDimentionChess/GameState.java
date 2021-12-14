package FiveDimentionChess;

public class GameState {

    private FiveBoard currentFiveBoard;

    public GameState() {
        /*
        String sampleNotationPath 
            = "E:/Program Folder/5DChess/Java/FiveDimentionChess/app/src/main/resources/FiveDimentionChess/sampleNotation.txt";
        String sampleNotation = FileIO.getTxtFile(sampleNotationPath);
        NotationParser.fullParse(sampleNotation);
        */

        currentFiveBoard = new FiveBoard();
    }

    public FiveBoard getFiveBoard() {
        return currentFiveBoard;
    }
}
