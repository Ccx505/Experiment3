实验三：Android 界面与菜单设计综合实验
一、实验目的

熟悉 SimpleAdapter 的使用方法及其在 ListView 中的数据绑定。
掌握 自定义列表项布局 的实现。
学会使用 Toast 显示用户交互信息。
了解 通知（Notification） 的基本创建与显示机制。
掌握 自定义对话框（AlertDialog） 的创建与布局绑定方法。
学习 XML 定义菜单（OptionsMenu 与 ContextMenu） 的方法。
实现字体大小、颜色动态设置等菜单交互功能。

二、实验内容与要求
1. 使用 SimpleAdapter 实现列表界面

要求：
使用 SimpleAdapter 将数据（文字 + 图片）绑定到 ListView。
自定义列表项布局（包含图片与文字），如示例图所示。
图片资源使用QQ群提供的附件图片。
单击某个列表项时：
弹出 Toast 显示选中项的信息；
同时 发送通知（Notification）：
通知图标（Icon）为应用程序图标；
通知标题（Title）为列表项内容；
通知正文内容可自拟。

2. 创建自定义对话框

要求：
创建一个自定义布局文件，布局中包含文本输入框、确认和取消按钮等控件；
使用 AlertDialog.Builder 的 setView() 方法将该布局加载到对话框中；
现确认与取消按钮的事件响应；
确认按钮点击后显示用户输入或执行相应操作。

3. 使用 XML 定义菜单

菜单包括以下部分：
（1）字体大小菜单

菜单项包括“小（10号字）”、“中（16号字）”、“大（20号字）”；
点击后动态修改测试文本的字体大小。

（2）普通菜单项

点击后弹出 Toast 提示（例如“这是普通菜单项”）。

（3）字体颜色菜单

菜单项包括“红色”、“黑色”；
点击后设置文本的颜色。

4. 创建上下文菜单（ActionMode）

要求：
使用 ListView 或 ListActivity；
长按列表项时进入上下文操作模式（ActionMode）；
在 ActionMode 中显示可操作菜单项（例如删除、分享等）；
执行菜单项后弹出相应的 Toast 提示。

三、程序结构说明
文件名	功能描述
MainActivity.java	主界面逻辑，负责列表展示、菜单与通知功能实现
list_item.xml	自定义列表项布局（图片+文字）
dialog_custom.xml	自定义对话框布局文件
res/menu/menu_main.xml	顶部菜单项定义（字体、颜色、普通项）
res/menu/context_menu.xml	上下文菜单定义（删除、分享等操作）
drawable/	存放实验所用图片资源（tiger.jpg、cat.png、dog.jpeg 等）
AndroidManifest.xml	声明主活动及应用权限等信息

四、实验结果截图
点击列表项的 Toast 与通知显示效果
(https://github.com/Ccx505/Experiment3/blob/main/屏幕截图%202025-10-28%20183845.png)
自定义对话框展示效果
(https://github.com/Ccx505/Experiment3/blob/main/屏幕截图%202025-10-28%20183856.png)
菜单操作（字体大小与颜色）效果
(https://github.com/Ccx505/Experiment3/blob/main/屏幕截图%202025-10-28%20183914.png)
上下文菜单（长按列表项）显示效果
(https://github.com/Ccx505/Experiment3/blob/main/屏幕截图%202025-10-28%20183926.png)

五、实验总结

通过本次实验：
掌握了 SimpleAdapter 的使用及自定义列表布局；
学会了使用 AlertDialog.Builder 构建并显示自定义对话框；
理解了 Android 菜单系统的结构与 XML 定义方式；
熟悉了通知机制与上下文菜单（ActionMode）的创建与使用；
提升了 Android 界面交互设计与事件响应编程能力。
