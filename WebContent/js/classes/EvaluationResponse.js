class EvaluationResponse extends Element {
    
    constructor (id, tagName) {
        super(id, tagName);
        
        $(this.dom).attr("class", "evaluation");
        
        this.itemWrapper = new ItemWrapper(id + "-item-wrapper");
        this.evaluationWrapper = new EvaluationWrapper(id + "-evaluation-wrapper");
        
        
        this.evaluationWrapper.starBar.freeze();
        this.itemWrapper.attach(this.dom);
        this.evaluationWrapper.attach(this.dom);
    }
    
}