class Star {
                
    static get BLACK() { return "&#9733;"; }
    static get WHITE() { return "&#9734;"; }

    constructor(id) {
        this.dom = jQuery
            (document.createElement("star"))
            .attr("id", id)
            .html(Star.WHITE);
    }

    toggle() {
        if ($(this.dom).html() == Html.decode(Star.BLACK))
            $(this.dom).html(Star.WHITE);
        else
            $(this.dom).html(Star.BLACK);
    }

    on () {
        $(this.dom).html(Star.BLACK);
    }

    off () {
        $(this.dom).html(Star.WHITE);
    }

    isToggled() {
        return $(this.dom).html() == Html.decode(Star.BLACK)
    }

}