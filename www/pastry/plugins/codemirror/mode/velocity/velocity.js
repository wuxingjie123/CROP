!function(e){"object"==typeof exports&&"object"==typeof module?e(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],e):e(CodeMirror)}(function(e){"use strict";e.defineMode("velocity",function(){function e(e){for(var t={},n=e.split(" "),r=0;r<n.length;++r)t[n[r]]=!0;return t}function t(e,t,n){return t.tokenize=n,n(e,t)}function n(e,n){var f=n.beforeParams;n.beforeParams=!1;var c=e.next();if("'"==c&&n.inParams)return n.lastTokenWasBuiltin=!1,t(e,n,r(c));if('"'!=c){if(/[\[\]{}\(\),;\.]/.test(c))return"("==c&&f?n.inParams=!0:")"==c&&(n.inParams=!1,n.lastTokenWasBuiltin=!0),null;if(/\d/.test(c))return n.lastTokenWasBuiltin=!1,e.eatWhile(/[\w\.]/),"number";if("#"==c&&e.eat("*"))return n.lastTokenWasBuiltin=!1,t(e,n,i);if("#"==c&&e.match(/ *\[ *\[/))return n.lastTokenWasBuiltin=!1,t(e,n,a);if("#"==c&&e.eat("#"))return n.lastTokenWasBuiltin=!1,e.skipToEnd(),"comment";if("$"==c)return e.eatWhile(/[\w\d\$_\.{}]/),l&&l.propertyIsEnumerable(e.current())?"keyword":(n.lastTokenWasBuiltin=!0,n.beforeParams=!0,"builtin");if(u.test(c))return n.lastTokenWasBuiltin=!1,e.eatWhile(u),"operator";e.eatWhile(/[\w\$_{}@]/);var k=e.current();return o&&o.propertyIsEnumerable(k)?"keyword":s&&s.propertyIsEnumerable(k)||e.current().match(/^#@?[a-z0-9_]+ *$/i)&&"("==e.peek()&&(!s||!s.propertyIsEnumerable(k.toLowerCase()))?(n.beforeParams=!0,n.lastTokenWasBuiltin=!1,"keyword"):n.inString?(n.lastTokenWasBuiltin=!1,"string"):e.pos>k.length&&"."==e.string.charAt(e.pos-k.length-1)&&n.lastTokenWasBuiltin?"builtin":(n.lastTokenWasBuiltin=!1,null)}return n.lastTokenWasBuiltin=!1,n.inString?(n.inString=!1,"string"):n.inParams?t(e,n,r(c)):void 0}function r(e){return function(t,r){for(var i=!1,a,o=!1;null!=(a=t.next());){if(a==e&&!i){o=!0;break}if('"'==e&&"$"==t.peek()&&!i){r.inString=!0,o=!0;break}i=!i&&"\\"==a}return o&&(r.tokenize=n),"string"}}function i(e,t){for(var r=!1,i;i=e.next();){if("#"==i&&r){t.tokenize=n;break}r="*"==i}return"comment"}function a(e,t){for(var r=0,i;i=e.next();){if("#"==i&&2==r){t.tokenize=n;break}"]"==i?r++:" "!=i&&(r=0)}return"meta"}var o=e("#end #else #break #stop #[[ #]] #{end} #{else} #{break} #{stop}"),s=e("#if #elseif #foreach #set #include #parse #macro #define #evaluate #{if} #{elseif} #{foreach} #{set} #{include} #{parse} #{macro} #{define} #{evaluate}"),l=e("$foreach.count $foreach.hasNext $foreach.first $foreach.last $foreach.topmost $foreach.parent.count $foreach.parent.hasNext $foreach.parent.first $foreach.parent.last $foreach.parent $velocityCount $!bodyContent $bodyContent"),u=/[+\-*&%=<>!?:\/|]/;return{startState:function(){return{tokenize:n,beforeParams:!1,inParams:!1,inString:!1,lastTokenWasBuiltin:!1}},token:function(e,t){return e.eatSpace()?null:t.tokenize(e,t)},blockCommentStart:"#*",blockCommentEnd:"*#",lineComment:"##",fold:"velocity"}}),e.defineMIME("text/velocity","velocity")});