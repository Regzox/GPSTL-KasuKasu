package entities;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class EvaluationResponse implements Entity {

	String senderId = null;
	String receiverId = null;
	String loanId = null;
	
	public EvaluationResponse(String senderId, String receiverId, String loanId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.loanId = loanId;
	}
	
	@Override
	public DBObject toDBObject() {
		return new BasicDBObject()
		.append("sender_id", senderId)
		.append("receiver_id", receiverId)
		.append("loan_id", loanId);
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

}
