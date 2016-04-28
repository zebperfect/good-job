//自定义编辑框对象
	Ext.form.KindEditorPanel = Ext.extend(Ext.form.TextArea, {
	    onRender : function(ct, position){   
			Ext.form.TextArea.superclass.onRender.call(this, ct, position);
			this.fieldId = this.fieldName+'KindEditor';
			var parentEle = $(this.el.dom).parent();
			this.height = isNaN(this.fieldHeight) ? 200 : this.fieldHeight;
			parentEle.empty()
			var random = Math.random();
			parentEle.append("<iframe id="+this.fieldId+
			" name="+this.fieldId+
			" height="+this.height+
			" width=800 src="+basePath+"kindeditor/editor.jsp?ran="+random+
			" frameborder=0 scrolling=no marginwidth=0 marginheight=0></iframe>"+
			" <form action='"+basePath+"/kindeditor/editor.jsp?ran="+random+
			"' id="+this.fieldId+"Form method=post target="+this.fieldId+
			" style='display: none;'>"+
			"	<textarea name='editorValue' ></textarea>"+
			"	<input type='submit'/>"+
			" </form>");
		},
	    getValue : function(){
	    	//这里可能存在延迟，所以，如果如果没有数据，不管即可，反正在读取的时候，肯定有值
	    	var func = window.frames[this.fieldId].getValueForEditor;
	    	if(func){
	    		return window.frames[this.fieldId].getValueForEditor();
	    	}else{
	    		return "";
	    	}
	    }, 
	    setValue : function(v){
	      	//直接通过请求方式传递，首先读取父页面的editorForm对象
	      	var editorForm = document.getElementById(this.fieldId+"Form");
	      	editorForm.editorValue.value = v;
	      	editorForm.submit();
	    }
	});   
	Ext.reg('kindeditor',Ext.form.KindEditorPanel);  