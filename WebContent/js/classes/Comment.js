class Comment {
                
    constructor (id, text) {
        this._id = id;

        this.dom = $(document.createElement("input"))
        .attr("id", id)
        .attr("type", "textarea")
        .val(text)
        .addClass("comment");
    }

    text() {
        return $(this.dom).val();
    }

    clear() {
        $ (this.dom).val("");
    }
}