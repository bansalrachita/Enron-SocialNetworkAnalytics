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
public class WordsAreOnlyWords {
    
    Map<String, Integer> bag_of_words = new HashMap<>();
    Map<Integer, String> reverse_bag_of_words = new HashMap<>();
    int unique_counter = 1;

    public WordsAreOnlyWords() {
    }

    public Map<String, Integer> getBag_of_words() {
        return bag_of_words;
    }

    public void setBag_of_words(Map<String, Integer> bag_of_words) {
        this.bag_of_words = bag_of_words;
    }

    public Map<Integer, String> getReverse_bag_of_words() {
        return reverse_bag_of_words;
    }

    public void setReverse_bag_of_words(Map<Integer, String> reverse_bag_of_words) {
        this.reverse_bag_of_words = reverse_bag_of_words;
    }
    
    
    public Integer getWord(String word){
        //do stemming
        
        if(word!=null){
            Integer numeric_word = getBag_of_words().get(word.trim());
            
            if(numeric_word == null){
                getBag_of_words().put(word, unique_counter);
                getReverse_bag_of_words().put(unique_counter, word);
                numeric_word = unique_counter;
                unique_counter++;
            }
            return numeric_word;
        }
        return null;
    }
}
