/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carde
 */
public enum Genre {
    MALE,FEMALE;
    
    @Override
  public String toString() {
    switch(this) {
      case MALE: return "MALE";
      case FEMALE: return "FEMALE";
      default: throw new IllegalArgumentException();
    }
  }
}
