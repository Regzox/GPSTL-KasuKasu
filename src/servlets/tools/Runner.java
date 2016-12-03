package servlets.tools;

import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author AJoan
 * RUNNER means "coursier" in french
 * DUTY means "devoir","mission" in french
 * A RUNNER is ordered by a BOSS 
 * and have a DUTY to accomplish for a CUSTOMMER(request,response) 
 * who have a PACKET(epn,opn) to transit */
public class Runner extends Thread {


	private HttpServletRequest customer_request;
	private HttpServletResponse customer_response;
	private Set<String> packet_epn;
	private Set<String> packet_opn;
	private HttpServlet boss;
	
	public Runner(HttpServletRequest customer_request,
			HttpServletResponse customer_response, Set<String> packet_epn,
			Set<String> packet_opn, HttpServlet boss) {
		super();
		this.customer_request = customer_request;
		this.customer_response = customer_response;
		this.packet_epn = packet_epn;
		this.packet_opn = packet_opn;
		this.boss = boss;}
	
	
	public HttpServletRequest getCustomer_request() {return customer_request;}

	public HttpServletResponse getCustomer_response() {return customer_response;}

	public Set<String> getPacket_epn() {return packet_epn;}

	public Set<String> getPacket_opn() {return packet_opn;}

	public HttpServlet getBoss() {return boss;}

	@Override public void run() {}
}
