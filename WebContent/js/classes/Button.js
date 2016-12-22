class Button extends Element {
                
    constructor (id, html) {
        super(id, "button");
        
        $(this.dom).html(html);
    }

}