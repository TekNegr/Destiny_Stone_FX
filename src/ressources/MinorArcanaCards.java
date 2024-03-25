package ressources;

public class MinorArcanaCards extends Card{

    //ENUM WITH VALUES
    private enum Suit {
        SWORDS("Swords"), CUPS("Cups"), PENTACLES("Pentacles"), WANDS("Wands");

        private String suitName;
        Suit(String suitName){ 
            this.suitName = suitName;
        }

        public String getSuit(){
            return suitName;
        }


    }

    private enum Value{
        ACE("Ace", 1),
        TWO("Two", 2),
        THREE("Three", 3),
        FOUR("Four", 4),
        FIVE("Five", 5),
        SIX("Six", 6),
        SEVEN("Seven", 7),
        EIGHT("Eight", 8),
        NINE("Nine", 9),
        TEN("Ten", 10),
        PAGE("Page", 11),
        KNIGHT("Knight", 12),
        QUEEN("Queen", 13),
        KING("King", 14);


        private String valueName;
        private int valueNumber;
        Value(String name, int valueNumber){
            this.valueName=name;
            this.valueNumber=valueNumber;
        }

        public int getValue(){
            return valueNumber;
        }

        public void changeValue(int change){
            this.valueNumber += change;
        }


    }

    private Suit suit;
    private Value value;

    //CONSTRUCTOR
    public MinorArcanaCards(String name,  String destiny, int cost, int HP, Suit suit, Value value, boolean isCharacter){
        super(name, destiny, cost, HP, false, isCharacter);
        this.suit = suit;
        this.value = value;

    }


    //CUSTOM METHODS

    ///Populate deck with minor arcanas
    public static MinorArcanaCards[] populateWithMinCards(){
        MinorArcanaCards[] DeckMinCards = new MinorArcanaCards[56];
        for (int i=0; i<56; i++){
            for(Suit suit : Suit.values()){
                for(Value value: Value.values()) {
                    String cardName = value.valueName + " of " +  suit.suitName;
                    String cardDestiny;
                    int cardCost;
                    int HP, DMG;
                    boolean isCharacter;
                    if (value.valueNumber > 10){
                        cardDestiny = "Spawnable of" + suit.suitName;
                        isCharacter = true;
                        cardCost = value.valueNumber -5;
                        HP = setHPMinARc(suit, value);
                        DMG = setDMGMinArc(suit, value);
                    }
                    else{
                        cardDestiny = "Spell of" + suit.suitName;
                        isCharacter = false;
                        cardCost = value.valueNumber;
                        DMG = 0;
                        HP = 0;
                    }
                    DeckMinCards[i] = new MinorArcanaCards(cardName, cardDestiny, cardCost,HP, suit, value, isCharacter);
                }
            }
        }
        return DeckMinCards;
    }


    ///HP setter
    public static int setHPMinARc(Suit suit, Value value){
        int hp = 0;
        switch (value) {
            case KING:
                hp = 15;
                break;

            case QUEEN:
                hp=12;
                break;

            case KNIGHT:
                hp=9;
                break;

            case PAGE:
                hp=6;
                break;
            default:
                hp = 0;
                break;
        }
        if (suit == Suit.CUPS ){
            hp *= 1.3;
        }
        return hp;

    }

    ///DMG setter
    public static int setDMGMinArc(Suit suit, Value value){
        int DMG;
        switch (value) {
            case KING:
                DMG = 6;
                break;

            case QUEEN:
                DMG=4;
                break;

            case KNIGHT:
                DMG=3;
                break;

            case PAGE:
                DMG=1;
                break;
            default:
                DMG = 0;
                break;
        }
        if (suit == Suit.SWORDS){
            DMG *= 1.3;
        }
        return DMG;
    }


    // FONCTION ABSTRAITE D'ACTIVATION
    @Override
    public void placeCard(Slot placementSlot, Game game, PlayerHalf player){
        if(player.getYouCard().status.contains(Effect.B_WAND) && this.suit == Suit.WANDS){
            this.value.changeValue(2);
        }
        else if(player.getYouCard().status.contains(Effect.B_SWORD)&& this.suit == Suit.SWORDS){
            this.value.changeValue(3);
        }
        else if(player.getYouCard().status.contains(Effect.B_PENT)&& this.suit == Suit.PENTACLES){
            player.addMp((this.value.valueNumber<10)? (this.value.valueNumber%2)+1:(this.value.valueNumber-10));

        }
        else if(player.getYouCard().status.contains(Effect.B_CUPS)&& this.suit == Suit.CUPS){
            player.getYouCard().varyHealth((this.value.valueNumber<10)? (this.value.valueNumber%2):(this.value.valueNumber-10));
        }
        if(!placementSlot.acceptOnlyMaj()){
            if(placementSlot.isEmpty()){
                placementSlot.addCard(this);
                this.addStats(Effect.STUN);
            }
        }
    }

    @Override
    public void useSkill(Card targetCard, Game game, PlayerHalf player){
        Slot[] Player_Persons = player.getPersons_Field() ;
        Effect used_effect;
        switch (this.suit) {
            case SWORDS:{
                if(this.value.valueNumber> 10){
                    for ( Slot slot: Player_Persons) {
                        used_effect = Effect.B_DMG;
                        slot.getCard().varyDamage((this.value.getValue() - 10) );
                        slot.getCard().addStats(used_effect);
                        //Give card's value - 10 of Damage to each card in player person field
                    }
                }
                else{
                    targetCard.varyDamage( this.value.getValue());
                }
                break;
            }
            case CUPS:{
                if(this.value.valueNumber> 10){
                    for ( Slot slot: Player_Persons) {
                        used_effect = Effect.B_HEAL;
                        slot.getCard().varyHealth((this.value.getValue() - 10) );
                        slot.getCard().addStats(used_effect);
                         //Give card's value - 10 of HP to each card in player person field
                    }
                }
                else{
                    targetCard.varyHealth(this.value.getValue());
                }
                   
                break;
            }
            case PENTACLES:{
                int givenMP =0;
                if(this.value.valueNumber> 10){
                    for ( Slot slot: Player_Persons){
                        givenMP ++;
                        givenMP += (this.value.getValue() - 10)/2;
                    }
                }
                    //Give card's value - 10 of MP to the player
                else{
                    givenMP = this.value.getValue();
                }
                player.addMp(givenMP);
                break;
            }
            case WANDS:{
                int givenCards =0;
                int givenMP =0;
                if(this.value.valueNumber> 10){
                    for ( Slot slot: Player_Persons){givenCards++;}
                    givenMP = (this.value.getValue() -10)/2;
                }
                //Give +2 MP and draw the card's value - 10 for the player
                else{
                    givenCards=(this.value.getValue()/2) +1;
                    givenMP=(this.value.getValue()/2) -1;
                }
                for(int i = 0; i< givenCards ;i++){player.drawFromDeck();}
                player.addMp(givenMP);
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void attackCard(Slot slotTarget, Game game, PlayerHalf player){
        int restAtk = 0;
        PlayerHalf opponent = game.player1.equals(player) ? game.player2 : game.player1;
        if(!this.status.contains(Effect.STUN)){
            if(this.isCharacter){
                if(this.damage > slotTarget.getCard().getHealth()){
                    restAtk = slotTarget.getCard().getHealth() - this.damage;
                }
                slotTarget.getCard().hurt(this.damage, this);
                opponent.getYouCard().hurt(restAtk, this);
            }
        }
        
    };
}
