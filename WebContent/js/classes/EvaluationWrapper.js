class EvaluationWrapper extends Element {
    
    constructor (id, tagName) {
        super(id, tagName);
        
        $(this.dom).attr("class", "evaluation-wrapper");
                
        this.dom_comment_label = $(document.createElement(this.tagName))
            .attr("id", id + "-comment-label")
            .attr("class", "comment-label");
        
        this.dom_comment = $(document.createElement("textarea"))
            .attr("rows", "3")
            .attr("id", id + "-comment")
            .attr("class", "comment")
            .attr("placeholder", "Type your comment");
        
        this.dom_mark_label = $(document.createElement(this.tagName))
            .attr("id", id + "-mark-label")
            .attr("class", "mark-label");
        
        this.starBar = new StarBar(id + "-starbar", 5);
        
        this.dom_mark = this.starBar.dom;
        $(this.dom_mark).addClass("mark");
        
        $(this.dom) .append(this.dom_comment_label)
                    .append(this.dom_comment)
                    .append(this.dom_mark_label)
                    .append(this.dom_mark);
    }
    
    commentLabel (text) {
        if (text !== undefined)
            (this.dom_comment_label).html(text);
        else
            return $(this.dom_comment_label).html();
    }
    
    comment (text) {
        if (text !== undefined)
            (this.dom_comment).val(text);
        else
            return $(this.dom_comment).val();
    }
    
    clear() {
        $ (this.dom_comment).val("");
        this.starBar.reset();
    }
    
    freeze () {
        $(this.dom_comment).attr("disabled", "disabled");
    }
    
    markLabel (text) {
        if (text !== undefined)
            (this.dom_mark_label).html(text);
        else
            return $(this.dom_mark_label).html();
    }
    
    get mark () {
        return this.starBar.mark;
    }
    
    set mark (mark) {
        return this.starBar.mark = mark;
    }
    
}