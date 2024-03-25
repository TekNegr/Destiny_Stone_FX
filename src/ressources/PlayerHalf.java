package ressources;

import java.util.Objects;

public class PlayerHalf {
    private String Username;
    private int MP;
    private int max_MP;
    private Hand playerHand;
    private Slot You_Slot;
    private Slot[] Event_Slots;
    private Slot[] Persons_Field;
    private Game game;


    // CONSTRUCTOR
    public PlayerHalf(String Username, Game game){
        this.game = game;
        this.max_MP = 5;
        this.Username = Username;
        this.playerHand = new Hand(15, this.game);
        this.You_Slot = new Slot(true, "You");
        this.Event_Slots = new Slot[3];
        for(int i=0;i<3;i++){
            this.Event_Slots[i] = new Slot(true, "Event");
        }
        this.Persons_Field = new Slot[10];
        for(int i=0;i<6;i++){
            this.Persons_Field[i] = new Slot(false, "Person");
        }
    }

    // GETTERS & SETTERS
    public String getUsername( ){ return this.Username;}

    public Slot[] getPersons_Field(){
        return this.Persons_Field;
    }

    public void placeCard(Slot placemenSlot, int index){
        if (placemenSlot == null){
            this.playerHand.placeCard(placemenSlot, index, this.game, this);
        }
    }

    public Slot[] getEvent_Slots(){
        return this.Event_Slots;
    }

    public int getOccupiedPersons(){
        int count=0;
        for(Slot slot:this.Persons_Field) {
            if(!slot.isEmpty())count++;
        }
        return count;
    }

    public Card getCardWithEffect(Card.Effect effect){
        for(Slot slot: this.Persons_Field){
            Card card = slot.getCard();
            if(card.status.contains(effect)){
                return card;
            }
        }
        return null;
    }

    public boolean isEffectInPersons(Card.Effect effect){
        for(Slot slot: this.Persons_Field){
            Card card = slot.getCard();
            if(card.status.contains(effect)){
                return true;
            }
        }
        return false;
    }

    public boolean isEffectInEvent(Card.Effect effect){
        for(Slot slot: this.Event_Slots){
            Card card = slot.getCard();
            if(card.status.contains(effect)){
                return true;
            }
        }
        return false;
    }
    //Draw
    public void drawFromDeck(){
        this.addToHand(this.game.drawingDeck.drawCard());
    }

    //Updater des slots 
    public void updateSlots(Slot upd_You, Slot[] upd_Event, Slot[] upd_Person){
        if(upd_You != this.You_Slot || upd_You != null){
            this.You_Slot = upd_You;
        }
        else if(upd_Event != this.Event_Slots || upd_Event != null){
            this.Event_Slots = upd_Event;
        }
        else if(upd_Person != this.Persons_Field || upd_Person != null){
            this.Persons_Field = upd_Person;
        }
    }
    
    //Set the You Card 
    public void setYouCard(MajorArcanaCard You_Card){
        You_Card.setHealthTo(50);
        You_Card.setCost(0);
        this.You_Slot.addCard(You_Card);
    }

    //Ajout de carte a la main
    public void addToHand(Card card){
        this.playerHand.addCard(card);
    }

    //Max_MP +1
    public void addMaxMP(){this.max_MP ++;}

    //Add n MP to player
    public void addMp(int delta){
        while(this.MP < this.max_MP){
            this.MP += delta;
        }
    }

    //Regen MP to Max_MP
    public void regenMp(){
        if(this.MP<this.max_MP){this.MP = this.max_MP;}
    }

    //return you card
    public Card getYouCard(){
        return You_Slot.getCard();
    }




    public void damageAll(int dmg, Card sender){
        dmg = (dmg/this.getOccupiedPersons());
        for(Slot slot:this.Persons_Field)slot.getCard().hurt(dmg, sender);
    }

    //Log of player
    public void logPlayer(){
        System.out.println("Player :"+ this.Username + "\n");
        System.out.println("You:");
        this.You_Slot.getCard().logCard();
        System.out.println("\n");
        System.out.println("Event: \n");
        for(int i=0;i<3;i++){
            this.Event_Slots[i].getCard().logCard();
            System.out.println("\n");
        }
        System.out.println("\n");
        System.out.println("\n");
        for(int i=0;i<10;i++){
            this.Persons_Field[i].getCard().logCard();
            System.out.println("\n");
        }

    }

    public boolean isCardInPersons(Card card){
        for(Slot slot : this.Persons_Field){
            if (Objects.equals(slot.getCard(), card)){
                return true;
            }
        }
        return false;
    }

    public boolean isCardInEvent(Card card){
        for(Slot slot : this.Event_Slots){
            if (Objects.equals(slot.getCard(), card)){
                return true;
            }
        }
        return false;
    }
}
