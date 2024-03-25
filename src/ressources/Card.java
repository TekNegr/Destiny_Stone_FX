package ressources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.image.Image;

public abstract class Card {
    protected String name;
    protected String destiny;
    protected int cost;
    protected String Placement;
    protected int HealthPoint;
    protected Image image;
    protected ArrayList<Effect> status;
    protected boolean isMajorArcana;
    //protected Effect effect;
    protected boolean isCharacter;
    protected int damage;
    protected Game game;

    // enum of effects ? not sure what to do with it
    public enum Effect {

        DODGE("Dodge"),
        IMMUNE("Immune"),
        PROTECTED("Protected"),
        PROTECTOR("Protector"),
        PARRY("Parry"),
        ALONE("Immune until alone"),
        SOLITUDE("Solitude"),
        DEFLECT("Deflect"),
        PEEP("Peep through opponent hand"),
        REFLECT("REFLECT"),
        ENEMY_ALLY( "Enemy Ally"),

        DAMAGE("Damage"),
        DAMAGE_ALL("Damage all"),
        BURNING_SURFACE("Burning Surface"),

        B_HEAL("Heal"),
        B_MP("Generate MP"),
        B_DMG("Boost Damage"),
        B_RAND("Random Buff"),
        B_WAND("Boost Wand"),
        B_SWORD( "Boost Sword" ),
        B_PENT( "Boost Pentacle" ),
        B_CUPS( "Boost Cups" ),
        B_ALL("Boost all"),
        B_WORLD("Boost of the world"),
        B_SUN("Boost of the Sun"),
        B_TEMP("Temperance boost"),
        DISTANCE("Distance Boost"),
        CRUMBLE("Crumbling"),
        CUMULATE_MP("Cumulate MP"),
        DEVIL_DEAL("Devilish deal"),
        MOON_HEAL("Heal Moon"),
        REMEMBER("Remember the fallen"),
        B_TOWER("Boost Tower"),


        N_HEAL("Nerf Heal"),
        N_MP("Nerf MP"),
        N_DMG("Nerf Damage"),
        N_RAND( "Nerf Random"),
        STAR_VUL("Star vulnerability"),

        EMPTY_DRAW("Drawing advantage when empty handed"),
        DRAW("Draw"),
        DRAW_Min("Draw Minor Arcana"),
        DRAW_Maj("Draw Major Arcana"),
        STEAL("Steal Card"),
        RETURN( "Return Card" ),
        DISCARD( "Discard Card" ),
        REPLACE("Replace Card"),

        FREE_SPAWN("Next spawn is free"),
        SPAWN_SELF("Spawn self"),
        SPAWN_LOVER( "Spawn Lover" ),
        SPAWN_STR("Spawn Strength"),
        SPAWN_HERMIT( "Hermit Spawns Self" ),
        SPAWN_MOON( "Spawn Moon" ),
        REVIVE("Revive"),
        GRAVE_ROB( "Rob GraveYard"),
        

        STUN("Stuned card"),
        CONFUSE("Confuse/stun cards"),
        SACRIFICE("Sacrifice of card"),
        DEATH_DEAL("Deal with Death"),
        SUICIDE("Suicide"),
        ASSISTED_DEATH("Assisted death");
        
        ;

        private Card originCard;
        private String effectName;
        private int value;

        Effect(String name) {
            this.effectName = name;
        }

        public void setOrigin(Card card){
            this.originCard = card;
        }

        public void setValue(int v) {
            this.value = v;
        }

        public int getValue() {
            return this.value;
        }
    }

    // CONSTRUCTEUR
    public Card(String name, String destiny, int cost, int HP, boolean isMA, boolean isCharacter) {
        this.name = name;
        this.destiny = destiny;
        this.cost = cost;
        this.HealthPoint = HP;
        this.isMajorArcana = isMA;
        this.Placement = null;
        this.status = new ArrayList<Effect>();
        this.isCharacter = isCharacter;
    }




    // GETTERS AND SETTERS

    //// GETTERS


    public int getHealth() {
        return HealthPoint;
    }

    public String getName() {
        return name;
    }

    public String getDestiny() {
        return destiny;
    }

    public int getCost() {
        return cost;
    }

    public Effect[] getStats() {
        return this.status.toArray(new Effect[this.status.size()]);
    }

