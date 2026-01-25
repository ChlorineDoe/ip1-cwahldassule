package edu.brandeis.cosi103a.ip1.ip2;

public class Card {
    private int cost;
    private int value;
    private String type;

    // Constructor
    public Card(int cost, int value, String type) {
        this.cost = cost;
        this.value = value;
        this.type = type;
    }

    // Getters
    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    // Setters
    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Card{" +
                "cost=" + cost +
                ", value=" + value +
                ", type='" + type + '\'' +
                '}';
    }
}
