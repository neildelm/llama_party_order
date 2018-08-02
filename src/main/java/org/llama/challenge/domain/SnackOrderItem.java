package org.llama.challenge.domain;

public class SnackOrderItem {
    private Snack snack;
    private int amount = 0;

    public SnackOrderItem snack(Snack snack){
        this.snack = snack;
        return this;
    }

    public SnackOrderItem amount(int amount){
        this.amount = amount;
        return this;
    }

    public Snack getSnack(){
        return snack;
    }

    public int getAmount(){
        return amount;
    }

    @Override
    public String toString() {
        return "SnackOrderItem{" +
                "snack=" + snack +
                ", amount=" + amount +
                '}';
    }
}