    public int getDamage() {
        return this.damage;
    }

    public boolean isCardAlone(PlayerHalf player){
        return (player.getPersons_Field().length==1)? true:false;
    }

    //// SETTERS
    public void setGame(Game g){
        this.game = g;
    }

    public void varCost(int delta) {
        this.cost += delta;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPlacement(String placement) {
        this.Placement = placement;
    }

    public void setHealthTo(int HP) {
        this.HealthPoint = HP;
    }

    public void setDamage(int dmg) {
        this.damage = dmg;
    }

    public void hurt(int dmg, Card sender){
        PlayerHalf player = this.game.player1.isCardInPersons(sender)? this.game.player2:this.game.player1;
        PlayerHalf opponent = this.game.player1.isCardInPersons(sender)? this.game.player1:this.game.player2;
        Random random = new Random();
        if(!this.status.contains(Effect.IMMUNE)){
            if (this.status.contains(Effect.PROTECTED)) {
                Effect protectorEffect = searchEffect(Effect.PROTECTOR);
                if (protectorEffect != null && protectorEffect.originCard != null) {
                    protectorEffect.originCard.hurt(dmg, this);
                }
            }
            else if (this.status.contains(Effect.PARRY)){
                    sender.hurt(dmg/2, this);
            }
            else if(this.status.contains(Effect.DEFLECT)){
                opponent.damageAll(dmg, this);
                
            }
            else if(player.isEffectInPersons(Effect.PROTECTOR)){
                player.getCardWithEffect(Effect.PROTECTOR).hurt(dmg,sender);
            }
            else if(this.status.contains(Effect.BURNING_SURFACE)){
                this.HealthPoint -= dmg;
                sender.hurt(dmg/3, this);
            }
            else if(this.Placement =="You"){
                if(player.isEffectInEvent(Effect.SACRIFICE)){
                    for(Slot slot: player.getEvent_Slots()){
                        //if(slot.getCard()==Card){

                        //}
                    }
                }
            }
            else{
                this.HealthPoint -= dmg;
            }
        }
        if(this.HealthPoint == 0){
            this.die(player);
        }
        
    }



    public Effect searchEffect(Effect effect) {
        for (Effect tempEff : this.status) {
            if (tempEff.equals(effect)) {
                return tempEff;
            }
        }
        return null;
    }

    public void die(PlayerHalf player){
        if(player.getYouCard().hasEffect(Effect.CRUMBLE)){
            player.addMp(this.cost/2);
        }
        for(Slot slot: player.getEvent_Slots()){
            if(slot.getCard().hasEffect(Effect.CUMULATE_MP)){
                for(Effect effect:slot.getCard().getStats()){
                    if(effect == Effect.CUMULATE_MP){
                        player.addMp(1);
                    }
                }
            }
        }
        //Ajouter logique de mort
    }
    //// VARIER

    public boolean hasEffect(Effect effect){
        return this.status.contains(effect)?true:false;
    }
    public void varyHealth(int delta) {
        this.HealthPoint += delta;
    }

    public void addStats(Effect status) {
        this.status.add(status);
    }

    public void removeStats(Effect status) {
        Effect[] statuses = this.getStats();
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals(status)) {
                this.status.remove(i);
                break;
            }
        }
    }

    public void varyDamage(int delta) {
        this.damage += delta;
    }

    // Console log of cards
    public void logCard() {
        if (this.isMajorArcana) {
            System.out.println("MajorArcana");
        } else {
            System.out.println("MinorArcana");
        }
        System.out.println("Card : " + this.name + "\n");
        System.out.println("Destiny : " + this.destiny + "\n");
        System.out.println("Stats: " + this.HealthPoint + " HP" + this.cost + " MP cost \n");

        System.out.println("\n");
        System.out.println("----------------------------------\n");
    }

    // FONCTION ABSTRAITE D'ACTIVATION
    //Place carte dans un slot
    public abstract void placeCard(Slot placementSlot, Game game, PlayerHalf player);

    //Active le skill d'une carte
    public abstract void useSkill(Card targetCard, Game game, PlayerHalf player) throws Exception;

    //Attack une autre carte si c'est un character
    public abstract void attackCard(Slot slotTarget, Game game, PlayerHalf player);

    public void removeStats(){
        this.status = new ArrayList<Effect>();
    }


}
