package discordBot;

import com.mashape.unirest.http.exceptions.UnirestException;

public class CalculCote {
	
	private static InfoAPI infoC;
	
	static float ratio;
	static String pseudo, region;
	float totRatioEq1, totRatioEq2, ratioEq1, ratioEq2, coteEq1, coteEq2;
	int denoEq1 = 5, denoEq2 =5;
		
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
				if(ratio == 0)
					denoEq1--;
			}	
			else
			{
				totRatioEq2 += ratio;
				//System.out.print(" EQ2 " + ratioEq2);
				if(ratio == 0)
					denoEq2--;

			}
		}
		
		ratioEq1 = totRatioEq1/denoEq1;
		ratioEq2 = totRatioEq2/denoEq2;
		
		coteEq1 = 1/ratioEq1;
		coteEq2 = 1/ratioEq2;
	}	
}
