console.log("def:component_checkbox"),define(["js/core"],function(e){var t=e.api,n=e.Component,a=e.Model;a.Textarea=a.Input.extend({validate:function(e){var t=this._super.apply(this,arguments);if(t)return this._super.apply(this,arguments);var n=this.component.$el,a,r,i,l=e[this.mapping.value];if(_.isString(l)){if(a="v-area-min-len",r=n.data(a),r&&l.length<r)return i=n.data(a+"-msg"),i?i:"还需输入"+(r-l.length)+"个字符！";if(a="v-area-max-len",r=n.data(a),r&&l.length>r)return i=n.data(a+"-msg"),i?i:"超出"+(l.length-r)+"个字符！"}return null}}),n.Textarea=n.Input.extend({})});