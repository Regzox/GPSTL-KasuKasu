class EvaluationRequest extends Element {
    
    constructor (id, tagName) {
        super(id, tagName);
        
        $(this.dom).attr("class", "evaluation");
        
        this.itemWrapper = new ItemWrapper(id + "-item-wrapper");
        this.evaluationWrapper = new EvaluationWrapper(id + "-evaluation-wrapper");
        this.choice = new Choice(id + "-choice");
                
        this.itemWrapper.attach(this.dom);
        this.evaluationWrapper.attach(this.dom);
        this.choice.attach(this.dom);
    }
    
}