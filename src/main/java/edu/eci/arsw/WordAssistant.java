package edu.eci.arsw;

import edu.eci.arsw.util.Maybe;


public class WordAssistant {

	LinguisticDataSource lds=new LinguisticDataSource();
	
	/**
	 * Obj: Verificar que la palabra ingresada esté sujeta a correcciones, por ejemplo
	 * por un error típico de digitación identificado.	
	 * @param word
	 * @return
	 */
	public Maybe<String> check(String word){
		Maybe<String> equiv=lds.getEquivalences(word);
		
		return equiv;
		
	}
	
	
}
