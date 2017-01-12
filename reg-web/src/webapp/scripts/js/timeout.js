/*
 * timeout-dialog.js v1.0.1, 01-03-2012
 * 
 * @author: Rodrigo Neri (@rigoneri)
 * 
 * (The MIT License)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE. 
 */

String.prototype.format = function() {
  var s = this,
      i = arguments.length;

  while (i--) {
    s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
  }
  return s;
};

var matched, browser;

jQuery.uaMatch = function( ua ) {
    ua = ua.toLowerCase();

    var match = /(chrome)[ \/]([\w.]+)/.exec( ua ) ||
        /(webkit)[ \/]([\w.]+)/.exec( ua ) ||
        /(opera)(?:.*version|)[ \/]([\w.]+)/.exec( ua ) ||
        /(msie) ([\w.]+)/.exec( ua ) ||
        ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec( ua ) ||
        [];

    return {
        browser: match[ 1 ] || "",
        version: match[ 2 ] || "0"
    };
};

matched = jQuery.uaMatch( navigator.userAgent );
browser = {};

if ( matched.browser ) {
    browser[ matched.browser ] = true;
    browser.version = matched.version;
}

// Chrome is Webkit, but Webkit is also Safari.
if ( browser.chrome ) {
    browser.webkit = true;
} else if ( browser.webkit ) {
    browser.safari = true;
}

jQuery.browser = browser;

jQuery(function() {
  jQuery.timeoutDialog = function(options) {

    var settings = {
      timeout: 7130,
      countdown: 60,
      title : 'Your session is about to expire!',
      message : 'You will be logged out in {0} seconds.',
      question: 'Do you want to stay signed in?',
      keep_alive_button_text: 'Yes, Keep me signed in',
      sign_out_button_text: 'No, Sign me out',
      keep_alive_url: '/registry/protected/keepalive.action',
      logout_url: '/registry/logout.action',
      logout_redirect_url: '/registry/logout.action',
      restart_on_yes: true,
      dialog_width: 650
    }    

    settings = jQuery.extend(settings, options);

    var TimeoutDialog = {
      init: function () {
        this.setupDialogTimer();
      }, 

      setupDialogTimer: function() {
        var self = this;
        window.setTimeout(function() {
           self.setupDialog();
        }, (settings.timeout - settings.countdown) * 1000);
      },

      setupDialog: function() {
        var self = this;
        self.destroyDialog();
        if(window.supressTimeoutPopup) {
        	return;
        }

        jQuery('<div id="timeout-dialog">' +
            '<p id="timeout-message">' + settings.message.format('<span id="timeout-countdown">' + settings.countdown + '</span>') + '</p>' + 
            '<p id="timeout-question">' + settings.question + '</p>' +
          '</div>')
        .appendTo('body')
        .dialog({
          modal: true,
          width: settings.dialog_width,
          minHeight: 'auto',
          autoOpen: true,
          zIndex: 10000,
          closeOnEscape: false,
          draggable: false,
          resizable: false,
          dialogClass: 'timeout-dialog',
          title: settings.title,
          buttons : {
            'keep-alive-button' : { 
              text: settings.keep_alive_button_text,
              id: "timeout-keep-signin-btn",
              click: function() {
                self.keepAlive();
              }
            },
            'sign-out-button' : {
              text: settings.sign_out_button_text,
              id: "timeout-sign-out-button",
              click: function() {
                self.signOut(true);
              }
            }
          }
        });

        self.startCountdown();
      },

      destroyDialog: function() {
        if (jQuery("#timeout-dialog").length) {
          jQuery(this).dialog("close");
          jQuery('#timeout-dialog').remove();
        }
      },

      startCountdown: function() {
        var self = this,
            counter = settings.countdown;

        this.countdown = window.setInterval(function() {
          counter -= 1;
          jQuery("#timeout-countdown").html(counter);

          if (counter <= 0) {
            window.clearInterval(self.countdown);
            self.signOut(false);
          }

        }, 1000);
      },

      keepAlive: function() {
        var self = this;
        this.destroyDialog();
        window.clearInterval(this.countdown);

        jQuery.get(settings.keep_alive_url, function(data) {
            if (settings.restart_on_yes) {
              self.setupDialogTimer();
            }
        });
      },

      signOut: function(is_forced) {
        var self = this;
        this.destroyDialog();

        if (settings.logout_url != null) {
            jQuery.post(settings.logout_url, function(data){
                self.redirectLogout(is_forced);
            });
        }
        else {
            self.redirectLogout(is_forced);
        }
      }, 

      redirectLogout: function(is_forced){
        var target = settings.logout_redirect_url + '?next=' + encodeURIComponent(window.location.pathname + window.location.search);
        if (!is_forced)
          target += '&timeout=t';
        window.location = target;
      }
    };

    TimeoutDialog.init();
  };
});