package com.radirius.merc.fmwk;

import com.radirius.merc.gfx.Graphics;

/**
 * A game state class to be used in {@code StateCore}.
 * 
 * @author wessles
 */
public abstract class GameState {
    protected final StateCore parent_statecore;
    
    public GameState(StateCore parent_statecore) {
        this.parent_statecore = parent_statecore;
    }
    
    public abstract void update(float delta);
    
    public abstract void render(Graphics g);
    
    public StateCore getParentStateCore() {
        return parent_statecore;
    }
}
