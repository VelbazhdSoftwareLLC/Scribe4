package eu.veldsoft.scribe4;

import eu.veldsoft.scribe4.model.ScribeMark;
import eu.veldsoft.scribe4.model.XY;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public class CellView extends View {

  private ScribeMark mark = ScribeMark.EMPTY;
  private int size = Constants.MiniGridViewSize.SMALL;
  private XY xy;
  private boolean lastMove;

  /**/{
    this.setPadding(2, 2, 2, 2);
  }

  public CellView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellView);

    CharSequence markString = a.getString(R.styleable.CellView_mark);
    if (markString == null || markString.equals("0"))
      setMark(ScribeMark.EMPTY);
    else if (markString.equals("1"))
      setMark(ScribeMark.RED);
    else if (markString.equals("2"))
      setMark(ScribeMark.BLUE);
    else if (markString.equals("3"))
      setMark(ScribeMark.GREEN);
    else if (markString.equals("4"))
      setMark(ScribeMark.PURPLE );
    else
      throw new RuntimeException("Illegal value for 'mark' attribute");
  }

  public CellView(Context context, int size) {
    super(context);
    this.size = size;
  }

  public void setMark(ScribeMark mark) {
    this.mark = mark;
    this.postInvalidate();
  }

  public void setLastMove(boolean lastMove) {
    if (this.lastMove == lastMove)
      return;

    this.lastMove = lastMove;
    this.postInvalidate();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    WindowManager windowManager = (WindowManager) this.getContext()
        .getSystemService(Context.WINDOW_SERVICE);
    int displayWidth = windowManager.getDefaultDisplay().getWidth();
    int displayHeight = windowManager.getDefaultDisplay().getHeight();

    int dimension;
    switch (size) {
    case Constants.MiniGridViewSize.LARGE:
      dimension = (Math.min(displayWidth, displayHeight) - 50) / 3;
      break;
    case Constants.MiniGridViewSize.SMALL:
    default:
      dimension = (Math.min(displayWidth, displayHeight) - 20) / 9;
    }
    setMeasuredDimension(dimension, dimension);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Paint paint = new Paint();
    paint.setColor(Settings.getColorForMark(this.mark, this.isEnabled()));
    canvas.drawRoundRect(
        new RectF(getPaddingLeft(), getPaddingTop(), this.getWidth()
            - getPaddingRight(), this.getHeight() - getPaddingBottom()), 10,
        10, paint);

    if (this.lastMove) {
      paint.setColor(Settings.getLastMoveColorForMark(this.mark,
          this.isEnabled()));
      canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2,
          this.getWidth() / 8, paint);
    }
  }

  public void setXY(XY xy) {
    this.xy = xy;
  }

  public XY getXY() {
    return xy;
  }

  public ScribeMark getMark() {
    return this.mark;
  }
}
