class EvaluationResponseNotification extends Element {
    
    constructor (id, tagName) {
        super(id, tagName);
        
        $(this.dom).attr("class", "evaluation-response-notification");
        
        this.cancel = new Button(id + "-cancel", Choice.CANCEL);
        $(this.cancel.dom).addClass("cancel");
        this.profile = new Profile(id + "-profile");
        this.evaluation = new EvaluationResponse(id + "-evaluation");
        this.evaluation.evaluationWrapper.freeze();
        
        this.cancel.attach(this.dom);
        this.profile.attach(this.dom);
        this.evaluation.attach(this.dom);
    }
    
}