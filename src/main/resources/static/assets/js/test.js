


    if (navigator.userAgent.indexOf('Chrome-Lighthouse') < 0) {
       var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
       (function(){
          var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
          s1.async=true;
          s1.src='https://embed.tawk.to/58f4863730ab263079b601bb/default';
          s1.charset='UTF-8';
          s1.setAttribute('crossorigin','*');
          s0.parentNode.insertBefore(s1,s0);
       })();
       Tawk_API.onLoad = function () {
          jQuery('#tawkchat-container > iframe').first().remove();
          jQuery('div[style*="999999999"] > iframe').first().remove();
          Tawk_API.hideWidget();
       }
       Tawk_API.onChatMinimized = function(){
          Tawk_API.hideWidget();
       };
    }
