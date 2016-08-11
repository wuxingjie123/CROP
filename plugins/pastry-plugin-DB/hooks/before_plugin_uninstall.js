// /*
// bower uninstall之后将/www/mockdata里 ”mockdata-组件名称“ 文件夹删除
//  */
// process.argv.splice(0,2);
// console.log('process.argv:' + process.argv);
// var fs = require('fs');
// var path = require('path');
// console.log('exec preuninstall.js');

// module.exports = function(ctx) {
// 	console.log('args = ' + ctx.opts);
// 	return;
	
//     // make sure android platform is part of build
//     if (ctx.opts.platforms.indexOf('android') < 0) {
//         return;
//     }
//     var fs = ctx.requireCordovaModule('fs'),
//         path = ctx.requireCordovaModule('path'),
//         deferral = ctx.requireCordovaModule('q').defer();

//     var platformRoot = path.join(ctx.opts.projectRoot, 'platforms/android');
//     var apkFileLocation = path.join(platformRoot, 'build/outputs/apk/android-debug.apk');

//     fs.stat(apkFileLocation, function(err,stats) {
//         if (err) {
//                 deferral.reject('Operation failed');
//         } else {
//             console.log('Size of ' + apkFileLocation + ' is ' + stats.size +' bytes');
//             deferral.resolve();
//         }
//     });

//     return deferral.promise;
// };
