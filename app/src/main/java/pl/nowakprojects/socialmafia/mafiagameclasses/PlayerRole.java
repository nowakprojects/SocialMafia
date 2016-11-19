package pl.nowakprojects.socialmafia.mafiagameclasses;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.parceler.Parcel;
import org.parceler.Transient;

import pl.nowakprojects.socialmafia.MainActivity;
import pl.nowakprojects.socialmafia.R;
import pl.nowakprojects.socialmafia.SocialMafia;

/**
 * Created by Mateusz on 20.06.2016.
 */
@Parcel
public class PlayerRole {

    public enum Fraction {TOWN, MAFIA, SYNDICATE, NEUTRAL, GROUP, NOFRACTION};
    public enum ActionType {OnlyZeroNight, AllNights, AllNightsBesideZero, ActionRequire, OnceAGame, NoAction, MafiaAction, OnlyZeroNightAndActionRequire, Double };

    int name = 0;
    int description = 0;
    int iconResourceID = 0;
    Fraction fraction;
    ActionType actionType;
    int nightWakeHierarchyNumber;
    int rolePlayersAmount=0;
    boolean onlyPremium=false;

    @Transient
    MaterialDialog roleDescriptionDialog;

    //PlayerActionViewHolder
    //boolean playerTurn = false;
    //HumanPlayer lastChoseenPlayer;
    //boolean roleActionMade = false;

    public PlayerRole(int name, int description, int iconResourceID, Fraction fraction, ActionType actionType, int nightWakeHierarchyNumber) {
        this.name = name;
        this.description = description;
        this.iconResourceID = iconResourceID;
        this.fraction = fraction;
        this.actionType = actionType;
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;
    }

    public PlayerRole(int name, int description, int iconResourceID, Fraction fraction, ActionType actionType, int nightWakeHierarchyNumber, boolean onlyPremium) {
        this.name = name;
        this.description = description;
        this.iconResourceID = iconResourceID;
        this.fraction = fraction;
        this.actionType = actionType;
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;

        this.onlyPremium=onlyPremium;
    }


    // empty constructor needed by the Parceler library
    public PlayerRole(){

    }

    public boolean isMafiaRole(){
        return fraction==Fraction.MAFIA;
    }

    public boolean isTownRole(){
        return fraction==Fraction.TOWN;
    }

    public boolean isSyndicateRoles(){
        return fraction==Fraction.SYNDICATE;
    }

    public boolean isFractionRole(Fraction checkedFraction){
        return fraction==checkedFraction;
    }

    public int getFractionNameStringID() {
        if(fraction.equals(Fraction.TOWN))
            return R.string.town;
        if(fraction.equals(Fraction.MAFIA))
            return R.string.mafia;
        if(fraction.equals(Fraction.SYNDICATE))
            return R.string.syndicate;

        return R.string.fraction;
    }

    private void buildRoleDescriptionDialog(Context context){
        roleDescriptionDialog = new MaterialDialog.Builder(context)
                .title(context.getString(getName()))
                .content(context.getString(getDescription()))
                .positiveText(R.string.ok)
                .build();
    }

    public void showRoleDescriptionDialog(Context context){
        if(roleDescriptionDialog==null)
            buildRoleDescriptionDialog(context);

        roleDescriptionDialog.show();
    }

    //GETTERS AND SETTERS:

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getIconResourceID() {
        return iconResourceID;
    }

    public void setIconResourceID(int iconResourceID) {
        this.iconResourceID = iconResourceID;
    }

    public Fraction getFraction() {
        return fraction;
    }


    public void setFraction(Fraction fraction) {
        this.fraction = fraction;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }


    public int getNightWakeHierarchyNumber() {
        return nightWakeHierarchyNumber;
    }

    public void setNightWakeHierarchyNumber(int nightWakeHierarchyNumber) {
        this.nightWakeHierarchyNumber = nightWakeHierarchyNumber;
    }


    public int getRolePlayersAmount() {
        return rolePlayersAmount;
    }

    public void setRolePlayersAmount(int rolePlayersAmount) {
        this.rolePlayersAmount = rolePlayersAmount;
    }

    public boolean isOrdinaryCitizenRole(){
        return this.name == R.string.citizen;
    }

    public boolean isOrdinaryMafiosoRole(){
        return this.name == R.string.mafioso;
    }

    public boolean isBasicMafiaRole(){
        return isOrdinaryCitizenRole()||isOrdinaryMafiosoRole();
    }

    /*public boolean is_actionMade() {
        return roleActionMade;
    }

    public void set_bActionMade(boolean b_actionMade) {
        this.roleActionMade = b_actionMade;
    }*/

    /*public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }*/


    @Override
    public String toString() {
        return String.valueOf(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerRole)) return false;

        PlayerRole that = (PlayerRole) o;

        if (name != that.name) return false;
        if (description != that.description) return false;
        if (iconResourceID != that.iconResourceID) return false;
        return fraction == that.fraction;

    }


    @Override
    public int hashCode() {
        int result = name;
        result = 31 * result + description;
        result = 31 * result + iconResourceID;
        return result;
    }
}
