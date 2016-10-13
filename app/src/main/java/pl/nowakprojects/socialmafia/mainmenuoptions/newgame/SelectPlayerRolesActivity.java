package pl.nowakprojects.socialmafia.mainmenuoptions.newgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;

import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.PlayerRole;
import pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.RolesDataObjects;

/**
 * Wybór ról jakie znajdą się w aktualnej grze
 */
public class SelectPlayerRolesActivity extends AppCompatActivity implements PlayerChoosingRoleAdapter.RoleAmountChangedCallback {

    static final String EXTRA_SELECTED_ROLES_LIST = "pl.nowakprojects.socialmafia.mainmenuoptions.newgame.mafiagameclasses.EXTRA_SELECTED_ROLES_LIST";
    static final String LOG_TAG = "SelectPlayersRolesActivity.class";

    private RecyclerView townRolesList;
    private RecyclerView mafiaRolesList;
    private RecyclerView syndicateRolesList;

    private PlayerChoosingRoleAdapter townRolesAdapter;
    private PlayerChoosingRoleAdapter mafiaRolesAdapter;
    private PlayerChoosingRoleAdapter syndicateRolesAdapter;

    private ArrayList<PlayerRole> townRoles;
    private ArrayList<PlayerRole> mafiaRoles;
    private ArrayList<PlayerRole> syndicateRoles;


    private ArrayList<PlayerRole> allSelectedRoles; //selected roles from all fractions
    private ArrayList<String> playersNamesList; //lista imion graczy

    TextView howManyFunctionsSelected;
    TextView townSelectedRolesAmount;
    TextView mafiaSelectedRolesAmount;
    TextView syndicateSelectedRolesAmount;

    private int howManyTownRolesWasSelected = 0;
    private int howManyMafiaRolesWasSelected = 0;
    private int howManySyndicateRolesWasSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player_roles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Stworzenie recyclerView dla kazdej z frakcji ---------------------------------------------------------------------------
        townRoles = RolesDataObjects.getTownRolesList();
        townRolesList = (RecyclerView) findViewById(R.id.selectTownRolesRecyclerView);
        townRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        townRolesAdapter = new PlayerChoosingRoleAdapter(townRoles,this);
        townRolesAdapter.setRoleAmountChangedCallback(this);
        townRolesList.setAdapter(townRolesAdapter);

        mafiaRoles = RolesDataObjects.getMafiaRolesList();
        mafiaRolesList = (RecyclerView) findViewById(R.id.selectMafiaRolesRecyclerView);
        mafiaRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mafiaRolesAdapter = new PlayerChoosingRoleAdapter(mafiaRoles,this);
        mafiaRolesAdapter.setRoleAmountChangedCallback(this);
        mafiaRolesList.setAdapter(mafiaRolesAdapter);

        syndicateRoles = RolesDataObjects.getSyndicateRolesList();
        syndicateRolesList = (RecyclerView) findViewById(R.id.selectSyndicateRolesRecyclerView);
        syndicateRolesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        syndicateRolesAdapter = new PlayerChoosingRoleAdapter(syndicateRoles,this);
        syndicateRolesAdapter.setRoleAmountChangedCallback(this);
        syndicateRolesList.setAdapter(syndicateRolesAdapter);
        //--------------------------------------------------------------------------------------------------------------------------
        //Oderanie listy imion graczy:
        playersNamesList = getIntent().getStringArrayListExtra(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST);
        TextView pickedPlayersAmount = (TextView) findViewById(R.id.pickedPlayersAmount);
        pickedPlayersAmount.setText(String.valueOf(playersNamesList.size()));
        townSelectedRolesAmount = (TextView) findViewById(R.id.townSelectedRolesAmount);
        mafiaSelectedRolesAmount = (TextView) findViewById(R.id.mafiaSelectedRolesAmount);
        syndicateSelectedRolesAmount = (TextView) findViewById(R.id.syndicateSelectedRolesAmount);

        howManyFunctionsSelected = (TextView) findViewById(R.id.howMuchRolesWasSelected);

