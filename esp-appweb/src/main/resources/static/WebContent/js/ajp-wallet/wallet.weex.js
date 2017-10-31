// { "framework": "Vue" }

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _App = __webpack_require__(1);

	var _App2 = _interopRequireDefault(_App);

	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	var _index3 = __webpack_require__(30);

	var _index4 = _interopRequireDefault(_index3);

	var _index5 = __webpack_require__(157);

	var _index6 = _interopRequireDefault(_index5);

	var _index7 = __webpack_require__(274);

	var _index8 = _interopRequireDefault(_index7);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	Vue.use(_index4.default

	// create the app instance.1123
	// here we inject the router and store to all child components,
	// making them available everywhere as `this.$router` and `this.$store`.
	// to use until by `this.$options.untils`
	); /**
	    * Created by x298017064010 on 17/6/12.
	    */

	exports.default = new Vue(Vue.util.extend({ el: '#root', router: _index6.default, store: _index2.default, event: _index8.default }, _App2.default));


	_index6.default.push('/');

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* script */
	__vue_exports__ = __webpack_require__(2)

	/* template */
	var __vue_template__ = __webpack_require__(3)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/App.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 2 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//


	exports.default = {
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {
	        console.log(this.$getConfig()

	        // 1.存储路由
	        );this.$store.state.router = this.$router;
	        // 2.存储系统数据
	        this.$store.state.env = this.$getConfig().env;
	        this.$store.state.os = this.$store.state.env.platform;
	        this.$store.state.screenHeight = this.$store.state.env.deviceHeight / (this.$store.state.env.deviceWidth + 50) * 750;
	        // 3.存储原生数据
	        var localData = this.$getConfig().localData;
	        this.$store.dispatch('LOCAL_DATA', localData);
	    },
	    mounted: function mounted() {}
	};

/***/ }),
/* 3 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', [_c('router-view', {
	    staticStyle: {
	      flex: "1"
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _vuex = __webpack_require__(5);

	var _vuex2 = _interopRequireDefault(_vuex);

	var _mutations = __webpack_require__(6);

	var mutations = _interopRequireWildcard(_mutations);

	var _actions = __webpack_require__(7);

	var actions = _interopRequireWildcard(_actions);

	var _getters = __webpack_require__(8);

	var getters = _interopRequireWildcard(_getters);

	var _MobileAuth = __webpack_require__(9);

	var _MobileAuth2 = _interopRequireDefault(_MobileAuth);

	var _Auth = __webpack_require__(12);

	var _Auth2 = _interopRequireDefault(_Auth);

	var _Face = __webpack_require__(15);

	var _Face2 = _interopRequireDefault(_Face);

	var _LinkMan = __webpack_require__(18);

	var _LinkMan2 = _interopRequireDefault(_LinkMan);

	var _Withdraw = __webpack_require__(21);

	var _Withdraw2 = _interopRequireDefault(_Withdraw);

	var _Commission = __webpack_require__(24);

	var _Commission2 = _interopRequireDefault(_Commission);

	var _commissionApply = __webpack_require__(27);

	var _commissionApply2 = _interopRequireDefault(_commissionApply);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	// Vuex is auto installed on the web


	// 佣金分期模块
	if (WXEnvironment.platform !== 'Web') {
	    Vue.use(_vuex2.default);
	}

	// 钱包模块
	/**
	 * Created by x298017064010 on 17/6/12.
	 */

	var store = new _vuex2.default.Store({

	    mutations: mutations,
	    actions: actions,
	    getters: getters,
	    modules: {
	        auth: _Auth2.default,
	        face: _Face2.default,
	        mobileAuth: _MobileAuth2.default,
	        withdraw: _Withdraw2.default,

	        linkInfo: _LinkMan2.default,
	        commApply: _commissionApply2.default,

	        commsWL: _Commission2.default
	    },

	    // 初始化整个应用状态 this.$store.state.count
	    state: {
	        updateVer: {
	            wallet: {
	                wallet_sit_ver: 24,
	                wallet_prd_ver: 8
	            },
	            commission: {
	                commission_sit_ver: 35,
	                commission_prd_ver: 28
	            }
	        },

	        debug: false, // debug模式(用于调试)
	        prd: false, // 生产环境

	        icons: {}, // 存储图片路径

	        isDidClick: false, // 防抖
	        router: {},
	        errNetParam: {}, // 网络断开, 刷新参数

	        banner_src: '',

	        env: {},
	        os: '',
	        screenHeight: '',

	        mobile: '', // 用户名
	        token: '', // 登陆验证
	        customerId: '', // 客户号
	        appId: '', //  appId
	        lastVersion: '', //  最新版本号
	        imagePath: '' //  图片路径
	    }
	});

	exports.default = store;

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

	/**
	 * vuex v2.3.0
	 * (c) 2017 Evan You
	 * @license MIT
	 */
	(function (global, factory) {
		 true ? module.exports = factory() :
		typeof define === 'function' && define.amd ? define(factory) :
		(global.Vuex = factory());
	}(this, (function () { 'use strict';

	var applyMixin = function (Vue) {
	  var version = Number(Vue.version.split('.')[0]);

	  if (version >= 2) {
	    var usesInit = Vue.config._lifecycleHooks.indexOf('init') > -1;
	    Vue.mixin(usesInit ? { init: vuexInit } : { beforeCreate: vuexInit });
	  } else {
	    // override init and inject vuex init procedure
	    // for 1.x backwards compatibility.
	    var _init = Vue.prototype._init;
	    Vue.prototype._init = function (options) {
	      if ( options === void 0 ) options = {};

	      options.init = options.init
	        ? [vuexInit].concat(options.init)
	        : vuexInit;
	      _init.call(this, options);
	    };
	  }

	  /**
	   * Vuex init hook, injected into each instances init hooks list.
	   */

	  function vuexInit () {
	    var options = this.$options;
	    // store injection
	    if (options.store) {
	      this.$store = options.store;
	    } else if (options.parent && options.parent.$store) {
	      this.$store = options.parent.$store;
	    }
	  }
	};

	var devtoolHook =
	  typeof window !== 'undefined' &&
	  window.__VUE_DEVTOOLS_GLOBAL_HOOK__;

	function devtoolPlugin (store) {
	  if (!devtoolHook) { return }

	  store._devtoolHook = devtoolHook;

	  devtoolHook.emit('vuex:init', store);

	  devtoolHook.on('vuex:travel-to-state', function (targetState) {
	    store.replaceState(targetState);
	  });

	  store.subscribe(function (mutation, state) {
	    devtoolHook.emit('vuex:mutation', mutation, state);
	  });
	}

	/**
	 * Get the first item that pass the test
	 * by second argument function
	 *
	 * @param {Array} list
	 * @param {Function} f
	 * @return {*}
	 */
	/**
	 * Deep copy the given object considering circular structure.
	 * This function caches all nested objects and its copies.
	 * If it detects circular structure, use cached copy to avoid infinite loop.
	 *
	 * @param {*} obj
	 * @param {Array<Object>} cache
	 * @return {*}
	 */


	/**
	 * forEach for object
	 */
	function forEachValue (obj, fn) {
	  Object.keys(obj).forEach(function (key) { return fn(obj[key], key); });
	}

	function isObject (obj) {
	  return obj !== null && typeof obj === 'object'
	}

	function isPromise (val) {
	  return val && typeof val.then === 'function'
	}

	function assert (condition, msg) {
	  if (!condition) { throw new Error(("[vuex] " + msg)) }
	}

	var Module = function Module (rawModule, runtime) {
	  this.runtime = runtime;
	  this._children = Object.create(null);
	  this._rawModule = rawModule;
	  var rawState = rawModule.state;
	  this.state = (typeof rawState === 'function' ? rawState() : rawState) || {};
	};

	var prototypeAccessors$1 = { namespaced: {} };

	prototypeAccessors$1.namespaced.get = function () {
	  return !!this._rawModule.namespaced
	};

	Module.prototype.addChild = function addChild (key, module) {
	  this._children[key] = module;
	};

	Module.prototype.removeChild = function removeChild (key) {
	  delete this._children[key];
	};

	Module.prototype.getChild = function getChild (key) {
	  return this._children[key]
	};

	Module.prototype.update = function update (rawModule) {
	  this._rawModule.namespaced = rawModule.namespaced;
	  if (rawModule.actions) {
	    this._rawModule.actions = rawModule.actions;
	  }
	  if (rawModule.mutations) {
	    this._rawModule.mutations = rawModule.mutations;
	  }
	  if (rawModule.getters) {
	    this._rawModule.getters = rawModule.getters;
	  }
	};

	Module.prototype.forEachChild = function forEachChild (fn) {
	  forEachValue(this._children, fn);
	};

	Module.prototype.forEachGetter = function forEachGetter (fn) {
	  if (this._rawModule.getters) {
	    forEachValue(this._rawModule.getters, fn);
	  }
	};

	Module.prototype.forEachAction = function forEachAction (fn) {
	  if (this._rawModule.actions) {
	    forEachValue(this._rawModule.actions, fn);
	  }
	};

	Module.prototype.forEachMutation = function forEachMutation (fn) {
	  if (this._rawModule.mutations) {
	    forEachValue(this._rawModule.mutations, fn);
	  }
	};

	Object.defineProperties( Module.prototype, prototypeAccessors$1 );

	var ModuleCollection = function ModuleCollection (rawRootModule) {
	  var this$1 = this;

	  // register root module (Vuex.Store options)
	  this.root = new Module(rawRootModule, false);

	  // register all nested modules
	  if (rawRootModule.modules) {
	    forEachValue(rawRootModule.modules, function (rawModule, key) {
	      this$1.register([key], rawModule, false);
	    });
	  }
	};

	ModuleCollection.prototype.get = function get (path) {
	  return path.reduce(function (module, key) {
	    return module.getChild(key)
	  }, this.root)
	};

	ModuleCollection.prototype.getNamespace = function getNamespace (path) {
	  var module = this.root;
	  return path.reduce(function (namespace, key) {
	    module = module.getChild(key);
	    return namespace + (module.namespaced ? key + '/' : '')
	  }, '')
	};

	ModuleCollection.prototype.update = function update$1 (rawRootModule) {
	  update(this.root, rawRootModule);
	};

	ModuleCollection.prototype.register = function register (path, rawModule, runtime) {
	    var this$1 = this;
	    if ( runtime === void 0 ) runtime = true;

	  var parent = this.get(path.slice(0, -1));
	  var newModule = new Module(rawModule, runtime);
	  parent.addChild(path[path.length - 1], newModule);

	  // register nested modules
	  if (rawModule.modules) {
	    forEachValue(rawModule.modules, function (rawChildModule, key) {
	      this$1.register(path.concat(key), rawChildModule, runtime);
	    });
	  }
	};

	ModuleCollection.prototype.unregister = function unregister (path) {
	  var parent = this.get(path.slice(0, -1));
	  var key = path[path.length - 1];
	  if (!parent.getChild(key).runtime) { return }

	  parent.removeChild(key);
	};

	function update (targetModule, newModule) {
	  // update target module
	  targetModule.update(newModule);

	  // update nested modules
	  if (newModule.modules) {
	    for (var key in newModule.modules) {
	      if (!targetModule.getChild(key)) {
	        console.warn(
	          "[vuex] trying to add a new module '" + key + "' on hot reloading, " +
	          'manual reload is needed'
	        );
	        return
	      }
	      update(targetModule.getChild(key), newModule.modules[key]);
	    }
	  }
	}

	var Vue; // bind on install

	var Store = function Store (options) {
	  var this$1 = this;
	  if ( options === void 0 ) options = {};

	  assert(Vue, "must call Vue.use(Vuex) before creating a store instance.");
	  assert(typeof Promise !== 'undefined', "vuex requires a Promise polyfill in this browser.");

	  var state = options.state; if ( state === void 0 ) state = {};
	  var plugins = options.plugins; if ( plugins === void 0 ) plugins = [];
	  var strict = options.strict; if ( strict === void 0 ) strict = false;

	  // store internal state
	  this._committing = false;
	  this._actions = Object.create(null);
	  this._mutations = Object.create(null);
	  this._wrappedGetters = Object.create(null);
	  this._modules = new ModuleCollection(options);
	  this._modulesNamespaceMap = Object.create(null);
	  this._subscribers = [];
	  this._watcherVM = new Vue();

	  // bind commit and dispatch to self
	  var store = this;
	  var ref = this;
	  var dispatch = ref.dispatch;
	  var commit = ref.commit;
	  this.dispatch = function boundDispatch (type, payload) {
	    return dispatch.call(store, type, payload)
	  };
	  this.commit = function boundCommit (type, payload, options) {
	    return commit.call(store, type, payload, options)
	  };

	  // strict mode
	  this.strict = strict;

	  // init root module.
	  // this also recursively registers all sub-modules
	  // and collects all module getters inside this._wrappedGetters
	  installModule(this, state, [], this._modules.root);

	  // initialize the store vm, which is responsible for the reactivity
	  // (also registers _wrappedGetters as computed properties)
	  resetStoreVM(this, state);

	  // apply plugins
	  plugins.concat(devtoolPlugin).forEach(function (plugin) { return plugin(this$1); });
	};

	var prototypeAccessors = { state: {} };

	prototypeAccessors.state.get = function () {
	  return this._vm._data.$$state
	};

	prototypeAccessors.state.set = function (v) {
	  assert(false, "Use store.replaceState() to explicit replace store state.");
	};

	Store.prototype.commit = function commit (_type, _payload, _options) {
	    var this$1 = this;

	  // check object-style commit
	  var ref = unifyObjectStyle(_type, _payload, _options);
	    var type = ref.type;
	    var payload = ref.payload;
	    var options = ref.options;

	  var mutation = { type: type, payload: payload };
	  var entry = this._mutations[type];
	  if (!entry) {
	    console.error(("[vuex] unknown mutation type: " + type));
	    return
	  }
	  this._withCommit(function () {
	    entry.forEach(function commitIterator (handler) {
	      handler(payload);
	    });
	  });
	  this._subscribers.forEach(function (sub) { return sub(mutation, this$1.state); });

	  if (options && options.silent) {
	    console.warn(
	      "[vuex] mutation type: " + type + ". Silent option has been removed. " +
	      'Use the filter functionality in the vue-devtools'
	    );
	  }
	};

	Store.prototype.dispatch = function dispatch (_type, _payload) {
	  // check object-style dispatch
	  var ref = unifyObjectStyle(_type, _payload);
	    var type = ref.type;
	    var payload = ref.payload;

	  var entry = this._actions[type];
	  if (!entry) {
	    console.error(("[vuex] unknown action type: " + type));
	    return
	  }
	  return entry.length > 1
	    ? Promise.all(entry.map(function (handler) { return handler(payload); }))
	    : entry[0](payload)
	};

	Store.prototype.subscribe = function subscribe (fn) {
	  var subs = this._subscribers;
	  if (subs.indexOf(fn) < 0) {
	    subs.push(fn);
	  }
	  return function () {
	    var i = subs.indexOf(fn);
	    if (i > -1) {
	      subs.splice(i, 1);
	    }
	  }
	};

	Store.prototype.watch = function watch (getter, cb, options) {
	    var this$1 = this;

	  assert(typeof getter === 'function', "store.watch only accepts a function.");
	  return this._watcherVM.$watch(function () { return getter(this$1.state, this$1.getters); }, cb, options)
	};

	Store.prototype.replaceState = function replaceState (state) {
	    var this$1 = this;

	  this._withCommit(function () {
	    this$1._vm._data.$$state = state;
	  });
	};

	Store.prototype.registerModule = function registerModule (path, rawModule) {
	  if (typeof path === 'string') { path = [path]; }
	  assert(Array.isArray(path), "module path must be a string or an Array.");
	  this._modules.register(path, rawModule);
	  installModule(this, this.state, path, this._modules.get(path));
	  // reset store to update getters...
	  resetStoreVM(this, this.state);
	};

	Store.prototype.unregisterModule = function unregisterModule (path) {
	    var this$1 = this;

	  if (typeof path === 'string') { path = [path]; }
	  assert(Array.isArray(path), "module path must be a string or an Array.");
	  this._modules.unregister(path);
	  this._withCommit(function () {
	    var parentState = getNestedState(this$1.state, path.slice(0, -1));
	    Vue.delete(parentState, path[path.length - 1]);
	  });
	  resetStore(this);
	};

	Store.prototype.hotUpdate = function hotUpdate (newOptions) {
	  this._modules.update(newOptions);
	  resetStore(this, true);
	};

	Store.prototype._withCommit = function _withCommit (fn) {
	  var committing = this._committing;
	  this._committing = true;
	  fn();
	  this._committing = committing;
	};

	Object.defineProperties( Store.prototype, prototypeAccessors );

	function resetStore (store, hot) {
	  store._actions = Object.create(null);
	  store._mutations = Object.create(null);
	  store._wrappedGetters = Object.create(null);
	  store._modulesNamespaceMap = Object.create(null);
	  var state = store.state;
	  // init all modules
	  installModule(store, state, [], store._modules.root, true);
	  // reset vm
	  resetStoreVM(store, state, hot);
	}

	function resetStoreVM (store, state, hot) {
	  var oldVm = store._vm;

	  // bind store public getters
	  store.getters = {};
	  var wrappedGetters = store._wrappedGetters;
	  var computed = {};
	  forEachValue(wrappedGetters, function (fn, key) {
	    // use computed to leverage its lazy-caching mechanism
	    computed[key] = function () { return fn(store); };
	    Object.defineProperty(store.getters, key, {
	      get: function () { return store._vm[key]; },
	      enumerable: true // for local getters
	    });
	  });

	  // use a Vue instance to store the state tree
	  // suppress warnings just in case the user has added
	  // some funky global mixins
	  var silent = Vue.config.silent;
	  Vue.config.silent = true;
	  store._vm = new Vue({
	    data: {
	      $$state: state
	    },
	    computed: computed
	  });
	  Vue.config.silent = silent;

	  // enable strict mode for new vm
	  if (store.strict) {
	    enableStrictMode(store);
	  }

	  if (oldVm) {
	    if (hot) {
	      // dispatch changes in all subscribed watchers
	      // to force getter re-evaluation for hot reloading.
	      store._withCommit(function () {
	        oldVm._data.$$state = null;
	      });
	    }
	    Vue.nextTick(function () { return oldVm.$destroy(); });
	  }
	}

	function installModule (store, rootState, path, module, hot) {
	  var isRoot = !path.length;
	  var namespace = store._modules.getNamespace(path);

	  // register in namespace map
	  if (module.namespaced) {
	    store._modulesNamespaceMap[namespace] = module;
	  }

	  // set state
	  if (!isRoot && !hot) {
	    var parentState = getNestedState(rootState, path.slice(0, -1));
	    var moduleName = path[path.length - 1];
	    store._withCommit(function () {
	      Vue.set(parentState, moduleName, module.state);
	    });
	  }

	  var local = module.context = makeLocalContext(store, namespace, path);

	  module.forEachMutation(function (mutation, key) {
	    var namespacedType = namespace + key;
	    registerMutation(store, namespacedType, mutation, local);
	  });

	  module.forEachAction(function (action, key) {
	    var namespacedType = namespace + key;
	    registerAction(store, namespacedType, action, local);
	  });

	  module.forEachGetter(function (getter, key) {
	    var namespacedType = namespace + key;
	    registerGetter(store, namespacedType, getter, local);
	  });

	  module.forEachChild(function (child, key) {
	    installModule(store, rootState, path.concat(key), child, hot);
	  });
	}

	/**
	 * make localized dispatch, commit, getters and state
	 * if there is no namespace, just use root ones
	 */
	function makeLocalContext (store, namespace, path) {
	  var noNamespace = namespace === '';

	  var local = {
	    dispatch: noNamespace ? store.dispatch : function (_type, _payload, _options) {
	      var args = unifyObjectStyle(_type, _payload, _options);
	      var payload = args.payload;
	      var options = args.options;
	      var type = args.type;

	      if (!options || !options.root) {
	        type = namespace + type;
	        if (!store._actions[type]) {
	          console.error(("[vuex] unknown local action type: " + (args.type) + ", global type: " + type));
	          return
	        }
	      }

	      return store.dispatch(type, payload)
	    },

	    commit: noNamespace ? store.commit : function (_type, _payload, _options) {
	      var args = unifyObjectStyle(_type, _payload, _options);
	      var payload = args.payload;
	      var options = args.options;
	      var type = args.type;

	      if (!options || !options.root) {
	        type = namespace + type;
	        if (!store._mutations[type]) {
	          console.error(("[vuex] unknown local mutation type: " + (args.type) + ", global type: " + type));
	          return
	        }
	      }

	      store.commit(type, payload, options);
	    }
	  };

	  // getters and state object must be gotten lazily
	  // because they will be changed by vm update
	  Object.defineProperties(local, {
	    getters: {
	      get: noNamespace
	        ? function () { return store.getters; }
	        : function () { return makeLocalGetters(store, namespace); }
	    },
	    state: {
	      get: function () { return getNestedState(store.state, path); }
	    }
	  });

	  return local
	}

	function makeLocalGetters (store, namespace) {
	  var gettersProxy = {};

	  var splitPos = namespace.length;
	  Object.keys(store.getters).forEach(function (type) {
	    // skip if the target getter is not match this namespace
	    if (type.slice(0, splitPos) !== namespace) { return }

	    // extract local getter type
	    var localType = type.slice(splitPos);

	    // Add a port to the getters proxy.
	    // Define as getter property because
	    // we do not want to evaluate the getters in this time.
	    Object.defineProperty(gettersProxy, localType, {
	      get: function () { return store.getters[type]; },
	      enumerable: true
	    });
	  });

	  return gettersProxy
	}

	function registerMutation (store, type, handler, local) {
	  var entry = store._mutations[type] || (store._mutations[type] = []);
	  entry.push(function wrappedMutationHandler (payload) {
	    handler(local.state, payload);
	  });
	}

	function registerAction (store, type, handler, local) {
	  var entry = store._actions[type] || (store._actions[type] = []);
	  entry.push(function wrappedActionHandler (payload, cb) {
	    var res = handler({
	      dispatch: local.dispatch,
	      commit: local.commit,
	      getters: local.getters,
	      state: local.state,
	      rootGetters: store.getters,
	      rootState: store.state
	    }, payload, cb);
	    if (!isPromise(res)) {
	      res = Promise.resolve(res);
	    }
	    if (store._devtoolHook) {
	      return res.catch(function (err) {
	        store._devtoolHook.emit('vuex:error', err);
	        throw err
	      })
	    } else {
	      return res
	    }
	  });
	}

	function registerGetter (store, type, rawGetter, local) {
	  if (store._wrappedGetters[type]) {
	    console.error(("[vuex] duplicate getter key: " + type));
	    return
	  }
	  store._wrappedGetters[type] = function wrappedGetter (store) {
	    return rawGetter(
	      local.state, // local state
	      local.getters, // local getters
	      store.state, // root state
	      store.getters // root getters
	    )
	  };
	}

	function enableStrictMode (store) {
	  store._vm.$watch(function () { return this._data.$$state }, function () {
	    assert(store._committing, "Do not mutate vuex store state outside mutation handlers.");
	  }, { deep: true, sync: true });
	}

	function getNestedState (state, path) {
	  return path.length
	    ? path.reduce(function (state, key) { return state[key]; }, state)
	    : state
	}

	function unifyObjectStyle (type, payload, options) {
	  if (isObject(type) && type.type) {
	    options = payload;
	    payload = type;
	    type = type.type;
	  }

	  assert(typeof type === 'string', ("Expects string as the type, but found " + (typeof type) + "."));

	  return { type: type, payload: payload, options: options }
	}

	function install (_Vue) {
	  if (Vue) {
	    console.error(
	      '[vuex] already installed. Vue.use(Vuex) should be called only once.'
	    );
	    return
	  }
	  Vue = _Vue;
	  applyMixin(Vue);
	}

	// auto install in dist mode
	if (typeof window !== 'undefined' && window.Vue) {
	  install(window.Vue);
	}

	var mapState = normalizeNamespace(function (namespace, states) {
	  var res = {};
	  normalizeMap(states).forEach(function (ref) {
	    var key = ref.key;
	    var val = ref.val;

	    res[key] = function mappedState () {
	      var state = this.$store.state;
	      var getters = this.$store.getters;
	      if (namespace) {
	        var module = getModuleByNamespace(this.$store, 'mapState', namespace);
	        if (!module) {
	          return
	        }
	        state = module.context.state;
	        getters = module.context.getters;
	      }
	      return typeof val === 'function'
	        ? val.call(this, state, getters)
	        : state[val]
	    };
	    // mark vuex getter for devtools
	    res[key].vuex = true;
	  });
	  return res
	});

	var mapMutations = normalizeNamespace(function (namespace, mutations) {
	  var res = {};
	  normalizeMap(mutations).forEach(function (ref) {
	    var key = ref.key;
	    var val = ref.val;

	    val = namespace + val;
	    res[key] = function mappedMutation () {
	      var args = [], len = arguments.length;
	      while ( len-- ) args[ len ] = arguments[ len ];

	      if (namespace && !getModuleByNamespace(this.$store, 'mapMutations', namespace)) {
	        return
	      }
	      return this.$store.commit.apply(this.$store, [val].concat(args))
	    };
	  });
	  return res
	});

	var mapGetters = normalizeNamespace(function (namespace, getters) {
	  var res = {};
	  normalizeMap(getters).forEach(function (ref) {
	    var key = ref.key;
	    var val = ref.val;

	    val = namespace + val;
	    res[key] = function mappedGetter () {
	      if (namespace && !getModuleByNamespace(this.$store, 'mapGetters', namespace)) {
	        return
	      }
	      if (!(val in this.$store.getters)) {
	        console.error(("[vuex] unknown getter: " + val));
	        return
	      }
	      return this.$store.getters[val]
	    };
	    // mark vuex getter for devtools
	    res[key].vuex = true;
	  });
	  return res
	});

	var mapActions = normalizeNamespace(function (namespace, actions) {
	  var res = {};
	  normalizeMap(actions).forEach(function (ref) {
	    var key = ref.key;
	    var val = ref.val;

	    val = namespace + val;
	    res[key] = function mappedAction () {
	      var args = [], len = arguments.length;
	      while ( len-- ) args[ len ] = arguments[ len ];

	      if (namespace && !getModuleByNamespace(this.$store, 'mapActions', namespace)) {
	        return
	      }
	      return this.$store.dispatch.apply(this.$store, [val].concat(args))
	    };
	  });
	  return res
	});

	function normalizeMap (map) {
	  return Array.isArray(map)
	    ? map.map(function (key) { return ({ key: key, val: key }); })
	    : Object.keys(map).map(function (key) { return ({ key: key, val: map[key] }); })
	}

	function normalizeNamespace (fn) {
	  return function (namespace, map) {
	    if (typeof namespace !== 'string') {
	      map = namespace;
	      namespace = '';
	    } else if (namespace.charAt(namespace.length - 1) !== '/') {
	      namespace += '/';
	    }
	    return fn(namespace, map)
	  }
	}

	function getModuleByNamespace (store, helper, namespace) {
	  var module = store._modulesNamespaceMap[namespace];
	  if (!module) {
	    console.error(("[vuex] module namespace not found in " + helper + "(): " + namespace));
	  }
	  return module
	}

	var index = {
	  Store: Store,
	  install: install,
	  version: '2.3.0',
	  mapState: mapState,
	  mapMutations: mapMutations,
	  mapGetters: mapGetters,
	  mapActions: mapActions
	};

	return index;

	})));


/***/ }),
/* 6 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.DEMO_MUTATION = DEMO_MUTATION;
	/**
	 * Created by x298017064010 on 17/6/12.
	 *
	 * 更改应用状态的唯一方法(同步事务), 调用: this.$store.commit('increment', otherProps)
	 */

	function DEMO_MUTATION(state, payload) {
	  console.log(state.count, payload);
	}

/***/ }),
/* 7 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.LOCAL_DATA = LOCAL_DATA;
	/**
	 * Created by x298017064010 on 17/6/12.
	 *
	 * 这里是提交mutations, 而不是直接更改状态(异步事务), 调用: this.$store.dispatch('DEMO_ACTION_ASYNC')
	 */

	// 本地数据
	function LOCAL_DATA(_ref, payload) {
	  var commit = _ref.commit,
	      state = _ref.state;


	  state.mobile = payload.mobile;
	  state.token = payload.token;
	  state.customerId = payload.customerId;
	}

/***/ }),
/* 8 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.DEMO_GETTER = DEMO_GETTER;
	/**
	 * Created by x298017064010 on 17/6/12.
	 *
	 * 相当于store 的计算属性方便子组件在任意场合调用, 调用: this.$store.getters.getCurrentCount
	 */

	// root
	function DEMO_GETTER(state) {
	  return state.count;
	}

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _MobileAuthMutations = __webpack_require__(10);

	var mutations = _interopRequireWildcard(_MobileAuthMutations);

	var _MobileAuthActions = __webpack_require__(11);

	var actions = _interopRequireWildcard(_MobileAuthActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var mobileAuth = {
	    actions: actions,
	    mutations: mutations,
	    state: {
	        account: '',
	        password: '',
	        captcha: '',

	        token: '', // 令牌
	        website: '' // 运营商
	    }
	};

	exports.default = mobileAuth;

/***/ }),
/* 10 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.PHONE_INITIALIZE = PHONE_INITIALIZE;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	function PHONE_INITIALIZE(state, payload) {
	    console.log('PHONE_INITIALIZE - payload: ', payload

	    // 特殊处理, 存主module
	    );state.token = payload.token == null ? '' : payload.token;
	    state.website = payload.website == null ? '' : payload.website;

	    console.log('PHONE_INITIALIZE - store: ', state);
	}

/***/ }),
/* 11 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.PHONE_INITIALIZE = PHONE_INITIALIZE;
	exports.PHONE_SUBMITINFORMATION = PHONE_SUBMITINFORMATION;
	exports.PHONE_COMPARINGINFORMATION = PHONE_COMPARINGINFORMATION;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	// 初始化数据
	function PHONE_INITIALIZE(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        rootState = _ref.rootState;

	    Vue.TipHelper.showHub(0);

	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        realName: rootState.auth.realName,
	        identityNo: rootState.auth.identityNo,
	        repeatFlag: '1'
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.PHONE_INITIALIZE, prama, function (res) {
	        if (res.status == 1) {
	            commit('PHONE_INITIALIZE', res.data);
	            payload.callback();
	        } else {
	            Vue.TipHelper.dismisHub();
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 提交信息
	function PHONE_SUBMITINFORMATION(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;


	    Vue.TipHelper.showHub(0);

	    var prama = {};
	    if (state.captcha == '') {
	        prama = {
	            customerId: rootState.customerId,
	            "x-auth-token": rootState.token,
	            realName: rootState.auth.realName,
	            identityNo: rootState.auth.identityNo,
	            token: state.token,
	            website: state.website,
	            account: state.account,
	            password: state.password
	        };
	    } else {
	        prama = {
	            customerId: rootState.customerId,
	            "x-auth-token": rootState.token,
	            realName: rootState.auth.realName,
	            identityNo: rootState.auth.identityNo,
	            token: state.token,
	            website: state.website,
	            account: state.account,
	            password: state.password,
	            captcha: state.captcha, // 随机码
	            type: 'SUBMIT_CAPTCHA' //  随机码不为空的时候
	        };
	    }

	    Vue.HttpHelper.post(Vue.UrlMacro.PHONE_SUBMITINFORMATION, prama, function (res) {
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.dismisHub();
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 比对信息
	function PHONE_COMPARINGINFORMATION(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;

	    Vue.TipHelper.showHub(0);

	    var prama = {};
	    if (state.captcha == '') {
	        prama = {
	            customerId: rootState.customerId,
	            "x-auth-token": rootState.token,
	            realName: rootState.auth.realName,
	            identityNo: rootState.auth.identityNo,
	            token: state.token,
	            website: state.website,
	            account: state.account,
	            password: state.password
	        };
	    } else {
	        prama = {
	            customerId: rootState.customerId,
	            "x-auth-token": rootState.token,
	            realName: rootState.auth.realName,
	            identityNo: rootState.auth.identityNo,
	            token: state.token,
	            website: state.website,
	            account: state.account,
	            password: state.password,
	            captcha: state.captcha, // 随机码
	            type: 'SUBMIT_CAPTCHA' //  随机码不为空的时候
	        };
	    }

	    Vue.HttpHelper.post(Vue.UrlMacro.PHONE_COMPARINGINFORMATION, prama, function (res) {
	        // 处理loading图
	        if (res.data.type != 'RUNNING') {
	            Vue.TipHelper.dismisHub();
	        }
	        // 处理数据
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.dismisHub();
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _AuthMutations = __webpack_require__(13);

	var mutations = _interopRequireWildcard(_AuthMutations);

	var _AuthActions = __webpack_require__(14);

	var actions = _interopRequireWildcard(_AuthActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var auth = {
	    actions: actions,
	    mutations: mutations,

	    state: {
	        agent: '', // 客户端平台
	        id: '', // 客户id
	        createdDate: '',
	        updatedDate: '',
	        creditApplyType: '',
	        creditSignFailureNum: '', // 签字优化需求
	        isFromEsp: '', // 1 : 转介绍过来, -1非转介绍
	        repeatApply: '', // 重复申请

	        // -------------- 钱包接口
	        page: '', // 钱包状态 page值

	        ageCondition: '', // 用户的年龄是否符合提现（0为不符合，1为符合）
	        applyCreditDate: '', // 额度申请日期
	        auditStatus: '', // 电审状态（-1：电审拒绝；0：待激活；1：电审申请中；2：电审通过）
	        availableAmount: '', // 可用额度
	        riskLevel: '', // 1-高风险客服
	        billDay: '',
	        creditRejRemainDate: '', // 激活额度锁单天数, page=301
	        customerFlag: '', // 新老客户标记 0:老客户，1：新客户
	        customerId: '', // 客户号
	        customerStatus: '', // 客户状态值
	        expireDate: '', // 额度失效时间
	        // identityExpires: '',    // 身份证有效期
	        // identityNo: '',         // 身份证号码
	        // isZmAuth: '',
	        loanAmount: '', // 提现金额
	        loanDate: '', // 提现时间
	        lockDays: '', // 申请额度/锁单天数
	        // mobile:'',              // 账号 手机号
	        noReadCount: '', // 推送消息未读数
	        // realName: '',           // 客户姓名
	        rejectTimeLimit: '',
	        repayDay: '',
	        sltAccountId: '',
	        supportAdvanceFlag: '', // 是否可以提升额度 ( 是否支持提额（false或true）)
	        totalAmount: '', // 授信总额度
	        totalWithdrawAmount: '',
	        userId: '',

	        // -------------- 身份验证
	        status: '', // 验证状态

	        mobile: '', // 电话
	        openId: '',
	        remindRepayment: '',
	        signatureAuditStatus: '', // 审核状态（ -1:拒绝; 0：审核中；1：通过） 200为空

	        identityNo: '', // 身份证号
	        identityExpires: '', // 身份证有效期
	        identityPerson: '', // 是否上传过手持身份证   1 - 是
	        identityRecognizeAddress: '', //身份证地址
	        identityRecognizeName: '', // 身份证姓名

	        realName: '', // 真实姓名
	        educationDegree: '', // 学历
	        marryStatus: '', // 婚姻状态

	        blackLock: '', // 是否黑名单锁单(1-是 0-否）
	        blackLockDays: '', // 黑名单锁单天数

	        // ---------  信息认证
	        monthlyIncomeCard: '',
	        monthlyIncomeCash: '',
	        industry: '',
	        profession: '',
	        payFundOrSecurity: '',
	        mobileAuthStatus: '', // 手机认证状态 未认证：0, 认证中：1, 已认证：2
	        mobileAuthFlag: '', // 手机认证标识: wait / fail / null (Reviewing) / noauth / auth
	        isZmAuth: '', // 芝麻认证状态: 未认证: 0, 已认证: 1

	        // ---------- 银行卡认证
	        localRegistry: '',
	        mobileAuthDate: '',
	        mobileAuthIdentity: '',
	        mobileRepeatApply: '',
	        mobileReportID: '',

	        bankInfoId: '',
	        bankCode: '',
	        cardBank: '',
	        cardNo: '',
	        cardType: 'CR',

	        bankMobile: '', // 银行卡预留电话

	        // ---------- 反导流产品
	        productList: [],
	        isRefresh: false
	        // id	        String	产品Id
	        // productName	String	产品名称
	        // productLogo	String	产品logo
	        // productUrl	String	产品url
	        // producDesc	String	产品描述
	        // displayOrder	String	产品排序 小在前
	    }
	};

	exports.default = auth;

/***/ }),
/* 13 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.HOME_PAGE = HOME_PAGE;
	exports.IDENTITY_INIT = IDENTITY_INIT;
	exports.INFOAUTH_INIT = INFOAUTH_INIT;
	exports.BANKCARD_INIT = BANKCARD_INIT;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	function HOME_PAGE(state, payload) {

	    for (var i in payload) {
	        if (payload.hasOwnProperty(i)) {
	            for (var key in state) {
	                if (payload.hasOwnProperty(key)) {
	                    if (i === key) {
	                        if (i == 'customerId' || i == 'userId') {
	                            state[key] = String(payload[i]) == null ? '' : String(payload[i]);
	                        } else {
	                            state[key] = payload[i] == null ? '' : payload[i];
	                        }
	                    }
	                }
	            }
	        }
	    }
	    console.log('HOME_PAGE - store: ', state);
	}

	// 身份认证初始化
	function IDENTITY_INIT(state, payload) {

	    state.creditApplyType = payload.creditApplyType == null ? '' : payload.creditApplyType;
	    state.isFromEsp = payload.isFromEsp == null ? '' : payload.isFromEsp;
	    state.repeatApply = payload.repeatApply == null ? '' : payload.repeatApply;

	    var data = payload.customer;

	    for (var i in data) {
	        if (data.hasOwnProperty(i)) {
	            for (var key in state) {
	                if (data.hasOwnProperty(key)) {
	                    if (i === key) {
	                        state[key] = data[i] == null ? '' : data[i];
	                    }
	                }
	            }
	        }
	    }
	    console.log('IDENTITYINFO_DOWNLOAD - store: ', state);
	}

	// 信息认证初始化
	function INFOAUTH_INIT(state, payload) {

	    for (var i in payload) {
	        if (payload.hasOwnProperty(i)) {
	            for (var key in state) {
	                if (payload.hasOwnProperty(key)) {
	                    if (i === key) {
	                        if (i == 'customerId' || i == 'userId') {
	                            state[key] = String(payload[i]) == null ? '' : String(payload[i]);
	                        } else {
	                            state[key] = payload[i] == null ? '' : payload[i];
	                        }
	                        // Vue.StorageHelper.setItem(key, payload[i])
	                    }
	                }
	            }
	        }
	    }
	    console.log('INFOAUTH_INIT - store: ', state
	    // 测试
	    // Vue.StorageHelper.getAllKeys()
	    );
	}

	// 银行卡认证初始化
	function BANKCARD_INIT(state, payload) {

	    var data = payload.customer;

	    for (var i in data) {
	        if (data.hasOwnProperty(i)) {
	            for (var key in state) {
	                if (data.hasOwnProperty(key)) {
	                    if (i === key) {
	                        state[key] = data[i] == null ? '' : data[i];
	                    }
	                }
	            }
	        }
	    }

	    state.repeatApply = payload.repeatApply == null ? '' : payload.repeatApply;
	    state.bankInfoId = payload.bankInfoId == null ? '' : payload.bankInfoId;
	    state.cardBank = payload.cardBank == null ? '' : payload.cardBank;
	    state.cardNo = payload.cardNo == null ? '' : payload.cardNo;
	    state.cardType = payload.cardType == null ? '' : payload.cardType;

	    console.log('BANKCARD_INIT - store: ', state);
	}

/***/ }),
/* 14 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.HOME_PAGE = HOME_PAGE;
	exports.IDENTITY_INIT = IDENTITY_INIT;
	exports.IDENTITY_SAVE = IDENTITY_SAVE;
	exports.INFOAUTH_INIT = INFOAUTH_INIT;
	exports.INFOAUTH_SAVE = INFOAUTH_SAVE;
	exports.INFOAUTH_ZMAUTH = INFOAUTH_ZMAUTH;
	exports.BANKCARD_INIT = BANKCARD_INIT;
	exports.BANKCARD_SAVE = BANKCARD_SAVE;
	exports.RESULT_LOAD_PRODUCT_LIST = RESULT_LOAD_PRODUCT_LIST;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	var firstStep = function firstStep() {
	    Vue.NaviHelper.render('identity', '身份认证');
	};
	var seconedStep = function seconedStep() {
	    Vue.NaviHelper.render('information', '信息认证');
	};
	var thirdStep = function thirdStep() {
	    Vue.NaviHelper.render('bankCard', '银行卡认证');
	};
	var endStep = function endStep() {
	    Vue.NaviHelper.render('authResultPage', '银行卡认证');
	};
	var mobileAuthStep = function mobileAuthStep() {
	    Vue.NaviHelper.render('mobileAuthPage', '手机认证');
	};
	var linkManStep = function linkManStep() {
	    //联系人
	    Vue.NaviHelper.render('linkMan', '激活额度');
	};

	var withdrawStep = function withdrawStep() {
	    Vue.NaviHelper.render('withdraw', '提现');
	};
	var withdrawStateStep = function withdrawStateStep() {
	    Vue.NaviHelper.render('withdrawState', '提现状态');
	};
	var testStep = function testStep() {
	    Vue.NaviHelper.render('linkMan', '激活额度');
	};

	// 钱包信息
	function HOME_PAGE(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        dispatch = _ref.dispatch,
	        rootState = _ref.rootState;

	    console.log('BASE_URL: ', Vue.UrlMacro.BASE_URL);
	    Vue.TipHelper.showHub(0

	    // 初始化请求
	    );var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.HOME_PAGE, prama, function (res) {
	        if (res.status == 1) {
	            commit('HOME_PAGE', res.data);
	            payload.callback();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg

	            // 展示断网页面
	            );Vue.TipHelper.dismisHub();
	            Vue.NaviHelper.renderNetErr({ repo: Vue.UrlMacro.HOME_PAGE, body: prama, callback: payload.callback });
	        }
	    });
	}

	// 身份认证 - 初始化
	function IDENTITY_INIT(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;

	    Vue.TipHelper.showHub(0

	    // 初始化请求
	    );var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.IDENTITY_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            // 1.保存数据
	            commit('IDENTITY_INIT', res.data

	            // 测试
	            );if (rootState.debug) {
	                // state.page = '301'
	                // state.status = '02'
	                // state.mobileAuthFlag = 'noauth'
	                // state.creditRejRemainDate = 301
	                // state.lockDays = 302
	                // state.blackLock = '1'
	                // state.blackLockDays = 11
	            }

	            console.log('page: ', state.page);
	            console.log('riskLevel: ', state.riskLevel);
	            console.log('status: ', state.status);
	            console.log('mobileAuthStatus: ', state.mobileAuthStatus // 手机实名状态 0:未认证 1:认证中 2:已认证 3:重新认证',
	            );console.log('mobileAuthFlag: ', state.mobileAuthFlag);
	            console.log('lockDays: ', state.lockDays);
	            console.log('creditRejRemainDate: ', state.creditRejRemainDate);

	            console.log('rootState.debug: ', rootState.debug);
	            console.log('state.isRefresh----', state.isRefresh);

	            console.log('blackLock', state.blackLock);
	            console.log('blackLockDays', state.blackLockDays);

	            if (rootState.debug) {
	                testStep();
	                return;
	            }

	            // 2.跳转页面
	            // 根据状态render不同的页面
	            if (state.blackLock == '1' && state.blackLockDays > 0) {
	                // 黑名单检测
	                endStep();
	                return;
	            }
	            if (state.status == '00' || state.status == '-99') {
	                firstStep();
	            } else if (state.status == '01') {
	                if (state.mobileAuthStatus == 0 || state.mobileAuthStatus == null || state.auth.mobileAuthStatus == '') {
	                    seconedStep();
	                } else {
	                    thirdStep();
	                }
	            } else if (state.status == '0001') {//  --------- (废弃)

	            } else if (state.status == '0101') {
	                seconedStep();
	            } else if (state.status == '0102') {
	                thirdStep();
	            } else if (state.status == '02') {
	                // 待激活, 且手机实名认证完成  手机实名认证一共三种状态,未认证,已认证,认证中.
	                if (state.mobileAuthFlag == 'fail') {
	                    // 手机认证页
	                    mobileAuthStep();
	                } else {
	                    // 结果页
	                    endStep();
	                }
	            } else if (state.status == '03') {
	                // 征信审核失败
	                if (state.isRefresh) {
	                    // 授信结果刷新，统一跳转钱包首页（但除开02、03、05、06锁单30天情况外）
	                    if (state.lockDays != '0') {} else {
	                        Vue.NaviHelper.push('root');
	                        return;
	                    }
	                }
	                if (state.page == '3' || state.page == '4' || state.page == '5' || state.page == '6' || state.page == '7' || state.page == '303') {
	                    // 返回钱包首页
	                    Vue.NaviHelper.push('root');
	                } else {
	                    endStep();
	                }
	            } else if (state.status == '04') {// 决策进行中??? 带个刷新按钮 --------- (废弃)
	                // 带刷新按钮的页面
	            } else if (state.status == '05') {
	                // 决策异常
	                // 刷新页面, 去掉刷新按钮
	                endStep();
	            } else if (state.status == '0501') {// 征信姓名和手机实名认证不匹配 --------- (废弃)
	                // 重新申请网络请求'/customer/recredit', 成功后走授信流程, 显示身份认证页面
	            } else if (state.status == '06') {
	                // 授信流程已经走完
	                console.log('111state.isRefresh----' + state.isRefresh);
	                console.log('111state.riskLevel----' + state.riskLevel);

	                if (state.isRefresh) {
	                    // 授信结果刷新，统一跳转钱包首页（但除开02、03、05、06锁单30天情况外）
	                    if (state.page == '301' && state.creditRejRemainDate != '0') {} else if (state.lockDays != '0') {} else {
	                        Vue.NaviHelper.push('root');
	                        return;
	                    }
	                }

	                if (state.riskLevel == '1') {
	                    //高风险用户  auditStatus == -1 0  走添加联系人；刷新时不判断风险用户，直接到钱包首页
	                    linkManStep();
	                } else {
	                    if (state.auditStatus == '2') {
	                        //电审通过
	                        if (state.page == '2') {
	                            // 重走授信流程
	                            firstStep();
	                        } else if (state.page == '4' || state.page == '5' || state.page == '6' || state.page == '7') {
	                            // 显示提现详情页
	                            withdrawStateStep();
	                        } else {
	                            if (state.ageCondition == '0') {
	                                Vue.TipHelper.showHub('2', '对不起，根据您的信用情况，暂时无法为您提供服务');
	                            } else {
	                                // 显示申请提现页面
	                                withdrawStep();
	                            }
	                        }
	                    }
	                }
	            } else {
	                firstStep();
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 身份认证 - 提交
	function IDENTITY_SAVE(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;


	    Vue.TipHelper.showHub(0);

	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        realName: state.realName,
	        identityNo: state.identityNo,
	        creditApplyType: state.creditApplyType // 1 - 信息认证;     0 / null - 其他
	        // educationDegree: state.educationDegree,  // 需求变更, 删除
	        // marryStatus: state.marryStatus,
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.IDENTITY_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();

	        if (res.status == 1) {
	            seconedStep();
	        } else {
	            if (state.blackLock == '1' && state.blackLockDays > 0) {
	                endStep();
	            } else {
	                Vue.TipHelper.showHub('1', res.msg);
	            }
	        }
	    });
	}

	// 信息认证初始化
	function INFOAUTH_INIT(_ref4, payload) {
	    var commit = _ref4.commit,
	        state = _ref4.state,
	        rootState = _ref4.rootState;

	    // Vue.TipHelper.showHub(0)

	    // 初始化请求
	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.INFOAUTH_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            commit('INFOAUTH_INIT', res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 信息认证 - 提交
	function INFOAUTH_SAVE(_ref5, payload) {
	    var commit = _ref5.commit,
	        state = _ref5.state,
	        rootState = _ref5.rootState;


	    Vue.TipHelper.showHub(0);

	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        educationDegree: state.educationDegree,
	        marryStatus: state.marryStatus,
	        profession: state.profession
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.INFOAUTH_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();

	        if (res.status == 1) {
	            if (res.data.isCreditSwich == 0) {
	                thirdStep();
	            } else {
	                Vue.NaviHelper.push('pop');
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 信息认证芝麻认证
	function INFOAUTH_ZMAUTH(_ref6, payload) {
	    var commit = _ref6.commit,
	        state = _ref6.state,
	        rootState = _ref6.rootState;

	    Vue.TipHelper.showHub(0
	    // 初始化请求
	    );var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token,
	        identityNo: state.identityNo
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.INFOAUTH_ZMAUTH, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            // commit('INFOAUTH_INIT', res.data)
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 银行卡认证初始化
	function BANKCARD_INIT(_ref7, payload) {
	    var commit = _ref7.commit,
	        state = _ref7.state,
	        rootState = _ref7.rootState;

	    // Vue.TipHelper.showHub(0)

	    // 初始化请求
	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.BANKCARD_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            commit('BANKCARD_INIT', res.data);
	            payload.callback();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 银行卡认证 - 提交
	function BANKCARD_SAVE(_ref8, payload) {
	    var commit = _ref8.commit,
	        state = _ref8.state,
	        rootState = _ref8.rootState;


	    Vue.TipHelper.showHub(0);

	    var prama = {
	        mobile: state.bankMobile.replace(/\s/g, ""), // 注意, 这里是银行预留电话
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        cardType: state.cardType,
	        cardNo: state.cardNo.replace(/\s/g, ""), // 如果去掉格式化,需要的话!!!
	        cardBank: state.cardBank,
	        bankCode: state.bankCode,
	        bankInfoId: '' // 后台自定义字段, 不需要处理, 必须传''
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.BANKCARD_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub

	        // --------------测试
	        ();if (rootState.debug) {
	            console.log('这里需要看一下回调结果, 进行判断~~~~~~~~~~~~:', res);
	            return;
	        }

	        if (res.status == 1) {

	            if (res.data.isCreditSwich == '0') {
	                endStep();
	            } else {
	                Vue.NaviHelper.push('pop');
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 反导流产品
	function RESULT_LOAD_PRODUCT_LIST(_ref9, payload) {
	    var commit = _ref9.commit,
	        state = _ref9.state,
	        rootState = _ref9.rootState;

	    var prama = {
	        "x-auth-token": rootState.token
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.RESULT_LOAD_PRODUCT_LIST, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            // 存数据
	            payload.callback(res.data
	            // state.productList = res.data.productList
	            // 回调(可选)
	            // payload.callback(res.data.productList)
	            );
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _FaceMutations = __webpack_require__(16);

	var mutations = _interopRequireWildcard(_FaceMutations);

	var _FaceActions = __webpack_require__(17);

	var actions = _interopRequireWildcard(_FaceActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var face = {
	    actions: actions,
	    mutations: mutations,
	    state: {
	        // ----------- 拍照
	        address: '',
	        baiduSimilarity: '',
	        birthday: '',
	        description: '',
	        folk: '',
	        isIdentityExist: '',
	        issueAuthority: '',
	        minshiSimilarity: '',
	        name: '',
	        resultCode: '',
	        sex: '',

	        validPeriod: '',

	        base64Pic: ''
	    }
	};

	exports.default = face;

/***/ }),
/* 16 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.IDENTITY_RECOGNIZE = IDENTITY_RECOGNIZE;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	// 身份证信息
	function IDENTITY_RECOGNIZE(state, payload) {
	    console.log('IDENTITY_RECOGNIZE - payload: ', payload);

	    var data = payload;

	    // 特殊处理, 存主module
	    state.identityNo = data.cardNo == null ? '' : payload.cardNo;
	    state.identityExpires = data.validPeriod == null ? '' : payload.validPeriod;

	    for (var i in data) {
	        if (data.hasOwnProperty(i)) {
	            for (var key in state) {
	                if (data.hasOwnProperty(key)) {
	                    if (i === key) {
	                        state[key] = data[i] == null ? '' : data[i];
	                    }
	                }
	            }
	        }
	    }

	    console.log('IDENTITY_RECOGNIZE - store: ', state);
	}

/***/ }),
/* 17 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.IDENTITY_RECOGNIZE = IDENTITY_RECOGNIZE;
	exports.FACEPAIR_RECOGNICE = FACEPAIR_RECOGNICE;
	exports.IDENTITY_DOWNLOAD = IDENTITY_DOWNLOAD;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	// 身份证拍照 - 提交
	function IDENTITY_RECOGNIZE(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        rootState = _ref.rootState;


	    Vue.TipHelper.showHub(0

	    // @{
	    //     @"mobile": _manager.mobile,
	    //     @"customerId": _manager.customerId,
	    //     @"x-auth-token": _manager.token,
	    //     @"imgFile": base64ImageCode,
	    //     @"type":imageType,   //"front"  正面   "back"    背面   "facepair_0"  手持
	    //     @"platformType" : @"app"
	    // };

	    // 暂存
	    );state.base64Pic = payload.base64Pic;
	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        platformType: "app",
	        type: payload.type == 1 ? 'front' : 'back',
	        imgFile: payload.base64Pic // 需要传进来
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.IDENTITY_RECOGNIZE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            commit('IDENTITY_RECOGNIZE', res.data);
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 人脸识别, 上传照片
	function FACEPAIR_RECOGNICE(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;


	    Vue.TipHelper.showHub(0);

	    var prama = {
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        compareType: 'liveness',
	        originalType: 'front',
	        comparePicture: payload.base64Pic // 需要传进来
	    };

	    Vue.HttpHelper.post(Vue.UrlMacro.FACEPAIR_RECOGNICE, prama, function (res) {
	        Vue.TipHelper.dismisHub();

	        if (res.status == 1) {
	            // 暂存
	            // state.identity.base64Pic = res.data.facepairStr;
	            // commit('FACEPAIR_RECOGNICE', res.data)
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 下载照片
	function IDENTITY_DOWNLOAD(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;


	    // @{
	    //     @"imgType"         : cardType,
	    //     @"customerId"      : _manager.customerId,
	    //     @"x-auth-token"    : _manager.token
	    // }

	    // 暂存
	    // state.identity.base64Pic = payload.base64Pic;
	    var prama = {
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        imgType: payload.imageType
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.IDENTITY_DOWNLOAD, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _LinkManMutations = __webpack_require__(19);

	var mutations = _interopRequireWildcard(_LinkManMutations);

	var _LinkManActions = __webpack_require__(20);

	var actions = _interopRequireWildcard(_LinkManActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	//添加联系人
	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var linkMan = {
	    actions: actions,
	    mutations: mutations,

	    state: {
	        // 添加联系人参数
	        link1Name: "", //联系人姓名
	        link1Phone: "", //联系人电话
	        link1Relation: "", //联系人关系
	        link2Name: "", //联系人姓名
	        link2Phone: "", //联系人电话
	        link2Relation: "", //联系人关系
	        isSuc: false, //

	        all: '' // 全部联系人
	    }
	};

	exports.default = linkMan;

/***/ }),
/* 19 */
/***/ (function(module, exports) {

	/**
	 * Created by x298017064010 on 17/7/26.
	 */
	"use strict";

/***/ }),
/* 20 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.LINKMAN_SAVE = LINKMAN_SAVE;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	// 添加联系人 - 提交
	function LINKMAN_SAVE(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        rootState = _ref.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        customerId: rootState.customerId,
	        "x-auth-token": rootState.token,
	        contactOneName: state.link1Name,
	        contactOneMobile: state.link1Phone.replace(/\s/g, ''),
	        contactOneRela: state.link1Relation,
	        contactTwoName: state.link2Name,
	        contactTwoMobile: state.link2Phone.replace(/\s/g, ''),
	        contactTwoRela: state.link2Relation,
	        activeFlag: payload.activeFlag, //额度激活标记 1:钱包激活 2:佣金分期激活
	        contactListStr: state.all // 上传全部联系人数据
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.LINKMAN_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        console.log(res);
	        if (res.status == 1) {
	            payload.callback(res);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _WithdrawMutations = __webpack_require__(22);

	var mutations = _interopRequireWildcard(_WithdrawMutations);

	var _WithdrawActions = __webpack_require__(23);

	var actions = _interopRequireWildcard(_WithdrawActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var withdraw = {
	    actions: actions,
	    mutations: mutations,
	    state: {
	        // 提现

	        realName: '', //姓名
	        identitycard: '', //身份证号
	        phone: '', //手机号
	        customerId: '', //客户Id
	        cardFlag: '', //绑卡状态 0：未绑卡 1：绑卡

	        withdrawMoney: '', //申请提现金额 默认1000
	        availableAmount: '', //可提现金额
	        paymentType: '', //分几期
	        formalitiesRate: '', //费率
	        loanType: '', //贷款类型（0：首贷；2：再贷；3：提现中但未放款；4：不符合取现条件）
	        agreementId: '', //服务协议id
	        scheduleAmount: '', //分期每期应还金额  分期每期应还金额取第二期
	        loanAmount: '', //放款金额
	        lockDate: '', //锁单天数
	        transStatus: '', //订单状态 判断提现状态  -1:管理平台拒绝根据（creditExpire）false：重新提现；true：重新获取额度。
	        creditExpire: '', //判断额度是否有效

	        cardBank: '', //银行名称
	        bankCode: '', //银行code
	        cardNo: '', //银行卡号

	        bindCardBank: '', //新绑定的银行名称
	        bindCardNo: '', //新绑定的银行卡号
	        bindbankCode: '' //新绑定的银行code
	    }
	};

	exports.default = withdraw;

/***/ }),
/* 22 */
/***/ (function(module, exports) {

	/**
	 * Created by x298017064010 on 17/7/26.
	 */
	"use strict";

/***/ }),
/* 23 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.WITHDRAW_INIT = WITHDRAW_INIT;
	exports.WITHDRAWSTATE_AGREEMENTID_INIT = WITHDRAWSTATE_AGREEMENTID_INIT;
	exports.TRIALREPAYMENTINFO_INIT = TRIALREPAYMENTINFO_INIT;
	exports.BINDBANK_INIT = BINDBANK_INIT;
	exports.BINDBANKLIST_INIT = BINDBANKLIST_INIT;
	exports.BINDBANK_SAVE = BINDBANK_SAVE;
	exports.CHANGEBANK_SAVE = CHANGEBANK_SAVE;
	exports.TRIALREPAY_SHOW = TRIALREPAY_SHOW;
	exports.WITHDRAW_TRANSACTIONPASS_JUDGE = WITHDRAW_TRANSACTIONPASS_JUDGE;
	exports.WITHDRAW_TRANSACTIONPASS_LOCK = WITHDRAW_TRANSACTIONPASS_LOCK;
	exports.WITHDRAW_TRANSACTIONPASS_VALID = WITHDRAW_TRANSACTIONPASS_VALID;
	exports.WITHDRAW_TRANSACTIONPASS_SAVE = WITHDRAW_TRANSACTIONPASS_SAVE;
	exports.WITHDRAW_SAVE = WITHDRAW_SAVE;
	exports.WITHDRAWSTATE_INIT = WITHDRAWSTATE_INIT;
	exports.WITHDRAWSTATEINFO_INIT = WITHDRAWSTATEINFO_INIT;
	// 提现初始化
	function WITHDRAW_INIT(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        rootState = _ref.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAW_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        console.log(res.status
	        // debugger;
	        );if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            payload.fail(res);
	        }
	    });
	}

	// 提现   获取agreementId
	function WITHDRAWSTATE_AGREEMENTID_INIT(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;

	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": state.customerId,
	        "desc": "txht"
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAWSTATE_AGREEMENTID_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 提现初始化
	function TRIALREPAYMENTINFO_INIT(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "loanType": state.loanType,
	        "withdrawMoney": state.withdrawMoney,
	        "paymentType": state.paymentType
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.TRIALREPAYMENTINFO_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 绑卡页面初始化数据
	function BINDBANK_INIT(_ref4, payload) {
	    var commit = _ref4.commit,
	        state = _ref4.state,
	        rootState = _ref4.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": payload.custom,
	        "signatureType": '2'
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.BINDBANK_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 绑卡页面--获取银行列表
	function BINDBANKLIST_INIT(_ref5, payload) {
	    var commit = _ref5.commit,
	        state = _ref5.state,
	        rootState = _ref5.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "customerId": payload.customerId,
	        "dictCode": payload.dictCode
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.BINDBANKLIST_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 绑卡页面--绑卡
	function BINDBANK_SAVE(_ref6, payload) {
	    var commit = _ref6.commit,
	        state = _ref6.state,
	        rootState = _ref6.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "payCardBank": state.bindCardBank,
	        "payCardNo": state.bindCardNo,
	        "payBankCode": state.bindbankCode
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.BINDBANK_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 换卡页面--换卡
	function CHANGEBANK_SAVE(_ref7, payload) {
	    var commit = _ref7.commit,
	        state = _ref7.state,
	        rootState = _ref7.rootState;

	    Vue.TipHelper.showHub(0);
	    console.log(payload);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "customerId": payload.customerId,
	        "newCardNo": state.bindCardNo,
	        "newCardBank": state.bindCardBank,
	        "newBankCode": state.bindbankCode,
	        "cardReseRveMobile": rootState.mobile,
	        "mobile": rootState.mobile,

	        "authAccountName": state.realName,
	        "accreditAgreeId": payload.accreditAgreeId,
	        "accreditAgreeTime": payload.accreditAgreeTime,
	        "sltAccountId": payload.sltAccountId
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.CHANGEBANK_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 提现 点击借款期限，展示分期详情
	function TRIALREPAY_SHOW(_ref8, payload) {
	    var commit = _ref8.commit,
	        state = _ref8.state,
	        rootState = _ref8.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "loanType": "0",
	        //"loanType":state.loanType,
	        "paymentType": state.paymentType,
	        "withdrawMoney": state.withdrawMoney
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.TRIALREPAY_SHOW, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 提现 提交-- 是否有交易密码
	function WITHDRAW_TRANSACTIONPASS_JUDGE(_ref9, payload) {
	    var commit = _ref9.commit,
	        state = _ref9.state,
	        rootState = _ref9.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": state.customerId
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAW_TRANSACTIONPASS_JUDGE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 提现 提交-- 交易密码是否锁定
	function WITHDRAW_TRANSACTIONPASS_LOCK(_ref10, payload) {
	    var commit = _ref10.commit,
	        state = _ref10.state,
	        rootState = _ref10.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAW_TRANSACTIONPASS_LOCK, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else if (res.msg == '交易密码已锁定。') {
	            payload.lock();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 提现 提交-- 交易密码是否正确
	function WITHDRAW_TRANSACTIONPASS_VALID(_ref11, payload) {
	    var commit = _ref11.commit,
	        state = _ref11.state,
	        rootState = _ref11.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": state.customerId,
	        "password": payload.password
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAW_TRANSACTIONPASS_VALID, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        payload.callback(res.data);
	    });
	}
	// 提现 提交-- 无交易密码 保存交易密码
	function WITHDRAW_TRANSACTIONPASS_SAVE(_ref12, payload) {
	    var commit = _ref12.commit,
	        state = _ref12.state,
	        rootState = _ref12.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": state.customerId,
	        "password": payload.password
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAW_TRANSACTIONPASS_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        payload.callback(res.data);
	    });
	}
	// 提现 申请提现的数据保存
	function WITHDRAW_SAVE(_ref13, payload) {
	    var commit = _ref13.commit,
	        state = _ref13.state,
	        rootState = _ref13.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": state.customerId,
	        "withdrawMoney": state.withdrawMoney,
	        "scheduleAmount": state.scheduleAmount,
	        "paymentType": state.paymentType,
	        "agreementId": state.agreementId,
	        "loanType": '0'
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAW_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        console.log('d交易密码已锁定。');
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else if (res.msg == '交易密码已锁定。') {
	            payload.lock();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 提现 状态1   获取customerId
	function WITHDRAWSTATE_INIT(_ref14, payload) {
	    var commit = _ref14.commit,
	        state = _ref14.state,
	        rootState = _ref14.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": state.customerId
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAWSTATE_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 提现 状态2
	function WITHDRAWSTATEINFO_INIT(_ref15, payload) {
	    var commit = _ref15.commit,
	        state = _ref15.state,
	        rootState = _ref15.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "sltAccountId": '' + state.sltAccountId
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.WITHDRAWSTATEINFO_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _CommissionMutations = __webpack_require__(25);

	var mutations = _interopRequireWildcard(_CommissionMutations);

	var _CommissionActions = __webpack_require__(26);

	var actions = _interopRequireWildcard(_CommissionActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	//佣金分期
	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var commission = {
	    actions: actions,
	    mutations: mutations,

	    state: {
	        realName: '', // 客户姓名
	        identitycard: '', // 身份证号码
	        location: '', //请求的地区列表json
	        cityName: '上海', //选择的城市
	        cityCode: 'sh_shanghai', //选择的城市
	        username: '', //用户名
	        password: '', //密码
	        vercode: '', //验证码
	        token: '' //本次会话的令牌
	    }
	};

	exports.default = commission;

/***/ }),
/* 25 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.COMMIS_CITY_INIT = COMMIS_CITY_INIT;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	//公积金 社保 省市信息
	function COMMIS_CITY_INIT(state, payload) {
	  state.location = payload;
	}

/***/ }),
/* 26 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.COMMISSION_INIT = COMMISSION_INIT;
	exports.PROMOTE_INIT = PROMOTE_INIT;
	exports.LINKMAN_CREDIT_AUTH = LINKMAN_CREDIT_AUTH;
	exports.COMMISSION_JUDGE = COMMISSION_JUDGE;
	exports.PROVID_CITY_INIT = PROVID_CITY_INIT;
	exports.PROVID_INFO_INIT = PROVID_INFO_INIT;
	exports.SECURITY_INFO_INIT = SECURITY_INFO_INIT;
	exports.SECURITY_CITY_INIT = SECURITY_CITY_INIT;
	exports.PROVID_VERIFICODE = PROVID_VERIFICODE;
	exports.SECURITY_VERIFICODE = SECURITY_VERIFICODE;
	exports.PROVID_SUBMIT = PROVID_SUBMIT;
	exports.SECURITY_SUBMIT = SECURITY_SUBMIT;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	// 佣金分期初始化
	function COMMISSION_INIT(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        rootState = _ref.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg
	            // 返回钱包首页 //
	            // if(res.status == '0' && res.msg.indexOf('佣金信息初始化失败') >= 0) {
	            //     Vue.NaviHelper.push('root', {}, '', function (r) {})
	            // }
	            );
	        }
	    });
	}

	// 提额初始化
	function PROMOTE_INIT(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "customerid": rootState.customerId
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.PROMOTE_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.code == '0000') {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', '已有提额记录'
	            // 回首页
	            );Vue.NaviHelper.render('comStage', '佣金分期');
	        }
	    });
	}

	function LINKMAN_CREDIT_AUTH(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "customerId": rootState.customerId
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.LINKMAN_CREDIT_AUTH, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	// 佣金分期 交单规则
	function COMMISSION_JUDGE(_ref4, payload) {
	    var commit = _ref4.commit,
	        state = _ref4.state,
	        rootState = _ref4.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_JUDGE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	//社保查询 公积金查询，是2个人写些的接口，返回的状态 是code 或者 status。乱
	// 公积金查询 获取城市信息
	function PROVID_CITY_INIT(_ref5, payload) {
	    var commit = _ref5.commit,
	        state = _ref5.state,
	        rootState = _ref5.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.PROVID_CITY_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == '0000') {
	            commit('COMMIS_CITY_INIT', res.data);
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 2	公积金表单设置查询 获取城市提交选项信息
	function PROVID_INFO_INIT(_ref6, payload) {
	    var commit = _ref6.commit,
	        state = _ref6.state,
	        rootState = _ref6.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "city": state.cityCode
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.PROVID_INFO_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == '0000') {
	            payload.callback(JSON.parse(res.data).FormSettings[0].FormParams);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 2	社保查询表单设置查询 获取城市提交选项信息
	function SECURITY_INFO_INIT(_ref7, payload) {
	    var commit = _ref7.commit,
	        state = _ref7.state,
	        rootState = _ref7.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "city": state.cityCode
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.SECURITY_INFO_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == '0000') {
	            payload.callback(JSON.parse(res.data).FormSettings[0].FormParams);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 社保查询 获取城市信息
	function SECURITY_CITY_INIT(_ref8, payload) {
	    var commit = _ref8.commit,
	        state = _ref8.state,
	        rootState = _ref8.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.SECURITY_CITY_INIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == '0000') {
	            commit('COMMIS_CITY_INIT', res.data);
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 公积金查询 获取验证码
	function PROVID_VERIFICODE(_ref9, payload) {
	    var commit = _ref9.commit,
	        state = _ref9.state,
	        rootState = _ref9.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "city": state.cityCode
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.PROVID_VERIFICODE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.code == '0000') {
	            payload.callback(res);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 社保查询 获取验证码
	function SECURITY_VERIFICODE(_ref10, payload) {
	    var commit = _ref10.commit,
	        state = _ref10.state,
	        rootState = _ref10.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "city": state.cityCode
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.SECURITY_VERIFICODE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.code == '0000') {
	            payload.callback(res);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	// 公积金查询 提交
	function PROVID_SUBMIT(_ref11, payload) {
	    var commit = _ref11.commit,
	        state = _ref11.state,
	        rootState = _ref11.rootState;

	    var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	    eventModule.showPromotionQuota(0, "", function () {}); //打开load
	    var prama = {
	        "x-auth-token": rootState.token,
	        "token": state.token, //本次会话的令牌
	        "customerid": rootState.customerId, //客户号
	        "city": state.cityCode, //城市编码
	        "name": state.realName, //姓名
	        "bustype": "AJQH", //业务类型
	        "identitycard": state.identitycard, //身份证号码
	        "username": state.username, //公积金账号
	        "password": state.password, //密码
	        "vercode": state.vercode //验证码
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.PROVID_SUBMIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();

	        setTimeout(function () {
	            if (res.code == '0000') {
	                payload.succ(res);
	            } else if (res.code == '2001' || res.code == '2009') {
	                Vue.TipHelper.showHub('1', res.msg);
	                payload.fail(res);
	            } else {
	                eventModule.closePromotionQuota(); //关闭load
	                if (res.code == '2005' || res.code == '2006' || res.code == '2007' || res.code == '2008') {
	                    payload.replay(res);
	                    setTimeout(function () {
	                        Vue.TipHelper.showHub('1', res.msg);
	                    }, 100);
	                }
	            }
	        }, 1000);
	    });
	}

	// 社保查询 提交
	function SECURITY_SUBMIT(_ref12, payload) {
	    var commit = _ref12.commit,
	        state = _ref12.state,
	        rootState = _ref12.rootState;

	    var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	    eventModule.showPromotionQuota(0, "", function () {}); //打开load
	    var prama = {
	        "x-auth-token": rootState.token,
	        "token": state.token, //本次会话的令牌
	        "customerid": rootState.customerId, //客户号
	        "city": state.cityCode, //城市编码
	        "name": state.realName, //姓名
	        "bustype": "AJQH", //业务类型
	        "identitycard": state.identitycard, //身份证号码
	        "username": state.username, //公积金账号
	        "password": state.password, //密码
	        "vercode": state.vercode //验证码
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.SECURITY_SUBMIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();

	        console.log('111111111111111', res);
	        setTimeout(function () {
	            if (res.code == '0000') {
	                payload.succ(res);
	            } else if (res.code == '2001' || res.code == '2009') {
	                Vue.TipHelper.showHub('1', res.msg);
	                payload.fail(res);
	            } else {
	                eventModule.closePromotionQuota(); //关闭load
	                if (res.code == '2005' || res.code == '2006' || res.code == '2007' || res.code == '2008') {
	                    payload.replay(res);
	                    setTimeout(function () {
	                        Vue.TipHelper.showHub('1', res.msg);
	                    }, 100);
	                }
	            }
	        }, 1000);
	    });
	}

/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _commissionApplyMutations = __webpack_require__(28);

	var mutations = _interopRequireWildcard(_commissionApplyMutations);

	var _commissionApplyActions = __webpack_require__(29);

	var actions = _interopRequireWildcard(_commissionApplyActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var commApply = {
	    mutations: mutations,
	    actions: actions,

	    state: {
	        isUpload: false, //是否上传服务协议
	        isUploadconfirm: false, //是否上传佣金确认书
	        houseHoldArea: '', //
	        area: [], //佣金分期地址
	        shanghaiArea: '', //上海具体区
	        isHedging: true, //是否拒绝29天，重新修改资料申请
	        isRefreshBtn: false, //是否显示刷新按钮
	        isPhoneReview: true, //电审是否通过
	        hedgingdays: '', //
	        comsAmt: '', //佣金金额
	        houseHoldProvince: '', //房屋物业省
	        houseHoldCity: '', //房屋物业市
	        houseHoldAddress: '', //房屋物业详细地址
	        mailAddress: '', //通讯地址
	        workAddress: '', //工作单位地址
	        workPhone: '', //单位电话
	        workName: '', //单位名称
	        amountAll: '', //分期总计金额
	        deductMoneyDate: '', //分期每月具体还款日期
	        eachPeriodTotalAmount: '', //分期每月预计还款金额
	        platformRecommendedFee: '', //平台借款手续费
	        isbankbing: '', //是否绑卡
	        totalAmount: '', //可分期佣金总金额
	        cardNoStr: '', //银行卡卡号后四位数
	        bankCode: '', //银行英文简称
	        cardBank: '', //银行名称
	        parampressImg: '', //银行水印图标
	        bankiconImg: '', //银行图标
	        message: '', //选择佣金分期的金额
	        cardNo: '', //银行卡卡号
	        stages: '', //分期的期数
	        auditStatus: '1', //佣金分期审核刷新，锁单的状态
	        lockDays: '', //锁单天数
	        riskLevel: '', //额度激活风险等级 0-低风险 1-高风险

	        availableAmount: '', // 额度
	        bindCardSource: '', // 是否可绑卡/换卡 (1: 不可, 2: 可以)
	        cardFlag: '', // 卡是否有效(0: 未绑卡, 3: 已失效, 其他: 正常)
	        formalitiesRate: '' // 手续费费率(0.02-0.05)
	    }

	};

	exports.default = commApply;

/***/ }),
/* 28 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.COMMISSION_UPLOAD_PROTOCL = COMMISSION_UPLOAD_PROTOCL;
	exports.COMMISSION_INIT_COMMISSIONINFO = COMMISSION_INIT_COMMISSIONINFO;
	exports.COMMISSION_SELECT_ADDRESS = COMMISSION_SELECT_ADDRESS;
	exports.COMMISSION_REFRESH_COMSSION = COMMISSION_REFRESH_COMSSION;
	exports.COMMISSION_COMMCASH_INITDATA = COMMISSION_COMMCASH_INITDATA;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	// 身份认证初始化
	function COMMISSION_UPLOAD_PROTOCL(state, payload) {

	    if (payload == 'YJPROTOCOL') {
	        state.isUpload = true;
	    } else if (payload == 'YJCONFIRM') {
	        state.isUploadconfirm = true;
	    }
	}

	// 佣金分期地址选择
	function COMMISSION_INIT_COMMISSIONINFO(state, payload) {

	    if (payload.protocol == '0') {
	        state.isUpload = false;
	    } else {
	        state.isUpload = true;
	    }
	    if (payload.comsConfirm == '0') {
	        state.isUploadconfirm = false;
	    } else {
	        state.isUploadconfirm = true;
	    }

	    console.log('重新带入数据', payload);

	    state.comsAmt = payload.comsAmt == null ? '' : payload.comsAmt;
	    // state.houseHoldProvince = res.data.houseHoldProvince== null ? '' : res.data.houseHoldProvince;
	    state.shanghaiArea = payload.houseHoldCity == null ? '' : payload.houseHoldCity;
	    state.houseHoldAddress = payload.houseHoldAddress == null ? '' : payload.houseHoldAddress;
	    state.mailAddress = payload.mailAddress == null ? '' : payload.mailAddress;
	    state.workAddress = payload.workAddress == null ? '' : payload.workAddress;
	    state.workPhone = payload.workPhone == null ? '' : payload.workPhone;
	    state.workName = payload.workName == null ? '' : payload.workName;
	}

	// 佣金分期地址选择
	function COMMISSION_SELECT_ADDRESS(state, payload) {

	    state.area = payload;
	}
	// 佣金分期审核刷新(锁单天数)
	function COMMISSION_REFRESH_COMSSION(state, payload) {
	    // payload.auditStatus = '2'
	    state.auditStatus = String(payload.auditStatus);
	    state.lockDays = payload.lockDays == null ? '' : payload.lockDays;
	}

	function COMMISSION_COMMCASH_INITDATA(state, payload) {
	    state.availableAmount = payload.availableAmount;
	    state.cardNoStr = payload.cardNoStr;
	    state.bankCode = payload.bankCode == null ? '' : payload.bankCode.toLowerCase();
	    state.cardBank = payload.cardBank;
	    state.parampressImg = 'WXLocal/ic_bank_dark_' + state.bankCode;
	    state.bankiconImg = 'WXLocal/ic_bank_' + state.bankCode;
	    state.isbankbing = payload.cardFlag;
	    state.cardFlag = payload.cardFlag;
	    state.message = state.availableAmount;
	    state.platformRecommendedFee = state.message * 0.02;
	    state.cardNo = payload.cardNo;
	    state.formalitiesRate = payload.formalitiesRate;
	    state.bindCardSource = payload.bindCardSource;
	    if (payload.cardFlag == '1') {
	        state.isbankbing = true;
	    } else {
	        state.isbankbing = false;
	        state.parampressImg = '';
	        state.bankiconImg = 'WXLocal/wx_no_tied_card';
	    }
	}
	//支付平台推荐费插入流水

/***/ }),
/* 29 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.COMMISSION_UPLOAD_PROTOCL = COMMISSION_UPLOAD_PROTOCL;
	exports.COMMISSION_SELECT_ADDRESS = COMMISSION_SELECT_ADDRESS;
	exports.COMMISSION_SAVE_COMMISSIONINFO = COMMISSION_SAVE_COMMISSIONINFO;
	exports.COMMISSION_REFRESH_COMSSION = COMMISSION_REFRESH_COMSSION;
	exports.COMMISSION_COMMCASH_INITDATA = COMMISSION_COMMCASH_INITDATA;
	exports.COMMISSION_COMMCASH_TRIALREPAYMENTINFO = COMMISSION_COMMCASH_TRIALREPAYMENTINFO;
	exports.COMMISSION_COMMCASH_SAVESTREAM = COMMISSION_COMMCASH_SAVESTREAM;
	exports.COMMISSION_INIT_COMMISSIONINFO = COMMISSION_INIT_COMMISSIONINFO;
	exports.COMMISSION_COFIRM_MODIFY_COMSINFO = COMMISSION_COFIRM_MODIFY_COMSINFO;
	/**
	 * Created by x298017064010 on 17/7/26.
	 */

	//佣金分期上传居间协议
	function COMMISSION_UPLOAD_PROTOCL(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        rootState = _ref.rootState;

	    Vue.TipHelper.showHub(0

	    // // 初始化请求
	    );var prama = {
	        mobile: rootState.mobile,
	        imgFile: payload.imgFile,
	        "x-auth-token": rootState.token,
	        imageType: payload.imageType
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_UPLOAD_PROTOCL, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            // 1.保存数据
	            commit('COMMISSION_UPLOAD_PROTOCL', payload.imageType

	            // payload.callback({resulet:res.status})


	            );
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	//佣金分期地址选择
	function COMMISSION_SELECT_ADDRESS(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;

	    Vue.TipHelper.showHub(0

	    // // 初始化请求
	    );var prama = {
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_SELECT_ADDRESS, prama, function (res) {
	        console.log(res);
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            commit('COMMISSION_SELECT_ADDRESS', res.data.houseHoldArea);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	//佣金分期审核数据保存
	function COMMISSION_SAVE_COMMISSIONINFO(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;

	    Vue.TipHelper.showHub(0);

	    var prama = {
	        mobile: rootState.mobile,
	        comsAmt: state.comsAmt,
	        houseHoldProvince: '上海市',
	        houseHoldCity: state.shanghaiArea,
	        houseHoldAddress: state.houseHoldAddress,
	        mailAddress: state.mailAddress,
	        workAddress: state.workAddress,
	        workPhone: state.workPhone,
	        workName: state.workName,
	        "x-auth-token": rootState.token
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_SAVE_COMMISSIONINFO, prama, function (res) {
	        console.log(res);
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback();
	        } else {
	            if (res.status == '0' && res.msg.indexOf('保存失败') >= 0) {
	                Vue.TipHelper.showHub('1', '佣金信息填写有误');
	                return;
	            }
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	//佣金分期审核刷新
	function COMMISSION_REFRESH_COMSSION(_ref4, payload) {
	    var commit = _ref4.commit,
	        state = _ref4.state,
	        rootState = _ref4.rootState;

	    Vue.TipHelper.showHub(0

	    // console.log(rootState);
	    // console.log(state);
	    // // 初始化请求
	    // state.hedgingdays = '96';
	    );var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	        // commit('COMMISSION_REFRESH_COMSSION',99);
	    };Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_REFRESH_COMSSION, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {

	            // res.data.lockDays=8;


	            commit('COMMISSION_REFRESH_COMSSION', res.data);

	            payload.callback();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	//提现页面初始化数据
	function COMMISSION_COMMCASH_INITDATA(_ref5, payload) {
	    var commit = _ref5.commit,
	        state = _ref5.state,
	        rootState = _ref5.rootState;

	    Vue.TipHelper.showHub(0

	    // console.log(rootState);
	    // console.log(state);
	    // // 初始化请求
	    // state.hedgingdays = '96';
	    );var prama = {
	        // mobile:'18818210524',
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token

	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_COMMCASH_INITDATA, prama, function (res) {
	        console.log(res);
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {

	            commit('COMMISSION_COMMCASH_INITDATA', res.data);

	            payload.callback();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	//点击借款期限，展示分期详情
	function COMMISSION_COMMCASH_TRIALREPAYMENTINFO(_ref6, payload) {
	    var commit = _ref6.commit,
	        state = _ref6.state,
	        rootState = _ref6.rootState;

	    Vue.TipHelper.showHub(0

	    // console.log(rootState);
	    // console.log(state);
	    // // 初始化请求
	    // state.hedgingdays = '96';
	    );var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token,
	        withdrawMoney: state.message,
	        paymentType: payload.stages
	        // commit('COMMISSION_REFRESH_COMSSION',99);
	    };Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_COMMCASH_TRIALREPAYMENTINFO, prama, function (res) {
	        console.log(res);
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            state.amountAll = res.data.amountAll;
	            state.deductMoneyDate = res.data.deductMoneyDate;
	            state.eachPeriodTotalAmount = res.data.eachPeriodTotalAmount;
	            state.platformRecommendedFee = res.data.platformRecommendedFee;

	            // 1.保存数据
	            // commit('COMMISSION_SELECT_ADDRESS', true)

	            // payload.callback({resulet:res.status})
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	//支付平台推荐费插入流水
	function COMMISSION_COMMCASH_SAVESTREAM(_ref7, payload) {
	    var commit = _ref7.commit,
	        state = _ref7.state,
	        rootState = _ref7.rootState;

	    Vue.TipHelper.showHub(0);

	    var prama = {
	        "x-auth-token": rootState.token,
	        customerId: rootState.customerId,
	        availableCommissions: state.availableAmount.toString(),
	        withdrawMoney: state.message.toString(),
	        paymentType: state.stages,
	        feeAmt: state.platformRecommendedFee.toString()
	        // commit('COMMISSION_REFRESH_COMSSION',99);
	    };Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_COMMCASH_SAVESTREAM, prama, function (res) {
	        console.log(res);
	        Vue.TipHelper.dismisHub();
	        // 特殊处理, 防抖
	        rootState.isDidClick = false;

	        if (res.status == 1) {
	            payload.callback();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}
	//佣金信息初始化
	function COMMISSION_INIT_COMMISSIONINFO(_ref8, payload) {
	    var commit = _ref8.commit,
	        state = _ref8.state,
	        rootState = _ref8.rootState;

	    Vue.TipHelper.showHub(0
	    // console.log(rootState);
	    // console.log(state);
	    // // 初始化请求
	    // state.hedgingdays = '96';
	    );var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	        // commit('COMMISSION_REFRESH_COMSSION',99);
	    };Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_INIT_COMMISSIONINFO, prama, function (res) {
	        console.log(res);
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            // 没有初始数据
	            if (res.data == null) {
	                return;
	            } else {
	                commit('COMMISSION_INIT_COMMISSIONINFO', res.data);
	                payload.callback(res.data);
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

	//修改资料重新申请
	function COMMISSION_COFIRM_MODIFY_COMSINFO(_ref9, payload) {
	    var commit = _ref9.commit,
	        state = _ref9.state,
	        rootState = _ref9.rootState;

	    Vue.TipHelper.showHub(0);

	    var prama = {
	        mobile: rootState.mobile,
	        "x-auth-token": rootState.token
	        // commit('COMMISSION_REFRESH_COMSSION',99);
	    };Vue.HttpHelper.post(Vue.UrlMacro.COMMISSION_COFIRM_MODIFY_COMSINFO, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            // 修改资料重新申请成功
	            payload.callback();
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    });
	}

/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _UrlMacro = __webpack_require__(31);

	var _UrlMacro2 = _interopRequireDefault(_UrlMacro);

	var _HttpHelper = __webpack_require__(32);

	var _HttpHelper2 = _interopRequireDefault(_HttpHelper);

	var _NaviHelper = __webpack_require__(33);

	var _NaviHelper2 = _interopRequireDefault(_NaviHelper);

	var _LogHelper = __webpack_require__(34);

	var _LogHelper2 = _interopRequireDefault(_LogHelper);

	var _StorageHelper = __webpack_require__(35);

	var _StorageHelper2 = _interopRequireDefault(_StorageHelper);

	var _TipHelper = __webpack_require__(36);

	var _TipHelper2 = _interopRequireDefault(_TipHelper);

	var _CheckHelper = __webpack_require__(37);

	var _CheckHelper2 = _interopRequireDefault(_CheckHelper);

	var _AssetHelper = __webpack_require__(38);

	var _AssetHelper2 = _interopRequireDefault(_AssetHelper);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	/**
	 * Created by x298017064010 on 17/6/21.
	 */
	var Plugin = {
	    install: function install(Vue) {

	        // 1. 添加全局方法或属性(方法大写, 属性用下划线连接)
	        Vue.UrlMacro = _UrlMacro2.default;

	        Vue.HttpHelper = _HttpHelper2.default;

	        Vue.LogHelper = _LogHelper2.default;

	        Vue.NaviHelper = _NaviHelper2.default;

	        Vue.StorageHelper = _StorageHelper2.default;

	        Vue.TipHelper = _TipHelper2.default;

	        Vue.CheckHelper = _CheckHelper2.default;

	        Vue.AssetHelper = _AssetHelper2.default; // 改为vuex存储

	        // 2. 添加全局资源
	        Vue.directive('my-directive', {
	            // bind (el, binding, vnode, oldVnode) {
	            //     // 逻辑...
	            // }
	        }

	        // 2. 自定义过滤器
	        );Vue.filter('thousandSeparator', function (num) {
	            // 千分位处理
	            num = String(Number(num).toFixed(2));
	            var re = /(-?\d+)(\d{3})/;
	            while (re.test(num)) {
	                num = num.replace(re, "$1,$2");
	            }
	            return num;
	        });

	        Vue.filter('emojiFomat', function (str) {// 表情符号处理
	            // return str.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	        });

	        Vue.filter('realNameFormat', function (e) {
	            // 姓名格式化
	            if (e.length == 2) {
	                return '*' + e.substr(1, 2);
	            } else if (e.length >= 3) {
	                var stars = '';
	                for (var i = 1; i <= e.length - 2; i++) {
	                    stars = stars + '*';
	                }
	                return e.substr(0, 1) + stars + e.substr(e.length - 1, e.length);
	            } else {
	                return e;
	            }
	        });

	        Vue.filter('identityFormat', function (e) {
	            // 身份证格式化
	            if (e == '') {
	                return e;
	            }
	            return e.substr(0, 6) + '********' + e.substr(14, 4);
	        });

	        Vue.filter('cardNoFormat', function (e) {
	            // 银行卡号格式化
	            if (e == '') {
	                return e;
	            }
	            return e.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
	        });

	        Vue.filter('mobileFormat', function (e) {
	            // 手机号格式化 123 2345 6789
	            if (e == '') {
	                return e;
	            }
	            var value = e.replace(/\D/g, '').substring(0, 11);
	            var valueLen = value.length;
	            if (valueLen > 3 && valueLen < 8) {
	                value = value.substr(0, 3) + ' ' + value.substr(3);
	            } else if (valueLen >= 8) {
	                value = value.substr(0, 3) + ' ' + value.substr(3, 4) + ' ' + value.substr(7);
	            }
	            return value;
	        });

	        Vue.filter('mobileFormatText', function (e) {
	            // 手机号格式化: 123****6789
	            if (e == '') {
	                return e;
	            }
	            return e.substr(0, 3) + '****' + e.substr(7, 11);
	        }

	        // 3. 注入组件
	        );Vue.mixin({
	            // created: function () {
	            //     // 逻辑...
	            // }
	        }

	        // 4. 添加实例方法
	        );Vue.prototype.$myMethod = function (options) {
	            // 逻辑...
	            console.log('myMethod');
	        };
	    }
	};

	exports.default = Plugin;

/***/ }),
/* 31 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var PRD = _index2.default.state.prd; /**
	                                      * Created by x298017064010 on 17/6/22.
	                                      */


	var BASE_URL = '';
	var WEB_URL = '';
	if (PRD) {
	  BASE_URL = 'https://ajp-app-prod.apass.cn/appweb/data/ws/rest'; // PRD
	  WEB_URL = 'https://ajp-app-prod.apass.cn/';
	} else {
	  BASE_URL = 'https://ajp-app-sit.apass.cn/appweb/data/ws/rest'; // SIT & UAT - 安家派
	  WEB_URL = 'https://ajp-app-sit.apass.cn/';
	}

	var HOME_CHECKVERSION = "/checkVersion/app";
	var HOME_PAGE = "/home/index";

	/**
	 *  信贷
	 */
	var IDENTITY_INIT = "/customer/initIdentityInfo";
	var IDENTITY_SAVE = "/customer/saveBasicInfo";

	var INFOAUTH_INIT = "/customer/initInformationAuth";
	var INFOAUTH_SAVE = "/customer/saveInformationAuth";
	var INFOAUTH_ZMAUTH = "/zm/auth/query";

	var BANKCARD_INIT = "/customer/initInformationBank";
	var BANKCARD_SAVE = "/customer/saveInformationBank";

	/**
	 *  人脸识别
	 */
	var IDENTITY_RECOGNIZE = "/identity/recognize";
	var FACEPAIR_RECOGNICE = "/identity/facepairRecognice";
	var IDENTITY_DOWNLOAD = "/customer/download";

	/**
	 *  反导流
	 */
	var RESULT_LOAD_PRODUCT_LIST = '/reverse/loadProductList';

	/**
	 *  手机认证
	 */
	var PHONE_INITIALIZE = "/juxinli/mobile/init"; // 初始化数据
	var PHONE_SUBMITINFORMATION = "/juxinli/mobile/req/collect"; // 提交信息
	var PHONE_COMPARINGINFORMATION = "/juxinli/mobile/resp/collect"; // 比对信息

	/**
	 *  激活联系人
	 */
	var LINKMAN_SAVE = "/customer/saveContactInfo";
	var LINKMAN_CREDIT_AUTH = "/customer/creditAuthActiveApply";

	/**
	 *  佣金分期
	 */
	var COMMISSION_INIT = "/commission/initData";
	var COMMISSION_JUDGE = "/commission/judgeSubmit";
	var PROMOTE_INIT = "/sccFundSys/resultPage";
	var PROVID_CITY_INIT = "/accfund/fetchCitiesList";
	var PROVID_INFO_INIT = "/accfund/formSetting";
	var PROVID_VERIFICODE = "/accfund/loginInit";
	var SECURITY_VERIFICODE = "/sccFundSys/advanceToken";
	var PROVID_SUBMIT = "/accfund/loginAuth";
	var SECURITY_INFO_INIT = "/sccFundSys/advanceFormSettingByCity";
	var SECURITY_SUBMIT = "/sccFundSys/login";
	var SECURITY_CITY_INIT = "/sccFundSys/advanceFormSettingCites";

	var COMMISSION_UPLOAD_PROTOCL = "/commission/uploadProtocl";
	var COMMISSION_SELECT_ADDRESS = "/commission/selectAddress";
	var COMMISSION_SAVE_COMMISSIONINFO = "/commission/saveCommissionInfo";
	var COMMISSION_REFRESH_COMSSION = "/commission/refreshComssion";
	var COMMISSION_COMMCASH_INITDATA = "/commcash/initData";
	var COMMISSION_COMMCASH_TRIALREPAYMENTINFO = "/commcash/newTrialRepaymentInfo";
	var COMMISSION_COMMCASH_SAVESTREAM = "/commcash/saveStream";
	var COMMISSION_INIT_COMMISSIONINFO = "/commission/initCommissionInfo";
	var COMMISSION_COFIRM_MODIFY_COMSINFO = "/commission/cofirmModifyComsInfo";

	/**
	 *  提现
	 */
	var WITHDRAW_INIT = "/cash/initData";
	var WITHDRAWSTATE_AGREEMENTID_INIT = "/agreement/saveContract";
	var TRIALREPAYMENTINFO_INIT = "/cash/trialRepaymentInfo";
	var TRIALREPAY_SHOW = "/cash/newTrialRepaymentInfo";

	var WITHDRAW_TRANSACTIONPASS_JUDGE = "/transactionGesture/ifTranPassword";
	var WITHDRAW_TRANSACTIONPASS_LOCK = "/transactionGesture/ifTranPasswordLock";
	var WITHDRAW_TRANSACTIONPASS_VALID = "/transactionGesture/ifWithdrawTranPasswordNew";
	var WITHDRAW_TRANSACTIONPASS_SAVE = "/transactionGesture/savetransaction";
	var WITHDRAW_SAVE = "/cash/buildLoan";

	var WITHDRAWSTATE_INIT = "/customer/validate/creditAuth";
	var WITHDRAWSTATEINFO_INIT = "/myCenter/search/singleWithDrawInfo";

	/**
	 *  绑卡
	 */
	var BINDBANK_INIT = "/card/initData";
	var BINDBANKLIST_INIT = "/card/bankList";
	var BINDBANK_SAVE = "/card/bindCard";
	var CHANGEBANK_SAVE = "/card/changeCard";

	exports.default = {
	  BASE_URL: BASE_URL, // 服务器ip
	  WEB_URL: WEB_URL, // 合同ip

	  HOME_CHECKVERSION: HOME_CHECKVERSION, // 强制升级check (不需要使用)
	  HOME_PAGE: HOME_PAGE, // 首页数据

	  IDENTITY_INIT: IDENTITY_INIT, // 初始化身份认证基本数据
	  IDENTITY_SAVE: IDENTITY_SAVE, // 身份认证 - 信息提交(七个信息)

	  INFOAUTH_INIT: INFOAUTH_INIT, // 信息认证初始化
	  INFOAUTH_SAVE: INFOAUTH_SAVE, // 信息认证完成提交
	  INFOAUTH_ZMAUTH: INFOAUTH_ZMAUTH, // 芝麻认证

	  BANKCARD_INIT: BANKCARD_INIT, // 银行卡认证初始化
	  BANKCARD_SAVE: BANKCARD_SAVE, // 银行卡认证完成提交

	  RESULT_LOAD_PRODUCT_LIST: RESULT_LOAD_PRODUCT_LIST, // 反导流

	  LINKMAN_CREDIT_AUTH: LINKMAN_CREDIT_AUTH, // 低风险用户激活授权
	  LINKMAN_SAVE: LINKMAN_SAVE, // 添加联系人保存

	  PROMOTE_INIT: PROMOTE_INIT, // 提额方式选择页面初始化
	  PROVID_CITY_INIT: PROVID_CITY_INIT, // 公积金查询 城市初始化
	  PROVID_INFO_INIT: PROVID_INFO_INIT, // 公积金查询 获取城市提交选项信息
	  PROVID_VERIFICODE: PROVID_VERIFICODE, // 公积金查询 验证码初始化
	  PROVID_SUBMIT: PROVID_SUBMIT, // 公积金查询 提交
	  SECURITY_CITY_INIT: SECURITY_CITY_INIT, // 社保查询 城市初始化
	  SECURITY_INFO_INIT: SECURITY_INFO_INIT, // 社保查询 获取城市提交选项信息
	  SECURITY_VERIFICODE: SECURITY_VERIFICODE, // 社保查询 提交
	  SECURITY_SUBMIT: SECURITY_SUBMIT, // 社保查询 验证码初始化
	  COMMISSION_JUDGE: COMMISSION_JUDGE, //佣金分期交单规则接口调用
	  COMMISSION_INIT: COMMISSION_INIT, // 佣金分期初始化


	  IDENTITY_RECOGNIZE: IDENTITY_RECOGNIZE, // 上传身份证正反面
	  FACEPAIR_RECOGNICE: FACEPAIR_RECOGNICE, // 上传人脸识别照片
	  IDENTITY_DOWNLOAD: IDENTITY_DOWNLOAD, // 下载之前上传过的照片

	  COMMISSION_UPLOAD_PROTOCL: COMMISSION_UPLOAD_PROTOCL, //佣金分期上传居间协议
	  COMMISSION_SELECT_ADDRESS: COMMISSION_SELECT_ADDRESS, //佣金分期地址选择
	  COMMISSION_SAVE_COMMISSIONINFO: COMMISSION_SAVE_COMMISSIONINFO, //佣金分期审核数据保存
	  COMMISSION_REFRESH_COMSSION: COMMISSION_REFRESH_COMSSION, //佣金分期审核刷新
	  COMMISSION_COMMCASH_INITDATA: COMMISSION_COMMCASH_INITDATA, //提现页面初始化数据
	  COMMISSION_COMMCASH_TRIALREPAYMENTINFO: COMMISSION_COMMCASH_TRIALREPAYMENTINFO, //点击借款期限，展示分期详情
	  COMMISSION_COMMCASH_SAVESTREAM: COMMISSION_COMMCASH_SAVESTREAM, //支付平台推荐费插入流水
	  COMMISSION_INIT_COMMISSIONINFO: COMMISSION_INIT_COMMISSIONINFO, //佣金信息初始化
	  COMMISSION_COFIRM_MODIFY_COMSINFO: COMMISSION_COFIRM_MODIFY_COMSINFO, // 用户点击重新修改资料

	  WITHDRAW_INIT: WITHDRAW_INIT, //提现初始化
	  WITHDRAWSTATE_AGREEMENTID_INIT: WITHDRAWSTATE_AGREEMENTID_INIT, //提现初始化--获取agreementId
	  TRIALREPAYMENTINFO_INIT: TRIALREPAYMENTINFO_INIT, //点击借款期限，展示分期详情
	  TRIALREPAY_SHOW: TRIALREPAY_SHOW, // 点击借款期限，展示分期详情
	  WITHDRAW_TRANSACTIONPASS_JUDGE: WITHDRAW_TRANSACTIONPASS_JUDGE, // 提现 提交-- 是否有交易密码
	  WITHDRAW_TRANSACTIONPASS_LOCK: WITHDRAW_TRANSACTIONPASS_LOCK, // 提现 提交-- 交易密码是否锁定
	  WITHDRAW_TRANSACTIONPASS_VALID: WITHDRAW_TRANSACTIONPASS_VALID, // 提现 提交-- 交易密码是否正确
	  WITHDRAW_TRANSACTIONPASS_SAVE: WITHDRAW_TRANSACTIONPASS_SAVE, //  提现 提交-- 无交易密码 保存交易密码
	  WITHDRAW_SAVE: WITHDRAW_SAVE, // 申请提现的数据保存
	  WITHDRAWSTATE_INIT: WITHDRAWSTATE_INIT, // 提现 状态1
	  WITHDRAWSTATEINFO_INIT: WITHDRAWSTATEINFO_INIT, // 提现 状态2

	  BINDBANK_INIT: BINDBANK_INIT, // 绑卡页面初始化数据
	  BINDBANKLIST_INIT: BINDBANKLIST_INIT, // 绑卡页面绑卡列表
	  BINDBANK_SAVE: BINDBANK_SAVE, // 绑卡页面--绑卡
	  CHANGEBANK_SAVE: CHANGEBANK_SAVE, // 换卡页面--绑卡

	  PHONE_INITIALIZE: PHONE_INITIALIZE,
	  PHONE_SUBMITINFORMATION: PHONE_SUBMITINFORMATION,
	  PHONE_COMPARINGINFORMATION: PHONE_COMPARINGINFORMATION
	};

/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; /**
	                                                                                                                                                                                                                                                                               * Created by x298017064010 on 17/6/21.
	                                                                                                                                                                                                                                                                               */


	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var stream = weex.requireModule('stream');
	var eventModule = weex.requireModule('event');

	var httpHelper = {
	    // GET 请求不支持 body 方式传递参数，请使用 url 传参。
	    get: function get(repo, callback) {

	        return stream.fetch({
	            method: 'GET',
	            type: 'json',
	            timeout: 50000,
	            url: Vue.UrlMacro.BASE_WEEX + repo
	        }, function (res) {

	            console.log('~~~~~~~~~~~ get: ' + repo + ' ~~~~~~~~~~', '\n出参: ', res);
	            console.log('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~');

	            callback(res);
	        }, function (progress) {});
	    },

	    // body 参数仅支持 string 类型的参数，请勿直接传递 JSON
	    post: function post(repo, body, callback, isReload) {

	        var jsonStr = ''; // 加密前
	        var dataStr = ''; // 加密后
	        if ((typeof body === 'undefined' ? 'undefined' : _typeof(body)) !== String) {
	            jsonStr = JSON.stringify(body);
	        } else {
	            jsonStr = body;
	        }
	        eventModule.wxEncryptJsonStr(jsonStr, function (r) {
	            dataStr = r.result;

	            return stream.fetch({
	                method: 'POST',
	                type: 'json',
	                timeout: 50000,
	                headers: { 'Content-Type': 'application/json' },
	                // , 'Charset':'UTF-8', 'Accept-Encoding':'gzip,deflate', 'Accept-Language':'zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3'
	                url: Vue.UrlMacro.BASE_URL + repo,
	                body: { data: dataStr }
	            }, function (res) {
	                console.log('~~~~~~~~~~~ post: ' + repo + ' ~~~~~~~~~~', '\n入参: ', body, '\n出参: ', res);
	                console.log('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~');

	                if (res.status === 200 && res.ok === true) {

	                    if (isReload) {
	                        // 取消断网页面
	                        Vue.NaviHelper.backNetErr();
	                    } else {
	                        if (res.data.status == '-1' && res.data.msg.indexOf('请重新登录') >= 0) {
	                            // 弹出登录页
	                            Vue.NaviHelper.push('native/main/login', {}, '', function (r) {});
	                            if (_index2.default.state.os == 'iOS') {
	                                Vue.NaviHelper.push('root', {}, '', function (r) {});
	                            }
	                            return;
	                        }
	                        callback(res.data);
	                    }
	                } else {
	                    // 请求失败
	                    Vue.TipHelper.dismisHub
	                    // 展示断网页面
	                    ();Vue.NaviHelper.renderNetErr({ repo: repo, body: body, callback: callback });
	                }
	            }, function (progress) {});
	        });
	    }
	};

	exports.default = httpHelper;

/***/ }),
/* 33 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var eventModule = weex.requireModule('event'); /**
	                                                * Created by x298017064010 on 17/6/21.
	                                                */

	var navigator = weex.requireModule('navigator');
	var globalEvent = weex.requireModule('globalEvent');

	var naviHelper = {

	    // channel: 标记用于回调的路径
	    push: function push(url, object, channel, callback) {

	        globalEvent.addEventListener(channel, function (r) {
	            globalEvent.removeEventListener(channel
	            // if (typeof r === String) {
	            //     r = JSON.parse(r);
	            // }
	            );callback(r);
	        });
	        eventModule.openURL({
	            url: url,
	            data: object,
	            animated: 'true'
	        });

	        // 目前不可用
	        // 定义每一个子页面的回调channel
	        // const bankListChannel = new BroadcastChannel(channel);
	        // bankListChannel.onmessage = function (event) {
	        //     // bankListChannel.onmessage = null;
	        //     console.log('111111111111111')
	        //
	        //     // 取值
	        //     callback(event)
	        //     Vue.NaviHelper.pop()
	        // }

	        // var bankListChannel = new BroadcastChannel('bankList');
	        // bankListChannel.postMessage(v);
	    },

	    pop: function pop(callback) {
	        navigator.push({
	            url: 'pop',
	            animated: 'true'
	        }, callback);
	    },

	    popRoot: function popRoot(callback) {
	        navigator.push({
	            url: 'root',
	            animated: 'true'
	        }, callback);
	    },

	    render: function render(url, title, isShowHelp) {
	        console.log('render ' + url + ' success');
	        _index2.default.state.router.push(url);
	        eventModule.wxSetNavTitle(title, isShowHelp == null ? true : isShowHelp);
	    },

	    back: function back(title) {
	        _index2.default.state.router.go(-1);
	        eventModule.wxSetNavTitle(title, true);
	    },

	    renderNetErr: function renderNetErr(param) {
	        _index2.default.state.errNetParam = param;
	        _index2.default.state.router.push('errorNetPage');
	    },

	    backNetErr: function backNetErr() {
	        _index2.default.state.router.go(-1);
	    }
	};

	exports.default = naviHelper;

/***/ }),
/* 34 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	exports.default = LogHelper;
	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	function LogHelper(msg) {
	    console.log('To Native: ', msg);
	    eventModule.wxNativeLog(msg, function (e) {});
	}

/***/ }),
/* 35 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	/**
	 * Created by x298017064010 on 17/6/22.
	 */
	var storage = weex.requireModule('storage');

	var storageHelper = {

	    setItem: function setItem(key, val) {
	        storage.setItem(key, val);
	    },

	    getItem: function getItem(key, callback) {
	        storage.getItem(key, function (e) {
	            callback(e.data);
	        });
	    },

	    getAllKeys: function getAllKeys() {
	        storage.getAllKeys(function (e) {
	            console.log('~~~~~~~~~~~~~~~ Storage: ~~~~~~~~~~~~~~~~~~~~');
	            console.log(e.data);
	        });
	    }
	};

	exports.default = storageHelper;

/***/ }),
/* 36 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	var modal = weex.requireModule('modal');

	var TipHelper = {

	    showHub: function showHub(type, msg) {
	        // type(-1: 网络失败, 0: 加载中, 1: 文本提示,2:文本提示，不随页面关闭消失)
	        eventModule.wxShowLoadingView(type, msg);
	    },

	    dismisHub: function dismisHub() {
	        eventModule.wxDismissLoadingView();
	    },

	    showAlert: function showAlert(title, msg, cancel, ok, callback, cancelOnTouch) {
	        eventModule.wxShowAlertView(title, msg, cancel, ok, callback, cancelOnTouch);
	    }
	};

	exports.default = TipHelper;

/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var checkHelper = {
	    // 按键防抖(废弃)
	    checkDidClick: function checkDidClick() {
	        if (_index2.default.state.isDidClick) {
	            //已经按下
	            return true;
	        } else {
	            //第一次按下
	            _index2.default.state.isDidClick = true;
	            return false;
	        }
	    },
	    // 校验姓名(仅汉字和字母)
	    checkRealName: function checkRealName(realName) {
	        var isRealNamePass = true;
	        if (!/^[\u4E00-\u9FA5]{2,4}$/.test(realName)) {
	            isRealNamePass = false;
	        }
	        return isRealNamePass;
	    },
	    // 校验电话号
	    checkMobileNo: function checkMobileNo(mobileNo) {
	        var isBankMobilePass = true;
	        if (isNaN(mobileNo) || mobileNo.substr(0, 1) != '1' || mobileNo.length != 11) {
	            isBankMobilePass = false;
	        }
	        return isBankMobilePass;
	    },
	    // 校验银行卡号
	    checkCardNo: function checkCardNo(cardNo) {
	        var isCardNoPass = true;
	        if (isNaN(cardNo)) {
	            isCardNoPass = false;
	        }
	        return isCardNoPass;
	    },

	    // 校验手机服务密码
	    checkMobileAuthPassword: function checkMobileAuthPassword(password) {
	        var isPasswordPass = true;
	        if (isNaN(password)) {
	            isPasswordPass = false;
	        }
	        return isPasswordPass;
	    }
	}; /**
	    * Created by x298017064010 on 17/9/5.
	    */
	exports.default = checkHelper;

/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	// 注册图片
	var wx_eye_close = 'WXBase64/' + __webpack_require__(39).split(',').pop(); /**
	                                                                                                       * Created by x298017064010 on 17/9/19.
	                                                                                                       */

	var wx_wallet_invalid = 'WXBase64/' + __webpack_require__(40).split(',').pop();
	var wx_linkMan_suc = 'WXBase64/' + __webpack_require__(41).split(',').pop();
	var wx_add = 'WXBase64/' + __webpack_require__(42).split(',').pop();

	_index2.default.state.icons = {
	    wx_eye_close: wx_eye_close,
	    wx_wallet_invalid: wx_wallet_invalid,
	    wx_linkMan_suc: wx_linkMan_suc,
	    wx_add: wx_add
	};

/***/ }),
/* 39 */
/***/ (function(module, exports) {

	module.exports = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAYAAACM/rhtAAAAAXNSR0IArs4c6QAAAyhJREFUWAntlU1IVFEUx2eewzAWyERJH4iRBX4QUVGbsEFTsFloSDXRokVUtJ8IYZh0ZowhqFW0KJQWLsKNSoGLkBoMWhlGH4S1SWtRaDPhFMXYfPQ7Mm94jvOmJC2Je+Fw7zvnf8753/+77z6LRQ2lgFJAKaAUUAr8/wpkMhnrat2lNRAI2CH4AnvvdDpbvV7v939BFh5V6XS6V9O0XtZ3dA5aV1fXDx40rGl2drafoE0P/q2ZnmUIdJd+jdheY1/NarVmsFacUUBt7OIeCWuMoJVc00ve4BC2Ex6vSktLg8Z+opwF0ATSHmb5GXND8mE4HF4vsZUc9JW31Qe5Q8wfIOju6Oj4Yuy54OMIhUJ7ksnkCAAh99put7f4/f4pY8JyrUU5hOinXjvE4iUlJa7Ozs5n+fXnFdSdAJ6i5AES3uGrnpubGwsGg+16fLlm3k45qt2nnpCLQa65EDnpt4CgONhVC8mVLD9i5alUapDd9mFOif/poE59IpEYp0cDteIQPAK5MbO6C14xyW0kyoFFSO0ESRtYX8Xko4niu8Z8A9xXs4JmfjnTEAtT6xwY6fsW2wbB51gjNWOFcnMEAewjeVTIQOQSz5clgXkHvh6sQZ4p9gm7BWZAjoT4io3u7u5a3sJ53sxZcGuxBLlXmG9SM4LVUO8J1kSveH6tHEHuwxHAzQBvc+7O5APxNdEkAKZej4GdxCL4RY1JzlIMMrLBjTzvwu9irs7i5Tobxi5A5I34mLeAecRyO/aYHgeJZySmjxxBgm6a7ycQJjGpA/JnYi5wPfgrmH95X9JQFB8Cf53clwXqVWZJVjgcjs0+n2/GiMkRNDqLrbNX0TiYKZvNdpxraTeKbYWs2Dr833iOsT6FiZq1EJsoVpN4GfFNzPPKGrFL/q2x26NSAFWGs19fwS+QZvKHOA3Ug4Ukx2yAlbO36PwJftE1Y1ZE90PwmKwhOKD7TGa5hC2QPGkS/y33kgiy0yqqVsu5qqurGy3WgfgD4jMQrMnmFYObxpZEkEMcpVKEpgGPx5MyrUogG7/IZgZ5nC6GVTGlgFJAKaAUUAooBVavAj8Blhtu9xyMieEAAAAASUVORK5CYII="

/***/ }),
/* 40 */
/***/ (function(module, exports) {

	module.exports = "data:image/png;base64,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"

/***/ }),
/* 41 */
/***/ (function(module, exports) {

	module.exports = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJYAAACWCAYAAAA8AXHiAAAAAXNSR0IArs4c6QAAIbRJREFUeAHtXQt0VNW53vvMTEhIAkhByUwID4XYUq0Ve3uFPiCigpCEWr1X9IJ2rfpoqy1eb1ttadXWRx+rF71atXbVW+FafLRoEhCoCGp99CHWpYvWgCKEmYkCamBCQjIzZ9/vP8k57HPmdZLM40xyzlrJ2Xuf/fj3/3+z979f/+ZsBD8vV1eXHWRsBhOiFmyoZYJPg3uM4KKSMY4/Vgl/JeP97j5eRRAWQVgEXvyJCBdwc36EcfEuwlrhbp3I2O45wWB3X5KR95+PlCq3+KfXCN5TB1B8WmXsVCZYLSpfI4CgXPAAmQrk3YbcWxXG3gLY/s7FqG314T1tuSjPaXnmhKlOqOTGqVMnxXvj81UWrwN25kPMJzuBLgDsHWBuu8I82zwlnu2L9+59zxF0ZZmIYQWsjYHAGTGVLYfgFqK1+ESWeZWT7CCAfwD4m70KW7s4FHo9J4UUINOiB1bT5Ml+Fo9fJhhfjlbptAHyMI4WZC+6RequWoXCdgGQh7yqAp0pHuGKElG83ogvGo1UjRtHOhVr7+iojPp8lWosVilUFTqYpzKmqJVg5ASuspnoZmvR/UFfE1MR3UNpbD+cv8mZWMs8nkca9+8P207nwIhFCawW/+zRqvL+hRAswMQWgK/ARfqHc94DpecVzsVzQihvMJ/SOqOy8u1ZO3f2pk85uK87Z80q2R2JnMKiai3n6ulC8Hmcs7OFEKNs5KgCnFsB7LWKetL6+vCOLhtpHBWlqID19CmnjIke7f4Gurrr0bJg4JX6QcViaI1eFZxtQ7Ox7WOcv1zoURqNQj8QYk6csTouGA0kzkI9vKlroY0sMHDlq33lZb+84O23j6SL66RvRQGsrYHAx46qbCVAch2EMTYVA/vBtBm/9jWsomJzY2ur1n2lil/o8Kba2krW2bkQre4K1Iv0wtQg47wDYLy3XGF3LQiFPig07ZnKdzSwNk+ZUtUTjd0Apl8DppenqcwOxpU1JaNL1+FXjV948T1ojSf2dh1bxoS6AtTPTlUDCOwoWuIHRvm8v1i4b197qniFDncksMDkUdHu7u/gl/w96CSlSZnE+WEour8SXu/DS9vaMLIaPs9TNTWf4LHY5RiQXJ2qhYbOeAwt8x2+srKf4cfU47TaOw5YG6omnx/n6j0A1IxkzALBBznjd3nLy+4tJp0jWV0yhZFOGTvafa1gYmUqnRIA260w5dr68P4/Zsovn98dA6ynA4HqqMpWg4kXJWMAGBiGjvFzzic9WIyjpGR1shtGo2Ah3rsKOua38YPzJ0uHH9vvfQq7/oJQKJjse77DCg4scfHFnpaXXrpeFexmVL7CygAQ+D5X+M3esrLfOrHJt9KbSz+pCLHu7iuEKm5FC3ZSkrI6Fc5urZ87dzV/4gkMPgv3FBRY2uRmTP0dWqkvJmFBHPM+948uLV117p49h5N8H7FBz0yfPrbr2LHbMC/3NTAhYRIWrdfzzKtcWshJ1oIBS9OlWHxtMt0B3d5fFOb9en1432sjFj02Kt7in3KmymL3oXv8rDU6BHvQwzzLl7Tv32L9lg9/3oFFXV/Tyy//iKviJoDKXD7nHyqC3wRF9NcAFz67TyYOAFS8xT/5SpWLOzGCHC/HB3OxyMDvbJwz54f57hrNgpWpyoG7pbo6EFfVdZhG+HxC9pxtKRk9enmxzkMl1CfPAX3zYF1rwdvzE4rm7E8eRVlWHwyGEr7lKCBvwNpQVTM7xmJPox4nWuoSh07wg4Zw8CduK2XhzAC91Ho1+6tvhM76YyS16l4HvMx7wZL2th0DzHZQ0fMCrKf8/gX4JT0JCk2jPgAphH1Jl9SH214cFPVuoqQcaPHXfA770B4F0AKWCJ1QPr60NBzeagnPujfjroChltji9/87VKmNyMcMKsY3Kaz8DBdUQ+VwYnriKfEWPcEmy1fIgG/sk4nlS5a9OW2x0Cxfpwr1LtBsBrDC72gMBle5XV+WpWnJjrrGpurq25gqvmf5pCpcWQn14x5LeNa8OQNWc1XgNpWJ78uUojDBufKtXFZILs9193GAfuBCqHdbR+EK47c3tIdW5YJPOQFWMlBhRb4Xi8aXN4bDj+aiIm6e6TnQ5PdfgkXthzEBUSLHzBW4sg6s/u7vf2Ti4e7k3HthY7jtGUu4680jB5r8NecKEVuPIk36LrrFb2a7F8kqsEgpxJrfOrnJxbLMIezhXoTlhVfzyEO3qBQcwDLaWTgjsAnLQROkKKqHs0vrw+HHpLAhObMGLG1KgUZ/5qa2k3s9811QDUlGWU9M4BKx+HZkfLzlgqoCFXhxtqYizKO1QVaBJj+1eSoZVKRTUffntlSD5GrukpFMSDak9xqlkOww16jJ0ggcvGPILZa2TBNXabHYmFFHptAT2aWuoj54weQjJSn0kBR2l5jWbA94PMqZQ13+GVKLRQvK2tqfBCpiCE0puKDKBzSGVgbJiGRlyeVEkinJ1hI+IO+QEp95+PDtQPxlphJp8jMcutMU5nocy4F1kSN/vWTsGOoG5Y0BU3YFg75HI5FnB0v4oLvC/v1Um+RmlJYQMGxd7M6oD1YchUnXv3i9EYvXi3QKSJ3Bfq5Fg93PNaiukHZ+xvs26RnA1BaUefkKF1S6aIrnTTJT+mRnbKuhBoNkrO3yHURVBgwsre+NxWmuSj6JHO/bpbDr0CBocJM4gAP14V2HSIYgxdgrr8mYZD0IfWvAOtbsjo4bUOCVMi/QBa5qaA/+Tg5z3cXHgXWRw23LKsdEQfk5EvVTdu/fH1kXibwshWV0Gl1ZxpiIQEe0elXxTziliTW2pTEUWuR2gXY46Pw42o6IQGATlHl5J2pnicI/PpCjZQPqCuncnxlU/EPaTuyCyvmAsUshyZJkijmjD6U0Ff2yl4LSO20Di0aB1sOksL15o7tHPT2Di/EryZQOtci0k+wJA3JYOretrlCzpdDV/SaaSePYO5D9l4ZQ8Gy3tUrH3uL9pk1BBKpfwds4WgZZ7/aNLjvNzsFhWy1Wb1fXd2VQgV0YBXq/7oKqeIGTiXKSLcmYZK3HJQxoxlr0gDTvjC1WvymhPcjUsPqCrTD3YjngujT5up+GCQewnggDLexavToA3DGYUJqeyYRSxhaL7FOZQAVbCnTsXS/IfQ9vDpCs0fq8r9eSsKDZLNMDUrzTAoss6WF/1TVyWjLQ4dpSkDkyvN0ka5K5qZbAhIYNU6DZkxZYmnlGyZIemsEwWX0xZ+H6hjsHSOYke72emCAvJ2zo/mTvlMAio1+wx2T0rZSY7FPZGREkK8gNK14OkMxJ9nINCBuEETlMdqcEFlmSQzc4To+MfvYgGT3T/e57ZHGAZE8YMGoNbGgYMQLMjqTA0izIwTyhHBXrgXeNNEt6cv1HuptkTxiQ+YBJ05WEFTlMdycFlmacX969AEOyZPNTT+S+ncuBgcyOD7QWGgaABT0d7X4grOh++Z0UWNqND1Issk6MfrZojNdLpI8oJ8503hpj8c1NgQCt6Wb9IQwQFuSMrVjRvyUAS9vY1XeNiB6Hkclrw+M6HMmBJn/1d2An44dEHGyUrmyqCtyPOSeoRdl9ErAArCTbDJgALLrwCKTI4TuGmx317LK68Lk95a/+Jmwz/FSmBPrPNU2B6ofELbfIspSjDMrdjwXZxpbSjxlTfgmFardoyVFw44Psdd3O4kBzVfVVvM+iTyJhQlzR/OCvH9k+b17qq1QSU2UOsWAiATPIwQQsuu8PUwyn6TmjHY3RNSK63307iwNPBQIrBFMfgBKdsstDd3jJkV27HqfbyLJFPWGCsGHkB8xo2DECLMDqu0RS+sr5Zihsx+cupE+us7Ac0IynqeKhdKDSKcQi8pd2d3R8Q/cP9a1hAtiQ87Fix9RiQe1bKEfG78DtBk0McYan2T+5EcZX/g/U2DqzgPmn9WNnzMiukbUEbJixYwCL7lAG+o3rbrWmDlezOYOVLhU6B2Chb6HK1MchK1t6E9b4Ngb8ky6Z/9xzx7suPbOhvIENuTsk7BCG9CwNYNHF3HogvWF6729Ov+9PpnckuJsDgTqYfXzSYtEnZdUBqq3Y8fnls3bsoJM3WX0IG4QROVMZQwaw+m57l6Jxtl3yuc4CcwBG0+ZifqpZ3huXjiS0Ji8obFIj9KHcXTlnwYiMIQNYOBhRJxOKznub7HfdheNA8+TJn2Ei9jS6m3I7VKCl+jMbU7kk12u7iRjhRq+nAavFP70GE2rTdaJBWE+Fz/eS7nffheMADeNFXN0CUKXcoiJTh23jr40p8S3KhxpDGCGsGOULcTJhifwasATvMbVWGJ6+Mn/v3mNGAtdREA7QTasxIZ5B93eCLQI4f7Oc8/Mguw5b8YcYiTBCWJGz0bGkAUsV4kz5Iw5oPCf7XXf+OQCDdjN4NPYsBCfbCk1JCFqO1rKy0gX5vog8AStCfJqI1ICFd61MMa6MekP2u+78cqB5ypRpcVVsQ/dnDN/TUsD5O8yj1J3/zjsH0sbLwUcrVlTGTqVi+oAlzMBiPqU1BzS4WdrgANnHEL3RZzGlUG0jOk7CszbfqJJzYFfU2JNuJ13W4lix0o8l5eXq6jIMTTWFq7+w+IzKyrezVrCbkW0O0ARjVDBqqabZSYTuL+zzeOoWv/vuPjvxcxGnHyvGoVbCEmFKwULgDFQE/v6H872zdu48bk1XD3ffOeVAi3/mhFhPL3Sq42YMMhR4QGG+cy7Yv/+dDPFy+lnDCjCjF0JYIkwpaHJN+hXM17jdoM6lPL031NScEGdHn4FQjCW1tEXDEozPoyyoD+99K228fH20YgaYIh3LBCwEuMDKl0BQDh2hikfjW/ADP8NWsXT+QHjOWxwMvmkrfh4iJcFMrYLzYtNNZSsusEz8yKFny+mnl0e7ujCjLj5js5hOXE0Cg7P5uSXVJk0M1kN2meIKPk3B9gvzjC7nh0yRXE9OOLB96tTSY4c+aME81Vw7BWD016V4lCX1oZBpQtJO2lzHwSACatXxB3riWNwqJiqPB0HzUnlE9rvu7HOAdnMe6el9EgKYbyf3vmUT79KGYPB5O/HzHScBM8AUukduAhaQ5QIrh5Kh/edvf/QR7acyb6pMVSbnUbRWFzn6Sr4EzHAClmSoFh6F885UdXTDh8YBMmt9ZNfuR9D9NdrMKa4ofFlDKLTBZvyCREuCmQqabjC1WIrX67ZYORAPHcNqfvHlh9D9/ZvN7HGHoLIC3d8fbMYvWLQEzABTXqwJVAJcBlG+aNQFlsGN7DgAJo7dn3SaZoWdHDFbTbenXVkfLg7b+YSZHrliwBR1haYWq2rcOEcAK+tn4eSK59mNI+9347drunQhHQnQqa6FKc6H0sVx0rckmNGA5SQaNVroDsTDrbvewBGnescRN0CCYMPzp2h/rrObDNbzbmgIh++zG9+p8ajFMrVQ7R0dphYs34Q3TZt2kqqKZ1Hux+OM/wGHMi/KNw3ZKo+MdKCl+o7t/BS+Crd8/Lft+A6JmAQzEVLeTcCK+nwFAxbZtRTHerdCJ+lbZhLCh1Mpj+LI0384hIe2yYA9hRt1Ix12EuHs321LQ6Hb7cR1WpwEzABTmCA1T4iqsVhBgIWZ6HFHBXsGeusnLYzzYM/3wy3+6q9awh3rbaqq/hZMvtxpl0BMgP6isT30A7vxnRYvATPAVEJXCHtHeQdWU21t5ZHe6Ga0VNq21iSMw9KT+iBM9ZhsoiaJV/AgjP6uhj0Fk+W7tERxfh9upP2vtHEc/jEJZghY5q4Q92rmFViaWcpIZCNAZVytkYyPGKpzmOq5p9nvd6wQMPq7HGf/7k9Gf7Iw3Mf8UGMo6PgfSzLaTWEJmEFXiPOEJh0rpuS3xSop66jADovxJkLTeLBo/nMI0HHdBhnpAKh+Qz+ANOQbnzCl8EjDVV+9Et3g8UlE42txOayYIUyRjmUyAQmuTMhntegAgMIr5oHBf7dbLgT4I4DrDrvxcx2vxT95KQBv20gHsPeHhrlzL+e33IKzB8X/JGAGmKLdDe/KVeMqmyn78+Gma2NxyLIODP+r3fIArpuaqwIFH5o3V1cvijP1MTQ7do10bKj2T1rGn3gibreuTo+XgBlgipR3045R/IRMO0rzVSk6ZMnHVCxAF/Gi3TJVJq5v8gfuoyUTu2myGa8lEDgHAF+PKZsSO/mibs/ASMdFuTDSYaf8XMVJgplW6gpNwIKGUBBgUaXpWHjphAkLQRNNkNp6AKqvtQSqf5NtW5uZCm/x13wOE7lNKL80U1z6jnmq5ycqSm6NdNghJBdxrJgBpjgd1TkYVzGFZCid8RnjTxhdyJM6tLvySE90PbbsLrLLB7QG68bMnLki63agkhCAJad/UePqVvDM3gia81fGnjDuvPk7d3Ymya6og2jT4u4PP+pCJTQjcOg6xESPUq7MCQa7waA2qXae3ZHIKZI/706yCXDK+HFLAfWn7BaOpZNlR1p3P/bq7Nk+u2kGEw9rf5/GKWUy0mEPVIztKC8dtWg4gor4148Vw7IgYYkwRToWtVXm7jCqFqw71OjBP2oxx86ceTG6xcf0sExvtHAXhtrfe5JavExxB/O9uaZmFtL9ETrVOFvpYaSj1KOcN6yv4bNipR9LfcCyKPCcq6fbYlyOI1G31jh3zmWYinjYblHQeRZjFr8l1R0vdvOxxoM5oZliQEY62FtkpGNhMPihNa/h5LdiBYDSzjpqwMLW0tfkymKQNU/2F9JNw3Jcav4VNKu/sksHwLVAFe2baKnIbpp08chIB46+P4tm/qR08YxvmpEOzzmFMNJh0JAnRwJW+ucjNWBxMWqbTAcU4bNz1Z3I5dh10+z00vbQNdirdLfdNADBF1ik84+0uG03TbJ4W/z+yWpvdBu6P1tGOpDHPg8bVVcwIx3JKpGjMM3uB7AiZ69jSQNWfXhPG3SZd/QI+MWP6oxGbZ1309Pk4429SisVzn5ityzU41+P9PY+m+ma2VT50UXrx5g29TE1VRw5HD+AUInXc47GT/nDMHV/IMQcwopRPWBIr7sGrL4PYrsRAQ5MC9fJfqe4sbvyJrSot9ilB6PFMztV9hxtILSbhuLh6PvEYzAnBMbNsJMOw2xcyC0KbqTDDq3ZipOIkeMYMoClMI+pO8R22vnZIiDb+WA/+K3YGfBd+/lij1dP7/O05dlOms3V1eOjXd3YG8Y+bic+WvsPvB7lXNBlHl3bSly8kbB8bmp8ZAwZwPKUeEwtFvaofCZbym8uWNcYDv5M4co30VJAncr8oOWpVePihY3Tpk1JF5uMdPSQMVkhPpUunvGN8w4P8zrKSIdBWw4dGjaEOEsuQsaQAazFe/e+ByH9Q48IaXlZZ6e907p6ojy/G8LBe3DE9moUi+WqzA9Zho719Lzw9OTJJyeLTUY6eru6N6HuJoYli0th4Bf2HbFF9eF9plF1qvjDKhzY0DDSXynCDmFIr6MBrL4A88U7aAtW6BGd+m5sD/4ax7e/AvrQ5Wd+oHPVROPqCy3+qafKsWmE033wgw0Y/c2Rw1O5oed1Ma9nCU4p/zlVnGEdbsUG55vk+pqA5VXYWvkjmLyQlFhTmAM9OISwBkfRL8WvJmaHPHRz/rjofR6HNLSJYNRx1AFVfRK96jw76TH668GvtRFTCi/YiT/c4miYADbkenm5th/NCDIBa3Eo9DoU0Tf1r9TU9XYdW6b7nfxGy/E4556LQL9dM5cnYsvL9o0nVX8WivrjaJ3Pt1U/GOlAH/jlpeHwVlvxh2EkwoTcDRJmNOxIdTUBi8JxmbSl1VId3x3q9WkI72/CJGojWhR7lx8IMT7qEX9CC9ag55HuTS0i8r8E82kb08Ub9t+EGRMJmAEDEoDFPJ5HEC4rw7M3TJlib9jtAI42BoOb8eu4ACA4aoscOrto71HRIq5A/uvtRR+esei2DNRstlQ7tR8zUlASYGlLEZyZmvl4NHqFKZXDPWhRtjPuPR/gMu3nHyzZyIeMdHwVLeK6weYxXNLxWOxyU12AlWTLV4ktFlJxRTF1h+Dq1TS/Y8rQ4R4YKnuJez3Y6sw/GjKpCv8GdKr/HXI+RZ4BYYCwIFfDihX9W1JgKepJ6/ErPW5XEjYlY0e7i+78W8P+/X/DaKUOUwOH9AoP+K3w/0QLeP+A0w3DBBoGgAW9aoQRworul99JgUX33GGPtuk0LyYXV2Z7j5NMSK7cNFoRXu8XwQRj8s5uWTgQ8H1MZay2G384x9MOFgMD5jry1anuREwKLErsLS+7F8PIDj0jDC8nCvHeVbq/mN5L29r+4WFiCWi2Nc9FdaPDDw3tIcecXSw0v0n2hAGDDmDDV172S8NvcaQEFq58PYJFxnvl+Dhk9W2aTJTDisW9pL19xyifdz7U8GhmmvlfYaRjXuZ4IyMGyZxkL9eWsEEYkcNkd0pgUaRyhd2FLsQYttOMday7+wo5g2JyL2pre9HHsWSTZhIVyv4b2FT42WKqV65pjXZ3f4Vkr5dDmCBs6P5k77TA0i5V5PwBOSFmq299Zvp0Q4GTvxWDe3F7+6s+hZ8F5iRMokLJfwvboM8ohnrki0ZN1qq4xVQeMJHpws20wKLM0H38Qp7JRj97UtexY7eZCioyD91DE4+WfgqaFJ2H0x4YstgDewqfRF1RRffROUCyJpnrfsICYUL3p3pnBNbCffvaocmalFjsEPhai3/KmakyLYbwCw/t2TVKxOk4Vyf+gg2BSacOJ3sK2ZAByZhkbcoLWNAwYQpM9GQEFiXxlZX9DEjdLSX3qCxWMJsJEh1Dci5677298RLfzBM9yky+Y4cNpX5IxRVVYuhUsPURuw9Ee3TCCQOEBd2f7g1Vw96zoWry+TEW3yzHVrDJrqE9+KAc5rqHBweaq6qvUplqOnLnZZ6FS9r3b7FTQ9vAosyaqgJPYKL0IiNjXMhYMrrsVAw7j8/SGx9dR7FyYMvJJ5/Y3X3sn9iPN16vA+b1fo8pmIt1f6a3ra5Qz8SnsOvhJp2k70HBvV1da6nZ1IPcd3FzgGTZ3d29RgYVatTZL3vblRsQsC4IhYLY432rKXdskIM98xtNYa6naDmgydKy6ZFkTrIfSKUGBCzKuH7u3NW03CEXgu7xx2QvSg5z3cXHAZIhyVKmnGRNMpfD7LgH1YU1TZ7sZ7H46/LaEUYMIYWVn0FmH+0U7MZxFgda/DMnqOzo6+gKAzplAMdBHBg5I9l+Kz1OqveAWyzKiAryMM9yFGxMJhJBqji6xtW3UrHaueEks37ZyaASJOPBgIpqOihgUUJt2Knwn5Bbf9CMLsLJl6KeldfrMpLeJDOSnVxnofA77U4tyOl096CBRRk0zJnzA8zK/0nPTHur4ntQAK8zhbkex3JAkxVkZiIQMm2cM+eHprABegalY8llkD2EeFx9DWEn6uFaF8nZpbBl8Kge5r6dxwGYvbwEyszvoM/IODjg8Shn1geDoaFQPKQWiwomArzMewGcxvwWEYq90Q83+WvOHQpxbtrccYBkQzKygKqTZDlUUBHVQwYWZbKkvW0HMP8l0z4n2D4XIrYeI8izKI77OIcDJBOSDSZBj9unpz1qkKEmyyyQmhVgER10Mhjbf+lwq3wmsYLF45tccGVBUlnKQpMFZILsKqQsVZJdNk93Zw1YRGR9OPwYTAuZNtxj28UEEYtvd7tFSYwFcmrdH2RBMpFJIJmR7OSwobqzCiwihkwL4XTL7RbCKgSLb9CURcsH15sfDhDvSQYoTW6poAvx2/vMQWWXjqwDi8jD6ZZVCeCi/hwjEHcqIrsCtJObxnPw3qRTIaEGKsjKTh4DjSMPMweaNmN8qhAur7zbMvJAjfgdsIGwCstA+OQ+ueIAzahrE9bWeSrowdT95aKl0uuSU2BRIXRBJG6jp20Yx0cgCMfi5iaFl69w1xZ1UWT3ra390RKbZUadRu6kqGdbp7JSn3NgUYFP+f0L0A0+Caepf0clg1iPWlYfbnvRSpjrHzwHtJvJWPxRtFjG2l9/bp00pZDN0V8qKnOiY1kLo4pg4m0ewg+YvsEof1zEnsPO1Juo2TZ9cz0D5oDW9YGXxNMkoDpAMsgHqIjwvApTW/5R1XVovT6fwDXOtpSMHr3c3eacwBlbATitPJF284K35yckwNqfR1GWZWNGPSHvFAF5abH0sqlijXPnzodVvDuBaLPiDobAYvFbTVXVV7qtl86xzO++Vqr6SuKdFVTEY+I18TyfoCKq89piyWyiUz9xFl8LdE2UwzWiOP+LwrxfH5Fmrq3MSOOnc3/9x/ASTAJAsAdpP9VQtr6kKTrjp4IBiyjD8gLtRF0HcH0hCaVxHHm/f3Rp6aphfd9fkopnCqJj79oJ5b7DpMa5Pz0dhPoCdn4uG+wmPT2fobwLCiwiXFx8saflpZeuVwW7GV7zqBEBIPB9NOc3e8vKfgv9q4fSjNSHrL6QURayn4Efo3HsXeJHJx180M4l4Do+KTzvzoIDS6/x04FAdVRlqzHvcpEeJr8xmRrGdOrPOZ/0YCpjX3L84eTWjJ6RfSqYEoJOZVh9ketI5/7oiNZAT9PIeWTT7Rhg6ZXSdC+u3gMGztDD5DcIPggm3kWG4dLZZ5LTFKsbLdQYMs+IH9vKZLoo1YuOvXuEcl2hdKlUvHUcsIhQavIxdP4uAETzW6VJief8MOyL/wpmIB8mi31J4xRpIJm8JuvEmiFZyeanXB0AClcpsjvIloITVQRHAktnIF1E2RON3YDloGvwiy3Xw5O8sdFQWVMyunRdsc6D9c1D4RaQPuP8sh11U3UhsKNoph4gU0J2rL6YEufR42hg6XygG1KPqmwldIxrAbJxerj1jcrEwHS6QGANq6jY3NjaGrHGcZJfu5qNblijC49wNw1+PN6U9MHmJ3TMe8mSXiajZynzyOOHogCWzg87Oocel0AmOP8bQLYd4/FtFT7fS/P37k2w4qfHz8eb7tmmK5ExXKsDmObTnZBpwQSiUI+i1CmLCli68GmUpCrvXyhUdTkEtADhGVcQoJP0YOfkK9ipg3U05Q3mU1pnVFa+PWvnzl4932y+d86aVbI7EjmFRdVaztXTsRQ6D/NyZ0NntGMcWAWitpJxfrKjXoyj4KIElgwAbZI1Hr8Mii5AJk6Tv9lwY0cP3wtwtgKZrUJhuwDAg1zlEcZVXHLJOxWvN+KLRiNV48Zp3Wp7R0dl1OerVGOxSlWICiaUSqGISgBmIiyVzcSG/1qAoha0TEX5CZOXaWnCLVrahUe4z6iQk5tpabT5seiBJddzYyBwRkxlyzHtSvoKXSbk+AcCwIiWb6a7Iq1Xszme+DQEDitgyfXcOHXqpHhvfL7K4nUwXFuHuaDp8vdCuTGFskdwsY0u5qY7lOXrbgtFUy7KHbbAsjKrxT+9RvCeOnRfZJQXXRWrReVr0LLlhAfIFCoda0PurSivFd3qa1yM2lYf3tNmpW04+nPC1GJhFN0DDRuXMwCBWtAMsPFp0JXG4hx3JfCGP6xdCri55iY/PRGEQQeDHqad/oZb8Ah0s8NI9y7CWvGtFVs2ds8JBrspwUh8/h/rQZNGxNdUjQAAAABJRU5ErkJggg=="

/***/ }),
/* 42 */
/***/ (function(module, exports) {

	module.exports = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACUAAAAkCAYAAAAOwvOmAAAAAXNSR0IArs4c6QAABMJJREFUWAnNmFtoXFUUhjMXZzIYU0Gb0glaBKEQFEG0iqVqA2qtsdqCgYIiChKf1AbKJGnuM6QZC3mIj4oKKkp8aI1XVNpKsQ8FX8oQtAg2QhNbW+MgIclcMn7/7jnDmeRMPGPSSTcs9t5rr/Wv/6yzz74cX02FJZlM3jQ7O7vL5/PtKhQKW3CP0t5MHaI/TVtyYXFx8YdQKDTe3d19ocIQNT6vDv39/Y8T9AD2zdQhj34FCP6E/bt+v/9tMHJe/P6TFED3AZpEmi3APIFO0R8PBAJnCTaVz+enGcvQ30xbWbuT8aeolc06+eHzK3Kor6/vU+qCdOVKWVKA+SCUoO7EWXZXIDBE/T76v8oBOvWjo6PhmZmZFl7lIDhNGoPQN8h+MP522jrbrqSsefMRhk8DkEOO1NbWJjs6OtJOZ6/tsbGxwMTExIsQO4w0gHcuGAzu6enp+cUNYxkpi9ApjO/B+TLyHE910s25Ul0ikbgtm80eg9i9+KbJ/MNgn12KU0IKAz8OnyEtkPmZp9nN0/y21Gk1/ZGRkUg6nf6QGPvAmYTYNuJecmL6nR3awxYhZejJtSakWO3t7XNg76f5I7KFeEchVfI1F0kNDg4+wIQ8iIO+on0YnsfpmhSwM8zRvYBPQuoh4mqpKZYiqVwud0RaSCV7e3s1pzwXgjyIPO/ZAcOurq4/efiXLJ/OoaGhW2x/QwrAPSh2QOhSJBIx5GwDLzVPe4CnHfFi67RhzTpBzK/QbVhYWOi2xwwpAF+TAoN4LBb7xx70WkMqgG/Aq73TDr8YfSAKr5CcWo35adzMwCO0s7znD6SsZiF+inhnkBvh8ZhiK1MtsAyiOPF/F0cBraawLByTP2/sWdValww76nEp1qmY2DYXZep2EeFLmFgnQjVNTU3abvJIVFuSSEVFhhROqV6P0traqpPHRWJrj9wUpGFIcSArS4rJuA07LRuuhbnQROoj2CVcDa4qU4x/ssK44otLNAjYimcbC+RlAretAGiGsDlUzoZMnGNsJVLGlTdW0OszGcpkMiZjbqA84asYb2TJaHATfD5HZtzGbB2k7nbDduhMfB5sWkvBNMnaSkdK1/ONHCF22QFQ0mRbyoBT0NZRMuCxo8mdSqU2YZ5n0l9Upn6XL8dYczJUu9qFyb2VmNoRpjTptU59a5F4ptpkHPHMR0S2v5OOqeL/ko5uGY8ODw9vcBhWrcnUMSs5PI4qqPY+HeBPIjfMz8+/QF3VQvy7CKglZxb5XsE1p3Q6eEs1r7JHZ3S1q1WImSQWFHy6F84rriFFR3uP7nINc3NzBzVQSWEK/IG9pKIyMDCwk5i7cUqHw+HiwmtICYlLgiGDUYyj8Y5K0PmMX6+rq9Mr8Fw4aW7ki3/PcjjMcnLFdl56m3mTSadzui4O95PB87bhWtbghohzHMztxDmN7ESXsWMUM2UpOjD4gmzdinwdj8fvsA3XqtYVC+yPwduOTBJvr5OQ4pRkSorr7jIqUirX3bX9Ki2z12m1jyOd6JTR9f3BYRNTzfv29Cuovr5+gZ9p0Wv6K8hJTG3WlCf4Yt6g2Uz2QkvHy/SLP80aGxvfaWtry5axK1Evm+gloy4da74t+70I0TDmU3xNq/69+C/+unE9hbrWZQAAAABJRU5ErkJggg=="

/***/ }),
/* 43 */,
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(process) {/**
	  * vue-router v2.5.3
	  * (c) 2017 Evan You
	  * @license MIT
	  */
	'use strict';

	/*  */

	function assert (condition, message) {
	  if (!condition) {
	    throw new Error(("[vue-router] " + message))
	  }
	}

	function warn (condition, message) {
	  if (process.env.NODE_ENV !== 'production' && !condition) {
	    typeof console !== 'undefined' && console.warn(("[vue-router] " + message));
	  }
	}

	var View = {
	  name: 'router-view',
	  functional: true,
	  props: {
	    name: {
	      type: String,
	      default: 'default'
	    }
	  },
	  render: function render (_, ref) {
	    var props = ref.props;
	    var children = ref.children;
	    var parent = ref.parent;
	    var data = ref.data;

	    data.routerView = true;

	    // directly use parent context's createElement() function
	    // so that components rendered by router-view can resolve named slots
	    var h = parent.$createElement;
	    var name = props.name;
	    var route = parent.$route;
	    var cache = parent._routerViewCache || (parent._routerViewCache = {});

	    // determine current view depth, also check to see if the tree
	    // has been toggled inactive but kept-alive.
	    var depth = 0;
	    var inactive = false;
	    while (parent) {
	      if (parent.$vnode && parent.$vnode.data.routerView) {
	        depth++;
	      }
	      if (parent._inactive) {
	        inactive = true;
	      }
	      parent = parent.$parent;
	    }
	    data.routerViewDepth = depth;

	    // render previous view if the tree is inactive and kept-alive
	    if (inactive) {
	      return h(cache[name], data, children)
	    }

	    var matched = route.matched[depth];
	    // render empty node if no matched route
	    if (!matched) {
	      cache[name] = null;
	      return h()
	    }

	    var component = cache[name] = matched.components[name];

	    // attach instance registration hook
	    // this will be called in the instance's injected lifecycle hooks
	    data.registerRouteInstance = function (vm, val) {
	      // val could be undefined for unregistration
	      var current = matched.instances[name];
	      if (
	        (val && current !== vm) ||
	        (!val && current === vm)
	      ) {
	        matched.instances[name] = val;
	      }
	    }

	    // also regiseter instance in prepatch hook
	    // in case the same component instance is reused across different routes
	    ;(data.hook || (data.hook = {})).prepatch = function (_, vnode) {
	      matched.instances[name] = vnode.componentInstance;
	    };

	    // resolve props
	    data.props = resolveProps(route, matched.props && matched.props[name]);

	    return h(component, data, children)
	  }
	};

	function resolveProps (route, config) {
	  switch (typeof config) {
	    case 'undefined':
	      return
	    case 'object':
	      return config
	    case 'function':
	      return config(route)
	    case 'boolean':
	      return config ? route.params : undefined
	    default:
	      if (process.env.NODE_ENV !== 'production') {
	        warn(
	          false,
	          "props in \"" + (route.path) + "\" is a " + (typeof config) + ", " +
	          "expecting an object, function or boolean."
	        );
	      }
	  }
	}

	/*  */

	var encodeReserveRE = /[!'()*]/g;
	var encodeReserveReplacer = function (c) { return '%' + c.charCodeAt(0).toString(16); };
	var commaRE = /%2C/g;

	// fixed encodeURIComponent which is more conformant to RFC3986:
	// - escapes [!'()*]
	// - preserve commas
	var encode = function (str) { return encodeURIComponent(str)
	  .replace(encodeReserveRE, encodeReserveReplacer)
	  .replace(commaRE, ','); };

	var decode = decodeURIComponent;

	function resolveQuery (
	  query,
	  extraQuery,
	  _parseQuery
	) {
	  if ( extraQuery === void 0 ) extraQuery = {};

	  var parse = _parseQuery || parseQuery;
	  var parsedQuery;
	  try {
	    parsedQuery = parse(query || '');
	  } catch (e) {
	    process.env.NODE_ENV !== 'production' && warn(false, e.message);
	    parsedQuery = {};
	  }
	  for (var key in extraQuery) {
	    var val = extraQuery[key];
	    parsedQuery[key] = Array.isArray(val) ? val.slice() : val;
	  }
	  return parsedQuery
	}

	function parseQuery (query) {
	  var res = {};

	  query = query.trim().replace(/^(\?|#|&)/, '');

	  if (!query) {
	    return res
	  }

	  query.split('&').forEach(function (param) {
	    var parts = param.replace(/\+/g, ' ').split('=');
	    var key = decode(parts.shift());
	    var val = parts.length > 0
	      ? decode(parts.join('='))
	      : null;

	    if (res[key] === undefined) {
	      res[key] = val;
	    } else if (Array.isArray(res[key])) {
	      res[key].push(val);
	    } else {
	      res[key] = [res[key], val];
	    }
	  });

	  return res
	}

	function stringifyQuery (obj) {
	  var res = obj ? Object.keys(obj).map(function (key) {
	    var val = obj[key];

	    if (val === undefined) {
	      return ''
	    }

	    if (val === null) {
	      return encode(key)
	    }

	    if (Array.isArray(val)) {
	      var result = [];
	      val.slice().forEach(function (val2) {
	        if (val2 === undefined) {
	          return
	        }
	        if (val2 === null) {
	          result.push(encode(key));
	        } else {
	          result.push(encode(key) + '=' + encode(val2));
	        }
	      });
	      return result.join('&')
	    }

	    return encode(key) + '=' + encode(val)
	  }).filter(function (x) { return x.length > 0; }).join('&') : null;
	  return res ? ("?" + res) : ''
	}

	/*  */


	var trailingSlashRE = /\/?$/;

	function createRoute (
	  record,
	  location,
	  redirectedFrom,
	  router
	) {
	  var stringifyQuery$$1 = router && router.options.stringifyQuery;
	  var route = {
	    name: location.name || (record && record.name),
	    meta: (record && record.meta) || {},
	    path: location.path || '/',
	    hash: location.hash || '',
	    query: location.query || {},
	    params: location.params || {},
	    fullPath: getFullPath(location, stringifyQuery$$1),
	    matched: record ? formatMatch(record) : []
	  };
	  if (redirectedFrom) {
	    route.redirectedFrom = getFullPath(redirectedFrom, stringifyQuery$$1);
	  }
	  return Object.freeze(route)
	}

	// the starting route that represents the initial state
	var START = createRoute(null, {
	  path: '/'
	});

	function formatMatch (record) {
	  var res = [];
	  while (record) {
	    res.unshift(record);
	    record = record.parent;
	  }
	  return res
	}

	function getFullPath (
	  ref,
	  _stringifyQuery
	) {
	  var path = ref.path;
	  var query = ref.query; if ( query === void 0 ) query = {};
	  var hash = ref.hash; if ( hash === void 0 ) hash = '';

	  var stringify = _stringifyQuery || stringifyQuery;
	  return (path || '/') + stringify(query) + hash
	}

	function isSameRoute (a, b) {
	  if (b === START) {
	    return a === b
	  } else if (!b) {
	    return false
	  } else if (a.path && b.path) {
	    return (
	      a.path.replace(trailingSlashRE, '') === b.path.replace(trailingSlashRE, '') &&
	      a.hash === b.hash &&
	      isObjectEqual(a.query, b.query)
	    )
	  } else if (a.name && b.name) {
	    return (
	      a.name === b.name &&
	      a.hash === b.hash &&
	      isObjectEqual(a.query, b.query) &&
	      isObjectEqual(a.params, b.params)
	    )
	  } else {
	    return false
	  }
	}

	function isObjectEqual (a, b) {
	  if ( a === void 0 ) a = {};
	  if ( b === void 0 ) b = {};

	  var aKeys = Object.keys(a);
	  var bKeys = Object.keys(b);
	  if (aKeys.length !== bKeys.length) {
	    return false
	  }
	  return aKeys.every(function (key) { return String(a[key]) === String(b[key]); })
	}

	function isIncludedRoute (current, target) {
	  return (
	    current.path.replace(trailingSlashRE, '/').indexOf(
	      target.path.replace(trailingSlashRE, '/')
	    ) === 0 &&
	    (!target.hash || current.hash === target.hash) &&
	    queryIncludes(current.query, target.query)
	  )
	}

	function queryIncludes (current, target) {
	  for (var key in target) {
	    if (!(key in current)) {
	      return false
	    }
	  }
	  return true
	}

	/*  */

	// work around weird flow bug
	var toTypes = [String, Object];
	var eventTypes = [String, Array];

	var Link = {
	  name: 'router-link',
	  props: {
	    to: {
	      type: toTypes,
	      required: true
	    },
	    tag: {
	      type: String,
	      default: 'a'
	    },
	    exact: Boolean,
	    append: Boolean,
	    replace: Boolean,
	    activeClass: String,
	    exactActiveClass: String,
	    event: {
	      type: eventTypes,
	      default: 'click'
	    }
	  },
	  render: function render (h) {
	    var this$1 = this;

	    var router = this.$router;
	    var current = this.$route;
	    var ref = router.resolve(this.to, current, this.append);
	    var location = ref.location;
	    var route = ref.route;
	    var href = ref.href;

	    var classes = {};
	    var globalActiveClass = router.options.linkActiveClass;
	    var globalExactActiveClass = router.options.linkExactActiveClass;
	    // Support global empty active class
	    var activeClassFallback = globalActiveClass == null
	            ? 'router-link-active'
	            : globalActiveClass;
	    var exactActiveClassFallback = globalExactActiveClass == null
	            ? 'router-link-exact-active'
	            : globalExactActiveClass;
	    var activeClass = this.activeClass == null
	            ? activeClassFallback
	            : this.activeClass;
	    var exactActiveClass = this.exactActiveClass == null
	            ? exactActiveClassFallback
	            : this.exactActiveClass;
	    var compareTarget = location.path
	      ? createRoute(null, location, null, router)
	      : route;

	    classes[exactActiveClass] = isSameRoute(current, compareTarget);
	    classes[activeClass] = this.exact
	      ? classes[exactActiveClass]
	      : isIncludedRoute(current, compareTarget);

	    var handler = function (e) {
	      if (guardEvent(e)) {
	        if (this$1.replace) {
	          router.replace(location);
	        } else {
	          router.push(location);
	        }
	      }
	    };

	    var on = { click: guardEvent };
	    if (Array.isArray(this.event)) {
	      this.event.forEach(function (e) { on[e] = handler; });
	    } else {
	      on[this.event] = handler;
	    }

	    var data = {
	      class: classes
	    };

	    if (this.tag === 'a') {
	      data.on = on;
	      data.attrs = { href: href };
	    } else {
	      // find the first <a> child and apply listener and href
	      var a = findAnchor(this.$slots.default);
	      if (a) {
	        // in case the <a> is a static node
	        a.isStatic = false;
	        var extend = _Vue.util.extend;
	        var aData = a.data = extend({}, a.data);
	        aData.on = on;
	        var aAttrs = a.data.attrs = extend({}, a.data.attrs);
	        aAttrs.href = href;
	      } else {
	        // doesn't have <a> child, apply listener to self
	        data.on = on;
	      }
	    }

	    return h(this.tag, data, this.$slots.default)
	  }
	};

	function guardEvent (e) {
	  // don't redirect with control keys
	  if (e.metaKey || e.ctrlKey || e.shiftKey) { return }
	  // don't redirect when preventDefault called
	  if (e.defaultPrevented) { return }
	  // don't redirect on right click
	  if (e.button !== undefined && e.button !== 0) { return }
	  // don't redirect if `target="_blank"`
	  if (e.currentTarget && e.currentTarget.getAttribute) {
	    var target = e.currentTarget.getAttribute('target');
	    if (/\b_blank\b/i.test(target)) { return }
	  }
	  // this may be a Weex event which doesn't have this method
	  if (e.preventDefault) {
	    e.preventDefault();
	  }
	  return true
	}

	function findAnchor (children) {
	  if (children) {
	    var child;
	    for (var i = 0; i < children.length; i++) {
	      child = children[i];
	      if (child.tag === 'a') {
	        return child
	      }
	      if (child.children && (child = findAnchor(child.children))) {
	        return child
	      }
	    }
	  }
	}

	var _Vue;

	function install (Vue) {
	  if (install.installed) { return }
	  install.installed = true;

	  _Vue = Vue;

	  Object.defineProperty(Vue.prototype, '$router', {
	    get: function get () { return this.$root._router }
	  });

	  Object.defineProperty(Vue.prototype, '$route', {
	    get: function get () { return this.$root._route }
	  });

	  var isDef = function (v) { return v !== undefined; };

	  var registerInstance = function (vm, callVal) {
	    var i = vm.$options._parentVnode;
	    if (isDef(i) && isDef(i = i.data) && isDef(i = i.registerRouteInstance)) {
	      i(vm, callVal);
	    }
	  };

	  Vue.mixin({
	    beforeCreate: function beforeCreate () {
	      if (isDef(this.$options.router)) {
	        this._router = this.$options.router;
	        this._router.init(this);
	        Vue.util.defineReactive(this, '_route', this._router.history.current);
	      }
	      registerInstance(this, this);
	    },
	    destroyed: function destroyed () {
	      registerInstance(this);
	    }
	  });

	  Vue.component('router-view', View);
	  Vue.component('router-link', Link);

	  var strats = Vue.config.optionMergeStrategies;
	  // use the same hook merging strategy for route hooks
	  strats.beforeRouteEnter = strats.beforeRouteLeave = strats.created;
	}

	/*  */

	var inBrowser = typeof window !== 'undefined';

	/*  */

	function resolvePath (
	  relative,
	  base,
	  append
	) {
	  var firstChar = relative.charAt(0);
	  if (firstChar === '/') {
	    return relative
	  }

	  if (firstChar === '?' || firstChar === '#') {
	    return base + relative
	  }

	  var stack = base.split('/');

	  // remove trailing segment if:
	  // - not appending
	  // - appending to trailing slash (last segment is empty)
	  if (!append || !stack[stack.length - 1]) {
	    stack.pop();
	  }

	  // resolve relative path
	  var segments = relative.replace(/^\//, '').split('/');
	  for (var i = 0; i < segments.length; i++) {
	    var segment = segments[i];
	    if (segment === '..') {
	      stack.pop();
	    } else if (segment !== '.') {
	      stack.push(segment);
	    }
	  }

	  // ensure leading slash
	  if (stack[0] !== '') {
	    stack.unshift('');
	  }

	  return stack.join('/')
	}

	function parsePath (path) {
	  var hash = '';
	  var query = '';

	  var hashIndex = path.indexOf('#');
	  if (hashIndex >= 0) {
	    hash = path.slice(hashIndex);
	    path = path.slice(0, hashIndex);
	  }

	  var queryIndex = path.indexOf('?');
	  if (queryIndex >= 0) {
	    query = path.slice(queryIndex + 1);
	    path = path.slice(0, queryIndex);
	  }

	  return {
	    path: path,
	    query: query,
	    hash: hash
	  }
	}

	function cleanPath (path) {
	  return path.replace(/\/\//g, '/')
	}

	var index$1 = Array.isArray || function (arr) {
	  return Object.prototype.toString.call(arr) == '[object Array]';
	};

	/**
	 * Expose `pathToRegexp`.
	 */
	var index = pathToRegexp;
	var parse_1 = parse;
	var compile_1 = compile;
	var tokensToFunction_1 = tokensToFunction;
	var tokensToRegExp_1 = tokensToRegExp;

	/**
	 * The main path matching regexp utility.
	 *
	 * @type {RegExp}
	 */
	var PATH_REGEXP = new RegExp([
	  // Match escaped characters that would otherwise appear in future matches.
	  // This allows the user to escape special characters that won't transform.
	  '(\\\\.)',
	  // Match Express-style parameters and un-named parameters with a prefix
	  // and optional suffixes. Matches appear as:
	  //
	  // "/:test(\\d+)?" => ["/", "test", "\d+", undefined, "?", undefined]
	  // "/route(\\d+)"  => [undefined, undefined, undefined, "\d+", undefined, undefined]
	  // "/*"            => ["/", undefined, undefined, undefined, undefined, "*"]
	  '([\\/.])?(?:(?:\\:(\\w+)(?:\\(((?:\\\\.|[^\\\\()])+)\\))?|\\(((?:\\\\.|[^\\\\()])+)\\))([+*?])?|(\\*))'
	].join('|'), 'g');

	/**
	 * Parse a string for the raw tokens.
	 *
	 * @param  {string}  str
	 * @param  {Object=} options
	 * @return {!Array}
	 */
	function parse (str, options) {
	  var tokens = [];
	  var key = 0;
	  var index = 0;
	  var path = '';
	  var defaultDelimiter = options && options.delimiter || '/';
	  var res;

	  while ((res = PATH_REGEXP.exec(str)) != null) {
	    var m = res[0];
	    var escaped = res[1];
	    var offset = res.index;
	    path += str.slice(index, offset);
	    index = offset + m.length;

	    // Ignore already escaped sequences.
	    if (escaped) {
	      path += escaped[1];
	      continue
	    }

	    var next = str[index];
	    var prefix = res[2];
	    var name = res[3];
	    var capture = res[4];
	    var group = res[5];
	    var modifier = res[6];
	    var asterisk = res[7];

	    // Push the current path onto the tokens.
	    if (path) {
	      tokens.push(path);
	      path = '';
	    }

	    var partial = prefix != null && next != null && next !== prefix;
	    var repeat = modifier === '+' || modifier === '*';
	    var optional = modifier === '?' || modifier === '*';
	    var delimiter = res[2] || defaultDelimiter;
	    var pattern = capture || group;

	    tokens.push({
	      name: name || key++,
	      prefix: prefix || '',
	      delimiter: delimiter,
	      optional: optional,
	      repeat: repeat,
	      partial: partial,
	      asterisk: !!asterisk,
	      pattern: pattern ? escapeGroup(pattern) : (asterisk ? '.*' : '[^' + escapeString(delimiter) + ']+?')
	    });
	  }

	  // Match any characters still remaining.
	  if (index < str.length) {
	    path += str.substr(index);
	  }

	  // If the path exists, push it onto the end.
	  if (path) {
	    tokens.push(path);
	  }

	  return tokens
	}

	/**
	 * Compile a string to a template function for the path.
	 *
	 * @param  {string}             str
	 * @param  {Object=}            options
	 * @return {!function(Object=, Object=)}
	 */
	function compile (str, options) {
	  return tokensToFunction(parse(str, options))
	}

	/**
	 * Prettier encoding of URI path segments.
	 *
	 * @param  {string}
	 * @return {string}
	 */
	function encodeURIComponentPretty (str) {
	  return encodeURI(str).replace(/[\/?#]/g, function (c) {
	    return '%' + c.charCodeAt(0).toString(16).toUpperCase()
	  })
	}

	/**
	 * Encode the asterisk parameter. Similar to `pretty`, but allows slashes.
	 *
	 * @param  {string}
	 * @return {string}
	 */
	function encodeAsterisk (str) {
	  return encodeURI(str).replace(/[?#]/g, function (c) {
	    return '%' + c.charCodeAt(0).toString(16).toUpperCase()
	  })
	}

	/**
	 * Expose a method for transforming tokens into the path function.
	 */
	function tokensToFunction (tokens) {
	  // Compile all the tokens into regexps.
	  var matches = new Array(tokens.length);

	  // Compile all the patterns before compilation.
	  for (var i = 0; i < tokens.length; i++) {
	    if (typeof tokens[i] === 'object') {
	      matches[i] = new RegExp('^(?:' + tokens[i].pattern + ')$');
	    }
	  }

	  return function (obj, opts) {
	    var path = '';
	    var data = obj || {};
	    var options = opts || {};
	    var encode = options.pretty ? encodeURIComponentPretty : encodeURIComponent;

	    for (var i = 0; i < tokens.length; i++) {
	      var token = tokens[i];

	      if (typeof token === 'string') {
	        path += token;

	        continue
	      }

	      var value = data[token.name];
	      var segment;

	      if (value == null) {
	        if (token.optional) {
	          // Prepend partial segment prefixes.
	          if (token.partial) {
	            path += token.prefix;
	          }

	          continue
	        } else {
	          throw new TypeError('Expected "' + token.name + '" to be defined')
	        }
	      }

	      if (index$1(value)) {
	        if (!token.repeat) {
	          throw new TypeError('Expected "' + token.name + '" to not repeat, but received `' + JSON.stringify(value) + '`')
	        }

	        if (value.length === 0) {
	          if (token.optional) {
	            continue
	          } else {
	            throw new TypeError('Expected "' + token.name + '" to not be empty')
	          }
	        }

	        for (var j = 0; j < value.length; j++) {
	          segment = encode(value[j]);

	          if (!matches[i].test(segment)) {
	            throw new TypeError('Expected all "' + token.name + '" to match "' + token.pattern + '", but received `' + JSON.stringify(segment) + '`')
	          }

	          path += (j === 0 ? token.prefix : token.delimiter) + segment;
	        }

	        continue
	      }

	      segment = token.asterisk ? encodeAsterisk(value) : encode(value);

	      if (!matches[i].test(segment)) {
	        throw new TypeError('Expected "' + token.name + '" to match "' + token.pattern + '", but received "' + segment + '"')
	      }

	      path += token.prefix + segment;
	    }

	    return path
	  }
	}

	/**
	 * Escape a regular expression string.
	 *
	 * @param  {string} str
	 * @return {string}
	 */
	function escapeString (str) {
	  return str.replace(/([.+*?=^!:${}()[\]|\/\\])/g, '\\$1')
	}

	/**
	 * Escape the capturing group by escaping special characters and meaning.
	 *
	 * @param  {string} group
	 * @return {string}
	 */
	function escapeGroup (group) {
	  return group.replace(/([=!:$\/()])/g, '\\$1')
	}

	/**
	 * Attach the keys as a property of the regexp.
	 *
	 * @param  {!RegExp} re
	 * @param  {Array}   keys
	 * @return {!RegExp}
	 */
	function attachKeys (re, keys) {
	  re.keys = keys;
	  return re
	}

	/**
	 * Get the flags for a regexp from the options.
	 *
	 * @param  {Object} options
	 * @return {string}
	 */
	function flags (options) {
	  return options.sensitive ? '' : 'i'
	}

	/**
	 * Pull out keys from a regexp.
	 *
	 * @param  {!RegExp} path
	 * @param  {!Array}  keys
	 * @return {!RegExp}
	 */
	function regexpToRegexp (path, keys) {
	  // Use a negative lookahead to match only capturing groups.
	  var groups = path.source.match(/\((?!\?)/g);

	  if (groups) {
	    for (var i = 0; i < groups.length; i++) {
	      keys.push({
	        name: i,
	        prefix: null,
	        delimiter: null,
	        optional: false,
	        repeat: false,
	        partial: false,
	        asterisk: false,
	        pattern: null
	      });
	    }
	  }

	  return attachKeys(path, keys)
	}

	/**
	 * Transform an array into a regexp.
	 *
	 * @param  {!Array}  path
	 * @param  {Array}   keys
	 * @param  {!Object} options
	 * @return {!RegExp}
	 */
	function arrayToRegexp (path, keys, options) {
	  var parts = [];

	  for (var i = 0; i < path.length; i++) {
	    parts.push(pathToRegexp(path[i], keys, options).source);
	  }

	  var regexp = new RegExp('(?:' + parts.join('|') + ')', flags(options));

	  return attachKeys(regexp, keys)
	}

	/**
	 * Create a path regexp from string input.
	 *
	 * @param  {string}  path
	 * @param  {!Array}  keys
	 * @param  {!Object} options
	 * @return {!RegExp}
	 */
	function stringToRegexp (path, keys, options) {
	  return tokensToRegExp(parse(path, options), keys, options)
	}

	/**
	 * Expose a function for taking tokens and returning a RegExp.
	 *
	 * @param  {!Array}          tokens
	 * @param  {(Array|Object)=} keys
	 * @param  {Object=}         options
	 * @return {!RegExp}
	 */
	function tokensToRegExp (tokens, keys, options) {
	  if (!index$1(keys)) {
	    options = /** @type {!Object} */ (keys || options);
	    keys = [];
	  }

	  options = options || {};

	  var strict = options.strict;
	  var end = options.end !== false;
	  var route = '';

	  // Iterate over the tokens and create our regexp string.
	  for (var i = 0; i < tokens.length; i++) {
	    var token = tokens[i];

	    if (typeof token === 'string') {
	      route += escapeString(token);
	    } else {
	      var prefix = escapeString(token.prefix);
	      var capture = '(?:' + token.pattern + ')';

	      keys.push(token);

	      if (token.repeat) {
	        capture += '(?:' + prefix + capture + ')*';
	      }

	      if (token.optional) {
	        if (!token.partial) {
	          capture = '(?:' + prefix + '(' + capture + '))?';
	        } else {
	          capture = prefix + '(' + capture + ')?';
	        }
	      } else {
	        capture = prefix + '(' + capture + ')';
	      }

	      route += capture;
	    }
	  }

	  var delimiter = escapeString(options.delimiter || '/');
	  var endsWithDelimiter = route.slice(-delimiter.length) === delimiter;

	  // In non-strict mode we allow a slash at the end of match. If the path to
	  // match already ends with a slash, we remove it for consistency. The slash
	  // is valid at the end of a path match, not in the middle. This is important
	  // in non-ending mode, where "/test/" shouldn't match "/test//route".
	  if (!strict) {
	    route = (endsWithDelimiter ? route.slice(0, -delimiter.length) : route) + '(?:' + delimiter + '(?=$))?';
	  }

	  if (end) {
	    route += '$';
	  } else {
	    // In non-ending mode, we need the capturing groups to match as much as
	    // possible by using a positive lookahead to the end or next path segment.
	    route += strict && endsWithDelimiter ? '' : '(?=' + delimiter + '|$)';
	  }

	  return attachKeys(new RegExp('^' + route, flags(options)), keys)
	}

	/**
	 * Normalize the given path string, returning a regular expression.
	 *
	 * An empty array can be passed in for the keys, which will hold the
	 * placeholder key descriptions. For example, using `/user/:id`, `keys` will
	 * contain `[{ name: 'id', delimiter: '/', optional: false, repeat: false }]`.
	 *
	 * @param  {(string|RegExp|Array)} path
	 * @param  {(Array|Object)=}       keys
	 * @param  {Object=}               options
	 * @return {!RegExp}
	 */
	function pathToRegexp (path, keys, options) {
	  if (!index$1(keys)) {
	    options = /** @type {!Object} */ (keys || options);
	    keys = [];
	  }

	  options = options || {};

	  if (path instanceof RegExp) {
	    return regexpToRegexp(path, /** @type {!Array} */ (keys))
	  }

	  if (index$1(path)) {
	    return arrayToRegexp(/** @type {!Array} */ (path), /** @type {!Array} */ (keys), options)
	  }

	  return stringToRegexp(/** @type {string} */ (path), /** @type {!Array} */ (keys), options)
	}

	index.parse = parse_1;
	index.compile = compile_1;
	index.tokensToFunction = tokensToFunction_1;
	index.tokensToRegExp = tokensToRegExp_1;

	/*  */

	var regexpCompileCache = Object.create(null);

	function fillParams (
	  path,
	  params,
	  routeMsg
	) {
	  try {
	    var filler =
	      regexpCompileCache[path] ||
	      (regexpCompileCache[path] = index.compile(path));
	    return filler(params || {}, { pretty: true })
	  } catch (e) {
	    if (process.env.NODE_ENV !== 'production') {
	      warn(false, ("missing param for " + routeMsg + ": " + (e.message)));
	    }
	    return ''
	  }
	}

	/*  */

	function createRouteMap (
	  routes,
	  oldPathList,
	  oldPathMap,
	  oldNameMap
	) {
	  // the path list is used to control path matching priority
	  var pathList = oldPathList || [];
	  var pathMap = oldPathMap || Object.create(null);
	  var nameMap = oldNameMap || Object.create(null);

	  routes.forEach(function (route) {
	    addRouteRecord(pathList, pathMap, nameMap, route);
	  });

	  // ensure wildcard routes are always at the end
	  for (var i = 0, l = pathList.length; i < l; i++) {
	    if (pathList[i] === '*') {
	      pathList.push(pathList.splice(i, 1)[0]);
	      l--;
	      i--;
	    }
	  }

	  return {
	    pathList: pathList,
	    pathMap: pathMap,
	    nameMap: nameMap
	  }
	}

	function addRouteRecord (
	  pathList,
	  pathMap,
	  nameMap,
	  route,
	  parent,
	  matchAs
	) {
	  var path = route.path;
	  var name = route.name;
	  if (process.env.NODE_ENV !== 'production') {
	    assert(path != null, "\"path\" is required in a route configuration.");
	    assert(
	      typeof route.component !== 'string',
	      "route config \"component\" for path: " + (String(path || name)) + " cannot be a " +
	      "string id. Use an actual component instead."
	    );
	  }

	  var normalizedPath = normalizePath(path, parent);
	  var record = {
	    path: normalizedPath,
	    regex: compileRouteRegex(normalizedPath),
	    components: route.components || { default: route.component },
	    instances: {},
	    name: name,
	    parent: parent,
	    matchAs: matchAs,
	    redirect: route.redirect,
	    beforeEnter: route.beforeEnter,
	    meta: route.meta || {},
	    props: route.props == null
	      ? {}
	      : route.components
	        ? route.props
	        : { default: route.props }
	  };

	  if (route.children) {
	    // Warn if route is named and has a default child route.
	    // If users navigate to this route by name, the default child will
	    // not be rendered (GH Issue #629)
	    if (process.env.NODE_ENV !== 'production') {
	      if (route.name && route.children.some(function (child) { return /^\/?$/.test(child.path); })) {
	        warn(
	          false,
	          "Named Route '" + (route.name) + "' has a default child route. " +
	          "When navigating to this named route (:to=\"{name: '" + (route.name) + "'\"), " +
	          "the default child route will not be rendered. Remove the name from " +
	          "this route and use the name of the default child route for named " +
	          "links instead."
	        );
	      }
	    }
	    route.children.forEach(function (child) {
	      var childMatchAs = matchAs
	        ? cleanPath((matchAs + "/" + (child.path)))
	        : undefined;
	      addRouteRecord(pathList, pathMap, nameMap, child, record, childMatchAs);
	    });
	  }

	  if (route.alias !== undefined) {
	    if (Array.isArray(route.alias)) {
	      route.alias.forEach(function (alias) {
	        var aliasRoute = {
	          path: alias,
	          children: route.children
	        };
	        addRouteRecord(pathList, pathMap, nameMap, aliasRoute, parent, record.path);
	      });
	    } else {
	      var aliasRoute = {
	        path: route.alias,
	        children: route.children
	      };
	      addRouteRecord(pathList, pathMap, nameMap, aliasRoute, parent, record.path);
	    }
	  }

	  if (!pathMap[record.path]) {
	    pathList.push(record.path);
	    pathMap[record.path] = record;
	  }

	  if (name) {
	    if (!nameMap[name]) {
	      nameMap[name] = record;
	    } else if (process.env.NODE_ENV !== 'production' && !matchAs) {
	      warn(
	        false,
	        "Duplicate named routes definition: " +
	        "{ name: \"" + name + "\", path: \"" + (record.path) + "\" }"
	      );
	    }
	  }
	}

	function compileRouteRegex (path) {
	  var regex = index(path);
	  if (process.env.NODE_ENV !== 'production') {
	    var keys = {};
	    regex.keys.forEach(function (key) {
	      warn(!keys[key.name], ("Duplicate param keys in route with path: \"" + path + "\""));
	      keys[key.name] = true;
	    });
	  }
	  return regex
	}

	function normalizePath (path, parent) {
	  path = path.replace(/\/$/, '');
	  if (path[0] === '/') { return path }
	  if (parent == null) { return path }
	  return cleanPath(((parent.path) + "/" + path))
	}

	/*  */


	function normalizeLocation (
	  raw,
	  current,
	  append,
	  router
	) {
	  var next = typeof raw === 'string' ? { path: raw } : raw;
	  // named target
	  if (next.name || next._normalized) {
	    return next
	  }

	  // relative params
	  if (!next.path && next.params && current) {
	    next = assign({}, next);
	    next._normalized = true;
	    var params = assign(assign({}, current.params), next.params);
	    if (current.name) {
	      next.name = current.name;
	      next.params = params;
	    } else if (current.matched) {
	      var rawPath = current.matched[current.matched.length - 1].path;
	      next.path = fillParams(rawPath, params, ("path " + (current.path)));
	    } else if (process.env.NODE_ENV !== 'production') {
	      warn(false, "relative params navigation requires a current route.");
	    }
	    return next
	  }

	  var parsedPath = parsePath(next.path || '');
	  var basePath = (current && current.path) || '/';
	  var path = parsedPath.path
	    ? resolvePath(parsedPath.path, basePath, append || next.append)
	    : basePath;

	  var query = resolveQuery(
	    parsedPath.query,
	    next.query,
	    router && router.options.parseQuery
	  );

	  var hash = next.hash || parsedPath.hash;
	  if (hash && hash.charAt(0) !== '#') {
	    hash = "#" + hash;
	  }

	  return {
	    _normalized: true,
	    path: path,
	    query: query,
	    hash: hash
	  }
	}

	function assign (a, b) {
	  for (var key in b) {
	    a[key] = b[key];
	  }
	  return a
	}

	/*  */


	function createMatcher (
	  routes,
	  router
	) {
	  var ref = createRouteMap(routes);
	  var pathList = ref.pathList;
	  var pathMap = ref.pathMap;
	  var nameMap = ref.nameMap;

	  function addRoutes (routes) {
	    createRouteMap(routes, pathList, pathMap, nameMap);
	  }

	  function match (
	    raw,
	    currentRoute,
	    redirectedFrom
	  ) {
	    var location = normalizeLocation(raw, currentRoute, false, router);
	    var name = location.name;

	    if (name) {
	      var record = nameMap[name];
	      if (process.env.NODE_ENV !== 'production') {
	        warn(record, ("Route with name '" + name + "' does not exist"));
	      }
	      var paramNames = record.regex.keys
	        .filter(function (key) { return !key.optional; })
	        .map(function (key) { return key.name; });

	      if (typeof location.params !== 'object') {
	        location.params = {};
	      }

	      if (currentRoute && typeof currentRoute.params === 'object') {
	        for (var key in currentRoute.params) {
	          if (!(key in location.params) && paramNames.indexOf(key) > -1) {
	            location.params[key] = currentRoute.params[key];
	          }
	        }
	      }

	      if (record) {
	        location.path = fillParams(record.path, location.params, ("named route \"" + name + "\""));
	        return _createRoute(record, location, redirectedFrom)
	      }
	    } else if (location.path) {
	      location.params = {};
	      for (var i = 0; i < pathList.length; i++) {
	        var path = pathList[i];
	        var record$1 = pathMap[path];
	        if (matchRoute(record$1.regex, location.path, location.params)) {
	          return _createRoute(record$1, location, redirectedFrom)
	        }
	      }
	    }
	    // no match
	    return _createRoute(null, location)
	  }

	  function redirect (
	    record,
	    location
	  ) {
	    var originalRedirect = record.redirect;
	    var redirect = typeof originalRedirect === 'function'
	        ? originalRedirect(createRoute(record, location, null, router))
	        : originalRedirect;

	    if (typeof redirect === 'string') {
	      redirect = { path: redirect };
	    }

	    if (!redirect || typeof redirect !== 'object') {
	      if (process.env.NODE_ENV !== 'production') {
	        warn(
	          false, ("invalid redirect option: " + (JSON.stringify(redirect)))
	        );
	      }
	      return _createRoute(null, location)
	    }

	    var re = redirect;
	    var name = re.name;
	    var path = re.path;
	    var query = location.query;
	    var hash = location.hash;
	    var params = location.params;
	    query = re.hasOwnProperty('query') ? re.query : query;
	    hash = re.hasOwnProperty('hash') ? re.hash : hash;
	    params = re.hasOwnProperty('params') ? re.params : params;

	    if (name) {
	      // resolved named direct
	      var targetRecord = nameMap[name];
	      if (process.env.NODE_ENV !== 'production') {
	        assert(targetRecord, ("redirect failed: named route \"" + name + "\" not found."));
	      }
	      return match({
	        _normalized: true,
	        name: name,
	        query: query,
	        hash: hash,
	        params: params
	      }, undefined, location)
	    } else if (path) {
	      // 1. resolve relative redirect
	      var rawPath = resolveRecordPath(path, record);
	      // 2. resolve params
	      var resolvedPath = fillParams(rawPath, params, ("redirect route with path \"" + rawPath + "\""));
	      // 3. rematch with existing query and hash
	      return match({
	        _normalized: true,
	        path: resolvedPath,
	        query: query,
	        hash: hash
	      }, undefined, location)
	    } else {
	      if (process.env.NODE_ENV !== 'production') {
	        warn(false, ("invalid redirect option: " + (JSON.stringify(redirect))));
	      }
	      return _createRoute(null, location)
	    }
	  }

	  function alias (
	    record,
	    location,
	    matchAs
	  ) {
	    var aliasedPath = fillParams(matchAs, location.params, ("aliased route with path \"" + matchAs + "\""));
	    var aliasedMatch = match({
	      _normalized: true,
	      path: aliasedPath
	    });
	    if (aliasedMatch) {
	      var matched = aliasedMatch.matched;
	      var aliasedRecord = matched[matched.length - 1];
	      location.params = aliasedMatch.params;
	      return _createRoute(aliasedRecord, location)
	    }
	    return _createRoute(null, location)
	  }

	  function _createRoute (
	    record,
	    location,
	    redirectedFrom
	  ) {
	    if (record && record.redirect) {
	      return redirect(record, redirectedFrom || location)
	    }
	    if (record && record.matchAs) {
	      return alias(record, location, record.matchAs)
	    }
	    return createRoute(record, location, redirectedFrom, router)
	  }

	  return {
	    match: match,
	    addRoutes: addRoutes
	  }
	}

	function matchRoute (
	  regex,
	  path,
	  params
	) {
	  var m = path.match(regex);

	  if (!m) {
	    return false
	  } else if (!params) {
	    return true
	  }

	  for (var i = 1, len = m.length; i < len; ++i) {
	    var key = regex.keys[i - 1];
	    var val = typeof m[i] === 'string' ? decodeURIComponent(m[i]) : m[i];
	    if (key) {
	      params[key.name] = val;
	    }
	  }

	  return true
	}

	function resolveRecordPath (path, record) {
	  return resolvePath(path, record.parent ? record.parent.path : '/', true)
	}

	/*  */


	var positionStore = Object.create(null);

	function setupScroll () {
	  window.addEventListener('popstate', function (e) {
	    saveScrollPosition();
	    if (e.state && e.state.key) {
	      setStateKey(e.state.key);
	    }
	  });
	}

	function handleScroll (
	  router,
	  to,
	  from,
	  isPop
	) {
	  if (!router.app) {
	    return
	  }

	  var behavior = router.options.scrollBehavior;
	  if (!behavior) {
	    return
	  }

	  if (process.env.NODE_ENV !== 'production') {
	    assert(typeof behavior === 'function', "scrollBehavior must be a function");
	  }

	  // wait until re-render finishes before scrolling
	  router.app.$nextTick(function () {
	    var position = getScrollPosition();
	    var shouldScroll = behavior(to, from, isPop ? position : null);
	    if (!shouldScroll) {
	      return
	    }
	    var isObject = typeof shouldScroll === 'object';
	    if (isObject && typeof shouldScroll.selector === 'string') {
	      var el = document.querySelector(shouldScroll.selector);
	      if (el) {
	        position = getElementPosition(el);
	      } else if (isValidPosition(shouldScroll)) {
	        position = normalizePosition(shouldScroll);
	      }
	    } else if (isObject && isValidPosition(shouldScroll)) {
	      position = normalizePosition(shouldScroll);
	    }

	    if (position) {
	      window.scrollTo(position.x, position.y);
	    }
	  });
	}

	function saveScrollPosition () {
	  var key = getStateKey();
	  if (key) {
	    positionStore[key] = {
	      x: window.pageXOffset,
	      y: window.pageYOffset
	    };
	  }
	}

	function getScrollPosition () {
	  var key = getStateKey();
	  if (key) {
	    return positionStore[key]
	  }
	}

	function getElementPosition (el) {
	  var docEl = document.documentElement;
	  var docRect = docEl.getBoundingClientRect();
	  var elRect = el.getBoundingClientRect();
	  return {
	    x: elRect.left - docRect.left,
	    y: elRect.top - docRect.top
	  }
	}

	function isValidPosition (obj) {
	  return isNumber(obj.x) || isNumber(obj.y)
	}

	function normalizePosition (obj) {
	  return {
	    x: isNumber(obj.x) ? obj.x : window.pageXOffset,
	    y: isNumber(obj.y) ? obj.y : window.pageYOffset
	  }
	}

	function isNumber (v) {
	  return typeof v === 'number'
	}

	/*  */

	var supportsPushState = inBrowser && (function () {
	  var ua = window.navigator.userAgent;

	  if (
	    (ua.indexOf('Android 2.') !== -1 || ua.indexOf('Android 4.0') !== -1) &&
	    ua.indexOf('Mobile Safari') !== -1 &&
	    ua.indexOf('Chrome') === -1 &&
	    ua.indexOf('Windows Phone') === -1
	  ) {
	    return false
	  }

	  return window.history && 'pushState' in window.history
	})();

	// use User Timing api (if present) for more accurate key precision
	var Time = inBrowser && window.performance && window.performance.now
	  ? window.performance
	  : Date;

	var _key = genKey();

	function genKey () {
	  return Time.now().toFixed(3)
	}

	function getStateKey () {
	  return _key
	}

	function setStateKey (key) {
	  _key = key;
	}

	function pushState (url, replace) {
	  saveScrollPosition();
	  // try...catch the pushState call to get around Safari
	  // DOM Exception 18 where it limits to 100 pushState calls
	  var history = window.history;
	  try {
	    if (replace) {
	      history.replaceState({ key: _key }, '', url);
	    } else {
	      _key = genKey();
	      history.pushState({ key: _key }, '', url);
	    }
	  } catch (e) {
	    window.location[replace ? 'replace' : 'assign'](url);
	  }
	}

	function replaceState (url) {
	  pushState(url, true);
	}

	/*  */

	function runQueue (queue, fn, cb) {
	  var step = function (index) {
	    if (index >= queue.length) {
	      cb();
	    } else {
	      if (queue[index]) {
	        fn(queue[index], function () {
	          step(index + 1);
	        });
	      } else {
	        step(index + 1);
	      }
	    }
	  };
	  step(0);
	}

	/*  */

	var History = function History (router, base) {
	  this.router = router;
	  this.base = normalizeBase(base);
	  // start with a route object that stands for "nowhere"
	  this.current = START;
	  this.pending = null;
	  this.ready = false;
	  this.readyCbs = [];
	  this.readyErrorCbs = [];
	  this.errorCbs = [];
	};

	History.prototype.listen = function listen (cb) {
	  this.cb = cb;
	};

	History.prototype.onReady = function onReady (cb, errorCb) {
	  if (this.ready) {
	    cb();
	  } else {
	    this.readyCbs.push(cb);
	    if (errorCb) {
	      this.readyErrorCbs.push(errorCb);
	    }
	  }
	};

	History.prototype.onError = function onError (errorCb) {
	  this.errorCbs.push(errorCb);
	};

	History.prototype.transitionTo = function transitionTo (location, onComplete, onAbort) {
	    var this$1 = this;

	  var route = this.router.match(location, this.current);
	  this.confirmTransition(route, function () {
	    this$1.updateRoute(route);
	    onComplete && onComplete(route);
	    this$1.ensureURL();

	    // fire ready cbs once
	    if (!this$1.ready) {
	      this$1.ready = true;
	      this$1.readyCbs.forEach(function (cb) { cb(route); });
	    }
	  }, function (err) {
	    if (onAbort) {
	      onAbort(err);
	    }
	    if (err && !this$1.ready) {
	      this$1.ready = true;
	      this$1.readyErrorCbs.forEach(function (cb) { cb(err); });
	    }
	  });
	};

	History.prototype.confirmTransition = function confirmTransition (route, onComplete, onAbort) {
	    var this$1 = this;

	  var current = this.current;
	  var abort = function (err) {
	    if (isError(err)) {
	      if (this$1.errorCbs.length) {
	        this$1.errorCbs.forEach(function (cb) { cb(err); });
	      } else {
	        warn(false, 'uncaught error during route navigation:');
	        console.error(err);
	      }
	    }
	    onAbort && onAbort(err);
	  };
	  if (
	    isSameRoute(route, current) &&
	    // in the case the route map has been dynamically appended to
	    route.matched.length === current.matched.length
	  ) {
	    this.ensureURL();
	    return abort()
	  }

	  var ref = resolveQueue(this.current.matched, route.matched);
	    var updated = ref.updated;
	    var deactivated = ref.deactivated;
	    var activated = ref.activated;

	  var queue = [].concat(
	    // in-component leave guards
	    extractLeaveGuards(deactivated),
	    // global before hooks
	    this.router.beforeHooks,
	    // in-component update hooks
	    extractUpdateHooks(updated),
	    // in-config enter guards
	    activated.map(function (m) { return m.beforeEnter; }),
	    // async components
	    resolveAsyncComponents(activated)
	  );

	  this.pending = route;
	  var iterator = function (hook, next) {
	    if (this$1.pending !== route) {
	      return abort()
	    }
	    try {
	      hook(route, current, function (to) {
	        if (to === false || isError(to)) {
	          // next(false) -> abort navigation, ensure current URL
	          this$1.ensureURL(true);
	          abort(to);
	        } else if (
	          typeof to === 'string' ||
	          (typeof to === 'object' && (
	            typeof to.path === 'string' ||
	            typeof to.name === 'string'
	          ))
	        ) {
	          // next('/') or next({ path: '/' }) -> redirect
	          abort();
	          if (typeof to === 'object' && to.replace) {
	            this$1.replace(to);
	          } else {
	            this$1.push(to);
	          }
	        } else {
	          // confirm transition and pass on the value
	          next(to);
	        }
	      });
	    } catch (e) {
	      abort(e);
	    }
	  };

	  runQueue(queue, iterator, function () {
	    var postEnterCbs = [];
	    var isValid = function () { return this$1.current === route; };
	    // wait until async components are resolved before
	    // extracting in-component enter guards
	    var enterGuards = extractEnterGuards(activated, postEnterCbs, isValid);
	    var queue = enterGuards.concat(this$1.router.resolveHooks);
	    runQueue(queue, iterator, function () {
	      if (this$1.pending !== route) {
	        return abort()
	      }
	      this$1.pending = null;
	      onComplete(route);
	      if (this$1.router.app) {
	        this$1.router.app.$nextTick(function () {
	          postEnterCbs.forEach(function (cb) { cb(); });
	        });
	      }
	    });
	  });
	};

	History.prototype.updateRoute = function updateRoute (route) {
	  var prev = this.current;
	  this.current = route;
	  this.cb && this.cb(route);
	  this.router.afterHooks.forEach(function (hook) {
	    hook && hook(route, prev);
	  });
	};

	function normalizeBase (base) {
	  if (!base) {
	    if (inBrowser) {
	      // respect <base> tag
	      var baseEl = document.querySelector('base');
	      base = (baseEl && baseEl.getAttribute('href')) || '/';
	    } else {
	      base = '/';
	    }
	  }
	  // make sure there's the starting slash
	  if (base.charAt(0) !== '/') {
	    base = '/' + base;
	  }
	  // remove trailing slash
	  return base.replace(/\/$/, '')
	}

	function resolveQueue (
	  current,
	  next
	) {
	  var i;
	  var max = Math.max(current.length, next.length);
	  for (i = 0; i < max; i++) {
	    if (current[i] !== next[i]) {
	      break
	    }
	  }
	  return {
	    updated: next.slice(0, i),
	    activated: next.slice(i),
	    deactivated: current.slice(i)
	  }
	}

	function extractGuards (
	  records,
	  name,
	  bind,
	  reverse
	) {
	  var guards = flatMapComponents(records, function (def, instance, match, key) {
	    var guard = extractGuard(def, name);
	    if (guard) {
	      return Array.isArray(guard)
	        ? guard.map(function (guard) { return bind(guard, instance, match, key); })
	        : bind(guard, instance, match, key)
	    }
	  });
	  return flatten(reverse ? guards.reverse() : guards)
	}

	function extractGuard (
	  def,
	  key
	) {
	  if (typeof def !== 'function') {
	    // extend now so that global mixins are applied.
	    def = _Vue.extend(def);
	  }
	  return def.options[key]
	}

	function extractLeaveGuards (deactivated) {
	  return extractGuards(deactivated, 'beforeRouteLeave', bindGuard, true)
	}

	function extractUpdateHooks (updated) {
	  return extractGuards(updated, 'beforeRouteUpdate', bindGuard)
	}

	function bindGuard (guard, instance) {
	  if (instance) {
	    return function boundRouteGuard () {
	      return guard.apply(instance, arguments)
	    }
	  }
	}

	function extractEnterGuards (
	  activated,
	  cbs,
	  isValid
	) {
	  return extractGuards(activated, 'beforeRouteEnter', function (guard, _, match, key) {
	    return bindEnterGuard(guard, match, key, cbs, isValid)
	  })
	}

	function bindEnterGuard (
	  guard,
	  match,
	  key,
	  cbs,
	  isValid
	) {
	  return function routeEnterGuard (to, from, next) {
	    return guard(to, from, function (cb) {
	      next(cb);
	      if (typeof cb === 'function') {
	        cbs.push(function () {
	          // #750
	          // if a router-view is wrapped with an out-in transition,
	          // the instance may not have been registered at this time.
	          // we will need to poll for registration until current route
	          // is no longer valid.
	          poll(cb, match.instances, key, isValid);
	        });
	      }
	    })
	  }
	}

	function poll (
	  cb, // somehow flow cannot infer this is a function
	  instances,
	  key,
	  isValid
	) {
	  if (instances[key]) {
	    cb(instances[key]);
	  } else if (isValid()) {
	    setTimeout(function () {
	      poll(cb, instances, key, isValid);
	    }, 16);
	  }
	}

	function resolveAsyncComponents (matched) {
	  return function (to, from, next) {
	    var hasAsync = false;
	    var pending = 0;
	    var error = null;

	    flatMapComponents(matched, function (def, _, match, key) {
	      // if it's a function and doesn't have cid attached,
	      // assume it's an async component resolve function.
	      // we are not using Vue's default async resolving mechanism because
	      // we want to halt the navigation until the incoming component has been
	      // resolved.
	      if (typeof def === 'function' && def.cid === undefined) {
	        hasAsync = true;
	        pending++;

	        var resolve = once(function (resolvedDef) {
	          // save resolved on async factory in case it's used elsewhere
	          def.resolved = typeof resolvedDef === 'function'
	            ? resolvedDef
	            : _Vue.extend(resolvedDef);
	          match.components[key] = resolvedDef;
	          pending--;
	          if (pending <= 0) {
	            next();
	          }
	        });

	        var reject = once(function (reason) {
	          var msg = "Failed to resolve async component " + key + ": " + reason;
	          process.env.NODE_ENV !== 'production' && warn(false, msg);
	          if (!error) {
	            error = isError(reason)
	              ? reason
	              : new Error(msg);
	            next(error);
	          }
	        });

	        var res;
	        try {
	          res = def(resolve, reject);
	        } catch (e) {
	          reject(e);
	        }
	        if (res) {
	          if (typeof res.then === 'function') {
	            res.then(resolve, reject);
	          } else {
	            // new syntax in Vue 2.3
	            var comp = res.component;
	            if (comp && typeof comp.then === 'function') {
	              comp.then(resolve, reject);
	            }
	          }
	        }
	      }
	    });

	    if (!hasAsync) { next(); }
	  }
	}

	function flatMapComponents (
	  matched,
	  fn
	) {
	  return flatten(matched.map(function (m) {
	    return Object.keys(m.components).map(function (key) { return fn(
	      m.components[key],
	      m.instances[key],
	      m, key
	    ); })
	  }))
	}

	function flatten (arr) {
	  return Array.prototype.concat.apply([], arr)
	}

	// in Webpack 2, require.ensure now also returns a Promise
	// so the resolve/reject functions may get called an extra time
	// if the user uses an arrow function shorthand that happens to
	// return that Promise.
	function once (fn) {
	  var called = false;
	  return function () {
	    if (called) { return }
	    called = true;
	    return fn.apply(this, arguments)
	  }
	}

	function isError (err) {
	  return Object.prototype.toString.call(err).indexOf('Error') > -1
	}

	/*  */


	var HTML5History = (function (History$$1) {
	  function HTML5History (router, base) {
	    var this$1 = this;

	    History$$1.call(this, router, base);

	    var expectScroll = router.options.scrollBehavior;

	    if (expectScroll) {
	      setupScroll();
	    }

	    window.addEventListener('popstate', function (e) {
	      this$1.transitionTo(getLocation(this$1.base), function (route) {
	        if (expectScroll) {
	          handleScroll(router, route, this$1.current, true);
	        }
	      });
	    });
	  }

	  if ( History$$1 ) HTML5History.__proto__ = History$$1;
	  HTML5History.prototype = Object.create( History$$1 && History$$1.prototype );
	  HTML5History.prototype.constructor = HTML5History;

	  HTML5History.prototype.go = function go (n) {
	    window.history.go(n);
	  };

	  HTML5History.prototype.push = function push (location, onComplete, onAbort) {
	    var this$1 = this;

	    var ref = this;
	    var fromRoute = ref.current;
	    this.transitionTo(location, function (route) {
	      pushState(cleanPath(this$1.base + route.fullPath));
	      handleScroll(this$1.router, route, fromRoute, false);
	      onComplete && onComplete(route);
	    }, onAbort);
	  };

	  HTML5History.prototype.replace = function replace (location, onComplete, onAbort) {
	    var this$1 = this;

	    var ref = this;
	    var fromRoute = ref.current;
	    this.transitionTo(location, function (route) {
	      replaceState(cleanPath(this$1.base + route.fullPath));
	      handleScroll(this$1.router, route, fromRoute, false);
	      onComplete && onComplete(route);
	    }, onAbort);
	  };

	  HTML5History.prototype.ensureURL = function ensureURL (push) {
	    if (getLocation(this.base) !== this.current.fullPath) {
	      var current = cleanPath(this.base + this.current.fullPath);
	      push ? pushState(current) : replaceState(current);
	    }
	  };

	  HTML5History.prototype.getCurrentLocation = function getCurrentLocation () {
	    return getLocation(this.base)
	  };

	  return HTML5History;
	}(History));

	function getLocation (base) {
	  var path = window.location.pathname;
	  if (base && path.indexOf(base) === 0) {
	    path = path.slice(base.length);
	  }
	  return (path || '/') + window.location.search + window.location.hash
	}

	/*  */


	var HashHistory = (function (History$$1) {
	  function HashHistory (router, base, fallback) {
	    History$$1.call(this, router, base);
	    // check history fallback deeplinking
	    if (fallback && checkFallback(this.base)) {
	      return
	    }
	    ensureSlash();
	  }

	  if ( History$$1 ) HashHistory.__proto__ = History$$1;
	  HashHistory.prototype = Object.create( History$$1 && History$$1.prototype );
	  HashHistory.prototype.constructor = HashHistory;

	  // this is delayed until the app mounts
	  // to avoid the hashchange listener being fired too early
	  HashHistory.prototype.setupListeners = function setupListeners () {
	    var this$1 = this;

	    window.addEventListener('hashchange', function () {
	      if (!ensureSlash()) {
	        return
	      }
	      this$1.transitionTo(getHash(), function (route) {
	        replaceHash(route.fullPath);
	      });
	    });
	  };

	  HashHistory.prototype.push = function push (location, onComplete, onAbort) {
	    this.transitionTo(location, function (route) {
	      pushHash(route.fullPath);
	      onComplete && onComplete(route);
	    }, onAbort);
	  };

	  HashHistory.prototype.replace = function replace (location, onComplete, onAbort) {
	    this.transitionTo(location, function (route) {
	      replaceHash(route.fullPath);
	      onComplete && onComplete(route);
	    }, onAbort);
	  };

	  HashHistory.prototype.go = function go (n) {
	    window.history.go(n);
	  };

	  HashHistory.prototype.ensureURL = function ensureURL (push) {
	    var current = this.current.fullPath;
	    if (getHash() !== current) {
	      push ? pushHash(current) : replaceHash(current);
	    }
	  };

	  HashHistory.prototype.getCurrentLocation = function getCurrentLocation () {
	    return getHash()
	  };

	  return HashHistory;
	}(History));

	function checkFallback (base) {
	  var location = getLocation(base);
	  if (!/^\/#/.test(location)) {
	    window.location.replace(
	      cleanPath(base + '/#' + location)
	    );
	    return true
	  }
	}

	function ensureSlash () {
	  var path = getHash();
	  if (path.charAt(0) === '/') {
	    return true
	  }
	  replaceHash('/' + path);
	  return false
	}

	function getHash () {
	  // We can't use window.location.hash here because it's not
	  // consistent across browsers - Firefox will pre-decode it!
	  var href = window.location.href;
	  var index = href.indexOf('#');
	  return index === -1 ? '' : href.slice(index + 1)
	}

	function pushHash (path) {
	  window.location.hash = path;
	}

	function replaceHash (path) {
	  var i = window.location.href.indexOf('#');
	  window.location.replace(
	    window.location.href.slice(0, i >= 0 ? i : 0) + '#' + path
	  );
	}

	/*  */


	var AbstractHistory = (function (History$$1) {
	  function AbstractHistory (router, base) {
	    History$$1.call(this, router, base);
	    this.stack = [];
	    this.index = -1;
	  }

	  if ( History$$1 ) AbstractHistory.__proto__ = History$$1;
	  AbstractHistory.prototype = Object.create( History$$1 && History$$1.prototype );
	  AbstractHistory.prototype.constructor = AbstractHistory;

	  AbstractHistory.prototype.push = function push (location, onComplete, onAbort) {
	    var this$1 = this;

	    this.transitionTo(location, function (route) {
	      this$1.stack = this$1.stack.slice(0, this$1.index + 1).concat(route);
	      this$1.index++;
	      onComplete && onComplete(route);
	    }, onAbort);
	  };

	  AbstractHistory.prototype.replace = function replace (location, onComplete, onAbort) {
	    var this$1 = this;

	    this.transitionTo(location, function (route) {
	      this$1.stack = this$1.stack.slice(0, this$1.index).concat(route);
	      onComplete && onComplete(route);
	    }, onAbort);
	  };

	  AbstractHistory.prototype.go = function go (n) {
	    var this$1 = this;

	    var targetIndex = this.index + n;
	    if (targetIndex < 0 || targetIndex >= this.stack.length) {
	      return
	    }
	    var route = this.stack[targetIndex];
	    this.confirmTransition(route, function () {
	      this$1.index = targetIndex;
	      this$1.updateRoute(route);
	    });
	  };

	  AbstractHistory.prototype.getCurrentLocation = function getCurrentLocation () {
	    var current = this.stack[this.stack.length - 1];
	    return current ? current.fullPath : '/'
	  };

	  AbstractHistory.prototype.ensureURL = function ensureURL () {
	    // noop
	  };

	  return AbstractHistory;
	}(History));

	/*  */

	var VueRouter = function VueRouter (options) {
	  if ( options === void 0 ) options = {};

	  this.app = null;
	  this.apps = [];
	  this.options = options;
	  this.beforeHooks = [];
	  this.resolveHooks = [];
	  this.afterHooks = [];
	  this.matcher = createMatcher(options.routes || [], this);

	  var mode = options.mode || 'hash';
	  this.fallback = mode === 'history' && !supportsPushState;
	  if (this.fallback) {
	    mode = 'hash';
	  }
	  if (!inBrowser) {
	    mode = 'abstract';
	  }
	  this.mode = mode;

	  switch (mode) {
	    case 'history':
	      this.history = new HTML5History(this, options.base);
	      break
	    case 'hash':
	      this.history = new HashHistory(this, options.base, this.fallback);
	      break
	    case 'abstract':
	      this.history = new AbstractHistory(this, options.base);
	      break
	    default:
	      if (process.env.NODE_ENV !== 'production') {
	        assert(false, ("invalid mode: " + mode));
	      }
	  }
	};

	var prototypeAccessors = { currentRoute: {} };

	VueRouter.prototype.match = function match (
	  raw,
	  current,
	  redirectedFrom
	) {
	  return this.matcher.match(raw, current, redirectedFrom)
	};

	prototypeAccessors.currentRoute.get = function () {
	  return this.history && this.history.current
	};

	VueRouter.prototype.init = function init (app /* Vue component instance */) {
	    var this$1 = this;

	  process.env.NODE_ENV !== 'production' && assert(
	    install.installed,
	    "not installed. Make sure to call `Vue.use(VueRouter)` " +
	    "before creating root instance."
	  );

	  this.apps.push(app);

	  // main app already initialized.
	  if (this.app) {
	    return
	  }

	  this.app = app;

	  var history = this.history;

	  if (history instanceof HTML5History) {
	    history.transitionTo(history.getCurrentLocation());
	  } else if (history instanceof HashHistory) {
	    var setupHashListener = function () {
	      history.setupListeners();
	    };
	    history.transitionTo(
	      history.getCurrentLocation(),
	      setupHashListener,
	      setupHashListener
	    );
	  }

	  history.listen(function (route) {
	    this$1.apps.forEach(function (app) {
	      app._route = route;
	    });
	  });
	};

	VueRouter.prototype.beforeEach = function beforeEach (fn) {
	  return registerHook(this.beforeHooks, fn)
	};

	VueRouter.prototype.beforeResolve = function beforeResolve (fn) {
	  return registerHook(this.resolveHooks, fn)
	};

	VueRouter.prototype.afterEach = function afterEach (fn) {
	  return registerHook(this.afterHooks, fn)
	};

	VueRouter.prototype.onReady = function onReady (cb, errorCb) {
	  this.history.onReady(cb, errorCb);
	};

	VueRouter.prototype.onError = function onError (errorCb) {
	  this.history.onError(errorCb);
	};

	VueRouter.prototype.push = function push (location, onComplete, onAbort) {
	  this.history.push(location, onComplete, onAbort);
	};

	VueRouter.prototype.replace = function replace (location, onComplete, onAbort) {
	  this.history.replace(location, onComplete, onAbort);
	};

	VueRouter.prototype.go = function go (n) {
	  this.history.go(n);
	};

	VueRouter.prototype.back = function back () {
	  this.go(-1);
	};

	VueRouter.prototype.forward = function forward () {
	  this.go(1);
	};

	VueRouter.prototype.getMatchedComponents = function getMatchedComponents (to) {
	  var route = to
	    ? to.matched
	      ? to
	      : this.resolve(to).route
	    : this.currentRoute;
	  if (!route) {
	    return []
	  }
	  return [].concat.apply([], route.matched.map(function (m) {
	    return Object.keys(m.components).map(function (key) {
	      return m.components[key]
	    })
	  }))
	};

	VueRouter.prototype.resolve = function resolve (
	  to,
	  current,
	  append
	) {
	  var location = normalizeLocation(
	    to,
	    current || this.history.current,
	    append,
	    this
	  );
	  var route = this.match(location, current);
	  var fullPath = route.redirectedFrom || route.fullPath;
	  var base = this.history.base;
	  var href = createHref(base, fullPath, this.mode);
	  return {
	    location: location,
	    route: route,
	    href: href,
	    // for backwards compat
	    normalizedTo: location,
	    resolved: route
	  }
	};

	VueRouter.prototype.addRoutes = function addRoutes (routes) {
	  this.matcher.addRoutes(routes);
	  if (this.history.current !== START) {
	    this.history.transitionTo(this.history.getCurrentLocation());
	  }
	};

	Object.defineProperties( VueRouter.prototype, prototypeAccessors );

	function registerHook (list, fn) {
	  list.push(fn);
	  return function () {
	    var i = list.indexOf(fn);
	    if (i > -1) { list.splice(i, 1); }
	  }
	}

	function createHref (base, fullPath, mode) {
	  var path = mode === 'hash' ? '#' + fullPath : fullPath;
	  return base ? cleanPath(base + '/' + path) : path
	}

	VueRouter.install = install;
	VueRouter.version = '2.5.3';

	if (inBrowser && window.Vue) {
	  window.Vue.use(VueRouter);
	}

	module.exports = VueRouter;

	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(45)))

/***/ }),
/* 45 */
/***/ (function(module, exports) {

	// shim for using process in browser
	var process = module.exports = {};

	// cached from whatever global is present so that test runners that stub it
	// don't break things.  But we need to wrap it in a try catch in case it is
	// wrapped in strict mode code which doesn't define any globals.  It's inside a
	// function because try/catches deoptimize in certain engines.

	var cachedSetTimeout;
	var cachedClearTimeout;

	function defaultSetTimout() {
	    throw new Error('setTimeout has not been defined');
	}
	function defaultClearTimeout () {
	    throw new Error('clearTimeout has not been defined');
	}
	(function () {
	    try {
	        if (typeof setTimeout === 'function') {
	            cachedSetTimeout = setTimeout;
	        } else {
	            cachedSetTimeout = defaultSetTimout;
	        }
	    } catch (e) {
	        cachedSetTimeout = defaultSetTimout;
	    }
	    try {
	        if (typeof clearTimeout === 'function') {
	            cachedClearTimeout = clearTimeout;
	        } else {
	            cachedClearTimeout = defaultClearTimeout;
	        }
	    } catch (e) {
	        cachedClearTimeout = defaultClearTimeout;
	    }
	} ())
	function runTimeout(fun) {
	    if (cachedSetTimeout === setTimeout) {
	        //normal enviroments in sane situations
	        return setTimeout(fun, 0);
	    }
	    // if setTimeout wasn't available but was latter defined
	    if ((cachedSetTimeout === defaultSetTimout || !cachedSetTimeout) && setTimeout) {
	        cachedSetTimeout = setTimeout;
	        return setTimeout(fun, 0);
	    }
	    try {
	        // when when somebody has screwed with setTimeout but no I.E. maddness
	        return cachedSetTimeout(fun, 0);
	    } catch(e){
	        try {
	            // When we are in I.E. but the script has been evaled so I.E. doesn't trust the global object when called normally
	            return cachedSetTimeout.call(null, fun, 0);
	        } catch(e){
	            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error
	            return cachedSetTimeout.call(this, fun, 0);
	        }
	    }


	}
	function runClearTimeout(marker) {
	    if (cachedClearTimeout === clearTimeout) {
	        //normal enviroments in sane situations
	        return clearTimeout(marker);
	    }
	    // if clearTimeout wasn't available but was latter defined
	    if ((cachedClearTimeout === defaultClearTimeout || !cachedClearTimeout) && clearTimeout) {
	        cachedClearTimeout = clearTimeout;
	        return clearTimeout(marker);
	    }
	    try {
	        // when when somebody has screwed with setTimeout but no I.E. maddness
	        return cachedClearTimeout(marker);
	    } catch (e){
	        try {
	            // When we are in I.E. but the script has been evaled so I.E. doesn't  trust the global object when called normally
	            return cachedClearTimeout.call(null, marker);
	        } catch (e){
	            // same as above but when it's a version of I.E. that must have the global object for 'this', hopfully our context correct otherwise it will throw a global error.
	            // Some versions of I.E. have different rules for clearTimeout vs setTimeout
	            return cachedClearTimeout.call(this, marker);
	        }
	    }



	}
	var queue = [];
	var draining = false;
	var currentQueue;
	var queueIndex = -1;

	function cleanUpNextTick() {
	    if (!draining || !currentQueue) {
	        return;
	    }
	    draining = false;
	    if (currentQueue.length) {
	        queue = currentQueue.concat(queue);
	    } else {
	        queueIndex = -1;
	    }
	    if (queue.length) {
	        drainQueue();
	    }
	}

	function drainQueue() {
	    if (draining) {
	        return;
	    }
	    var timeout = runTimeout(cleanUpNextTick);
	    draining = true;

	    var len = queue.length;
	    while(len) {
	        currentQueue = queue;
	        queue = [];
	        while (++queueIndex < len) {
	            if (currentQueue) {
	                currentQueue[queueIndex].run();
	            }
	        }
	        queueIndex = -1;
	        len = queue.length;
	    }
	    currentQueue = null;
	    draining = false;
	    runClearTimeout(timeout);
	}

	process.nextTick = function (fun) {
	    var args = new Array(arguments.length - 1);
	    if (arguments.length > 1) {
	        for (var i = 1; i < arguments.length; i++) {
	            args[i - 1] = arguments[i];
	        }
	    }
	    queue.push(new Item(fun, args));
	    if (queue.length === 1 && !draining) {
	        runTimeout(drainQueue);
	    }
	};

	// v8 likes predictible objects
	function Item(fun, array) {
	    this.fun = fun;
	    this.array = array;
	}
	Item.prototype.run = function () {
	    this.fun.apply(null, this.array);
	};
	process.title = 'browser';
	process.browser = true;
	process.env = {};
	process.argv = [];
	process.version = ''; // empty string to avoid regexp issues
	process.versions = {};

	function noop() {}

	process.on = noop;
	process.addListener = noop;
	process.once = noop;
	process.off = noop;
	process.removeListener = noop;
	process.removeAllListeners = noop;
	process.emit = noop;
	process.prependListener = noop;
	process.prependOnceListener = noop;

	process.listeners = function (name) { return [] }

	process.binding = function (name) {
	    throw new Error('process.binding is not supported');
	};

	process.cwd = function () { return '/' };
	process.chdir = function (dir) {
	    throw new Error('process.chdir is not supported');
	};
	process.umask = function() { return 0; };


/***/ }),
/* 46 */,
/* 47 */,
/* 48 */,
/* 49 */,
/* 50 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(51)
	)

	/* script */
	__vue_exports__ = __webpack_require__(52)

	/* template */
	var __vue_template__ = __webpack_require__(57)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/views/errorNetPage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-c7dbf6a6"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 51 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "image": {
	    "paddingBottom": 80
	  },
	  "text": {
	    "paddingBottom": 80,
	    "width": 750,
	    "height": 40,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#7E7E7E",
	    "textAlign": "center"
	  },
	  "btn": {
	    "width": 320,
	    "height": 80,
	    "borderStyle": "solid",
	    "borderColor": "#c2c2c2",
	    "borderRadius": 10,
	    "borderWidth": 1,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "btn-text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#000000",
	    "textAlign": "center"
	  }
	}

/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var globalEvent = weex.requireModule('globalEvent'); //
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event');

	exports.default = {
	    props: {
	        // 监听cell点击事件
	        onClick: {
	            type: Function
	        }
	    },
	    components: { ccImage: _ccImage2.default },
	    data: function data() {
	        return {};
	    },

	    methods: {
	        onClick: function onClick() {
	            // 1.取出请求参数
	            var _$store$state$errNetP = this.$store.state.errNetParam,
	                repo = _$store$state$errNetP.repo,
	                body = _$store$state$errNetP.body,
	                callback = _$store$state$errNetP.callback;
	            // 2.开loading

	            Vue.TipHelper.showHub(0
	            // 3.重新请求
	            );setTimeout(function () {
	                Vue.HttpHelper.post(repo, body, callback, true);
	            }, 500);
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {

	        eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	        //佣金分期 页面 返回，都返回到app
	        globalEvent.addEventListener('WXback', function (e) {
	            Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	        });
	    },
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXback');
	    }
	};

/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(54)
	)

	/* script */
	__vue_exports__ = __webpack_require__(55)

	/* template */
	var __vue_template__ = __webpack_require__(56)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/ccImage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-0ba41ced"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 54 */
/***/ (function(module, exports) {

	module.exports = {}

/***/ }),
/* 55 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    props: {
	        src: {
	            type: String,
	            default: ''
	            //                default: 'http://ok7s5wpmw.bkt.clouddn.com/wx_logo.png'
	        },
	        // 监听cell点击事件
	        onClick: {
	            type: Function
	        },
	        have3xPNG: { // 没有3x图, 不处理
	            type: Boolean,
	            default: true
	        },
	        divWidth: {
	            type: Number
	        },
	        divHeight: {
	            type: Number
	        }
	    },
	    data: function data() {
	        return {
	            imgWidth: 1,
	            imgHeight: 1
	        };
	    },

	    methods: {
	        load: function load(e) {
	            var _$store$state$env = this.$store.state.env,
	                platform = _$store$state$env.platform,
	                scale = _$store$state$env.scale;
	            var _e$size = e.size,
	                naturalWidth = _e$size.naturalWidth,
	                naturalHeight = _e$size.naturalHeight;

	            //                console.log(this.src, e, this.have3xPNG)
	            //                console.log('w: ', naturalWidth)
	            //                console.log('h: ', naturalHeight)

	            // 做适配

	            if (platform == 'iOS' && this.have3xPNG) {
	                this.imgWidth = naturalWidth / scale * 2;
	                this.imgHeight = naturalHeight / scale * 2;
	            } else {
	                this.imgWidth = naturalWidth;
	                this.imgHeight = naturalHeight;
	            }
	        }
	    },
	    created: function created() {},

	    watch: {
	        src: function src(newValue, o) {
	            this.src = newValue;
	        }
	    }
	};

/***/ }),
/* 56 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(_vm.src != '') ? _c('image', {
	    style: {
	      width: _vm.imgWidth,
	      height: _vm.imgHeight
	    },
	    attrs: {
	      "src": _vm.src
	    },
	    on: {
	      "load": _vm.load,
	      "click": _vm.onClick
	    }
	  }) : _vm._e()])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 57 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('ccImage', {
	    staticClass: ["image"],
	    attrs: {
	      "src": 'WXLocal/wx_network_error'
	    }
	  }), _c('text', {
	    staticClass: ["text"]
	  }, [_vm._v("网络好像不太给力")]), _c('div', {
	    staticClass: ["btn"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_c('text', {
	    staticClass: ["btn-text"]
	  }, [_vm._v("点击刷新")])])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 58 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(59)
	)

	/* script */
	__vue_exports__ = __webpack_require__(60)

	/* template */
	var __vue_template__ = __webpack_require__(61)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/views/banner.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-bd8a5c16"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 59 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1
	  },
	  "scroller": {
	    "flex": 1
	  }
	}

/***/ }),
/* 60 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            src: this.$store.state.banner_src,
	            imgWidth: 1,
	            imgHeight: 1
	        };
	    },

	    methods: {
	        load: function load(e) {

	            // 图片加载完成回调
	            var _e$size = e.size,
	                naturalWidth = _e$size.naturalWidth,
	                naturalHeight = _e$size.naturalHeight;

	            this.imgWidth = naturalWidth;
	            this.imgHeight = naturalHeight;

	            console.log(this.src);
	            console.log('w: ', naturalWidth);
	            console.log('h: ', naturalHeight);
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {},
	    destroyed: function destroyed() {}
	};

/***/ }),
/* 61 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroller"]
	  }, [_c('image', {
	    ref: "img",
	    style: {
	      width: _vm.imgWidth,
	      height: _vm.imgHeight
	    },
	    attrs: {
	      "src": _vm.src
	    },
	    on: {
	      "load": _vm.load
	    }
	  })])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(66)
	)

	/* script */
	__vue_exports__ = __webpack_require__(67)

	/* template */
	var __vue_template__ = __webpack_require__(68)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/wl/uiButton.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-56afe322"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 66 */
/***/ (function(module, exports) {

	module.exports = {
	  "active": {
	    "backgroundColor": "#e2262a",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "opacity": 1,
	    "opacity:active": 0.84
	  },
	  "disabled": {
	    "backgroundColor": "#c5c5c5",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "text": {
	    "flex": 1,
	    "backgroundColor": "#FF0000",
	    "color": "#ffffff",
	    "textAlign": "center",
	    "overflow": "hidden"
	  },
	  "text_normal": {
	    "fontSize": 34,
	    "height": 90,
	    "lineHeight": 90,
	    "borderRadius": 8
	  },
	  "text_small": {
	    "fontSize": 30,
	    "height": 68,
	    "lineHeight": 68,
	    "borderRadius": 4
	  }
	}

/***/ }),
/* 67 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	exports.default = {
	    props: {
	        title: {
	            type: String,
	            default: '我是按钮'
	        },
	        size: {
	            type: String,
	            default: 'normal'
	        },
	        onClick: {
	            type: Function
	        },
	        flag: {
	            type: String,
	            default: 'active' // active:可点击状态 / -aliAuth / -mobileAuth  disabled:置灰状态
	        }
	    },
	    components: {},
	    data: function data() {
	        return {
	            styleObject: {
	                opacity: 1
	            }
	        };
	    },
	    created: function created() {
	        if (this.size == 'small') {
	            this.textClass = 'text_small';
	        } else {
	            this.textClass = 'text_normal';
	        }
	    },
	    mounted: function mounted() {},

	    computed: {}
	};

/***/ }),
/* 68 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('text', {
	    staticClass: ["text"],
	    class: [_vm.flag, _vm.textClass],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_vm._v(_vm._s(_vm.title))])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 69 */,
/* 70 */,
/* 71 */,
/* 72 */,
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */,
/* 78 */,
/* 79 */,
/* 80 */,
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */,
/* 93 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(94)
	)

	/* script */
	__vue_exports__ = __webpack_require__(95)

	/* template */
	var __vue_template__ = __webpack_require__(100)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/infoCell.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-4ac2ee0e"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 94 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF"
	  },
	  "content": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "height": 88,
	    "paddingLeft": 25,
	    "paddingRight": 25
	  },
	  "content-left": {
	    "flexDirection": "row",
	    "alignItems": "center"
	  },
	  "content-right": {
	    "flex": 1
	  },
	  "content-right-text": {
	    "flex": 1,
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center"
	  },
	  "content-right-btn": {
	    "flex": 1,
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center"
	  },
	  "content-right-input": {
	    "flex": 1,
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center"
	  },
	  "content-right-inputMoney": {
	    "flex": 1,
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center"
	  },
	  "content-right-checkBox": {
	    "flex": 1,
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center"
	  },
	  "star": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#E2262A",
	    "marginRight": 16
	  },
	  "text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#7E7E7E"
	  },
	  "input": {
	    "flex": 1,
	    "height": 60,
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030",
	    "placeholderColor": "#C5C5C5"
	  },
	  "btn": {
	    "flex": 1,
	    "justifyContent": "center"
	  },
	  "btn-text": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030"
	  },
	  "btn-text-placeholder": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#C5C5C5"
	  },
	  "btn-text-auth": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#22B362"
	  },
	  "btn-text-wait": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#C5C5C5"
	  },
	  "checkBox": {
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center",
	    "marginLeft": 20
	  },
	  "checkBox-img": {
	    "marginRight": 16
	  },
	  "arrow-img": {
	    "marginLeft": 10
	  },
	  "arrow-text": {
	    "marginLeft": 10,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030"
	  }
	}

/***/ }),
/* 95 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccLine = __webpack_require__(96);

	var _ccLine2 = _interopRequireDefault(_ccLine);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: { ccImage: _ccImage2.default, ccLine: _ccLine2.default },
	    props: {
	        // 区分不同的cell
	        types: {
	            required: true,
	            type: String,
	            default: ''
	        },
	        // 区分 手机认证 / 芝麻分认证 / 普通按钮
	        flag: {
	            type: String,
	            default: '' // '' / -aliAuth / -mobileAuth
	        },
	        isShowStar: {
	            type: Boolean,
	            default: true
	        },
	        // 显示主题文本
	        title: {
	            type: String,
	            default: ''
	        },
	        // 显示输入框文本
	        inputValue: {
	            type: String,
	            default: ''
	        },
	        // 显示选项卡文本, 选中后才展示
	        selectValue: {
	            type: String,
	            default: ''
	        },
	        // 显示占位文本
	        placeholder: {
	            type: String,
	            default: ''
	        },
	        // 仅显示姓名与身份证内容
	        textValue: {
	            type: String,
	            default: ''
	        },
	        // 文本颜色
	        textColor: {
	            type: String,
	            default: ''
	        },
	        // 是否显示分割线
	        isLineShow: {
	            type: Boolean,
	            default: true
	        },
	        // 是否显示箭头标识
	        isHintShow: {
	            type: Boolean,
	            default: false
	        },
	        maxLength: {
	            type: Number
	        },
	        // 监听cell点击事件
	        onClick: {
	            type: Function
	        },
	        // 监听输入的文本数据
	        onChange: {
	            type: Function
	        },
	        onDidChange: {
	            type: Function
	        },
	        onFocus: {
	            type: Function
	        },
	        onBlur: {
	            type: Function
	        },
	        // check box
	        isCheckRes: { // '1',  '0'
	            type: String,
	            default: ''
	        },
	        onCheckYes: {
	            type: Function
	        },
	        onCheckNo: {
	            type: Function
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {},
	    mounted: function mounted() {},
	    update: function update() {}
	};

/***/ }),
/* 96 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(97)
	)

	/* script */
	__vue_exports__ = __webpack_require__(98)

	/* template */
	var __vue_template__ = __webpack_require__(99)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/ccLine.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-fab0aa5c"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 97 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#A4A4A4",
	    "height": 0.6,
	    "opacity": 0.8,
	    "marginLeft": 28,
	    "marginRight": 4
	  }
	}

/***/ }),
/* 98 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//

	exports.default = {
	    components: {},
	    props: {
	        // 是否显示分割线
	        isLineShow: {
	            type: Boolean,
	            default: true
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {}
	};

/***/ }),
/* 99 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return (_vm.isLineShow) ? _c('div', {
	    staticClass: ["wrapper"]
	  }) : _vm._e()
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 100 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('ccLine', {
	    attrs: {
	      "isLineShow": _vm.isLineShow
	    }
	  }), _c('div', {
	    staticClass: ["content"]
	  }, [_c('div', {
	    staticClass: ["content-left"]
	  }, [(_vm.isShowStar) ? _c('text', {
	    staticClass: ["star"]
	  }, [_vm._v("*")]) : _vm._e(), _c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.title))])]), _c('div', {
	    staticClass: ["content-right"]
	  }, [(this.types === 'inputText') ? _c('div', {
	    staticClass: ["content-right-input"]
	  }, [_c('input', {
	    staticClass: ["input"],
	    attrs: {
	      "type": "text",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxLength,
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onChange]
	    }
	  })]) : _vm._e(), (this.types === 'inputTel') ? _c('div', {
	    staticClass: ["content-right-input"]
	  }, [_c('input', {
	    staticClass: ["input"],
	    attrs: {
	      "type": "tel",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxLength,
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onChange],
	      "change": _vm.onDidChange,
	      "focus": _vm.onFocus,
	      "blur": _vm.onBlur
	    }
	  })]) : _vm._e(), (this.types === 'inputMoney') ? _c('div', {
	    staticClass: ["content-right-inputMoney"]
	  }, [_c('input', {
	    staticClass: ["input"],
	    attrs: {
	      "type": "tel",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxLength,
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onChange]
	    }
	  }), _c('text', {
	    staticClass: ["arrow-text"]
	  }, [_vm._v("元")]), _c('ccImage', {
	    staticClass: ["arrow-img"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1) : _vm._e(), (this.types === 'text') ? _c('div', {
	    staticClass: ["content-right-text"]
	  }, [_c('text', {
	    staticClass: ["text"],
	    style: {
	      color: _vm.textColor
	    }
	  }, [_vm._v(_vm._s(_vm.textValue))])]) : _vm._e(), (this.types === 'btn') ? _c('div', {
	    staticClass: ["content-right-btn"]
	  }, [_c('div', {
	    staticClass: ["btn"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [(_vm.selectValue != '') ? _c('text', {
	    class: ['btn-text' + _vm.flag]
	  }, [_vm._v(_vm._s(_vm.selectValue))]) : _c('text', {
	    staticClass: ["btn-text-placeholder"]
	  }, [_vm._v(_vm._s(_vm.placeholder))])]), (_vm.flag == '') ? _c('ccImage', {
	    staticClass: ["arrow-img"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  }) : _vm._e()], 1) : _vm._e(), (this.types === 'checkBox') ? _c('div', {
	    staticClass: ["content-right-checkBox"]
	  }, [_c('div', {
	    staticClass: ["checkBox"],
	    on: {
	      "click": _vm.onCheckYes
	    }
	  }, [_c('ccImage', {
	    staticClass: ["checkBox-img"],
	    attrs: {
	      "src": _vm.isCheckRes == '' ? 'WXLocal/wx_select_unpass' : (_vm.isCheckRes == '1' ? 'WXLocal/wx_select_pass' : 'WXLocal/wx_select_unpass')
	    }
	  }), _c('text', {
	    staticClass: ["btn-text"]
	  }, [_vm._v("是")])], 1), _c('div', {
	    staticClass: ["checkBox"],
	    on: {
	      "click": _vm.onCheckNo
	    }
	  }, [_c('ccImage', {
	    staticClass: ["checkBox-img"],
	    attrs: {
	      "src": _vm.isCheckRes == '' ? 'WXLocal/wx_select_unpass' : (_vm.isCheckRes == '0' ? 'WXLocal/wx_select_pass' : 'WXLocal/wx_select_unpass')
	    }
	  }), _c('text', {
	    staticClass: ["btn-text"]
	  }, [_vm._v("否")])], 1)]) : _vm._e()])])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 101 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(102)
	)

	/* script */
	__vue_exports__ = __webpack_require__(103)

	/* template */
	var __vue_template__ = __webpack_require__(104)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/agreeBtn.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-41eaa6ee"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 102 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flexDirection": "row",
	    "alignItems": "center"
	  },
	  "text-black": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "lineHeight": 33,
	    "fontSize": 24,
	    "color": "#303030"
	  },
	  "text-blue": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "lineHeight": 33,
	    "fontSize": 24,
	    "color": "#0E8EE9"
	  },
	  "image": {
	    "marginRight": 16
	  }
	}

/***/ }),
/* 103 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    props: {
	        title: {
	            type: String,
	            default: ''
	        },
	        isShow: {
	            type: Boolean,
	            default: false
	        },
	        isFinish: {
	            type: Boolean,
	            default: false
	        },
	        onClick: {
	            type: Function
	        }
	    },
	    components: { ccImage: _ccImage2.default },
	    data: function data() {
	        return {
	            src: '',
	            styleObject: {
	                opacity: 0
	            }
	        };
	    },

	    methods: {},
	    created: function created() {
	        if (this.isShow) {
	            this.styleObject.opacity = 1;
	        } else {
	            this.styleObject.opacity = 0;
	        }
	        if (this.isFinish) {
	            this.src = 'WXLocal/wx_agreement_pass';
	        } else {
	            this.src = 'WXLocal/wx_agreement_unpass';
	        }
	    },

	    watch: {
	        isShow: function isShow(val, oldVal) {
	            if (this.isShow) {
	                this.styleObject.opacity = 1;
	            } else {
	                this.styleObject.opacity = 0;
	            }
	        },
	        isFinish: function isFinish(val, oldVal) {
	            if (this.isFinish) {
	                this.src = 'WXLocal/wx_agreement_pass';
	            } else {
	                this.src = 'WXLocal/wx_agreement_unpass';
	            }
	        }

	    }
	}; //
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 104 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"],
	    style: _vm.styleObject,
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_c('ccImage', {
	    staticClass: ["image"],
	    attrs: {
	      "src": _vm.src
	    }
	  }), _c('text', {
	    staticClass: ["text-black"]
	  }, [_vm._v("同意并签署")]), _c('text', {
	    staticClass: ["text-blue"]
	  }, [_vm._v("相关合同文件")])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 105 */,
/* 106 */,
/* 107 */,
/* 108 */,
/* 109 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(110)
	)

	/* script */
	__vue_exports__ = __webpack_require__(111)

	/* template */
	var __vue_template__ = __webpack_require__(112)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/ccButton.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-52444ce0"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 110 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "height": 90,
	    "borderRadius": 4,
	    "backgroundColor": "#E2262A",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "text": {
	    "textAlign": "center",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 36,
	    "color": "#FFFFFF"
	  }
	}

/***/ }),
/* 111 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	exports.default = {
	    props: {
	        title: {
	            type: String,
	            default: '我是按钮'
	        },
	        onClick: {
	            type: Function
	        },
	        isClick: { // 是否可点击
	            type: Boolean,
	            default: true
	        }
	    },
	    components: {},
	    data: function data() {
	        return {
	            styleObject: {
	                opacity: 1,
	                backgroundColor: ''
	            }
	        };
	    },
	    created: function created() {
	        if (this.isClick) {
	            //                this.styleObject.opacity = 1
	            this.styleObject.backgroundColor = '#E2262A';
	        } else {
	            //                this.styleObject.opacity = 0.2
	            this.styleObject.backgroundColor = '#C5C5C5';
	        }
	    },
	    mounted: function mounted() {},

	    computed: {},
	    watch: {
	        isClick: function isClick(val, oldVal) {
	            if (this.isClick) {
	                //                this.styleObject.opacity = 1
	                this.styleObject.backgroundColor = '#E2262A';
	            } else {
	                //                this.styleObject.opacity = 0.2
	                this.styleObject.backgroundColor = '#C5C5C5';
	            }
	        }
	    }
	};

/***/ }),
/* 112 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    ref: "btn",
	    staticClass: ["wrapper"],
	    style: _vm.styleObject,
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.title))])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 113 */,
/* 114 */,
/* 115 */,
/* 116 */,
/* 117 */,
/* 118 */,
/* 119 */,
/* 120 */,
/* 121 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(122)
	)

	/* script */
	__vue_exports__ = __webpack_require__(123)

	/* template */
	var __vue_template__ = __webpack_require__(124)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/hint.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2c5400e5"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 122 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "height": 90,
	    "paddingTop": 32,
	    "paddingBottom": 16,
	    "paddingLeft": 21,
	    "paddingRight": 5
	  },
	  "text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 24,
	    "color": "#C5C5C5",
	    "lineHeight": 24
	  }
	}

/***/ }),
/* 123 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: {},
	    props: {
	        title: {
	            type: String,
	            default: ''
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {}
	};

/***/ }),
/* 124 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.title))])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 125 */,
/* 126 */,
/* 127 */,
/* 128 */,
/* 129 */,
/* 130 */,
/* 131 */,
/* 132 */,
/* 133 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(134)
	)

	/* script */
	__vue_exports__ = __webpack_require__(135)

	/* template */
	var __vue_template__ = __webpack_require__(136)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/itView.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-15597ace"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 134 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF",
	    "flex": 1,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "image": {
	    "marginTop": 50,
	    "marginBottom": 10,
	    "width": 110,
	    "height": 110
	  },
	  "text": {
	    "marginTop": 10,
	    "marginBottom": 30
	  }
	}

/***/ }),
/* 135 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        src: {
	            type: String,
	            default: ''
	        },
	        title: {
	            type: String,
	            default: ''
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {}
	}; //
	//
	//
	//
	//
	//
	//

/***/ }),
/* 136 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('ccImage', {
	    staticClass: ["image"],
	    attrs: {
	      "src": _vm.src
	    }
	  }), _c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.title))])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */,
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _vueRouter = __webpack_require__(44);

	var _vueRouter2 = _interopRequireDefault(_vueRouter);

	var _root = __webpack_require__(158);

	var _root2 = _interopRequireDefault(_root);

	var _errorNetPage = __webpack_require__(50);

	var _errorNetPage2 = _interopRequireDefault(_errorNetPage);

	var _webPage = __webpack_require__(162);

	var _webPage2 = _interopRequireDefault(_webPage);

	var _banner = __webpack_require__(58);

	var _banner2 = _interopRequireDefault(_banner);

	var _wallet = __webpack_require__(166);

	var _wallet2 = _interopRequireDefault(_wallet);

	var _identity = __webpack_require__(170);

	var _identity2 = _interopRequireDefault(_identity);

	var _information = __webpack_require__(178);

	var _information2 = _interopRequireDefault(_information);

	var _bankCard = __webpack_require__(182);

	var _bankCard2 = _interopRequireDefault(_bankCard);

	var _authResultPage = __webpack_require__(186);

	var _authResultPage2 = _interopRequireDefault(_authResultPage);

	var _mobileAuthPage = __webpack_require__(198);

	var _mobileAuthPage2 = _interopRequireDefault(_mobileAuthPage);

	var _zmAuthPage = __webpack_require__(206);

	var _zmAuthPage2 = _interopRequireDefault(_zmAuthPage);

	var _bankList = __webpack_require__(210);

	var _bankList2 = _interopRequireDefault(_bankList);

	var _personalInfoProtocal = __webpack_require__(214);

	var _personalInfoProtocal2 = _interopRequireDefault(_personalInfoProtocal);

	var _linkman = __webpack_require__(222);

	var _linkman2 = _interopRequireDefault(_linkman);

	var _withdraw = __webpack_require__(230);

	var _withdraw2 = _interopRequireDefault(_withdraw);

	var _withdrawcontract = __webpack_require__(246);

	var _withdrawcontract2 = _interopRequireDefault(_withdrawcontract);

	var _withdrawState = __webpack_require__(250);

	var _withdrawState2 = _interopRequireDefault(_withdrawState);

	var _bindBank = __webpack_require__(254);

	var _bindBank2 = _interopRequireDefault(_bindBank);

	var _changeBank = __webpack_require__(258);

	var _changeBank2 = _interopRequireDefault(_changeBank);

	var _bankList3 = __webpack_require__(262);

	var _bankList4 = _interopRequireDefault(_bankList3);

	var _bindBankcontract = __webpack_require__(266);

	var _bindBankcontract2 = _interopRequireDefault(_bindBankcontract);

	var _changeBankcontract = __webpack_require__(270);

	var _changeBankcontract2 = _interopRequireDefault(_changeBankcontract);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	Vue.use(_vueRouter2.default); /**
	                               * Created by x298017064010 on 17/6/12.
	                               */

	exports.default = new _vueRouter2.default({
	    // mode: 'abstract',    // 不需要设置模式, 系统自动匹配
	    routes: [{ path: '/root', component: _root2.default }, { path: '/banner', component: _banner2.default }, { path: '/errorNetPage', component: _errorNetPage2.default }, { path: '/webPage', component: _webPage2.default },

	    // 授信
	    { path: '/wallet', component: _wallet2.default }, { path: '/identity', component: _identity2.default }, { path: '/information', component: _information2.default }, { path: '/bankCard', component: _bankCard2.default }, { path: '/authResultPage', component: _authResultPage2.default },

	    // 授信-子页面
	    { path: '/personalInfoProtocal', component: _personalInfoProtocal2.default }, { path: '/bankList', component: _bankList2.default }, { path: '/zmAuthPage', component: _zmAuthPage2.default }, { path: '/mobileAuthPage', component: _mobileAuthPage2.default },

	    // 激活
	    { path: '/linkMan', component: _linkman2.default },

	    //提现
	    { path: '/withdraw', component: _withdraw2.default }, { path: '/withdrawcontract', component: _withdrawcontract2.default }, { path: '/withdrawState', component: _withdrawState2.default }, { path: '/bindBank', component: _bindBank2.default }, { path: '/changeBank', component: _changeBank2.default }, { path: '/bindBankList', component: _bankList4.default }, { path: '/bindBankContract', component: _bindBankcontract2.default }, { path: '/changeBankcontract', component: _changeBankcontract2.default }, { path: '/', redirect: '/root' }]
	});

/***/ }),
/* 158 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(159)
	)

	/* script */
	__vue_exports__ = __webpack_require__(160)

	/* template */
	var __vue_template__ = __webpack_require__(161)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/root.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-09ae991d"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 159 */
/***/ (function(module, exports) {

	module.exports = {}

/***/ }),
/* 160 */
/***/ (function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: {},
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {}
	};

/***/ }),
/* 161 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  })
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 162 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(163)
	)

	/* script */
	__vue_exports__ = __webpack_require__(164)

	/* template */
	var __vue_template__ = __webpack_require__(165)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/views/webPage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-ea10fdec"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 163 */
/***/ (function(module, exports) {

	module.exports = {
	  "webview": {
	    "flex": 1
	  }
	}

/***/ }),
/* 164 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            url: ''
	        };
	    },

	    methods: {},
	    computed: {},
	    created: function created() {},
	    mounted: function mounted() {
	        var _$getConfig$localData = this.$getConfig().localData,
	            url = _$getConfig$localData.url,
	            title = _$getConfig$localData.title; //分期金额

	        this.url = url;

	        console.log('url: ', this.url);

	        eventModule.wxSetNavTitle(title, true);
	    },
	    destroy: function destroy() {}
	};

/***/ }),
/* 165 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('web', {
	    ref: "webview",
	    staticClass: ["webview"],
	    attrs: {
	      "src": _vm.url
	    },
	    on: {
	      "pagestart": _vm.start,
	      "pagefinish": _vm.finish,
	      "error": _vm.error
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 166 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(167)
	)

	/* script */
	__vue_exports__ = __webpack_require__(168)

	/* template */
	var __vue_template__ = __webpack_require__(169)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/wallet/wallet.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-561afaa4"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 167 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "justifyContent": "center",
	    "alignItems": "center"
	  }
	}

/***/ }),
/* 168 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	var animation = weex.requireModule('animation');

	exports.default = {
	    components: {},
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {
	        var that = this;
	        // 获取手机号与token
	        that.$store.dispatch('LOCAL_DATA', this.$getConfig().localData);
	        // 获取主页状态
	        that.$store.dispatch('HOME_PAGE', { callback: function callback() {
	                // 接着获取身份认证数据
	                that.$store.dispatch('IDENTITY_INIT');
	            } });
	    },
	    mounted: function mounted() {}
	};

/***/ }),
/* 169 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  })
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 170 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(171)
	)

	/* script */
	__vue_exports__ = __webpack_require__(172)

	/* template */
	var __vue_template__ = __webpack_require__(177)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/wallet/identity.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-508f5429"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 171 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#eeeeee"
	  },
	  "hint": {
	    "height": 72
	  },
	  "idAuthCard": {
	    "width": 750,
	    "height": 390,
	    "backgroundColor": "#eeeeee",
	    "marginTop": 24
	  },
	  "agree": {
	    "marginTop": 76,
	    "marginLeft": 63
	  },
	  "btn-commit": {
	    "marginTop": 20,
	    "marginLeft": 51,
	    "marginRight": 51,
	    "marginBottom": 32
	  },
	  "scroll": {
	    "flex": 1,
	    "justifyContent": "space-between"
	  }
	}

/***/ }),
/* 172 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _authHeader = __webpack_require__(173);

	var _authHeader2 = _interopRequireDefault(_authHeader);

	var _itView = __webpack_require__(133);

	var _itView2 = _interopRequireDefault(_itView);

	var _hint = __webpack_require__(121);

	var _hint2 = _interopRequireDefault(_hint);

	var _infoCell = __webpack_require__(93);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _agreeBtn = __webpack_require__(101);

	var _agreeBtn2 = _interopRequireDefault(_agreeBtn);

	var _ccButton = __webpack_require__(109);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	var globalEvent = weex.requireModule('globalEvent');

	var curType = '';

	exports.default = {
	    components: { hint: _hint2.default, infoCell: _infoCell2.default, ccButton: _ccButton2.default, agreeBtn: _agreeBtn2.default, authHeader: _authHeader2.default },
	    data: function data() {
	        return {
	            authHeaderItems: [{ img: 'WXLocal/wx_idauth_light', title: '身份认证', class: '' }, { img: 'WXLocal/wx_infoauth_dark', title: '信息认证', class: '-unActive' }, { img: 'WXLocal/wx_bankauth_dark', title: '银行卡认证', class: '-unActive' }],
	            isAgreeFinish: false // 合同签写完成的状态

	            //                isShowAgreeBtn: false,  // 合同按钮显隐开关

	            //                isClick: true,  // 提交按钮可点击状态
	            //                isNext: true,  // 是否可提交
	        };
	    },

	    methods: {
	        checkCommit: function checkCommit() {
	            var isRealNamePass = Vue.CheckHelper.checkRealName(this.$store.state.auth.realName);
	            if (!isRealNamePass) {
	                this.$store.state.auth.realName = '';
	            }

	            if (isRealNamePass) {
	                return true;
	            } else {
	                Vue.TipHelper.showHub('1', '姓名输入有误, 请重新输入!');
	                return false;
	            }
	        },
	        clickCommit: function clickCommit() {
	            var that = this;

	            // 测试
	            console.log('realName', this.$store.state.auth.realName);
	            console.log('identityNo', this.$store.state.auth.identityNo);
	            console.log('identityExpires', this.$store.state.auth.identityExpires);
	            console.log('identityPerson', this.$store.state.auth.identityPerson);
	            console.log('isAgreeFinish', this.isAgreeFinish);

	            // 提示签合同
	            if (!that.isAgreeFinish && that.isShowAgreeBtn) {
	                Vue.TipHelper.showAlert('', '您还未签署相关合同\n请于签署后重新提交认证', '取消', '确定', function (r) {
	                    if (r.result === '确定') {
	                        Vue.NaviHelper.push('weex/common?id=wallet&path=router_personalInfoProtocal', {
	                            title: '个人信息授权书',
	                            realName: that.$store.state.auth.realName,
	                            customerId: that.$store.state.customerId,
	                            token: that.$store.state.token,
	                            mobile: that.$store.state.mobile,
	                            identityNo: that.$store.state.auth.identityNo,
	                            isAgreeFinish: that.isAgreeFinish
	                        }, 'channel_pushAgreePage', function (r) {
	                            console.log('回调写在下面了: ', r);
	                        });
	                    }
	                }, false);
	            }

	            if (this.isClick) {
	                // 提交校验
	                if (!this.checkCommit()) {
	                    return;
	                }

	                // 进行下一步, 信息认证
	                Vue.TipHelper.showAlert('温馨提示', '请确认填写信息真实有效，提交之后不可修改，是否确认提交？', '否', '是', function (r) {
	                    if (r.result === '是') {
	                        that.$store.dispatch('IDENTITY_SAVE');
	                    }
	                }, false);
	            }
	        },
	        changeNameInput: function changeNameInput(e) {
	            console.log('inputName:', e.value);
	        },
	        pushAgreePage: function pushAgreePage() {
	            console.log('pushAgreePage:', this.$store.state.mobile);

	            if (!this.isShowAgreeBtn) {
	                return;
	            }

	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_personalInfoProtocal', {
	                title: '个人信息授权书',
	                realName: this.$store.state.auth.realName,
	                customerId: this.$store.state.customerId,
	                token: this.$store.state.token,
	                mobile: this.$store.state.mobile,
	                identityNo: this.$store.state.auth.identityNo,
	                isAgreeFinish: this.isAgreeFinish
	            }, 'channel_pushAgreePage', function (r) {
	                console.log('回调写在下面了: ', r
	                //                    if (r.success == true) {
	                //                        that.isAgreeFinish = true
	                //                    }
	                );
	            });
	        },


	        // ------监听
	        inputName: function inputName(e) {
	            console.log('inputName:', e.value, e.value.charCodeAt());
	            this.$store.state.auth.realName = e.value;
	            this.isAgreeFinish = false;
	        },


	        // ------初始化
	        initWXIdAuthCard: function initWXIdAuthCard() {
	            var that = this;
	            var idAuthCard = this.$refs.idAuthCard;
	            // 第一次进来, 一定隐藏(新需求不选)
	            //                idAuthCard.setHidden(!this.isShowAuthCard)

	            var imgs = ['wx_idauth_bioassay', 'wx_idauth_front', 'wx_idauth_back'];
	            var msgs = ['请完成人脸识别', '请拍摄身份证正面', '请拍摄身份证背面'];
	            var tip = '安家趣花将对您的信息严格保密';
	            for (var i = 0; i < 3; i++) {
	                idAuthCard.setMessage(i, msgs[i], false // 未完成的文案
	                );idAuthCard.setPictureByLocalRes(i, imgs[i]);
	                idAuthCard.setTip(i, tip);
	            }

	            globalEvent.addEventListener('WXAuth', function (r) {
	                // 回收键盘
	                eventModule.recycleKeyBoard
	                // 根据 r.event 区分事件
	                ();if (r.event == 'click') {
	                    if (r.type == 0 && that.$store.state.auth.identityNo == '') {
	                        Vue.TipHelper.showHub(1, '请先拍摄身份证正面照');
	                    } else {
	                        curType = r.type;
	                        idAuthCard.receiverClickEvent(r.type, false);
	                    }
	                } else if (r.event == 'result') {
	                    if (r.success == true) {
	                        if (curType == 1 || curType == 2) {
	                            // 直接上传身份证正反面照片
	                            that.$store.dispatch('IDENTITY_RECOGNIZE', { type: curType, base64Pic: r.base64Pic, callback: function callback(res) {
	                                    if (curType == 1) {
	                                        // 正面, 传身份证号
	                                        Vue.TipHelper.showAlert('请确认身份证号码', res.cardNo, '重新拍摄', '确定', function (r) {
	                                            if (r.result == '确定') {
	                                                // 1.如果前后证件照重新拍摄, 清空其他
	                                                if (that.$store.state.auth.identityNo != '' && that.$store.state.auth.identityExpires != '') {
	                                                    // 重新拍摄
	                                                    for (var i = 0; i < 3; i++) {
	                                                        idAuthCard.setMessage(i, msgs[i], false // 未完成的文案
	                                                        );idAuthCard.setPictureByLocalRes(i, imgs[i]);
	                                                        idAuthCard.setTip(i, tip);
	                                                    }
	                                                    that.$store.state.auth.identityNo = '';
	                                                    that.$store.state.auth.identityExpires = '';
	                                                    that.$store.state.auth.identityPerson = '0';
	                                                }
	                                                // 2.设置新图片
	                                                idAuthCard.setPictureByBase64(curType, that.$store.state.face.base64Pic);
	                                                idAuthCard.setMessage(curType, '身份证号码:' + res.cardNo, true
	                                                // 3.存储身份证号
	                                                );that.$store.state.auth.identityNo = res.cardNo;
	                                            } else {
	                                                idAuthCard.receiverClickEvent(curType, true // 第二个参数, 是否重新拍摄
	                                                );
	                                            }
	                                        }, false);
	                                    } else if (curType == 2) {
	                                        // 北面, 传身份证有效期
	                                        Vue.TipHelper.showAlert('请确认身份证有效期', res.validPeriod, '重新拍摄', '确定', function (r) {
	                                            if (r.result == '确定') {
	                                                // 1.如果前后证件照重新拍摄, 清空其他
	                                                if (that.$store.state.auth.identityNo != '' && that.$store.state.auth.identityExpires != '') {
	                                                    // 重新拍摄
	                                                    for (var i = 0; i < 3; i++) {
	                                                        idAuthCard.setMessage(i, msgs[i], false // 未完成的文案
	                                                        );idAuthCard.setPictureByLocalRes(i, imgs[i]);
	                                                        idAuthCard.setTip(i, tip);
	                                                    }
	                                                    that.$store.state.auth.identityNo = '';
	                                                    that.$store.state.auth.identityExpires = '';
	                                                    that.$store.state.auth.identityPerson = '0';
	                                                }
	                                                // 2.设置新图片
	                                                idAuthCard.setPictureByBase64(curType, that.$store.state.face.base64Pic);
	                                                idAuthCard.setMessage(curType, '身份证有效期:' + res.validPeriod, true
	                                                // 3.存储身份证有效期
	                                                );that.$store.state.auth.identityExpires = res.validPeriod;
	                                            } else {
	                                                idAuthCard.receiverClickEvent(curType, true // 第二个参数, 是否重新拍摄
	                                                );
	                                            }
	                                        }, false);
	                                    }
	                                } });
	                        } else {
	                            // 上传人脸识别照片
	                            that.$store.dispatch('FACEPAIR_RECOGNICE', { type: curType, base64Pic: r.base64Pic, callback: function callback(res) {
	                                    if (res.operationResult == true) {
	                                        Vue.TipHelper.showHub(1, '已通过验证');
	                                        idAuthCard.setPictureByBase64(curType, res.facepairStr // 该base64与拍照的不同, 仅限人脸识别
	                                        );idAuthCard.setMessage(curType, '已完成人脸识别', true);
	                                        that.$store.state.auth.identityPerson = '1';
	                                    } else {
	                                        Vue.TipHelper.showHub(1, res.displayInfo);
	                                        that.$store.state.auth.identityPerson = '0';
	                                    }
	                                } });
	                        }
	                    } else {
	                        // 人脸识别失败

	                    }
	                } else if (r.event == 'signature') {
	                    if (r.success == true) {
	                        console.log('手写签名全局事件回调: ', r
	                        // todo...
	                        );that.isAgreeFinish = true;
	                    }
	                }
	            });
	        },
	        configWXIdAuthCardData: function configWXIdAuthCardData() {
	            var that = this;
	            var idAuthCard = that.$refs.idAuthCard;
	            if (that.$store.state.auth.identityNo != null && that.$store.state.auth.identityNo != '') {
	                // 下载身份证正面照
	                that.$store.dispatch('IDENTITY_DOWNLOAD', { imageType: 'front', callback: function callback(res) {
	                        idAuthCard.setPictureByBase64('1', res.front);
	                        idAuthCard.setMessage('1', '身份证号码:' + that.$store.state.auth.identityNo, true

	                        // 下载身份证背面照
	                        );if (that.$store.state.auth.identityExpires != null && that.$store.state.auth.identityExpires != '') {
	                            that.$store.dispatch('IDENTITY_DOWNLOAD', { imageType: 'back', callback: function callback(res) {
	                                    idAuthCard.setPictureByBase64('2', res.back);
	                                    idAuthCard.setMessage('2', '身份证有效期:' + that.$store.state.auth.identityExpires, true

	                                    // 下载人脸识别照
	                                    );if (that.$store.state.auth.identityPerson == '1') {
	                                        that.$store.dispatch('IDENTITY_DOWNLOAD', { imageType: 'facepair_0', callback: function callback(res) {
	                                                idAuthCard.setPictureByBase64('0', res.facepair_0);
	                                                idAuthCard.setMessage('0', '已完成人脸识别', true);
	                                            } });
	                                    }
	                                } });
	                        }
	                    } });
	            }
	        }
	    },
	    computed: {
	        isClick: function isClick() {
	            if (this.$store.state.auth.realName !== '' && this.isAgreeFinish) {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        //            isShowAuthCard: function () {
	        //                if ( (this.$store.state.auth.realName !== null &&  this.$store.state.auth.realName !== '')
	        //                        && (this.$store.state.auth.educationDegree !== null && this.$store.state.auth.educationDegree !== '')
	        //                        && (this.$store.state.auth.marryStatus !== null && this.$store.state.auth.marryStatus !== '')) {
	        //                    return true;
	        //                } else {
	        //                    return false;
	        //                }
	        //            },
	        isShowAgreeBtn: function isShowAgreeBtn() {
	            if (this.$store.state.auth.realName != '' && this.$store.state.auth.identityNo !== '' && this.$store.state.auth.identityExpires !== '' && this.$store.state.auth.identityPerson == '1') {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        realName: function realName() {
	            return this.$store.state.auth.realName;
	        }
	    },
	    watch: {
	        //            isShowAuthCard: function (val, oldVal) {
	        //                console.log('3333333', val)
	        //
	        //                const idAuthCard = this.$refs.idAuthCard
	        ////                idAuthCard.setHidden(!val)
	        //            },
	    },
	    created: function created() {},
	    mounted: function mounted() {
	        // 初始化卡片控件
	        this.initWXIdAuthCard
	        // 下载卡片控件图片数据
	        ();this.configWXIdAuthCardData();
	    },
	    updated: function updated() {},
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXAuth');
	    }
	};

/***/ }),
/* 173 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(174)
	)

	/* script */
	__vue_exports__ = __webpack_require__(175)

	/* template */
	var __vue_template__ = __webpack_require__(176)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/authHeader.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-7aef4d33"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 174 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "backgroundColor": "#FFFFFF",
	    "width": 750,
	    "height": 234
	  },
	  "line": {
	    "width": 750,
	    "height": 110,
	    "position": "absolute",
	    "top": 40
	  },
	  "item": {
	    "flex": 1,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "image": {
	    "marginTop": 40,
	    "marginBottom": 16
	  },
	  "text": {
	    "marginBottom": 40,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#303030",
	    "lineHeight": 28
	  },
	  "text-unActive": {
	    "marginBottom": 40,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#7E7E7E",
	    "lineHeight": 28
	  }
	}

/***/ }),
/* 175 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        items: {
	            type: Array,
	            default: []
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {},
	    mounted: function mounted() {}
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 176 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('image', {
	    staticClass: ["line"],
	    attrs: {
	      "src": 'WXLocal/wx_auth_line'
	    }
	  }), _c('div', {
	    staticClass: ["item"]
	  }, [_c('ccImage', {
	    staticClass: ["image"],
	    attrs: {
	      "src": _vm.items[0].img
	    }
	  }), _c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.items[0].title))])], 1), _c('div', {
	    staticClass: ["item"]
	  }, [_c('ccImage', {
	    staticClass: ["image"],
	    attrs: {
	      "src": _vm.items[1].img
	    }
	  }), _c('text', {
	    class: ['text' + _vm.items[1].class]
	  }, [_vm._v(_vm._s(_vm.items[1].title))])], 1), _c('div', {
	    staticClass: ["item"]
	  }, [_c('ccImage', {
	    staticClass: ["image"],
	    attrs: {
	      "src": _vm.items[2].img
	    }
	  }), _c('text', {
	    class: ['text' + _vm.items[2].class]
	  }, [_vm._v(_vm._s(_vm.items[2].title))])], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 177 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroll"]
	  }, [_c('div', [_c('authHeader', {
	    staticClass: ["header"],
	    attrs: {
	      "items": _vm.authHeaderItems
	    }
	  }), _c('hint', {
	    staticClass: ["hint"],
	    attrs: {
	      "title": '请填写本人真实信息, 有利于额度申请审批成功。'
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'inputText',
	      "title": '真实姓名',
	      "inputValue": _vm.realName,
	      "maxLength": 16,
	      "placeholder": '请输入真实姓名',
	      "isLineShow": false,
	      "onChange": _vm.inputName
	    }
	  }), _c('WXIdAuthCard', {
	    ref: "idAuthCard",
	    staticClass: ["idAuthCard"]
	  })], 1), _c('div', [(this.$store.state.os == 'iOS') ? _c('div', {
	    style: {
	      height: this.$store.state.env.scale == 3 ? 210 : 180
	    }
	  }) : _vm._e(), _c('agreeBtn', {
	    staticClass: ["agree"],
	    attrs: {
	      "isShow": _vm.isShowAgreeBtn,
	      "isFinish": _vm.isAgreeFinish,
	      "onClick": _vm.pushAgreePage
	    }
	  }), _c('ccButton', {
	    staticClass: ["btn-commit"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.clickCommit,
	      "isClick": _vm.isClick
	    }
	  })], 1)])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 178 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(179)
	)

	/* script */
	__vue_exports__ = __webpack_require__(180)

	/* template */
	var __vue_template__ = __webpack_require__(181)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/wallet/information.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-63b77d11"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 179 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#eeeeee"
	  },
	  "header": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "hint": {
	    "height": 72
	  },
	  "btn-commit": {
	    "marginTop": 20,
	    "marginLeft": 51,
	    "marginRight": 51,
	    "marginBottom": 32
	  },
	  "scroll": {
	    "flex": 1,
	    "justifyContent": "space-between"
	  }
	}

/***/ }),
/* 180 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _authHeader = __webpack_require__(173);

	var _authHeader2 = _interopRequireDefault(_authHeader);

	var _hint = __webpack_require__(121);

	var _hint2 = _interopRequireDefault(_hint);

	var _infoCell = __webpack_require__(93);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _ccButton = __webpack_require__(109);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	var globalEvent = weex.requireModule('globalEvent');

	exports.default = {
	    components: { authHeader: _authHeader2.default, hint: _hint2.default, infoCell: _infoCell2.default, ccButton: _ccButton2.default },
	    data: function data() {
	        return {
	            authHeaderItems: [{ img: 'WXLocal/wx_idauth_light', title: '身份认证', class: '' }, { img: 'WXLocal/wx_infoauth_light', title: '信息认证', class: '' }, { img: 'WXLocal/wx_bankauth_dark', title: '银行卡认证', class: '-unActive' }],
	            // 认证按钮样式标记
	            mobileAuthCSSFlag: '',
	            zmAuthCSSFlag: ''
	        };
	    },

	    methods: {
	        clickCommit: function clickCommit() {
	            console.log('mobileAuthFlag', this.$store.state.auth.mobileAuthFlag);
	            console.log('educationDegree', this.$store.state.auth.educationDegree);
	            console.log('marryStatus', this.$store.state.auth.marryStatus);
	            console.log('profession', this.$store.state.auth.profession);
	            console.log('isZmAuth', this.$store.state.auth.isZmAuth);

	            var that = this;
	            if (this.isClick) {
	                Vue.TipHelper.showAlert('温馨提示', '请确认填写信息真实有效，提交之后不可修改，是否确认提交？', '否', '是', function (r) {
	                    if (r.result === '是') {
	                        that.$store.dispatch('INFOAUTH_SAVE');
	                    }
	                }, false);
	            }
	        },
	        pushZMAuthPage: function pushZMAuthPage() {
	            console.log('pushZMAuthPage, isZmAuth:', this.$store.state.auth.isZmAuth);

	            var that = this;
	            //                if (this.$store.state.debug) {
	            //                    Vue.NaviHelper.push('weex/common?id=wallet&path=common?router_zmAuthPage', {title: '芝麻分认证', authUrl: r.authURL}, 'channel_pushZMAuthPage', function (r) {
	            //                        console.log('字码')
	            //                        if (r.result == true) {
	            //                            that.$store.state.auth.isZmAuth = '1'
	            //                        }
	            //                    });
	            //                }

	            if (this.zmAuthStatus == '') {
	                this.$store.dispatch('INFOAUTH_ZMAUTH', { callback: function callback(r) {
	                        console.log(r);
	                        if (r.isOpenAuth == true) {
	                            Vue.NaviHelper.push('weex/common?id=wallet&path=router_zmAuthPage', { title: '芝麻分认证', authUrl: r.authURL }, 'channel_pushZMAuthPage', function (r) {
	                                if (r.result == true) {
	                                    that.$store.state.auth.isZmAuth = '1';
	                                }
	                            });
	                        } else {
	                            //                            Vue.TipHelper.showHub('1', '芝麻分已认证')
	                            that.$store.state.auth.isZmAuth = '1';
	                        }
	                    } });
	            }
	        },
	        pushMobileAuthPage: function pushMobileAuthPage() {
	            console.log('pushMobileAuthPage, mobileAuthFlag:', this.$store.state.auth.mobileAuthFlag);

	            var that = this;
	            if (this.mobileAuthFlag == '') {
	                // weex页面
	                //                    Vue.NaviHelper.push('weex/common?id=wallet&path=router_mobileAuthPage', {
	                //                        title: '手机认证',
	                //                        mobileAuthFlag: that.$store.state.auth.mobileAuthFlag,
	                //                        realName: that.$store.state.auth.realName,
	                //                        identityNo: that.$store.state.auth.identityNo,
	                //                        mobile: that.$store.state.mobile,
	                //                    }, 'channel_pushMobileAuthPage', function (r) {
	                //                        console.log('weex回调在下面: ', r)
	                //                        if (r.success == true) {
	                //                            console.log('手机认证页返回值是: ', r)
	                //                            that.$store.state.auth.mobileAuthFlag = 'wait'
	                //                        }
	                //                    });

	                // 取值
	                globalEvent.addEventListener('WXAuth', function (r) {
	                    // 根据 r.event 区分事件
	                    if (r.event == 'phoneAuth') {
	                        if (r.success == true) {
	                            console.log('手机认证页返回值是: ', r);
	                            _that.$store.state.auth.mobileAuthFlag = 'wait';
	                        }
	                    }
	                });

	                var _that = this;
	                Vue.NaviHelper.push('native/main/phoneAuth', {
	                    title: '手机认证页面',
	                    mobileAuthFlag: _that.$store.state.auth.mobileAuthFlag,
	                    realName: _that.$store.state.auth.realName,
	                    identityNo: _that.$store.state.auth.identityNo
	                }, 'pushMobileAuthPage', function (r) {
	                    console.log('手机认证页返回值是2222: ', r);
	                });
	            }
	        },
	        openEduCard: function openEduCard(a) {
	            var content = {
	                title: "请选择教育程度",
	                items: ["硕士及以上", "本科", "大专", "高中", "技校", "初中", "小学", "其他"]
	            };

	            var that = this;
	            eventModule.wxSelectListView(content, this.selectEdu, function (e) {
	                console.log('selectEdu:', e);

	                that.selectEdu = e.result;

	                var localSelect = '';
	                if (e.result == "硕士及以上") localSelect = 'ssjys';else if (e.result == "本科") localSelect = 'bk';else if (e.result == "大专") localSelect = 'dz';else if (e.result == "高中") localSelect = 'gz';else if (e.result == "技校") localSelect = 'jx';else if (e.result == "初中") localSelect = 'cz';else if (e.result == "小学") localSelect = 'xx';else if (e.result == "其他") localSelect = 'qt';
	                that.$store.state.auth.educationDegree = localSelect;
	            });
	        },
	        openMarryCard: function openMarryCard() {
	            var content = {
	                title: "请选择婚姻状况",
	                items: ["已婚", "未婚", "离异", "丧偶", "未说明婚姻状况"]
	            };
	            var that = this;
	            eventModule.wxSelectListView(content, this.selectMarry, function (e) {
	                console.log('selectMarry:', e);
	                that.selectMarry = e.result;

	                var localSelect = '';
	                if (e.result == "已婚") localSelect = 'yh';else if (e.result == "未婚") localSelect = 'wh';else if (e.result == "离异") localSelect = 'lh';else if (e.result == "丧偶") localSelect = 'so';else if (e.result == "未说明婚姻状况") localSelect = 'wsmhyzk';
	                that.$store.state.auth.marryStatus = localSelect;
	            });
	        },
	        openJobCard: function openJobCard() {
	            var content = {
	                title: "请选择职业",
	                items: ["企业主", "私营业主", "上班族", "其他"]
	            };
	            var that = this;
	            eventModule.wxSelectListView(content, this.selectJob, function (e) {
	                console.log('selectJob:', e);
	                that.$store.state.auth.profession = e.result;
	            });
	        }
	    },
	    computed: {
	        isClick: function isClick() {
	            if (this.mobileAuthFlag != '' &&
	            //                        && this.$store.state.auth.monthlyIncomeCard !== ''
	            //                        && this.$store.state.auth.monthlyIncomeCash !== ''
	            //                        && this.$store.state.auth.industry !== ''
	            //                        && this.$store.state.auth.payFundOrSecurity !== ''
	            this.$store.state.auth.educationDegree != '' && this.$store.state.auth.marryStatus != '' && this.$store.state.auth.profession != '') {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        mobileAuthFlag: function mobileAuthFlag() {
	            if (this.$store.state.auth.mobileAuthFlag == 'auth') {
	                this.mobileAuthCSSFlag = '-auth';
	                return '已认证';
	            } else if (this.$store.state.auth.mobileAuthFlag == 'wait') {
	                this.mobileAuthCSSFlag = '-wait';
	                return '认证中';
	            } else {
	                this.mobileAuthCSSFlag = '';
	                return '';
	            }
	        },
	        zmAuthStatus: function zmAuthStatus() {
	            if (this.$store.state.auth.isZmAuth == '1') {
	                this.zmAuthCSSFlag = '-auth';
	                return '已认证';
	            } else {
	                this.zmAuthCSSFlag = '';
	                return '';
	            }
	        },
	        selectSocial: function selectSocial() {
	            var localSelect = this.$store.state.auth.payFundOrSecurity;
	            if (localSelect == '1') return '是';else if (localSelect == '0') return '否';
	        },
	        selectEdu: function selectEdu() {
	            var localSelect = this.$store.state.auth.educationDegree;
	            if (localSelect == "ssjys") return '硕士及以上';else if (localSelect == "bk") return '本科';else if (localSelect == "dz") return '大专';else if (localSelect == "gz") return '高中';else if (localSelect == "jx") return '技校';else if (localSelect == "cz") return '初中';else if (localSelect == "xx") return '小学';else if (localSelect == "qt") return '其他';
	        },
	        selectMarry: function selectMarry() {
	            var localSelect = this.$store.state.auth.marryStatus;
	            if (localSelect == "yh") return '已婚';else if (localSelect == "wh") return '未婚';else if (localSelect == "lh") return '离异';else if (localSelect == "so") return '丧偶';else if (localSelect == "wsmhyzk") return '未说明婚姻状况';
	        },
	        selectProfession: function selectProfession() {
	            return this.$store.state.auth.profession;
	        }
	    },
	    created: function created() {
	        this.$store.dispatch('INFOAUTH_INIT');
	    },
	    mounted: function mounted() {},
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXAuth');
	    }
	};

/***/ }),
/* 181 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroll"]
	  }, [_c('div', [_c('authHeader', {
	    staticClass: ["header"],
	    attrs: {
	      "items": _vm.authHeaderItems
	    }
	  }), _c('hint', {
	    staticClass: ["hint"],
	    attrs: {
	      "title": '请填写本人真实信息, 有利于额度申请审批成功。'
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'btn',
	      "title": '教育程度',
	      "placeholder": '请选择',
	      "selectValue": _vm.selectEdu,
	      "onClick": _vm.openEduCard,
	      "isLineShow": false
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'btn',
	      "title": '婚姻状况',
	      "placeholder": '请选择',
	      "selectValue": _vm.selectMarry,
	      "onClick": _vm.openMarryCard
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'btn',
	      "title": '从事职业',
	      "placeholder": '请选择',
	      "selectValue": _vm.selectProfession,
	      "onClick": _vm.openJobCard
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'btn',
	      "title": '手机认证',
	      "flag": _vm.mobileAuthCSSFlag,
	      "placeholder": '认证手机是获取额度的必要条件',
	      "selectValue": _vm.mobileAuthFlag,
	      "onClick": _vm.pushMobileAuthPage
	    }
	  })], 1), _c('div', [(this.$store.state.os == 'iOS') ? _c('div', {
	    style: {
	      height: this.$store.state.env.scale == 3 ? 360 : 330
	    }
	  }) : _vm._e(), _c('ccButton', {
	    staticClass: ["btn-commit"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.clickCommit,
	      "isClick": _vm.isClick
	    }
	  })], 1)])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 182 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(183)
	)

	/* script */
	__vue_exports__ = __webpack_require__(184)

	/* template */
	var __vue_template__ = __webpack_require__(185)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/wallet/bankCard.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-b01f5852"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 183 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#eeeeee"
	  },
	  "header": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "hint": {
	    "height": 72
	  },
	  "changeBankCard": {
	    "width": 300,
	    "paddingTop": 16,
	    "paddingLeft": 24,
	    "paddingBottom": 20
	  },
	  "text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 24,
	    "color": "#108EE9",
	    "lineHeight": 24
	  },
	  "btn-commit": {
	    "marginTop": 20,
	    "marginLeft": 51,
	    "marginRight": 51,
	    "marginBottom": 32
	  },
	  "scroll": {
	    "flex": 1,
	    "justifyContent": "space-between"
	  }
	}

/***/ }),
/* 184 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _authHeader = __webpack_require__(173);

	var _authHeader2 = _interopRequireDefault(_authHeader);

	var _hint = __webpack_require__(121);

	var _hint2 = _interopRequireDefault(_hint);

	var _infoCell = __webpack_require__(93);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _ccButton = __webpack_require__(109);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: { authHeader: _authHeader2.default, hint: _hint2.default, infoCell: _infoCell2.default, ccButton: _ccButton2.default },
	    data: function data() {
	        return {
	            authHeaderItems: [{ img: 'WXLocal/wx_idauth_light', title: '身份认证', class: '' }, { img: 'WXLocal/wx_infoauth_light', title: '信息认证', class: '' }, { img: 'WXLocal/wx_bankauth_light', title: '银行卡认证', class: '' }],
	            isCreditCard: false,
	            realName: '',
	            identityNo: ''
	        };
	    },

	    methods: {
	        checkCommit: function checkCommit() {
	            // 银行预留电话验证
	            var isBankMobilePass = Vue.CheckHelper.checkMobileNo(this.$store.state.auth.bankMobile.replace(/\s/g, ""));
	            if (!isBankMobilePass) {
	                this.$store.state.auth.bankMobile = '';
	            }
	            // 银行卡号验证
	            var isCardNoPass = Vue.CheckHelper.checkCardNo(this.$store.state.auth.cardNo.replace(/\s/g, ""));
	            if (!isCardNoPass) {
	                this.$store.state.auth.cardNo = '';
	            }

	            if (isBankMobilePass && isCardNoPass) {
	                return true;
	            } else {
	                if (!isBankMobilePass && !isCardNoPass) {
	                    Vue.TipHelper.showHub('1', '银行卡卡号、银行预留电话填写有误, 请重新输入!');
	                } else {
	                    if (!isBankMobilePass) {
	                        Vue.TipHelper.showHub('1', '银行预留电话填写有误,\n请重新输入!');
	                    } else if (!isCardNoPass) {
	                        Vue.TipHelper.showHub('1', '银行卡卡号填写有误,\n请重新输入!');
	                    }
	                }
	                return false;
	            }
	        },
	        clickCommit: function clickCommit() {

	            console.log('cardType', this.$store.state.auth.cardType); // 区分引用卡还是储蓄卡
	            console.log('cardBank', this.$store.state.auth.cardBank);
	            console.log('cardNo', this.$store.state.auth.cardNo);
	            console.log('bankCode', this.$store.state.auth.bankCode);
	            console.log('bankMobile', this.$store.state.auth.bankMobile);
	            console.log('bankInfoId', this.$store.state.auth.bankInfoId); // 为空 = 未认证 = 才能正常保存

	            var that = this;
	            if (this.isClick) {
	                // 输入信息校验
	                if (!this.checkCommit()) {
	                    return;
	                }
	                // 进行下一步, 信息认证
	                Vue.TipHelper.showAlert('温馨提示', '请确认填写信息真实有效，提交之后不可修改，是否确认提交？', '否', '是', function (r) {
	                    if (r.result === '是') {
	                        that.$store.dispatch('BANKCARD_SAVE');
	                    }
	                }, false);
	            }
	        },
	        changeBankCard: function changeBankCard() {
	            console.log('changeBankCard:');

	            this.isCreditCard = !this.isCreditCard;
	            if (this.isCreditCard == false) {
	                this.$store.state.auth.cardType = 'DR';
	            } else {
	                this.$store.state.auth.cardType = 'CR';
	            }
	            this.$store.state.auth.cardBank = '';
	            this.$store.state.auth.bankCode = '';
	            this.$store.state.auth.bankMobile = '';
	            this.$store.state.auth.cardNo = '';
	        },
	        pushBankListPage: function pushBankListPage() {
	            console.log('pushBankListPage:');
	            var that = this;

	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_bankList', { title: '银行列表', isCredit: this.isCreditCard }, 'channel_pushBankList', function (r) {
	                console.log('你点击的银行是: ', r.result);

	                var _r$result = r.result,
	                    bankName = _r$result.bankName,
	                    bankCode = _r$result.bankCode;

	                that.$store.state.auth.cardBank = bankName;
	                that.$store.state.auth.bankCode = bankCode;
	            });
	        },
	        inputCreditCardNumber: function inputCreditCardNumber(e) {
	            console.log('inputCreditCardNumber:', e.value);
	            this.$store.state.auth.cardNo = e.value;
	        },
	        inputTelNumber: function inputTelNumber(e) {
	            console.log('inputBankTelNumber:', e.value);
	            this.$store.state.auth.bankMobile = e.value;
	        }
	    },
	    computed: {
	        isClick: function isClick() {
	            if (this.$store.state.auth.cardBank !== '' && this.$store.state.auth.cardNo !== '' && this.$store.state.auth.bankCode !== '' && this.$store.state.auth.bankMobile !== '') {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        cardBank: function cardBank() {
	            return this.$store.state.auth.cardBank;
	        },
	        cardNo: function cardNo() {
	            return this.$store.state.auth.cardNo;
	        },
	        bankMobile: function bankMobile() {
	            return this.$store.state.auth.bankMobile;
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var that = this;
	        that.$store.dispatch('BANKCARD_INIT', { callback: function callback() {
	                that.realName = that.$store.state.auth.realName;
	                that.identityNo = that.$store.state.auth.identityNo;
	                that.$store.state.auth.cardBank = '';
	                that.$store.state.auth.cardNo = '';
	                that.$store.state.auth.bankMobile = '';
	                // 新用户后台没给, 特殊处理
	                if (that.$store.state.auth.cardType == '') {
	                    that.$store.state.auth.cardType = 'DR';
	                    that.isCreditCard = false;
	                }
	            } });
	    }
	};

/***/ }),
/* 185 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroll"]
	  }, [_c('div', [_c('authHeader', {
	    staticClass: ["header"],
	    attrs: {
	      "items": _vm.authHeaderItems
	    }
	  }), _c('hint', {
	    staticClass: ["hint"],
	    attrs: {
	      "title": '请填写本人真实信息, 有利于额度申请审批成功。'
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'text',
	      "title": '身份证号',
	      "isShowStar": false,
	      "textValue": _vm._f("identityFormat")(_vm.identityNo),
	      "isLineShow": false
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'text',
	      "title": '真实姓名',
	      "isShowStar": false,
	      "textValue": _vm._f("realNameFormat")(_vm.realName)
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'btn',
	      "title": '银行名称',
	      "selectValue": _vm.cardBank,
	      "placeholder": '请选择',
	      "onClick": _vm.pushBankListPage
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'inputTel',
	      "title": _vm.isCreditCard ? '信用卡卡号' : '借记卡卡号',
	      "inputValue": _vm._f("cardNoFormat")(_vm.cardNo),
	      "placeholder": _vm.isCreditCard ? '请输入本人信用卡卡号' : '请输入本人借记卡卡号',
	      "maxLength": _vm.isCreditCard ? 16 + 3 : 19 + 4,
	      "onChange": _vm.inputCreditCardNumber
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'inputTel',
	      "title": '手机号码',
	      "inputValue": _vm._f("mobileFormat")(_vm.bankMobile),
	      "placeholder": '请输入银行预留手机号',
	      "maxLength": 13,
	      "onChange": _vm.inputTelNumber
	    }
	  }), (false) ? _c('div', {
	    staticClass: ["changeBankCard"],
	    on: {
	      "click": _vm.changeBankCard
	    }
	  }, [_c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.isCreditCard ? '返回借记卡认证' : '若无借记卡，请点击 '))])]) : _vm._e()], 1), _c('div', [(this.$store.state.os == 'iOS') ? _c('div', {
	    style: {
	      height: this.$store.state.env.scale == 3 ? 280 : 250
	    }
	  }) : _vm._e(), _c('ccButton', {
	    staticClass: ["btn-commit"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.clickCommit,
	      "isClick": _vm.isClick
	    }
	  })], 1)])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 186 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(187)
	)

	/* script */
	__vue_exports__ = __webpack_require__(188)

	/* template */
	var __vue_template__ = __webpack_require__(197)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/wallet/authResultPage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-e9002382"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 187 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#eeeeee"
	  },
	  "scroller": {
	    "flex": 1,
	    "flexDirection": "column",
	    "justifyContent": "space-between"
	  },
	  "header": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "marginBottom": 24
	  },
	  "footer": {
	    "backgroundColor": "#FFFFFF",
	    "flex": 1,
	    "justifyContent": "space-between"
	  },
	  "footer-content": {
	    "marginBottom": 48
	  },
	  "btn-commit": {
	    "marginLeft": 51,
	    "marginRight": 51,
	    "marginBottom": 32
	  },
	  "producList": {
	    "backgroundColor": "#FFFFFF"
	  },
	  "producList-title": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030",
	    "lineHeight": 30,
	    "padding": 30
	  },
	  "content": {
	    "flex": 1
	  }
	}

/***/ }),
/* 188 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _authHeader = __webpack_require__(173);

	var _authHeader2 = _interopRequireDefault(_authHeader);

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccButton = __webpack_require__(109);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	var _ccCells = __webpack_require__(189);

	var _ccCells2 = _interopRequireDefault(_ccCells);

	var _resultContent = __webpack_require__(193);

	var _resultContent2 = _interopRequireDefault(_resultContent);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var globalEvent = weex.requireModule('globalEvent'); //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { authHeader: _authHeader2.default, ccImage: _ccImage2.default, ccButton: _ccButton2.default, ccCells: _ccCells2.default, resultContent: _resultContent2.default },
	    data: function data() {
	        return {
	            authHeaderItems: [{ img: 'WXLocal/wx_idauth_light', title: '身份认证', class: '' }, { img: 'WXLocal/wx_infoauth_light', title: '信息认证', class: '' }, { img: 'WXLocal/wx_bankauth_light', title: '银行卡认证', class: '' }],

	            // 反导流产品列表(废弃)
	            isShowProducList: false,
	            productList: [],
	            logoBaseUrl: Vue.UrlMacro.WEB_URL + 'appweb/fileView/loadImgBase?location='

	        };
	    },

	    methods: {
	        clickReflash: function clickReflash() {
	            console.log('click reflash: ');
	            var that = this;

	            if (that.isClick) {
	                that.$store.state.auth.isRefresh = true;
	                that.$store.dispatch('HOME_PAGE', { callback: function callback() {
	                        that.$store.dispatch('IDENTITY_INIT');
	                    } });
	            }
	        },
	        clickCell: function clickCell(e) {
	            console.log('click cell:', e);
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_webPage', {
	                title: e.productName,
	                url: e.productUrl
	            }, 'channel_webPage', function (r) {
	                // 需要回调?
	            });
	        }
	    },
	    computed: {
	        // 失败, 锁单   03(电审-锁单) / 06(提额-无额度)
	        resultStatus: function resultStatus() {
	            if (this.$store.state.auth.blackLock == '1' && this.$store.state.auth.blackLockDays > 0) {
	                // 锁单状态
	                return '-01';
	            } else {
	                // 正常流程
	                if (this.$store.state.auth.status == '02') {
	                    if (this.$store.state.auth.mobileAuthFlag == 'noauth') {
	                        return '0201'; // 审核失败, 无刷新 0201
	                    } else {
	                            return '0202'; // 等待, 刷新 0202
	                        }
	                } else if (this.$store.state.auth.status == '03') {
	                    return '03'; // 审核被拒, 取lockDays
	                } else if (this.$store.state.auth.status == '05') {
	                    return '05'; // 等待, 无刷新(决策异常) 05
	                } else if (this.$store.state.auth.status == '06') {
	                    if (this.$store.state.auth.page == '301') {
	                        return '06301'; // 电审被拒, 取creditRejRemainDate
	                    } else if (this.$store.state.auth.page == '302' || this.$store.state.auth.page == '303') {
	                        return '06302'; // 获额度成功, 待激活, 刷新返回首页
	                    } else {
	                            return '06'; // 提现与无额度锁单, 取lockDays
	                        }
	                } else {
	                    return '0202';
	                }
	            }
	        },
	        contentType: function contentType() {
	            if (this.resultStatus == '-01') {
	                return 'lock';
	            } else {
	                if (this.resultStatus == '0201' || this.resultStatus == '03' || this.resultStatus == '06' || this.resultStatus == '06301') {
	                    return 'fail';
	                } else {
	                    return 'wait';
	                }
	            }
	        },
	        isShowBtn: function isShowBtn() {
	            if (this.resultStatus == '0201' || this.resultStatus == '05' || this.resultStatus == '-01') {
	                return false;
	            } else {
	                return true;
	            }
	        },
	        btnTitle: function btnTitle() {
	            if (this.resultStatus == '06301') {
	                this.isClick = false;

	                eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	                //佣金分期 页面 返回，都返回到app
	                globalEvent.addEventListener('WXback', function (e) {
	                    Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	                });

	                return '可在' + this.$store.state.auth.creditRejRemainDate + '天后进行再次申请';
	            } else if (this.resultStatus == '03' || this.resultStatus == '06') {
	                this.isClick = false;

	                eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	                //佣金分期 页面 返回，都返回到app
	                globalEvent.addEventListener('WXback', function (e) {
	                    Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	                });

	                return '可在' + this.$store.state.auth.lockDays + '天后进行再次申请';
	            } else {
	                this.isClick = true;
	                return '刷新';
	            }
	        },
	        isClick: function isClick() {
	            if (this.resultStatus == '-01') {
	                eventModule.wxSetNavTitle("认证失败", true);
	                return false; // 被拒,显示反导流
	            } else {
	                if (this.resultStatus == '03' || this.resultStatus == '06') {
	                    eventModule.wxSetNavTitle("额度申请失败", true);
	                    return false; // 被拒,显示反导流
	                } else {
	                    return true;
	                }
	            }
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var that = this;
	        if (that.isClick == false) {
	            that.$store.dispatch('RESULT_LOAD_PRODUCT_LIST', { callback: function callback(res) {
	                    that.productList = res.productList;
	                    console.log('反导流数据: ', that.productList);
	                } });
	        }
	    }
	};

/***/ }),
/* 189 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(190)
	)

	/* script */
	__vue_exports__ = __webpack_require__(191)

	/* template */
	var __vue_template__ = __webpack_require__(192)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/ccCells.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-bb9ba83a"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 190 */
/***/ (function(module, exports) {

	module.exports = {
	  "normal": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderTopWidth": 1,
	    "borderTopStyle": "solid",
	    "borderTopColor": "#EEEEEE"
	  },
	  "normal-left": {
	    "margin": 24
	  },
	  "normal-right": {
	    "flex": 1,
	    "justifyContent": "center",
	    "marginTop": 24,
	    "marginBottom": 24,
	    "marginRight": 24
	  },
	  "normal-text-title": {
	    "lines": 1,
	    "textOverflow": "ellipsis",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#303030",
	    "lineHeight": 28,
	    "marginBottom": 19
	  },
	  "normal-text-desc": {
	    "lines": 2,
	    "textOverflow": "ellipsis",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 24,
	    "color": "#7E7E7E",
	    "lineHeight": 33
	  },
	  "arrow-img": {
	    "marginRight": 25
	  }
	}

/***/ }),
/* 191 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        // 区分不同的cell
	        types: {
	            required: true,
	            type: String,
	            default: ''
	        },
	        title: {
	            type: String,
	            default: ''
	        },
	        desc: {
	            type: String,
	            default: ''
	        },
	        img: {
	            type: String,
	            default: ''
	        },
	        onClick: {
	            type: Function
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {},
	    mounted: function mounted() {
	        console.log('ccCell: ', this.img);
	    }
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 192 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return (_vm.types == 'normal') ? _c('div', {
	    staticClass: ["normal"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_c('ccImage', {
	    staticClass: ["normal-left"],
	    attrs: {
	      "src": _vm.img,
	      "have3xPNG": false
	    }
	  }), _c('div', {
	    staticClass: ["normal-right"]
	  }, [_c('text', {
	    staticClass: ["normal-text-title"]
	  }, [_vm._v(_vm._s(_vm.title))]), _c('text', {
	    staticClass: ["normal-text-desc"]
	  }, [_vm._v(_vm._s(_vm.desc))])]), _c('ccImage', {
	    staticClass: ["arrow-img"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1) : _vm._e()
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 193 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(194)
	)

	/* script */
	__vue_exports__ = __webpack_require__(195)

	/* template */
	var __vue_template__ = __webpack_require__(196)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/resultContent.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-63403ca4"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 194 */
/***/ (function(module, exports) {

	module.exports = {
	  "text-img-text": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "marginTop": 32
	  },
	  "icon-text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#303030",
	    "lineHeight": 28
	  },
	  "icon-img": {
	    "marginLeft": 2,
	    "marginRight": 2
	  },
	  "content": {
	    "flex": 1,
	    "alignItems": "center",
	    "marginBottom": 48
	  },
	  "content-img": {
	    "width": 270,
	    "height": 270,
	    "marginTop": 73
	  },
	  "content-text-tip": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030",
	    "marginTop": 40,
	    "marginBottom": 24
	  },
	  "content-text-desc": {
	    "textAlign": "center",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#7E7E7E",
	    "lineHeight": 42,
	    "lines": 0
	  }
	}

/***/ }),
/* 195 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        types: {
	            type: String,
	            default: ''
	        },
	        isShowTopTip: {
	            type: Boolean,
	            default: true
	        },
	        lockDays: {
	            type: Number,
	            default: 30
	        }
	    },
	    data: function data() {
	        return {
	            // 设置所有可能性
	            resultItems: {
	                wait: {
	                    img: 'WXLocal/wx_auth_applying',
	                    title: '您的申请已提交，请耐心等待！',
	                    desc: '预计10分钟内完成审核，\n审核通过后将以短信形式通知您，请注意查收。'
	                },
	                fail: {
	                    img: 'WXLocal/wx_auth_fail',
	                    title: '额度申请失败',
	                    desc: '根据您的征信状况，安家派暂时无法提供额度服务。\n但您依旧可使用支付宝或银联进行购物，感谢您的使用！'
	                },
	                lock: {
	                    img: 'WXLocal/wx_auth_fail',
	                    title: '',
	                    desc: '抱歉, 根据您的实际状况，\n安家派暂时无法提供信贷服务。请' + this.lockDays + '日后重试！'
	                }
	            }
	        };
	    },

	    methods: {},
	    computed: {},
	    created: function created() {}
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 196 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(_vm.isShowTopTip) ? _c('div', {
	    staticClass: ["text-img-text"]
	  }, [_c('text', {
	    staticClass: ["icon-text"]
	  }, [_vm._v("数据来源于")]), _c('ccImage', {
	    staticClass: ["icon-img"],
	    attrs: {
	      "src": 'WXLocal/wx_icon_boss'
	    }
	  }), _c('text', {
	    staticClass: ["icon-text"]
	  }, [_vm._v("中国人民银行征信中心")])], 1) : _vm._e(), _c('div', {
	    staticClass: ["content"]
	  }, [_c('ccImage', {
	    staticClass: ["content-img"],
	    attrs: {
	      "src": _vm.resultItems[_vm.types].img
	    }
	  }), _c('text', {
	    staticClass: ["content-text-tip"]
	  }, [_vm._v(_vm._s(_vm.resultItems[_vm.types].title))]), _c('text', {
	    staticClass: ["content-text-desc"]
	  }, [_vm._v(_vm._s(_vm.resultItems[_vm.types].desc))])], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 197 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["content"]
	  }, [(_vm.isClick) ? _c('authHeader', {
	    staticClass: ["header"],
	    attrs: {
	      "items": _vm.authHeaderItems
	    }
	  }) : _vm._e(), _c('div', {
	    staticClass: ["footer"]
	  }, [_c('resultContent', {
	    staticClass: ["footer-content"],
	    attrs: {
	      "types": _vm.contentType,
	      "isShowTopTip": _vm.isClick,
	      "lockDays": this.$store.state.auth.blackLockDays
	    }
	  }), (_vm.isShowBtn) ? _c('ccButton', {
	    staticClass: ["btn-commit"],
	    attrs: {
	      "title": _vm.btnTitle,
	      "onClick": _vm.clickReflash,
	      "isClick": _vm.isClick
	    }
	  }) : _vm._e()], 1)], 1), (_vm.isClick == false) ? _c('div', {
	    staticClass: ["producList"]
	  }, [_c('text', {
	    staticClass: ["producList-title"]
	  }, [_vm._v("急用钱，不等待！立即申请以下产品吧：")]), _vm._l((_vm.productList), function(product, index) {
	    return _c('div', {
	      staticClass: ["cell"]
	    }, [_c('div', {
	      on: {
	        "click": function($event) {
	          _vm.clickCell(product)
	        }
	      }
	    }, [_c('ccCells', {
	      attrs: {
	        "types": 'normal',
	        "img": _vm.logoBaseUrl + product.productLogo,
	        "title": product.productName,
	        "desc": product.producDesc
	      }
	    })], 1)])
	  })], 2) : _vm._e()])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 198 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(199)
	)

	/* script */
	__vue_exports__ = __webpack_require__(200)

	/* template */
	var __vue_template__ = __webpack_require__(205)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/others/mobileAuthPage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-19a209cc"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 199 */
/***/ (function(module, exports) {

	module.exports = {
	  "top": {
	    "padding": 24
	  },
	  "bottom": {
	    "padding": 22
	  },
	  "btn-commit": {
	    "marginLeft": 51,
	    "marginRight": 51
	  },
	  "text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 24,
	    "color": "#7E7E7E"
	  },
	  "top-text": {
	    "lineHeight": 24
	  },
	  "bottom-text": {
	    "lineHeight": 39,
	    "rows": 1
	  }
	}

/***/ }),
/* 200 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _loginView = __webpack_require__(201);

	var _loginView2 = _interopRequireDefault(_loginView);

	var _ccButton = __webpack_require__(109);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { loginView: _loginView2.default, ccButton: _ccButton2.default },
	    data: function data() {
	        return {
	            mobileNo: '',
	            password: '',
	            isShowPassword: false,

	            isAgainVerifyCode: false, // 标记是否重新获取验证码
	            count: 120 // 请求120次
	        };
	    },

	    methods: {
	        checkCommit: function checkCommit() {

	            // 银行预留电话验证
	            var isMobilePasswordPass = Vue.CheckHelper.checkMobileAuthPassword(this.password);
	            if (!isMobilePasswordPass) {
	                this.password = '';
	            }

	            if (isMobilePasswordPass) {
	                return true;
	            } else {
	                Vue.TipHelper.showHub('1', '服务密码输入有误, 请重新输入!');
	                return false;
	            }
	        },
	        clickCommit: function clickCommit() {
	            console.log('click commit'

	            // 保存密码
	            );this.$store.state.mobileAuth.password = this.password;

	            console.log('this.$store.state.auth.realName: ', this.$store.state.auth.realName);
	            console.log('this.$store.state.auth.identityNo: ', this.$store.state.auth.identityNo);
	            console.log('this.$store.state.mobileAuth.account: ', this.$store.state.mobileAuth.account);
	            console.log('this.$store.state.mobileAuth.password: ', this.$store.state.mobileAuth.password);
	            console.log('this.$store.state.mobileAuth.token: ', this.$store.state.mobileAuth.token);
	            console.log('this.$store.state.mobileAuth.website: ', this.$store.state.mobileAuth.website);
	            console.log('this.$store.state.mobileAuth.captcha: ', this.$store.state.mobileAuth.captcha);

	            if (this.isClick) {
	                // 输入信息校验
	                if (!this.checkCommit()) {
	                    return;
	                }

	                // 提交验证
	                var that = this;
	                // 可以弹出弹窗
	                that.isAgainVerifyCode = false;
	                that.$store.dispatch('PHONE_INITIALIZE', { callback: function callback() {
	                        if (that.$store.state.mobileAuth.token == '' || that.$store.state.mobileAuth.website == '') {
	                            Vue.TipHelper.showHub('1', '亲爱的用户，您的手机号码所在的运营商暂时不支持，请稍后再试!');
	                            return;
	                        }

	                        that.$store.dispatch('PHONE_SUBMITINFORMATION', { callback: function callback(res) {
	                                that.$store.dispatch('PHONE_COMPARINGINFORMATION', { callback: function callback(res) {
	                                        that.callback(res);
	                                    } });
	                            } });
	                    } });
	            }
	        },
	        callback: function callback(res) {
	            var that = this;

	            if (res.type == 'CONTROL') {
	                switch (parseInt(res.processCode)) {
	                    case 10002:
	                        {
	                            //                            Vue.TipHelper.showHub('1', res.content)
	                            if (that.isAgainVerifyCode) {} else {
	                                // 弹出动态验证码码框
	                                var tempMobile = that.mobileNo.substr(0, 3) + '****' + that.mobileNo.substr(7, 11);
	                                eventModule.phoneAuthDialog(tempMobile, function (e) {
	                                    console.log('callback', e);
	                                    if (e.result == 'verifyCode') {
	                                        // 重新倒计时
	                                        eventModule.phoneAuthCountDown
	                                        // 标记已经弹出, 无需再弹
	                                        ();that.isAgainVerifyCode = true;
	                                        // 清空当前保存的验证码
	                                        that.$store.state.mobileAuth.captcha = '';
	                                        // 重新获取验证码
	                                        that.$store.dispatch('PHONE_INITIALIZE', { callback: function callback() {
	                                                that.$store.dispatch('PHONE_SUBMITINFORMATION', { callback: function callback(res) {
	                                                        that.$store.dispatch('PHONE_COMPARINGINFORMATION', { callback: function callback(res) {
	                                                                that.callback(res);
	                                                            } });
	                                                    } });
	                                            } });
	                                    } else {
	                                        that.isAgainVerifyCode = false;
	                                        if (e.result != '') {
	                                            that.$store.state.mobileAuth.captcha = e.result;
	                                            that.$store.dispatch('PHONE_SUBMITINFORMATION', { callback: function callback(res) {
	                                                    that.$store.dispatch('PHONE_COMPARINGINFORMATION', { callback: function callback(res) {
	                                                            that.callback(res);
	                                                        } });
	                                                } });
	                                        } else {
	                                            Vue.TipHelper.showHub('1', res.content);
	                                        }
	                                    }
	                                });
	                            }
	                        }break;
	                    case 10003:
	                        {
	                            Vue.TipHelper.showHub('1', res.content);
	                            that.password = '';
	                        }break;
	                    case 10004:
	                        {
	                            Vue.TipHelper.showHub('1', '动态密码错误');
	                            that.$store.state.mobileAuth.captcha = '';
	                        }break;
	                    case 10006:
	                        {
	                            Vue.TipHelper.showHub('1', res.content);
	                            that.$store.state.mobileAuth.captcha = '';
	                            // 动态密码重新下发, 重新倒计时
	                            eventModule.phoneAuthCountDown();
	                        }break;
	                    case 10007:
	                        {
	                            Vue.TipHelper.showHub('1', res.content);
	                            that.password = '';
	                        }break;
	                    case 10008:
	                        {
	                            eventModule.closePhoneAuthDialog();
	                            eventModule.wxFirePushCallback('channel_pushMobileAuthPage', { success: true });
	                            return;
	                        }break;
	                }
	            } else if (res.type == 'ERROR') {
	                Vue.TipHelper.showHub('1', res.content);
	            } else {
	                // 轮询
	                that.count = that.count - 1;
	                console.log('that.count: ', that.count);

	                if (that.count <= 0) {
	                    Vue.TipHelper.showHub('1', '获取验证码异常, 请重试。');
	                    return;
	                }
	                that.$store.dispatch('PHONE_COMPARINGINFORMATION', { callback: that.callback });
	            }
	        },

	        onInput: function onInput(e) {
	            console.log('onInput', e.value

	            // 保存长度
	            );var inputLength = e.value.length;
	            var lastInputLength = this.password.length;

	            // 删除键按下
	            if (inputLength < lastInputLength) {
	                this.password = this.password.replace(this.password.substr(lastInputLength - 1, lastInputLength), '');
	            } else {
	                // 适配android的输入bug
	                if (e.value.substr(inputLength - 1, inputLength) == '●') {
	                    return;
	                }
	                if (this.isShowPassword) {
	                    // 无黑点的输入, 取全部值
	                    this.password = e.value;
	                } else {
	                    // 有黑点的输入, 取最后值
	                    this.password = this.password + e.value.substr(inputLength - 1, inputLength);
	                }
	            }
	        },
	        onClickDelete: function onClickDelete() {
	            console.log('onClickDelete');
	            this.password = '';
	            this.passwordFormat = '';
	        },
	        onClickEye: function onClickEye() {
	            console.log('onClickEye, isShowPassword: ', this.isShowPassword);
	            this.isShowPassword = !this.isShowPassword;
	        },
	        onClickForget: function onClickForget() {
	            console.log('onClickForget');

	            var that = this;
	            Vue.NaviHelper.push('native/main/findphone', {
	                title: '手机认证'
	                //                    website: that.$store.state.mobileAuth.website,
	            }, 'pushMobileForgetPage', function (r) {
	                console.log('忘记密码回调:', r);
	            });
	        }
	    },
	    computed: {
	        isClick: function isClick() {
	            if (this.password.length >= 6) {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        passwordFormat: function passwordFormat() {
	            if (!this.isShowPassword) {
	                var temp = '';
	                for (var i = 0; i < this.password.length; i++) {
	                    temp = temp + '●';
	                }
	                return temp;
	            }
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var _$getConfig$localData = this.$getConfig().localData,
	            mobile = _$getConfig$localData.mobile,
	            realName = _$getConfig$localData.realName,
	            identityNo = _$getConfig$localData.identityNo;

	        this.$store.state.mobileAuth.account = this.mobileNo = mobile;
	        this.$store.state.auth.realName = this.$store.state.auth.realName == '' ? realName : this.$store.state.auth.realName;
	        this.$store.state.auth.identityNo = this.$store.state.auth.identityNo == '' ? identityNo : this.$store.state.auth.identityNo;
	        this.$store.state.mobileAuth.captcha = '';

	        //            const that = this
	        //            this.$store.dispatch('PHONE_INITIALIZE', {callback: function () {
	        //                Vue.TipHelper.dismisHub()
	        //                // 初始化职位了获得website
	        //                that.$store.state.mobileAuth.token = ''
	        //            }})
	    }
	};

/***/ }),
/* 201 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(202)
	)

	/* script */
	__vue_exports__ = __webpack_require__(203)

	/* template */
	var __vue_template__ = __webpack_require__(204)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/cc/loginView.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-32e40480"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 202 */
/***/ (function(module, exports) {

	module.exports = {
	  "top": {
	    "height": 87,
	    "justifyContent": "center",
	    "marginLeft": 24,
	    "marginRight": 24,
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#DFDFDF",
	    "borderBottomStyle": "solid"
	  },
	  "middle": {
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center",
	    "marginLeft": 24,
	    "marginRight": 24,
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#DFDFDF",
	    "borderBottomStyle": "solid"
	  },
	  "bottom": {
	    "height": 87,
	    "marginLeft": 24,
	    "marginRight": 24,
	    "justifyContent": "center",
	    "alignItems": "flex-end"
	  },
	  "middle-left": {
	    "flex": 1,
	    "height": 87,
	    "marginRight": 50
	  },
	  "middle-right": {
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center"
	  },
	  "delete": {
	    "width": 50,
	    "height": 50,
	    "justifyContent": "center",
	    "alignItems": "flex-end",
	    "marginRight": 22
	  },
	  "middle-right-delete": {
	    "width": 26,
	    "height": 26
	  },
	  "middle-right-eye": {
	    "width": 40,
	    "height": 40
	  },
	  "text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#303030",
	    "lineHeight": 28
	  },
	  "text-forget": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 24,
	    "color": "#108EE9",
	    "lineHeight": 24
	  },
	  "input": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "color": "#303030",
	    "lineHeight": 28,
	    "placeholderColor": "#C5C5C5"
	  }
	}

/***/ }),
/* 203 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: {},
	    props: {
	        // 区分不同的cell
	        types: {
	            required: true,
	            type: String,
	            default: ''
	        },
	        mobileNo: {
	            type: String,
	            default: ''
	        },
	        inputValue: {
	            type: String,
	            default: ''
	        },
	        onInput: {
	            type: Function
	        },

	        isShowPassword: {
	            type: Boolean,
	            default: false
	        },
	        onClickDelete: {
	            type: Function
	        },
	        onClickEye: {
	            type: Function
	        },
	        onClickForget: {
	            type: Function
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    computed: {},
	    created: function created() {}
	};

/***/ }),
/* 204 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(_vm.types == 'mobileAuth') ? _c('div', {
	    staticClass: ["mobileAuth"]
	  }, [_c('div', {
	    staticClass: ["top"]
	  }, [_c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.mobileNo))])]), _c('div', {
	    staticClass: ["middle"]
	  }, [_c('input', {
	    staticClass: ["middle-left", "input"],
	    attrs: {
	      "placeholder": "请输入手机服务密码",
	      "type": "tel",
	      "maxlength": "8",
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onInput]
	    }
	  }), _c('div', {
	    staticClass: ["middle-right"]
	  }, [(_vm.inputValue != '') ? _c('div', {
	    staticClass: ["delete"],
	    on: {
	      "click": _vm.onClickDelete
	    }
	  }, [_c('image', {
	    staticClass: ["middle-right-delete"],
	    attrs: {
	      "src": 'WXLocal/wx_delete'
	    }
	  })]) : _vm._e(), _c('image', {
	    staticClass: ["middle-right-eye"],
	    attrs: {
	      "src": _vm.isShowPassword ? 'WXLocal/wx_eye_open' : this.$store.state.icons.wx_eye_close
	    },
	    on: {
	      "click": _vm.onClickEye
	    }
	  })])]), _c('div', {
	    staticClass: ["bottom"]
	  }, [_c('text', {
	    staticClass: ["text-forget"],
	    on: {
	      "click": _vm.onClickForget
	    }
	  }, [_vm._v("忘记服务密码？")])])]) : _vm._e()])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 205 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_vm._m(0), _c('loginView', {
	    staticClass: ["loginView"],
	    attrs: {
	      "types": 'mobileAuth',
	      "mobileNo": _vm._f("mobileFormatText")(_vm.mobileNo),
	      "inputValue": _vm.isShowPassword ? _vm.password : _vm.passwordFormat,
	      "onInput": _vm.onInput,
	      "onClickDelete": _vm.onClickDelete,
	      "onClickEye": _vm.onClickEye,
	      "onClickForget": _vm.onClickForget,
	      "isShowPassword": _vm.isShowPassword
	    }
	  }), _c('ccButton', {
	    staticClass: ["btn-commit"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.clickCommit,
	      "isClick": _vm.isClick
	    }
	  }), _vm._m(1)], 1)
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["top"]
	  }, [_c('text', {
	    staticClass: ["text", "top-text"]
	  }, [_vm._v("手机认证是获取额度的必要条件，感谢您的配合。")])])
	},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["bottom"]
	  }, [_c('text', {
	    staticClass: ["text", "bottom-text"]
	  }, [_vm._v("服务密码是指客户用以登录运营商（中国移动、中国联通、中国电信等）网上营业厅等身份识别密码，由6-8位阿拉伯数字组成。")])])
	}]}
	module.exports.render._withStripped = true

/***/ }),
/* 206 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(207)
	)

	/* script */
	__vue_exports__ = __webpack_require__(208)

	/* template */
	var __vue_template__ = __webpack_require__(209)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/others/zmAuthPage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-70f984ab"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 207 */
/***/ (function(module, exports) {

	module.exports = {
	  "webview": {
	    "flex": 1
	  }
	}

/***/ }),
/* 208 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            url: ''
	        };
	    },

	    methods: {
	        start: function start(event) {
	            console.log('pagestart', event);
	        },
	        finish: function finish(event) {
	            console.log('pagefinish', event);
	            if (event.url.indexOf('zmauth/success') >= 0) {
	                // 认证成功, 返回
	                eventModule.wxFirePushCallback('channel_pushZMAuthPage', { result: true });
	                Vue.NaviHelper.push('pop');
	            }
	        },
	        error: function error(event) {
	            console.log('error', event);
	        }
	    },
	    computed: {},
	    created: function created() {},
	    mounted: function mounted() {
	        var authUrl = this.$getConfig().localData.authUrl; //分期金额

	        this.url = authUrl;

	        //            eventModule.getBaseInfo(function (e) {
	        //                that.userId = e.result.userId;
	        //                that.url ='http://gfbapp.vcash.cn/#/Ajqhapplyinstallment?userId='+that.userId+'&withdrawMoney='+ that.message +'&loanPeriods='+that.stages+'&token='+that.token;
	        //
	        //            });
	    },
	    destroy: function destroy() {}
	};

/***/ }),
/* 209 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('web', {
	    ref: "webview",
	    staticClass: ["webview"],
	    attrs: {
	      "src": _vm.url
	    },
	    on: {
	      "pagestart": _vm.start,
	      "pagefinish": _vm.finish,
	      "error": _vm.error
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 210 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(211)
	)

	/* script */
	__vue_exports__ = __webpack_require__(212)

	/* template */
	var __vue_template__ = __webpack_require__(213)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/others/bankList.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-62c5df3b"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 211 */
/***/ (function(module, exports) {

	module.exports = {
	  "cell": {
	    "height": 100,
	    "marginBottom": 20,
	    "paddingLeft": 30,
	    "paddingRight": 30
	  },
	  "item": {
	    "flex": 1,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "borderBottomWidth": 0.5,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#A9A9A9"
	  },
	  "item-last": {
	    "flex": 1,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "borderBottomWidth": 0
	  },
	  "image": {
	    "width": 50,
	    "height": 50
	  },
	  "text": {
	    "marginLeft": 30,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030"
	  }
	}

/***/ }),
/* 212 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: {},
	    props: {},
	    data: function data() {
	        return {
	            isCredit: true
	        };
	    },

	    methods: {
	        onClick: function onClick(v) {
	            var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	            eventModule.wxFirePushCallback('channel_pushBankList', { result: v });
	        }
	    },
	    computed: {
	        bankList: function bankList() {
	            if (this.isCredit) {
	                return [{ bankCode: 'ICBC', bankName: '工商银行' }, { bankCode: 'CCB', bankName: '建设银行' }, { bankCode: 'ABC', bankName: '农业银行' }, { bankCode: 'CMB', bankName: '招商银行' }, { bankCode: 'BOC', bankName: '中国银行' }, { bankCode: 'CMBC', bankName: '民生银行' }, { bankCode: 'CEB', bankName: '光大银行' }, { bankCode: 'CGB', bankName: '广发银行' }, { bankCode: 'CITIC', bankName: '中信银行' }, { bankCode: 'CIB', bankName: '兴业银行' }, { bankCode: 'PAB', bankName: '平安银行' }, { bankCode: 'BJBANK', bankName: '北京银行' }, { bankCode: 'COMM', bankName: '交通银行' }, { bankCode: 'SPDB', bankName: '浦发银行' }, { bankCode: 'SHBANK', bankName: '上海银行' }, { bankCode: 'HXBANK', bankName: '华夏银行' }, { bankCode: 'PSBOC', bankName: '邮储银行' }];
	            } else {
	                return [{ bankCode: 'ICBC', bankName: '工商银行' }, { bankCode: 'CCB', bankName: '建设银行' }, { bankCode: 'ABC', bankName: '农业银行' }, { bankCode: 'CMB', bankName: '招商银行' }, { bankCode: 'BOC', bankName: '中国银行' }, { bankCode: 'CEB', bankName: '光大银行' }, { bankCode: 'CGB', bankName: '广发银行' }, { bankCode: 'CITIC', bankName: '中信银行' }, { bankCode: 'CIB', bankName: '兴业银行' }, { bankCode: 'PAB', bankName: '平安银行' }, { bankCode: 'BJBANK', bankName: '北京银行' }, { bankCode: 'COMM', bankName: '交通银行' }, { bankCode: 'SPDB', bankName: '浦发银行' }, { bankCode: 'SHBANK', bankName: '上海银行' }, { bankCode: 'HXBANK', bankName: '华夏银行' }, { bankCode: 'PSBOC', bankName: '邮储银行' }];
	            }
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var localData = this.$getConfig().localData;
	        this.isCredit = localData.isCredit;

	        console.log(this.bankList.length);
	    }
	};

/***/ }),
/* 213 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('list', _vm._l((_vm.bankList), function(bank, index) {
	    return _c('cell', {
	      staticClass: ["cell"],
	      appendAsTree: true,
	      attrs: {
	        "append": "tree"
	      }
	    }, [_c('div', {
	      class: ['item' + (_vm.bankList.length == index + 1 ? '-last' : '')],
	      on: {
	        "click": function($event) {
	          _vm.onClick({
	            bankName: bank.bankName,
	            bankCode: bank.bankCode
	          })
	        }
	      }
	    }, [_c('image', {
	      staticClass: ["image"],
	      attrs: {
	        "src": 'WXLocal/bank_icon_' + bank.bankCode.toLowerCase()
	      }
	    }), _c('text', {
	      staticClass: ["text"]
	    }, [_vm._v(_vm._s(bank.bankName))])])])
	  }))])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 214 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(215)
	)

	/* script */
	__vue_exports__ = __webpack_require__(216)

	/* template */
	var __vue_template__ = __webpack_require__(221)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/contracts/personalInfoProtocal.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-0c82b43d"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 215 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff",
	    "justifyContent": "space-between"
	  },
	  "botWrap": {
	    "flexDirection": "row",
	    "justifyContent": "space-around",
	    "alignItems": "stretch",
	    "paddingTop": 20,
	    "paddingRight": 40,
	    "paddingLeft": 40,
	    "paddingBottom": 20,
	    "backgroundColor": "#FFFFFF"
	  },
	  "btn": {
	    "width": 180,
	    "marginLeft": 0,
	    "marginRight": 0
	  },
	  "webView": {
	    "flex": 1,
	    "position": "absolute",
	    "left": 0,
	    "right": 0,
	    "top": 0,
	    "bottom": 0
	  }
	}

/***/ }),
/* 216 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _grayButton = __webpack_require__(217);

	var _grayButton2 = _interopRequireDefault(_grayButton);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//


	exports.default = {
	    components: { uiButton: _uiButton2.default, grayButton: _grayButton2.default },
	    data: function data() {
	        return {
	            isAgreeFinish: false // 签字是否已经完成
	        };
	    },

	    methods: {
	        concel: function concel() {
	            //取消按钮
	            Vue.NaviHelper.push('pop');
	        },
	        agree: function agree() {
	            //同意按钮
	            console.log('跳转手写签名页:');
	            var that = this;
	            //                if(PageControllerObject.contractPage==1){     合同是否重签标记位（1为重签，0为正常签名）
	            //                    desc = 'txht';
	            //                }else {
	            //                    desc = 'xieyi';
	            //                }

	            Vue.NaviHelper.push('native/main/signature', {
	                title: '手写签名页',
	                'realName': this.$store.state.auth.realName,
	                'desc': 'xieyi', //
	                'customerId': this.$store.state.customerId,
	                'mobile': this.$store.state.mobile,
	                'sltAccountId': '', //WithdrawRecordObject.sltAccountId,  // 订单id-----------未知
	                'signatureType': '1' // 该合同页为固定值
	            }, 'channel_pushAgreePage', function (r) {
	                console.log('回调: ', r);
	                //                    that.$store.state.auth.bankCode = r
	            });
	            // 结束当前界面
	            if (this.$store.state.env.platform == 'android') {
	                Vue.NaviHelper.push('pop');
	            }
	        }
	    },
	    computed: {
	        //签署时间，以前都是取手机时间
	        timeNow: function timeNow() {
	            var _now = new Date();
	            return _now.getFullYear() + '年' + (_now.getMonth() + 1) + '月' + _now.getDate() + '日' + ' ' + _now.getHours() + ':' + _now.getMinutes() + ':' + _now.getSeconds();
	        },
	        url: function url() {
	            var temp = Vue.UrlMacro.WEB_URL + '#/personalInfoProtocal?' + 'xAuthToken=' + this.$store.state.token + '&mobile=' + this.$store.state.mobile + '&customerId=' + this.$store.state.customerId + '&identityNo=' + this.$store.state.auth.identityNo + '&userName=' + this.$store.state.auth.realName + '&personInfoSignature=' + (this.isAgreeFinish ? '1' : '0') + '&isWeex=' + '1';
	            console.log('url: ', temp);
	            return encodeURI(temp);
	        }
	    },
	    mounted: function mounted() {
	        var localData = this.$getConfig().localData;
	        this.$store.state.mobile = localData.mobile;
	        this.$store.state.token = localData.token;
	        this.$store.state.auth.realName = localData.realName;
	        this.$store.state.auth.identityNo = localData.identityNo;
	        this.$store.state.auth.customerId = localData.customerId;
	        this.isAgreeFinish = localData.isAgreeFinish;

	        var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	        eventModule.wxSetNavTitle(localData.title, false);
	    },
	    destroyed: function destroyed() {}
	};

/***/ }),
/* 217 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(218)
	)

	/* script */
	__vue_exports__ = __webpack_require__(219)

	/* template */
	var __vue_template__ = __webpack_require__(220)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/wl/grayButton.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-26801b3e"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 218 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "borderRadius": 8,
	    "color": "#303030",
	    "overflow": "hidden"
	  },
	  "text_normal": {
	    "flex": 1,
	    "height": 90,
	    "color": "#303030",
	    "textAlign": "center",
	    "lineHeight": 90,
	    "fontSize": 34,
	    "backgroundColor": "#ffffff",
	    "borderRadius": 4,
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c5c5c5",
	    "color:active": "#ffffff",
	    "backgroundColor:active": "#c5c5c5"
	  },
	  "text_small": {
	    "flex": 1,
	    "height": 68,
	    "color": "#303030",
	    "textAlign": "center",
	    "lineHeight": 68,
	    "fontSize": 30,
	    "backgroundColor": "#ffffff",
	    "borderRadius": 4,
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c5c5c5",
	    "color:active": "#ffffff",
	    "backgroundColor:active": "#c5c5c5"
	  }
	}

/***/ }),
/* 219 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	exports.default = {
	    props: {
	        title: {
	            type: String,
	            default: '我是按钮'
	        },
	        size: {
	            type: String,
	            default: 'normal'
	        },
	        onClick: {
	            type: Function
	        }

	    },
	    components: {},
	    data: function data() {
	        return {
	            styleObject: {
	                borderRadius: '8px'
	            }
	        };
	    },
	    created: function created() {
	        if (this.size == 'small') {
	            this.styleObject.borderRadius = '4px';
	            this.textClass = 'text_small';
	        } else {
	            this.styleObject.borderRadius = '8px';
	            this.textClass = 'text_normal';
	        }
	    },
	    mounted: function mounted() {},

	    computed: {}

	};

/***/ }),
/* 220 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    class: [_vm.objClass]
	  }, [_c('text', {
	    class: [_vm.textClass],
	    style: _vm.styleObject,
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_vm._v(_vm._s(_vm.title))])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 221 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('web', {
	    staticClass: ["webView"],
	    attrs: {
	      "src": _vm.url
	    }
	  }), _c('div'), (!_vm.isAgreeFinish) ? _c('div', {
	    staticClass: ["botWrap"]
	  }, [_c('grayButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "size": 'small',
	      "title": '取消',
	      "onClick": _vm.concel
	    }
	  }), _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "size": 'small',
	      "title": '同意',
	      "onClick": _vm.agree
	    }
	  })], 1) : _vm._e()], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 222 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(223)
	)

	/* script */
	__vue_exports__ = __webpack_require__(224)

	/* template */
	var __vue_template__ = __webpack_require__(229)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/linkman/linkman.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-3c35aa74"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 223 */
/***/ (function(module, exports) {

	module.exports = {
	  "ui-mask": {
	    "position": "absolute",
	    "left": 0,
	    "top": 0,
	    "bottom": 0,
	    "right": 0,
	    "width": 750,
	    "backgroundColor": "rgba(0,0,0,0.6)",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "confirm": {
	    "width": 660,
	    "paddingTop": 120,
	    "paddingLeft": 40,
	    "paddingRight": 40,
	    "paddingBottom": 40,
	    "backgroundColor": "#ffffff",
	    "borderRadius": 10
	  },
	  "confirm_icon": {
	    "width": 150,
	    "height": 150,
	    "marginLeft": 215
	  },
	  "confirm_sup": {
	    "fontSize": 28,
	    "color": "#313131",
	    "marginBottom": 20,
	    "marginTop": 100,
	    "textAlign": "center"
	  },
	  "confirm_sub": {
	    "fontSize": 28,
	    "color": "#666666",
	    "marginBottom": 80,
	    "textAlign": "center"
	  },
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#eeeeee"
	  },
	  "scroller": {
	    "flex": 1,
	    "justifyContent": "space-between"
	  },
	  "list": {
	    "marginBottom": 28,
	    "backgroundColor": "#ffffff",
	    "borderBottomColor": "#dddddd",
	    "borderBottomWidth": 2,
	    "borderBottomStyle": "solid"
	  },
	  "botWrap": {
	    "marginLeft": 50,
	    "marginRight": 50,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "paddingBottom": 20
	  },
	  "btn": {
	    "marginLeft": 0,
	    "marginRight": 0,
	    "marginBottom": 20
	  },
	  "tip": {
	    "alignItems": "center"
	  },
	  "tip_text": {
	    "fontSize": 24,
	    "color": "#7E7E7E",
	    "lineHeight": 38,
	    "paddingBottom": 30
	  },
	  "add": {
	    "backgroundColor": "#ffffff",
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center",
	    "height": 120,
	    "paddingTop": 44,
	    "paddingBottom": 44,
	    "paddingLeft": 24,
	    "paddingRight": 24,
	    "marginBottom": 10
	  },
	  "left-text": {
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030",
	    "lineHeight": 30
	  },
	  "right-icon": {
	    "width": 37,
	    "height": 36
	  }
	}

/***/ }),
/* 224 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _linkInfo = __webpack_require__(225);

	var _linkInfo2 = _interopRequireDefault(_linkInfo);

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var globalEvent = weex.requireModule('globalEvent');
	var modal = weex.requireModule('modal');

	exports.default = {
	    components: { linkInfo: _linkInfo2.default, uiButton: _uiButton2.default, ccImage: _ccImage2.default },
	    data: function data() {
	        return {
	            link1Relation: "",
	            link2Relation: "",
	            isClick: false,
	            curName: "",
	            curPhone: ""

	            // 已经获取全部联系人
	        };
	    },

	    methods: {
	        onChangeLink1Name: function onChangeLink1Name(e) {
	            //联系人1姓名
	            this.$store.state.linkInfo.link1Name = e.value;
	            this.submitVerify();
	        },
	        onChangeLink2Name: function onChangeLink2Name(e) {
	            //联系人2姓名
	            this.$store.state.linkInfo.link2Name = e.value;
	            this.submitVerify();
	        },
	        onBlurLink1Name: function onBlurLink1Name(e) {
	            //联系人1失去焦点
	            this.regName(e.value, 'link1Name');
	        },
	        onBlurLink2Name: function onBlurLink2Name(e) {
	            //联系人2失去焦点
	            this.regName(e.value, 'link2Name');
	        },
	        onChangeLink1Phone: function onChangeLink1Phone(e) {
	            //联系人1手机号
	            this.$store.state.linkInfo.link1Phone = this.paddingSpace(e.value);
	            this.submitVerify();
	        },
	        onChangeLink2Phone: function onChangeLink2Phone(e) {
	            //联系人2手机号
	            this.$store.state.linkInfo.link2Phone = this.paddingSpace(e.value);
	            this.submitVerify();
	        },
	        onBlurRegPhone1: function onBlurRegPhone1(e) {
	            //联系人1手机号失去焦点验证
	            this.regPhone(e.value, 'link1Phone');
	        },
	        onBlurRegPhone2: function onBlurRegPhone2(e) {
	            //联系人2手机号失去焦点验证
	            this.regPhone(e.value, 'link2Phone');
	        },
	        choseLink1: function choseLink1(e) {
	            //打开手机通讯录
	            this.curName = 'link1Name';
	            this.curPhone = 'link1Phone';
	            eventModule.openAddressBook();
	        },
	        choseLink2: function choseLink2(e) {
	            //打开手机通讯录
	            this.curName = 'link2Name';
	            this.curPhone = 'link2Phone';
	            eventModule.openAddressBook();
	        },
	        openRelationCard1: function openRelationCard1() {
	            ////联系人1打开关系弹窗
	            this.openRelation('link1Relation', 'link1Phone');
	        },
	        openRelationCard2: function openRelationCard2() {
	            ////联系人2打开关系弹窗
	            this.openRelation('link2Relation', 'link2Phone');
	        },
	        toSubmit: function toSubmit() {
	            //点击按钮提交
	            if (this.isClick && this.isHasCom()) {
	                var that = this;
	                var localData = this.$getConfig().localData;
	                var linkInfo = this.$store.state.linkInfo;
	                //if(linkInfo.link1Name && linkInfo.link1Phone && linkInfo.link1Relation &&
	                //        linkInfo.link2Name && linkInfo.link2Phone && linkInfo.link2Relation
	                //){
	                if (!regName(linkInfo.link1Name) || !regName(linkInfo.link2Name)) {
	                    Vue.TipHelper.showHub('1', '请输入4位以内汉字');
	                    return;
	                } else if (!regPh(linkInfo.link1Phone) || !regPh(linkInfo.link1Phone)) {
	                    Vue.TipHelper.showHub('1', '请输入正确手机号码');
	                    return;
	                }

	                this.$store.dispatch("LINKMAN_SAVE", { activeFlag: localData.activeFlag, callback: function callback(res) {
	                        that.$store.state.linkInfo.isSuc = true;
	                        eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法  屏蔽返回按钮
	                        globalEvent.addEventListener('WXback', function (e) {
	                            eventModule.setEnableBack(1, 0);
	                        });
	                    } });
	            }

	            function regPh(num) {
	                return (/^1\d{10}$/.test(num.replace(/\s+/g, ""))
	                );
	            }
	            function regName(name) {
	                return (/^[\u4E00-\u9FA5]{1,4}$/.test(name)
	                );
	            }
	        },
	        subConfirm: function subConfirm() {
	            Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	        },
	        regPhone: function regPhone(val, model, callback) {
	            //验证手机格式
	            var that = this;
	            if (val != '' && !/^1\d{10}$/.test(val.replace(/\s+/g, ""))) {
	                Vue.TipHelper.showHub('1', '请输入正确手机号码');
	                that.$store.state.linkInfo[model] = '';
	                that.submitVerify();
	            } else {
	                callback && callback();
	            }
	        },
	        regName: function regName(val, model, callback) {
	            var that = this;
	            if (!/^[\u4E00-\u9FA5]{1,4}$/.test(val)) {
	                Vue.TipHelper.showHub('1', '请输入4位以内汉字');
	                that.$store.state.linkInfo[model] = '';
	                that.submitVerify();
	            } else {
	                callback && callback();
	            }
	        },
	        openRelation: function openRelation(model, phone) {
	            //打开关系弹窗
	            var that = this;
	            var val = that.$store.state.linkInfo[phone];
	            that.regPhone(val, phone, function () {
	                //对应手机号为空或者正确，才可以点击
	                var content = {
	                    title: "请选择联系人关系",
	                    items: ["父母", "子女", "配偶", "兄弟姐妹", "朋友", "同事"]
	                };
	                eventModule.wxSelectListView(content, that[model], function (e) {
	                    console.log(e.result);

	                    if (e.result == '配偶' && (that.$store.state.linkInfo.link1Relation == 'po' || that.$store.state.linkInfo.link2Relation == 'po')) {
	                        Vue.TipHelper.showHub('1', '最多有一位配偶，请检查');
	                    } else {
	                        that[model] = e.result;
	                        that.$store.state.linkInfo[model] = that.format(e.result);
	                        that.submitVerify();
	                    }
	                });
	            });
	        },
	        format: function format(val) {
	            switch (val) {
	                case "父母":
	                    return "fm";
	                case "子女":
	                    return "zn";
	                case "配偶":
	                    return "po";
	                case "兄弟姐妹":
	                    return "xdjm";
	                case "朋友":
	                    return "py";
	                case "同事":
	                    return "ts";
	            }
	        },
	        paddingSpace: function paddingSpace(str) {
	            //手机间隔格式化133 3333 3333
	            return str.replace(/\s/g, '').replace(/(^\d{3})(?=\d)/g, "$1 ").replace(/(\d{4})(?=\d)/g, "$1 ");
	        },
	        submitVerify: function submitVerify() {
	            //是否可提交
	            var linkInfo = this.$store.state.linkInfo;
	            var that = this;
	            if (linkInfo.link1Name && linkInfo.link1Phone && linkInfo.link1Relation && linkInfo.link2Name && linkInfo.link2Phone && linkInfo.link2Relation) {
	                that.isClick = true;
	            } else {
	                that.isClick = false;
	            }
	            function regPh(num) {
	                return (/^1\d{10}$/.test(num.replace(/\s+/g, ""))
	                );
	            }
	            function regName(name) {
	                return (/^[\u4E00-\u9FA5]{1,4}$/.test(name)
	                );
	            }
	        },
	        isHasCom: function isHasCom() {
	            //联系人信息 姓名 电话不能重复，配偶关系只能为一个
	            var linkInfo = this.$store.state.linkInfo;
	            if (isHasPOTwo()) {
	                Vue.TipHelper.showHub('1', '不能有2个配偶');
	                return false;
	            } else if (linkInfo.link1Name == linkInfo.link2Name || linkInfo.link1Phone == linkInfo.link2Phone) {
	                Vue.TipHelper.showHub('1', '联系人信息请勿重复');

	                return false;
	            }
	            return true;
	            function isHasPOTwo() {
	                var flag = false,
	                    arr = [];
	                for (var i in linkInfo) {
	                    if (linkInfo[i] == 'po') {
	                        arr.push(linkInfo[i]);
	                    }
	                }
	                if (arr.length > 1) {
	                    flag = true;
	                }
	                return flag;
	            }
	        }
	    },
	    created: function created() {},

	    computed: {
	        showC: function showC() {
	            return this.$store.state.linkInfo.isSuc == true ? true : false;
	        },
	        isGetAll: function isGetAll() {
	            if (this.$store.state.linkInfo.link1Name != '' && this.$store.state.linkInfo.link1Phone != '') {
	                return true;
	            } else if (this.$store.state.linkInfo.link2Name != '' && this.$store.state.linkInfo.link2Phone != '') {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        objStyle: function objStyle() {
	            if (this.isGetAll) {
	                return { opacity: 1.0 };
	            } else {
	                return { opacity: 0.0, height: 1 };
	            }
	        }
	    },
	    mounted: function mounted() {
	        var that = this;

	        globalEvent.addEventListener('WXContact', function (r) {
	            console.log('WXContact callback', r);
	            that.$store.state.linkInfo.all = r.all;

	            r.phone && (r.phone = r.phone.replace(/^86/, ''));
	            if (!/^[\u4E00-\u9FA5]{1,4}$/.test(r.name)) {
	                //姓名不是4位以内汉字，包含4位
	                Vue.TipHelper.showHub('1', '联系人姓名有误');
	                return;
	            } else if (!r.phone) {
	                Vue.TipHelper.showHub('1', '手机号不正确');
	                return;
	            } else if (!/^1\d{10}$/.test(r.phone)) {
	                //电话格式有误
	                Vue.TipHelper.showHub('1', '手机号不正确');
	                return;
	            } else {
	                //导入成功
	                that.$store.state.linkInfo[that.curName] = r.name;
	                that.$store.state.linkInfo[that.curPhone] = r.phone;
	            }
	        });
	    },
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXContact');
	        globalEvent.removeEventListener('WXback');;
	    }
	};

/***/ }),
/* 225 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(226)
	)

	/* script */
	__vue_exports__ = __webpack_require__(227)

	/* template */
	var __vue_template__ = __webpack_require__(228)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/wl/linkInfo.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-01d6e651"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 226 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF"
	  },
	  "content": {
	    "flexDirection": "row",
	    "justifyContent": "flex-start",
	    "alignItems": "center",
	    "marginLeft": 24,
	    "height": 90
	  },
	  "content-left": {
	    "width": 248,
	    "height": 88,
	    "flexDirection": "row",
	    "alignItems": "center"
	  },
	  "text": {
	    "fontSize": 30,
	    "color": "#7E7E7E",
	    "justifyContent": "flex-start"
	  },
	  "content-right": {
	    "position": "relative",
	    "flex": 1,
	    "height": 88,
	    "paddingTop": 23
	  },
	  "content-right-input": {
	    "flexDirection": "row",
	    "justifyContent": "flex-end",
	    "alignItems": "center",
	    "paddingRight": 24
	  },
	  "input": {
	    "flex": 1,
	    "height": 42,
	    "lineHeight": 42,
	    "color": "#303030",
	    "fontSize": 30,
	    "placeholderColor": "#c2c2c2",
	    "textAlign": "right"
	  },
	  "text-placeholder": {
	    "height": 42,
	    "lineHeight": 42,
	    "fontSize": 30,
	    "color": "#c2c2c2"
	  },
	  "arrow": {
	    "marginLeft": 20,
	    "marginRight": 10
	  },
	  "line": {
	    "position": "absolute",
	    "bottom": 0,
	    "left": 0,
	    "right": 0,
	    "height": 2,
	    "backgroundColor": "#dddddd"
	  },
	  "choseLink": {
	    "width": 37,
	    "height": 36,
	    "marginLeft": 10
	  }
	}

/***/ }),
/* 227 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        //�Ƿ���ʾ�»���
	        isShowBB: {
	            type: Boolean,
	            default: false
	        },
	        // ���ֲ�ͬ��cell
	        types: {
	            required: true,
	            type: String,
	            default: ''
	        },
	        // ��ʾ�����ı�
	        title: {
	            type: String,
	            default: ''
	        },
	        // ��ʾռλ�ı�
	        placeholder: {
	            type: String,
	            default: '������...'
	        },
	        maxLength: {
	            type: Number
	        },
	        // ��ʾѡ��ı�, ѡ�к��չʾ
	        selectValue: {
	            type: String,
	            default: ''
	        },
	        // ��ʾ������ı�
	        inputValue: {
	            type: String,
	            default: ''
	        },
	        // ����cell����¼�
	        onClick: {
	            type: Function
	        },
	        // ����������ı�����
	        onChange: {
	            type: Function
	        },
	        onBlur: {
	            type: Function
	        },
	        onInput: {
	            type: Function
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {},
	    update: function update() {}
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 228 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["content"]
	  }, [_c('div', {
	    staticClass: ["content-left"]
	  }, [_c('text', {
	    staticClass: ["text"]
	  }, [_vm._v(_vm._s(_vm.title))])]), _c('div', {
	    staticClass: ["content-right"]
	  }, [(this.types === 'text') ? _c('div', {
	    staticClass: ["content-right-input"]
	  }, [_c('input', {
	    staticClass: ["input"],
	    attrs: {
	      "type": "text",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxLength,
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "change": _vm.onChange,
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onInput]
	    }
	  }), _c('image', {
	    staticClass: ["choseLink"],
	    attrs: {
	      "src": this.$store.state.icons.wx_add
	    },
	    on: {
	      "click": _vm.onClick
	    }
	  })]) : _vm._e(), (this.types === 'tel') ? _c('div', {
	    staticClass: ["content-right-input"]
	  }, [_c('input', {
	    staticClass: ["input"],
	    attrs: {
	      "type": "tel",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxLength,
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "change": _vm.onChange,
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onInput]
	    }
	  }), _c('image', {
	    staticClass: ["choseLink"]
	  })]) : _vm._e(), (this.types === 'select') ? _c('div', {
	    staticClass: ["content-right-input"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_c('div', [(_vm.selectValue !== '') ? _c('text', {
	    staticClass: ["input"]
	  }, [_vm._v(_vm._s(_vm.selectValue))]) : _c('text', {
	    staticClass: ["text-placeholder"]
	  }, [_vm._v(_vm._s(_vm.placeholder))])]), _c('ccImage', {
	    staticClass: ["arrow"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1) : _vm._e()]), (_vm.isShowBB) ? _c('div', {
	    staticClass: ["line"]
	  }) : _vm._e()])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 229 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["scroller"]
	  }, [_c('div', [_c('div', {
	    staticClass: ["first"]
	  }, [(!_vm.isGetAll) ? _c('div', {
	    staticClass: ["add"],
	    on: {
	      "click": _vm.choseLink1
	    }
	  }, [_c('text', {
	    staticClass: ["left-text"]
	  }, [_vm._v("联系人一")]), _c('image', {
	    staticClass: ["right-icon"],
	    attrs: {
	      "src": this.$store.state.icons.wx_add
	    }
	  })]) : _vm._e(), _c('div', {
	    staticClass: ["list"],
	    style: _vm.objStyle
	  }, [_c('linkInfo', {
	    attrs: {
	      "types": 'text',
	      "title": '联系人一',
	      "placeholder": '请输入姓名',
	      "isShowBB": true,
	      "inputValue": this.$store.state.linkInfo.link1Name,
	      "onInput": _vm.onChangeLink1Name,
	      "onClick": _vm.choseLink1,
	      "onChange": _vm.onBlurLink1Name
	    }
	  }), _c('linkInfo', {
	    attrs: {
	      "types": 'tel',
	      "title": '手机号',
	      "placeholder": '请输入11位手机号码',
	      "maxLength": 13,
	      "isShowBB": true,
	      "inputValue": this.$store.state.linkInfo.link1Phone,
	      "onInput": _vm.onChangeLink1Phone,
	      "onChange": _vm.onBlurRegPhone1
	    }
	  }), _c('linkInfo', {
	    attrs: {
	      "types": 'select',
	      "title": '关系',
	      "placeholder": '请选择关系',
	      "selectValue": _vm.link1Relation,
	      "onClick": _vm.openRelationCard1
	    }
	  })], 1)]), _c('div', {
	    staticClass: ["second"]
	  }, [(!_vm.isGetAll) ? _c('div', {
	    staticClass: ["add"],
	    on: {
	      "click": _vm.choseLink2
	    }
	  }, [_c('text', {
	    staticClass: ["left-text"]
	  }, [_vm._v("联系人二")]), _c('image', {
	    staticClass: ["right-icon"],
	    attrs: {
	      "src": this.$store.state.icons.wx_add
	    }
	  })]) : _vm._e(), _c('div', {
	    staticClass: ["list"],
	    style: _vm.objStyle
	  }, [_c('linkInfo', {
	    attrs: {
	      "types": 'text',
	      "title": '联系人二',
	      "placeholder": '请输入姓名',
	      "isShowBB": true,
	      "inputValue": this.$store.state.linkInfo.link2Name,
	      "onInput": _vm.onChangeLink2Name,
	      "onClick": _vm.choseLink2,
	      "onChange": _vm.onBlurLink2Name
	    }
	  }), _c('linkInfo', {
	    attrs: {
	      "types": 'tel',
	      "title": '手机号',
	      "placeholder": '请输入11位手机号码',
	      "maxLength": 13,
	      "isShowBB": true,
	      "inputValue": this.$store.state.linkInfo.link2Phone,
	      "onInput": _vm.onChangeLink2Phone,
	      "onChange": _vm.onBlurRegPhone2
	    }
	  }), _c('linkInfo', {
	    attrs: {
	      "types": 'select',
	      "title": '关系',
	      "placeholder": '请选择关系',
	      "selectValue": _vm.link2Relation,
	      "onClick": _vm.openRelationCard2
	    }
	  })], 1)])]), _c('div', {
	    staticClass: ["botWrap"]
	  }, [_vm._m(0), _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.toSubmit,
	      "flag": _vm.isClick ? 'active' : 'disabled'
	    }
	  })], 1)]), (_vm.showC == true) ? _c('div', {
	    staticClass: ["ui-mask"]
	  }, [_c('div', {
	    staticClass: ["confirm"]
	  }, [_c('image', {
	    staticClass: ["confirm_icon"],
	    attrs: {
	      "src": this.$store.state.icons.wx_linkMan_suc
	    }
	  }), _c('text', {
	    staticClass: ["confirm_sup"]
	  }, [_vm._v("您的申请已提交")]), _c('text', {
	    staticClass: ["confirm_sub"]
	  }, [_vm._v("请耐心等待客服电话核对信息")]), _c('uiButton', {
	    staticClass: ["confirmBtn"],
	    attrs: {
	      "title": '确认',
	      "onClick": _vm.subConfirm,
	      "size": 'small'
	    }
	  })], 1)]) : _vm._e()])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["tip"]
	  }, [_c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("此功能需您授权安家趣花使用您的通讯录，感谢您的理解。")])])
	}]}
	module.exports.render._withStripped = true

/***/ }),
/* 230 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(231)
	)

	/* script */
	__vue_exports__ = __webpack_require__(232)

	/* template */
	var __vue_template__ = __webpack_require__(245)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/withdraw/withdraw.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2ee8ec78"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 231 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#f4f4f4"
	  },
	  "scroller": {
	    "flex": 1,
	    "justifyContent": "space-between"
	  },
	  "wd_info": {
	    "backgroundColor": "#ffffff"
	  },
	  "wdi_title": {
	    "paddingTop": 24,
	    "paddingLeft": 24,
	    "flexDirection": "row"
	  },
	  "wdi_title1": {
	    "fontSize": 28,
	    "color": "#303030"
	  },
	  "wdi_title2": {
	    "fontSize": 28,
	    "color": "#dddddd"
	  },
	  "form_list_wrap2": {
	    "paddingLeft": 24,
	    "backgroundColor": "#ffffff"
	  },
	  "info_tip": {
	    "fontSize": 22,
	    "color": "#7E7E7E",
	    "paddingTop": 16,
	    "paddingLeft": 20,
	    "paddingRight": 20,
	    "height": 100,
	    "lineHeight": 30
	  },
	  "form_list1": {
	    "paddingLeft": 24,
	    "paddingRight": 24
	  },
	  "ui-border-top": {
	    "borderTopStyle": "solid",
	    "borderTopWidth": 1,
	    "borderTopColor": "#dfdfdf"
	  },
	  "form_list2": {
	    "paddingRight": 24
	  },
	  "info_tip2": {
	    "fontSize": 24,
	    "color": "#C5C5C5",
	    "height": 50
	  },
	  "agreeBtn": {
	    "marginBottom": 20
	  },
	  "botWrap": {
	    "paddingTop": 30,
	    "marginLeft": 58,
	    "marginRight": 48,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "marginBottom": 32
	  },
	  "btn": {
	    "marginRight": 0
	  },
	  "top": {
	    "marginBottom": 30
	  }
	}

/***/ }),
/* 232 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _agreeBtn = __webpack_require__(101);

	var _agreeBtn2 = _interopRequireDefault(_agreeBtn);

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _uiBank = __webpack_require__(233);

	var _uiBank2 = _interopRequireDefault(_uiBank);

	var _uiSum = __webpack_require__(237);

	var _uiSum2 = _interopRequireDefault(_uiSum);

	var _flistBtw = __webpack_require__(241);

	var _flistBtw2 = _interopRequireDefault(_flistBtw);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//


	/*
	 * 1.设置密码框 在app上保存
	 * 2.提现状态页面：跳转至 查看账单 方法
	 *
	 *
	 *
	 *
	 * */

	var dom = weex.requireModule('dom');
	var globalEvent = weex.requireModule('globalEvent');
	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { ccImage: _ccImage2.default, agreeBtn: _agreeBtn2.default, uiButton: _uiButton2.default, uiBank: _uiBank2.default, uiSum: _uiSum2.default, flistBtw: _flistBtw2.default },
	    data: function data() {
	        return {
	            withdrawMoney: '', //申请提现金额
	            withdrawMoneyFloor: '', //可用额度取整
	            isTipShow: false, //每月预计还款 点击 ! 是否显示提示信息
	            formalitiesRate: '', //平台推荐费 费率
	            limit: 0, //银行卡每月还款限额
	            showTrialRepay: false, //点击借款期限，展示分期详情
	            limitFlag: false, //还款额是否在银行卡限额内
	            paymentType: '', //选择分期期限
	            firstPeriodAmount: 0, //首月需还总金额
	            periodTotalAmount: 0, //需还总金额
	            repaymentDate: '', //每月还款日
	            bindCardSource: false //2 true:允许重新绑卡
	        };
	    },

	    methods: {
	        init: function init() {
	            var that = this;

	            //提现信息初始化
	            that.$store.dispatch('WITHDRAW_INIT', {
	                callback: function callback(res) {
	                    that.$store.state.withdraw.cardBank = res.cardBank;
	                    that.$store.state.withdraw.bankCode = res.bankCode;
	                    that.$store.state.withdraw.cardNo = res.cardNo;
	                    that.$store.state.withdraw.cardFlag = res.cardFlag; //绑卡状态
	                    that.$store.state.withdraw.loanType = res.loanType; //贷款类型
	                    that.$store.state.withdraw.availableAmount = res.availableAmount; //可提现金额
	                    //that.$store.state.withdraw.availableAmount ='991.12'
	                    that.formalitiesRate = res.formalitiesRate; //平台推荐费 费率
	                    that.limit = returnLimit(res.bankCode) * 10000;
	                    console.log(that.limit);
	                    if (res.bindCardSource == '2') {
	                        //2:允许重新绑卡
	                        that.bindCardSource = true;
	                    } else {
	                        that.bindCardSource = false;
	                    }

	                    if (that.$store.state.withdraw.availableAmount < 1000) {
	                        that.$store.state.withdraw.withdrawMoney = 0;
	                    }
	                    that.withdrawMoneyFloor = Math.floor(that.$store.state.withdraw.availableAmount / 100).toFixed(2) * 100;
	                    that.withdrawMoney = thousandSeparator2(that.withdrawMoneyFloor);
	                    eventModule.getBaseInfo(function (res) {
	                        //从app获取信息
	                        console.log('从app获取信息~~~~~~~~', res);
	                        that.$store.state.withdraw.customerId = res.result.customerId;
	                        that.$store.state.withdraw.realName = res.result.realName;
	                        getAgreementId();
	                    });
	                },
	                fail: function fail(res) {
	                    //
	                    console.log('sfdd');
	                    Vue.TipHelper.showHub('2', res.msg);
	                    Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	                }
	            });

	            function getAgreementId() {
	                //请求接口，获取合同编号
	                that.$store.dispatch('WITHDRAWSTATE_AGREEMENTID_INIT', {
	                    callback: function callback(res) {
	                        that.$store.state.withdraw.agreementId = res.id + '';
	                    }
	                });
	            }

	            function returnLimit(code) {
	                switch (code) {
	                    case 'ICBC':
	                        return '5.0';
	                        break;
	                    case 'CCB':
	                        return '0.5';
	                        break;
	                    case 'ABC':
	                        return '2.0';
	                        break;
	                    case 'CMB':
	                        return '5.0';
	                        break;
	                    case 'BOC':
	                        return '1.0';
	                        break;
	                    case 'CMBC':
	                        return '200.0';
	                        break;
	                    case 'CEB':
	                        return '500.0';
	                        break;
	                    case 'CGB':
	                        return '500.0';
	                        break;
	                    case 'CITIC':
	                        return '5.0';
	                        break;
	                    case 'CIB':
	                        return '500.0';
	                        break;
	                    case 'PAB':
	                        return '5.0';
	                        break;
	                    case 'BJBANK':
	                        return '0.0';
	                        break;
	                    case 'COMM':
	                        return '0.0';
	                        break;
	                    case 'HXBANK':
	                        return '0.0';
	                        break;
	                    case 'SHBANK':
	                        return '0.0';
	                        break;
	                    case 'SPDB':
	                        return '0.0';
	                        break;
	                    case 'PSBOC':
	                        return '0.0';
	                        break;
	                    default:
	                        return '0';
	                }
	            }

	            //
	            //mBankLimit.put("BJBANK", 0.0);
	            //mBankLimit.put("COMM", 0.0);
	            //mBankLimit.put("HXBANK", 0.0);
	            //mBankLimit.put("SHBANK", 0.0);
	            //mBankLimit.put("SPDB", 0.0);
	            //mBankLimit.put("PSBOC", 0.0);

	            //mBankLimit.put("ICBC", 5.0);
	            //mBankLimit.put("CCB", 0.5);
	            //mBankLimit.put("ABC", 2.0);
	            //mBankLimit.put("CMB", 5.0);
	            //mBankLimit.put("BOC", 1.0);
	            //mBankLimit.put("CMBC", 200.0);
	            //mBankLimit.put("CEB", 500.0);
	            //mBankLimit.put("CGB", 500.0);
	            //mBankLimit.put("CITIC", 5.0);
	            //mBankLimit.put("CIB", 500.0);
	            //mBankLimit.put("PAB", 5.0);
	        },
	        bindBank: function bindBank() {
	            //选择银行卡
	            var that = this;
	            if (that.$store.state.withdraw.cardFlag == '1') return;
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_bindBank', {
	                title: '银行卡绑定',
	                cardFlag: that.$store.state.withdraw.cardFlag,
	                source: 'weex/common?id=wallet&path=router_withdraw'
	            }, 'channel_pushBankList', function (r) {});
	        },

	        //输入金额变化
	        withdrawChange: function withdrawChange(e) {

	            var that = this;
	            if (!e.value) return;
	            that.withdrawMoney = e.value;
	            //if(!/^(\d+,?)+(\.\d{0,2})?$/g.test(e.value)){
	            //        Vue.TipHelper.showHub('1', '请输入正确金额');
	            //        that.withdrawMoney =  '1,000.00';
	            //}


	            that.paymentType = '';
	            that.$store.state.withdraw.paymentType = '';

	            var withdrawM = clearThousand(that.withdrawMoney);
	            if (withdrawM > Number(that.withdrawMoneyFloor)) {
	                //输入超过最大可提现金额，显示最大可提现金额
	                withdrawM = e.value = that.withdrawMoneyFloor;
	                Vue.TipHelper.showHub('1', '请不要超过可用额度');
	                that.withdrawMoney = e.value;
	            }
	        },

	        //输入框获取焦点
	        withdrawFocus: function withdrawFocus(e) {
	            var that = this;
	            if (Number(that.withdrawMoneyFloor) < 1000) {
	                Vue.TipHelper.showHub('1', '您的可提现金额少于1000元');
	            } else {
	                that.withdrawMoney = clearThousand(that.withdrawMoney);
	            }
	        },

	        //输入框失去焦点
	        withdrawBlur: function withdrawBlur(e) {
	            var that = this;
	            var withdrawM = clearThousand(that.withdrawMoney);
	            if (withdrawM > Number(that.withdrawMoneyFloor)) {
	                //输入超过最大可提现金额，显示最大可提现金额
	                withdrawM = thousandSeparator(that.withdrawMoneyFloor);
	            } else if (withdrawM < 1000) {
	                withdrawM = thousandSeparator('1000');
	            } else if (Number(withdrawM) % 100 != 0) {
	                Vue.TipHelper.showHub('1', '请输入100的整数倍');
	                withdrawM = Math.floor(Number(withdrawM) / 100) * 100;
	            }
	            that.$store.state.withdraw.withdrawMoney = that.withdrawMoney = String(withdrawM);
	            that.withdrawMoney = thousandSeparator2(that.withdrawMoney);
	        },
	        sumClickBind: function sumClickBind() {
	            //已绑卡，提现金额小于1000元
	            if (this.withdrawMoneyFloor < 1000) {
	                Vue.TipHelper.showHub('1', '您的可提现金额少于1000元');
	            }
	        },
	        sumClickUnBind: function sumClickUnBind() {
	            //未绑卡
	            Vue.TipHelper.showHub('1', '您还没有绑卡喔');
	        },
	        minusClick: function minusClick() {
	            // 减点击
	            var that = this;
	            var munusSum = toInt(clearThousand(that.withdrawMoney));
	            if (munusSum > 1000) {
	                that.withdrawMoney = thousandSeparator2(munusSum - 100);
	            } else {
	                that.withdrawMoney = thousandSeparator2(1000);
	            }
	        },
	        plusClick: function plusClick() {
	            // 加点击
	            var that = this;
	            var munusSum = toInt(clearThousand(that.withdrawMoney));
	            if (munusSum < 1000) {
	                that.withdrawMoney = thousandSeparator2(1000);
	            } else if (munusSum < that.withdrawMoneyFloor) {
	                that.withdrawMoney = thousandSeparator2(munusSum + 100);
	            } else {
	                that.withdrawMoney = thousandSeparator2(that.withdrawMoneyFloor);
	            }
	        },
	        chosePayment: function chosePayment() {
	            //选择分几期
	            var that = this;
	            that.periodTotalAmount = 0;
	            if (that.$store.state.withdraw.cardFlag != '1') {
	                //未绑卡
	                Vue.TipHelper.showHub('1', '您还没有绑卡喔');
	                return;
	            }

	            that.withdrawMoney = thousandSeparator2(that.withdrawMoney);
	            var withdrawM = clearThousand(that.withdrawMoney);
	            if (!/^(\d+,?)+(\.0{0,2})?$/g.test(withdrawM)) {
	                Vue.TipHelper.showHub('1', '请输入正确金额');
	                that.withdrawMoney = thousandSeparator(that.withdrawMoneyFloor);
	                return;
	            } else if (withdrawM > Number(that.withdrawMoneyFloor)) {
	                //输入超过最大可提现金额，显示最大可提现金额
	                withdrawM = thousandSeparator(that.withdrawMoneyFloor);
	                that.$store.state.withdraw.withdrawMoney = String(withdrawM);
	                that.withdrawMoney = thousandSeparator2(String(withdrawM));
	                return;
	            } else if (withdrawM < 1000) {
	                if (Number(that.withdrawMoneyFloor) < 1000) {
	                    Vue.TipHelper.showHub('1', '您的可提现金额少于1000元');
	                } else {
	                    Vue.TipHelper.showHub('1', '最低借款额为1000元哦！');
	                    withdrawM = thousandSeparator('1000.00');
	                    that.$store.state.withdraw.withdrawMoney = String(withdrawM);
	                    that.withdrawMoney = thousandSeparator2(String(withdrawM));
	                }
	                return;
	            } else if (Number(withdrawM) % 100 != 0) {
	                Vue.TipHelper.showHub('1', '请输入100的整数倍');
	                withdrawM = Math.floor(Number(withdrawM) / 100) * 100;
	                that.$store.state.withdraw.withdrawMoney = String(withdrawM);
	                that.withdrawMoney = thousandSeparator2(String(withdrawM));
	                return;
	            } else {
	                that.$store.state.withdraw.withdrawMoney = String(withdrawM);
	                that.withdrawMoney = thousandSeparator2(String(withdrawM));
	            }

	            var content = {
	                title: "请选择借款期限",
	                items: ["3期", "6期", "9期", "12期"]
	            };
	            eventModule.wxSelectListView(content, that.paymentType, function (e) {
	                that.paymentType = e.result;
	                that.$store.state.withdraw.paymentType = e.result.substring(0, e.result.length - 1);

	                that.$store.dispatch('TRIALREPAY_SHOW', {
	                    callback: function callback(res) {

	                        res.forEach(function (e, i) {
	                            that.periodTotalAmount += Number(e.eachPeriodTotalAmount); //非整数相加异常
	                        });
	                        that.periodTotalAmount = toFixedTwo(that.periodTotalAmount); //整数加上.00
	                        that.firstPeriodAmount = toFixedTwo(res[0].eachPeriodTotalAmount);
	                        if (Math.round(that.firstPeriodAmount) > that.limit) {
	                            //首月还款额大于银行卡限额
	                            Vue.TipHelper.showHub('1', '您的月还款额超过该行限额请换张银行卡重试或选择更长还款期限');
	                            that.limitFlag = false;
	                        } else {
	                            that.limitFlag = true;
	                        }
	                        that.$store.state.withdraw.scheduleAmount = res[1].eachPeriodTotalAmount; //分期每期应还金额  分期每期应还金额取第二期
	                        var repayDay = new Date(res[0].repaymentDate.replace(/\-/g, "/")).getDate();
	                        that.repaymentDate = '每月' + repayDay + '日';
	                        that.showTrialRepay = true;
	                    }
	                });
	            });
	        },
	        toggleTip: function toggleTip() {
	            //点击 ！ 切换显示提示信息
	            this.isTipShow = !this.isTipShow;
	            if (this.isTipShow) {
	                console.log(this.isTipShow);
	                setTimeout(function () {
	                    var el = this.$refs.tip;
	                    dom.scrollToElement(el, {});
	                }, 1000);
	            }
	        },
	        checkContract: function checkContract() {
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_withdrawcontract', {
	                title: '《贷款合同》',
	                withdrawMoney: that.$store.state.withdraw.withdrawMoney,
	                loanPeriods: that.$store.state.withdraw.paymentType,
	                agreementId: that.$store.state.withdraw.agreementId

	            }, '', function (r) {});
	        },
	        toSubmit: function toSubmit() {
	            //立即提现

	            /*
	             * 1.ios 只差 从密码键盘点击‘忘记交易密码’
	             * */
	            var that = this;
	            if (that.showTrialRepay) {
	                var showTranPassImport = function showTranPassImport(tips) {
	                    eventModule.showTransactionPassword({
	                        money: that.$store.state.withdraw.withdrawMoney,
	                        realName: that.$store.state.withdraw.realName,
	                        cardNoStr: that.$store.state.withdraw.cardNo.slice(-4)
	                    }, false, tips, function (e) {
	                        _showPasswordImport(e.result);
	                    });
	                };

	                //输入交易密码 判断是否正确


	                var _showPasswordImport = function _showPasswordImport(pass) {
	                    //输入交易密码 判断是否正确
	                    that.$store.dispatch('WITHDRAW_TRANSACTIONPASS_VALID', {
	                        password: pass,
	                        callback: function callback(res) {
	                            if (res === true) {
	                                //输入正确
	                                eventModule.closeTransactionPasswordView(); //关闭
	                                _withdraw_submint();
	                            } else {
	                                //res 为次数
	                                if (res >= 3) {
	                                    //Vue.TipHelper.showHub('1', '交易密码输入次数过多，请重置交易密码....');
	                                    eventModule.closeTransactionPasswordView(); //关闭
	                                    eventModule.showLimitPassword({ cardNoStr: that.$store.state.withdraw.cardNo.slice(-4) }); //密码显示输入框
	                                } else {
	                                    //交易密码输入不正确，您今日还可以输入(3 - res)次
	                                    showTranPassImport(String(3 - res));
	                                }
	                            }
	                        }
	                    });
	                };

	                //无交易密码  输入交易密码 保存交易密码


	                var _showPasswordSave = function _showPasswordSave(pass) {
	                    that.$store.dispatch('WITHDRAW_TRANSACTIONPASS_SAVE', {
	                        password: pass,
	                        callback: function callback(res) {
	                            console.log('保存交易密码suc');
	                            _withdraw_submint();
	                        }
	                    });
	                };

	                var _withdraw_submint = function _withdraw_submint() {
	                    that.$store.dispatch('WITHDRAW_SAVE', {
	                        callback: function callback(res) {
	                            Vue.NaviHelper.push('weex/common?id=wallet&path=router_withdrawState', {}, '', function (r) {});
	                        },
	                        lock: function lock() {
	                            eventModule.showLimitPassword({ cardNoStr: that.$store.state.withdraw.cardNo.slice(-4) }); //密码显示输入框
	                        }
	                    });
	                };

	                //条件允许，按键亮起

	                //判断有无交易密码
	                that.$store.dispatch('WITHDRAW_TRANSACTIONPASS_JUDGE', {
	                    callback: function callback(res) {
	                        if (res == true) {
	                            //有交易密码

	                            that.$store.dispatch('WITHDRAW_TRANSACTIONPASS_LOCK', {
	                                callback: function callback(state) {
	                                    eventModule.showTransactionPassword({
	                                        money: that.$store.state.withdraw.withdrawMoney,
	                                        realName: that.$store.state.withdraw.realName,
	                                        cardNoStr: that.$store.state.withdraw.cardNo.slice(-4)
	                                    }, false, '', function (e) {
	                                        _showPasswordImport(e.result);
	                                    });
	                                },
	                                lock: function lock() {
	                                    eventModule.showLimitPassword({ cardNoStr: that.$store.state.withdraw.cardNo.slice(-4) }); //密码显示输入框
	                                }
	                            });
	                        } else {
	                            //无交易密码 设置交易密码，设置完成后 直接提现
	                            console.log('无交易密码 设置交易密码');
	                            eventModule.showTransactionPassword({
	                                money: that.$store.state.withdraw.withdrawMoney,
	                                realName: that.$store.state.withdraw.realName,
	                                cardNoStr: that.$store.state.withdraw.cardNo.slice(-4)
	                            }, true, '', function (e) {
	                                eventModule.closeTransactionPasswordView(); //关闭
	                                _showPasswordSave(e.result);
	                            });
	                        }
	                    }
	                }

	                //调用输入密码键盘
	                );
	            }
	        }
	    },
	    mounted: function mounted() {
	        var that = this;
	        eventModule.wxSetNavTitle('提现', true);
	        that.init();

	        eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	        globalEvent.addEventListener('WXback', function (e) {
	            Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	        });
	    },

	    computed: {
	        formalities: function formalities() {
	            var that = this;
	            that.showTrialRepay = false;
	            that.paymentType = '';
	            that.$store.state.withdraw.paymentType = '';
	            var fmt = clearThousand(that.withdrawMoney) * that.formalitiesRate;
	            if (isNaN(fmt) || fmt < 50) {
	                fmt = 50;
	            }
	            return thousandSeparator2(fmt);
	        },
	        inputColor: function inputColor() {
	            var that = this;
	            if (that.$store.state.withdraw.paymentType != '') {
	                return '#303030';
	            } else {
	                return '#C2C2C2';
	            }
	        }

	    },
	    created: function created() {},
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXback');
	    }
	};

	function thousandSeparator(num) {
	    //千分位
	    var num = String(String(num).replace(",", ""));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}
	function thousandSeparator2(num) {
	    //千分位 小数点后2位
	    var num = String(Number(String(num).replace(",", "")).toFixed(2));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}
	function clearThousand(num) {
	    //去除千分位
	    return String(num).replace(/,/g, "");
	}
	function toInt(num) {
	    //舍去不足百的金额
	    return Math.floor(Number(num) / 100) * 100;
	}
	function toFixedTwo(num) {
	    var num = Number(num);
	    if (isNaN(num)) return '';
	    if (num % 1 === 0) {
	        return parseFloat(num).toFixed(2);
	    } else {
	        return num.toFixed(2);
	    }
	}
	function regexNum(str) {
	    var regex = /(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
	    var str = str.replace(/,/g, "");
	    if (str.indexOf(".") == -1) {
	        str = str.replace(regex, '$1,');
	    } else {
	        var newStr = str.split('.');
	        var str_2 = newStr[0].replace(regex, '$1,');

	        if (newStr[1].length <= 1) {
	            //小数点后只有一位时
	            str = str_2 + '.' + newStr[1];
	        } else if (newStr[1].length > 1) {
	            //小数点后两位以上时
	            var decimals = newStr[1].substr(0, 2);
	            str = str_2 + '.' + decimals;
	        }
	    }
	    return str;
	};

/***/ }),
/* 233 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(234)
	)

	/* script */
	__vue_exports__ = __webpack_require__(235)

	/* template */
	var __vue_template__ = __webpack_require__(236)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/wl/uiBank.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-5b3e7db9"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 234 */
/***/ (function(module, exports) {

	module.exports = {
	  "wdbank_header": {
	    "position": "relative",
	    "height": 240,
	    "width": 750
	  },
	  "wdbankh_bg": {
	    "position": "absolute",
	    "left": 1,
	    "right": 1,
	    "top": 10,
	    "width": 748,
	    "height": 230
	  },
	  "wdbankh_wrap": {
	    "width": 702,
	    "height": 190,
	    "marginTop": 30,
	    "marginLeft": 24,
	    "marginRight": 24,
	    "borderRadius": 4
	  },
	  "wdbankhw_icon": {
	    "position": "absolute",
	    "left": 24,
	    "top": 50,
	    "width": 90,
	    "height": 90
	  },
	  "wdbankhw_iconbg": {
	    "position": "absolute",
	    "right": 63,
	    "top": 0,
	    "width": 220,
	    "height": 190
	  },
	  "wdbankhw_info": {
	    "position": "absolute",
	    "left": 138,
	    "top": 47
	  },
	  "wdbankhwi_bank": {
	    "fontSize": 32,
	    "color": "#303030",
	    "marginBottom": 24
	  },
	  "wdbankhwi_nobank": {
	    "fontSize": 32,
	    "color": "#E2262A",
	    "marginBottom": 24
	  },
	  "wdbankhwi_sum_state": {
	    "fontSize": 28,
	    "color": "#303030"
	  },
	  "wdbankhwi_sum": {
	    "fontSize": 28,
	    "color": "#484848"
	  },
	  "wdbankhw_href": {
	    "position": "absolute",
	    "width": 15,
	    "height": 26,
	    "top": 90,
	    "right": 24
	  },
	  "wdbankhw_invalid": {
	    "position": "absolute",
	    "width": 141,
	    "height": 132,
	    "top": 29,
	    "right": 24
	  },
	  "mb-24": {
	    "marginBottom": 24
	  },
	  "ui-fd-row": {
	    "flexDirection": "row"
	  },
	  "wdbankhwi_sum_statet": {
	    "color": "#303030",
	    "fontSize": 28
	  }
	}

/***/ }),
/* 235 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _components$props$dat;

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; } //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = (_components$props$dat = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        //是否已绑卡
	        isBind: {
	            type: Boolean,
	            default: true
	        },
	        //是否可以绑卡
	        isCanBind: {
	            type: Boolean,
	            default: false
	        },
	        //是否失效
	        isInvalid: {
	            type: Boolean,
	            default: false
	        },
	        //银行名称
	        cardBank: {
	            type: String,
	            default: ''
	        },
	        //银行code
	        bankCode: {
	            type: String,
	            default: ''
	        },
	        //银行卡号
	        cardNo: {
	            type: String,
	            default: ''
	        },
	        //可提现金额
	        anount: {
	            type: String,
	            default: ''
	        },
	        //点击事件
	        onClick: {
	            type: Function
	        },
	        types: {
	            type: String,
	            default: 'common'
	        }
	    },
	    data: function data() {
	        return {
	            styleObject: {
	                opacity: 0.1
	            }
	        };
	    },

	    methods: {},
	    created: function created() {},

	    computed: {
	        iconLow: function iconLow() {
	            console.log(this.bankCode.toLowerCase());
	            return this.bankCode.toLowerCase();
	        }
	    }
	}, _defineProperty(_components$props$dat, 'created', function created() {
	    this.styleObject.opacity = 0.02;
	}), _defineProperty(_components$props$dat, 'watch', {
	    anount: function anount() {
	        //传值进入前 淡化，监听提现金额，有值再显示
	        console.log('aaa---' + this.anount);
	        if (this.anount >= 0) {
	            this.styleObject.opacity = 1;
	        }
	    }
	}), _defineProperty(_components$props$dat, 'filters', {
	    lastFour: function lastFour(num) {
	        if (num == '' || num == null) {
	            return '';
	        }
	        return num.slice(-4);
	    }

	}), _defineProperty(_components$props$dat, 'update', function update() {}), _components$props$dat);

/***/ }),
/* 236 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["wdbank_header"]
	  }, [_c('image', {
	    staticClass: ["wdbankh_bg"],
	    attrs: {
	      "src": "WXLocal/wx_bind_bank_card"
	    }
	  }), _c('div', {
	    staticClass: ["wdbankh_wrap"],
	    style: _vm.styleObject,
	    on: {
	      "click": _vm.onClick
	    }
	  }, [(_vm.isBind) ? _c('image', {
	    staticClass: ["wdbankhw_icon"],
	    attrs: {
	      "src": 'WXLocal/ic_bank_' + _vm.iconLow
	    }
	  }) : _c('image', {
	    staticClass: ["wdbankhw_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_no_tied_card'
	    }
	  }), _c('div', {
	    staticClass: ["wdbankhw_info"]
	  }, [(_vm.types == 'common') ? _c('div', [(_vm.isBind) ? _c('text', {
	    staticClass: ["wdbankhwi_bank"]
	  }, [_vm._v(_vm._s(_vm.cardBank) + "（" + _vm._s(_vm._f("lastFour")(_vm.cardNo)) + "）")]) : _c('text', {
	    staticClass: ["wdbankhwi_nobank"]
	  }, [_vm._v("您暂未绑定银行卡")]), _c('text', {
	    staticClass: ["wdbankhwi_sum"]
	  }, [_vm._v("可提现金额" + _vm._s(_vm._f("thousandSeparator")(_vm.anount)))])]) : (_vm.types == 'state') ? _c('div', [_c('text', {
	    staticClass: ["wdbankhwi_sum_state", "mb-24"]
	  }, [_vm._v("到账银行：" + _vm._s(_vm.cardBank) + "（" + _vm._s(_vm._f("lastFour")(_vm.cardNo)) + "）")]), _c('div', {
	    staticClass: ["ui-fd-row"]
	  }, [_c('text', {
	    staticClass: ["wdbankhwi_sum_state"]
	  }, [_vm._v("提现金额：")]), _c('text', {
	    staticClass: ["wdbankhwi_sum_statet"]
	  }, [_vm._v(_vm._s(_vm._f("thousandSeparator")(_vm.anount)))])])]) : _vm._e()]), (_vm.isBind) ? _c('image', {
	    staticClass: ["wdbankhw_iconbg"],
	    attrs: {
	      "src": 'WXLocal/ic_bank_dark_' + _vm.iconLow
	    }
	  }) : _vm._e(), (_vm.isCanBind) ? _c('image', {
	    staticClass: ["wdbankhw_href"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  }) : _vm._e(), (_vm.isInvalid) ? _c('image', {
	    staticClass: ["wdbankhw_invalid"],
	    attrs: {
	      "src": this.$store.state.icons.wx_wallet_invalid
	    }
	  }) : _vm._e()])])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 237 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(238)
	)

	/* script */
	__vue_exports__ = __webpack_require__(239)

	/* template */
	var __vue_template__ = __webpack_require__(240)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/wl/uiSum.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2c549fde"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 238 */
/***/ (function(module, exports) {

	module.exports = {
	  "wdsum_wrap": {
	    "marginTop": 20,
	    "marginBottom": 20,
	    "marginLeft": 24,
	    "marginRight": 24,
	    "height": 88,
	    "borderColor": "#C5C5C5",
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderRadius": 4,
	    "flexDirection": "row"
	  },
	  "wdsumw_minus": {
	    "color": "#C5C5C5",
	    "width": 88,
	    "height": 88,
	    "borderRightWidth": 1,
	    "borderRightColor": "#C5C5C5",
	    "borderRightStyle": "solid",
	    "textAlign": "center",
	    "lineHeight": 88,
	    "fontSize": 40
	  },
	  "wdsumw_plus": {
	    "color": "#C5C5C5",
	    "width": 88,
	    "height": 88,
	    "borderLeftWidth": 1,
	    "borderLeftColor": "#C5C5C5",
	    "borderLeftStyle": "solid",
	    "textAlign": "center",
	    "lineHeight": 88,
	    "fontSize": 40
	  },
	  "wdsum_in": {
	    "flex": 1,
	    "fontSize": 36,
	    "color": "#303030",
	    "textAlign": "center"
	  },
	  "wdsum_intext": {
	    "lineHeight": 88
	  }
	}

/***/ }),
/* 239 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        // 显示输入框文本
	        inputValue: {
	            type: String,
	            default: ''
	        },
	        ref: {
	            type: String,
	            default: 'inputSum'
	        },
	        // 监听输入的文本数据
	        onChange: {
	            type: Function
	        },
	        // 获取焦点
	        onFocus: {
	            type: Function
	        },
	        // text框点击
	        sumClick: {
	            type: Function
	        },
	        // 减点击
	        minusClick: {
	            type: Function
	        },
	        // 加点击
	        plusClick: {
	            type: Function
	        },
	        // 失去焦点
	        onBlur: {
	            type: Function
	        },
	        isClick: {
	            type: Boolean,
	            default: true
	        }

	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {},
	    update: function update() {}
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 240 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(!_vm.isClick) ? _c('div', {
	    staticClass: ["wdsum_wrap"]
	  }, [_c('text', {
	    staticClass: ["wdsumw_minus"],
	    on: {
	      "click": _vm.minusClick
	    }
	  }, [_vm._v("-")]), _c('input', {
	    staticClass: ["wdsum_in"],
	    attrs: {
	      "type": "tel",
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onChange],
	      "focus": _vm.onFocus,
	      "blur": _vm.onBlur
	    }
	  }), _c('text', {
	    staticClass: ["wdsumw_plus"],
	    on: {
	      "click": _vm.plusClick
	    }
	  }, [_vm._v("+")])]) : _c('div', {
	    staticClass: ["wdsum_wrap"],
	    on: {
	      "click": _vm.sumClick
	    }
	  }, [_c('text', {
	    staticClass: ["wdsumw_minus"]
	  }, [_vm._v("-")]), _c('text', {
	    staticClass: ["wdsum_in", "wdsum_intext"]
	  }, [_vm._v(_vm._s(_vm.inputValue))]), _c('text', {
	    staticClass: ["wdsumw_plus"]
	  }, [_vm._v("+")])])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 241 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(242)
	)

	/* script */
	__vue_exports__ = __webpack_require__(243)

	/* template */
	var __vue_template__ = __webpack_require__(244)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/common/components/wl/flistBtw.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-3ab7606c"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 242 */
/***/ (function(module, exports) {

	module.exports = {
	  "form_list": {
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center",
	    "height": 88
	  },
	  "form_star": {
	    "fontSize": 30,
	    "color": "#E2262A",
	    "marginRight": 20
	  },
	  "form_list_title": {
	    "fontSize": 30
	  },
	  "form_listr_text": {
	    "fontSize": 30,
	    "flex": 1
	  },
	  "form_list_right": {
	    "flex": 1,
	    "paddingLeft": 100
	  },
	  "form_listr_input": {
	    "flex": 1,
	    "fontSize": 30,
	    "placeholderColor": "#C2C2C2",
	    "paddingTop": 20,
	    "paddingBottom": 20
	  },
	  "align_right": {
	    "textAlign": "right"
	  },
	  "flex-dir-row": {
	    "flexDirection": "row",
	    "alignItems": "center"
	  },
	  "form_listr_href": {
	    "marginLeft": 20
	  }
	}

/***/ }),
/* 243 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        //是否显示star
	        isShowStar: {
	            type: Boolean,
	            default: false
	        },
	        //是否点击箭头
	        isHref: {
	            type: Boolean,
	            default: false
	        },
	        //是否右边text红色显示
	        isRightTextRed: {
	            type: Boolean,
	            default: false
	        },
	        //是否显示！ 提醒
	        isTip: {
	            type: Boolean,
	            default: false
	        },
	        //是否右边输入框不可点击
	        disabled: {
	            type: Boolean,
	            default: false
	        },
	        // types 类型
	        types: {
	            type: String,
	            default: 'textShow'
	        },
	        // 内容局右局左
	        align: {
	            type: String,
	            default: 'left'
	        },
	        // 左边标题内容
	        title: {
	            type: String,
	            default: ''
	        },
	        // 右边内容
	        content: {
	            type: String,
	            default: ''
	        },
	        // 显示输入框文本
	        inputValue: {
	            type: String,
	            default: ''
	        },
	        // 显示占位文本
	        placeholder: {
	            type: String,
	            default: ''
	        },
	        maxLength: {
	            type: Number
	        },
	        // 监听输入的文本数据
	        onChange: {
	            type: Function
	        },
	        // 左边内容颜色
	        leftColor: {
	            type: String,
	            default: ''
	        },
	        // 右边内容颜色
	        rightColor: {
	            type: String,
	            default: ''
	        },
	        onClick: {
	            type: Function
	        },
	        onFocus: {
	            type: Function
	        },
	        onBlur: {
	            type: Function
	        }
	    },
	    data: function data() {
	        return {
	            leftStyleObject: {
	                color: '#7E7E7E'
	            },
	            rightStyleObject: {
	                color: '#303030'
	            }
	        };
	    },
	    methods: function methods() {},
	    created: function created() {
	        if (this.leftColor) {
	            this.leftStyleObject.color = this.leftColor;
	        }
	        if (this.rightColor) {
	            this.rightStyleObject.color = this.rightColor;
	        }
	        if (this.align == 'left') {
	            this.alignClass = '';
	        } else {
	            this.alignClass = 'align_right';
	        }
	    },

	    computed: {},
	    watch: {
	        leftColor: function leftColor(val, oldVal) {
	            this.leftStyleObject.color = this.leftColor;
	        },
	        rightColor: function rightColor(val, oldVal) {
	            this.rightStyleObject.color = this.rightColor;
	        }
	    },
	    update: function update() {}
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),
/* 244 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["form_list"]
	  }, [_c('div', {
	    staticClass: ["form_list_left", "flex-dir-row"]
	  }, [(_vm.isShowStar) ? _c('text', {
	    staticClass: ["form_star"]
	  }, [_vm._v("*")]) : _vm._e(), _c('text', {
	    staticClass: ["form_list_title"],
	    style: _vm.leftStyleObject
	  }, [_vm._v(_vm._s(_vm.title))])]), _c('div', {
	    staticClass: ["form_list_right", "flex-dir-row"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [(this.types == 'textShow') ? _c('text', {
	    staticClass: ["form_listr_text"],
	    class: [_vm.alignClass],
	    style: _vm.rightStyleObject
	  }, [_vm._v(_vm._s(_vm.content))]) : _vm._e(), (this.types == 'num') ? _c('input', {
	    staticClass: ["form_listr_input"],
	    class: [_vm.alignClass],
	    style: _vm.rightStyleObject,
	    attrs: {
	      "type": "tel",
	      "dir": "rtl",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxLength,
	      "disabled": _vm.disabled,
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onChange],
	      "focus": _vm.onFocus,
	      "change": _vm.onBlur
	    }
	  }) : _vm._e(), (_vm.isHref) ? _c('ccImage', {
	    staticClass: ["form_listr_href"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  }) : _vm._e(), (_vm.isTip) ? _c('ccImage', {
	    staticClass: ["form_listr_href"],
	    attrs: {
	      "src": 'WXLocal/wx_explain'
	    }
	  }) : _vm._e()], 1)])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 245 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["top"]
	  }, [_c('uiBank', {
	    attrs: {
	      "cardBank": this.$store.state.withdraw.cardBank,
	      "bankCode": this.$store.state.withdraw.bankCode,
	      "anount": this.$store.state.withdraw.availableAmount,
	      "onClick": _vm.bindBank,
	      "cardNo": this.$store.state.withdraw.cardNo,
	      "isBind": this.$store.state.withdraw.cardFlag == '1',
	      "isCanBind": this.$store.state.withdraw.cardFlag != '1'
	    }
	  }), _c('div', {
	    staticClass: ["wd_info"]
	  }, [_c('div', {
	    staticClass: ["wdi_title"]
	  }, [_c('text', {
	    ref: 'tip',
	    staticClass: ["wdi_title1"]
	  }, [_vm._v("提现1金额")]), _c('text', {
	    staticClass: ["wdi_title2"]
	  }, [_vm._v("（最低1,000，以100的整倍数增减）")])]), (this.$store.state.withdraw.cardFlag == '1') ? _c('uiSum', {
	    attrs: {
	      "inputValue": _vm.withdrawMoney,
	      "onChange": _vm.withdrawChange,
	      "onFocus": _vm.withdrawFocus,
	      "onBlur": _vm.withdrawBlur,
	      "sumClick": _vm.sumClickBind,
	      "isClick": this.$store.state.withdraw.availableAmount < 1000,
	      "minusClick": _vm.minusClick,
	      "plusClick": _vm.plusClick
	    }
	  }) : _c('uiSum', {
	    attrs: {
	      "inputValue": _vm.withdrawMoney,
	      "isClick": true,
	      "sumClick": _vm.sumClickUnBind
	    }
	  }), _c('flistBtw', {
	    staticClass: ["form_list1", "ui-border-top"],
	    attrs: {
	      "title": '分期期数',
	      "content": _vm.paymentType ? _vm.paymentType : '请选择',
	      "isShowStar": true,
	      "isHref": true,
	      "onClick": _vm.chosePayment,
	      "rightColor": _vm.inputColor,
	      "align": 'right'
	    }
	  }), _c('flistBtw', {
	    staticClass: ["form_list1", "ui-border-top"],
	    attrs: {
	      "title": '平台推荐费',
	      "content": _vm.formalities,
	      "rightColor": '#E2262A',
	      "align": 'right'
	    }
	  })], 1), _c('text', {
	    staticClass: ["info_tip"]
	  }, [_vm._v("每月最后还款日平台将对您还款银行卡进行扣款，您也可在“我的账单”进行主动还款。")]), _c('div', {
	    staticClass: ["form_list_wrap2"],
	    style: {
	      opacity: _vm.showTrialRepay == true ? 1.0 : 0.0
	    }
	  }, [_c('flistBtw', {
	    staticClass: ["form_list2"],
	    attrs: {
	      "title": this.$store.state.withdraw.paymentType + '期共计',
	      "content": _vm.periodTotalAmount,
	      "align": 'right'
	    }
	  }), _c('flistBtw', {
	    staticClass: ["form_list2", "ui-border-top"],
	    attrs: {
	      "title": '还款日期',
	      "content": _vm.repaymentDate,
	      "align": 'right'
	    }
	  }), _c('flistBtw', {
	    staticClass: ["form_list2", "ui-border-top"],
	    attrs: {
	      "title": '首月预计还款',
	      "content": _vm.firstPeriodAmount,
	      "isTip": true,
	      "onClick": _vm.toggleTip,
	      "align": 'right'
	    }
	  }), (_vm.isTipShow) ? _c('text', {
	    ref: 'tip',
	    staticClass: ["info_tip2"]
	  }, [_vm._v("每月待还款金额以平台出账日出账金额为准")]) : _vm._e()], 1)], 1), _c('div', {
	    staticClass: ["botWrap"]
	  }, [(_vm.showTrialRepay) ? _c('agreeBtn', {
	    staticClass: ["agreeBtn"],
	    attrs: {
	      "isShow": true,
	      "isFinish": true,
	      "onClick": _vm.checkContract
	    }
	  }) : _vm._e(), _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '立即提现',
	      "flag": _vm.showTrialRepay && _vm.limitFlag ? 'active' : 'disabled',
	      "onClick": _vm.toSubmit
	    }
	  })], 1)])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 246 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(247)
	)

	/* script */
	__vue_exports__ = __webpack_require__(248)

	/* template */
	var __vue_template__ = __webpack_require__(249)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/contracts/withdrawcontract.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-af3f1e76"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 247 */
/***/ (function(module, exports) {

	module.exports = {
	  "webview": {
	    "flex": 1
	  }
	}

/***/ }),
/* 248 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            userId: '',
	            message: '',
	            stages: '',
	            withdrawMoney: '', //提现金额
	            loanPeriods: '', //借款还款期数
	            agreementId: '', //借款还款期数
	            token: this.$store.state.token
	        };
	    },

	    methods: {},
	    computed: {
	        url: function url() {
	            var temp = Vue.UrlMacro.WEB_URL + '/Ajqhwithdrawstages?' + 'userId=' + this.userId + '&withdrawMoney=' + this.withdrawMoney + '&agreementId=' + this.agreementId + '&loanPeriods=' + this.loanPeriods + '&flag=' + 'weex' + '&token=' + this.token;
	            console.log('url: ', temp);
	            return encodeURI(temp);
	        }

	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var that = this;
	        var data = that.$getConfig().localData;

	        that.loanPeriods = data.loanPeriods; //借款还款期数
	        that.withdrawMoney = data.withdrawMoney; //提现金额
	        that.agreementId = data.agreementId; //合同编号
	        eventModule.wxSetNavTitle(data.title, false);
	        eventModule.getBaseInfo(function (e) {
	            that.userId = e.result.userId;
	            //that.url ='http://gfbapp.vcash.cn/#/Ajqhwithdrawstages?userId='+that.userId+'&withdrawMoney='+ that.withdrawMoney +'&agreementId='+that.agreementId+'&loanPeriods='+that.loanPeriods +'&flag=weex&token='+that.token;
	            //http://localhost:9000/#/Ajqhwithdrawstages?userId=8008986&withdrawMoney=50000&loanPeriods=6&flag=weex
	        });
	    },
	    destroy: function destroy() {}
	};

/***/ }),
/* 249 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('web', {
	    ref: "webview",
	    staticClass: ["webview"],
	    attrs: {
	      "src": _vm.url
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 250 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(251)
	)

	/* script */
	__vue_exports__ = __webpack_require__(252)

	/* template */
	var __vue_template__ = __webpack_require__(253)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/withdraw/withdrawState.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-681baadd"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 251 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "bc": {
	    "backgroundColor": "#f4f4f4"
	  },
	  "scroller": {
	    "flex": 1
	  },
	  "progress": {
	    "backgroundColor": "#ffffff"
	  },
	  "prog_title": {
	    "fontSize": 28,
	    "color": "#303030",
	    "paddingLeft": 22,
	    "height": 72,
	    "lineHeight": 72,
	    "borderBottomStyle": "solid",
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#DFDFDF"
	  },
	  "info_list": {
	    "paddingLeft": 20,
	    "flexDirection": "row",
	    "height": 72,
	    "alignItems": "center"
	  },
	  "list_text": {
	    "fontSize": 28,
	    "color": "#303030",
	    "marginLeft": 24,
	    "marginTop": 2
	  },
	  "list_text_data": {
	    "fontSize": 24,
	    "color": "#C5C5C5",
	    "paddingRight": 24
	  },
	  "flex1": {
	    "flex": 1
	  },
	  "prog_opint": {
	    "marginLeft": 34
	  },
	  "botWrap": {
	    "marginLeft": 58,
	    "marginRight": 48,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "marginBottom": 32
	  },
	  "btn": {
	    "marginRight": 0
	  },
	  "fail_info": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "flex-end"
	  },
	  "fail_info_left": {
	    "fontSize": 24,
	    "color": "#333333"
	  },
	  "fail_info_wran": {
	    "fontSize": 34,
	    "color": "#E2262A"
	  },
	  "fail_info_right": {
	    "fontSize": 24,
	    "color": "#333333"
	  },
	  "text_fail": {
	    "fontSize": 28,
	    "color": "#E2262A"
	  },
	  "tip_info": {
	    "fontSize": 24,
	    "color": "#7E7E7E",
	    "lineHeight": 36,
	    "paddingLeft": 24,
	    "paddingRight": 10,
	    "paddingTop": 34
	  }
	}

/***/ }),
/* 252 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _agreeBtn = __webpack_require__(101);

	var _agreeBtn2 = _interopRequireDefault(_agreeBtn);

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _uiBank = __webpack_require__(233);

	var _uiBank2 = _interopRequireDefault(_uiBank);

	var _uiSum = __webpack_require__(237);

	var _uiSum2 = _interopRequireDefault(_uiSum);

	var _flistBtw = __webpack_require__(241);

	var _flistBtw2 = _interopRequireDefault(_flistBtw);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var dom = weex.requireModule('dom');
	var globalEvent = weex.requireModule('globalEvent');
	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { ccImage: _ccImage2.default, agreeBtn: _agreeBtn2.default, uiButton: _uiButton2.default, uiBank: _uiBank2.default, uiSum: _uiSum2.default, flistBtw: _flistBtw2.default },
	    data: function data() {
	        return {
	            transStatus: '-1', //提现状态
	            createdDate: '', //时间-
	            reviewDate: '', //时间-
	            surrenderDate: '', //时间-
	            auditFlag: '' //页面显示提现状态
	        };
	    },

	    methods: {
	        init: function init() {
	            var that = this;
	            eventModule.getBaseInfo(function (app) {
	                //从app获取信息
	                //console.log('从app获取信息~~~~~~~~', app)
	                that.$store.state.withdraw.customerId = app.result.customerId;
	                //that.$store.state.withdraw.customerId = "10826835";
	                that.$store.dispatch('WITHDRAWSTATE_INIT', {
	                    callback: function callback(res) {
	                        that.$store.state.withdraw.sltAccountId = res.sltAccountId + '';
	                        that.$store.state.withdraw.creditExpire = res.creditExpire;
	                        that.getInfo();
	                    }
	                });
	            });
	        },
	        getInfo: function getInfo() {
	            var that = this;
	            that.$store.dispatch('WITHDRAWSTATEINFO_INIT', {
	                callback: function callback(res) {
	                    //console.log(res)
	                    that.$store.state.withdraw.cardBank = res.cardBank;
	                    that.$store.state.withdraw.bankCode = res.bankCode;
	                    that.$store.state.withdraw.cardNo = res.cardNo;
	                    that.$store.state.withdraw.loanAmount = res.loanAmount; //放款金额
	                    that.$store.state.withdraw.lockDate = res.lockDate; //锁单天数
	                    that.$store.state.withdraw.transStatus = res.transStatus; //订单状态

	                    that.createdDate = res.createdDate;
	                    that.reviewDate = res.reviewDate;
	                    that.surrenderDate = res.surrenderDate;
	                    //that.auditFlag = res.auditFlag;

	                    //("重新签名",-4), // 不再使用
	                    //("提现申请复核中", -2),  // 不再使用
	                    //("提现申请失败", -1),    // 不再使用
	                    //("提现申请中", 0),
	                    //("提现完成", 1),
	                    //("还款已提交 ", 2),
	                    // ("已还清", 4),
	                    //("已解约", 5);   //  暂时未使用
	                    switch (res.transStatus) {
	                        case -2:
	                        case 0:
	                            that.transStatus = 1; //提现审核中、放款中、财务退回
	                            break;
	                        case -3:
	                            if (res.auditFlag == 1) {
	                                that.transStatus = 5; //重新绑卡
	                            } else {
	                                that.transStatus = 1;
	                            }
	                            break;
	                        case 4:
	                            that.transStatus = 3; //已经结清
	                            break;
	                        case -1:
	                            that.transStatus = 4; //管理平台拒绝
	                            break;
	                        default:
	                            that.transStatus = 2; //已放款未结清
	                            break;
	                    }
	                    if (that.transStatus == 3) {}
	                    //that.transStatus = 5;
	                    console.log(that.transStatus);
	                }
	            });
	        },
	        goToWithdraw: function goToWithdraw() {
	            //重新提现
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_withdraw', { title: '提现' }, '', function (r) {});
	        },
	        goToEGet: function goToEGet() {
	            //重新获取额度
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_wallet', { title: '' }, '', function (r) {});
	        },
	        changBankCard: function changBankCard() {
	            //更换银行卡
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_changeBank', {
	                title: '银行卡绑定',
	                sltAccountId: that.$store.state.withdraw.sltAccountId,
	                source: 'weex/common?id=wallet&path=router_withdrawState'
	            }, '', function (r) {});
	        },
	        checkBill: function checkBill() {

	            try {
	                eventModule.startH5Bill();
	            } catch (e) {
	                console.log('查看账单');
	            }
	        }
	    },
	    mounted: function mounted() {
	        var that = this;
	        eventModule.wxSetNavTitle('提现状态', true);
	        that.init();

	        eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	        globalEvent.addEventListener('WXback', function (e) {
	            Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	        });
	    },

	    computed: {},
	    created: function created() {},
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXback');
	        console.log('withdraw - destroyed');
	    }
	};

	function thousandSeparator(num) {
	    //千分位
	    var num = String(String(num).replace(",", ""));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}
	function thousandSeparator2(num) {
	    //千分位 小数点后2位
	    var num = String(Number(String(num).replace(",", "")).toFixed(2));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}
	function clearThousand(num) {
	    //去除千分位
	    return String(num).replace(/,/g, "");
	}
	function toInt(num) {
	    //舍去不足百的金额
	    return Math.floor(Number(num) / 100) * 100;
	}

/***/ }),
/* 253 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["bc"]
	  }, [_c('uiBank', {
	    attrs: {
	      "cardBank": this.$store.state.withdraw.cardBank,
	      "bankCode": this.$store.state.withdraw.bankCode,
	      "anount": this.$store.state.withdraw.loanAmount,
	      "onClick": _vm.bindBank,
	      "isBind": true,
	      "types": 'state',
	      "isInvalid": _vm.transStatus == '5',
	      "cardNo": this.$store.state.withdraw.cardNo
	    }
	  })], 1), _c('div', {
	    staticClass: ["progress"]
	  }, [_c('text', {
	    staticClass: ["prog_title"]
	  }, [_vm._v("处理进度")]), _c('div', {
	    staticClass: ["info_list"]
	  }, [_c('ccImage', {
	    staticClass: ["prog_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_agreement_withdraw'
	    }
	  }), _c('text', {
	    staticClass: ["list_text", "flex1"]
	  }, [_vm._v("申请已提交")]), _c('text', {
	    staticClass: ["list_text_data"]
	  }, [_vm._v(_vm._s(_vm.createdDate))])], 1), _c('ccImage', {
	    staticClass: ["prog_opint"],
	    attrs: {
	      "src": 'WXLocal/wx_process_line'
	    }
	  }), _c('div', {
	    staticClass: ["info_list"]
	  }, [_c('ccImage', {
	    staticClass: ["prog_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_agreement_withdraw'
	    }
	  }), _c('text', {
	    staticClass: ["list_text", "flex1"]
	  }, [_vm._v("放款处理中")]), _c('text', {
	    staticClass: ["list_text_data"]
	  }, [_vm._v(_vm._s(_vm.reviewDate))])], 1), _c('ccImage', {
	    staticClass: ["prog_opint"],
	    attrs: {
	      "src": 'WXLocal/wx_process_line'
	    }
	  }), (_vm.transStatus == '4' || _vm.transStatus == '5') ? _c('div', {
	    staticClass: ["info_list"]
	  }, [_c('ccImage', {
	    staticClass: ["prog_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_delete_new'
	    }
	  }), _c('text', {
	    staticClass: ["list_text", "flex1", "text_fail"]
	  }, [_vm._v("放款失败")]), _c('text', {
	    staticClass: ["list_text_data"]
	  }, [_vm._v(_vm._s(_vm.surrenderDate))])], 1) : _vm._e(), (_vm.transStatus == '1') ? _c('div', {
	    staticClass: ["info_list"]
	  }, [_c('ccImage', {
	    staticClass: ["prog_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_agreement_unpass'
	    }
	  }), _c('text', {
	    staticClass: ["list_text", "flex1"]
	  }, [_vm._v("放款成功")]), _c('text', {
	    staticClass: ["list_text_data"]
	  }, [_vm._v(_vm._s(_vm.surrenderDate))])], 1) : _vm._e(), (_vm.transStatus == '2') ? _c('div', {
	    staticClass: ["info_list"]
	  }, [_c('ccImage', {
	    staticClass: ["prog_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_agreement_withdraw'
	    }
	  }), _c('text', {
	    staticClass: ["list_text", "flex1"]
	  }, [_vm._v("放款成功")]), _c('text', {
	    staticClass: ["list_text_data"]
	  }, [_vm._v(_vm._s(_vm.surrenderDate))])], 1) : _vm._e()], 1), (_vm.transStatus == '1') ? _c('text', {
	    staticClass: ["tip_info"]
	  }, [_vm._v("预计当天即可完成放款，放款成功后将以短信形式通知您，请注意查收。若24小时未放款可联系客服进行咨询。")]) : _vm._e(), (_vm.transStatus == '2') ? _c('text', {
	    staticClass: ["tip_info"]
	  }, [_vm._v("钱款将转至您所绑定的银行卡内，到账后将扣除手续费，请注意卡内余额变动。（各银行到款时间有所差异，如24小时未到账请联系客服，感谢您的理解）")]) : _vm._e(), (_vm.transStatus == '5') ? _c('text', {
	    staticClass: ["tip_info"]
	  }, [_vm._v("您的银行卡信息有误，为了不影响您的使用，请尝试更换银行卡。")]) : _vm._e()]), _c('div', {
	    staticClass: ["botWrap"]
	  }, [(_vm.transStatus == '4') ? _c('div', [(this.$store.state.withdraw.lockDate > 0) ? _c('div', [_c('div', {
	    staticClass: ["fail_info"]
	  }, [_c('text', {
	    staticClass: ["fail_info_left"]
	  }, [_vm._v("抱歉,您的提现申请失败,请")]), _c('text', {
	    staticClass: ["fail_info_wran"]
	  }, [_vm._v(_vm._s(this.$store.state.withdraw.lockDate))]), _c('text', {
	    staticClass: ["fail_info_right"]
	  }, [_vm._v("天后重试")])])]) : _c('div', [(this.$store.state.withdraw.creditExpire == false) ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '重新提现',
	      "onClick": _vm.goToWithdraw
	    }
	  }) : _vm._e(), (this.$store.state.withdraw.creditExpire == true) ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '重新获取额度',
	      "onClick": _vm.goToEGet
	    }
	  }) : _vm._e()], 1)]) : _vm._e(), (_vm.transStatus == '2') ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '查看账单',
	      "onClick": _vm.checkBill
	    }
	  }) : _vm._e(), (_vm.transStatus == '5') ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '更换银行卡',
	      "onClick": _vm.changBankCard
	    }
	  }) : _vm._e()], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 254 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(255)
	)

	/* script */
	__vue_exports__ = __webpack_require__(256)

	/* template */
	var __vue_template__ = __webpack_require__(257)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/withdraw/bindBank.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-02829753"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 255 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#f4f4f4"
	  },
	  "scroller": {
	    "flex": 1
	  },
	  "top_info": {
	    "width": 702,
	    "height": 184,
	    "backgroundColor": "#ffffff",
	    "borderRadius": 16,
	    "marginTop": 24,
	    "marginLeft": 24,
	    "marginRight": 24,
	    "paddingLeft": 24,
	    "paddingRight": 24,
	    "paddingTop": 4
	  },
	  "ui-border-top": {
	    "borderTopStyle": "solid",
	    "borderTopWidth": 1,
	    "borderTopColor": "#dfdfdf"
	  },
	  "tip": {
	    "flexDirection": "row",
	    "alignItems": "center",
	    "marginLeft": 24,
	    "marginTop": 24,
	    "marginBottom": 30
	  },
	  "tip_text": {
	    "fontSize": 24,
	    "color": "#7E7E7E",
	    "marginLeft": 16
	  },
	  "info_wrap": {
	    "paddingLeft": 24,
	    "backgroundColor": "#ffffff"
	  },
	  "list": {
	    "paddingRight": 24
	  },
	  "botWrap": {
	    "marginLeft": 58,
	    "marginRight": 48,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "marginBottom": 32,
	    "height": 150
	  },
	  "btn": {
	    "marginRight": 0
	  },
	  "agreeBtn": {
	    "marginBottom": 24
	  }
	}

/***/ }),
/* 256 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _agreeBtn = __webpack_require__(101);

	var _agreeBtn2 = _interopRequireDefault(_agreeBtn);

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _uiBank = __webpack_require__(233);

	var _uiBank2 = _interopRequireDefault(_uiBank);

	var _uiSum = __webpack_require__(237);

	var _uiSum2 = _interopRequireDefault(_uiSum);

	var _flistBtw = __webpack_require__(241);

	var _flistBtw2 = _interopRequireDefault(_flistBtw);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var dom = weex.requireModule('dom');
	var globalEvent = weex.requireModule('globalEvent');
	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { ccImage: _ccImage2.default, agreeBtn: _agreeBtn2.default, uiButton: _uiButton2.default, uiBank: _uiBank2.default, uiSum: _uiSum2.default, flistBtw: _flistBtw2.default },
	    data: function data() {
	        return {
	            identitycard: '', //身份证号 加密后
	            encrypt_Name: '', //姓名 加密后
	            bindCardBank: '', //新绑定的银行名称
	            bindCardNo: '', //新绑定的银行卡号
	            phone: '', //手机号 加密后
	            source: '' //标记从哪里进来，绑卡后再跳过去
	        };
	    },

	    methods: {
	        init: function init() {
	            var that = this;
	            var localData = this.$getConfig().localData;
	            console.log(localData);
	            that.source = !!localData.source ? localData.source : 'weex/common?id=wallet&path=router_withdraw'; //获取上一个页面，绑卡后再返回，没有值 默认返回提现
	            eventModule.getBaseInfo(function (res) {
	                //从app获取信息
	                console.log('从app获取信息~~~~~~~~', res);
	                var appResult = res.result;
	                that.$store.state.withdraw.realName = appResult.realName;
	                that.$store.state.withdraw.identitycard = appResult.idCardNum;
	                that.$store.state.withdraw.phone = appResult.mobile;

	                that.identitycard = appResult.idCardNum.slice(0, 6) + "********" + appResult.idCardNum.slice(-4);
	                that.phone = appResult.mobile.substr(0, 3) + '****' + appResult.mobile.substr(-4);
	                that.encrypt_Name = encryptName(appResult.realName);

	                console.log('sssssssssssss' + appResult.customerId);
	                if (localData.cardFlag == '1') {
	                    //已绑卡的时候才初始化
	                    that.$store.dispatch('BINDBANK_INIT', { custom: appResult.customerId, callback: function callback(res) {
	                            that.$store.state.withdraw.cardBank = res.cardBank;
	                            that.$store.state.withdraw.bankCode = res.bankCode;
	                            that.$store.state.withdraw.cardNo = res.cardNo;
	                            that.$store.state.withdraw.loanType = res.loanType; //贷款类型
	                            that.$store.state.withdraw.availableAmount = res.availableAmount; //贷款类型


	                            if (that.$store.state.withdraw.availableAmount < 1000) {
	                                that.$store.state.withdraw.withdrawMoney = 0;
	                            }
	                            that.withdrawMoney = thousandSeparator(that.$store.state.withdraw.withdrawMoney);
	                        }
	                    });
	                }
	            });
	        },
	        choseBank: function choseBank() {
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_bindBankList', { title: '银行列表', isCredit: this.isCreditCard, dictCode: 'BANKLIST' }, 'channel_pushBankList', function (r) {
	                console.log('你点击的银行是: ', r.result);
	                that.$store.state.withdraw.bindCardBank = that.bindCardBank = r.result.bankName;
	                that.$store.state.withdraw.bindbankCode = r.result.bankCode;
	            });
	        },
	        bankChange: function bankChange(e) {
	            //银行卡号输入变化
	            var that = this;
	            that.bindCardNo = e.value.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
	            that.$store.state.withdraw.bindCardNo = e.value.replace(/\s+/g, "");
	        },
	        phoneFocus: function phoneFocus() {
	            //手机号获取焦点
	            console.log('手机号获取焦点');
	            var that = this;
	            that.phone = '';
	        },
	        phoneChange: function phoneChange(e) {
	            //手机号输入变化
	            var that = this;
	            if (e.value.indexOf('*') != '-1') {
	                //让$store.state.withdraw.phone 第一次不变为 177****7777
	                return;
	            }
	            that.phone = paddingSpace(e.value);
	            that.$store.state.withdraw.phone = e.value.replace(/\s+/g, "");
	        },
	        phoneBlur: function phoneBlur(e) {
	            //手机号失去焦点
	            var that = this;
	            console.log('手机号失去焦点');
	            if (e.value != '' && !regPhone(e.value)) {
	                that.phone = '';
	                that.$store.state.withdraw.phone = '';
	                Vue.TipHelper.showHub('1', '请输入正确手机号码');
	            }
	        },
	        agreeClick: function agreeClick() {
	            //合同点击
	            console.log('合同点击');
	            var that = this;
	            if (!that.bindCardBank || !that.bindCardNo) {
	                Vue.TipHelper.showHub('1', '请您先完成银行卡绑定');
	                return;
	            }
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_bindBankContract', { title: '合同', capital: '', cardBank: that.bindCardBank, cardNo: that.bindCardNo }, '', function (r) {});
	        },
	        toSubmit: function toSubmit() {
	            //提交
	            var that = this;
	            if (that.isClick) {
	                that.$store.dispatch('BINDBANK_SAVE', {
	                    callback: function callback(res) {
	                        console.log('绑卡成功' //需要修改 从不同页面进来 绑卡后 返回到不同页面
	                        );Vue.NaviHelper.push(that.source, { title: '银行列表' }, 'channel_pushBankList', function (r) {});
	                    }
	                });
	            }
	        }
	    },
	    mounted: function mounted() {
	        var that = this;
	        that.init();
	    },
	    created: function created() {},

	    computed: {
	        isClick: function isClick() {
	            var that = this;
	            if (that.$store.state.withdraw.realName && that.$store.state.withdraw.identitycard && that.$store.state.withdraw.bindCardNo && regPhone(that.$store.state.withdraw.phone)) {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        inputColor: function inputColor() {
	            var that = this;
	            if (that.$store.state.withdraw.bindCardBank != '') {
	                return '#303030';
	            } else {
	                return '#C2C2C2';
	            }
	        }
	    }
	};

	function thousandSeparator(num) {
	    //千分位 小数点后2位
	    var num = String(Number(String(num).replace(",", "")).toFixed(2));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}
	function paddingSpace(str) {
	    //手机号格式化
	    return str.replace(/\s/g, '').replace(/(^\d{3})(?=\d)/g, "$1 ").replace(/(\d{4})(?=\d)/g, "$1 ");
	};
	function regPhone(phone) {
	    return (/^1\d{10}$/.test(phone.replace(/\s+/g, ""))
	    );
	}
	function encryptName(value) {
	    if (null == value || value == '') {
	        return '';
	    }
	    var len = value.length;
	    var str = "";
	    if (len > 2) {
	        for (var i = 0; i < len - 2; i++) {
	            str += "*";
	        }
	        return value.substring(0, 1) + str + value.substring(len - 1, len);
	    } else {
	        return value.substring(0, 1) + "*";
	    }
	}

/***/ }),
/* 257 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["top_info"]
	  }, [_c('flistBtw', {
	    attrs: {
	      "title": '持卡人姓名',
	      "content": _vm.encrypt_Name,
	      "leftColor": '#303030',
	      "rightColor": '#484848',
	      "align": 'right'
	    }
	  }), _c('flistBtw', {
	    staticClass: ["ui-border-top"],
	    attrs: {
	      "title": '持卡人身份证',
	      "content": _vm.identitycard,
	      "leftColor": '#303030',
	      "rightColor": '#484848',
	      "align": 'right'
	    }
	  })], 1), _c('div', {
	    staticClass: ["tip"]
	  }, [_c('ccImage', {
	    staticClass: ["tip_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_explain'
	    }
	  }), _c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("请确认银行卡信息真实有效，该卡将用于平台交易。")])], 1), _c('div', {
	    staticClass: ["info_wrap"]
	  }, [_c('flistBtw', {
	    staticClass: ["list"],
	    attrs: {
	      "title": '选择银行',
	      "content": _vm.bindCardBank == '' ? '请选择银行卡归属银行' : _vm.bindCardBank,
	      "leftColor": '#303030',
	      "rightColor": _vm.inputColor,
	      "isHref": true,
	      "onClick": _vm.choseBank
	    }
	  }), _c('flistBtw', {
	    staticClass: ["list", "ui-border-top"],
	    attrs: {
	      "title": '银行卡号',
	      "placeholder": '请输入该银行卡卡号',
	      "inputValue": _vm.bindCardNo,
	      "leftColor": '#303030',
	      "rightColor": '#303030',
	      "types": 'num',
	      "maxLength": 23,
	      "onChange": _vm.bankChange
	    }
	  }), _c('flistBtw', {
	    staticClass: ["list", "ui-border-top"],
	    attrs: {
	      "title": '手机号码',
	      "inputValue": _vm.phone,
	      "leftColor": '#303030',
	      "rightColor": '#303030',
	      "types": 'num',
	      "placeholder": '请输入手机号码',
	      "maxLength": 13,
	      "onFocus": _vm.phoneFocus,
	      "onBlur": _vm.phoneBlur,
	      "onChange": _vm.phoneChange
	    }
	  })], 1)]), _c('div', {
	    staticClass: ["botWrap"]
	  }, [_c('agreeBtn', {
	    staticClass: ["agreeBtn"],
	    attrs: {
	      "isShow": true,
	      "isFinish": true,
	      "onClick": _vm.agreeClick
	    }
	  }), _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.toSubmit,
	      "flag": _vm.isClick ? 'active' : 'disabled'
	    }
	  })], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 258 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(259)
	)

	/* script */
	__vue_exports__ = __webpack_require__(260)

	/* template */
	var __vue_template__ = __webpack_require__(261)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/withdraw/changeBank.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2612b974"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 259 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#f4f4f4"
	  },
	  "scroller": {
	    "flex": 1
	  },
	  "top_info": {
	    "width": 702,
	    "height": 184,
	    "backgroundColor": "#ffffff",
	    "borderRadius": 16,
	    "marginTop": 24,
	    "marginLeft": 24,
	    "marginRight": 24,
	    "paddingLeft": 24,
	    "paddingRight": 24,
	    "paddingTop": 4
	  },
	  "ui-border-top": {
	    "borderTopStyle": "solid",
	    "borderTopWidth": 1,
	    "borderTopColor": "#dfdfdf"
	  },
	  "tip": {
	    "flexDirection": "row",
	    "alignItems": "center",
	    "marginLeft": 24,
	    "marginTop": 24,
	    "marginBottom": 30
	  },
	  "tip_text": {
	    "fontSize": 24,
	    "color": "#7E7E7E",
	    "marginLeft": 16
	  },
	  "info_wrap": {
	    "paddingLeft": 24,
	    "backgroundColor": "#ffffff"
	  },
	  "list": {
	    "paddingRight": 24
	  },
	  "botWrap": {
	    "marginLeft": 58,
	    "marginRight": 48,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "marginBottom": 32,
	    "height": 150
	  },
	  "btn": {
	    "marginRight": 0
	  },
	  "agreeBtn": {
	    "marginBottom": 24
	  }
	}

/***/ }),
/* 260 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _agreeBtn = __webpack_require__(101);

	var _agreeBtn2 = _interopRequireDefault(_agreeBtn);

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _uiBank = __webpack_require__(233);

	var _uiBank2 = _interopRequireDefault(_uiBank);

	var _uiSum = __webpack_require__(237);

	var _uiSum2 = _interopRequireDefault(_uiSum);

	var _flistBtw = __webpack_require__(241);

	var _flistBtw2 = _interopRequireDefault(_flistBtw);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var dom = weex.requireModule('dom');
	var globalEvent = weex.requireModule('globalEvent');
	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { ccImage: _ccImage2.default, agreeBtn: _agreeBtn2.default, uiButton: _uiButton2.default, uiBank: _uiBank2.default, uiSum: _uiSum2.default, flistBtw: _flistBtw2.default },
	    data: function data() {
	        return {
	            identitycard: '', //身份证号 加密后
	            encrypt_Name: '', //姓名 加密后
	            bindCardBank: '', //新绑定的银行名称
	            bindCardNo: '', //新绑定的银行卡号
	            customerId: '', //customerId
	            phone: '', //手机号 加密后
	            source: '', //标记从哪里进来，绑卡后再跳过去
	            accreditAgreeId: '', //accreditAgreeId
	            accreditAgreeTime: '', //accreditAgreeTime
	            sltAccountId: '', //sltAccountIdsss
	            isAgreeFinish: false // 合同签写完成的状态
	        };
	    },

	    methods: {
	        init: function init() {
	            var that = this;
	            var localData = this.$getConfig().localData;
	            console.log(localData);
	            that.sltAccountId = localData.sltAccountId;
	            console.log(that.sltAccountId);
	            that.source = !!localData.source ? localData.source : 'weex/common?id=wallet&path=router_withdraw'; //获取上一个页面，绑卡后再返回，没有值 默认返回提现
	            eventModule.getBaseInfo(function (res) {
	                //从app获取信息
	                console.log('从app获取信息~~~~~~~~', res);
	                var appResult = res.result;
	                that.$store.state.withdraw.realName = appResult.realName;
	                that.$store.state.withdraw.identitycard = appResult.idCardNum;
	                that.$store.state.withdraw.phone = appResult.mobile;

	                that.identitycard = appResult.idCardNum.slice(0, 6) + "********" + appResult.idCardNum.slice(-4);
	                that.phone = appResult.mobile.substr(0, 3) + '****' + appResult.mobile.substr(-4);
	                that.encrypt_Name = encryptName(appResult.realName);
	                that.customerId = appResult.customerId;

	                that.$store.dispatch('BINDBANK_INIT', { custom: that.customerId, callback: function callback(res) {
	                        that.$store.state.withdraw.cardBank = res.cardBank;
	                        that.$store.state.withdraw.bankCode = res.bankCode;
	                        that.$store.state.withdraw.cardNo = res.cardNo;
	                        that.$store.state.withdraw.loanType = res.loanType; //贷款类型
	                        that.$store.state.withdraw.availableAmount = res.availableAmount; //贷款类型
	                        that.accreditAgreeId = res.signId;
	                        that.accreditAgreeTime = res.signDate;

	                        if (that.$store.state.withdraw.availableAmount < 1000) {
	                            that.$store.state.withdraw.withdrawMoney = 0;
	                        }
	                        that.withdrawMoney = thousandSeparator(that.$store.state.withdraw.withdrawMoney);
	                    }
	                });
	            });

	            globalEvent.addEventListener('WXAuth', function (r) {
	                console.log('手写签名全局事件回调: ', r);
	                if (r.event == 'signature') {
	                    if (r.success == true) {
	                        console.log('手写签名全局事件回调: ' + r);
	                        that.accreditAgreeId = r.id;
	                        // todo...
	                        that.isAgreeFinish = true;
	                    }
	                }
	            });
	        },
	        agreeClick: function agreeClick() {
	            //合同点击
	            console.log('合同点击');
	            var that = this;
	            if (!that.bindCardBank || !that.bindCardNo) {
	                Vue.TipHelper.showHub('1', '请您先完成银行卡绑定');
	                return;
	            }

	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_changeBankcontract', {
	                title: '合同',
	                realName: that.$store.state.withdraw.realName,
	                changeCardFlag: '',
	                cardBank: that.bindCardBank,
	                cardNo: that.bindCardNo,
	                customerId: that.customerId,
	                isAgreeFinish: that.isAgreeFinish
	            }, 'channel_pushAgreePage', function (r) {
	                console.log('回调在上面');
	            });
	        },
	        choseBank: function choseBank() {
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=wallet&path=router_bindBankList', { title: '银行列表', isCredit: this.isCreditCard, dictCode: 'BANKLIST' }, 'channel_pushBankList', function (r) {
	                console.log('你点击的银行是: ', r.result);
	                that.$store.state.withdraw.bindCardBank = that.bindCardBank = r.result.bankName;
	                that.$store.state.withdraw.bindbankCode = r.result.bankCode;
	                that.isAgreeFinish = false;
	            });
	        },
	        bankChange: function bankChange(e) {
	            //银行卡号输入变化
	            var that = this;
	            that.bindCardNo = e.value.replace(/[\s]/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
	            that.$store.state.withdraw.bindCardNo = e.value.replace(/\s+/g, "");
	            this.isAgreeFinish = false;
	        },
	        phoneFocus: function phoneFocus() {
	            //手机号获取焦点
	            console.log('手机号获取焦点');
	            var that = this;
	            that.phone = '';
	        },
	        phoneChange: function phoneChange(e) {
	            //手机号输入变化
	            var that = this;
	            if (e.value.indexOf('*') != '-1') {
	                //让$store.state.withdraw.phone 第一次不变为 177****7777
	                return;
	            }
	            that.phone = paddingSpace(e.value);
	            that.$store.state.withdraw.phone = e.value.replace(/\s+/g, "");
	            this.isAgreeFinish = false;
	        },
	        phoneBlur: function phoneBlur(e) {
	            //手机号失去焦点
	            var that = this;
	            console.log('手机号失去焦点');
	            if (e.value != '' && !regPhone(e.value)) {
	                that.phone = '';
	                that.$store.state.withdraw.phone = '';
	                Vue.TipHelper.showHub('1', '请输入正确手机号码');
	            }
	        },
	        toSubmit: function toSubmit() {
	            //提交
	            var that = this;

	            //String customerId = CommonUtils.getValue(paramMap, ParamsCode.CUSTOMER_ID);// 客户号
	            //String newCardNo = CommonUtils.getValue(paramMap, "newCardNo");// 银行卡号
	            //String newBankCode = CommonUtils.getValue(paramMap, "newBankCode");// 银行Code
	            //String newCardBank = CommonUtils.getValue(paramMap, "newCardBank");// 银行名称
	            //String mobile = CommonUtils.getValue(paramMap, "mobile");// 校验过的用户手机号码
	            //String authAccountName = CommonUtils.getValue(paramMap, "authAccountName");// 网络征信、社保、公积金的姓名
	            //String accreditAgreeId = CommonUtils.getValue(paramMap, "accreditAgreeId");// 改卡合同的授权书的id
	            //String accreditAgreeTime = CommonUtils.getValue(paramMap, "accreditAgreeTime");// 改卡合同的授权书的时间
	            //String cardReseRveMobile = CommonUtils.getValue(paramMap, "cardReseRveMobile");// 银行卡预留手机号
	            //String sltAccountId = CommonUtils.getValue(paramMap, "sltAccountId");// 新增

	            if (that.isClick) {
	                that.$store.dispatch('CHANGEBANK_SAVE', {
	                    customerId: that.customerId,
	                    accreditAgreeId: that.accreditAgreeId,
	                    accreditAgreeTime: that.accreditAgreeTime,
	                    sltAccountId: that.sltAccountId,
	                    timeNow: new Date().getTime(),
	                    callback: function callback(res) {
	                        console.log('绑卡成功' //需要修改 从不同页面进来 绑卡后 返回到不同页面
	                        );Vue.NaviHelper.push(that.source, { title: '银行列表' }, 'channel_pushBankList', function (r) {});
	                    }
	                });
	            }
	        }
	    },
	    mounted: function mounted() {
	        var that = this;
	        that.init();
	    },
	    created: function created() {},
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXAuth');
	        console.log('changeBanck - destroyed');
	    },

	    computed: {

	        isClick: function isClick() {
	            var that = this;
	            if (that.$store.state.withdraw.realName && that.$store.state.withdraw.identitycard && that.$store.state.withdraw.bindCardNo && regPhone(that.$store.state.withdraw.phone) && !!that.isAgreeFinish) {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        inputColor: function inputColor() {
	            var that = this;
	            if (that.$store.state.withdraw.bindCardBank != '') {
	                return '#303030';
	            } else {
	                return '#C2C2C2';
	            }
	        }
	    }
	};

	function thousandSeparator(num) {
	    //千分位 小数点后2位
	    var num = String(Number(String(num).replace(",", "")).toFixed(2));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}
	function paddingSpace(str) {
	    //手机号格式化
	    return str.replace(/\s/g, '').replace(/(^\d{3})(?=\d)/g, "$1 ").replace(/(\d{4})(?=\d)/g, "$1 ");
	};
	function regPhone(phone) {
	    return (/^1\d{10}$/.test(phone.replace(/\s+/g, ""))
	    );
	}
	function encryptName(value) {
	    if (null == value || value == '') {
	        return '';
	    }
	    var len = value.length;
	    var str = "";
	    if (len > 2) {
	        for (var i = 0; i < len - 2; i++) {
	            str += "*";
	        }
	        return value.substring(0, 1) + str + value.substring(len - 1, len);
	    } else {
	        return value.substring(0, 1) + "*";
	    }
	}

/***/ }),
/* 261 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["top_info"]
	  }, [_c('flistBtw', {
	    attrs: {
	      "title": '持卡人姓名',
	      "content": _vm.encrypt_Name,
	      "leftColor": '#303030',
	      "rightColor": '#484848',
	      "align": 'right'
	    }
	  }), _c('flistBtw', {
	    staticClass: ["ui-border-top"],
	    attrs: {
	      "title": '持卡人身份证',
	      "content": _vm.identitycard,
	      "leftColor": '#303030',
	      "rightColor": '#484848',
	      "align": 'right'
	    }
	  })], 1), _c('div', {
	    staticClass: ["tip"]
	  }, [_c('ccImage', {
	    staticClass: ["tip_icon"],
	    attrs: {
	      "src": 'WXLocal/wx_explain'
	    }
	  }), _c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("请确认银行卡信息真实有效，该卡将用于平台交易。")])], 1), _c('div', {
	    staticClass: ["info_wrap"]
	  }, [_c('flistBtw', {
	    staticClass: ["list"],
	    attrs: {
	      "title": '选择银行',
	      "content": _vm.bindCardBank == '' ? '请选择银行卡归属银行' : _vm.bindCardBank,
	      "leftColor": '#303030',
	      "rightColor": _vm.inputColor,
	      "isHref": true,
	      "onClick": _vm.choseBank
	    }
	  }), _c('flistBtw', {
	    staticClass: ["list", "ui-border-top"],
	    attrs: {
	      "title": '银行卡号',
	      "placeholder": '请输入该银行卡卡号',
	      "inputValue": _vm.bindCardNo,
	      "leftColor": '#303030',
	      "rightColor": '#303030',
	      "types": 'num',
	      "maxLength": 23,
	      "onChange": _vm.bankChange
	    }
	  }), _c('flistBtw', {
	    staticClass: ["list", "ui-border-top"],
	    attrs: {
	      "title": '手机号码',
	      "inputValue": _vm.phone,
	      "leftColor": '#303030',
	      "rightColor": '#303030',
	      "types": 'num',
	      "placeholder": '请输入手机号码',
	      "maxLength": 13,
	      "onFocus": _vm.phoneFocus,
	      "onBlur": _vm.phoneBlur,
	      "onChange": _vm.phoneChange
	    }
	  })], 1)]), _c('div', {
	    staticClass: ["botWrap"]
	  }, [_c('agreeBtn', {
	    staticClass: ["agreeBtn"],
	    attrs: {
	      "isShow": true,
	      "isFinish": _vm.isAgreeFinish,
	      "onClick": _vm.agreeClick
	    }
	  }), _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.toSubmit,
	      "flag": _vm.isClick ? 'active' : 'disabled'
	    }
	  })], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 262 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(263)
	)

	/* script */
	__vue_exports__ = __webpack_require__(264)

	/* template */
	var __vue_template__ = __webpack_require__(265)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/withdraw/bankList.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2840b3b4"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 263 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1
	  },
	  "cell": {
	    "paddingLeft": 20
	  },
	  "item": {
	    "height": 88,
	    "flex": 1,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "borderBottomWidth": 1,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#A9A9A9"
	  },
	  "image": {
	    "width": 50,
	    "height": 50
	  },
	  "text": {
	    "marginLeft": 30,
	    "fontSize": 32,
	    "color": "#303030"
	  }
	}

/***/ }),
/* 264 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(53);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {},
	    data: function data() {
	        return {
	            bankList: {}
	        };
	    },

	    methods: {
	        init: function init() {
	            var that = this;
	            var localData = this.$getConfig().localData;
	            eventModule.getBaseInfo(function (res) {
	                //从app获取信息
	                var appResult = res.result;

	                that.$store.dispatch('BINDBANKLIST_INIT', {
	                    customerId: appResult.customerId,
	                    dictCode: localData.dictCode,
	                    callback: function callback(res) {
	                        that.bankList = res;
	                        that.bankList.forEach(function (e, i) {
	                            e.imgcode = 'WXLocal/bank_icon_' + e.cardCode.toLowerCase();
	                        });

	                        console.log(that.bankList);
	                    }
	                });
	            });
	        },
	        onClick: function onClick(v) {
	            var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	            eventModule.wxFirePushCallback('channel_pushBankList', { result: v });
	        }
	    },
	    computed: {},
	    created: function created() {},
	    mounted: function mounted() {
	        this.init();
	    }
	};

/***/ }),
/* 265 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('scroller', {
	    staticClass: ["wrapper"]
	  }, _vm._l((_vm.bankList), function(bank, index) {
	    return _c('div', {
	      staticClass: ["cell"]
	    }, [_c('div', {
	      staticClass: ["item"],
	      on: {
	        "click": function($event) {
	          _vm.onClick({
	            bankName: bank.dictName,
	            bankCode: bank.cardCode
	          })
	        }
	      }
	    }, [_c('image', {
	      staticClass: ["image"],
	      attrs: {
	        "src": bank.imgcode,
	        "resize": "contain"
	      }
	    }), _c('text', {
	      staticClass: ["text"]
	    }, [_vm._v(_vm._s(bank.dictName))])])])
	  }))
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 266 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(267)
	)

	/* script */
	__vue_exports__ = __webpack_require__(268)

	/* template */
	var __vue_template__ = __webpack_require__(269)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/contracts/bindBankcontract.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-27347f54"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 267 */
/***/ (function(module, exports) {

	module.exports = {
	  "webview": {
	    "flex": 1
	  }
	}

/***/ }),
/* 268 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            userId: '',
	            stages: '',
	            cardBank: '',
	            cardNo: '',
	            capital: '',
	            token: this.$store.state.token
	        };
	    },

	    methods: {},
	    computed: {
	        url: function url() {
	            var temp = Vue.UrlMacro.WEB_URL + '/Ajqhbankbinging?' + 'userId=' + this.userId + '&capital=' + this.stages + '&token=' + this.$store.state.token + '&cardBank=' + this.cardBank + '&cardNo=' + this.cardNo;
	            console.log('url: ', temp);
	            return encodeURI(temp);
	        }

	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var that = this;
	        var localData = this.$getConfig().localData;
	        that.capital = localData.capital;
	        that.cardBank = localData.cardBank;
	        that.cardNo = localData.cardNo;

	        eventModule.wxSetNavTitle(localData.title, false);
	        eventModule.getBaseInfo(function (e) {
	            that.userId = e.result.userId;
	        });
	    },
	    destroy: function destroy() {}
	};

/***/ }),
/* 269 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('web', {
	    ref: "webview",
	    staticClass: ["webview"],
	    attrs: {
	      "src": _vm.url
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 270 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(271)
	)

	/* script */
	__vue_exports__ = __webpack_require__(272)

	/* template */
	var __vue_template__ = __webpack_require__(273)
	__vue_options__ = __vue_exports__ = __vue_exports__ || {}
	if (
	  typeof __vue_exports__.default === "object" ||
	  typeof __vue_exports__.default === "function"
	) {
	if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
	__vue_options__ = __vue_exports__ = __vue_exports__.default
	}
	if (typeof __vue_options__ === "function") {
	  __vue_options__ = __vue_options__.options
	}
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajp_weex/ajp_weex/src/wallet/views/contracts/changeBankcontract.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-7fb02ef2"
	__vue_options__.style = __vue_options__.style || {}
	__vue_styles__.forEach(function (module) {
	  for (var name in module) {
	    __vue_options__.style[name] = module[name]
	  }
	})
	if (typeof __register_static_styles__ === "function") {
	  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
	}

	module.exports = __vue_exports__


/***/ }),
/* 271 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff",
	    "justifyContent": "space-between"
	  },
	  "botWrap": {
	    "flexDirection": "row",
	    "justifyContent": "space-around",
	    "alignItems": "stretch",
	    "paddingTop": 20,
	    "paddingRight": 40,
	    "paddingLeft": 40,
	    "paddingBottom": 20,
	    "backgroundColor": "#FFFFFF"
	  },
	  "btn": {
	    "width": 180,
	    "marginLeft": 0,
	    "marginRight": 0
	  },
	  "webView": {
	    "flex": 1
	  }
	}

/***/ }),
/* 272 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _uiButton = __webpack_require__(65);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _grayButton = __webpack_require__(217);

	var _grayButton2 = _interopRequireDefault(_grayButton);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: { uiButton: _uiButton2.default, grayButton: _grayButton2.default },
	    data: function data() {
	        return {
	            mobile: '',
	            changeCardFlag: false,
	            customerId: '',
	            cardBank: '',
	            cardNo: '',
	            realName: '',

	            stages: '',
	            isAgreeFinish: false, // 签字是否已经完成
	            capital: '',
	            token: this.$store.state.token
	        };
	    },

	    methods: {
	        concel: function concel() {
	            //取消按钮
	            Vue.NaviHelper.push('pop');
	        },
	        agree: function agree() {
	            //同意按钮
	            console.log('跳转手写签名页:', this.$store.state.realName);
	            var that = this;

	            Vue.NaviHelper.push('native/main/signature', {
	                title: '手写签名页',
	                'realName': that.realName,
	                'desc': 'changeCard', //
	                'customerId': this.$store.state.customerId,
	                'mobile': this.$store.state.mobile,
	                'sltAccountId': '', //WithdrawRecordObject.sltAccountId,  // 订单id-----------未知
	                'signatureType': '3', // 该合同页为固定值
	                'type': 'changeCard' // 该合同页为固定值
	            }, 'channel_pushAgreePage', function (r) {
	                console.log('回调: ', r);
	                //                    that.$store.state.auth.bankCode = r
	            });

	            // 结束当前界面
	            if (this.$store.state.env.platform == 'android') {
	                Vue.NaviHelper.push('pop');
	            }
	        }
	    },
	    computed: {

	        url: function url() {
	            var temp = Vue.UrlMacro.WEB_URL + '/repaceSupplementalContract?' + 'changeCardFlag=' + '1' + '&capital=' + this.stages + '&xAuthToken=' + this.$store.state.token + '&cardBank=' + this.cardBank + '&cardNo=' + this.cardNo + '&customerId=' + this.customerId + '&isNewApp=' + '1';
	            console.log('url: ', temp);
	            return encodeURI(temp);
	        }

	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	        var that = this;
	        var localData = this.$getConfig().localData;
	        that.changeCardFlag = localData.changeCardFlag;
	        that.cardBank = localData.cardBank;
	        that.cardNo = localData.cardNo;
	        that.realName = localData.realName;
	        that.customerId = localData.customerId;
	        this.isAgreeFinish = localData.isAgreeFinish;

	        eventModule.wxSetNavTitle(localData.title, false);
	        eventModule.getBaseInfo(function (e) {
	            that.mobile = e.result.mobile;
	        });
	    },
	    destroy: function destroy() {}
	};

/***/ }),
/* 273 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('web', {
	    staticClass: ["webView"],
	    attrs: {
	      "src": _vm.url
	    }
	  }), _c('div'), (!_vm.isAgreeFinish) ? _c('div', {
	    staticClass: ["botWrap"]
	  }, [_c('grayButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "size": 'small',
	      "title": '取消',
	      "onClick": _vm.concel
	    }
	  }), _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "size": 'small',
	      "title": '同意',
	      "onClick": _vm.agree
	    }
	  })], 1) : _vm._e()], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 274 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _RouterEvent = __webpack_require__(275);

	var _RouterEvent2 = _interopRequireDefault(_RouterEvent);

	var _ParamEvent = __webpack_require__(276);

	var _ParamEvent2 = _interopRequireDefault(_ParamEvent);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    routerEvent: _RouterEvent2.default,
	    paramEvent: _ParamEvent2.default
	};

/***/ }),
/* 275 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _index = __webpack_require__(157);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var globalEvent = weex.requireModule('globalEvent');

	globalEvent.addEventListener("router_demo", function (e) {
	    _index2.default.push('demo');
	    console.log('push demo successed');
	});

	globalEvent.addEventListener("router_banner", function (e) {
	    _index2.default.push('banner');
	    console.log('push banner successed');
	});

	globalEvent.addEventListener("router_webPage", function (e) {
	    _index2.default.push('webPage');
	    console.log('push webPage successed');
	});

	//-----------信贷----------------
	globalEvent.addEventListener("router_wallet", function (e) {
	    _index2.default.push('wallet');
	    console.log('push wallet successed');
	});

	globalEvent.addEventListener("router_identity", function (e) {
	    _index2.default.push('identity');
	    console.log('push identity successed');
	});

	globalEvent.addEventListener("router_information", function (e) {
	    _index2.default.push('information');
	    console.log('push information successed');
	});

	globalEvent.addEventListener("router_bankCard", function (e) {
	    _index2.default.push('bankCard');
	    console.log('push bankCard successed');
	});
	globalEvent.addEventListener("router_authResultPage", function (e) {
	    _index2.default.push('authResultPage');
	    console.log('push authResultPage successed');
	});

	//-----------信贷-子页----------------
	globalEvent.addEventListener("router_mobileAuthPage", function (e) {
	    _index2.default.push('mobileAuthPage');
	    console.log('push mobileAuthPage successed');
	});
	globalEvent.addEventListener("router_bankList", function (e) {
	    _index2.default.push('bankList');
	    console.log('push bankList successed');
	});
	globalEvent.addEventListener("router_zmAuthPage", function (e) {
	    _index2.default.push('zmAuthPage');
	    console.log('push zmAuthPage successed');
	});
	globalEvent.addEventListener("router_personalInfoProtocal", function (e) {
	    _index2.default.push('personalInfoProtocal');
	    console.log('push personalInfoProtocal successed');
	});

	//-----------激活----------------
	globalEvent.addEventListener("router_linkMan", function (e) {
	    _index2.default.push('linkMan');
	    console.log('push router_linkMan successed');
	});
	globalEvent.addEventListener("router_linkmanWait", function (e) {
	    _index2.default.push('linkmanWait');
	    console.log('push router_linkmanWait successed');
	}

	//-----------提现----------------
	);globalEvent.addEventListener("router_withdraw", function (e) {
	    _index2.default.push('withdraw');
	    //router.push('bindBank');
	    //router.push('withdrawState');
	    console.log('push router_withdraw successed');
	}
	//-----------提现合同----------------
	);globalEvent.addEventListener("router_withdrawcontract", function (e) {
	    _index2.default.push('withdrawcontract');
	    console.log('push router_withdrawcontract successed');
	}
	//-----------提现状态----------------
	);globalEvent.addEventListener("router_withdrawState", function (e) {
	    _index2.default.push('withdrawState');
	    //router.push('withdraw');
	    console.log('push router_withdrawState successed');
	}
	//-----------绑卡----------------
	);globalEvent.addEventListener("router_bindBank", function (e) {
	    _index2.default.push('bindBank');
	    console.log('push router_bindBank successed');
	}
	//----------- 换卡----------------
	);globalEvent.addEventListener("router_changeBank", function (e) {
	    _index2.default.push('changeBank');
	    console.log('push router_changeBank successed');
	}
	//-----------绑卡列表----------------
	);globalEvent.addEventListener("router_bindBankList", function (e) {
	    _index2.default.push('bindBankList');
	    console.log('push router_bindBankList successed');
	}
	//-----------绑卡合同----------------
	);globalEvent.addEventListener("router_bindBankContract", function (e) {
	    _index2.default.push('bindBankContract');
	    console.log('push router_bindBankContract successed');
	}
	//-----------换卡合同----------------
	);globalEvent.addEventListener("router_changeBankcontract", function (e) {
	    _index2.default.push('changeBankcontract');
	    console.log('push router_changeBankcontract successed');
	});

/***/ }),
/* 276 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _index = __webpack_require__(4);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var globalEvent = weex.requireModule('globalEvent'); /**
	                                                      * Created by x298017064010 on 17/6/14.
	                                                      */

	globalEvent.addEventListener("param_banner", function (e) {
	    console.log('param_banner: ', e);

	    var src = e.src;


	    _index2.default.state.banner_src = src;
	});

/***/ })
/******/ ]);