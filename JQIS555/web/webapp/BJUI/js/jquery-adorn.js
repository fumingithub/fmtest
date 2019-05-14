/*!
 * LABELAUTY jQuery Plugin
 *
 * @file: jquery-radio.js
 * @author: xnn@jqsoft.com
 */

(function( $ ){

	$.fn.adorn = function( options )
	{
		var settings = $.extend(
		{
			icon: true,
			cancel: false
			//label: true,
			//separator: "|",
			//checked_label: "Checked",
			//unchecked_label: "Unchecked",
			//minimum_width: false,
			//same_width: true
		}, options);

		return this.each(function()
		{
			var $object = $( this );
			var name = $object.attr("name");
			if( $object.is( ":checkbox" ) === false && $object.is( ":radio" ) === false ) return this;
			$object.hide();
			$object.wrap("<button btnFlag='"+name+"' class='adorn-div' type='button'>"+ $object.attr("data-label")+"</button>");
			if(settings.icon){
				$object.parent().attr("data-icon","fa-close");
			}
			if($object.is(":checked")){
				$object.parent().addClass("adorn-checked");
				if(settings.icon){
					$object.parent().attr("data-icon","fa-check");
				}
			}
			$object.parent().on('click',function(e){
				var $current = $(e.currentTarget),bool = $current.hasClass("adorn-checked");
				$("button[btnFlag='"+name+"']").removeClass("adorn-checked");
				if(settings.icon){
					$("button[btnFlag='"+name+"']").find('i').removeClass("fa-check").addClass("fa-close");
				}
				$("input:radio[name='"+name+"']").prop("checked",false);
				if(settings.cancel && bool){//控制点击取消，当前选中则再次点击取消
					return;
				}
				$current.addClass("adorn-checked").find("input:radio").prop("checked",true);
				if(settings.icon){
					$current.addClass("adorn-checked").find('i').removeClass("fa-close").addClass("fa-check");
				}
			});
		});
	};
}( jQuery ));
