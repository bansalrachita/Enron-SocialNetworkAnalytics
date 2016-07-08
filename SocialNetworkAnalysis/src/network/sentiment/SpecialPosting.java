/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network.sentiment;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rachita
 */
public class SpecialPosting {
    
    Map<Integer, Integer> specialPosting;
    WordsAreOnlyWords wordMap;

    public SpecialPosting(WordsAreOnlyWords wordMap) {
        this.specialPosting = new HashMap<>();
        this.wordMap = wordMap;
    }

    public Map<Integer, Integer> getSpecialPosting() {
        return specialPosting;
    }

    public WordsAreOnlyWords getWordMap() {
        return wordMap;
    }

    public void setSpecialPosting(Map<Integer, Integer> specialPosting) {
        this.specialPosting = specialPosting;
    }

    public void setWordMap(WordsAreOnlyWords wordMap) {
        this.wordMap = wordMap;
    }
    
    public void addToSpecialPosting(String word){
        Integer word_integer = wordMap.getWord(word);
        
        if(null != word_integer && specialPosting.containsKey(word)){
            specialPosting.put(word_integer, specialPosting.get(word_integer)+ 1);
        }else{
            specialPosting.put(word_integer,1);
        }
    }
    
    
}
