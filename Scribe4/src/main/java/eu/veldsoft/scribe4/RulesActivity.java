package eu.veldsoft.scribe4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class RulesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        for (View view : new View[]{this.findViewById(R.id.rules_mgv1),
                this.findViewById(R.id.rules_mgv2),
                this.findViewById(R.id.rules_mgv3),
                this.findViewById(R.id.rules_mgv4),}) {
            ((MiniGridView) view).setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        startActivity(new Intent(this, GlyphActivity.class));
        return false;
    }

}
