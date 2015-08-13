package edu.eci.arsw;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import edu.eci.arsw.util.Maybe;

public class LinguisticDataSource {

	final private static Map<String,String> equivalencesMap;
	
	static{		
		equivalencesMap=new LinkedHashMap<>();
		
		equivalencesMap.put("hoal", "hola");
		equivalencesMap.put("yola", "hola");
		equivalencesMap.put("jola", "hola");
		equivalencesMap.put("hol", "hola");
		equivalencesMap.put("vuenas", "buenas");
		equivalencesMap.put("nuenas", "buenas");
		equivalencesMap.put("huenas", "buenas");
				
	}
	
	public Maybe<String> getEquivalences(String origw){
		String res = equivalencesMap.get(origw);
		if (res==null){
		   return Maybe.getNothing();
		}
		else{
		   return Maybe.Just(res);
		}
	}
	

	public Set<String> getWordList(){
		return equivalencesMap.keySet();
	}
	
	
}
