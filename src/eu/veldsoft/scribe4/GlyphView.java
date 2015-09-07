package eu.veldsoft.scribe4;

import java.util.Set;

import eu.veldsoft.scribe4.model.Glyphs;
import eu.veldsoft.scribe4.model.XY;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

public class GlyphView extends View {

  private Set<XY> glyph;

  private Paint paint = new Paint();

  public GlyphView(Context context, AttributeSet attrs) {
    super(context, attrs);
    paint.setStyle(Style.FILL);
  }

  private int centerX(int x) {
    return (2 * x + 1) * getWidth() / 6;
  }

  private int centerY(int x) {
    return (2 * x + 1) * getHeight() / 6;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    WindowManager windowManager = (WindowManager) this.getContext()
        .getSystemService(Context.WINDOW_SERVICE);
    int displayHeight = windowManager.getDefaultDisplay().getHeight();

    int dimension = displayHeight / Glyphs.ALL_GLYPHS.size();

    setMeasuredDimension(dimension, dimension);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    for (XY xy : XY.allXYs()) {
      if (glyph.contains(xy)) {
        paint.setColor(Color.WHITE);
      }
      else {
        paint.setColor(Color.DKGRAY);
      }
      canvas.drawCircle(centerX(xy.x), centerY(xy.y), getWidth() / 8, paint);
    }
  }

  public void setGlyph(Set<XY> glyph) {
    this.glyph = glyph;
    postInvalidate();
  }
}
