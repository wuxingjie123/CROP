// Generated by CoffeeScript 1.10.0
var mkMsg;

mkMsg = function(defaultMsg, customMsg, append) {
  if (append === false) {
    return customMsg || defaultMsg;
  } else {
    return defaultMsg + "[" + customMsg + "]";
  }
};

module.exports = {
  Success: {
    Info: function(msg1) {
      this.msg = msg1;
      this.result = 0;
    },
    Data: function(data) {
      this.data = data;
      this.result = 0;
    },
    Value: function(value) {
      this.value = value;
      this.result = 0;
    }
  },
  Error: {
    EmulatorNotInitError: function(msg, append) {
      this.msg = mkMsg('模拟器未初始化!', msg, append);
      this.result = 8404;
    },
    EmulatorSendDataFailedError: function(msg, append) {
      this.msg = mkMsg('模拟器发送数据失败!', msg, append);
      this.result = 8501;
    },
    EmulatorInitFailedError: function(msg, append) {
      this.msg = mkMsg('模拟器初始化失败!', msg, append);
      this.result = 8500;
    }
  }
};
