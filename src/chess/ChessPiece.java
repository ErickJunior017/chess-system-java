package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece  extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    //Vai obter a posição da peça com
    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }

    //Vai verificar se o target é um aliado ou openente de acordo com a cor ou
    // também se tem alguma peça naquele local
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}
