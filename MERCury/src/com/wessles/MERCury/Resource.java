package com.wessles.MERCury;

/**
 * A common denominator of all resources. This is so that all resources can be cloned.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public interface Resource extends Cloneable {
  public void clean();
}
