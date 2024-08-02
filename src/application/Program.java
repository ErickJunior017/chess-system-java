package application;

import boardgame.Board;
import boardgame.BoardException;
import chess.ChessMatch;

public class Program {
    public static void main(String[] args) {
        try {
            ChessMatch chessMatch = new ChessMatch();
            UI.printBoard(chessMatch.getPieces());
        } catch(BoardException e){
            System.out.println(e.getMessage());
        }
    }
}
