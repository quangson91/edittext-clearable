package vnzit.com.edittextclearable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ClearableEditText clearableEditText = (ClearableEditText) findViewById(R.id.clearableEditText);
        clearableEditText.setCallback(new ClearableEditText.Callback() {
            @Override
            public void beforeClear(EditText editText) {
                Log.i(TAG, "Before clear: " + editText.getText());
            }

            @Override
            public void afterClear(EditText editText) {
                Log.i(TAG, "After clear: " + editText.getText());
            }
        });
    }
}
