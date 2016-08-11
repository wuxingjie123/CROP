/*
bower install 之后将安装组件里的模拟数据移动到项目根目录/www/mockdata/server中
 */
process.argv.splice(0,2);
console.log('process.argv:' + process.argv);
var fs = require('fs');
var path = require('path');
console.log('exec postinstall.js');
var exec = require('child_process').exec;
var destBasePath = 'mockdata/server/';
var sourceBasePath = 'components/';
var fileType     = '.do.js';
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
var Rename = function (sourceBasePath, destBasePath, fileType) {
	process.argv.forEach(function (fileComponent) {
		var index = fileComponent.lastIndexOf("-");
		var file = fileComponent.substring(index + 1) + fileType;
		var sourcePath = path.normalize(sourceBasePath + fileComponent + '/' + file);
		console.log('sourcePath :' + sourcePath);
		var destPath = path.normalize(destBasePath + file);
		fs.exists(destPath, function (exists) {
			if (exists) {
				fs.unlinkSync(destPath);
			}
		});
		fs.rename(sourcePath, destPath, function (error) {
			if (error) {
				console.error(error);
			} else {
				console.log(sourcePath + ' remove to ' + destPath + ' success');
			}
		});
	});
}
Rename(sourceBasePath,destBasePath,fileType);
	
