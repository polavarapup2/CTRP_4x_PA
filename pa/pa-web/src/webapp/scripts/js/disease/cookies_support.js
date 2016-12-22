
//*********************
//** Cookies Support **
//*********************

function setCookieForDays( cookieName, cookieVal, nDays ) {
	var today = new Date();
	var expires = nDays * 1000 * 60 * 60 * 24;
	var cookieExp = (new Date( today.getTime() + expires )).toGMTString();
	setCookie( cookieName, cookieVal, cookieExp );
}

function setCookie( cookieName, cookieVal, cookieExp, cookiePath, cookieDomain, cookieSecure ) {
	var cookieText = escape(cookieName) + '=' + escape(cookieVal); //escape() : Encodes the String
	cookieText += (cookieExp ? '; EXPIRES=' + cookieExp : '');
	cookieText += (cookiePath ? '; PATH=' + cookiePath : '');
	cookieText += (cookieDomain ? '; DOMAIN=' + cookieDomain : '');
	cookieText += (cookieSecure ? '; SECURE' : '');
	document.cookie = cookieText;
}

function getCookie( cookieName ) {
	var cookieVal = null;
	if(document.cookie) {
		var arr = document.cookie.split((escape(cookieName) + '=')); 
	    if(arr.length >= 2) {
	   	    var arr2 = arr[1].split(';');
		    cookieVal  = unescape(arr2[0]); 
	    }
	}
	return cookieVal;
}

function deleteCookie( cookieName ) {
	var tmp = getCookie(cookieName);
	if(tmp) { 
		setCookie(cookieName,tmp,(new Date(1))); 
	}
}

function getAndDeleteCookie( cookieName ) {
	var cookieVal = getCookie( cookieName );
	deleteCookie( cookieName );
	return cookieVal;
}

