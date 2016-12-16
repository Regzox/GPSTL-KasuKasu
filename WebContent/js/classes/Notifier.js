class Notifier {
    
    constructor (id) {
        this._id = id;
        this._dom = document.createElement("notifier");
        
        $(this._dom).attr("id", id);
    }
    
    push (dom) {
        $(this._dom).append(dom);
    }
    
    pop () {
        $(this._dom).children()[$(this._dom).children().length - 1].remove();
    }
    
    remove(index) {
        if (index < $(this._dom).children().length)
            $(this._dom).children()[index - 1].remove();
    }
    
}