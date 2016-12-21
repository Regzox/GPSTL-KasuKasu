package entities;

public class Evaluation implements Entity {
	
	EvaluationRequest er = null;
	String comment = null;
	String mark = null;

	public Evaluation(EvaluationRequest er, String comment, String mark) {
		this.er = er;
		this.comment = comment;
		this.mark = mark;
	}

	public EvaluationRequest getEr() {
		return er;
	}

	public void setEr(EvaluationRequest er) {
		this.er = er;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
