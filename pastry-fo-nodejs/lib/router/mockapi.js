// Generated by CoffeeScript 1.10.0
var CommonPackage, DEBUG_MOCK_DELAY_DEFAULT, DEBUG_MOCK_DELAY_USEDEFAULT, RES, _, dirname, execPath, express, fetchMockData, path, paths, relative, requirejs, router;

_ = require('underscore');

express = require('express');

requirejs = require('requirejs');

path = require('path');

relative = path.relative;

dirname = path.dirname;

paths = require('../common/paths');

RES = require('../common/response');

CommonPackage = require('../CommonPackage');

DEBUG_MOCK_DELAY_USEDEFAULT = false;

DEBUG_MOCK_DELAY_DEFAULT = 200;

execPath = dirname(process.argv[1]);

fetchMockData = function(url, param, callback) {
  var filename, i1, i2, i3, qs;
  i1 = url.indexOf(':');
  i2 = url.indexOf('/');
  i3 = url.indexOf('?');
  if (i3 < 0) {
    i3 = url.length;
  }
  if (i2 < 0) {
    i2 = i1 + 1;
  }
  path = url.substring(i1 + 1, i2);
  filename = url.substring(i2, i3);
  qs = url.substring(i3 + 1, url.length);
  url = relative(execPath, paths.wwwroot('mockdata', paths.normalize(path), filename));
  return requirejs([url], function(mockData) {
    var data, delay, f, i, it, len, logMsg, result;
    mockData = mockData[qs];
    if (mockData) {
      result = mockData[0].result;
      data = mockData[0].data;
      delay = mockData[0].delay;
      f = false;
      logMsg = 'find mock data success';
      for (i = 0, len = mockData.length; i < len; i++) {
        it = mockData[i];
        if (!(_.isEqual(param, it.param))) {
          continue;
        }
        data = it.result;
        data = it.data;
        delay = api.getTypeVal('Finite', it.delay, delay);
        f = true;
        break;
      }
      if (!f) {
        if (mockData[0].isDefault !== false) {
          logMsg = 'use default mock data';
        } else {
          data = mockData[mockData.length - 1].data;
          delay = api.getTypeVal('Finite', mockData[mockData.length - 1].delay, delay);
          result = api.getTypeVal('Finite', mockData[mockData.length - 1].result, 1);
          logMsg = 'use notfound mock data';
        }
      }
    }
    if (_.isUndefined(result)) {
      result = 0;
    }
    console.log('request param:');
    console.log(JSON.stringify(param, null, '  '));
    if (_.isObject(data)) {
      data = JSON.parse(JSON.stringify(data));
    }
    console.log(logMsg);
    console.log(JSON.stringify(data, null, '  '));
    callback(result, data || {
      errMsg: '该模拟接口不存在'
    }, delay);
  });
};

router = express.Router();

router.use('/', function(req, res) {
  var business, encryptKey;
  encryptKey = req.session.encryptKey;
  console.log(req.body);
  business = CommonPackage.getBusiness(req.body, {
    key: encryptKey
  });
  business = JSON.parse(business);
  console.log(business);
  fetchMockData(req.url, business, function(result, data, delay) {
    if (!delay || DEBUG_MOCK_DELAY_USEDEFAULT) {
      delay = DEBUG_MOCK_DELAY_DEFAULT;
    }
    return setTimeout((function() {
      var pkg;
      res.setHeader('sessionState', 100);
      pkg = new CommonPackage(data, {
        key: encryptKey
      });
      return res.end(JSON.stringify(pkg));
    }), delay);
  });
});

module.exports = router;
