package models;

public class Ingredient {
    public enum State { RAW, CHOPPED, FRIED, READY}

    private String name;
    private State currentState;

    public Ingredient(String name, State initialState) {
        this.name = name;
        this.currentState = initialState;

    }
    public void changeState(State newState) {
        this.currentState = newState;
    }

    public String getName() {
        return name;
    }
    public State getCurrentState() {
        return currentState;
    }
}
