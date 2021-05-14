package com.isenteam.betgames.bot;

import com.isenteam.betgames.API.InfoAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CalculCote {
	
	private InfoAPI infoC;
	
	private float ratio;
	private float totRatioEq1, totRatioEq2, ratioEq1, ratioEq2, coteEq1, coteEq2;
	private int denoEq1 = 5, denoEq2 = 5;
	
	public CalculCote(InfoAPI info) {
		this.infoC = info;		
	}
		
	public void calcul() throws UnirestException {
		
    	infoC.partyInfoMongo();
    	infoC.retrieveParticipantsInfo();
		for(int index = 0; index < infoC.getParticipant().length; index++)
		{
			ratio = infoC.getParticipant()[index].getRatio();
			if(index < (infoC.getParticipant().length/2))
			{
				totRatioEq1 += ratio;
				if(ratio == 0)
				{
					
					denoEq1--;
					System.out.println("deno1 " + denoEq2);
				}
			}	
			else
			{
				totRatioEq2 += ratio;
				if(ratio == 0)
				{
					
					denoEq2--;
					System.out.println("deno2 " + denoEq2);
				}
			}
		}
		
		ratioEq1 = totRatioEq1/denoEq1;
		ratioEq2 = totRatioEq2/denoEq2;
		System.out.println(ratioEq1);
		System.out.println(ratioEq2);
		
		coteEq1 = (float) (Math.round((1/ratioEq1) *100.0)/100.0);
		coteEq2 = (float) (Math.round((1/ratioEq2) *100.0)/100.0);
	}

	public float getCoteEq1() {
		return coteEq1;
	}

	public float getCoteEq2() {
		return coteEq2;
	}
}
