class Profile {
                
    constructor (id) {
        this.dom_img = $    
            (document.createElement("img"))
            .attr("id", id + "-image");

        this.dom_firstname = $    
            (document.createElement("firstname"))
            .attr("id", id + "-firstname");

        this.dom_lastname = $    
            (document.createElement("lastname"))
            .attr("id", id + "-lastname");

        this.dom = $
            (document.createElement("profile"))
            .attr("id", id)
            .append(this.dom_img)
            .append(this.dom_firstname)
            .append(this.dom_lastname);
    }

    image(src) {
        if (src != undefined)
            $(this.dom_img).attr("src", src);
        else
            return $(this.dom_img).attr("src"); 
    }

    firstname(text) {
        $(this.dom_firstname).html(text);
    }

    lastname(text) {
        $(this.dom_lastname).html(text);
    }

}