//
//  PTPathManager.h
//  PT
//
//  Created by gengych on 16/3/17.
//  Copyright © 2016年 中信网络科技. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 *  @ingroup resourceConfigModuleEnum
 *  PT框架需要的文件路径枚举   <br/>
 *  <table>
 *     <tr>
 *         <td>PTPath_Documents_Directory</td>
 *         <td>Documents 文件夹路径</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Release_Directory</td>
 *         <td>Documents/release 文件夹路径</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Backup_Directory</td>
 *         <td>Documents/backup 文件夹路径</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Menus_Directory</td>
 *         <td>Documents/menus 文件夹路径</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Datazip_Bundle_Path</td>
 *         <td>程序目录下的 data.zip 文件路径</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Datazip_Sandbox_Path</td>
 *         <td>沙箱目录下的 data.zip 文件路径</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_resourcejson_release_Path</td>
 *         <td>resource.json 文件路径:<br/>
                Documents/release/resource.json</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_resourcejson_backup_Path</td>
 *         <td>resource.json 文件路径:<br/>
                Documents/backup/resource.json</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_allmenuxml_menus_Path</td>
 *         <td>allmenu.xml 文件路径:<br/>
                Documents/menus/allmenu.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_allmenuxmlbak_menus_Path</td>
 *         <td>allmenu.xml.bak 文件路径:<br/>
                Documents/menus/allmenu.xml.bak</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_allmenuxml_backup_Path</td>
 *         <td>allmenu.xml 文件路径:<br/>
                Documents/backup/allmenu.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_allmenuxml_release_Path</td>
 *         <td>allmenu.xml 文件路径:<br/>
                Documents/release/allmenu.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_menuxml_menus_Path</td>
 *         <td>menu.xml 文件路径:<br/>
                Documents/menus/menu.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_menuxmlbak_menus_Path</td>
 *         <td>menu.xml.bak 文件路径:<br/>
                Documents/menus/menu.xml.bak</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_menuxml_backup_Path</td>
 *         <td>menu.xml 文件路径:<br/>
                Documents/backup/menu.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_menuxml_release_Path</td>
 *         <td>menu.xml 文件路径:<br/>
                Documents/release/menu.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_businessxml_menus_Path</td>
 *         <td>business.xml 文件路径:<br/>
                Documents/menus/business.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_businessxmlbak_menus_Path</td>
 *         <td>business.xml 文件路径:<br/>
                Documents/menus/business.xml.bak</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_businessxml_backup_Path</td>
 *         <td>business.xml 文件路径:<br/>
                Documents/backup/business.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_businessxml_release_Path</td>
 *         <td>business.xml 文件路径:<br/>
                Documents/release/business.xml</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Der_Path</td>
 *         <td>ptframework.der 文件路径:<br/>
                工程目录/PastryResources/(release|debug)/certificate/ptframework.der</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_License_Path</td>
 *         <td>ptframework_license 文件路径:<br/>
                工程目录/PastryResources/(release|debug)/certificate/ptframework_license</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Cacert_Path</td>
 *         <td>cacert.pem 文件路径:<br/>
                工程目录/PastryResources/(release|debug)/certificate/cacert.pem</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_SystemPlist_Path</td>
 *         <td>System.plist 文件路径:<br/>
                工程目录/PastryResources/release/System.plist</td>
 *     </tr>
 *     <tr>
 *         <td>PTPath_Settings_RootPlist_Path</td>
 *         <td>Settings.bundle/Root.plist 文件路径:<br/>
                工程目录/PastryResources/debug/Settings.bundle/Root.plist</td>
 *     </tr>
 *  </table>
 */
typedef enum {
    PTPath_Documents_Directory,         //!< Documents 文件夹路径
    PTPath_Release_Directory,           //!< Documents/release 文件夹路径
    PTPath_Backup_Directory,            //!< Documents/backup 文件夹路径
    PTPath_Menus_Directory,             //!< Documents/menus 文件夹路径

    PTPath_Datazip_Bundle_Path,         //!< 程序目录下的 data.zip 文件路径
    PTPath_Datazip_Sandbox_Path,        //!< 沙箱目录下的 data.zip 文件路径

    PTPath_resourcejson_release_Path,   //!< resource.json 文件路径:<br/>Documents/release/resource.json
    PTPath_resourcejson_backup_Path,    //!< resource.json 文件路径:<br/>Documents/backup/resource.json
    
    PTPath_allmenuxml_menus_Path,       //!< allmenu.xml 文件路径:<br/>Documents/menus/allmenu.xml
    PTPath_allmenuxmlbak_menus_Path,    //!< allmenu.xml.bak 文件路径:<br/>Documents/menus/allmenu.xml.bak
    PTPath_allmenuxml_backup_Path,      //!< allmenu.xml 文件路径:<br/>Documents/backup/allmenu.xml
    PTPath_allmenuxml_release_Path,     //!< allmenu.xml 文件路径:<br/>Documents/release/allmenu.xml
    
    PTPath_menuxml_menus_Path,          //!< menu.xml 文件路径:<br/>Documents/menus/menu.xml
    PTPath_menuxmlbak_menus_Path,       //!< menu.xml.bak 文件路径:<br/>Documents/menus/menu.xml.bak
    PTPath_menuxml_backup_Path,         //!< menu.xml 文件路径:<br/>Documents/backup/menu.xml
    PTPath_menuxml_release_Path,        //!< menu.xml 文件路径:<br/>Documents/release/menu.xml

    PTPath_businessxml_menus_Path,      //!< business.xml 文件路径:<br/>Documents/menus/business.xml
    PTPath_businessxmlbak_menus_Path,   //!< business.xml 文件路径:<br/>Documents/menus/business.xml.bak
    PTPath_businessxml_backup_Path,     //!< business.xml 文件路径:<br/>Documents/backup/business.xml
    PTPath_businessxml_release_Path,    //!< business.xml 文件路径:<br/>Documents/release/business.xml

    PTPath_Der_Path,                    //!< ptframework.der        文件路径:<br/>工程目录/PastryResources/(release|debug)/certificate/ptframework.der
    PTPath_License_Path,                //!< ptframework_license    文件路径:<br/>工程目录/PastryResources/(release|debug)/certificate/ptframework_license
    PTPath_Cacert_Path,                 //!< cacert.pem             文件路径:<br/>工程目录/PastryResources/(release|debug)/certificate/cacert.pem
    
    
    PTPath_SystemPlist_Path,            //!< System.plist           文件路径:<br/>工程目录/PastryResources/release/System.plist
    
    PTPath_Settings_RootPlist_Path,     //!< Settings.bundle/Root.plist 文件路径:<br/>工程目录/PastryResources/debug/Settings.bundle/Root.plist

} PTPathType;

/**
 * @ingroup resourceConfigModuleClass
 */
@interface PTPathManager : NSObject

+ (NSString *)getPath:(PTPathType)pathType;

+ (void)checkPath;

@end
