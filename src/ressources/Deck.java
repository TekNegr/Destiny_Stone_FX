package ressources;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
public class Deck extends Hand{

    //CONSTRUCTEUR
    public Deck(Game game){
        super(78, game);
    }


    //CUSTOM METHODS
    public void populateDeck(){
        Card[] MinCards = MinorArcanaCards.populateWithMinCards();
        Card[] MajCards= MajorArcanaCard.populateWithMajCards();
        this.hand = this.mixCards(MinCards, MajCards);
        this.shuffleDeck();
        for(Card card: this.hand){
            card.setGame(this.game);
        }
    }

    public Card[] mixCards(Card[] d1, Card[] d2){
        int mixedLength = d1.length + d2.length;
        ArrayList<Card> mixedDeck = new ArrayList<>(mixedLength);
        Collections.addAll(mixedDeck, d1);
        Collections.addAll(mixedDeck, d2);
        Collections.shuffle(mixedDeck);
        return mixedDeck.toArray(new Card[mixedLength]);
    }

    public void shuffleDeck(){
        ArrayList<Card> cardList =  new ArrayList<>();
        Collections.addAll(cardList, this.hand);
        Collections.shuffle(cardList);
        this.hand = cardList.toArray(new Card[this.handSize]);
    }

    public Card drawCard(){
        this.shuffleDeck();
        this.shuffleDeck();
        Card drawnCard = this.hand[0];
        this.removeCardbyObject(drawnCard);
        return drawnCard;
    }

    public MajorArcanaCard drawMajor(){
        MajorArcanaCard[] MajCards = MajorArcanaCard.populateWithMajCards();
        ArrayList<MajorArcanaCard> mixedMajors = new ArrayList<>(MajCards.length);
        Collections.addAll(mixedMajors, MajCards);
        Collections.shuffle(mixedMajors);
        MajCards = mixedMajors.toArray(new MajorArcanaCard[MajCards.length]);
        MajorArcanaCard drawnCard = MajCards[0];
        this.removeCardbyObject(drawnCard);
        return drawnCard;
    }

    public MinorArcanaCards drawMinor(){
        MinorArcanaCards[] MinCards = MinorArcanaCards.populateWithMinCards();
        ArrayList<MinorArcanaCards> mixedMinors = new ArrayList<>(MinCards.length);
        Collections.addAll(mixedMinors, MinCards);
        Collections.shuffle(mixedMinors);
        MinCards = mixedMinors.toArray(new MinorArcanaCards[MinCards.length]);
        MinorArcanaCards drawnCard = MinCards[0];
        this.removeCardbyObject(drawnCard);
        return drawnCard;
    }
    
}
