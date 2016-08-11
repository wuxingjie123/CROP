/*
bower uninstall之后将/www/mockdata里组件模拟数据文件删除
 */
process.argv.splice(0, 2);
console.log('process.argv:' + process.argv);
var fs = require('fs');
var path = require('path');
console.log('exec preuninstall.js');
var destBasePath = 'mockdata/server/';
var fileType = '.do.js';
deleteFolderRecursive = function (path) {
	var files = [];
	if (fs.existsSync(path)) {
		files = fs.readdirSync(path);
		files.forEach(function (file, index) {
			var curPath = path + "/" + file;
			if (fs.statSync(curPath).isDirectory()) { // recurse
				deleteFolderRecursive(curPath);
			} else { // delete file
				fs.unlinkSync(curPath);
			}
		});
		fs.rmdirSync(path);
	}
};
var DeleteFile = function (destBasePath, fileType) {
	process.argv.forEach(function (fileComponent) {
		var index = fileComponent.lastIndexOf("-");
		var file  = fileComponent.substring(index + 1) + fileType;
		var destPath = path.normalize(destBasePath + file);
		console.log('destPath: ' + destPath);
		fs.exists(destPath, function (exists) {
			if (exists) {
				fs.unlinkSync(destPath);
				console.log(destPath + ' delete success');
			}
		});
	});
}
DeleteFile(destBasePath, fileType);
