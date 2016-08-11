!function(e){"object"==typeof exports&&"object"==typeof module?e(require("../../lib/codemirror")):"function"==typeof define&&define.amd?define(["../../lib/codemirror"],e):e(CodeMirror)}(function(e){"use strict";function r(e,r){if(!window.JSHINT)return[];JSHINT(e,r,r.globals);var n=JSHINT.data().errors,i=[];return n&&o(n,i),i}function n(e){return i(e,c,"warning",!0),i(e,s,"error"),t(e)?null:e}function i(e,r,n,i){var t,o,a,c,s;t=e.description;for(var f=0;f<r.length;f++)o=r[f],a="string"==typeof o?o:o[0],c="string"==typeof o?null:o[1],s=-1!==t.indexOf(a),(i||s)&&(e.severity=n),s&&c&&(e.description=c)}function t(e){for(var r=e.description,n=0;n<a.length;n++)if(-1!==r.indexOf(a[n]))return!0;return!1}function o(r,i){for(var t=0;t<r.length;t++){var o=r[t];if(o){var a,c;if(a=[],o.evidence){var s=a[o.line];if(!s){var f=o.evidence;s=[],Array.prototype.forEach.call(f,function(e,r){"	"===e&&s.push(r+1)}),a[o.line]=s}if(s.length>0){var d=o.character;s.forEach(function(e){d>e&&(d-=1)}),o.character=d}}var u=o.character-1,l=u+1;o.evidence&&(c=o.evidence.substring(u).search(/.\b/),c>-1&&(l+=c)),o.description=o.reason,o.start=o.character,o.end=l,o=n(o),o&&i.push({message:o.description,severity:o.severity,from:e.Pos(o.line-1,u),to:e.Pos(o.line-1,l)})}}}var a=["Dangerous comment"],c=[["Expected '{'","Statement body should be inside '{ }' braces."]],s=["Missing semicolon","Extra comma","Missing property name","Unmatched "," and instead saw"," is not defined","Unclosed string","Stopping, unable to continue"];e.registerHelper("lint","javascript",r)});