class LoanMark {
                
    constructor (id) {
        this._id = id;
        this.cancel = new Button(id + "-cancel", "&#10006;");
        this.profile = new Profile(id + "-profile");
        this.starBar = new StarBar(id + "-starBar", 5);
        this.comment = new Comment(id + "-comment", "Blabla");
        this.choice = new Choice(id + "-choice");

        this.dom = $
            (document.createElement("loan-mark"))
            .append(this.cancel.dom)
            .append(this.profile.dom)
            .append(this.starBar.dom)
            .append(this.comment.dom)
            .append(this.choice.dom)
            .attr("id", id);

        this.profile.image("http://vignette2.wikia.nocookie.net/hmwikia/images/4/4d/Harvest_Moon_Magical_Melody_Cow.png/revision/latest?cb=20151001222457");
        this.profile.imageDimensions(100, 100);
        this.profile.idLabel("Id");
        this.profile.id("xxxxxx");
        this.profile.firstnameLabel("Prénom");
        this.profile.firstname("Jean");
        this.profile.lastnameLabel("Nom");
        this.profile.lastname("Dupond");
    }
    
}