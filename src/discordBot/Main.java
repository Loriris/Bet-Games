package discordBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main {
	//creation du jda
	public static JDA jda;
	
	//prefix à utiliser pour que le bot reconnaisse qu'on lui parle
	public static String prefix = "#";
	
	public static int getA() 
	{
		return 2; //cote_equipe_A
	}
	public static int getB() 
	{
		return 5; //cote_equipe_B
	}
	

	
	public static void main(String[] args) throws LoginException, InterruptedException
	{
		//crée le bot avec le token adapté		
		jda = JDABuilder.createDefault("NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ")
				.setActivity(Activity.watching("Ready to take bet"))
				.build()
				.awaitReady(); //This method will block until JDA has reached the status JDA.Status.CONNECTED.

		//appel de commande
		jda.addEventListener(new Commands());
		jda.getPresence().setStatus(OnlineStatus.IDLE);
	
	
	}
}
