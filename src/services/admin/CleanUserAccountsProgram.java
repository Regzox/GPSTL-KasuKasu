package services.admin;

import org.json.JSONException;
import org.json.JSONObject;

import dao.users.UserDao;
import utils.Tools;

/**
 * @author AJoan
 * Cron Program to remove non-confirmed users accounts from users database */
public class CleanUserAccountsProgram {

	private static Thread runningCUA=null;
	private static int nbmin=Integer.MAX_VALUE;


	/**Stop the CUAprogram
	 * @return
	 * @throws JSONException */
	public static JSONObject stop() throws JSONException {
		if(runningCUA==null)
			return Tools.serviceRefused("Come on dude ! you're trying to stop nonexistent CUAProgram."
					+ " No CUAProgram started for now.",-1);
		runningCUA.interrupt();runningCUA=null;nbmin=Integer.MAX_VALUE;
		return Tools.serviceMessage("Ok, Be aware you just stopped the CUAProgram !");}


	/**Restart the CUAprogram 
	 * @param nbmin
	 * @return
	 * @throws JSONException */
	public static JSONObject restart(int nbmin) throws JSONException {
		if(runningCUA==null) return start(nbmin);
		else {stop();return start(nbmin);}}


	/**Start the CUAprogram
	 * @param nbmin
	 * @return
	 * @throws JSONException */
	private static JSONObject start(int nbmin) throws JSONException{
		if(runningCUA!=null){
			return Tools.serviceRefused("Be Carefull there : an CUAProgram is already in progress..",-1);}
		CleanUserAccountsProgram.nbmin=nbmin;
		(runningCUA=new Thread(){
			@Override
			public void run() {
				while(true)
					try {System.out.println("cua ikite");
						UserDao.deleteUnconfirmedAccounts();sleep(1000*60*CleanUserAccountsProgram.nbmin);}
				//TODO on stop Exception in thread "Thread-6" java.lang.IllegalArgumentException: timeout value is negative why?
				catch (InterruptedException e) {System.out.println("cua is now interrupted");}//Do Nothing but warn
			}}).start(); //1000 milliseconds is one second.
		return Tools.serviceMessage("CUAProgram has started and refresh 's frequency is "+CleanUserAccountsProgram.nbmin+" minute(s).");}

}