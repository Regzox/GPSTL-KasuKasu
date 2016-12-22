class Element {
    
    constructor (id, tagName) {
        
        if (tagName !== undefined)
            this.tagName = tagName;
        else
            this.tagName = "div";
        
        this.dom = $(document.createElement(this.tagName))
            .attr("id", id);
        
    }
    
    attach (domElement) {
        this.attachedTo = domElement;
        $(this.attachedTo).append(this.dom);
    }
    
    distach () {
        $(this.attachedTo).remove();
        this.attachedTo = undefined;
    }

}