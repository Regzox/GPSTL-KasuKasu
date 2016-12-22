package services.admin;

import org.json.JSONException;
import org.json.JSONObject;

import dao.items.ItemsMR;
import utils.Tools;

/**
 * @author AJoan
 * Cron Program to refresh the tf-idf tables for MapReduce on items db */
public class ItemsMRProgram {
	
	private static Thread runningMR=null;
	private static int nbmin=Integer.MAX_VALUE;
	
	
 	/**Stop refreshing tf-idf cron job
 	 * @return
 	 * @throws JSONException */
	public static JSONObject stop() throws JSONException {
		if(runningMR==null)
			return Tools.serviceRefused("Come on dude ! you're trying to stop nonexistent MRProgram."
							+ " No MRProgram started for now.",-1);
			runningMR.interrupt();runningMR=null;nbmin=Integer.MAX_VALUE;
			return Tools.serviceMessage("Ok, Be aware you just stopped MRProgram !");}
	
	
	/**Restart refreshing tf-idf cron job
	 * @param nbmin
	 * @return
	 * @throws JSONException */
	public static JSONObject restart(int nbmin) throws JSONException {
		if(runningMR==null) return start(nbmin);
		else {stop();return start(nbmin);}}
	
	
	/**Start refreshing tf-idf cron job
	 * @param nbmin
	 * @return
	 * @throws JSONException */
	private static JSONObject start(int nbmin) throws JSONException{
		if(runningMR!=null){
			return Tools.serviceRefused("Be Carefull there : an MRProgram is already in progress..",-1);}
		ItemsMRProgram.nbmin=nbmin;
		(runningMR=new Thread(){
				@Override
				public void run() {
					while(true)
						try {ItemsMR.updateTFDF();sleep(1000*60*ItemsMRProgram.nbmin);}
					//TODO on stop Exception in thread "Thread-6" java.lang.IllegalArgumentException: timeout value is negative why?
					catch (JSONException e) {System.out.println("mr is now interrupted");}//Do Nothing but warn
					catch (InterruptedException e) {System.out.println("mr is now interrupted");}//Do Nothing but warn
					}}).start(); //1000 milliseconds is one second.
		return Tools.serviceMessage("MRProgram has started and refresh 's frequency is "+ItemsMRProgram.nbmin+" minute(s).");}

}