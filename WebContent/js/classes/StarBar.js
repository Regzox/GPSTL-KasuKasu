class StarBar {
                
    constructor(id, starCount) {
        this.dom = jQuery
            (document.createElement("star-bar"))
            .attr("id", id);
        this.stars = [];
        for (let i = 0; i < starCount; i++) {
            this.stars[i] = new Star("star-" + (i + 1));

            if (!i)
                this.stars[i].on();

            let _starBar = this;
            let _stars = this.stars;
            let _star = this.stars[i];
            let _i = i;

            $(this.stars[i].dom).click( function() {

                for (var star of _stars)
                    star.off();

                for (var i = 0; i <= _i; i++)
                    _stars[i].on();

                console.log(_starBar.mark);
            });
            
            $(this.stars[i].dom)
                .css("width", 100/starCount + "%")
                .css("display", "inline-block");
            $(this.dom).append(this.stars[i].dom);
        }
    }

    get mark () {
        var result = 0;
        for (var star of this.stars)
            if (star.isToggled())
                result += 1;
        return result;
    }
    
    reset () {
        for (var star of this.stars)
            star.off();
        this.stars[0].on();
    }
}