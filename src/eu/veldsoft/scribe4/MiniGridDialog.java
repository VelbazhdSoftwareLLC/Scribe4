package eu.veldsoft.scribe4;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import eu.veldsoft.scribe4.model.DefaultMiniGridListener;
import eu.veldsoft.scribe4.model.MiniGrid;
import eu.veldsoft.scribe4.model.ScribeMark;
import eu.veldsoft.scribe4.model.XY;

public class MiniGridDialog extends Dialog {

	private MiniGrid miniGrid;

	public MiniGridDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle args) {
		super.onCreate(args);
		this.setCanceledOnTouchOutside(true);
	}

	public void setValues(MiniGrid miniGrid, ScribeMark whoseTurn) {
		this.miniGrid = miniGrid;
		this.setTitle(whoseTurn.scribeMarkName() + ", "
				+ this.getContext().getString(R.string.make_move));
		this.setup();
	}

	private void setup() {
		MiniGridView mgv = new MiniGridView(this.getContext(),
				Constants.MiniGridViewSize.LARGE);
		mgv.setMiniGrid(this.miniGrid);
		this.setContentView(mgv);
		this.miniGrid.addMiniGridListener(new DefaultMiniGridListener() {
			@Override
			public void miniGridMarked(MiniGrid miniGrid, XY xy, ScribeMark mark) {
				MiniGridDialog.this.dismiss();
			}
		});
	}
}
