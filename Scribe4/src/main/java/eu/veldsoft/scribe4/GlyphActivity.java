package eu.veldsoft.scribe4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import eu.veldsoft.scribe4.model.Glyphs;
import eu.veldsoft.scribe4.model.XY;

public class GlyphActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.glyph_activity);
        GridView grid = (GridView) findViewById(R.id.glyph_grid);
        findViewById(R.id.button_back_to_game).setOnClickListener(this);

        List<Map<String, Object>> glyphsList = new ArrayList<Map<String, Object>>();
        for (Entry<String, Set<XY>> e : Glyphs.ALL_GLYPHS.entrySet()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", e.getKey());
            map.put("glyph", e.getValue());
            glyphsList.add(map);
        }

        SimpleAdapter a = new SimpleAdapter(this, glyphsList,
                R.layout.glyph_item, new String[]{"name", "glyph"},
                new int[]{R.id.glyph_name, R.id.glyph});
        SimpleAdapter.ViewBinder viewBinder = new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof GlyphView && data instanceof Set<?>) {
                    ((GlyphView) view).setGlyph((Set<XY>) data);
                    return true;
                }
                return false;
            }
        };
        a.setViewBinder(viewBinder);
        grid.setAdapter(a);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
