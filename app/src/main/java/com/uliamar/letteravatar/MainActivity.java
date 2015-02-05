package com.uliamar.letteravatar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.uliamar.letteravatargenerator.LetterAvatar;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "TAG";
    private EditText name_et;
    private ImageView render_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        render_iv = (ImageView) findViewById(R.id.render);

//        i.setImageBitmap(AvatarGen.createAvatar(this, name));
        name_et = (EditText) findViewById(R.id.name_et);
        name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    render_iv.setImageBitmap(LetterAvatar.createAvatar(MainActivity.this, s.toString()));
                }
            }
        });
        render_iv.setImageBitmap(LetterAvatar.createAvatar(MainActivity.this, name_et.getText().toString()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
