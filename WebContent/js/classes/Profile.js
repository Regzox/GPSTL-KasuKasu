class Profile extends Element {
                
    constructor (id, tagName) {
        super(id, tagName);

        $(this.dom).attr("class", "profile");
        
        this.dom_image_wrapper = $(document.createElement(this.tagName))
            .attr("id", id + "-image-wrapper")
            .attr("class", "image-wrapper");
        
        this.dom_image = $(document.createElement("img"))
            .attr("id", id + "-image")
            .attr("class", "image");

        this.dom_id_wrapper = $(document.createElement(this.tagName))
            .attr("id", id + "-id-wrapper")
            .attr("class", "id-wrapper");
        
        this.dom_id_label = $(document.createElement(this.tagName))
            .attr("id", id + "-id-label")
            .attr("class", "id-label");
        
        this.dom_id = $(document.createElement(this.tagName))
            .attr("id", id + "-id")
            .attr("class", "id");
        
        this.dom_firstname_wrapper = $(document.createElement(this.tagName))
            .attr("id", id + "-firstname-wrapper")
            .attr("class", "firstname-wrapper");
        
        this.dom_firstname_label = $(document.createElement(this.tagName))
            .attr("id", id + "-firstname-label")
            .attr("class", "firstname-label");
        
        this.dom_firstname = $(document.createElement(this.tagName))
            .attr("id", id + "-firstname")
            .attr("class", "firstname");
        
        this.dom_lastname_wrapper = $(document.createElement(this.tagName))    
            .attr("id", id + "-lastname-wrapper")
            .attr("class", "lastname-wrapper");
        
        this.dom_lastname_label = $(document.createElement(this.tagName))
            .attr("id", id + "-lastname-label")
            .attr("class", "lastname-label");
        
        this.dom_lastname = $(document.createElement(this.tagName))
            .attr("id", id + "-lastname")
            .attr("class", "lastname");
        
        $(this.dom) .append(this.dom_image_wrapper)
                    .append(this.dom_id_wrapper)
                    .append(this.dom_firstname_wrapper)
                    .append(this.dom_lastname_wrapper);
        
        $(this.dom_image_wrapper)   .append(this.dom_image);
        
        $(this.dom_id_wrapper)  .append(this.dom_id_label)
                                .append(this.dom_id);
        
        $(this.dom_firstname_wrapper)   .append(this.dom_firstname_label)
                                        .append(this.dom_firstname);
        
        $(this.dom_lastname_wrapper)    .append(this.dom_lastname_label)
                                        .append(this.dom_lastname);
        
    }
    
    /* To attach this profile dom représentation to another dom element */
    
    attachTo (domElement) {
        $(domElement)   .append(this.dom);
    }
    
    /* To get/set the image label */
    
    imageLabel (text) {
        if (text !== undefined)
            $(this.dom_id_label).html(text);
        else
            return $(this.dom_id_label).html();
    }
    
    /* To get/set image source */
    
    image (src) {
        if (src != undefined)
            $(this.dom_image).attr("src", src);
        else
            return $(this.dom_image).attr("src"); 
    }
    
    /* To set image dimensions */
    
    imageDimensions (width, height) {
        if (width !== undefined && height !== undefined) {
            if (!isNaN(width))
                $(this.dom_image).width(width);
            if (!isNaN(height))
                $(this.dom_image).height(height);
        }
    }
    
    /* To get/set id label */
    
    idLabel (text) {
        if (text !== undefined)
            $(this.dom_id_label).html(text);
        else
            return $(this.dom_id_label).html();
    }
    
    /* To get/set firstname */
    
    id (text) {
        if (text !== undefined)
            $(this.dom_id).html(text);
        else
            return $(this.dom_id).html();
    }

    /* To get/set firstname label */
    
    firstnameLabel (text) {
        if (text !== undefined)
            $(this.dom_firstname_label).html(text);
        else
            return $(this.dom_firstname_label).html();
    }
    
    /* To get/set firstname */
    
    firstname (text) {
        if (text !== undefined)
            $(this.dom_firstname).html(text);
        else
            return $(this.dom_firstname).html();
    }
    
    /* To get/set lastname */

    lastnameLabel (text) {
        if (text !== undefined)
            $(this.dom_lastname_label).html(text);
        else
            return $(this.dom_lastname_label).html();
    }
    
    /* To get/set lastname */

    lastname (text) {
        if (text !== undefined)
            $(this.dom_lastname).html(text);
        else
            return $(this.dom_lastname).html();
    }

}