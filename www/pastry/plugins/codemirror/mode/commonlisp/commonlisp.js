!function(t){"object"==typeof exports&&"object"==typeof module?t(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],t):t(CodeMirror)}(function(t){"use strict";t.defineMode("commonlisp",function(t){function e(t){for(var e;e=t.next();)if("\\"==e)t.next();else if(!u.test(e)){t.backUp(1);break}return t.current()}function n(t,n){if(t.eatSpace())return a="ws",null;if(t.match(c))return"number";var u=t.next();if("\\"==u&&(u=t.next()),'"'==u)return(n.tokenize=r)(t,n);if("("==u)return a="open","bracket";if(")"==u||"]"==u)return a="close","bracket";if(";"==u)return t.skipToEnd(),a="ws","comment";if(/['`,@]/.test(u))return null;if("|"==u)return t.skipTo("|")?(t.next(),"symbol"):(t.skipToEnd(),"error");if("#"==u){var u=t.next();return"["==u?(a="open","bracket"):/[+\-=\.']/.test(u)?null:/\d/.test(u)&&t.match(/^\d*#/)?null:"|"==u?(n.tokenize=o)(t,n):":"==u?(e(t),"meta"):"error"}var s=e(t);return"."==s?null:(a="symbol","nil"==s||"t"==s||":"==s.charAt(0)?"atom":"open"==n.lastType&&(i.test(s)||l.test(s))?"keyword":"&"==s.charAt(0)?"variable-2":"variable")}function r(t,e){for(var r=!1,o;o=t.next();){if('"'==o&&!r){e.tokenize=n;break}r=!r&&"\\"==o}return"string"}function o(t,e){for(var r,o;r=t.next();){if("#"==r&&"|"==o){e.tokenize=n;break}o=r}return a="ws","comment"}var i=/^(block|let*|return-from|catch|load-time-value|setq|eval-when|locally|symbol-macrolet|flet|macrolet|tagbody|function|multiple-value-call|the|go|multiple-value-prog1|throw|if|progn|unwind-protect|labels|progv|let|quote)$/,l=/^with|^def|^do|^prog|case$|^cond$|bind$|when$|unless$/,c=/^(?:[+\-]?(?:\d+|\d*\.\d+)(?:[efd][+\-]?\d+)?|[+\-]?\d+(?:\/[+\-]?\d+)?|#b[+\-]?[01]+|#o[+\-]?[0-7]+|#x[+\-]?[\da-f]+)/,u=/[^\s'`,@()\[\]";]/,a;return{startState:function(){return{ctx:{prev:null,start:0,indentTo:0},lastType:null,tokenize:n}},token:function(e,n){e.sol()&&"number"!=typeof n.ctx.indentTo&&(n.ctx.indentTo=n.ctx.start+1),a=null;var r=n.tokenize(e,n);return"ws"!=a&&(null==n.ctx.indentTo?"symbol"==a&&l.test(e.current())?n.ctx.indentTo=n.ctx.start+t.indentUnit:n.ctx.indentTo="next":"next"==n.ctx.indentTo&&(n.ctx.indentTo=e.column()),n.lastType=a),"open"==a?n.ctx={prev:n.ctx,start:e.column(),indentTo:null}:"close"==a&&(n.ctx=n.ctx.prev||n.ctx),r},indent:function(t,e){var n=t.ctx.indentTo;return"number"==typeof n?n:t.ctx.start+1},closeBrackets:{pairs:'()[]{}""'},lineComment:";;",blockCommentStart:"#|",blockCommentEnd:"|#"}}),t.defineMIME("text/x-common-lisp","commonlisp")});