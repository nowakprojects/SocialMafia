package pl.nowakprojects.socialmafia.ui.newgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mafiagameclasses.GameInitialSettings;
import pl.nowakprojects.socialmafia.mafiagameclasses.HumanPlayer;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRole;
import pl.nowakprojects.socialmafia.mafiagameclasses.TheGame;
import pl.nowakprojects.socialmafia.mafiagameclasses.roles.PlayerRolesManager;

public class ConnectPlayersToRolesActivity extends AppCompatActivity {

    static final String EXTRA_NEW_GAME = "pl.nowakprojects.socialmafia.mafiagameclasses.EXTRA_NEW_GAME";

    TheGame mNewGame;

    private ArrayList<HumanPlayer> mPlayersInfoList;
    private ArrayList<PlayerRole> mSelectedPlayersRoles;
    private ArrayList<String> mPlayersNamesList;
    private int mShowedRolesAmount = 0;
    PlayerShowingRoleAdapter mPlayerShowingRoleAdapter;


    @BindView(R.id.allPlayersRolesRecyclerView) RecyclerView mPlayersRolesRecyclerView;
    @BindView(R.id.startTheGameButton) Button mStartTheGameButton;
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_players_to_roles);
        ButterKnife.bind(this);

        vUiSetupUserInterface();

        vReceiveExtraObjects();

        vConnectPlayersToRoles();

        vUiSetupRecyclerView();


    }

    private void vCreateNewGame(){
        GameInitialSettings gameInitialSettings = new GameInitialSettings(
                0,
                mPlayersInfoList,
                180000,
                3,
                10,
                mPlayersInfoList.size(),
                iCountFractionRoles(mPlayersInfoList, PlayerRole.Fraction.MAFIA),
                iCountFractionRoles(mPlayersInfoList, PlayerRole.Fraction.TOWN),
                iCountFractionRoles(mPlayersInfoList, PlayerRole.Fraction.SYNDICATE),
                0
                );

        mNewGame = new TheGame(gameInitialSettings, getApplicationContext());
        //Te wszystkie ustawienia do GameConfiguration - taka klasa!!! - tutaj wszystko będzie final - immutable class
        /*mNewGame.setPlayersInfoList(mPlayersInfoList);
        mNewGame.setPlayersStartAmount(mPlayersInfoList.size());
        mNewGame.setMafiaStartAmount(iCountFractionRoles(mPlayersInfoList, PlayerRole.Fraction.MAFIA));
        mNewGame.setTownStartAmount(iCountFractionRoles(mPlayersInfoList, PlayerRole.Fraction.TOWN));
        mNewGame.setSyndicateStartAmount(iCountFractionRoles(mPlayersInfoList, PlayerRole.Fraction.SYNDICATE));*/
//        mNewGame.setMaxDailyTime(Long.valueOf(getResources().getString(R.string.sharedpref_daytime)));
    }

    private void vConnectPlayersToRoles(){
        mPlayersInfoList = createHumanPlayerWithRolesList(mSelectedPlayersRoles, mPlayersNamesList); //create new list of players (connected with Roles)
    }

    private ArrayList<HumanPlayer> createHumanPlayerWithRolesList(ArrayList<PlayerRole> selectedPlayerRolesList, ArrayList<String> playersNamesList) {
        ArrayList<HumanPlayer> playersInfoList = new ArrayList<>();
        Collections.shuffle(selectedPlayerRolesList); // pomieszanie roli, żeby nie były według wyboru
        // przydzielenie pomieszanych ról do graczy:
        //tworzymy nowe obiekty z imienia i roli gracza
        for (int i = 0; i < selectedPlayerRolesList.size(); i++) {
            playersInfoList.add(new HumanPlayer(playersNamesList.get(i), selectedPlayerRolesList.get(i)));
        }
        return playersInfoList;
    }

    private int iCountFractionRoles(ArrayList<HumanPlayer> playersInfoList, PlayerRole.Fraction fraction){
        int i_fractionRolesQuantity = 0;
        for(HumanPlayer hp: playersInfoList){
            if(hp.getPlayerRole().getFraction()== fraction)
                i_fractionRolesQuantity++;
        }
        return i_fractionRolesQuantity;
    }

    private void vReceiveExtraObjects(){
        mSelectedPlayersRoles = Parcels.unwrap(getIntent().getParcelableExtra(SelectPlayerRolesActivity.EXTRA_SELECTED_ROLES_LIST));
        PlayerRolesManager.setContextForRoles(mSelectedPlayersRoles,this);
        mPlayersNamesList = getIntent().getStringArrayListExtra(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST);
    }


    //Setup User Interface Methods:-----------------------------------------------------------------
    private void vUiSetupUserInterface(){
        vUiSetupToolbar();
        vUiSetupButtonListener();
    }

    private void vUiSetupToolbar(){
        setSupportActionBar(mToolbar);
    }

    private void vUiSetupButtonListener(){
        mStartTheGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShowedRolesAmount < mPlayersInfoList.size())
                    Toast.makeText(getApplicationContext(), R.string.tooLessPlayersRolesShowed, Toast.LENGTH_LONG).show();
                else {
                    vUiGoToTheGameActionActivity();
                }
            }
        });
    }

    private void vUiSetupRecyclerView(){
        mPlayerShowingRoleAdapter = new PlayerShowingRoleAdapter(mPlayersInfoList, this);
        mPlayersRolesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPlayersRolesRecyclerView.setAdapter(mPlayerShowingRoleAdapter);
    }

    private void vUiGoToTheGameActionActivity(){
        vCreateNewGame();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_NEW_GAME, Parcels.wrap(mNewGame));
        Intent intent = new Intent(getApplicationContext(), TheGameActionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public class PlayerShowingRoleAdapter extends RecyclerView.Adapter<PlayerShowingRoleAdapter.HumanPlayerViewHolder> {

        private ArrayList<HumanPlayer> humanPlayersList;
        private LayoutInflater inflater;
        private Context context;

        public PlayerShowingRoleAdapter(ArrayList<HumanPlayer> humanPlayersList, Context context) {
            this.humanPlayersList = humanPlayersList;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public HumanPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.player_role_showing_layout, parent, false);
            return new HumanPlayerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HumanPlayerViewHolder holder, int position) {
            HumanPlayer humanPlayer = humanPlayersList.get(position);
            holder.playerName.setText(humanPlayer.getPlayerName());
            holder.roleName.setText(getString(R.string.questionMarks));
            holder.showRoleButton.setText(getString(R.string.show_role));
            holder.playerRoleIcon.setImageResource(R.drawable.image_template);
            holder.wasRoleShowed.setChecked(humanPlayersList.get(position).isWasRoleShowed());
            holder.isRoleShowed = false;
        }

        @Override
        public int getItemCount() {
            return humanPlayersList.size();
        }

        class HumanPlayerViewHolder extends RecyclerView.ViewHolder {

            private ImageView playerRoleIcon;
            private TextView playerName;
            private TextView roleName;
            private Button showRoleButton;
            private CheckBox wasRoleShowed;

            private boolean isRoleShowed = false;

            public HumanPlayerViewHolder(View itemView) {
                super(itemView);

                playerRoleIcon = (ImageView) itemView.findViewById(R.id.playerStatusIcon);
                playerName = (TextView) itemView.findViewById(R.id.playerName);
                roleName = (TextView) itemView.findViewById(R.id.roleName);
                showRoleButton = (Button) itemView.findViewById(R.id.show_hide_button);
                wasRoleShowed = (CheckBox) itemView.findViewById(R.id.wasRoleShowedCheckBox);

                showRoleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (humanPlayersList.get(getAdapterPosition()).isWasRoleShowed() == false)
                            mShowedRolesAmount++;

                        humanPlayersList.get(getAdapterPosition()).setWasRoleShowed(true);
                        wasRoleShowed.setChecked(humanPlayersList.get(getAdapterPosition()).isWasRoleShowed());
                        if (isRoleShowed) {
                            isRoleShowed = false;
                            showRoleButton.setText(getString(R.string.show_role));
                            roleName.setText(getString(R.string.questionMarks));
                            playerRoleIcon.setImageResource(R.drawable.image_template);
                        } else {
                            isRoleShowed = true;
                            showRoleButton.setText(getString(R.string.hide_role));
                            roleName.setText(getString(humanPlayersList.get(getAdapterPosition()).getRoleName()));
                            playerRoleIcon.setImageResource(humanPlayersList.get(getAdapterPosition()).getPlayerRole().getIconResourceID());
                        }
                    }
                });

                /**
                 * Przy naciśnięciu karty roli pojawią się jej opis
                 */
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isRoleShowed) {
                            humanPlayersList.get(getAdapterPosition()).getPlayerRole().showRoleDescriptionDialog();
                        }
                    }
                });

            }

        }
    }

}
