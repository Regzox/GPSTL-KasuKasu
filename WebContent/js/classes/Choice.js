class Choice extends Element {
                
    static get CANCEL() { return "&#10006;"; }
    static get VALIDATE() { return "&#10004;"; }

    constructor (id, tagName) {
        super(id, tagName);
        
        $(this.dom).attr("class", "choice");
        
        this.cancel = new Button(id + "-cancel", Choice.CANCEL);
        this.validate = new Button(id + "-validate", Choice.VALIDATE);

        $(this.cancel.dom).addClass("cancel");
        $(this.validate.dom).addClass("validate");
        
        $(this.dom)
            .append(this.cancel.dom)
            .append(this.validate.dom);
    }

}