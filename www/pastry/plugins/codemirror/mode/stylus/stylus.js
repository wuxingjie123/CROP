!function(e){"object"==typeof exports&&"object"==typeof module?e(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],e):e(CodeMirror)}(function(e){"use strict";function t(e){return e=e.sort(function(e,t){return t>e}),new RegExp("^(("+e.join(")|(")+"))\\b")}function r(e){for(var t={},r=0;r<e.length;++r)t[e[r]]=!0;return t}function i(e){return e.replace(/[-[\]{}()*+?.,\\^$|#\s]/g,"\\$&")}e.defineMode("stylus",function(e){function g(e,t){if(V=e.string.match(/(^[\w-]+\s*=\s*$)|(^\s*[\w-]+\s*=\s*[\w-])|(^\s*(\.|#|@|\$|\&|\[|\d|\+|::?|\{|\>|~|\/)?\s*[\w-]*([a-z0-9-]|\*|\/\*)(\(|,)?)/),t.context.line.firstWord=V?V[0].replace(/^\s*/,""):"",t.context.line.indent=e.indentation(),te=e.peek(),e.match("//"))return e.skipToEnd(),["comment","comment"];if(e.match("/*"))return t.tokenize=f,f(e,t);if('"'==te||"'"==te)return e.next(),t.tokenize=k(te),t.tokenize(e,t);if("@"==te)return e.next(),e.eatWhile(/[\w\\-]/),["def",e.current()];if("#"==te){if(e.next(),e.match(/^[0-9a-f]{6}|[0-9a-f]{3}/i))return["atom","atom"];if(e.match(/^[a-z][\w-]*/i))return["builtin","hash"]}return e.match(K)?["meta","vendor-prefixes"]:e.match(/^-?[0-9]?\.?[0-9]/)?(e.eatWhile(/[a-z%]/i),["number","unit"]):"!"==te?(e.next(),[e.match(/^(important|optional)/i)?"keyword":"operator","important"]):"."==te&&e.match(/^\.[a-z][\w-]*/i)?["qualifier","qualifier"]:e.match(F)?("("==e.peek()&&(t.tokenize=w),["property","word"]):e.match(/^[a-z][\w-]*\(/i)?(e.backUp(1),["keyword","mixin"]):e.match(/^(\+|-)[a-z][\w-]*\(/i)?(e.backUp(1),["keyword","block-mixin"]):e.string.match(/^\s*&/)&&e.match(/^[-_]+[a-z][\w-]*/)?["qualifier","qualifier"]:e.match(/^(\/|&)(-|_|:|\.|#|[a-z])/)?(e.backUp(1),["variable-3","reference"]):e.match(/^&{1}\s*$/)?["variable-3","reference"]:e.match(G)?["operator","operator"]:e.match(/^\$?[-_]*[a-z0-9]+[\w-]*/i)?e.match(/^(\.|\[)[\w-\'\"\]]+/i,!1)&&!j(e.current())?(e.match(/\./),["variable-2","variable-name"]):["variable-2","word"]:e.match(D)?["operator",e.current()]:/[:;,{}\[\]\(\)]/.test(te)?(e.next(),[null,te]):(e.next(),[null,null])}function f(e,t){for(var r=!1,i;null!=(i=e.next());){if(r&&"/"==i){t.tokenize=null;break}r="*"==i}return["comment","comment"]}function k(e){return function(t,r){for(var i=!1,a;null!=(a=t.next());){if(a==e&&!i){")"==e&&t.backUp(1);break}i=!i&&"\\"==a}return(a==e||!i&&")"!=e)&&(r.tokenize=null),["string","string"]}}function w(e,t){return e.next(),e.match(/\s*[\"\')]/,!1)?t.tokenize=null:t.tokenize=k(")"),[null,"("]}function y(e,t,r,i){this.type=e,this.indent=t,this.prev=r,this.line=i||{firstWord:"",indent:0}}function v(e,t,r,i){return i=i>=0?i:A,e.context=new y(r,t.indentation()+i,e.context),r}function x(e,t){var r=e.context.indent-A;return t=t||!1,e.context=e.context.prev,t&&(e.context.indent=r),e.context.type}function z(e,t,r){return ee[r.context.type](e,t,r)}function q(e,t,r,i){for(var a=i||1;a>0;a--)r.context=r.context.prev;return z(e,t,r)}function j(e){return e.toLowerCase()in M}function $(e){return e=e.toLowerCase(),e in R||e in T}function C(e){return e.toLowerCase()in J}function B(e){return e.toLowerCase().match(K)}function L(e){var t=e.toLowerCase(),r="variable-2";return j(e)?r="tag":C(e)?r="block-keyword":$(e)?r="property":t in X||t in Q?r="atom":"return"==t||t in Y?r="keyword":e.match(/^[A-Z]/)&&(r="string"),r}function P(e,t){return W(t)&&("{"==e||"]"==e||"hash"==e||"qualifier"==e)||"block-mixin"==e}function _(e,t){return"{"==e&&t.match(/^\s*\$?[\w-]+/i,!1)}function E(e,t){return":"==e&&t.match(/^[a-z-]+/,!1)}function N(e){return e.sol()||e.string.match(new RegExp("^\\s*"+i(e.current())))}function W(e){return e.eol()||e.match(/^\s*$/,!1)}function U(e){var t=/^\s*[-_]*[a-z0-9]+[\w-]*/i,r="string"==typeof e?e.match(t):e.string.match(t);return r?r[0].replace(/^\s*/,""):""}var A=e.indentUnit,M=r(a),O=/^(a|b|i|s|col|em)$/i,R=r(s),S=r(c),X=r(m),Y=r(u),Z=r(o),F=t(o),H=r(l),I=r(n),T=r(d),D=/^\s*([.]{2,3}|&&|\|\||\*\*|[?!=:]?=|[-+*\/%<>]=?|\?:|\~)/,G=t(p),J=r(h),K=new RegExp(/^\-(moz|ms|o|webkit)-/i),Q=r(b),V="",ee={},te,re,ie,ae;return ee.block=function(e,t,r){if("comment"==e&&N(t)||","==e&&W(t)||"mixin"==e)return v(r,t,"block",0);if(_(e,t))return v(r,t,"interpolation");if(W(t)&&"]"==e&&!/^\s*(\.|#|:|\[|\*|&)/.test(t.string)&&!j(U(t)))return v(r,t,"block",0);if(P(e,t,r))return v(r,t,"block");if("}"==e&&W(t))return v(r,t,"block",0);if("variable-name"==e)return t.string.match(/^\s?\$[\w-\.\[\]\'\"]+$/)||C(U(t))?v(r,t,"variableName"):v(r,t,"variableName",0);if("="==e)return W(t)||C(U(t))?v(r,t,"block"):v(r,t,"block",0);if("*"==e&&(W(t)||t.match(/\s*(,|\.|#|\[|:|{)/,!1)))return ae="tag",v(r,t,"block");if(E(e,t))return v(r,t,"pseudo");if(/@(font-face|media|supports|(-moz-)?document)/.test(e))return v(r,t,W(t)?"block":"atBlock");if(/@(-(moz|ms|o|webkit)-)?keyframes$/.test(e))return v(r,t,"keyframes");if(/@extends?/.test(e))return v(r,t,"extend",0);if(e&&"@"==e.charAt(0))return t.indentation()>0&&$(t.current().slice(1))?(ae="variable-2","block"):/(@import|@require|@charset)/.test(e)?v(r,t,"block",0):v(r,t,"block");if("reference"==e&&W(t))return v(r,t,"block");if("("==e)return v(r,t,"parens");if("vendor-prefixes"==e)return v(r,t,"vendorPrefixes");if("word"==e){var i=t.current();if(ae=L(i),"property"==ae)return N(t)?v(r,t,"block",0):(ae="atom","block");if("tag"==ae){if(/embed|menu|pre|progress|sub|table/.test(i)&&$(U(t)))return ae="atom","block";if(t.string.match(new RegExp("\\[\\s*"+i+"|"+i+"\\s*\\]")))return ae="atom","block";if(O.test(i)&&(N(t)&&t.string.match(/=/)||!N(t)&&!t.string.match(/^(\s*\.|#|\&|\[|\/|>|\*)/)&&!j(U(t))))return ae="variable-2",C(U(t))?"block":v(r,t,"block",0);if(W(t))return v(r,t,"block")}if("block-keyword"==ae)return ae="keyword",t.current(/(if|unless)/)&&!N(t)?"block":v(r,t,"block");if("return"==i)return v(r,t,"block",0);if("variable-2"==ae&&t.string.match(/^\s?\$[\w-\.\[\]\'\"]+$/))return v(r,t,"block")}return r.context.type},ee.parens=function(e,t,r){if("("==e)return v(r,t,"parens");if(")"==e)return"parens"==r.context.prev.type?x(r):t.string.match(/^[a-z][\w-]*\(/i)&&W(t)||C(U(t))||/(\.|#|:|\[|\*|&|>|~|\+|\/)/.test(U(t))||!t.string.match(/^-?[a-z][\w-\.\[\]\'\"]*\s*=/)&&j(U(t))?v(r,t,"block"):t.string.match(/^[\$-]?[a-z][\w-\.\[\]\'\"]*\s*=/)||t.string.match(/^\s*(\(|\)|[0-9])/)||t.string.match(/^\s+[a-z][\w-]*\(/i)||t.string.match(/^\s+[\$-]?[a-z]/i)?v(r,t,"block",0):W(t)?v(r,t,"block"):v(r,t,"block",0);if(e&&"@"==e.charAt(0)&&$(t.current().slice(1))&&(ae="variable-2"),"word"==e){var i=t.current();ae=L(i),"tag"==ae&&O.test(i)&&(ae="variable-2"),("property"==ae||"to"==i)&&(ae="atom")}return"variable-name"==e?v(r,t,"variableName"):E(e,t)?v(r,t,"pseudo"):r.context.type},ee.vendorPrefixes=function(e,t,r){return"word"==e?(ae="property",v(r,t,"block",0)):x(r)},ee.pseudo=function(e,t,r){return $(U(t.string))?q(e,t,r):(t.match(/^[a-z-]+/),ae="variable-3",W(t)?v(r,t,"block"):x(r))},ee.atBlock=function(e,t,r){if("("==e)return v(r,t,"atBlock_parens");if(P(e,t,r))return v(r,t,"block");if(_(e,t))return v(r,t,"interpolation");if("word"==e){var i=t.current().toLowerCase();if(ae=/^(only|not|and|or)$/.test(i)?"keyword":Z.hasOwnProperty(i)?"tag":I.hasOwnProperty(i)?"attribute":H.hasOwnProperty(i)?"property":S.hasOwnProperty(i)?"string-2":L(t.current()),"tag"==ae&&W(t))return v(r,t,"block")}return"operator"==e&&/^(not|and|or)$/.test(t.current())&&(ae="keyword"),r.context.type},ee.atBlock_parens=function(e,t,r){if("{"==e||"}"==e)return r.context.type;if(")"==e)return W(t)?v(r,t,"block"):v(r,t,"atBlock");if("word"==e){var i=t.current().toLowerCase();return ae=L(i),/^(max|min)/.test(i)&&(ae="property"),"tag"==ae&&(ae=O.test(i)?"variable-2":"atom"),r.context.type}return ee.atBlock(e,t,r)},ee.keyframes=function(e,t,r){return"0"==t.indentation()&&("}"==e&&N(t)||"]"==e||"hash"==e||"qualifier"==e||j(t.current()))?q(e,t,r):"{"==e?v(r,t,"keyframes"):"}"==e?N(t)?x(r,!0):v(r,t,"keyframes"):"unit"==e&&/^[0-9]+\%$/.test(t.current())?v(r,t,"keyframes"):"word"==e&&(ae=L(t.current()),"block-keyword"==ae)?(ae="keyword",v(r,t,"keyframes")):/@(font-face|media|supports|(-moz-)?document)/.test(e)?v(r,t,W(t)?"block":"atBlock"):"mixin"==e?v(r,t,"block",0):r.context.type},ee.interpolation=function(e,t,r){return"{"==e&&x(r)&&v(r,t,"block"),"}"==e?t.string.match(/^\s*(\.|#|:|\[|\*|&|>|~|\+|\/)/i)||t.string.match(/^\s*[a-z]/i)&&j(U(t))?v(r,t,"block"):!t.string.match(/^(\{|\s*\&)/)||t.match(/\s*[\w-]/,!1)?v(r,t,"block",0):v(r,t,"block"):"variable-name"==e?v(r,t,"variableName",0):("word"==e&&(ae=L(t.current()),"tag"==ae&&(ae="atom")),r.context.type)},ee.extend=function(e,t,r){return"["==e||"="==e?"extend":"]"==e?x(r):"word"==e?(ae=L(t.current()),"extend"):x(r)},ee.variableName=function(e,t,r){return"string"==e||"["==e||"]"==e||t.current().match(/^(\.|\$)/)?(t.current().match(/^\.[\w-]+/i)&&(ae="variable-2"),"variableName"):q(e,t,r)},{startState:function(e){return{tokenize:null,state:"block",context:new y("block",e||0,null)}},token:function(e,t){return!t.tokenize&&e.eatSpace()?null:(re=(t.tokenize||g)(e,t),re&&"object"==typeof re&&(ie=re[1],re=re[0]),ae=re,t.state=ee[t.state](ie,e,t),ae)},indent:function(e,t,r){var i=e.context,a=t&&t.charAt(0),o=i.indent,n=U(t),l=r.length-r.replace(/^\s*/,"").length,s=e.context.prev?e.context.prev.line.firstWord:"",c=e.context.prev?e.context.prev.line.indent:l;return i.prev&&("}"==a&&("block"==i.type||"atBlock"==i.type||"keyframes"==i.type)||")"==a&&("parens"==i.type||"atBlock_parens"==i.type)||"{"==a&&"at"==i.type)?(o=i.indent-A,i=i.prev):/(\})/.test(a)||(/@|\$|\d/.test(a)||/^\{/.test(t)||/^\s*\/(\/|\*)/.test(t)||/^\s*\/\*/.test(s)||/^\s*[\w-\.\[\]\'\"]+\s*(\?|:|\+)?=/i.test(t)||/^(\+|-)?[a-z][\w-]*\(/i.test(t)||/^return/.test(t)||C(n)?o=l:/(\.|#|:|\[|\*|&|>|~|\+|\/)/.test(a)||j(n)?o=/\,\s*$/.test(s)?c:/^\s+/.test(r)&&(/(\.|#|:|\[|\*|&|>|~|\+|\/)/.test(s)||j(s))?c>=l?c:c+A:l:/,\s*$/.test(r)||!B(n)&&!$(n)||(o=C(s)?c>=l?c:c+A:/^\{/.test(s)?c>=l?l:c+A:B(s)||$(s)?l>=c?c:l:/^(\.|#|:|\[|\*|&|@|\+|\-|>|~|\/)/.test(s)||/=\s*$/.test(s)||j(s)||/^\$[\w-\.\[\]\'\"]/.test(s)?c+A:l)),o},electricChars:"}",lineComment:"//",fold:"indent"}});var a=["a","abbr","address","area","article","aside","audio","b","base","bdi","bdo","bgsound","blockquote","body","br","button","canvas","caption","cite","code","col","colgroup","data","datalist","dd","del","details","dfn","div","dl","dt","em","embed","fieldset","figcaption","figure","footer","form","h1","h2","h3","h4","h5","h6","head","header","hgroup","hr","html","i","iframe","img","input","ins","kbd","keygen","label","legend","li","link","main","map","mark","marquee","menu","menuitem","meta","meter","nav","nobr","noframes","noscript","object","ol","optgroup","option","output","p","param","pre","progress","q","rp","rt","ruby","s","samp","script","section","select","small","source","span","strong","style","sub","summary","sup","table","tbody","td","textarea","tfoot","th","thead","time","tr","track","u","ul","var","video"],o=["domain","regexp","url","url-prefix"],n=["all","aural","braille","handheld","print","projection","screen","tty","tv","embossed"],l=["width","min-width","max-width","height","min-height","max-height","device-width","min-device-width","max-device-width","device-height","min-device-height","max-device-height","aspect-ratio","min-aspect-ratio","max-aspect-ratio","device-aspect-ratio","min-device-aspect-ratio","max-device-aspect-ratio","color","min-color","max-color","color-index","min-color-index","max-color-index","monochrome","min-monochrome","max-monochrome","resolution","min-resolution","max-resolution","scan","grid"],s=["align-content","align-items","align-self","alignment-adjust","alignment-baseline","anchor-point","animation","animation-delay","animation-direction","animation-duration","animation-fill-mode","animation-iteration-count","animation-name","animation-play-state","animation-timing-function","appearance","azimuth","backface-visibility","background","background-attachment","background-clip","background-color","background-image","background-origin","background-position","background-repeat","background-size","baseline-shift","binding","bleed","bookmark-label","bookmark-level","bookmark-state","bookmark-target","border","border-bottom","border-bottom-color","border-bottom-left-radius","border-bottom-right-radius","border-bottom-style","border-bottom-width","border-collapse","border-color","border-image","border-image-outset","border-image-repeat","border-image-slice","border-image-source","border-image-width","border-left","border-left-color","border-left-style","border-left-width","border-radius","border-right","border-right-color","border-right-style","border-right-width","border-spacing","border-style","border-top","border-top-color","border-top-left-radius","border-top-right-radius","border-top-style","border-top-width","border-width","bottom","box-decoration-break","box-shadow","box-sizing","break-after","break-before","break-inside","caption-side","clear","clip","color","color-profile","column-count","column-fill","column-gap","column-rule","column-rule-color","column-rule-style","column-rule-width","column-span","column-width","columns","content","counter-increment","counter-reset","crop","cue","cue-after","cue-before","cursor","direction","display","dominant-baseline","drop-initial-after-adjust","drop-initial-after-align","drop-initial-before-adjust","drop-initial-before-align","drop-initial-size","drop-initial-value","elevation","empty-cells","fit","fit-position","flex","flex-basis","flex-direction","flex-flow","flex-grow","flex-shrink","flex-wrap","float","float-offset","flow-from","flow-into","font","font-feature-settings","font-family","font-kerning","font-language-override","font-size","font-size-adjust","font-stretch","font-style","font-synthesis","font-variant","font-variant-alternates","font-variant-caps","font-variant-east-asian","font-variant-ligatures","font-variant-numeric","font-variant-position","font-weight","grid","grid-area","grid-auto-columns","grid-auto-flow","grid-auto-position","grid-auto-rows","grid-column","grid-column-end","grid-column-start","grid-row","grid-row-end","grid-row-start","grid-template","grid-template-areas","grid-template-columns","grid-template-rows","hanging-punctuation","height","hyphens","icon","image-orientation","image-rendering","image-resolution","inline-box-align","justify-content","left","letter-spacing","line-break","line-height","line-stacking","line-stacking-ruby","line-stacking-shift","line-stacking-strategy","list-style","list-style-image","list-style-position","list-style-type","margin","margin-bottom","margin-left","margin-right","margin-top","marker-offset","marks","marquee-direction","marquee-loop","marquee-play-count","marquee-speed","marquee-style","max-height","max-width","min-height","min-width","move-to","nav-down","nav-index","nav-left","nav-right","nav-up","object-fit","object-position","opacity","order","orphans","outline","outline-color","outline-offset","outline-style","outline-width","overflow","overflow-style","overflow-wrap","overflow-x","overflow-y","padding","padding-bottom","padding-left","padding-right","padding-top","page","page-break-after","page-break-before","page-break-inside","page-policy","pause","pause-after","pause-before","perspective","perspective-origin","pitch","pitch-range","play-during","position","presentation-level","punctuation-trim","quotes","region-break-after","region-break-before","region-break-inside","region-fragment","rendering-intent","resize","rest","rest-after","rest-before","richness","right","rotation","rotation-point","ruby-align","ruby-overhang","ruby-position","ruby-span","shape-image-threshold","shape-inside","shape-margin","shape-outside","size","speak","speak-as","speak-header","speak-numeral","speak-punctuation","speech-rate","stress","string-set","tab-size","table-layout","target","target-name","target-new","target-position","text-align","text-align-last","text-decoration","text-decoration-color","text-decoration-line","text-decoration-skip","text-decoration-style","text-emphasis","text-emphasis-color","text-emphasis-position","text-emphasis-style","text-height","text-indent","text-justify","text-outline","text-overflow","text-shadow","text-size-adjust","text-space-collapse","text-transform","text-underline-position","text-wrap","top","transform","transform-origin","transform-style","transition","transition-delay","transition-duration","transition-property","transition-timing-function","unicode-bidi","vertical-align","visibility","voice-balance","voice-duration","voice-family","voice-pitch","voice-range","voice-rate","voice-stress","voice-volume","volume","white-space","widows","width","word-break","word-spacing","word-wrap","z-index","clip-path","clip-rule","mask","enable-background","filter","flood-color","flood-opacity","lighting-color","stop-color","stop-opacity","pointer-events","color-interpolation","color-interpolation-filters","color-rendering","fill","fill-opacity","fill-rule","image-rendering","marker","marker-end","marker-mid","marker-start","shape-rendering","stroke","stroke-dasharray","stroke-dashoffset","stroke-linecap","stroke-linejoin","stroke-miterlimit","stroke-opacity","stroke-width","text-rendering","baseline-shift","dominant-baseline","glyph-orientation-horizontal","glyph-orientation-vertical","text-anchor","writing-mode","font-smoothing","osx-font-smoothing"],c=["scrollbar-arrow-color","scrollbar-base-color","scrollbar-dark-shadow-color","scrollbar-face-color","scrollbar-highlight-color","scrollbar-shadow-color","scrollbar-3d-light-color","scrollbar-track-color","shape-inside","searchfield-cancel-button","searchfield-decoration","searchfield-results-button","searchfield-results-decoration","zoom"],d=["font-family","src","unicode-range","font-variant","font-feature-settings","font-stretch","font-weight","font-style"],u=["aliceblue","antiquewhite","aqua","aquamarine","azure","beige","bisque","black","blanchedalmond","blue","blueviolet","brown","burlywood","cadetblue","chartreuse","chocolate","coral","cornflowerblue","cornsilk","crimson","cyan","darkblue","darkcyan","darkgoldenrod","darkgray","darkgreen","darkkhaki","darkmagenta","darkolivegreen","darkorange","darkorchid","darkred","darksalmon","darkseagreen","darkslateblue","darkslategray","darkturquoise","darkviolet","deeppink","deepskyblue","dimgray","dodgerblue","firebrick","floralwhite","forestgreen","fuchsia","gainsboro","ghostwhite","gold","goldenrod","gray","grey","green","greenyellow","honeydew","hotpink","indianred","indigo","ivory","khaki","lavender","lavenderblush","lawngreen","lemonchiffon","lightblue","lightcoral","lightcyan","lightgoldenrodyellow","lightgray","lightgreen","lightpink","lightsalmon","lightseagreen","lightskyblue","lightslategray","lightsteelblue","lightyellow","lime","limegreen","linen","magenta","maroon","mediumaquamarine","mediumblue","mediumorchid","mediumpurple","mediumseagreen","mediumslateblue","mediumspringgreen","mediumturquoise","mediumvioletred","midnightblue","mintcream","mistyrose","moccasin","navajowhite","navy","oldlace","olive","olivedrab","orange","orangered","orchid","palegoldenrod","palegreen","paleturquoise","palevioletred","papayawhip","peachpuff","peru","pink","plum","powderblue","purple","rebeccapurple","red","rosybrown","royalblue","saddlebrown","salmon","sandybrown","seagreen","seashell","sienna","silver","skyblue","slateblue","slategray","snow","springgreen","steelblue","tan","teal","thistle","tomato","turquoise","violet","wheat","white","whitesmoke","yellow","yellowgreen"],m=["above","absolute","activeborder","additive","activecaption","afar","after-white-space","ahead","alias","all","all-scroll","alphabetic","alternate","always","amharic","amharic-abegede","antialiased","appworkspace","arabic-indic","armenian","asterisks","attr","auto","avoid","avoid-column","avoid-page","avoid-region","background","backwards","baseline","below","bidi-override","binary","bengali","blink","block","block-axis","bold","bolder","border","border-box","both","bottom","break","break-all","break-word","bullets","button","button-bevel","buttonface","buttonhighlight","buttonshadow","buttontext","calc","cambodian","capitalize","caps-lock-indicator","caption","captiontext","caret","cell","center","checkbox","circle","cjk-decimal","cjk-earthly-branch","cjk-heavenly-stem","cjk-ideographic","clear","clip","close-quote","col-resize","collapse","column","compact","condensed","contain","content","content-box","context-menu","continuous","copy","counter","counters","cover","crop","cross","crosshair","currentcolor","cursive","cyclic","dashed","decimal","decimal-leading-zero","default","default-button","destination-atop","destination-in","destination-out","destination-over","devanagari","disc","discard","disclosure-closed","disclosure-open","document","dot-dash","dot-dot-dash","dotted","double","down","e-resize","ease","ease-in","ease-in-out","ease-out","element","ellipse","ellipsis","embed","end","ethiopic","ethiopic-abegede","ethiopic-abegede-am-et","ethiopic-abegede-gez","ethiopic-abegede-ti-er","ethiopic-abegede-ti-et","ethiopic-halehame-aa-er","ethiopic-halehame-aa-et","ethiopic-halehame-am-et","ethiopic-halehame-gez","ethiopic-halehame-om-et","ethiopic-halehame-sid-et","ethiopic-halehame-so-et","ethiopic-halehame-ti-er","ethiopic-halehame-ti-et","ethiopic-halehame-tig","ethiopic-numeric","ew-resize","expanded","extends","extra-condensed","extra-expanded","fantasy","fast","fill","fixed","flat","flex","footnotes","forwards","from","geometricPrecision","georgian","graytext","groove","gujarati","gurmukhi","hand","hangul","hangul-consonant","hebrew","help","hidden","hide","higher","highlight","highlighttext","hiragana","hiragana-iroha","horizontal","hsl","hsla","icon","ignore","inactiveborder","inactivecaption","inactivecaptiontext","infinite","infobackground","infotext","inherit","initial","inline","inline-axis","inline-block","inline-flex","inline-table","inset","inside","intrinsic","invert","italic","japanese-formal","japanese-informal","justify","kannada","katakana","katakana-iroha","keep-all","khmer","korean-hangul-formal","korean-hanja-formal","korean-hanja-informal","landscape","lao","large","larger","left","level","lighter","line-through","linear","linear-gradient","lines","list-item","listbox","listitem","local","logical","loud","lower","lower-alpha","lower-armenian","lower-greek","lower-hexadecimal","lower-latin","lower-norwegian","lower-roman","lowercase","ltr","malayalam","match","matrix","matrix3d","media-controls-background","media-current-time-display","media-fullscreen-button","media-mute-button","media-play-button","media-return-to-realtime-button","media-rewind-button","media-seek-back-button","media-seek-forward-button","media-slider","media-sliderthumb","media-time-remaining-display","media-volume-slider","media-volume-slider-container","media-volume-sliderthumb","medium","menu","menulist","menulist-button","menulist-text","menulist-textfield","menutext","message-box","middle","min-intrinsic","mix","mongolian","monospace","move","multiple","myanmar","n-resize","narrower","ne-resize","nesw-resize","no-close-quote","no-drop","no-open-quote","no-repeat","none","normal","not-allowed","nowrap","ns-resize","numbers","numeric","nw-resize","nwse-resize","oblique","octal","open-quote","optimizeLegibility","optimizeSpeed","oriya","oromo","outset","outside","outside-shape","overlay","overline","padding","padding-box","painted","page","paused","persian","perspective","plus-darker","plus-lighter","pointer","polygon","portrait","pre","pre-line","pre-wrap","preserve-3d","progress","push-button","radial-gradient","radio","read-only","read-write","read-write-plaintext-only","rectangle","region","relative","repeat","repeating-linear-gradient","repeating-radial-gradient","repeat-x","repeat-y","reset","reverse","rgb","rgba","ridge","right","rotate","rotate3d","rotateX","rotateY","rotateZ","round","row-resize","rtl","run-in","running","s-resize","sans-serif","scale","scale3d","scaleX","scaleY","scaleZ","scroll","scrollbar","se-resize","searchfield","searchfield-cancel-button","searchfield-decoration","searchfield-results-button","searchfield-results-decoration","semi-condensed","semi-expanded","separate","serif","show","sidama","simp-chinese-formal","simp-chinese-informal","single","skew","skewX","skewY","skip-white-space","slide","slider-horizontal","slider-vertical","sliderthumb-horizontal","sliderthumb-vertical","slow","small","small-caps","small-caption","smaller","solid","somali","source-atop","source-in","source-out","source-over","space","spell-out","square","square-button","start","static","status-bar","stretch","stroke","sub","subpixel-antialiased","super","sw-resize","symbolic","symbols","table","table-caption","table-cell","table-column","table-column-group","table-footer-group","table-header-group","table-row","table-row-group","tamil","telugu","text","text-bottom","text-top","textarea","textfield","thai","thick","thin","threeddarkshadow","threedface","threedhighlight","threedlightshadow","threedshadow","tibetan","tigre","tigrinya-er","tigrinya-er-abegede","tigrinya-et","tigrinya-et-abegede","to","top","trad-chinese-formal","trad-chinese-informal","translate","translate3d","translateX","translateY","translateZ","transparent","ultra-condensed","ultra-expanded","underline","up","upper-alpha","upper-armenian","upper-greek","upper-hexadecimal","upper-latin","upper-norwegian","upper-roman","uppercase","urdu","url","var","vertical","vertical-text","visible","visibleFill","visiblePainted","visibleStroke","visual","w-resize","wait","wave","wider","window","windowframe","windowtext","words","x-large","x-small","xor","xx-large","xx-small","bicubic","optimizespeed","grayscale","row","row-reverse","wrap","wrap-reverse","column-reverse","flex-start","flex-end","space-between","space-around"],p=["in","and","or","not","is not","is a","is","isnt","defined","if unless"],h=["for","if","else","unless","from","to"],b=["null","true","false","href","title","type","not-allowed","readonly","disabled"],g=["@font-face","@keyframes","@media","@viewport","@page","@host","@supports","@block","@css"],f=a.concat(o,n,l,s,c,u,m,d,p,h,b,g);e.registerHelper("hintWords","stylus",f),e.defineMIME("text/x-styl","stylus")});