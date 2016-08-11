!function(e){"object"==typeof exports&&"object"==typeof module?e(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],e):e(CodeMirror)}(function(e){"use strict";e.defineMode("ruby",function(e){function t(e){for(var t={},n=0,r=e.length;r>n;++n)t[e[n]]=!0;return t}function n(e,t,n){return n.tokenize.push(e),e(t,n)}function r(e,t){if(e.sol()&&e.match("=begin")&&e.eol())return t.tokenize.push(f),"comment";if(e.eatSpace())return null;var r=e.next(),i;if("`"==r||"'"==r||'"'==r)return n(a(r,"string",'"'==r||"`"==r),e,t);if("/"==r){var o=e.current().length;if(e.skipTo("/")){var l=e.current().length;e.backUp(e.current().length-o);for(var d=0;e.current().length<l;){var c=e.next();if("("==c?d+=1:")"==c&&(d-=1),0>d)break}if(e.backUp(e.current().length-o),0==d)return n(a(r,"string-2",!0),e,t)}return"operator"}if("%"==r){var k="string",h=!0;e.eat("s")?k="atom":e.eat(/[WQ]/)?k="string":e.eat(/[r]/)?k="string-2":e.eat(/[wxq]/)&&(k="string",h=!1);var m=e.eat(/[^\w\s=]/);return m?(s.propertyIsEnumerable(m)&&(m=s[m]),n(a(m,k,h,!0),e,t)):"operator"}if("#"==r)return e.skipToEnd(),"comment";if("<"==r&&(i=e.match(/^<-?[\`\"\']?([a-zA-Z_?]\w*)[\`\"\']?(?:;|$)/)))return n(u(i[1]),e,t);if("0"==r)return e.eat("x")?e.eatWhile(/[\da-fA-F]/):e.eat("b")?e.eatWhile(/[01]/):e.eatWhile(/[0-7]/),"number";if(/\d/.test(r))return e.match(/^[\d_]*(?:\.[\d_]+)?(?:[eE][+\-]?[\d_]+)?/),"number";if("?"==r){for(;e.match(/^\\[CM]-/););return e.eat("\\")?e.eatWhile(/\w/):e.next(),"string"}if(":"==r)return e.eat("'")?n(a("'","atom",!1),e,t):e.eat('"')?n(a('"',"atom",!0),e,t):e.eat(/[\<\>]/)?(e.eat(/[\<\>]/),"atom"):e.eat(/[\+\-\*\/\&\|\:\!]/)?"atom":e.eat(/[a-zA-Z$@_\xa1-\uffff]/)?(e.eatWhile(/[\w$\xa1-\uffff]/),e.eat(/[\?\!\=]/),"atom"):"operator";if("@"==r&&e.match(/^@?[a-zA-Z_\xa1-\uffff]/))return e.eat("@"),e.eatWhile(/[\w\xa1-\uffff]/),"variable-2";if("$"==r)return e.eat(/[a-zA-Z_]/)?e.eatWhile(/[\w]/):e.eat(/\d/)?e.eat(/\d/):e.next(),"variable-3";if(/[a-zA-Z_\xa1-\uffff]/.test(r))return e.eatWhile(/[\w\xa1-\uffff]/),e.eat(/[\?\!]/),e.eat(":")?"atom":"ident";if("|"!=r||!t.varList&&"{"!=t.lastTok&&"do"!=t.lastTok){if(/[\(\)\[\]{}\\;]/.test(r))return p=r,null;if("-"==r&&e.eat(">"))return"arrow";if(/[=+\-\/*:\.^%<>~|]/.test(r)){var x=e.eatWhile(/[=+\-\/*:\.^%<>~|]/);return"."!=r||x||(p="."),"operator"}return null}return p="|",null}function i(e){return e||(e=1),function(t,n){if("}"==t.peek()){if(1==e)return n.tokenize.pop(),n.tokenize[n.tokenize.length-1](t,n);n.tokenize[n.tokenize.length-1]=i(e-1)}else"{"==t.peek()&&(n.tokenize[n.tokenize.length-1]=i(e+1));return r(t,n)}}function o(){var e=!1;return function(t,n){return e?(n.tokenize.pop(),n.tokenize[n.tokenize.length-1](t,n)):(e=!0,r(t,n))}}function a(e,t,n,r){return function(a,u){var f=!1,l;for("read-quoted-paused"===u.context.type&&(u.context=u.context.prev,a.eat("}"));null!=(l=a.next());){if(l==e&&(r||!f)){u.tokenize.pop();break}if(n&&"#"==l&&!f){if(a.eat("{")){"}"==e&&(u.context={prev:u.context,type:"read-quoted-paused"}),u.tokenize.push(i());break}if(/[@\$]/.test(a.peek())){u.tokenize.push(o());break}}f=!f&&"\\"==l}return t}}function u(e){return function(t,n){return t.match(e)?n.tokenize.pop():t.skipToEnd(),"string"}}function f(e,t){return e.sol()&&e.match("=end")&&e.eol()&&t.tokenize.pop(),e.skipToEnd(),"comment"}var l=t(["alias","and","BEGIN","begin","break","case","class","def","defined?","do","else","elsif","END","end","ensure","false","for","if","in","module","next","not","or","redo","rescue","retry","return","self","super","then","true","undef","unless","until","when","while","yield","nil","raise","throw","catch","fail","loop","callcc","caller","lambda","proc","public","protected","private","require","load","require_relative","extend","autoload","__END__","__FILE__","__LINE__","__dir__"]),d=t(["def","class","case","for","while","until","module","then","catch","loop","proc","begin"]),c=t(["end","until"]),s={"[":"]","{":"}","(":")"},p;return{startState:function(){return{tokenize:[r],indented:0,context:{type:"top",indented:-e.indentUnit},continuedLine:!1,lastTok:null,varList:!1}},token:function(e,t){p=null,e.sol()&&(t.indented=e.indentation());var n=t.tokenize[t.tokenize.length-1](e,t),r,i=p;if("ident"==n){var o=e.current();n="."==t.lastTok?"property":l.propertyIsEnumerable(e.current())?"keyword":/^[A-Z]/.test(o)?"tag":"def"==t.lastTok||"class"==t.lastTok||t.varList?"def":"variable","keyword"==n&&(i=o,d.propertyIsEnumerable(o)?r="indent":c.propertyIsEnumerable(o)?r="dedent":"if"!=o&&"unless"!=o||e.column()!=e.indentation()?"do"==o&&t.context.indented<t.indented&&(r="indent"):r="indent")}return(p||n&&"comment"!=n)&&(t.lastTok=i),"|"==p&&(t.varList=!t.varList),"indent"==r||/[\(\[\{]/.test(p)?t.context={prev:t.context,type:p||n,indented:t.indented}:("dedent"==r||/[\)\]\}]/.test(p))&&t.context.prev&&(t.context=t.context.prev),e.eol()&&(t.continuedLine="\\"==p||"operator"==n),n},indent:function(t,n){if(t.tokenize[t.tokenize.length-1]!=r)return 0;var i=n&&n.charAt(0),o=t.context,a=o.type==s[i]||"keyword"==o.type&&/^(?:end|until|else|elsif|when|rescue)\b/.test(n);return o.indented+(a?0:e.indentUnit)+(t.continuedLine?e.indentUnit:0)},electricInput:/^\s*(?:end|rescue|\})$/,lineComment:"#"}}),e.defineMIME("text/x-ruby","ruby")});