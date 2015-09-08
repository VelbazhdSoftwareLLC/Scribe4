package eu.veldsoft.scribe4;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView that displays its text upside down. From
 * http://stackoverflow.com/questions
 * /2558257/how-can-you-display-upside-down-text-with-a-textview-in-android
 */
public class UpsideDownTextView extends TextView {

	public UpsideDownTextView(Context context) {
		super(context);
	}

	public UpsideDownTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.save();

		float py = this.getHeight() / 2.0f;
		float px = this.getWidth() / 2.0f;
		canvas.rotate(180, px, py);

		super.onDraw(canvas);

		canvas.restore();
	}

}
