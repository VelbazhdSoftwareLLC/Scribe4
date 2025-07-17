package eu.veldsoft.scribe4;

import android.os.AsyncTask;

import eu.veldsoft.scribe4.model.GridPosition;
import eu.veldsoft.scribe4.model.MiniGrid;
import eu.veldsoft.scribe4.model.ScribeBoard;
import eu.veldsoft.scribe4.model.ScribeException;
import eu.veldsoft.scribe4.model.ScribeMark;
import eu.veldsoft.scribe4.model.XY;

/**
 * The AI that the human player is up against. The AI player is always blue. To
 * create an AI opponent, subclass this class and implement itsYourTurn().
 */
public abstract class AIPlayer {

    protected ScribeBoard board;
    protected ScribeMark mark;

    /**
     * Called to inform the AI player that it is its turn.
     */
    protected abstract GridPosition itsYourTurn();

    /**
     * Called when a new game is starting.
     */
    protected void restart(ScribeBoard board, ScribeMark player) {
        this.board = board;
        this.mark = player;
    }

    private void move(MiniGrid miniGrid, XY xy) {
        if (this.board.whoseTurn() == this.mark) {
            miniGrid.set(xy, this.mark);
        } else {
            throw new ScribeException(
                    "The AI player cannot move now because it is not its turn.");
        }
    }

    void move() {
        this.getTask().execute();
    }

    private final AsyncTask<Void, Void, Void> getTask() {
        return new AsyncTask<Void, Void, Void>() {
            GridPosition theMove = null;

            @Override
            protected Void doInBackground(Void... params) {
                this.theMove = AIPlayer.this.itsYourTurn();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                AIPlayer.this.move(this.theMove.miniGrid, this.theMove.xy);
            }
        };
    }

}