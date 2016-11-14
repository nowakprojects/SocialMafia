package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import pl.nowakprojects.socialmafia.R;

public class GameFinishedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //niemozliwy powrót do zakończonej gry!
    }

}