资源文件说明：
1、
    data.zip
    项目中使用的html资源文件；

2、
    Reference目录

    Reference/Settings.bundle/Root.plist
        功能：提供debug状态，从设置中修改服务器的地址；
        build备注：在使用 release 时，需要删除 Settings.bundle/Root.plist 文件；

    Reference/Settings.bundle/Log.plist
        功能：提供日志配置，
        build备注：在使用 release 时，需要删除 Settings.bundle/Log.plist 文件；

    Reference/PT.framework
        功能：PT框架包占位；
        build备注：在使用 release 时，需要使用 PastryResources/release/PT.framework 替换此位置的 PT.framework， release build 结束，使用 PastryResources/debug/PT.framework 替换回来；
                  在使用 debug 时，需要使用 PastryResources/debug/PT.framework 替换此位置的 PT.framework;
    
    Reference/PTResources.bundle
        功能：密码键盘使用的资源文件；

3、
    PastryResources 文件夹

    PastryResources/debug
        功能：debug build 使用的配置文件，详细内容参考 PastryResources/debug/README.md 说明文件；

    PastryResources/release
        功能：release build 使用的配置文件，详细内容参考 PastryResources/debug/README.md 说明文件；

