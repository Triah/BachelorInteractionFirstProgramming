package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

    public void update(float deltaTime){
        states.peek().update(deltaTime);
    }

    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }

    public State peek(){
        return states.peek();
    }

}
