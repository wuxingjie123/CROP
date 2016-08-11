cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/cordova-plugin-whitelist/whitelist.js",
        "id": "cordova-plugin-whitelist.whitelist",
        "pluginId": "cordova-plugin-whitelist",
        "runs": true
    },
    {
        "file": "plugins/pastry-plugin-sideMain/www/example.js",
        "id": "pastry-plugin-sideMain.example",
        "pluginId": "pastry-plugin-sideMain",
        "clobbers": [
            "example"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.2.1",
    "cordova-plugin-console": "1.0.3-dev",
    "pastry-plugin-browser": "1.0.0",
    "pastry-plugin-request": "1.0.0",
    "pastry-plugin-keyboard": "1.0.0",
    "pastry-plugin-unzip": "1.0.0",
    "pastry-plugin-guide": "1.0.0",
    "pastry-plugin-sideMain": "1.0.0",
    "pastry-plugin-storageDemo": "1.0.0"
}
// BOTTOM OF METADATA
});