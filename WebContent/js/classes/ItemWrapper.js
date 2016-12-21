/**********************************************

    Item wrapper with title and description

***********************************************/

class ItemWrapper {
    
    constructor (id, tagName) {
        
        if (tagName !== undefined)
            this.tagName = tagName;
        else
            this.tagName = "div";
        
        this.dom = $(document.createElement(this.tagName))
            .attr("id", id)
            .attr("class", "item-wrapper");
        
        this.dom_id_label = $(document.createElement(this.tagName))
            .attr("id", id + "-id-label")
            .attr("class", "id-label");
        
        this.dom_id = $(document.createElement(this.tagName))
            .attr("id", id + "-id")
            .attr("class", "id");
        
        this.dom_title_label = $(document.createElement(this.tagName))
            .attr("id", id + "-title-label")
            .attr("class", "title-label");
        
        this.dom_title = $(document.createElement(this.tagName))
            .attr("id", id + "-title")
            .attr("class", "title");
        
        this.dom_description_label = $(document.createElement(this.tagName))
            .attr("id", id + "-description-label")
            .attr("class", "description-label");
        
        this.dom_description = $(document.createElement(this.tagName))
            .attr("id", id + "-description")
            .attr("class", "description");
        
        $(this.dom) .append(this.dom_id_label)
                    .append(this.dom_id)
                    .append(this.dom_title_label)
                    .append(this.dom_title)
                    .append(this.dom_description_label)
                    .append(this.dom_description);
    }
    
    attach (domElement) {
        this.attachedTo = domElement;
        $(this.attachedTo).append(this.dom);
    }
    
    distach () {
        $(this.attachedTo).remove();
        this.attachedTo = undefined;
    }
    
    idLabel (text) {
        if (text !== undefined)
            $(this.dom_id_label).html(text);
        else
            return $(this.dom_id_label).html();
    }
    
    id (text) {
        if (text !== undefined)
            $(this.dom_id).html(text);
        else
            return $(this.dom_id).html();
    }
    
    titleLabel (text) {
        if (text !== undefined)
            (this.dom_title_label).html(text);
        else
            return $(this.dom_title_label).html();
    }
    
    title (text) {
        if (text !== undefined)
            (this.dom_title).html(text);
        else
            return $(this.dom_title).html();
    }
    
    descriptionLabel (text) {
        if (text !== undefined)
            (this.dom_description_label).html(text);
        else
            return $(this.dom_description_label).html();
    }
    
    description (text) {
        if (text !== undefined)
            (this.dom_description).html(text);
        else
            return $(this.dom_description).html();
    }
    
}