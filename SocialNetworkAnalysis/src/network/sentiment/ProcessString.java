/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.sentiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Rachita
 */
public class ProcessString {
    /*
     * processes string to give back SpecialPostings
     */
    
    WordsAreOnlyWords _wordMap = null;
    SpecialPosting posting=null;

    public ProcessString() {
        _wordMap = new WordsAreOnlyWords();
	posting = new SpecialPosting(_wordMap);
    }
    
    public String[] stopwords = { "the", "a", "as", "able", "about", "above",
			"according", "accordingly", "across", "actually", "after",
			"afterwards", "again", "against", "aint", "all", "allow", "allows",
			"almost", "alone", "along", "already", "also", "although",
			"always", "am", "among", "amongst", "an", "and", "another", "any",
			"anybody", "anyhow", "anyone", "anything", "anyway", "anyways",
			"anywhere", "apart", "appear", "appreciate", "appropriate", "are",
			"arent", "around", "as", "aside", "ask", "asking", "associated",
			"at", "available", "away", "awfully", "be", "became", "because",
			"become", "becomes", "becoming", "been", "before", "beforehand",
			"behind", "being", "believe", "below", "beside", "besides", "best",
			"better", "between", "beyond", "both", "brief", "but", "by",
			"cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause",
			"causes", "certain", "certainly", "changes", "clearly", "co",
			"com", "come", "comes", "concerning", "consequently", "consider",
			"considering", "contain", "containing", "contains",
			"corresponding", "could", "couldnt", "course", "currently",
			"definitely", "described", "despite", "did", "didnt", "different",
			"do", "does", "doesnt", "doing", "dont", "done", "down",
			"downwards", "during", "each", "edu", "eg", "eight", "either",
			"else", "elsewhere", "enough", "entirely", "especially", "et",
			"etc", "even", "ever", "every", "everybody", "everyone",
			"everything", "everywhere", "ex", "exactly", "example", "except",
			"far", "few", "ff", "fifth", "first", "five", "followed",
			"following", "follows", "for", "former", "formerly", "forth",
			"four", "from", "further", "furthermore", "get", "gets", "getting",
			"given", "gives", "go", "goes", "going", "gone", "got", "gotten",
			"greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt",
			"have", "havent", "having", "he", "hes", "hello", "help", "hence",
			"her", "here", "heres", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "hi", "him", "himself", "his",
			"hither", "hopefully", "how", "howbeit", "however", "i", "id",
			"ill", "im", "ive", "ie", "if", "ignored", "immediate", "in",
			"inasmuch", "inc", "indeed", "indicate", "indicated", "indicates",
			"inner", "insofar", "instead", "into", "inward", "is", "isnt",
			"it", "itd", "itll", "its", "its", "itself", "just", "keep",
			"keeps", "kept", "know", "knows", "known", "last", "lately",
			"later", "latter", "latterly", "least", "less", "lest", "let",
			"lets", "like", "liked", "likely", "little", "look", "looking",
			"looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean",
			"meanwhile", "merely", "might", "more", "moreover", "most",
			"mostly", "much", "must", "my", "myself", "name", "namely", "nd",
			"near", "nearly", "necessary", "need", "needs", "neither", "never",
			"nevertheless", "new", "next", "nine", "no", "nobody", "non",
			"none", "noone", "nor", "normally", "not", "nothing", "novel",
			"now", "nowhere", "obviously", "of", "off", "often", "oh", "ok",
			"okay", "old", "on", "once", "one", "ones", "only", "onto", "or",
			"other", "others", "otherwise", "ought", "our", "ours",
			"ourselves", "out", "outside", "over", "overall", "own",
			"particular", "particularly", "per", "perhaps", "placed", "please",
			"plus", "possible", "presumably", "probably", "provides", "que",
			"quite", "qv", "rather", "rd", "re", "really", "reasonably",
			"regarding", "regardless", "regards", "relatively", "respectively",
			"right", "said", "same", "saw", "say", "saying", "says", "second",
			"secondly", "see", "seeing", "seem", "seemed", "seeming", "seems",
			"seen", "self", "selves", "sensible", "sent", "serious",
			"seriously", "seven", "several", "shall", "she", "should",
			"shouldnt", "since", "six", "so", "some", "somebody", "somehow",
			"someone", "something", "sometime", "sometimes", "somewhat",
			"somewhere", "soon", "sorry", "specified", "specify", "specifying",
			"still", "sub", "such", "sup", "sure", "ts", "take", "taken",
			"tell", "tends", "th", "than", "thank", "thanks", "thanx", "that",
			"thats", "thats", "the", "their", "theirs", "them", "themselves",
			"then", "thence", "there", "theres", "thereafter", "thereby",
			"therefore", "therein", "theres", "thereupon", "these", "they",
			"theyd", "theyll", "theyre", "theyve", "think", "third", "this",
			"thorough", "thoroughly", "those", "though", "three", "through",
			"throughout", "thru", "thus", "to", "together", "too", "took",
			"toward", "towards", "tried", "tries", "truly", "try", "trying",
			"twice", "two", "un", "under", "unfortunately", "unless",
			"unlikely", "until", "unto", "up", "upon", "us", "use", "used",
			"useful", "uses", "using", "usually", "value", "various", "very",
			"via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we",
			"wed", "well", "were", "weve", "welcome", "well", "went", "were",
			"werent", "what", "whats", "whatever", "when", "whence",
			"whenever", "where", "wheres", "whereafter", "whereas", "whereby",
			"wherein", "whereupon", "wherever", "whether", "which", "while",
			"whither", "who", "whos", "whoever", "whole", "whom", "whose",
			"why", "will", "willing", "wish", "with", "within", "without",
			"wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you",
			"youd", "youll", "youre", "youve", "your", "yours", "yourself",
			"yourselves", "zero" };
    
    public Set<String> stopWords = new HashSet<String>(Arrays.asList(stopwords));
    public Set<String> stemmedStopWordSet = stemString(stopWords);
        
    public Map<String, Integer> returnSpecialPostings(String str){
        List<String> setStr = returnString(str);
        for(String s1: setStr){
            posting.addToSpecialPosting(s1);
        }
        return returnRealPostings();
        
    }
    
    public Map<String, Integer> returnRealPostings() {
		Map<String, Integer> realpostings = new HashMap<>();
		for (Integer i : posting.getSpecialPosting().keySet()) {
			int value = posting.getSpecialPosting().get(i);
			realpostings.put(posting.wordMap.reverse_bag_of_words.get(i), value);
		}
		return realpostings;
	}
    
    public List<String> returnString(String str){
        str = ridStopWords(str);
        return stemString(str);
    }
    
    public String ridStopWords(String string){
        String result = "";
        String[] words = string.split("\\s+");
        for(String word: words){
            if(word.isEmpty()){
                continue;
            }if (word.length() < 2 || word.charAt(0) >= '0'
		&& word.charAt(0) <= '9' || stopWords.contains(word))
		continue;
            
             result += (word + " ");
        }
       
        return result.replaceAll("[^a-zA-Z ]+", "").toLowerCase();
    }
    
    public Set<String> stemString(Set<String> stringSet) {
		Stemmer stemmer = new Stemmer();
		Set<String> results = new HashSet<String>();
		for (String string : stringSet) {
			results.add(stemmer.stem(string));
		}
		return results;
	}

	public List<String> stemString(String stringSet) {
		Stemmer stemmer = new Stemmer();
		List<String> results = new ArrayList<String>();
		for (String string : stringSet.split(" ")) {
			results.add(stemmer.stem(string));
		}
		return results;
	}
}
