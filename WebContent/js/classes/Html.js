class Html {
    static encode(value) {
        return $(document.createElement("div")).text(value).html();
    }

    static decode(value) {
        return $(document.createElement("div")).html(value).text();
    }
}