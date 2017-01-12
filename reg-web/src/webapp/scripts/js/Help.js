var Help = {
    popHelp: function(topic) {
        var windowUrl = Help.url;
        if (topic.length < 1) {
            topic = null;
        }
        if (topic != null) {
            windowUrl = windowUrl + topic;
        }
        window.open(windowUrl);
    }
}
