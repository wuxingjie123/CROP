# 基本接口

实现了基本接口 : PTUnzipInterface

# 支持平台
>
|平台 | 是否支持 |
|-----|------|
|JS    | 不支持    |
|iOS    | 支持    |
|android    | 支持(计划中)    |

# 依赖服务器数据

|平台 | 是否依赖 |
|-----|------|
|FO-Java    | 不依赖    |
|FO-NodeJS    | 不依赖    |

# 组件依赖关系
> 
|组件 | 版本号 | 地址|
|-----|------|----|
|PT基础框架    | 1.2.0    | 无|

# 功能介绍
>
* 必选功能
  * 管理 data.zip 的解压功能
* 可选功能
  * 显示解压进度页面(由 const BOOL isDisplayView 属性管理)

# 安装方法
>
* pastry本地包安装
        
    pastry bake plugin add pastry-plugin-unzip
>
* github在线安装

    # 安装指定 tag 版本
    pastry bake plugin add https://github.com/pastryTeam/pastry-plugin-unzip.git#0.1.0 
    
    # 安装最新代码
    pastry bake plugin add https://github.com/pastryTeam/pastry-plugin-unzip.git

# 使用方法
>
* 不使用解压页面显示功能

        // 修改代码
        const BOOL isDisplayView = NO;

>
* 使用解压页面显示功能

        // 修改代码
        const BOOL isDisplayView = YES;

# 作者
>
* pastryTeam团队

