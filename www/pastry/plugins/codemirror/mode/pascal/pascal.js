!function(e){"object"==typeof exports&&"object"==typeof module?e(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],e):e(CodeMirror)}(function(e){"use strict";e.defineMode("pascal",function(){function e(e){for(var t={},r=e.split(" "),n=0;n<r.length;++n)t[r[n]]=!0;return t}function t(e,t){var u=e.next();if("#"==u&&t.startOfLine)return e.skipToEnd(),"meta";if('"'==u||"'"==u)return t.tokenize=r(u),t.tokenize(e,t);if("("==u&&e.eat("*"))return t.tokenize=n,n(e,t);if(/[\[\]{}\(\),;\:\.]/.test(u))return null;if(/\d/.test(u))return e.eatWhile(/[\w\.]/),"number";if("/"==u&&e.eat("/"))return e.skipToEnd(),"comment";if(a.test(u))return e.eatWhile(a),"operator";e.eatWhile(/[\w\$_]/);var f=e.current();return o.propertyIsEnumerable(f)?"keyword":i.propertyIsEnumerable(f)?"atom":"variable"}function r(e){return function(t,r){for(var n=!1,o,i=!1;null!=(o=t.next());){if(o==e&&!n){i=!0;break}n=!n&&"\\"==o}return(i||!n)&&(r.tokenize=null),"string"}}function n(e,t){for(var r=!1,n;n=e.next();){if(")"==n&&r){t.tokenize=null;break}r="*"==n}return"comment"}var o=e("and array begin case const div do downto else end file for forward integer boolean char function goto if in label mod nil not of or packed procedure program record repeat set string then to type until var while with"),i={"null":!0},a=/[+\-*&%=<>!?|\/]/;return{startState:function(){return{tokenize:null}},token:function(e,r){if(e.eatSpace())return null;var n=(r.tokenize||t)(e,r);return"comment"==n||"meta"==n?n:n},electricChars:"{}"}}),e.defineMIME("text/x-pascal","pascal")});