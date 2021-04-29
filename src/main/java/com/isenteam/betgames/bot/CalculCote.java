package com.isenteam.betgames.bot;

import com.isenteam.betgames.API.InfoAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CalculCote {
	
	private InfoAPI infoC;
	
	private float ratio;
	float totRatioEq1, totRatioEq2, ratioEq1, ratioEq2, coteEq1, coteEq2;
	int denoEq1 = 5, denoEq2 =5;
	
	public CalculCote(InfoAPI info) {
		this.infoC = info;		
	}
		
	public void calcul() throws UnirestException {
		
    	infoC.PartyInfoMongo();
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
		
		coteEq1 = (float) (Math.round((1/ratioEq1) *100.0)/100.0);
		coteEq2 = (float) (Math.round((1/ratioEq2) *100.0)/100.0);
	}	
}