        Button assignRolesToPlayers = (Button) findViewById(R.id.assignRolesToPlayers);
        assignRolesToPlayers.setOnClickListener(new View.OnClickListener() {

            private boolean isThereOnlyOneFraction(){
                if(howManyMafiaRolesWasSelected>0&&howManySyndicateRolesWasSelected==0&&howManyTownRolesWasSelected==0)
                    return true;
                else if (howManyTownRolesWasSelected>0&&howManySyndicateRolesWasSelected==0&&howManyMafiaRolesWasSelected==0)
                    return true;
                else if(howManyMafiaRolesWasSelected>0&&howManySyndicateRolesWasSelected==0&&howManyTownRolesWasSelected==0)
                    return true;
                else
                    return false;
            }//private boolean isThereOnlyOneFraction()

            /**
             * DOKONCZYC!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             * @return
             */
            private boolean isFractionProportionCorrect(){
                return true;
            }

            /**
             *
             * @return
             */
            private boolean checkIfPlayersAmountIsCorrect(){
                if ((howManyTownRolesWasSelected+howManyMafiaRolesWasSelected+howManySyndicateRolesWasSelected)<playersNamesList.size()){
                    Toast.makeText(getApplicationContext(),R.string.tooLessFuctionsSelected, Toast.LENGTH_SHORT).show();
                    return false;
                } else if ((howManyTownRolesWasSelected+howManyMafiaRolesWasSelected+howManySyndicateRolesWasSelected)>playersNamesList.size()){
                    Toast.makeText(getApplicationContext(),R.string.tooMuchFuctionsSelected, Toast.LENGTH_SHORT).show();
                    return false;
                } else if (isThereOnlyOneFraction()) {
                    Toast.makeText(getApplicationContext(),R.string.onlyOneFractionSelected, Toast.LENGTH_SHORT).show();
                    return false;
                }else
                    return true;
            }// private boolean checkIfPlayersAmountIsCorrect()

            @Override
            public void onClick(View view) {
                //dodac ostrzezenia jak np. mafii jest za duzo i alertbox o zaakcpetowanie!!!

                if (checkIfPlayersAmountIsCorrect()) {
                    //przejscie do losowania ról:
                    connectSelectedRolesFromAllFractions();
                    //Tworzymy Bundle do przekazania do Activity mieszania ról
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(EXTRA_SELECTED_ROLES_LIST, Parcels.wrap(allSelectedRoles)); //wszystkie wybrane role przekazujemy
                    bundle.putStringArrayList(TapPlayersNamesActivity.EXTRA_PLAYERS_NAMES_LIST,playersNamesList); //wszystkie imiona graczy
                    Intent intent = new Intent(getApplicationContext(),ConnectPlayersToRolesActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }// if (checkIfPlayersAmountIsCorrect())

            }// public void onClick(View view)
        });

    }// protected void onCreate(Bundle savedInstanceState)

    @Override
    public void amountDecrease(PlayerRole.Fraction fraction) {

        if(fraction == PlayerRole.Fraction.TOWN)
            howManyTownRolesWasSelected--;
        else if(fraction == PlayerRole.Fraction.MAFIA)
            howManyMafiaRolesWasSelected--;
        else if(fraction == PlayerRole.Fraction.SYNDICATE)
            howManySyndicateRolesWasSelected--;

        updateFractionAmountTextViews();
    }//public void amountDecrease(PlayerRole.Fraction fraction)

    @Override
    public void amountIncrease(PlayerRole.Fraction fraction) {
        if(fraction == PlayerRole.Fraction.TOWN)
            howManyTownRolesWasSelected++;
        else if(fraction == PlayerRole.Fraction.MAFIA)
            howManyMafiaRolesWasSelected++;
        else if(fraction == PlayerRole.Fraction.SYNDICATE)
            howManySyndicateRolesWasSelected++;

        updateFractionAmountTextViews();
    }//public void amountIncrease(PlayerRole.Fraction fraction)

    private void updateFractionAmountTextViews(){
        townSelectedRolesAmount.setText(String.valueOf(howManyTownRolesWasSelected));
        mafiaSelectedRolesAmount.setText(String.valueOf(howManyMafiaRolesWasSelected));
        syndicateSelectedRolesAmount.setText(String.valueOf(howManySyndicateRolesWasSelected));
        howManyFunctionsSelected.setText(String.valueOf(howManyTownRolesWasSelected+howManyMafiaRolesWasSelected+howManySyndicateRolesWasSelected));
    }//private void updateFractionAmountTextViews()

    /**
     * Zapisuje wszystkie wybrane role do jednej listy allSelectedRoles
     */
    private void connectSelectedRolesFromAllFractions(){
        allSelectedRoles = new ArrayList<>();
        for(int i=0;i<townRolesAdapter.getSelectedRolesList().size();i++)
            allSelectedRoles.add(townRolesAdapter.getSelectedRolesList().get(i));
        for(int i=0;i<mafiaRolesAdapter.getSelectedRolesList().size();i++)
            allSelectedRoles.add(mafiaRolesAdapter.getSelectedRolesList().get(i));
        for(int i=0;i<syndicateRolesAdapter.getSelectedRolesList().size();i++)
            allSelectedRoles.add(syndicateRolesAdapter.getSelectedRolesList().get(i));
    }//private void connectSelectedRolesFromAllFractions()

}// SelectPlayerRolesActivity