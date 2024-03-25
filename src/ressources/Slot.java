package ressources;

public class Slot {
    private Card heldCard;
    private boolean OnlyMajorArcana;
    private String slotType;


    public Slot(boolean OMA, String type){
        this.OnlyMajorArcana = OMA;
        this.slotType=type;
        this.heldCard = null;
    }

    public Card getCard(){
        return heldCard;
    }

    public boolean acceptOnlyMaj(){
        return OnlyMajorArcana;
    }

    public boolean isEmpty(){
        return heldCard==null ? true : false;
    }

    public String getslotType(){
        return this.slotType;
    }

    public void addCard(Card card){
        if(this.OnlyMajorArcana && !card.isMajorArcana)throw new IndexOutOfBoundsException();
        else{
            this.heldCard = card;
            this.heldCard.Placement = this.slotType;
        }
    }

    public void removeCard(Card card){
        if (this.heldCard == card){
            this.heldCard = null;
        }
    }
}
