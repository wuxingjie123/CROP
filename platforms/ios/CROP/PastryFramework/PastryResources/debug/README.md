

release build 备注：删除 debug 整个文件夹目录；

1、
    certificate

    certificate/cacert.pem
        功能：Debug 时使用的 SSL证书

    certificate/ptframework_license
        功能：Debug 时使用的 客户端许可文件，内容任意

    certificate/ptframework.der
        功能：Debug 时使用的 包含 测试公钥证书/生产公钥证书；

2、
    developer_mode
        功能：Debug 时使用的 各种开发方式的配置文件
            1、开发者模式-JS、CSS等文件验签模式；
            2、开发者模式-JS、CSS等文件不验签模式；
            3、开启服务器合法性验证-JS、CSS等文件验签模式；
            4、开启服务器合法性验证-JS、CSS等文件不验签模式；
            5、关闭服务器合法性验证-JS、CSS等文件验签模式；
            6、关闭服务器合法性验证-JS、CSS等文件不验签模式； 

3、
    PT.framework
        功能：真机版 And 模拟器版 框架包、Debug版；
