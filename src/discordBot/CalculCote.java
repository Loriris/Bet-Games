package discordBot;

import com.mashape.unirest.http.exceptions.UnirestException;

public class CalculCote {
	
	private static InfoAPI infoC;
	
	static float ratio;
	static String pseudo;
	static String region;
	float totRatioEq1, totRatioEq2, ratioEq1, ratioEq2, coteEq1, coteEq2;
	
	//constructeur tableau de participants
	
	public void calcul() throws UnirestException {
		
		pseudo = Main.gameLog[0];
    	region = Main.gameLog[1];
    	infoC = new InfoAPI(pseudo, region);
    	infoC.PartyInfo();
    	infoC.retrieveParticipantsInfo();
		for(int index = 0; index < infoC.getParticipant().length; index++)
		{
			ratio = infoC.getParticipant()[index].getRatio();
			if(index < (infoC.getParticipant().length/2))
			{
				totRatioEq1 += ratio;
				//System.out.print(" EQ1 " + ratioEq1);
			}
			else
			{
				totRatioEq2 += ratio;
				//System.out.print(" EQ2 " + ratioEq2);

			}
	
			//System.out.print(" g " + ratio);
		}
		
		ratioEq1 = totRatioEq1/5;
		ratioEq2 = totRatioEq2/5;
		
		coteEq1 = 1/ratioEq1;
		coteEq2 = 1/ratioEq2;

		System.out.println("Eq1 " + coteEq1);
		System.out.println("Eq2 " + coteEq2);
	}	
	
}
