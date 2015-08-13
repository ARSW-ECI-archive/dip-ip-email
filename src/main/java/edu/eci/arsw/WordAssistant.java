package edu.eci.arsw;


public class WordAssistant {

	LinguisticDataSource lds=new LinguisticDataSource();
	
	/**
	 * Obj: Verificar que la palabra ingresada esté sujeta a correcciones, por ejemplo
	 * por un error típico de digitación identificado.	
	 * @param word
	 * @return
	 */
	public String check(String word){
		String equiv=lds.getEquivalences(word);
		
		return equiv;
		
	}
	
	
}
