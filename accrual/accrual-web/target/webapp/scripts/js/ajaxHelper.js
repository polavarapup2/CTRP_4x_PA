// Calls an Ajax.Updater
// div is the div that will be reloaded. If div i null, a Ajax.Request is used instead of an Ajax.Updater
// url is the url to call
// params is a javascript hash containing the request parameters.
// providedOptions the options object to use (This object is optional and useful for specifying call back functions)
function callAjaxPost(div, url, params, providedOptions) {
    var options = (providedOptions) ? providedOptions : {};
    options.asynchronous = true;
    options.evalScripts = false;
    options.method = 'post';
    options.parameters = params;
    if (div == null) {
        return new Ajax.Request(url, options);
    }
    return new Ajax.Updater(div, url, options);
}