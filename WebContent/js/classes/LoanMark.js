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

        this.profile.image("");
        this.profile.firstname("Jean");
        this.profile.lastname("Dupond");
    }
    
}