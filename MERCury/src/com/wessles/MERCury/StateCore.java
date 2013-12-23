package com.wessles.MERCury;

import java.util.HashMap;

import com.wessles.MERCury.opengl.Graphics;

/**
 * A sub-class of {@code Core} that will add in the capabilities to handle {@code GameState}s.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public abstract class StateCore extends Core {
  public HashMap<Integer, GameState> gamestates = new HashMap<Integer, GameState>();
  private int states = 0, current_state = 0;
  
  public StateCore(String name) {
    super(name);
  }
  
  @Override
  public abstract void init(ResourceManager RM);
  
  @Override
  public void update(float delta) {
    updateGameState(delta);
  }
  
  @Override
  public void render(Graphics g) {
    renderGameState(g);
  }
  
  @Override
  public abstract void cleanup(ResourceManager RM);
  
  public void updateGameState(float delta) {
    gamestates.get(current_state).update(delta);
  }
  
  public void renderGameState(Graphics g) {
    gamestates.get(current_state).render(g);
  }
  
  public void addState(GameState state) {
    gamestates.put(states, state);
    states++;
  }
  
  public void setState(int state) {
    current_state = state;
  }
}
