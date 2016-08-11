// /*
// plugin install 之后将安装组件里的 ”mockdata“ 文件夹移动到项目根目录/pastry-fo-nodejs/wwwroot/mockdata中 /www/mockdata中
//  */
// process.argv.splice(0,2);
// console.log('process.argv:' + process.argv);
// var fs = require('fs');
// var path = require('path');
// console.log('exec postinstall.js');
// var exec = require('child_process').exec;

// deleteFolderRecursive = function (path) {
//     var files = [];
//     if (fs.existsSync(path)) {
//         files = fs.readdirSync(path);
//         files.forEach(function (file, index) {
//             var curPath = path + "/" + file;
//             if (fs.statSync(curPath).isDirectory()) { // recurse
//                 deleteFolderRecursive(curPath);
//             } else { // delete file
//                 fs.unlinkSync(curPath);
//             }
//         });
//         fs.rmdirSync(path);
//     }
// };

// module.exports = function(ctx) {

//     var fs = ctx.requireCordovaModule('fs');
//     var path = ctx.requireCordovaModule('path');

//     var pluginName = ctx.opts.plugin.id;
//     // console.log('plugin id = ' + pluginName);
//     var rootPath = ctx.opts.projectRoot;
//     // console.log('rootPath = ' + rootPath);
//     var pluginSourceDir = ctx.opts.plugin.pluginInfo.dir;
//     var mockdataSourcePath = path.join(pluginSourceDir, 'mockdata');
//     console.log('mockdataSourcePath = ' + mockdataSourcePath);

//     if (fs.existsSync(mockdataSourcePath)) {
//         // 执行模拟数据的拷贝
//         var mockdataTargetPath = path.join(rootPath, 'pastry-fo-nodejs', 'wwwroot', 'mockdata');
//         console.log('mockdataTargetPath = ' + mockdataTargetPath);
//         // fs.copySync(mockdataSourcePath, mockdataTargetPath);

//         var fileComponentPath = mockdataSourcePath;
//         console.log('fileComponentPath :' + fileComponentPath);
//         fs.stat(fileComponentPath, function (err, stats) {
//             if (err) {
//                 console.error(err);
//             } else if (stats.isDirectory()) {
//                 fs.readdir(fileComponentPath, function (err, files) {
//                     if (err) {
//                         console.error(err);
//                     } else {
//                         var fileComponentChildPath;
//                         for (var i = 0; i < files.length; i++) {
//                             fileComponentChildPath = path.normalize(fileComponentPath + '/' + files[i]);
//                             if (files[i].substr(0, 8) === 'mockdata') {
//                                 var mockdataPath = path.join(mockdataSourcePath, files[i]);
//                                 fs.exists(mockdataPath, function (exists) {
//                                     if (exists) {
//                                         deleteFolderRecursive(mockdataPath);
//                                     }
//                                 });
//                                 fs.rename(fileComponentChildPath, mockdataPath, function (error) {
//                                     if (err) {
//                                         console.error(err);
//                                     } else {
//                                         console.log(fileComponentChildPath + ' remove  success ');
//                                     }
//                                 });
//                                 break;
//                             }
//                         }
//                     }
//                 });
//             }
//         });
//     } 

//     return;
// };