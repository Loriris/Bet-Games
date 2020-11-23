package discordBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class Main {
	public static JDA jda;
	
	public static String prefix = "#";
	
	public static void main(String[] args) throws LoginException
	{
		/*
		jda = new JDABuilder(AccountType.BOT).setToken("NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ").build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		*/
		
		jda = JDABuilder.createDefault("NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ").build();
		jda.addEventListener(new Commands());
	
	}
}
