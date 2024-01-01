package eu.veldsoft.scribe4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import eu.veldsoft.scribe4.ai.LeesAIPlayer;
import eu.veldsoft.scribe4.model.MiniGrid;
import eu.veldsoft.scribe4.model.ScribeBoard;
import eu.veldsoft.scribe4.model.ScribeListener;
import eu.veldsoft.scribe4.model.ScribeMark;

public class Main extends Activity implements View.OnClickListener,
        ScribeListener, OnSharedPreferenceChangeListener {

    private ScribeBoard scribeBoard;
    private ScribeMark winner;

    private MiniGrid lastClickedMiniGrid;
    private ScribeBoardView scribeBoardView;

    private boolean aiMode = false;
    private AIPlayer aiPlayer1 = new LeesAIPlayer();
    private AIPlayer aiPlayer2 = new LeesAIPlayer();
    private AIPlayer aiPlayer3 = new LeesAIPlayer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScribeMark.EMPTY.setName(getString(R.string.empty));
        ScribeMark.BLUE.setName(getString(R.string.blue));
        ScribeMark.RED.setName(getString(R.string.red));
        ScribeMark.GREEN.setName(getString(R.string.green));
        ScribeMark.PURPLE.setName(getString(R.string.purple));

        setContentView(R.layout.main);
        scribeBoardView = (ScribeBoardView) findViewById(R.id.scribeBoard);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        updateGameMode();

        showDialog(Constants.DialogId.NEW_GAME);
    }

    void startNewGame(boolean aiMode) {
        this.aiMode = aiMode;
        winner = null;
        scribeBoard = new ScribeBoard();
        scribeBoardView.setScribeBoard(scribeBoard);
        scribeBoard.addListener(this);
        if (getResources().getBoolean(R.bool.log_all_moves)) {
            Log.i("Logging all moves");
            MoveLogger.getInstance().setScribeBoard(scribeBoard);
        }
        updatePlayerViews(scribeBoard.whoseTurn());
        if (aiMode == true) {
            aiPlayer1.restart(scribeBoard, ScribeMark.BLUE);
            aiPlayer2.restart(scribeBoard, ScribeMark.GREEN);
            aiPlayer3.restart(scribeBoard, ScribeMark.PURPLE);
        }
    }

    private void updatePlayerViews(ScribeMark currentPlayer) {
        ((CellView) findViewById(R.id.player_cell)).setMark(currentPlayer);
        ((TextView) findViewById(R.id.player_text))
                .setText(R.string.its_your_turn);
        findViewById(R.id.player_cell).setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menuitem_glyphs) {
            startActivity(new Intent(this, GlyphActivity.class));
        }

        if (item.getItemId() == R.id.menuitem_rules) {
            startActivity(new Intent(this, RulesActivity.class));
        }

        if (item.getItemId() == R.id.menuitem_new_game) {
            showDialog(Constants.DialogId.NEW_GAME);
        }

        if (item.getItemId() == R.id.menuitem_settings) {
            startActivity(new Intent(this, ScribePreferences.class));
        }

        if (item.getItemId() == R.id.menuitem_about) {
            showDialog(Constants.DialogId.ABOUT);
        }

        if (item.getItemId() == R.id.menuitem_market) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + this.getPackageName())));
        }

        return true;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        prepareDialog(id, dialog);
    }

    private void prepareDialog(int id, Dialog dialog) {
        switch (id) {
            case Constants.DialogId.MINIGRID:
                if (this.lastClickedMiniGrid != null
                        && this.scribeBoard.whoseTurn() != null) {
                    MiniGridDialog miniGridDialog = (MiniGridDialog) dialog;
                    miniGridDialog.setValues(this.lastClickedMiniGrid,
                            this.scribeBoard.whoseTurn());
                }
                break;
            case Constants.DialogId.WINNER:
                ((AlertDialog) dialog).setMessage(winner.scribeMarkName() + " "
                        + this.getString(R.string.wins));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        super.onCreateDialog(id);
        switch (id) {
            case Constants.DialogId.ABOUT:
                return createAboutDialog();
            case Constants.DialogId.MINIGRID:
                MiniGridDialog miniGridDialog = new MiniGridDialog(this);
                prepareDialog(id, miniGridDialog);
                return miniGridDialog;
            case Constants.DialogId.NEW_GAME:
                return new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_confirm_new_game)
                        .setPositiveButton(R.string.vs_ai,
                                newGameDialogClickListener)
                        .setNegativeButton(R.string.vs_friend,
                                newGameDialogClickListener).create();
            case Constants.DialogId.WINNER:
                Dialog winnerDialog = new AlertDialog.Builder(this)
                        .setPositiveButton(android.R.string.yes,
                                winnerDialogClickListener)
                        .setNegativeButton(android.R.string.no,
                                winnerDialogClickListener).create();
                this.prepareDialog(id, winnerDialog);
                return winnerDialog;
            case Constants.DialogId.EXIT:
                return new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_confirm_exit)
                        .setPositiveButton(R.string.exit_scribe,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        finish();
                                    }
                                })
                        .setNegativeButton(android.R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                }).create();
            default:
                return null;
        }
    }

    private Dialog createAboutDialog() {
        AlertDialog aboutDialog = new AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.ok, null).create();
        String version = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                    this.getPackageName(), 0);
            version = TextUtils.htmlEncode(packageInfo.versionName);
        } catch (NameNotFoundException e) {
            Log.w("Unable to get app version:");
            Log.w("  " + e.toString());
        }
        TextView aboutView = (TextView) getLayoutInflater().inflate(
                R.layout.about, null);
        String appName = TextUtils
                .htmlEncode(this.getString(R.string.app_name));
        String text = this.getString(R.string.about_dialog_text, appName,
                version);
        CharSequence styledText = Html.fromHtml(text);
        aboutView.setText(styledText);
        aboutView.setMovementMethod(LinkMovementMethod.getInstance());
        aboutDialog.setView(aboutView);
        return aboutDialog;
    }

    /**
     * OnClickListener for the "New Game" dialog.
     */
    private DialogInterface.OnClickListener newGameDialogClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    /*
                     * AI opponent
                     */
                    startNewGame(true);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    /*
                     * human opponent
                     */
                    startNewGame(false);
                    break;
                default:
                    dialog.cancel();
            }
        }
    };

    private DialogInterface.OnClickListener winnerDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    showDialog(Constants.DialogId.NEW_GAME);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    finish();
                    break;
                default:
                    dialog.cancel();
            }
        }
    };

    @Override
    public void onBackPressed() {
        showDialog(Constants.DialogId.EXIT);
    }

    @Override
    public void scribeBoardWon(ScribeBoard scribeBoard, ScribeMark winner) {
        if (this.scribeBoard == scribeBoard) {
            this.winner = winner;
            showDialog(Constants.DialogId.WINNER);
        }
    }

    @Override
    public void whoseTurnChanged(ScribeBoard scribeBoard,
                                 ScribeMark currentPlayer) {
        if (this.scribeBoard != scribeBoard) {
            return;
        }

        updatePlayerViews(currentPlayer);

        if (aiMode == false) {
            return;
        }

        if (currentPlayer == ScribeMark.BLUE) {
            scribeBoardView.setEnabled(false);
            aiPlayer1.move();
        }
        if (currentPlayer == ScribeMark.GREEN) {
            scribeBoardView.setEnabled(false);
            aiPlayer2.move();
        }
        if (currentPlayer == ScribeMark.PURPLE) {
            scribeBoardView.setEnabled(false);
            aiPlayer3.move();
        }
    }

    /**
     * This activity serves as the OnClickListener for each of the MiniGridViews
     * it contains. When one of them is clicked, it creates a dialog which shows
     * a view of the same MiniGrid.
     */
    @Override
    public void onClick(View v) {
        if (!(v instanceof MiniGridView))
            return;
        if (!v.isEnabled())
            return;
        if (aiMode && scribeBoard.whoseTurn() != ScribeMark.RED)
            return;

        this.lastClickedMiniGrid = ((MiniGridView) v).getMiniGrid();
        showDialog(Constants.DialogId.MINIGRID);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (sharedPreferences == PreferenceManager
                .getDefaultSharedPreferences(this)) {
            if (key.equals(this.getString(R.string.gameMode))) {
                updateGameMode();
            }
        }
    }

    private void updateGameMode() {
        String mode = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(this.getString(R.string.gameMode), "majority");
        int index = mode.equals("majority") ? 0 : 1;
        String[] gameModeEntries = this.getResources().getStringArray(
                R.array.gameModeEntries);

        eu.veldsoft.scribe4.model.Settings.setGameMode(mode);
        String text = "Game mode: " + gameModeEntries[index];
        ((TextView) findViewById(R.id.gameMode)).setText(text);
    }
}
