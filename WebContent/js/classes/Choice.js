class Choice {
                
    static get CANCEL() { return "&#10006;"; }
    static get VALIDATE() { return "&#10004;"; }

    constructor (id) {
        this.dom = $
            (document.createElement("choice"))
            .attr("id", id);
        this.cancel = new Button(id + "-cancel", Choice.CANCEL);
        this.validate = new Button(id + "-validate", Choice.VALIDATE);

        $(this.cancel.dom).addClass("cancel");
        $(this.validate.dom).addClass("validate");
        
        $(this.dom)
            .append(this.cancel.dom)
            .append(this.validate.dom);
    }

}