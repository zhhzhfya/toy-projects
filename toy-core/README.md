### Toy-core 是什么
是一个软件开发平台
包含一些元数据和他们之间的关系数据
它们的关系类似：

app <- sy_serv <- sy_object_link <- sy_object

这个平台的最初目的是通过简单的配置，实现用户的软件开发。
包括web、ios、android的开发

思路：

*通过定义sy_meta元属性，生成元数据，元数据类似容器，根据容器定义出对象、对象关联关系、对外服务 等

*未来实现一个简单、智能的平台，基于vertx的分布式事件、实现分布式部署、热部署 等等

*未来实现伪代码的方式，动态生成代码、动态加载部署、方便用户落实业务控制。

*未来基于vertx支持cluster，事件是分布的，通过事件动态发布、加载、卸载，一套的


###用到技术
用到vertx v3.2.1、rxjava、angularjs、bootstrap、jquery、websql、android、ios等


###元数据实例
```xml
<META>
	<SY_META>
		<NAME NAME="名称" TYPE="STR" SIZE="" DESC=""/>
		<TYPE NAME="类型" TYPE="STR" SIZE="" DESC=""/>
		<SIZE NAME="大小" TYPE="STR" SIZE="" DESC=""/>
		<DESC NAME="描述" TYPE="STR" SIZE="" DESC=""/>
	</SY_META>
</META>
```

```xml
<META>
    <APP>
		<APP_ID NAME="产品ID" TYPE="LONG" SIZE="" DESC=""></APP_ID>
		<APP_NAME NAME="产品名称" TYPE="STR" SIZE="" DESC=""></APP_NAME>
		<DES NAME="描述" TYPE="STR" SIZE="" DESC=""></DES>
		<CREATE_TIME NAME="创建时间" TYPE="DATETIME" SIZE="" DESC=""></CREATE_TIME>
		<VERSION NAME="版本" TYPE="" SIZE="" DESC=""></VERSION>
	</APP>
</META>
```
```xml
<META>
    <SY_SERV>
		<SERV_ID NAME="服务ID" NAME="" TYPE="LONG" SIZE="" DESC=""></SERV_ID>
		<SERV_CODE NAME="服务编码" TYPE="STR" SIZE="" DESC="" SERIALIZE="JSON"></SERV_CODE>
		<SERV_NAME NAME="服务名称" TYPE="STR" SIZE="" DESC=""></SERV_NAME>
		<OBJECT_LINK_ID NAME="关联ID" TYPE="LONG" SIZE="" DESC="关联关系的ID，可所选"></RESOURCE_ID>
		<CREATE_TIME NAME="创建时间" TYPE="DATETIME" SIZE="" DESC=""></CREATE_TIME>
		<SY_SERV_ITEM>
			<SERV_ID NAME="服务ID" TYPE="LONG" SIZE="" DESC=""></SERV_ID>
			<OBJ_ID NAME="对象ID" TYPE="LONG" SIZE="" DESC=""></OBJ_ID>
			<ITEM_ID NAME="属性ID" TYPE="LONG" SIZE="" DESC=""></ITEM_ID>
			<ITEM_NAME NAME="属性名称" TYPE="STR" SIZE="40" DESC="字段描述"></ITEM_NAME>
			<FIELD_TYPE NAME="属性类型" TYPE="STR" SIZE="10" DESC=""></FIELD_TYPE>
			<INPUT_TYPE NAME="输入类型" TYPE="NUM" SIZE="" DESC="输入类型，1：文本框；2：下拉框；3：单选框；4：多选框；5：大文本；6：超大文本；7：图片上传；8：嵌入服务；9：分组框"></INPUT_TYPE>
			<INPUT_MODE NAME="输入模式" TYPE="NUM" SIZE="" DESC="输入模式，1：无；2：查询选择；3：字典选择；4：日期选择；5：动态提示；6：组合值；9：自定义。（可选可录入？，1：是；2：否。）"></INPUT_MODE>
			<INPUT_CONFIG NAME="输入模式配置" TYPE="STR" SIZE="200" DESC="输入设置，根据输入类型和输入模式进行设定"></INPUT_CONFIG>
			<INPUT_DEFAULT NAME="默认值" TYPE="STR" SIZE="100" DESC="缺省值，支持变量，支持序列格式"></INPUT_DEFAULT>
		</SY_SERV_ITEM>
	</SY_SERV>
</META>
```

###运行方式

MainVerticle.java 直接运行

###web 访问

http://localhost:8080/admin/main.html

###有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(zhhzhfya#163.com, 把#换成@)
* QQ: 37729782

###捐助开发者
多年的开发积累，希望开发更平民化、更简单，当然这个还差的很远，如果你喜欢请联系我，同时也能支持一下。谢谢

