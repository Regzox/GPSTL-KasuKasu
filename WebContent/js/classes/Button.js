class Button {
                
    constructor (id, html, tag) {
         this.dom = $
            (document.createElement("button"))
            .attr("id", id)
            .html(html);
    }

}