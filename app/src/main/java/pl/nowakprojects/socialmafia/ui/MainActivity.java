package pl.nowakprojects.socialmafia.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.ui.gamerules.GameRulesActivity;
import pl.nowakprojects.socialmafia.ui.loadgame.LoadGameActivity;
import pl.nowakprojects.socialmafia.ui.newgame.PickPlayersAmountActivity;
import pl.nowakprojects.socialmafia.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    //static boolean IS_PREMIUM_VESRION = false; //dodac to gdzies tam do gradle, jak na Udacity
    static boolean ALL_TIPS_COLLAPSED = false; //do preferencji czy wszystkie podpowiedzi maja byc zwiniecte, rozwiniete, ustawienia zalecane

    @BindView(R.id.buttonStartNewGame) ImageButton buttonStartNewGame;
    @BindView(R.id.buttonLoadGame) ImageButton buttonLoadGame;
    @BindView(R.id.buttonGameRules) ImageButton buttonGameRules;
    @BindView(R.id.buttonAppPreferences) ImageButton buttonAppPreferences;
    @BindView(R.id.facebookFanPageLayout) View facebookLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vUiSetupUserInterface();
    }


    private void vUiSetupUserInterface(){
        vUiSetupButtonListener();
    }

    private void vUiSetupButtonListener(){
        buttonStartNewGame.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PickPlayersAmountActivity.class);
            startActivity(intent);
        });

        buttonGameRules.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameRulesActivity.class);
            startActivity(intent);
        });

        buttonLoadGame.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoadGameActivity.class);
            startActivity(intent);
        });

        buttonAppPreferences.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        //w opcjach czas dnia, dlugosc budzenia
        facebookLayout.setOnClickListener(view -> {
            Uri url = Uri.parse("https://www.facebook.com/Social-Mafia-1764817623732317/");
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            startActivity(intent);
        });
    }
}
