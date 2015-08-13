package edu.eci.arsw;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;

public class LinguisticDataSource {

	private static Map<String,String> equivalencesMap;
	private static Set<String> wordList;
	
	static{		
		equivalencesMap=new LinkedHashMap<String,String>();
		wordList=new LinkedHashSet<String>();
		
		equivalencesMap.put("hoal", "hola");
		equivalencesMap.put("yola", "hola");
		equivalencesMap.put("jola", "hola");
		equivalencesMap.put("hol", "hola");
		equivalencesMap.put("vuenas", "buenas");
		equivalencesMap.put("nuenas", "buenas");
		equivalencesMap.put("huenas", "buenas");
		
		for (Entry<String,String> e:equivalencesMap.entrySet()){
			wordList.add(e.getValue());
		}
		
	}
	
	public String getEquivalences(String origw){
		String res=equivalencesMap.get(origw);
		if (res==null){
			return null;
		}
		else{
			return res;
		}
	}
	

	public Set<String> getWordList(){
		return wordList;
	}
	
	
}
