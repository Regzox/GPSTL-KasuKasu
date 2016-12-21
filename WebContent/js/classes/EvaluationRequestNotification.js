class EvaluationRequestNotification extends Element {
    
    constructor (id, tagName) {
        super(id, tagName);
        
        $(this.dom).attr("class", "evaluation-request-notification");
        
        this.cancel = new Button(id + "-cancel", Choice.CANCEL);
        $(this.cancel.dom).addClass("cancel");
        this.profile = new Profile(id + "-profile");
        this.evaluation = new EvaluationRequest(id + "-evaluation");
        
        this.cancel.attach(this.dom);
        this.profile.attach(this.dom);
        this.evaluation.attach(this.dom);   
    }
    
}