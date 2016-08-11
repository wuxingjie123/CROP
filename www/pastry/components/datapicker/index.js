console.log("def:component_datapicker"),define(["js/core","libex/bootstrap-datepicker","css!~/css/bootstrap-datepicker.css","css!~/css/bootstrap-datepicker-ex.css"],function(e){var t=e.api,a=e.Component,i=e.DependencyManager,n=e.Template,d=t.$;a.Datepicker=a.Base.extend({initialize:function(e){_.defaults(this,{dependencySelf:!1,defMod:"Input"}),this._super.apply(this,arguments);var a=this.model,i;a.mapping.value=t.getTypeVal("String",a.mapping.name,"value"),"input-daterange"===this.$el.attr("data-type")&&(i=_.template('<div class=" input-daterange date"><div class="input-daterange-start form-control datepicker"><input type="text" value="<%= '+a.mapping.name+'[0]%>" readonly/></div><div class="input-daterange-end form-control datepicker"><input type="text" value="<%= '+a.mapping.name+'[1]%>" readonly/></div></div>')),this.template=n.obtainTemplate(i,_.template("<div class='datepicker form-control'><div class='input-append date input-single'><input type='hidden' value='<%= "+a.mapping.name+"%>' readonly/><span class='add-on'></span></div></div>"))},afterRender:function(){var e=this,t=e.model;this.config={autoclose:!0,todayHighlight:!0,format:this.$el.data("format")||"yyyy-mm-dd",orientation:this.$el.data("orientation")||"top",language:this.$el.data("language")||"chn",clearBtn:this.$el.data("clearbtn")||!0,startDate:this.$el.data("startdate")||-(1/0),endDate:this.$el.data("enddate")||1/0,type:this.$el.data("type")||"input-single",minViewMode:this.$el.data("minviewmode")||0,langMapping:{}},d.api.datepicker.dates.chn={days:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],daysShort:["日","一","二","三","四","五","六"],daysMin:["日","一","二","三","四","五","六"],months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],monthsShort:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一","十二"],today:"今天",clear:"清空"},_.extend(this.config,this.options),this.config.langMapping&&_.extend(d.api.datepicker.dates,this.config.langMapping,{}),e.$el.find(".input-single,.input-daterange").datepicker(this.config).on("changeDate",function(t){if(-1!==e.config.type.indexOf("input-daterange")){var a={},l=0;e.$el.find("input").each(function(e,t){a[e]=d(this).val()})}else var a=e.$el.find("input").val();if(e._setDefValue(a),e.model.validationError){var p=n.obtainTemplate(e.$el.data("err-tip-template"));_.isFunction(p)&&(p=p({}).trim()),e.$el.tooltip("destroy").tooltip({title:e.model.validationError,placement:e.$el.data("err-tip-placement")||"right",template:p,trigger:"manual"}).tooltip("show")}else e.$el.tooltip("destroy");i.calcDependency(this.id)}).triggerHandler("changeDate")},_setDefValue:function(e){var t=0;if("input-single"===this.config.type)this.$el.find(".add-on").text(e);else if("input-daterange"===this.config.type&&_.isArray(e))for(var a in e)this.$el.find("input:eq("+t+")").val(e[t]);this.model.setValue(e,{validate:!0,silent:!0})}})});