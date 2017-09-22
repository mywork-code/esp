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

	var _index5 = __webpack_require__(38);

	var _index6 = _interopRequireDefault(_index5);

	var _index7 = __webpack_require__(149);

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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/App.vue"
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

	var _Wallet = __webpack_require__(9);

	var _Wallet2 = _interopRequireDefault(_Wallet);

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
	        wallet: _Wallet2.default,
	        auth: _Auth2.default,
	        face: _Face2.default,
	        withdraw: _Withdraw2.default,

	        linkInfo: _LinkMan2.default,
	        commApply: _commissionApply2.default,

	        commsWL: _Commission2.default
	    },

	    // 初始化整个应用状态 this.$store.state.count
	    state: {
	        debug: false,

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

	var _WalletMutations = __webpack_require__(10);

	var mutations = _interopRequireWildcard(_WalletMutations);

	var _WalletActions = __webpack_require__(11);

	var actions = _interopRequireWildcard(_WalletActions);

	function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var wallet = {
	    actions: actions,
	    mutations: mutations,
	    state: {
	        // 首页状态
	        // 1：无额度，锁单--显示锁单天数
	        // 2：无额度，无锁单--显示申请额度
	        // 3：有额度，未提现--显示立即提现
	        // 4：有额度，已提现，提现中--显示提现的详情（提现时间，提现金额）
	        // 5：有额度，已提现，提现成功--显示提现的详情（提现时间，提现金额）
	        // 6：有额度，已提现，提现失败，锁单--显示锁单天数
	        // 7：有额度，已提现，提现失败，无锁单--立即提现
	        // 303: 有额度,待激活
	        // 302:有额度,激活中
	        // 301:额度激活--被拒后剩余锁单天数状态
	        page: '',

	        ageCondition: '', // 用户的年龄是否符合提现（0为不符合，1为符合）
	        applyCreditDate: '', // 额度申请日期
	        auditStatus: '',
	        availableAmount: '', // 可用额度
	        billDay: '',
	        creditRejRemainDate: '', // 激活额度锁单天数
	        customerFlag: '', // 新老客户标记 0:老客户，1：新客户
	        customerId: '', // 客户号
	        customerStatus: '', // 客户状态值
	        expireDate: '', // 额度失效时间
	        identityExpires: '', // 身份证有效期
	        identityNo: '', // 身份证号码
	        isZmAuth: '',
	        loanAmount: '', // 提现金额
	        loanDate: '', // 提现时间
	        lockDays: '', // 申请额度锁单天数
	        mobile: '', // 账号 手机号
	        noReadCount: '', // 推送消息未读数
	        realName: '', // 客户姓名
	        rejectTimeLimit: '',
	        repayDay: '',
	        sltAccountId: '',
	        supportAdvanceFlag: '', // 是否可以提升额度 ( 是否支持提额（false或true）)
	        totalAmount: '', // 授信总额度
	        totalWithdrawAmount: '',
	        userId: ''
	    }
	};

	exports.default = wallet;

/***/ }),
/* 10 */
/***/ (function(module, exports) {

	/**
	 * Created by x298017064010 on 17/7/26.
	 */
	"use strict";

/***/ }),
/* 11 */
/***/ (function(module, exports) {

	/**
	 * Created by x298017064010 on 17/7/26.
	 */
	"use strict";

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
	        auditStatus: '', // ???
	        availableAmount: '', // 可用额度
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
	        productList: []
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
	    Vue.NaviHelper.render('authResultPage', '授信结果');
	};
	var linkManStep = function linkManStep() {
	    Vue.NaviHelper.render('linkMan', '联系人');
	};
	var withdrawStep = function withdrawStep() {
	    Vue.NaviHelper.render('withdraw', '提现');
	};
	var testStep = function testStep() {
	    Vue.NaviHelper.render('SelectProv', 'test');
	};

	// 钱包信息
	function HOME_PAGE(_ref, payload) {
	    var commit = _ref.commit,
	        state = _ref.state,
	        dispatch = _ref.dispatch,
	        rootState = _ref.rootState;

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
	            Vue.TipHelper.showHub('1', res.msg);
	            Vue.NaviHelper.push('pop');
	        }
	    }

	    // result:
	    // {
	    //     data =     {
	    //         ageCondition = 1;
	    //     availableAmount = 0;
	    //     customerFlag = 1;
	    //     customerId = 1082678032;
	    //     customerStatus = 0101;
	    //     identityExpires = 20260429;
	    //     identityNo = 21010619900606611X;
	    //     lockDays = 0;
	    //     mobile = 18369160246;
	    //     noReadCount = 0;
	    //     page = 2;
	    //     realName = "\U91d1\U5b9d\U6cc9";
	    //     supportAdvanceFlag = 0;
	    //     totalAmount = 0;
	    //     userId = 8009061;
	    // };
	    //     msg = "\U8bf7\U6c42\U6570\U636e\U6210\U529f!";
	    //     status = 1;
	    // }
	    );
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
	                state.page = '301';
	                state.status = '02';
	                state.mobileAuthFlag = 'noauth';
	                state.creditRejRemainDate = 301;
	                state.lockDays = 302;
	            }

	            console.log('page: ', state.page);
	            console.log('status: ', state.status);
	            console.log('mobileAuthStatus: ', state.mobileAuthStatus // 手机实名状态 0:未认证 1:认证中 2:已认证 3:重新认证',
	            );console.log('mobileAuthFlag: ', state.mobileAuthFlag);
	            console.log('lockDays: ', state.lockDays);
	            console.log('page=301 creditRejRemainDate: ', state.creditRejRemainDate

	            // firstStep();
	            // return;

	            // 2.跳转页面
	            // 根据状态render不同的页面

	            // 检查是否需要激活额度

	            // 是否可以提现 page == 3 / 7 且年龄适合

	            // 检查是否需要重新激活额度


	            );if (state.status == '00' || state.status == '-99') {
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
	                endStep
	                // if (state.mobileAuthFlag == 'noauth') {
	                //     // 失败, 不带刷新按钮
	                // } else {
	                //     // 带刷新按钮, 点刷新, 调identityInit, 如果仍然mobileAuthFlag == 'fail', push到手机认证, 点返回回钱包首页
	                // }

	                // if (state.mobileAuthFlag == 'fail') {
	                //     // 自动跳转到手机认证, 并且repairFlag = 1(安卓)
	                //     // 点刷新, 调identityInit, 如果仍然mobileAuthFlag == 'fail', push到手机认证, 点返回回钱包首页
	                // } else if (state.mobileAuthFlag == 'wait') {
	                //     // 点刷新, 调identityInit, 如果mobileAuthFlag == 'fail', push到手机认证, 点返回回钱包首页, 否则, 动画一下
	                // } else {
	                //     // 检查锁单天数
	                //     // 检查锁单天数, 如果 lockDays <=0, 调用清空用户信息接口, 然后跳转身份认证页
	                //     //                            >0, 显示剩余天数
	                //     // 带刷新按钮, 点击刷新, 检查锁单天数
	                // }

	                ();
	            } else if (state.status == '03') {
	                // 征信审核失败
	                if (state.page == '3' || state.page == '4' || state.page == '5' || state.page == '6' || state.page == '7' || state.page == '303') {
	                    // 返回钱包首页
	                    Vue.NaviHelper.push('root');
	                } else {
	                    endStep();
	                }
	                // if (state.page == '34567 || 303') {返回钱包首页}
	                // else 显示锁单天数
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
	                return; // 暂时没做
	                // ------------需要重新过一遍
	                if (state.page == '3') {
	                    if (state.ageCondition == '0') {
	                        Vue.TipHelper.showHub('2', '对不起，根据您的信用情况，暂时无法为您提供服务');
	                    } else {
	                        // 显示申请提现页面
	                        withdrawStep();
	                    }
	                } else if (state.page == '4' || state.page == '5' || state.page == '6' || state.page == '7') {
	                    // 显示提现状态页面
	                } else {
	                        // fail
	                    }
	            } else {
	                firstStep();
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    }

	    // result:
	    // {
	    //     data =     {
	    //         creditApplyType = 1;
	    //     customer =         {
	    //         agent = ios;
	    //     createdDate = 1497577322000;
	    //     creditApplyType = 1;
	    //     creditSignFailureNum = 0;
	    //     educationDegree = bk;
	    //     id = 1082678032;
	    //     identityExpires = 20260429;
	    //     identityNo = 21010619900606611X;
	    //     identityPerson = 1;
	    //     identityRecognizeAddress = "\U6c88\U9633\U5e02\U94c1\U897f\U533a\U5357\U6ed1\U7fd4\U8def58-3\U53f71-1-1";
	    //     identityRecognizeName = "\U91d1\U5b9d\U6cc9";
	    //     marryStatus = wh;
	    //     mobile = 18369160246;
	    //     openId = 18369160246;
	    //     realName = "\U91d1\U5b9d\U6cc9";
	    //     remindRepayment = 1;
	    //     signatureAuditStatus = 200;
	    //     status = 0101;
	    //     updatedDate = 1498636204000;
	    // };
	    //     isFromEsp = "-1";
	    //     repeatApply = false;
	    // };
	    //     msg = success;
	    //     status = 1;
	    // }
	    );
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

	            // -------------------------测试
	            if (rootState.debug) {
	                seconedStep();
	                return;
	            }

	            if (res.data.isCreditSwich == '0') {
	                seconedStep();
	            } else {
	                Vue.NaviHelper.push('pop');
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    }

	    // var data =  {
	    //     creditApplyType : '1',
	    //     customerId : '1082678032',
	    //     educationDegree : 'bk',
	    //     identityNo : '21010619900606611X',
	    //     marryStatus : 'wh',
	    //     mobile : '18369160246',
	    //     realName : state.realName,
	    //     "x-auth-token" : "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0ZW5pbmcuand0Lmlzc3Vlcj86bGlzdGVuaW5nLWp3dC1pc3N1ZXIiLCJhdWQiOiJsaXN0ZW5pbmcuand0LmF1ZGllbmNlPzpsaXN0ZW5pbmctand0LWF1ZGllbmNlIiwiaWF0IjoxNTAwNDMwNDUxLCJleHAiOjE1MDEwMzUyNTEsImluZm8iOnsidXNlcklkIjoiODAwOTA2MSIsIm1vYmlsZSI6IjE4MzY5MTYwMjQ2In19.zpgI0Dxomn2LoTfhqp9dVhF5JfvnptjwvAZ9_8eL3cU",
	    // }

	    // result:
	    // {
	    //     data =     {
	    //         entity =         {
	    //         agent = app;
	    //     creditApplyType = 1;
	    //     educationDegree = bk;
	    //     id = 1082678032;
	    //     marryStatus = wh;
	    //     mobile = 18369160246;
	    //     realName = "\U91d1\U5b9d\U6cc9";
	    //     status = 0101;
	    // };
	    //     isCreditSwich = 0;
	    // };
	    //     msg = "\U4fdd\U5b58\U7528\U6237\U4fe1\U606f\U6210\U529f";
	    //     status = 1;
	    // }

	    );
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
	            // -------------------------测试


	            if (res.data.isCreditSwich == 0) {
	                thirdStep();
	            } else {
	                Vue.NaviHelper.push('pop');
	            }
	        } else {
	            Vue.TipHelper.showHub('1', res.msg);
	        }
	    }

	    // {
	    //     customerId = 1082678032;
	    //     industry = "\U6279\U53d1/\U96f6\U552e/\U8d38\U6613";
	    //     mobile = 18369160246;
	    //     monthlyIncomeCard = 6666;
	    //     monthlyIncomeCash = 6666;
	    //     payFundOrSecurity = 1;
	    //     profession = "\U4f01\U4e1a\U4e3b";
	    //     "x-auth-token" = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0ZW5pbmcuand0Lmlzc3Vlcj86bGlzdGVuaW5nLWp3dC1pc3N1ZXIiLCJhdWQiOiJsaXN0ZW5pbmcuand0LmF1ZGllbmNlPzpsaXN0ZW5pbmctand0LWF1ZGllbmNlIiwiaWF0IjoxNTAwNDUzODc1LCJleHAiOjE1MDEwNTg2NzUsImluZm8iOnsidXNlcklkIjoiODAwOTA2MSIsIm1vYmlsZSI6IjE4MzY5MTYwMjQ2In19.9H3ZZScgl36C8AMGuVsCVGgBVcpF86x8N3F33-TeQks";
	    // }

	    // {
	    //     data =     {
	    //         isCreditSwich = 0;
	    //     };
	    //     msg = "\U4fdd\U5b58\U4fe1\U606f\U8ba4\U8bc1\U6210\U529f";
	    //     status = 1;
	    // }
	    );
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
	    }

	    // {
	    //     data =     {
	    //         customer =         {
	    //         agent = ios;
	    //     createdDate = 1497577322000;
	    //     creditApplyType = 1;
	    //     educationDegree = bk;
	    //     id = 1082678032;
	    //     identityExpires = 20260429;
	    //     identityNo = 21010619900606611X;
	    //     identityPerson = 1;
	    //     identityRecognizeAddress = "\U6c88\U9633\U5e02\U94c1\U897f\U533a\U5357\U6ed1\U7fd4\U8def58-3\U53f71-1-1";
	    //     identityRecognizeName = "\U91d1\U5b9d\U6cc9";
	    //     localRegistry = 0;
	    //     marryStatus = wh;
	    //     mobile = 18369160246;
	    //     mobileAuthDate = 1500465526000;
	    //     mobileAuthFlag = auth;
	    //     mobileAuthIdentity = 21010619900606611X;
	    //     mobileAuthStatus = 1;
	    //     mobileRepeatApply = 0;
	    //     mobileReportID = "jxl_45164";
	    //     openId = 18369160246;
	    //     realName = "\U91d1\U5b9d\U6cc9";
	    //     remindRepayment = 1;
	    //     status = 0102;
	    //     updatedDate = 1498652648000;
	    // };
	    //     repeatApply = false;
	    // };
	    //     msg = success;
	    //     status = 1;
	    // }
	    );
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
	        link2Relation: "" //联系人关系
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
	        activeFlag: payload.activeFlag //额度激活标记 1:钱包激活 2:佣金分期激活
	    };
	    Vue.HttpHelper.post(Vue.UrlMacro.LINKMAN_SAVE, prama, function (res) {
	        Vue.TipHelper.dismisHub();
	        if (res.status == 1) {
	            payload.callback(res.data);
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
	        cutomerId: '', //客户Id

	        withdrawMoney: '1000', //申请提现金额 默认1000
	        availableAmount: '', //可提现金额
	        paymentType: '', //分几期

	        cardBank: '', //银行名称
	        bankCode: '', //银行code
	        cardNo: '', //银行卡号

	        bindCardBank: '', //新绑定的银行名称
	        bindCardNo: '', //新绑定的银行卡号
	        loanType: '' //贷款类型（0：首贷；2：再贷；3：提现中但未放款；4：不符合取现条件）
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
	exports.TRIALREPAYMENTINFO_INIT = TRIALREPAYMENTINFO_INIT;
	exports.BINDBANK_INIT = BINDBANK_INIT;
	// ���ֳ�ʼ��
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
	        if (res.status == 1) {
	            payload.callback(res.data);
	        } else {
	            Vue.TipHelper.showHub('1', res.msg
	            // ����Ǯ����ҳ
	            );
	        }
	    });
	}
	// ���ֳ�ʼ��
	function TRIALREPAYMENTINFO_INIT(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;

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

	// ����ҳ����ʼ������
	function BINDBANK_INIT(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;

	    Vue.TipHelper.showHub(0);
	    var prama = {
	        "x-auth-token": rootState.token,
	        "mobile": rootState.mobile,
	        "customerId": payload.customerId,
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
	            // 返回钱包首页
	            );if (res.data.status == '0' && res.data.msg.indexOf('佣金信息初始化失败') >= 0) {
	                Vue.NaviHelper.push('root', {}, '', function (r) {});
	            }
	        }
	    });
	}

	function LINKMAN_CREDIT_AUTH(_ref2, payload) {
	    var commit = _ref2.commit,
	        state = _ref2.state,
	        rootState = _ref2.rootState;

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
	function COMMISSION_JUDGE(_ref3, payload) {
	    var commit = _ref3.commit,
	        state = _ref3.state,
	        rootState = _ref3.rootState;

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
	function PROVID_CITY_INIT(_ref4, payload) {
	    var commit = _ref4.commit,
	        state = _ref4.state,
	        rootState = _ref4.rootState;

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
	function PROVID_INFO_INIT(_ref5, payload) {
	    var commit = _ref5.commit,
	        state = _ref5.state,
	        rootState = _ref5.rootState;

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
	function SECURITY_INFO_INIT(_ref6, payload) {
	    var commit = _ref6.commit,
	        state = _ref6.state,
	        rootState = _ref6.rootState;

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
	function SECURITY_CITY_INIT(_ref7, payload) {
	    var commit = _ref7.commit,
	        state = _ref7.state,
	        rootState = _ref7.rootState;

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
	function PROVID_VERIFICODE(_ref8, payload) {
	    var commit = _ref8.commit,
	        state = _ref8.state,
	        rootState = _ref8.rootState;

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
	function SECURITY_VERIFICODE(_ref9, payload) {
	    var commit = _ref9.commit,
	        state = _ref9.state,
	        rootState = _ref9.rootState;

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
	function PROVID_SUBMIT(_ref10, payload) {
	    var commit = _ref10.commit,
	        state = _ref10.state,
	        rootState = _ref10.rootState;

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
	                payload.fail(res);
	            } else {
	                if (res.code == '2005' || res.code == '2006' || res.code == '2007' || res.code == '2008') {
	                    Vue.TipHelper.showHub('1', res.msg);
	                    payload.replay(res);
	                }
	                eventModule.closePromotionQuota(); //关闭load
	            }
	        }, 1000);
	    });
	}

	// 社保查询 提交
	function SECURITY_SUBMIT(_ref11, payload) {
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
	    Vue.HttpHelper.post(Vue.UrlMacro.SECURITY_SUBMIT, prama, function (res) {
	        Vue.TipHelper.dismisHub();

	        setTimeout(function () {
	            if (res.code == '0000') {
	                payload.succ(res);
	            } else if (res.code == '2001' || res.code == '2009') {
	                payload.fail(res);
	            } else {
	                if (res.code == '2005' || res.code == '2006' || res.code == '2007' || res.code == '2008') {
	                    Vue.TipHelper.showHub('1', res.msg);
	                    payload.replay(res);
	                }
	                eventModule.closePromotionQuota(); //关闭load
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

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

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
	            // 手机号格式化
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
	}; /**
	    * Created by x298017064010 on 17/6/21.
	    */
	exports.default = Plugin;

/***/ }),
/* 31 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	/**
	 * Created by x298017064010 on 17/6/22.
	 */

	var PRD = true;

	var BASE_URL = '';
	if (PRD) {
	    BASE_URL = 'http://ajqh.app.apass.cn/appweb/data/ws/rest'; // PRD
	} else {
	    BASE_URL = 'http://gfbapp.vcash.cn/appweb/data/ws/rest'; // SIT & UAT
	}

	var BASE_WEEX = 'http://espapp.sit.apass.cn/appweb/v1/app_weex'; // 下载链接
	var H5_BASE_URL = "http://gfbapp.vcash.cn/"; // 网页链接

	var HOME_CHECKVERSION = "/checkVersion/app";
	var HOME_PAGE = "/home/index";

	var IDENTITY_INIT = "/customer/initIdentityInfo";
	var IDENTITY_SAVE = "/customer/saveBasicInfo";

	var INFOAUTH_INIT = "/customer/initInformationAuth";
	var INFOAUTH_SAVE = "/customer/saveInformationAuth";
	var INFOAUTH_ZMAUTH = "/zm/auth/query";

	var BANKCARD_INIT = "/customer/initInformationBank";
	var BANKCARD_SAVE = "/customer/saveInformationBank";

	var RESULT_LOAD_PRODUCT_LIST = '/reverse/loadProductList';

	var LINKMAN_SAVE = "/customer/saveContactInfo";
	var LINKMAN_CREDIT_AUTH = "/customer/creditAuthActiveApply";

	var COMMISSION_INIT = "/commission/initData";
	var COMMISSION_JUDGE = "/commission/judgeSubmit";
	var PROVID_CITY_INIT = "/accfund/fetchCitiesList";
	var PROVID_INFO_INIT = "/accfund/formSetting";
	var PROVID_VERIFICODE = "/accfund/loginInit";
	var SECURITY_VERIFICODE = "/sccFundSys/advanceToken";
	var PROVID_SUBMIT = "/accfund/loginAuth";
	var SECURITY_INFO_INIT = "/sccFundSys/advanceFormSettingByCity";
	var SECURITY_SUBMIT = "/sccFundSys/login";
	var SECURITY_CITY_INIT = "/sccFundSys/advanceFormSettingCites";
	var IDENTITY_RECOGNIZE = "/identity/recognize";
	var FACEPAIR_RECOGNICE = "/identity/facepairRecognice";
	var IDENTITY_DOWNLOAD = "/customer/download";

	var COMMISSION_UPLOAD_PROTOCL = "/commission/uploadProtocl";
	var COMMISSION_SELECT_ADDRESS = "/commission/selectAddress";
	var COMMISSION_SAVE_COMMISSIONINFO = "/commission/saveCommissionInfo";
	var COMMISSION_REFRESH_COMSSION = "/commission/refreshComssion";
	var COMMISSION_COMMCASH_INITDATA = "/commcash/initData";
	var COMMISSION_COMMCASH_TRIALREPAYMENTINFO = "/commcash/newTrialRepaymentInfo";
	var COMMISSION_COMMCASH_SAVESTREAM = "/commcash/saveStream";
	var COMMISSION_INIT_COMMISSIONINFO = "/commission/initCommissionInfo";
	var COMMISSION_COFIRM_MODIFY_COMSINFO = "/commission/cofirmModifyComsInfo";

	var WITHDRAW_INIT = "/cash/initData";
	var TRIALREPAYMENTINFO_INIT = "/cash/trialRepaymentInfo";
	var BINDBANK_INIT = "/card/initData";

	exports.default = {
	    H5_BASE_URL: H5_BASE_URL,
	    BASE_URL: BASE_URL, // 服务器ip
	    BASE_WEEX: BASE_WEEX, // weex下载链接 (不需要使用)

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
	    TRIALREPAYMENTINFO_INIT: TRIALREPAYMENTINFO_INIT, //点击借款期限，展示分期详情
	    BINDBANK_INIT: BINDBANK_INIT // 绑卡页面初始化数据
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

	    render: function render(url, title) {
	        console.log('render ' + url + ' success');
	        _index2.default.state.router.push(url);
	        eventModule.wxSetNavTitle(title, true);
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
	        // type(-1: 网络失败, 0: 加载中, 1: 文本提示)
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
	    checkCardNo: function checkCardNo(cardNo, isCreditCard) {
	        var isCardNoPass = true;
	        if (isNaN(cardNo) || cardNo.length != (isCreditCard == true ? 16 : 19)) {
	            isCardNoPass = false;
	        }
	        return isCardNoPass;
	    }
	}; /**
	    * Created by x298017064010 on 17/9/5.
	    */
	exports.default = checkHelper;

/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _vueRouter = __webpack_require__(39);

	var _vueRouter2 = _interopRequireDefault(_vueRouter);

	var _root = __webpack_require__(41);

	var _root2 = _interopRequireDefault(_root);

	var _errorNetPage = __webpack_require__(45);

	var _errorNetPage2 = _interopRequireDefault(_errorNetPage);

	var _banner = __webpack_require__(53);

	var _banner2 = _interopRequireDefault(_banner);

	var _commissionStage = __webpack_require__(57);

	var _commissionStage2 = _interopRequireDefault(_commissionStage);

	var _promoteLimit = __webpack_require__(65);

	var _promoteLimit2 = _interopRequireDefault(_promoteLimit);

	var _providentFund = __webpack_require__(73);

	var _providentFund2 = _interopRequireDefault(_providentFund);

	var _security = __webpack_require__(77);

	var _security2 = _interopRequireDefault(_security);

	var _selectProvince = __webpack_require__(81);

	var _selectProvince2 = _interopRequireDefault(_selectProvince);

	var _cashimmediately = __webpack_require__(85);

	var _cashimmediately2 = _interopRequireDefault(_cashimmediately);

	var _applicationstagecontract = __webpack_require__(121);

	var _applicationstagecontract2 = _interopRequireDefault(_applicationstagecontract);

	var _commissioninformation = __webpack_require__(125);

	var _commissioninformation2 = _interopRequireDefault(_commissioninformation);

	var _choosearea = __webpack_require__(141);

	var _choosearea2 = _interopRequireDefault(_choosearea);

	var _waitreview = __webpack_require__(145);

	var _waitreview2 = _interopRequireDefault(_waitreview);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	/**
	 * Created by x298017064010 on 17/6/12.
	 */

	Vue.use(_vueRouter2.default);

	exports.default = new _vueRouter2.default({
	    // mode: 'abstract',    // 不需要设置模式, 系统自动匹配
	    routes: [{ path: '/root', component: _root2.default }, { path: '/banner', component: _banner2.default }, { path: '/errorNetPage', component: _errorNetPage2.default },

	    // 佣金分期-1
	    { path: '/comStage', component: _commissionStage2.default }, { path: '/promoteLimit', component: _promoteLimit2.default }, { path: '/providentFund', component: _providentFund2.default }, { path: '/security', component: _security2.default }, { path: '/selectProv', component: _selectProvince2.default },

	    //佣金分期-2
	    { path: '/commissioninformation', component: _commissioninformation2.default }, { path: '/choosearea', component: _choosearea2.default }, { path: '/waitreview', component: _waitreview2.default }, { path: '/cashimmediately', component: _cashimmediately2.default }, { path: '/applicationstagecontract', component: _applicationstagecontract2.default }, { path: '/', redirect: '/root' }]
	});

/***/ }),
/* 39 */
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

	/* WEBPACK VAR INJECTION */}.call(exports, __webpack_require__(40)))

/***/ }),
/* 40 */
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
/* 41 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(42)
	)

	/* script */
	__vue_exports__ = __webpack_require__(43)

	/* template */
	var __vue_template__ = __webpack_require__(44)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/root.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-f8304662"
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
/* 42 */
/***/ (function(module, exports) {

	module.exports = {}

/***/ }),
/* 43 */
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
/* 44 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  })
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(46)
	)

	/* script */
	__vue_exports__ = __webpack_require__(47)

	/* template */
	var __vue_template__ = __webpack_require__(52)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/views/errorNetPage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-1ea32791"
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
/* 46 */
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
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

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
/* 48 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(49)
	)

	/* script */
	__vue_exports__ = __webpack_require__(50)

	/* template */
	var __vue_template__ = __webpack_require__(51)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/ccImage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-434f5f89"
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
/* 49 */
/***/ (function(module, exports) {

	module.exports = {}

/***/ }),
/* 50 */
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


	            console.log(this.src, e, this.have3xPNG);
	            console.log('w: ', naturalWidth);
	            console.log('h: ', naturalHeight

	            // 做适配
	            );if (platform == 'iOS' && this.have3xPNG) {
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
/* 51 */
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
/* 52 */
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/views/banner.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2488efd9"
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

	module.exports = {
	  "wrapper": {
	    "flex": 1
	  },
	  "scroller": {
	    "flex": 1
	  }
	}

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
/* 56 */
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
/* 57 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(58)
	)

	/* script */
	__vue_exports__ = __webpack_require__(59)

	/* template */
	var __vue_template__ = __webpack_require__(64)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commission/commissionStage.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-34b5435c"
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
/* 58 */
/***/ (function(module, exports) {

	module.exports = {
	  "WXCommissionCard": {
	    "width": 750,
	    "height": 700
	  },
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "scroller": {
	    "flex": 1
	  },
	  "stage_text": {
	    "alignItems": "center",
	    "marginTop": 64,
	    "height": 200
	  },
	  "stage_text_sub": {
	    "color": "#303030",
	    "fontSize": 30,
	    "marginBottom": 24
	  },
	  "stage_text_sum": {
	    "color": "#303030",
	    "fontSize": 48,
	    "marginBottom": 10
	  },
	  "stage_promote": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "stage_promote_text": {
	    "fontSize": 30,
	    "color": "#E2262A",
	    "marginRight": 20
	  },
	  "promote_icon": {
	    "width": 50,
	    "height": 50
	  },
	  "slider": {
	    "position": "relative",
	    "height": 700,
	    "borderWidth": 2,
	    "borderStyle": "solid",
	    "borderColor": "#41B883"
	  },
	  "slider_items": {
	    "width": 500,
	    "position": "relative",
	    "backgroundColor": "#FCE1E5",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderWidth": 1,
	    "borderStyle": "solid",
	    "borderColor": "#006400"
	  },
	  "indicator": {
	    "height": 20,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "itemColor": "green",
	    "itemSelectedColor": "red",
	    "itemSize": 10,
	    "position": "absolute",
	    "top": 500,
	    "left": 0,
	    "right": 0,
	    "backgroundColor": "#008800"
	  },
	  "botWrap": {
	    "marginLeft": 48,
	    "marginRight": 48,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "paddingBottom": 32
	  },
	  "btn": {
	    "marginLeft": 10,
	    "marginRight": 0,
	    "height": 90
	  },
	  "tip": {
	    "alignItems": "center"
	  },
	  "tip_text": {
	    "color": "#303030",
	    "fontSize": 24,
	    "lineHeight": 40
	  },
	  "tip_text_warn": {
	    "color": "#fa9d35",
	    "fontSize": 30
	  },
	  "tip_day": {
	    "flexDirection": "row"
	  }
	}

/***/ }),
/* 59 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _uiButton = __webpack_require__(60);

	var _uiButton2 = _interopRequireDefault(_uiButton);

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


	var dom = weex.requireModule('dom');
	var globalEvent = weex.requireModule('globalEvent');
	var eventModule = weex.requireModule('event');

	exports.default = {
	    components: { ccImage: _ccImage2.default, uiButton: _uiButton2.default },
	    data: function data() {
	        return {
	            state: "-1", //客户状态
	            stageSum: "", //可用额度
	            lockDays: "0", //锁单天数
	            judgeState: "0", //我要提交 交单规则不通过 拒绝状态
	            activeCreditFlag: "0", //是否可提升额度 0-不展示 1-展示
	            riskLevel: "1" // 高风险等级
	        };
	    },

	    methods: {
	        initComStage: function initComStage() {
	            //初始化页面信息
	            var that = this;

	            that.$store.dispatch('COMMISSION_INIT', { callback: function callback(res) {

	                    that.state = res.status; //客户状态
	                    that.stageSum = res.displayAmt; //可用额度
	                    that.lockDays = res.lockDays == null ? '0' : res.lockDays; //锁单天数
	                    that.activeCreditFlag = res.activeCreditFlag; //是否可提升额度
	                    that.riskLevel = res.riskLevel; //额度激活等级

	                    console.log('status: ', that.state, 'displayAmt: ', that.stageSum, 'lockDays:', that.lockDays, 'activeCreditFlag: ', that.activeCreditFlag, 'riskLevel: ', that.riskLevel

	                    /*   0:无额度
	                         1：有额度未激活
	                         2：额度激活中
	                         3 立即使用【未提交信息】
	                         4:含vbs贷款／交单规则不通过／使用过趣花额度
	                         5 展示锁单天数
	                         6 立即使用【佣金信息审核中】
	                         7佣金信息审核通过【未支付】
	                         8佣金信息审核通过【支付中断】
	                    */
	                    );
	                } });
	        },
	        sliderInit: function sliderInit(e) {
	            var slider = this.$refs.slider;
	            var initInfo = [{
	                imgurl: "ic_commisson_one",
	                title: "佣金分期是什么？",
	                desc: "是安家趣花和中原集团联袂打造的信贷产品，用于支付购房佣金，缓解资金压力。"
	            }, {
	                imgurl: "ic_commisson_three",
	                title: "佣金分期如何用？",
	                desc: "提交身份证、手机号等基本信息并提供在中原购房的相关材料，通过审核后即可使用。"
	            }, {
	                imgurl: "ic_commisson_two",
	                title: "佣金分期怎么还？",
	                desc: "您可在每月最迟还款日前至【我的账单】页面进行主动还款（具体还款金额以出账为准）。"
	            }];
	            slider.init(initInfo);
	        },
	        applyAmount: function applyAmount() {
	            //点击跳转至额度申请页面 /main/etakeproxy
	            // 测试
	            if (this.$store.state.debug) {
	                Vue.NaviHelper.push('weex/common?id=commission&path=router_commissioninformation', { title: '佣金信息' }, '', function (r) {});
	                return;
	            }
	            Vue.NaviHelper.push('native/main/etakeproxy', { title: '额度申请' }, '', function (r) {});
	        },
	        activeAmount: function activeAmount(e) {
	            //激活额度 添加联系人
	            var that = this;
	            if (this.riskLevel == 0) {
	                // 低风险
	                this.$store.dispatch('LINKMAN_CREDIT_AUTH', { callback: function callback(res) {
	                        // 提示弹窗
	                        Vue.TipHelper.showAlert('额度激活已提交', '客服人员将在24小时内联系您，\n请注意接听021-51349308的来电。', '', '确定', function () {
	                            // 刷新页面
	                            that.initComStage(); //初始化页面信息
	                        }, false);
	                    } });
	            } else {
	                Vue.NaviHelper.push('native/main/addContacts', { ActiveFlag: '2', title: '额度申请' }, '', function (r) {});
	            }
	        },
	        useAmount: function useAmount() {
	            //立即使用
	            var that = this;
	            // 测试
	            if (that.$store.state.debug) {
	                console.log(this.$store.state);
	                Vue.NaviHelper.push('weex/common?id=commission&path=router_commissioninformation', { title: '佣金信息' }, '', function (r) {});
	                return;
	            }

	            /*
	             点击时跳转至“佣金信息”页面
	             若审核中则跳转至等待审核页面
	             若审核通过则跳转至申请分期页面
	             */
	            that.$store.dispatch('COMMISSION_JUDGE', { callback: function callback(res) {
	                    //判断失败原因
	                    if (res.errorType == '0') {
	                        //0 佣金分期可交单


	                        if (that.state == '3') {
	                            //立即使用【未提交信息】  点击时跳转至“佣金信息”页面
	                            console.log('跳转至“佣金信息”页面');
	                            Vue.NaviHelper.push('weex/common?id=commission&path=router_commissioninformation', { title: '佣金信息' }, '', function (r) {});
	                        } else if (that.state == '6' || that.state == '501') {
	                            //立即使用【佣金信息审核中】
	                            console.log('跳转至等待审核页面');
	                            Vue.NaviHelper.push('weex/common?id=commission&path=router_waitreview', { title: '等待审核', status: that.state }, '', function (r) {});
	                        } else if (that.state == '7') {
	                            //佣金信息审核通过【未支付】
	                            console.log('跳转至申请分期页面');
	                            Vue.NaviHelper.push('weex/common?id=commission&path=router_cashimmediately', { title: '申请分期' }, '', function (r) {});
	                        }
	                    } else if (res.errorType == '1' || res.errorType == '2') {
	                        //1 交单规则不通过; 2 含vbs订单或额度消费
	                        that.judgeState = res.errorType;
	                    }
	                } });
	        },
	        scanCode: function scanCode() {
	            //扫码付佣金
	            Vue.NaviHelper.push('native/main/scan', {
	                showTab: false,
	                scanTitle: "扫码付佣金",
	                needHelp: true,
	                desc: "请将中原地产提供的二维码放入扫描框内",
	                scanHandlerRoute: "/main/commissionscan"
	            }, '', function (r) {});
	        },
	        toPromote: function toPromote() {
	            //跳转至提额方式
	            Vue.NaviHelper.push('weex/common?id=commission&path=router_promoteLimit', { title: '提额方式' }, '', function (r) {});
	        }
	    },
	    created: function created() {},

	    computed: {},
	    ready: function ready() {},
	    mounted: function mounted() {
	        var that = this;
	        var slider = this.$refs.slider;

	        this.initComStage(); //初始化页面信息
	        this.sliderInit();

	        eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	        //佣金分期 页面 返回，都返回到app
	        globalEvent.addEventListener('WXback', function (e) {
	            Vue.NaviHelper.push('root'); //杀掉进程，返回到app钱包
	            var platform = that.$store.state.env.platform;

	            if (platform == 'iOS') {
	                slider.remveCardView();
	            }
	        });

	        globalEvent.addEventListener('WXBackCommission', function (e) {
	            that.initComStage(); //初始化页面信息
	            that.sliderInit();
	        });
	    },
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXback');
	    }
	};

/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(61)
	)

	/* script */
	__vue_exports__ = __webpack_require__(62)

	/* template */
	var __vue_template__ = __webpack_require__(63)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/wl/uiButton.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-db35c15a"
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
/* 61 */
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
/* 62 */
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
/* 63 */
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
/* 64 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["stage_text"]
	  }, [(_vm.state == '-1') ? _c('text', {
	    staticClass: ["stage_text_sub"]
	  }) : (_vm.state == '4' || _vm.state == '5' || _vm.state == '0') ? _c('text', {
	    staticClass: ["stage_text_sub"]
	  }, [_vm._v("中原地产佣金分期最高额度")]) : _c('text', {
	    staticClass: ["stage_text_sub"]
	  }, [_vm._v("中原地产佣金分期可用额度")]), (_vm.state == '-1') ? _c('text', {
	    staticClass: ["stage_text_sum"]
	  }, [_vm._v(_vm._s(_vm._f("thousandSeparator")(0)))]) : (_vm.state == '4' || _vm.state == '5' || _vm.state == '0') ? _c('text', {
	    staticClass: ["stage_text_sum"]
	  }, [_vm._v(_vm._s(_vm._f("thousandSeparator")(300000)))]) : _c('text', {
	    staticClass: ["stage_text_sum"]
	  }, [_vm._v(_vm._s(_vm._f("thousandSeparator")(_vm.stageSum)))]), (_vm.activeCreditFlag == '1') ? _c('div', {
	    staticClass: ["stage_promote"]
	  }, [_c('text', {
	    staticClass: ["stage_promote_text"],
	    on: {
	      "click": _vm.toPromote
	    }
	  }, [_vm._v("额度不够？立即提额")]), _c('ccImage', {
	    staticClass: ["promote_icon"],
	    attrs: {
	      "src": 'WXLocal/icon_commission_rmb'
	    }
	  })], 1) : _vm._e()]), _c('WXCommissionCard', {
	    ref: "slider",
	    staticClass: ["WXCommissionCard"]
	  })], 1), _c('div', {
	    staticClass: ["botWrap"]
	  }, [(_vm.state == '0') ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '我要额度',
	      "onClick": _vm.applyAmount
	    }
	  }) : _vm._e(), (_vm.state == '1') ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '激活额度',
	      "onClick": _vm.activeAmount
	    }
	  }) : _vm._e(), (_vm.state == '2') ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '激活中',
	      "flag": 'disabled'
	    }
	  }) : _vm._e(), ((_vm.state == '3' || _vm.state == '6' || _vm.state == '7' || _vm.state == '501') && _vm.judgeState == '0' && _vm.lockDays <= 0) ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '立即使用',
	      "onClick": _vm.useAmount
	    }
	  }) : _vm._e(), (_vm.state == '8') ? _c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '扫码付佣金',
	      "onClick": _vm.scanCode
	    }
	  }) : _vm._e(), (_vm.state == '4' || _vm.judgeState == '2') ? _c('div', {
	    staticClass: ["tip"]
	  }, [_c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("您含有未结清贷款，暂不提供服务")]), _c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("需结清后才可以使用佣金分期哦")])]) : _vm._e(), (_vm.judgeState == '1' || _vm.state == '301') ? _c('div', {
	    staticClass: ["tip"]
	  }, [_c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("根据您的实际情况，将不提供佣金分期服务,")]), _c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("感谢您的理解。")])]) : _vm._e(), (_vm.state == '5') ? _c('div', {
	    staticClass: ["tip"]
	  }, [_c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("抱歉暂时无法为您提供佣金分期服务，")]), _c('div', {
	    staticClass: ["tip_day"]
	  }, [_c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("请")]), _c('text', {
	    staticClass: ["tip_text_warn"]
	  }, [_vm._v(_vm._s(_vm.lockDays))]), _c('text', {
	    staticClass: ["tip_text"]
	  }, [_vm._v("天后重试")])])]) : _vm._e()], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
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
	var __vue_template__ = __webpack_require__(72)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commission/promoteLimit.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-3de9e5bc"
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
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "scroller": {
	    "flex": 1,
	    "paddingTop": 22
	  }
	}

/***/ }),
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccChose = __webpack_require__(68);

	var _ccChose2 = _interopRequireDefault(_ccChose);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	var dom = weex.requireModule('dom');

	exports.default = {
	    components: { ccImage: _ccImage2.default, ccChose: _ccChose2.default },
	    data: function data() {
	        return {
	            location: [],
	            unArr: [],
	            isSelect: 'A'
	        };
	    },

	    methods: {
	        toProvidentFund: function toProvidentFund() {
	            //跳转至公积金页面
	            Vue.NaviHelper.push('weex/common?id=commission&path=router_providentFund', { title: '公积金提额' }, '', function (r) {});
	        },
	        toSecurity: function toSecurity() {
	            //跳转至 社保
	            Vue.NaviHelper.push('weex/common?id=commission&path=router_security', { title: '社保提额' }, '', function (r) {});
	        }

	    },
	    created: function created() {},

	    computed: {},
	    ready: function ready() {},
	    mounted: function mounted() {},

	    filters: {},
	    destroyed: function destroyed() {}
	};

/***/ }),
/* 68 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(69)
	)

	/* script */
	__vue_exports__ = __webpack_require__(70)

	/* template */
	var __vue_template__ = __webpack_require__(71)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/wl/ccChose.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-796175c2"
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
/* 69 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF"
	  },
	  "channel_wrap": {
	    "position": "relative",
	    "height": 240,
	    "paddingLeft": 24,
	    "paddingRight": 24
	  },
	  "channel": {
	    "marginTop": 15,
	    "height": 210,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "paddingLeft": 32,
	    "paddingRight": 32
	  },
	  "channel_icon": {
	    "marginRight": 32
	  },
	  "area_right": {
	    "flex": 1
	  },
	  "area_city": {
	    "fontSize": 30,
	    "color": "#303030"
	  },
	  "area_tip": {
	    "marginTop": 20,
	    "fontSize": 24,
	    "color": "#7E7E7E",
	    "lineHeight": 35
	  },
	  "channel_iconHref": {
	    "width": 15,
	    "height": 26,
	    "marginLeft": 20
	  },
	  "channel_bg": {
	    "position": "absolute",
	    "left": 1,
	    "right": 1,
	    "top": 0,
	    "width": 748,
	    "height": 240
	  }
	}

/***/ }),
/* 70 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        // 显示主题文本
	        title: {
	            type: String,
	            default: ''
	        },
	        des: {
	            type: String,
	            default: ''
	        },
	        //icon
	        src: {
	            type: String,
	            default: ''
	        },
	        // 监听cell点击事件
	        onClick: {
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

/***/ }),
/* 71 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["channel_wrap"]
	  }, [_c('ccImage', {
	    staticClass: ["channel_bg"],
	    attrs: {
	      "src": 'WXLocal/wx_bind_bank_card'
	    }
	  }), _c('div', {
	    staticClass: ["channel"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [_c('ccImage', {
	    staticClass: ["channel_icon"],
	    attrs: {
	      "src": _vm.src
	    }
	  }), _c('div', {
	    staticClass: ["area_right"]
	  }, [_c('text', {
	    staticClass: ["area_city"]
	  }, [_vm._v(_vm._s(_vm.title))]), (this.des) ? _c('text', {
	    staticClass: ["area_tip"]
	  }, [_vm._v(_vm._s(_vm.des))]) : _vm._e()]), _c('ccImage', {
	    staticClass: ["channel_iconHref"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1)], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 72 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    ref: "box",
	    staticClass: ["scroller"],
	    on: {
	      "scroll": _vm.scrollChange
	    }
	  }, [_c('ccChose', {
	    attrs: {
	      "title": '我有公积金',
	      "onClick": _vm.toProvidentFund,
	      "src": 'WXLocal/wx_commission_provind'
	    }
	  }), _c('ccChose', {
	    attrs: {
	      "title": '我有社保',
	      "onClick": _vm.toSecurity,
	      "src": 'WXLocal/wx_commission_security'
	    }
	  })], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 73 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(74)
	)

	/* script */
	__vue_exports__ = __webpack_require__(75)

	/* template */
	var __vue_template__ = __webpack_require__(76)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commission/providentFund.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-e07fcc56"
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
/* 74 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "scroller": {
	    "flex": 1,
	    "paddingTop": 22,
	    "justifyContent": "space-between"
	  },
	  "form_list": {
	    "height": 88,
	    "marginLeft": 24,
	    "paddingRight": 24,
	    "lineHeight": 88,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "space-between",
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#dfdfdf",
	    "borderBottomWidth": 1
	  },
	  "form_list_input": {
	    "height": 42,
	    "lineHeight": 42,
	    "color": "#303030",
	    "fontSize": 30,
	    "placeholderColor": "#c5c5c5"
	  },
	  "flex1": {
	    "flex": 1
	  },
	  "regImg": {
	    "width": 120,
	    "height": 65,
	    "backgroundColor": "#cccccc"
	  },
	  "botWrap": {
	    "marginLeft": 50,
	    "marginRight": 50,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "paddingBottom": 32,
	    "paddingTop": 50
	  },
	  "btn": {
	    "marginLeft": 0,
	    "marginRight": 0,
	    "marginBottom": 20
	  },
	  "mt20": {
	    "marginTop": 20
	  }
	}

/***/ }),
/* 75 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _uiButton = __webpack_require__(60);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccChose = __webpack_require__(68);

	var _ccChose2 = _interopRequireDefault(_ccChose);

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

	var dom = weex.requireModule('dom');
	exports.default = {
	    components: { uiButton: _uiButton2.default, ccImage: _ccImage2.default, ccChose: _ccChose2.default },
	    data: function data() {
	        return {
	            formSet: [], //不同城市表单info
	            ID: '',
	            regImg: '',
	            loadShowBtn: false
	        };
	    },

	    methods: {
	        info_init: function info_init() {
	            //不同城市 需输入选项不一样
	            var that = this;

	            that.$store.dispatch('PROVID_INFO_INIT', {
	                callback: function callback(res) {
	                    that.formSet = res;
	                    that.loadShowBtn = true;
	                }
	            });
	            eventModule.getBaseInfo(function (res) {
	                console.log('test~~~~~~~~~~~~~~', res);
	                that.$store.state.commsWL.realName = res.result.realName;
	                that.ID = that.$store.state.commsWL.identitycard = res.result.idCardNum;
	            });
	        },
	        vercode_init: function vercode_init() {
	            //获取验证码
	            var that = this;
	            that.regImg = '';
	            that.$store.dispatch('PROVID_VERIFICODE', {
	                callback: function callback(res) {
	                    that.regImg = 'WXBase64/' + res.vercode;
	                    that.$store.state.commsWL.token = res.token;
	                    that.$store.state.commsWL.base64 = 'WXBase64/' + res.vercode;
	                }
	            });
	        },
	        selectCity: function selectCity() {
	            //点击选择地区
	            var that = this;
	            if (!that.$store.state.commsWL.location) {
	                //城市信息未获取
	                that.$store.dispatch('PROVID_CITY_INIT', {
	                    callback: function callback(res) {
	                        that.selectCityJump();
	                    }
	                });
	            } else {
	                that.selectCityJump();
	            }
	        },
	        selectCityJump: function selectCityJump() {
	            //跳转页面
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=commission&path=router_selectProv', {
	                title: '公积金提额',
	                location: that.$store.state.commsWL.location,
	                Source: 'provid'
	            }, 'provid_selectCity', function (r) {
	                that.$store.state.commsWL.username = '';
	                that.$store.state.commsWL.password = '';
	                that.$store.state.commsWL.vercode = '';
	                that.$store.state.commsWL.cityName = r.CityName;
	                that.$store.state.commsWL.cityCode = r.CityCode;
	                that.info_init();
	                that.vercode_init();
	            });
	        },
	        regUser: function regUser(e) {
	            //用户名 输入
	            this.$store.state.commsWL.username = e.value;
	        },
	        regPass: function regPass(e) {
	            //用户密码 输入
	            this.$store.state.commsWL.password = e.value;
	        },
	        regCode: function regCode(e) {
	            //用户验证码 输入
	            this.$store.state.commsWL.vercode = e.value;
	        },
	        toSubmit: function toSubmit() {
	            if (this.isClick) {
	                var that = this;
	                //that.providSuc = true;
	                that.$store.dispatch('PROVID_SUBMIT', {
	                    succ: function succ(res) {
	                        //提额成功
	                        var advance = thousandSeparator(res.advance);
	                        eventModule.showPromotionQuota(1, advance, function () {
	                            that.toCommission();
	                        });
	                        //res.advance;//提额
	                    }, fail: function fail(res) {
	                        //请求处理失败
	                        eventModule.showPromotionQuota(2, '', function () {
	                            that.toCommission();
	                        });
	                    }, replay: function replay(res) {
	                        //提示用户重新输入
	                        that.reflashPage(res);
	                    }
	                });
	            }
	        },
	        reflashPage: function reflashPage(res) {
	            //请求出错, 刷新页面
	            var that = this;
	            if (res.code != '2006') {
	                that.$store.state.commsWL.username = '';
	                that.$store.state.commsWL.password = '';
	            }
	            that.$store.state.commsWL.vercode = '';
	            that.vercode_init();
	        },

	        toCommission: function toCommission() {
	            Vue.NaviHelper.render('comStage', '佣金分期');
	        }

	    },
	    created: function created() {},

	    computed: {
	        isClick: function isClick() {
	            //判断按钮是否可点击，只校验选项不为空即可
	            var that = this;
	            return that.info.every(function (e) {
	                return that.$store.state.commsWL[e] != '';
	            });
	        },
	        info: function info() {
	            //不通城市返回的表单列表信息
	            var that = this;
	            var arr = [];
	            that.formSet.forEach(function (e, i) {
	                var code = e.ParameterCode;
	                if (code == 'username' || code == 'password' || code == 'vercode') {
	                    arr.push(code);
	                }
	            });
	            return arr;
	        },
	        regSrc: function regSrc() {
	            return this.regImg;
	        },
	        username: function username() {
	            return this.$store.state.commsWL.username;
	        },
	        password: function password() {
	            return this.$store.state.commsWL.password;
	        },
	        vercode: function vercode() {
	            return this.$store.state.commsWL.vercode;
	        }
	    },
	    ready: function ready() {},
	    mounted: function mounted() {
	        var that = this;
	        var localData = this.$getConfig().localData;

	        if (localData.CityName) this.$store.state.commsWL.cityName = localData.CityName;
	        if (localData.CityCode) this.$store.state.commsWL.cityCode = localData.CityCode;

	        that.info_init(); //不同城市所需要数据信息
	        that.vercode_init();
	    },

	    filters: {},
	    destroyed: function destroyed() {}
	};

	function thousandSeparator(num) {
	    //数值千分位
	    num = String(Number(num).toFixed(2));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}

/***/ }),
/* 76 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"],
	    style: {
	      height: this.$store.state.screenHeight
	    }
	  }, [_c('scroller', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["content"]
	  }, [_c('ccChose', {
	    attrs: {
	      "title": this.$store.state.commsWL.cityName + '公积金',
	      "onClick": _vm.selectCity,
	      "src": 'WXLocal/wx_commission_provind',
	      "des": '本页面信息仅作为数据授权，平台将遵守保密原则'
	    }
	  }), _c('div', {
	    staticClass: ["form_list", "mt20"]
	  }, [_c('text', {
	    staticClass: ["form_list_input"]
	  }, [_vm._v(_vm._s(_vm._f("realNameFormat")(this.$store.state.commsWL.realName)))]), _c('text', {
	    staticClass: ["form_list_input"]
	  }, [_vm._v(_vm._s(_vm._f("identityFormat")(this.$store.state.commsWL.identitycard)))])]), _vm._l((_vm.info), function(inf) {
	    return _c('div', [(inf == 'username') ? _c('div', {
	      staticClass: ["form_list"]
	    }, [_c('input', {
	      staticClass: ["form_list_input", "flex1"],
	      attrs: {
	        "type": "text",
	        "placeholder": "请输入用户名",
	        "value": (_vm.username)
	      },
	      on: {
	        "input": [function($event) {
	          _vm.username = $event.target.attr.value
	        }, _vm.regUser]
	      }
	    })]) : _vm._e(), (inf == 'password') ? _c('div', {
	      staticClass: ["form_list"]
	    }, [_c('input', {
	      staticClass: ["form_list_input", "flex1"],
	      attrs: {
	        "type": "password",
	        "placeholder": "请输入密码",
	        "value": (_vm.password)
	      },
	      on: {
	        "input": [function($event) {
	          _vm.password = $event.target.attr.value
	        }, _vm.regPass]
	      }
	    })]) : _vm._e(), (inf == 'vercode') ? _c('div', {
	      staticClass: ["form_list"]
	    }, [_c('input', {
	      staticClass: ["form_list_input", "flex1"],
	      attrs: {
	        "type": "text",
	        "placeholder": "请输入验证码",
	        "value": (_vm.vercode)
	      },
	      on: {
	        "input": [function($event) {
	          _vm.vercode = $event.target.attr.value
	        }, _vm.regCode]
	      }
	    }), _c('image', {
	      staticClass: ["regImg"],
	      attrs: {
	        "src": _vm.regImg
	      },
	      on: {
	        "click": _vm.vercode_init
	      }
	    })]) : _vm._e()])
	  })], 2), _c('div', {
	    staticClass: ["fixDiv"],
	    style: {
	      height: _vm.info.length == 3 ? 400 : 500
	    }
	  }), (_vm.loadShowBtn) ? _c('div', {
	    staticClass: ["botWrap"]
	  }, [_c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.toSubmit,
	      "flag": _vm.isClick ? 'active' : 'disabled'
	    }
	  })], 1) : _vm._e()])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 77 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(78)
	)

	/* script */
	__vue_exports__ = __webpack_require__(79)

	/* template */
	var __vue_template__ = __webpack_require__(80)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commission/security.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-446c5587"
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
/* 78 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "scroller": {
	    "flex": 1,
	    "paddingTop": 22,
	    "justifyContent": "space-between"
	  },
	  "form_list": {
	    "height": 88,
	    "marginLeft": 24,
	    "paddingRight": 24,
	    "lineHeight": 88,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "space-between",
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#dfdfdf",
	    "borderBottomWidth": 1
	  },
	  "form_list_input": {
	    "height": 42,
	    "lineHeight": 42,
	    "color": "#303030",
	    "fontSize": 30,
	    "placeholderColor": "#c5c5c5"
	  },
	  "flex1": {
	    "flex": 1
	  },
	  "regImg": {
	    "width": 120,
	    "height": 65,
	    "backgroundColor": "#cccccc"
	  },
	  "botWrap": {
	    "marginLeft": 50,
	    "marginRight": 50,
	    "justifyContent": "flex-end",
	    "alignItems": "stretch",
	    "paddingBottom": 32,
	    "paddingTop": 50
	  },
	  "btn": {
	    "marginLeft": 0,
	    "marginRight": 0,
	    "marginBottom": 20
	  }
	}

/***/ }),
/* 79 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _uiButton = __webpack_require__(60);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccChose = __webpack_require__(68);

	var _ccChose2 = _interopRequireDefault(_ccChose);

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

	var dom = weex.requireModule('dom');

	exports.default = {
	    components: { uiButton: _uiButton2.default, ccImage: _ccImage2.default, ccChose: _ccChose2.default },
	    data: function data() {
	        return {
	            formSet: [], //不同城市表单info
	            ID: '',
	            regImg: '',
	            loadShowBtn: false
	        };
	    },

	    methods: {
	        loadMore: function loadMore(e) {
	            console.log('loadMore:', e);
	        },
	        info_init: function info_init() {
	            //不同城市 需输入选项不一样
	            var that = this;
	            that.$store.dispatch('SECURITY_INFO_INIT', {
	                callback: function callback(res) {
	                    //                        console.log('111111111111', res)
	                    that.formSet = res;
	                    that.loadShowBtn = true;
	                }
	            });
	            eventModule.getBaseInfo(function (res) {
	                that.$store.state.commsWL.realName = res.result.realName;
	                that.ID = that.$store.state.commsWL.identitycard = res.result.idCardNum;
	            });
	        },
	        vercode_init: function vercode_init() {
	            //获取验证码
	            var that = this;
	            that.regImg = '';
	            that.$store.dispatch('SECURITY_VERIFICODE', {
	                callback: function callback(res) {
	                    that.regImg = 'WXBase64/' + res.vercode;
	                    that.$store.state.commsWL.token = res.token;
	                    that.$store.state.commsWL.base64 = 'WXBase64/' + res.vercode;
	                }
	            });
	        },
	        selectCity: function selectCity() {
	            //点击选择地区
	            var that = this;
	            if (!that.$store.state.commsWL.location) {
	                //城市信息未获取
	                that.$store.dispatch('SECURITY_CITY_INIT', {
	                    callback: function callback(res) {
	                        that.selectCityJump();
	                    }
	                });
	            } else {
	                that.selectCityJump();
	            }
	        },
	        selectCityJump: function selectCityJump() {
	            //跳转页面
	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=commission&path=router_selectProv', {
	                title: '社保提额',
	                location: that.$store.state.commsWL.location,
	                Source: 'security'
	            }, 'security_selectCity', function (r) {
	                that.$store.state.commsWL.username = '';
	                that.$store.state.commsWL.password = '';
	                that.$store.state.commsWL.vercode = '';
	                that.$store.state.commsWL.cityName = r.CityName;
	                that.$store.state.commsWL.cityCode = r.CityCode;
	                that.info_init();
	                that.vercode_init();
	            });
	        },
	        regUser: function regUser(e) {
	            //用户名 输入
	            this.$store.state.commsWL.username = e.value;
	        },
	        regPass: function regPass(e) {
	            //用户密码 输入
	            this.$store.state.commsWL.password = e.value;
	        },
	        regCode: function regCode(e) {
	            //用户验证码 输入
	            this.$store.state.commsWL.vercode = e.value;
	        },
	        toSubmit: function toSubmit() {
	            if (this.isClick) {
	                var that = this;
	                //that.providSuc = true;
	                that.$store.dispatch('SECURITY_SUBMIT', {
	                    succ: function succ(res) {
	                        //提额成功
	                        var advance = thousandSeparator(res.advance);
	                        eventModule.showPromotionQuota(1, advance, function () {
	                            that.toCommission();
	                        });
	                        //res.advance;//提额
	                    }, fail: function fail(res) {
	                        //请求处理失败
	                        eventModule.showPromotionQuota(2, '', function () {
	                            that.toCommission();
	                        });
	                    }, replay: function replay(res) {
	                        //提示用户重新输入
	                        that.reflashPage(res);
	                    }
	                });
	            }
	        },
	        reflashPage: function reflashPage(res) {
	            //请求出错, 刷新页面
	            var that = this;
	            if (res.code != '2006') {
	                that.$store.state.commsWL.username = '';
	                that.$store.state.commsWL.password = '';
	            }
	            that.$store.state.commsWL.vercode = '';
	            that.vercode_init();
	        },

	        toCommission: function toCommission() {
	            Vue.NaviHelper.render('comStage', '佣金分期');
	        }

	    },
	    created: function created() {},

	    computed: {
	        isClick: function isClick() {
	            //判断按钮是否可点击，只校验选项不为空即可
	            var that = this;
	            console.log('isClick', that.$store.state.commsWL
	            //                cityCode
	            //                realName
	            //                identitycard
	            //                username
	            //                password
	            //                vercode
	            );return that.info.every(function (e) {
	                return that.$store.state.commsWL[e] != '';
	            });
	        },
	        info: function info() {
	            //不通城市返回的表单列表信息
	            var that = this;
	            var arr = [];
	            that.formSet.forEach(function (e, i) {
	                var code = e.ParameterCode;

	                if (code == 'username' || code == 'password' || code == 'vercode') {
	                    arr.push(code);
	                }
	            });
	            return arr;
	        },
	        regSrc: function regSrc() {
	            return this.regImg;
	        },
	        username: function username() {
	            return this.$store.state.commsWL.username;
	        },
	        password: function password() {
	            return this.$store.state.commsWL.password;
	        },
	        vercode: function vercode() {
	            return this.$store.state.commsWL.vercode;
	        }
	    },
	    ready: function ready() {},
	    mounted: function mounted() {
	        var that = this;
	        var localData = this.$getConfig().localData;

	        if (localData.CityName) this.$store.state.commsWL.cityName = localData.CityName;
	        if (localData.CityCode) this.$store.state.commsWL.cityCode = localData.CityCode;

	        that.info_init(); //不同城市所需要数据信息
	        that.vercode_init();
	    },
	    destroyed: function destroyed() {}
	};

	function thousandSeparator(num) {
	    //数值千分位
	    num = String(Number(num).toFixed(2));
	    var re = /(-?\d+)(\d{3})/;
	    while (re.test(num)) {
	        num = num.replace(re, "$1,$2");
	    }
	    return num;
	}

/***/ }),
/* 80 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"],
	    style: {
	      height: this.$store.state.screenHeight
	    }
	  }, [_c('scroller', {
	    staticClass: ["scroller"]
	  }, [_c('div', {
	    staticClass: ["content"]
	  }, [_c('ccChose', {
	    attrs: {
	      "title": this.$store.state.commsWL.cityName + '社保中心',
	      "onClick": _vm.selectCity,
	      "src": 'WXLocal/wx_commission_security',
	      "des": '本页面信息仅作为数据授权，平台将遵守保密原则'
	    }
	  }), _c('div', {
	    staticClass: ["form_list"]
	  }, [_c('text', {
	    staticClass: ["form_list_input"]
	  }, [_vm._v(_vm._s(_vm._f("realNameFormat")(this.$store.state.commsWL.realName)))]), _c('text', {
	    staticClass: ["form_list_input"]
	  }, [_vm._v(_vm._s(_vm._f("identityFormat")(this.$store.state.commsWL.identitycard)))])]), _vm._l((_vm.info), function(inf) {
	    return _c('div', [(inf == 'username') ? _c('div', {
	      staticClass: ["form_list"]
	    }, [_c('input', {
	      staticClass: ["form_list_input", "flex1"],
	      attrs: {
	        "type": "text",
	        "placeholder": "请输入用户名",
	        "value": (_vm.username)
	      },
	      on: {
	        "input": [function($event) {
	          _vm.username = $event.target.attr.value
	        }, _vm.regUser]
	      }
	    })]) : _vm._e(), (inf == 'password') ? _c('div', {
	      staticClass: ["form_list"]
	    }, [_c('input', {
	      staticClass: ["form_list_input", "flex1"],
	      attrs: {
	        "type": "password",
	        "placeholder": "请输入密码",
	        "value": (_vm.password)
	      },
	      on: {
	        "input": [function($event) {
	          _vm.password = $event.target.attr.value
	        }, _vm.regPass]
	      }
	    })]) : _vm._e(), (inf == 'vercode') ? _c('div', {
	      staticClass: ["form_list"]
	    }, [_c('input', {
	      staticClass: ["form_list_input", "flex1"],
	      attrs: {
	        "type": "text",
	        "placeholder": "请输入验证码",
	        "value": (_vm.vercode)
	      },
	      on: {
	        "input": [function($event) {
	          _vm.vercode = $event.target.attr.value
	        }, _vm.regCode]
	      }
	    }), _c('image', {
	      staticClass: ["regImg"],
	      attrs: {
	        "src": _vm.regImg
	      },
	      on: {
	        "click": _vm.vercode_init
	      }
	    })]) : _vm._e()])
	  })], 2), _c('div', {
	    staticClass: ["fixDiv"],
	    style: {
	      height: _vm.info.length == 3 ? 400 : 500
	    }
	  }), (_vm.loadShowBtn) ? _c('div', {
	    staticClass: ["botWrap"]
	  }, [_c('uiButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '提交',
	      "onClick": _vm.toSubmit,
	      "flag": _vm.isClick ? 'active' : 'disabled'
	    }
	  })], 1) : _vm._e()])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 81 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(82)
	)

	/* script */
	__vue_exports__ = __webpack_require__(83)

	/* template */
	var __vue_template__ = __webpack_require__(84)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commission/selectProvince.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-7a17c153"
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
/* 82 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "scroller": {
	    "flex": 1,
	    "backgroundColor": "#ffffff"
	  },
	  "label": {
	    "fontSize": 32,
	    "color": "#313131",
	    "paddingLeft": 40,
	    "height": 64,
	    "lineHeight": 64,
	    "backgroundColor": "#eeeeee"
	  },
	  "items_city_wrap": {
	    "paddingLeft": 40,
	    "backgroundColor": "#ffffff"
	  },
	  "items_city": {
	    "height": 86,
	    "lineHeight": 86
	  },
	  "items": {
	    "height": 86,
	    "lineHeight": 86,
	    "paddingLeft": 40,
	    "marginLeft": 40,
	    "backgroundColor": "#ffffff"
	  },
	  "items_bt": {
	    "height": 86,
	    "lineHeight": 86,
	    "paddingLeft": 40,
	    "marginLeft": 40,
	    "borderStyle": "solid",
	    "borderColor": "#b6b6b6",
	    "borderTopWidth": 1
	  },
	  "nav_wrap": {
	    "position": "fixed",
	    "top": 0,
	    "bottom": 0,
	    "right": 40,
	    "width": 36,
	    "alignItems": "center",
	    "justifyContent": "center"
	  },
	  "nav": {
	    "backgroundColor": "#dcdcdc",
	    "borderRadius": 17,
	    "textAlign": "center",
	    "paddingTop": 8,
	    "paddingBottom": 8,
	    "color": "#4f4f4f"
	  },
	  "nav_letter": {
	    "width": 36,
	    "textAlign": "center",
	    "fontSize": 34,
	    "marginTop": 4,
	    "marginBottom": 4,
	    "color": "#4f4f4f",
	    "color:active": "#aa0000"
	  },
	  "nav_letter_active": {
	    "width": 36,
	    "textAlign": "center",
	    "fontSize": 34,
	    "marginTop": 4,
	    "marginBottom": 4,
	    "color": "#aa0000"
	  },
	  "active": {
	    "color": "#aa0000"
	  },
	  "bt": {
	    "borderStyle": "solid",
	    "borderColor": "#b6b6b6",
	    "borderTopWidth": 1
	  }
	}

/***/ }),
/* 83 */
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

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	var globalEvent = weex.requireModule('globalEvent');
	var dom = weex.requireModule('dom');
	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            location: [],
	            city: [],
	            unArr: [],
	            isSelect: 'A',
	            showProvince: true,
	            Source: ''
	        };
	    },

	    methods: {
	        init: function init() {
	            var that = this;
	            var localData = this.$getConfig().localData;
	            that.Source = localData.Source;
	            that.location = JSON.parse(localData.location).sort(function (a, b) {
	                return a.ProvinceCode.toLowerCase() > b.ProvinceCode.toLowerCase() ? 1 : -1;
	            });
	            that.location.forEach(function (e, i) {
	                var letter = e.ProvinceCode.substr(0, 1).toUpperCase();
	                e.flag = that.unArr.indexOf(letter) == -1 ? that.unArr.push(letter) && letter : '';
	            });
	        },
	        goto: function goto(e) {
	            this.isSelect = e;
	            var el = this.$refs['item' + e][0];
	            dom.scrollToElement(el, {});
	        },

	        scrollChange: function scrollChange(e) {
	            var that = this;
	            this.offsetX = e.contentOffset.x;
	            this.offsetY = e.contentOffset.y;
	            that.unArr.forEach(function (e, i) {
	                dom.getComponentRect(that.$refs['item' + e][0], function (option) {
	                    var _top = option.size.top;
	                    if (_top < 0 && _top > -50) {
	                        that.isSelect = that.unArr[i + 1];
	                    }
	                });
	            });
	        },
	        chosePro: function chosePro(e) {
	            //选择省份
	            console.log('返回按钮 不走原生');
	            this.showProvince = false; //隐藏省 显示市
	            this.city = e;
	            eventModule.setEnableBack(1, 0); //返回按钮监听 WXback 方法
	        },
	        selectCity: function selectCity(e) {
	            var that = this;
	            console.log('that.Source-----' + that.Source);

	            if (that.Source == 'provid') {
	                eventModule.wxFirePushCallback('provid_selectCity', { CityCode: e.CityCode, CityName: e.CityName });
	            } else if (that.Source == 'security') {
	                eventModule.wxFirePushCallback('security_selectCity', { CityCode: e.CityCode, CityName: e.CityName });
	            }
	        }
	    },
	    created: function created() {},

	    computed: {},
	    ready: function ready() {},
	    mounted: function mounted() {
	        var that = this;
	        globalEvent.addEventListener('WXback', function (e) {
	            console.log('no back');
	            that.showProvince = true;
	            eventModule.setEnableBack(0, 0); //返回按钮走原生返回
	        });

	        this.init();
	    },

	    filters: {},
	    destroyed: function destroyed() {
	        globalEvent.removeEventListener('WXback');
	    }
	};

/***/ }),
/* 84 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(_vm.showProvince) ? _c('scroller', {
	    ref: "box",
	    staticClass: ["scroller"],
	    on: {
	      "scroll": _vm.scrollChange
	    }
	  }, [_c('div', [_vm._l((_vm.location), function(item, index) {
	    return _c('div', [(item.flag) ? _c('text', {
	      ref: 'item' + item.flag,
	      refInFor: true,
	      staticClass: ["label"]
	    }, [_vm._v(_vm._s(item.flag))]) : _vm._e(), _c('text', {
	      class: ['items' + (item.flag ? '' : '_bt')],
	      on: {
	        "click": function($event) {
	          _vm.chosePro(item)
	        }
	      }
	    }, [_vm._v(_vm._s(item.ProvinceName))])])
	  }), _c('div', {
	    staticClass: ["nav_wrap"]
	  }, [_c('div', {
	    staticClass: ["nav"]
	  }, _vm._l((_vm.unArr), function(item, index) {
	    return _c('text', {
	      class: ['nav_letter' + (item == _vm.isSelect ? '_active' : '')],
	      on: {
	        "click": function($event) {
	          _vm.goto(item)
	        }
	      }
	    }, [_vm._v(_vm._s(item))])
	  }))])], 2)]) : _vm._e(), (!_vm.showProvince) ? _c('div', [_c('text', {
	    staticClass: ["label"]
	  }, [_vm._v(_vm._s(_vm.city.ProvinceName))]), _c('div', {
	    staticClass: ["items_city_wrap"]
	  }, _vm._l((_vm.city.CityLevel), function(item, index) {
	    return _c('text', {
	      class: [index != '0' ? 'bt' : '', 'items_city'],
	      on: {
	        "click": function($event) {
	          _vm.selectCity(item)
	        }
	      }
	    }, [_vm._v(_vm._s(item.CityName))])
	  }))]) : _vm._e()])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 85 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(86)
	)

	/* script */
	__vue_exports__ = __webpack_require__(87)

	/* template */
	var __vue_template__ = __webpack_require__(120)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commissioninstallment/cashimmediately.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-bcac2526"
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
/* 86 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "justifyContent": "space-between"
	  },
	  "scroll": {
	    "backgroundColor": "#f4f4f4",
	    "justifyContent": "space-between"
	  },
	  "foot": {
	    "backgroundColor": "#f4f4f4"
	  },
	  "borrowmoney": {
	    "marginTop": 30,
	    "marginLeft": 25,
	    "flexDirection": "row"
	  },
	  "yaunnumber": {
	    "width": 28,
	    "height": 70,
	    "position": "absolute",
	    "right": 90,
	    "top": 0,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "loandeadlien": {
	    "width": 700,
	    "height": 82,
	    "marginLeft": 25,
	    "marginTop": 22,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "borderBottomStyle": "solid",
	    "borderBottomWidth": 2,
	    "borderBottomColor": "#f5f5f5",
	    "justifyContent": "space-between"
	  },
	  "servicecharge": {
	    "width": 700,
	    "height": 82,
	    "marginLeft": 25,
	    "marginTop": 22,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "borderBottomStyle": "solid",
	    "borderBottomWidth": 2,
	    "borderBottomColor": "#f5f5f5",
	    "justifyContent": "space-between"
	  },
	  "warmprompt": {
	    "height": 102,
	    "paddingLeft": 25,
	    "paddingTop": 20,
	    "fontSize": 24,
	    "backgroundColor": "#f4f4f4",
	    "color": "#7e7e7e"
	  },
	  "submitbutton": {
	    "width": 650,
	    "height": 90,
	    "borderRadius": 10,
	    "backgroundColor": "#c2c2c2",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "loantitle": {
	    "fontSize": 28,
	    "color": "#303030"
	  },
	  "choosedeadline": {
	    "fontSize": 28
	  },
	  "containtext": {
	    "fontSize": 28
	  },
	  "addsubtract": {
	    "fontSize": 50,
	    "color": "#c2c2c2"
	  },
	  "warmtext": {
	    "color": "#7e7e7e"
	  },
	  "bluecol": {
	    "color": "#108ee9"
	  },
	  "borowcol": {
	    "color": "#c2c2c2",
	    "marginLeft": 20
	  },
	  "whitecol": {
	    "color": "#ffffff"
	  },
	  "parampressImg": {
	    "height": 196,
	    "width": 230,
	    "position": "absolute",
	    "right": 85,
	    "top": 28,
	    "zIndex": 10,
	    "opacity": 0.2
	  },
	  "bankcardrightarrow": {
	    "width": 18,
	    "height": 30,
	    "position": "absolute",
	    "right": 30,
	    "top": 86
	  },
	  "redcol": {
	    "color": "#e2262a"
	  },
	  "reimbursementlist": {
	    "height": 270,
	    "backgroundColor": "#ffffff"
	  },
	  "list": {
	    "height": 90
	  },
	  "agreebtn": {
	    "marginLeft": 50,
	    "height": 35
	  },
	  "commissionbtn": {
	    "width": 648,
	    "height": 90,
	    "marginLeft": 51,
	    "marginBottom": 32,
	    "marginTop": 25
	  },
	  "monthrepay": {
	    "height": 90,
	    "backgroundColor": "#ffffff",
	    "paddingLeft": 25,
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center"
	  },
	  "repaycon": {
	    "flexDirection": "row",
	    "alignItems": "center"
	  },
	  "repayitle": {
	    "fontSize": 30,
	    "color": "#7e7e7e"
	  },
	  "repaynumber": {
	    "fontSize": 30,
	    "color": "#303030"
	  },
	  "arrow-explain": {
	    "height": 36,
	    "width": 36,
	    "marginLeft": 17,
	    "marginRight": 27
	  },
	  "ccline": {
	    "backgroundColor": "#A4A4A4",
	    "height": 0.5,
	    "opacity": 0.8,
	    "marginLeft": 28,
	    "marginRight": 4
	  },
	  "borderline": {
	    "backgroundColor": "#A4A4A4",
	    "height": 0.5,
	    "opacity": 0.8
	  },
	  "promptrepay": {
	    "height": 60,
	    "backgroundColor": "#ffffff",
	    "flexDirection": "row"
	  },
	  "promptrepaytxt": {
	    "color": "#c5c5c5",
	    "fontSize": 24,
	    "marginLeft": 25
	  }
	}

/***/ }),
/* 87 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _infoCell = __webpack_require__(88);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _agreeBtn = __webpack_require__(96);

	var _agreeBtn2 = _interopRequireDefault(_agreeBtn);

	var _commissioninfor = __webpack_require__(100);

	var _commissioninfor2 = _interopRequireDefault(_commissioninfor);

	var _ccButton = __webpack_require__(104);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _bankcardheader = __webpack_require__(108);

	var _bankcardheader2 = _interopRequireDefault(_bankcardheader);

	var _changenumberframe = __webpack_require__(112);

	var _changenumberframe2 = _interopRequireDefault(_changenumberframe);

	var _hint = __webpack_require__(116);

	var _hint2 = _interopRequireDefault(_hint);

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

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: { hint: _hint2.default, infoCell: _infoCell2.default, commissioninfor: _commissioninfor2.default, agreeBtn: _agreeBtn2.default, ccButton: _ccButton2.default, bankcardheader: _bankcardheader2.default, changenumberframe: _changenumberframe2.default, ccImage: _ccImage2.default },
	    data: function data() {
	        return {
	            bankname: '工商银行',
	            bankcardnumber: '8888',
	            changebankcard: '更换银行卡',
	            cancashnumber: '28000',
	            listtotal: '29017.88',
	            selectStage: '',
	            isBankname: true,
	            isNobankbing: false,
	            isbystages: false,
	            isClick: false,

	            // 标记是否输入完成(获取/失去焦点时变化)
	            isDidInputMoney: true,
	            isMaxInputMoney: false,
	            // 分期期数未选择时隐藏
	            selectCommisOpacity: 0,
	            isShowTipCommitips: false,
	            noCanInput: false,
	            // 防抖
	            isDidClick: false
	        };
	    },

	    methods: {

	        subtractnumber: function subtractnumber() {
	            if (this.$store.state.commApply.availableAmount < 20000) {
	                return;
	            }
	            if (this.$store.state.commApply.isbankbing) {
	                if (this.$store.state.commApply.message <= 20000) {
	                    Vue.TipHelper.showHub('1', '最低分期金额20000');
	                    this.$store.state.commApply.message = 20000;
	                } else {
	                    this.$store.state.commApply.message = Number(this.$store.state.commApply.message) - 100;
	                }
	            } else {
	                Vue.TipHelper.showHub('1', '您还没有绑卡哦');
	            }

	            // this.$store.state.commApply.platformRecommendedFee = this.$store.state.commApply.message*this.formalitiesRate;
	        },
	        addnumber: function addnumber() {

	            if (this.$store.state.commApply.isbankbing) {
	                var maxValue = this.$store.state.commApply.availableAmount;
	                if (this.$store.state.commApply.message >= maxValue) {
	                    return;
	                } else {
	                    this.$store.state.commApply.message = Number(this.$store.state.commApply.message) + 100;
	                }
	            } else {
	                Vue.TipHelper.showHub('1', '您还没有绑卡哦');
	            }
	        },
	        openStageCard: function openStageCard() {
	            if (this.$store.state.commApply.availableAmount < 20000) {
	                Vue.TipHelper.showHub('1', '最低分期金额20000');
	                return;
	            }
	            if (this.$store.state.commApply.message < 20000) {
	                this.$store.state.commApply.message = 20000;
	                Vue.TipHelper.showHub('1', '最低分期金额20000');
	                return;
	            }

	            if (this.$store.state.commApply.isbankbing) {
	                var content = {
	                    title: "请选择分期期数",
	                    items: ["3期", "6期", "12期", "24期"]
	                };
	                var that = this;
	                eventModule.wxSelectListView(content, this.selectStage, function (e) {
	                    // console.log('selectStage:', e)
	                    that.selectStage = e.result;
	                    that.isbystages = true;
	                    that.selectCommisOpacity = 1;
	                    // var stages =e.result.substring(0, 1);
	                    that.$store.state.commApply.stages = e.result.substring(0, e.result.indexOf('期'));
	                    that.$store.dispatch('COMMISSION_COMMCASH_TRIALREPAYMENTINFO', { stages: that.$store.state.commApply.stages });
	                });
	            } else {
	                Vue.TipHelper.showHub('1', '您还没有绑卡哦');
	            }
	        },
	        commission: function commission() {
	            // 防抖(服务端已经修改)
	            //                if (Vue.checkDidClick()) {
	            //                    return
	            //                }

	            if (this.$store.state.debug == true) {
	                console.log('this.$store.state.commApply.bankiconImg', this.$store.state.commApply.bankiconImg);
	                console.log('this.$store.state.commApply.parampressImg', this.$store.state.commApply.parampressImg
	                //                    console.log('this.$store.state.commApply.bankiconImg', this.$store.state.commApply.bankiconImg)
	                );return;
	            }

	            var that = this;
	            if (this.isbystages) {

	                if (this.$store.state.commApply.message < 20000) {
	                    Vue.TipHelper.showHub('1', '最低分期金额20000');
	                    return;
	                }

	                this.$store.dispatch('COMMISSION_COMMCASH_SAVESTREAM', {
	                    callback: function callback() {

	                        Vue.NaviHelper.push('native/main/scan', {
	                            title: '额度申请',
	                            showTab: false,
	                            scanTitle: "扫码付佣金",
	                            needHelp: true,
	                            desc: "请将中原地产提供的二维码放入扫描框内",
	                            scanHandlerRoute: "/main/commissionscan"
	                        }, '', function (r) {});
	                        if (that.$store.state.env.platform == 'iOS') {
	                            eventModule.removeViewController('GFBWeexBaseVC');
	                        } else {
	                            Vue.NaviHelper.push('pop');
	                        }
	                    }
	                });
	            }
	        },
	        changebank: function changebank() {
	            // 不可点击
	            if (!this.isShowArrow) {
	                return;
	            }

	            var that = this;
	            var globalEvent = weex.requireModule('globalEvent');
	            globalEvent.addEventListener('WXBindCardResult', function (r) {
	                console.log('绑卡回调: ', r
	                // 刷新页面
	                );that.$store.dispatch('COMMISSION_COMMCASH_INITDATA');
	            });
	            Vue.NaviHelper.push('native/main/bankcard', {
	                cardNo: this.$store.state.commApply.cardNo,
	                capital: this.$store.state.commApply.totalAmount
	            }, 'channel_pushChangeBankcard', function (r) {
	                console.log('绑卡回调2: ', r);
	            });
	        },
	        clickTip: function clickTip() {
	            this.isShowTipCommitips = !this.isShowTipCommitips;
	        },
	        // -------------输入金额 - 监听方法
	        onChange: function onChange(e) {
	            console.log('change: ', e.value);

	            if (this.$store.state.commApply.availableAmount < 20000) {
	                Vue.TipHelper.showHub('1', '最低分期金额20000');
	            }
	            if (this.$store.state.commApply.message < 20000) {
	                this.$store.state.commApply.message = 20000;
	                Vue.TipHelper.showHub('1', '最低分期金额20000');
	            }
	        },
	        onInput: function onInput(e) {
	            if (e.value == '' || this.isDidInputMoney) {
	                return;
	            }

	            e.value = e.value.replace(/\D/g, '0' // 非字符限制

	            );if (e.value.substring(0, 1) == 0) {
	                e.value = 1;
	            }

	            if (this.$store.state.env.platform == 'android') {
	                var maxValue = this.$store.state.commApply.availableAmount;
	                if (e.value <= maxValue) {
	                    this.$store.state.commApply.message = e.value;
	                } else {
	                    this.$store.state.commApply.message = maxValue;
	                    this.commisonValue = maxValue;
	                }
	                this.$store.state.commApply.platformRecommendedFee = this.$store.state.commApply.message * this.formalitiesRate;
	            } else {
	                this.$store.state.commApply.message = e.value;
	                this.$store.state.commApply.platformRecommendedFee = this.$store.state.commApply.message * this.formalitiesRate;
	            }
	        },
	        onFocus: function onFocus() {
	            console.log('focus: ', this.$store.state.commApply.message, this.inputValue, this.commisonValue);
	            this.isDidInputMoney = false;

	            if (this.$store.state.commApply.isbankbing) {
	                if (this.commisonValue != '') {} else {
	                    this.$store.state.commApply.message = '';
	                }
	            } else {
	                this.isClick = true;
	                Vue.TipHelper.showHub('1', '您还没有绑卡哦');
	            }
	        },
	        onBlur: function onBlur() {
	            console.log('blur: ');
	            this.isDidInputMoney = true;

	            var maxValue = this.$store.state.commApply.availableAmount;
	            if (this.$store.state.commApply.message > maxValue) {
	                this.$store.state.commApply.message = maxValue;
	                this.commisonValue = maxValue;
	                this.$store.state.commApply.platformRecommendedFee = this.$store.state.commApply.message * this.formalitiesRate;
	            }
	        },

	        relevantcontracts: function relevantcontracts() {

	            Vue.NaviHelper.push('weex/common?id=commission&path=router_applicationstagecontract', { title: '相关合同', message: this.$store.state.commApply.message, stages: this.$store.state.commApply.stages }, 'channel_ApplicationStageContract', function () {});
	        }

	    },
	    computed: {
	        inputValue: function inputValue() {

	            return this.$store.state.commApply.message;
	        },
	        commisonValue: function commisonValue() {
	            var maxValue = this.$store.state.commApply.availableAmount;

	            if (Number(this.$store.state.commApply.message) >= maxValue) {
	                if (this.isDidInputMoney) {
	                    console.log('maxValue: ', maxValue);

	                    this.$store.state.commApply.message = maxValue;

	                    var num = String(Number(this.$store.state.commApply.message).toFixed(2));
	                    var re = /(-?\d+)(\d{3})/;
	                    while (re.test(num)) {
	                        num = num.replace(re, "$1,$2");
	                    }
	                    return num;
	                } else {
	                    return this.$store.state.commApply.message;
	                }
	            } else {
	                if (this.isDidInputMoney) {
	                    var num = String(Number(this.$store.state.commApply.message).toFixed(2));
	                    var re = /(-?\d+)(\d{3})/;
	                    while (re.test(num)) {
	                        num = num.replace(re, "$1,$2");
	                    }
	                    return num;
	                } else {
	                    return this.$store.state.commApply.message;
	                }
	            }
	        },
	        servicecharge: function servicecharge() {
	            //                console.log('platformRecommendedFee:', this.$store.state.commApply.platformRecommendedFee)
	            var minServiceCharge = 20000 * this.formalitiesRate;
	            var maxServiceCharge = this.$store.state.commApply.availableAmount * this.formalitiesRate;

	            if (this.$store.state.commApply.message * this.formalitiesRate <= minServiceCharge) {
	                return minServiceCharge;
	            } else if (this.$store.state.commApply.message * this.formalitiesRate >= maxServiceCharge) {
	                return maxServiceCharge;
	            } else {
	                return this.$store.state.commApply.message * this.formalitiesRate;
	            }
	        },
	        isShowArrow: function isShowArrow() {
	            console.log('cardFlag:', this.$store.state.commApply.cardFlag);
	            console.log('bindCardSource:', this.$store.state.commApply.bindCardSource);

	            if (this.$store.state.commApply.cardFlag == '0' || this.$store.state.commApply.cardFlag == '3') {
	                // 未绑卡
	                return true;
	            } else {
	                if (this.$store.state.commApply.bindCardSource == '2') {
	                    // 绑卡可更换
	                    return true;
	                } else {
	                    return false;
	                }
	            }
	        },
	        formalitiesRate: function formalitiesRate() {
	            // 借款手续费费率
	            return this.$store.state.commApply.formalitiesRate;
	        }

	    },
	    watch: {
	        commisonValue: function commisonValue(newV, oldV) {
	            console.log('commisonValue: ', newV, oldV);

	            var num = String(Number(newV).toFixed(2));
	            var re = /(-?\d+)(\d{3})/;
	            while (re.test(num)) {
	                num = num.replace(re, "$1,$2");
	            }

	            if (num == oldV || newV == oldV) {
	                return;
	            } else {
	                // 只有当输入框的数据大小改变了, 才隐藏
	                this.selectCommisOpacity = 0;
	                this.isbystages = false;
	                this.selectStage = '';
	            }
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {
	        var that = this;
	        this.$store.dispatch('COMMISSION_COMMCASH_INITDATA', { callback: function callback() {} });
	        //            formalitiesRate: this.$store.state.commApply.formalitiesRate
	        // this.hedgingdays = this.$store.state.commApply.hedgingdays;
	        // console.log(this.parampressImg)

	    },
	    destroy: function destroy() {
	        globalEvent.removeEventListener('WXBindCardResult');
	    }
	};

/***/ }),
/* 88 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(89)
	)

	/* script */
	__vue_exports__ = __webpack_require__(90)

	/* template */
	var __vue_template__ = __webpack_require__(95)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/infoCell.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-087ffef2"
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
/* 89 */
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
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#303030",
	    "placeholderColor": "#C5C5C5",
	    "paddingTop": 15,
	    "paddingBottom": 15
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
/* 90 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccLine = __webpack_require__(91);

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
/* 91 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(92)
	)

	/* script */
	__vue_exports__ = __webpack_require__(93)

	/* template */
	var __vue_template__ = __webpack_require__(94)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/ccLine.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-b508b694"
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
/* 92 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#A4A4A4",
	    "height": 0.5,
	    "opacity": 0.8,
	    "marginLeft": 28,
	    "marginRight": 4
	  }
	}

/***/ }),
/* 93 */
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
/* 94 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return (_vm.isLineShow) ? _c('div', {
	    staticClass: ["wrapper"]
	  }) : _vm._e()
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 95 */
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/agreeBtn.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-00b0905c"
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
/* 98 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

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
/* 99 */
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
/* 100 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(101)
	)

	/* script */
	__vue_exports__ = __webpack_require__(102)

	/* template */
	var __vue_template__ = __webpack_require__(103)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/lch/commissioninfor.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-75f6052e"
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
/* 101 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF"
	  },
	  "content": {
	    "flexDirection": "row",
	    "justifyContent": "center",
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
	  "btn-text-placeholder-mobileAuthNo": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#E2262A"
	  },
	  "btn-text-placeholder-mobileAuthYes": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#108EE9"
	  },
	  "btn-text-placeholder-aliAuth": {
	    "textAlign": "right",
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 30,
	    "color": "#108EE9"
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
	    "fontFamily": "PingFangSC-Regular",
	    "fontSize": 30,
	    "color": "#303030"
	  },
	  "content-middle-button": {
	    "width": 650,
	    "height": 90,
	    "borderRadius": 10,
	    "backgroundColor": "#c2c2c2",
	    "position": "fixed",
	    "left": 50,
	    "bottom": 40,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "bingbankcard": {
	    "height": 252,
	    "backgroundColor": "#f0f0f0",
	    "position": "relative"
	  },
	  "bankcardcontain": {
	    "width": 706,
	    "height": 196,
	    "backgroundColor": "#ffffff",
	    "marginLeft": 22,
	    "marginTop": 28,
	    "position": "relative",
	    "zIndex": 100
	  },
	  "bank-icon": {
	    "width": 95,
	    "height": 95,
	    "position": "absolute",
	    "left": 28,
	    "top": 50
	  },
	  "bankname": {
	    "position": "absolute",
	    "left": 145,
	    "top": 58,
	    "fontSize": 36,
	    "color": "#303030"
	  },
	  "bankcardnumber": {
	    "fontSize": 28,
	    "position": "absolute",
	    "left": 124,
	    "top": 125
	  },
	  "changebankcard": {
	    "fontSize": 28,
	    "color": "#108ee9",
	    "position": "absolute",
	    "left": 504,
	    "top": 60
	  },
	  "border-line": {
	    "width": 612,
	    "height": 2,
	    "backgroundColor": "#f5f5f5",
	    "position": "absolute",
	    "left": 44,
	    "top": 182
	  },
	  "cancash": {
	    "position": "absolute",
	    "left": 145,
	    "top": 120,
	    "fontSize": 36,
	    "color": "#484848"
	  },
	  "cancashnumber": {
	    "fontSize": 28,
	    "position": "absolute",
	    "right": 25,
	    "top": 205,
	    "color": "#d03b35"
	  },
	  "borrowmoney": {
	    "marginTop": 30,
	    "marginLeft": 25,
	    "flexDirection": "row"
	  },
	  "allout": {
	    "width": 700,
	    "height": 92,
	    "marginLeft": 25,
	    "marginTop": 20,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "fontSize": 28
	  },
	  "addnumber": {
	    "width": 95,
	    "height": 92,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c2c2c2",
	    "fontSize": 28,
	    "borderTopRightRadius": 10,
	    "borderBottomRightRadius": 10
	  },
	  "changenumber": {
	    "width": 510,
	    "height": 92,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "position": "relative",
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c2c2c2",
	    "fontSize": 28
	  },
	  "subtractnumber": {
	    "width": 95,
	    "height": 92,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c2c2c2",
	    "fontSize": 28,
	    "borderTopLeftRadius": 10,
	    "borderBottomLeftRadius": 10
	  },
	  "takeoutmoney": {
	    "width": 150,
	    "height": 70,
	    "backgroundColor": "#c2c2c2",
	    "color": "#ffffff",
	    "marginLeft": 46,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderRadius": 10
	  },
	  "inputnumber": {
	    "width": 150,
	    "height": 70
	  },
	  "yaunnumber": {
	    "width": 28,
	    "height": 70,
	    "position": "absolute",
	    "right": 90,
	    "top": 0,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "loandeadlien": {
	    "width": 700,
	    "height": 82,
	    "marginLeft": 25,
	    "marginTop": 22,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "borderBottomStyle": "solid",
	    "borderBottomWidth": 2,
	    "borderBottomColor": "#f5f5f5",
	    "justifyContent": "space-between"
	  },
	  "servicecharge": {
	    "width": 750,
	    "height": 82,
	    "paddingLeft": 25,
	    "paddingRight": 25,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "space-between"
	  },
	  "warmprompt": {
	    "flex": 1,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "height": 102,
	    "backgroundColor": "#f4f4f4",
	    "color": "#7e7e7e"
	  },
	  "submitbutton": {
	    "width": 650,
	    "height": 90,
	    "borderRadius": 10,
	    "backgroundColor": "#c2c2c2",
	    "position": "fixed",
	    "left": 50,
	    "bottom": 40,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "contract": {
	    "width": 650,
	    "height": 34,
	    "position": "fixed",
	    "left": 50,
	    "bottom": 160,
	    "flexDirection": "row"
	  },
	  "relevantcontracts": {
	    "color": "#108ee9"
	  },
	  "agree-icon": {
	    "width": 35,
	    "height": 35
	  },
	  "loantitle": {
	    "fontSize": 30,
	    "color": "#7e7e7e"
	  },
	  "choosedeadline": {
	    "fontSize": 28
	  },
	  "containtext": {
	    "fontSize": 28
	  },
	  "addsubtract": {
	    "fontSize": 50,
	    "color": "#c2c2c2"
	  },
	  "warmtext": {
	    "marginTop": 20,
	    "marginLeft": 25,
	    "marginRight": 25,
	    "marginBottom": 20,
	    "color": "#7e7e7e",
	    "fontSize": 22,
	    "lineHeight": 30
	  },
	  "bluecol": {
	    "color": "#108ee9"
	  },
	  "borowcol": {
	    "color": "#c2c2c2",
	    "marginLeft": 20
	  },
	  "whitecol": {
	    "color": "#ffffff"
	  },
	  "parampressImg": {
	    "height": 196,
	    "width": 230,
	    "position": "absolute",
	    "right": 85,
	    "top": 28,
	    "zIndex": 10,
	    "opacity": 0.2
	  },
	  "bankcardrightarrow": {
	    "width": 18,
	    "height": 30,
	    "position": "absolute",
	    "right": 30,
	    "top": 86
	  },
	  "redcol": {
	    "color": "#e2262a"
	  },
	  "reimbursementlist": {
	    "height": 270,
	    "backgroundColor": "#ffffff"
	  },
	  "list": {
	    "height": 90
	  }
	}

/***/ }),
/* 102 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccLine = __webpack_require__(91);

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
	        //借款手续费
	        servicecharge: {
	            type: Number,
	            default: ''
	        },
	        //输入佣金分期金额
	        message: {
	            type: Number,
	            default: ''
	        },
	        //银行名称
	        bankname: {
	            type: String,
	            default: ''
	        },
	        //银行卡卡号后四位数
	        bankcardnumber: {
	            type: Number,
	            default: ''
	        },
	        //可分期的佣金
	        cancashnumber: {
	            type: Number,
	            default: ''
	        },
	        //银行卡水印图标
	        parampressImg: {
	            type: String,
	            default: ''
	        },
	        //银行卡小图标
	        bankiconImg: {
	            type: String,
	            default: ''
	        },
	        //银行卡右箭头图标
	        cardrightarrowImg: {
	            type: String,
	            default: ''
	        },
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
	            default: '请输入...'
	        },
	        // 仅显示姓名与身份证内容
	        textValue: {
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

	    methods: {
	        subtractnumber: function subtractnumber() {
	            return this.message = Number(this.message) - 100;
	        },
	        addnumber: function addnumber() {
	            return this.message = Number(this.message) + 100;
	        }
	    },
	    created: function created() {},

	    watch: {
	        // num:function() {
	        //     var num;
	        //       num = Number(this.servicecharge).toFixed(2).toString().split(".");  
	        //       num[0] = num[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");  
	        //       this.servicecharge= num.join(".");
	        //       return this.servicecharge;                   
	        // }

	    },
	    mounted: function mounted() {},
	    update: function update() {}
	};

/***/ }),
/* 103 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(this.types === 'tiedcard') ? _c('div', {
	    staticClass: ["bingbankcard"]
	  }, [_c('div', {
	    staticClass: ["bankcardcontain"]
	  }, [_c('ccImage', {
	    staticClass: ["bank-icon"],
	    attrs: {
	      "src": _vm.bankiconImg
	    }
	  }), _c('text', {
	    staticClass: ["bankname"]
	  }, [_vm._v(_vm._s(_vm.bankname) + " (" + _vm._s(_vm.bankcardnumber) + ")")]), _c('text', {
	    staticClass: ["cancash"]
	  }, [_vm._v("可分期佣金" + _vm._s(_vm.cancashnumber))]), _c('ccImage', {
	    staticClass: ["bankcardrightarrow"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1), _c('ccImage', {
	    staticClass: ["parampressImg"],
	    attrs: {
	      "src": _vm.parampressImg
	    }
	  })], 1) : _vm._e(), (this.types === 'borrowmoney') ? _c('div', {
	    staticClass: ["borrowmoney"]
	  }, [_c('text', {
	    staticClass: ["containtext"]
	  }, [_vm._v("请选择佣金分期金额")]), _c('text', {
	    staticClass: ["containtext", "borowcol"]
	  }, [_vm._v("(最低分期金额" + _vm._s(_vm._f("thousandSeparator")(20000)) + ")")])]) : _vm._e(), (this.types === 'warmprompt') ? _c('div', {
	    staticClass: ["warmprompt"]
	  }, [_c('text', {
	    staticClass: ["warmtext"]
	  }, [_vm._v("每月最后还款日平台将对您还款银行卡进行扣款，您也可在“我的账单”进行主动还款。")])]) : _vm._e(), (this.types === 'closingcost') ? _c('div', {
	    staticClass: ["servicecharge"]
	  }, [_c('text', {
	    staticClass: ["loantitle"]
	  }, [_vm._v("借款手续费")]), _c('text', {
	    staticClass: ["choosedeadline", "redcol"]
	  }, [_vm._v(_vm._s(_vm.servicecharge))])]) : _vm._e(), (this.types === 'allout') ? _c('div', {
	    staticClass: ["allout"]
	  }, [_c('div', {
	    staticClass: ["subtractnumber"],
	    on: {
	      "click": _vm.subtractnumber
	    }
	  }, [_c('text', {
	    staticClass: ["addsubtract"]
	  }, [_vm._v("-")])]), _c('div', {
	    staticClass: ["changenumber"]
	  }, [_c('input', {
	    staticClass: ["inputnumber"],
	    attrs: {
	      "type": "tel",
	      "value": (_vm.message)
	    },
	    on: {
	      "input": function($event) {
	        _vm.message = $event.target.attr.value
	      }
	    }
	  })]), _c('div', {
	    staticClass: ["addnumber"],
	    on: {
	      "click": _vm.addnumber
	    }
	  }, [_c('text', {
	    staticClass: ["addsubtract"]
	  }, [_vm._v("+")])])]) : _vm._e(), (this.types === 'button') ? _c('div', {
	    staticClass: ["content-middle-button"]
	  }) : _vm._e(), _c('div', {
	    staticClass: ["content"]
	  }, [_c('div', {
	    staticClass: ["content-right"]
	  }, [(this.types === 'btn') ? _c('div', {
	    staticClass: ["content-right-btn"]
	  }, [_c('div', {
	    staticClass: ["btn"],
	    on: {
	      "click": _vm.onClick
	    }
	  }, [(_vm.selectValue !== '') ? _c('text', {
	    class: ['btn-text' + _vm.flag]
	  }, [_vm._v(_vm._s(_vm.selectValue))]) : _c('text', {
	    class: this.placeholder == '认证中' ? ['btn-text-placeholder'] : ['btn-text-placeholder' + _vm.flag]
	  }, [_vm._v(_vm._s(_vm.placeholder))])]), _c('ccImage', {
	    staticClass: ["arrow-img"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1) : _vm._e()])])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 104 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(105)
	)

	/* script */
	__vue_exports__ = __webpack_require__(106)

	/* template */
	var __vue_template__ = __webpack_require__(107)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/ccButton.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-d6ca2b18"
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
/* 105 */
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
/* 106 */
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
/* 107 */
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
/* 108 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(109)
	)

	/* script */
	__vue_exports__ = __webpack_require__(110)

	/* template */
	var __vue_template__ = __webpack_require__(111)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/lch/bankcardheader.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-c8d76898"
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
/* 109 */
/***/ (function(module, exports) {

	module.exports = {
	  "bingbankcard": {
	    "height": 245,
	    "backgroundColor": "#f0f0f0",
	    "position": "relative"
	  },
	  "bankcardcontain": {
	    "width": 702,
	    "height": 190,
	    "backgroundColor": "#ffffff",
	    "marginLeft": 24,
	    "marginTop": 26,
	    "position": "relative",
	    "zIndex": 100
	  },
	  "bank-icon": {
	    "position": "absolute",
	    "left": 28,
	    "top": 50
	  },
	  "bankname": {
	    "position": "absolute",
	    "left": 145,
	    "top": 58,
	    "fontSize": 32,
	    "color": "#303030"
	  },
	  "nobankbing": {
	    "position": "absolute",
	    "left": 145,
	    "top": 58,
	    "fontSize": 32,
	    "color": "#E2262A"
	  },
	  "bankcardnumber": {
	    "fontSize": 28,
	    "position": "absolute",
	    "left": 124,
	    "top": 125
	  },
	  "changebankcard": {
	    "fontSize": 28,
	    "color": "#108ee9",
	    "position": "absolute",
	    "left": 504,
	    "top": 60
	  },
	  "border-line": {
	    "width": 612,
	    "height": 2,
	    "backgroundColor": "#f5f5f5",
	    "position": "absolute",
	    "left": 44,
	    "top": 182
	  },
	  "cancash": {
	    "position": "absolute",
	    "left": 145,
	    "top": 120,
	    "fontSize": 28,
	    "color": "#484848"
	  },
	  "parampressImg": {
	    "height": 196,
	    "width": 230,
	    "position": "absolute",
	    "right": 85,
	    "top": 28,
	    "zIndex": 10
	  },
	  "bankcardrightarrow": {
	    "width": 18,
	    "height": 30,
	    "position": "absolute",
	    "right": 30,
	    "top": 86
	  }
	}

/***/ }),
/* 110 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    components: { ccImage: _ccImage2.default },
	    props: {
	        //是否显示未绑卡有关提示信息
	        changebank: {
	            type: Boolean,
	            default: true
	        },
	        //是否显示未绑卡有关提示信息
	        isNobankbing: {
	            type: Boolean,
	            default: true
	        },
	        //是否显示银行和银行卡卡号信息
	        isBankname: {
	            type: Boolean,
	            default: true
	        },
	        //银行名称
	        bankname: {
	            type: String,
	            default: ''
	        },
	        //银行卡卡号后四位数
	        bankcardnumber: {
	            type: Number,
	            default: ''
	        },
	        //可分期的佣金
	        cancashnumber: {
	            type: Number,
	            default: ''
	        },
	        //银行卡水印图标
	        parampressImg: {
	            type: String,
	            default: ''
	        },
	        //银行卡小图标
	        bankiconImg: {
	            type: String,
	            default: ''
	        },
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
	            default: '请输入...'
	        },
	        // 仅显示姓名与身份证内容
	        textValue: {
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
	        },
	        isShowArrow: {
	            type: Boolean,
	            default: false
	        }
	    },
	    data: function data() {
	        return {};
	    },

	    methods: {},
	    created: function created() {},

	    watch: {},
	    mounted: function mounted() {},
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

/***/ }),
/* 111 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["bingbankcard"],
	    on: {
	      "click": _vm.changebank
	    }
	  }, [_c('div', {
	    staticClass: ["bankcardcontain"]
	  }, [_c('ccImage', {
	    staticClass: ["bank-icon"],
	    attrs: {
	      "src": _vm.bankiconImg
	    }
	  }), (this.$store.state.commApply.isbankbing) ? _c('text', {
	    staticClass: ["bankname"]
	  }, [_vm._v(_vm._s(this.$store.state.commApply.cardBank) + " (" + _vm._s(this.$store.state.commApply.cardNoStr) + ")")]) : _vm._e(), (!this.$store.state.commApply.isbankbing) ? _c('text', {
	    staticClass: ["nobankbing"]
	  }, [_vm._v("您暂未绑定银行卡")]) : _vm._e(), _c('text', {
	    staticClass: ["cancash"]
	  }, [_vm._v("可分期佣金" + _vm._s(_vm._f("thousandSeparator")(this.$store.state.commApply.availableAmount)))]), (_vm.isShowArrow) ? _c('ccImage', {
	    staticClass: ["bankcardrightarrow"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  }) : _vm._e()], 1), _c('ccImage', {
	    staticClass: ["parampressImg"],
	    attrs: {
	      "src": _vm.parampressImg
	    }
	  })], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 112 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(113)
	)

	/* script */
	__vue_exports__ = __webpack_require__(114)

	/* template */
	var __vue_template__ = __webpack_require__(115)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/lch/changenumberframe.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-46f621ee"
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
/* 113 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#FFFFFF"
	  },
	  "allout": {
	    "width": 700,
	    "height": 100,
	    "marginLeft": 25,
	    "marginRight": 25,
	    "marginTop": 20,
	    "flexDirection": "row",
	    "alignItems": "center",
	    "justifyContent": "center",
	    "fontSize": 28
	  },
	  "addnumber": {
	    "width": 90,
	    "height": 88,
	    "textAlign": "center",
	    "lineHeight": 88,
	    "fontSize": 48,
	    "color": "#c2c2c2",
	    "borderStyle": "solid",
	    "borderColor": "#c2c2c2",
	    "borderTopRightRadius": 10,
	    "borderBottomRightRadius": 10,
	    "borderLeftWidth": 0,
	    "borderRightWidth": 1,
	    "borderTopWidth": 1,
	    "borderBottomWidth": 1
	  },
	  "changenumber": {
	    "width": 520,
	    "height": 88,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "position": "relative",
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c2c2c2",
	    "fontSize": 28
	  },
	  "subtractnumber": {
	    "width": 90,
	    "height": 88,
	    "textAlign": "center",
	    "lineHeight": 88,
	    "fontSize": 48,
	    "color": "#c2c2c2",
	    "borderStyle": "solid",
	    "borderColor": "#c2c2c2",
	    "borderTopLeftRadius": 10,
	    "borderBottomLeftRadius": 10,
	    "borderTopWidth": 1,
	    "borderBottomWidth": 1,
	    "borderLeftWidth": 1,
	    "borderRightWidth": 0
	  },
	  "takeoutmoney": {
	    "width": 150,
	    "height": 70,
	    "backgroundColor": "#c2c2c2",
	    "color": "#ffffff",
	    "marginLeft": 46,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "borderRadius": 10
	  },
	  "inputnumber": {
	    "flex": 1,
	    "height": 88,
	    "fontSize": 36,
	    "color": "#303030",
	    "textAlign": "center",
	    "borderStyle": "solid",
	    "borderWidth": 1,
	    "borderColor": "#c2c2c2"
	  },
	  "yaunnumber": {
	    "width": 28,
	    "height": 70,
	    "position": "absolute",
	    "right": 90,
	    "top": 0,
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "addsubtract": {
	    "borderWidth": 1,
	    "fontSize": 50,
	    "color": "#c2c2c2",
	    "justifyContent": "center",
	    "alignItems": "center"
	  }
	}

/***/ }),
/* 114 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccLine = __webpack_require__(91);

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

	exports.default = {
	    components: { ccImage: _ccImage2.default, ccLine: _ccLine2.default },
	    props: {
	        //文本框是否可点击
	        isClick: {
	            type: Boolean,
	            default: false
	        },
	        // 监听获取焦点的文本数据
	        onFocus: {
	            type: Function
	        },
	        onBlur: {
	            type: Function
	        },
	        // 监听输入的文本数据
	        onChange: {
	            type: Function
	        },
	        onInput: {
	            type: Function
	        },
	        // 监听增加点击事件
	        addnumber: {
	            type: Function
	        },
	        // 监听减少点击事件
	        subtractnumber: {
	            type: Function
	        },
	        //借款手续费
	        servicecharge: {
	            type: Number,
	            default: ''
	        },
	        //输入佣金分期金额
	        message: {
	            type: Number,
	            default: ''
	        },
	        //银行名称
	        bankname: {
	            type: String,
	            default: ''
	        },
	        //银行卡卡号后四位数
	        bankcardnumber: {
	            type: Number,
	            default: ''
	        },
	        //可分期的佣金
	        cancashnumber: {
	            type: Number,
	            default: ''
	        },
	        //银行卡水印图标
	        parampressImg: {
	            type: String,
	            default: ''
	        },
	        //银行卡小图标
	        bankiconImg: {
	            type: String,
	            default: ''
	        },
	        //银行卡右箭头图标
	        cardrightarrowImg: {
	            type: String,
	            default: ''
	        },
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
	            default: '请输入...'
	        },
	        // 仅显示姓名与身份证内容
	        textValue: {
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

	    watch: {
	        // num:function() {
	        //     var num;
	        //       num = Number(this.servicecharge).toFixed(2).toString().split(".");  
	        //       num[0] = num[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");  
	        //       this.servicecharge= num.join(".");
	        //       return this.servicecharge;                   
	        // }

	    },
	    mounted: function mounted() {},
	    update: function update() {}
	};

/***/ }),
/* 115 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('div', {
	    staticClass: ["allout"]
	  }, [_c('text', {
	    staticClass: ["subtractnumber"],
	    on: {
	      "click": _vm.subtractnumber
	    }
	  }, [_vm._v("-")]), _c('input', {
	    staticClass: ["inputnumber"],
	    attrs: {
	      "maxlength": _vm.maxLength,
	      "type": "tel",
	      "disabled": _vm.isClick,
	      "value": (_vm.message)
	    },
	    on: {
	      "change": _vm.onChange,
	      "input": [function($event) {
	        _vm.message = $event.target.attr.value
	      }, _vm.onInput],
	      "focus": _vm.onFocus,
	      "blur": _vm.onBlur
	    }
	  }), _c('text', {
	    staticClass: ["addnumber"],
	    on: {
	      "click": _vm.addnumber
	    }
	  }, [_vm._v("+")])])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 116 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(117)
	)

	/* script */
	__vue_exports__ = __webpack_require__(118)

	/* template */
	var __vue_template__ = __webpack_require__(119)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/hint.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-a235b86e"
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
/* 117 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "height": 90,
	    "paddingTop": 24,
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
/* 118 */
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
/* 119 */
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
/* 120 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroll"]
	  }, [_c('div', {
	    staticClass: ["content"]
	  }, [_c('bankcardheader', {
	    attrs: {
	      "bankname": _vm.bankname,
	      "bankcardnumber": _vm.bankcardnumber,
	      "cancashnumber": _vm.cancashnumber,
	      "parampressImg": this.$store.state.commApply.parampressImg,
	      "bankiconImg": this.$store.state.commApply.bankiconImg,
	      "cardrightarrowImg": _vm.cardrightarrowImg,
	      "isBankname": _vm.isBankname,
	      "isNobankbing": _vm.isNobankbing,
	      "changebank": _vm.changebank,
	      "isShowArrow": _vm.isShowArrow
	    }
	  }), _c('commissioninfor', {
	    attrs: {
	      "types": 'borrowmoney'
	    }
	  }), _c('changenumberframe', {
	    attrs: {
	      "message": _vm.commisonValue,
	      "maxLength": _vm.isDidInputMoney ? 20 : 10,
	      "addnumber": _vm.addnumber,
	      "subtractnumber": _vm.subtractnumber,
	      "onChange": _vm.onChange,
	      "onInput": _vm.onInput,
	      "onFocus": _vm.onFocus,
	      "onBlur": _vm.onBlur,
	      "isClick": _vm.noCanInput
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'btn',
	      "title": '分期期数',
	      "placeholder": '请选择',
	      "selectValue": _vm.selectStage,
	      "onClick": _vm.openStageCard,
	      "isLineShow": false
	    }
	  }), _c('div', {
	    staticClass: ["borderline"]
	  }), _c('commissioninfor', {
	    attrs: {
	      "types": 'closingcost',
	      "servicecharge": _vm._f("thousandSeparator")(_vm.servicecharge)
	    }
	  }), _c('hint', {
	    staticClass: ["hint"],
	    attrs: {
	      "title": '每月最后还款日平台将对您还款银行卡进行扣款，您也可在“我的账单”进行主动还款。'
	    }
	  }), _c('div', {
	    style: {
	      opacity: _vm.selectCommisOpacity
	    }
	  }, [_c('infoCell', {
	    attrs: {
	      "types": 'text',
	      "isLineShow": false,
	      "title": _vm.selectStage + '共计',
	      "isShowStar": false,
	      "textValue": _vm._f("thousandSeparator")(this.$store.state.commApply.amountAll),
	      "textColor": '#303030'
	    }
	  }), _c('infoCell', {
	    attrs: {
	      "types": 'text',
	      "title": '还款日期',
	      "isShowStar": false,
	      "textValue": '每月' + this.$store.state.commApply.deductMoneyDate + '日',
	      "textColor": '#303030'
	    }
	  }), _c('div', {
	    staticClass: ["ccline"]
	  }), _c('div', {
	    staticClass: ["monthrepayall"]
	  }, [_c('div', {
	    staticClass: ["monthrepay"]
	  }, [_vm._m(0), _c('div', {
	    staticClass: ["repaycon"]
	  }, [_c('text', {
	    staticClass: ["repaynumber"]
	  }, [_vm._v(_vm._s(_vm._f("thousandSeparator")(this.$store.state.commApply.eachPeriodTotalAmount)))]), _c('ccImage', {
	    staticClass: ["arrow-explain"],
	    attrs: {
	      "src": 'WXLocal/wx_explain',
	      "onClick": _vm.clickTip
	    }
	  })], 1)]), (_vm.isShowTipCommitips) ? _c('div', {
	    staticClass: ["promptrepay"]
	  }, [_c('text', {
	    staticClass: ["promptrepaytxt"]
	  }, [_vm._v("每月待还款金额以平台出账日出账金额为准")])]) : _vm._e()])], 1)], 1), _c('div', {
	    staticClass: ["foot"]
	  }, [_c('div', {
	    style: {
	      height: this.$store.state.env.scale == 3 ? 110 : 80
	    }
	  }), (_vm.isbystages) ? _c('agreeBtn', {
	    staticClass: ["agreebtn"],
	    attrs: {
	      "isShow": true,
	      "isFinish": true,
	      "onClick": _vm.relevantcontracts
	    }
	  }) : _vm._e(), _c('ccButton', {
	    staticClass: ["commissionbtn"],
	    attrs: {
	      "isClick": _vm.isbystages,
	      "title": '支付手续费',
	      "onClick": _vm.commission
	    }
	  })], 1)])])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', [_c('text', {
	    staticClass: ["repayitle"]
	  }, [_vm._v("首月预计还款")])])
	}]}
	module.exports.render._withStripped = true

/***/ }),
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/contracts/applicationstagecontract.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-be6c82ca"
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
	  "webview": {
	    "flex": 1
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

	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致

	exports.default = {
	    components: {},
	    data: function data() {
	        return {
	            url: '',
	            userId: '',
	            message: '',
	            stages: '',
	            token: this.$store.state.token
	        };
	    },

	    methods: {},
	    computed: {},
	    created: function created() {},
	    mounted: function mounted() {
	        var message = this.$getConfig().localData.message; //分期金额

	        var stages = this.$getConfig().localData.stages; //分期期数

	        this.message = message;
	        this.stages = stages;
	        var that = this;
	        eventModule.getBaseInfo(function (e) {
	            that.userId = e.result.userId;
	            that.url = 'http://gfbapp.vcash.cn/#/Ajqhapplyinstallment?userId=' + that.userId + '&withdrawMoney=' + that.message + '&loanPeriods=' + that.stages + '&token=' + that.token;
	        });
	    },
	    destroy: function destroy() {}
	};

/***/ }),
/* 124 */
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
	  }, [_vm._v("跳转合同页")])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 125 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(126)
	)

	/* script */
	__vue_exports__ = __webpack_require__(127)

	/* template */
	var __vue_template__ = __webpack_require__(140)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commissioninstallment/commissioninformation.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-2146de63"
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
/* 126 */
/***/ (function(module, exports) {

	module.exports = {
	  "scroll": {
	    "flex": 1,
	    "flexDirection": "column",
	    "justifyContent": "space-between",
	    "height": 1333
	  },
	  "wrapper": {
	    "flex": 1,
	    "backgroundColor": "#f4f4f4"
	  },
	  "protocol": {
	    "height": 86,
	    "paddingLeft": 24,
	    "backgroundColor": "#ffffff",
	    "position": "relative"
	  },
	  "protocolcontain": {
	    "height": 86,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#dfdfdf",
	    "borderBottomWidth": 0.5,
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center"
	  },
	  "protocolleft": {
	    "fontSize": 28,
	    "color": "#7e7e7e"
	  },
	  "alreadyupload": {
	    "fontSize": 28,
	    "color": "#108ee9",
	    "position": "absolute",
	    "top": 29,
	    "right": 92
	  },
	  "uploadedicon": {
	    "width": 43,
	    "height": 40,
	    "marginRight": 25,
	    "marginLeft": 25
	  },
	  "commission": {
	    "height": 86,
	    "paddingLeft": 24,
	    "backgroundColor": "#ffffff",
	    "position": "relative"
	  },
	  "commissioncontain": {
	    "height": 86,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#dfdfdf",
	    "borderBottomWidth": 1,
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center"
	  },
	  "practicalnumber": {
	    "fontSize": 28,
	    "position": "absolute",
	    "width": 350,
	    "height": 50,
	    "top": 25,
	    "right": 25,
	    "textAlign": "right",
	    "placeholderColor": "#c5c5c5"
	  },
	  "star": {
	    "color": "#e2262a"
	  },
	  "starcharacter": {
	    "fontSize": 28,
	    "color": "#7e7e7e",
	    "marginLeft": 16,
	    "position": "absolute",
	    "top": 29,
	    "left": 15
	  },
	  "address": {
	    "height": 86,
	    "paddingLeft": 24,
	    "backgroundColor": "#ffffff"
	  },
	  "addresscontain": {
	    "height": 86,
	    "borderBottomStyle": "solid",
	    "borderBottomColor": "#dfdfdf",
	    "borderBottomWidth": 1,
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center"
	  },
	  "chooseaddress": {
	    "fontSize": 28,
	    "marginRight": 20
	  },
	  "supplementaryinfor": {
	    "height": 70,
	    "backgroundColor": "#f4f4f4",
	    "flexDirection": "row",
	    "alignItems": "center",
	    "paddingLeft": 25
	  },
	  "supplementarychar": {
	    "color": "#c5c5c5",
	    "fontSize": 24
	  },
	  "borderline": {
	    "width": 750,
	    "height": 0.5,
	    "backgroundColor": "#ffffff"
	  },
	  "linecontain": {
	    "height": 0.5,
	    "backgroundColor": "#dfdfdf",
	    "width": 725,
	    "marginLeft": 25
	  },
	  "btn": {
	    "width": 648,
	    "height": 90,
	    "marginLeft": 51,
	    "marginBottom": 32,
	    "fontSize": 36
	  },
	  "arrow-icon": {
	    "marginRight": 26
	  },
	  "rightchoosearea": {
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center"
	  },
	  "autoInputPhone": {
	    "paddingTop": 10,
	    "paddingBottom": 20
	  }
	}

/***/ }),
/* 127 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _itView = __webpack_require__(128);

	var _itView2 = _interopRequireDefault(_itView);

	var _hint = __webpack_require__(116);

	var _hint2 = _interopRequireDefault(_hint);

	var _infoCell = __webpack_require__(88);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _ccButton = __webpack_require__(104);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	var _uiButton = __webpack_require__(60);

	var _uiButton2 = _interopRequireDefault(_uiButton);

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _textareamoudle = __webpack_require__(132);

	var _textareamoudle2 = _interopRequireDefault(_textareamoudle);

	var _ccInput = __webpack_require__(136);

	var _ccInput2 = _interopRequireDefault(_ccInput);

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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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
	var eventModule = weex.requireModule('event'); // 自定义模块, 要求event字段与原生一致
	var modal = weex.requireModule('modal');

	exports.default = {
	    components: { itView: _itView2.default, hint: _hint2.default, infoCell: _infoCell2.default, ccButton: _ccButton2.default, uiButton: _uiButton2.default, ccImage: _ccImage2.default, textareamoudle: _textareamoudle2.default, ccInput: _ccInput2.default },
	    data: function data() {
	        return {
	            area: '请选择地区',
	            areaheight: 100,
	            specificarea: '',
	            mapStyle: {
	                width: '650px',
	                height: '90px',
	                backgroundColor: '#c5c5c5',
	                position: 'fixed',
	                left: '50px',
	                bottom: '32px',
	                flexDirection: 'row',
	                justifyContent: 'center',
	                alignItems: 'center',
	                borderRadius: '5px'
	            },
	            areastyle: {
	                color: '#c5c5c5'
	            },
	            isShowAgreeBtn: true,

	            imgType: '',

	            // 标记是否输入完成(获取/失去焦点时变化)
	            isDidInputMoney: false,
	            formatMoney: '',
	            inputMoney: '',

	            // 标记是否弹出键盘
	            isShowKeyBorad: false,

	            // 控制是否可输入
	            disableInput: false

	        };
	    },

	    methods: {
	        choosearea: function choosearea() {

	            var that = this;
	            Vue.NaviHelper.push('weex/common?id=commission&path=router_choosearea', { title: '选择地区' }, 'channel_pushChooseArea', function (r) {
	                that.area = '上海 ' + r.result.shanghaiarea;
	                that.$store.state.commApply.shanghaiArea = r.result.shanghaiarea;
	                that.areastyle.color = "#303030";
	            });
	        },
	        submlitinfor: function submlitinfor() {

	            console.log('comsAmt', this.$store.state.commApply.comsAmt);
	            console.log('houseHoldAddress', this.$store.state.commApply.houseHoldAddress);
	            console.log('isUpload', this.$store.state.commApply.isUpload);
	            console.log('isUploadconfirm', this.$store.state.commApply.isUploadconfirm);

	            if (this.isClick) {
	                var that = this;
	                that.$store.dispatch('COMMISSION_SAVE_COMMISSIONINFO', {
	                    imgFile: 'JIJJI', callback: function callback() {
	                        Vue.NaviHelper.render('waitreview', '等待审核');
	                    }
	                });
	            }
	        },
	        uploadimg: function uploadimg() {
	            var that = this;
	            eventModule.openCameraCallback(function (e) {
	                that.$store.dispatch('COMMISSION_UPLOAD_PROTOCL', { imgFile: e.result, imageType: 'YJPROTOCOL' });
	            });
	        },
	        uploadconfirm: function uploadconfirm() {
	            var that = this;
	            eventModule.openCameraCallback(function (e) {
	                that.$store.dispatch('COMMISSION_UPLOAD_PROTOCL', { imgFile: e.result, imageType: 'YJCONFIRM' });
	            });
	        },
	        textwrap: function textwrap(e) {
	            e.value = e.value.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	            this.specificarea = e.value;
	            this.$store.state.commApply.houseHoldAddress = e.value;
	        },
	        mailAddress: function mailAddress(e) {
	            e.value = e.value.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	            this.$store.state.commApply.mailAddress = e.value;
	        },
	        workAddress: function workAddress(e) {
	            e.value = e.value.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	            this.$store.state.commApply.workAddress = e.value;
	        },
	        workPhone: function workPhone(e) {
	            var inputValue = e.value;
	            var inputLength = e.value.length;
	            var keyValue = inputValue.substring(inputLength - 1, inputLength);
	            if (isNaN(keyValue)) {
	                e.value = e.value.replace(keyValue, "0");
	            }
	            this.$store.state.commApply.workPhone = e.value;
	        },
	        workName: function workName(e) {
	            e.value = e.value.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
	            this.$store.state.commApply.workName = e.value;
	        },

	        // -------------输入金额 - 监听方法
	        inputMoneyFocus: function inputMoneyFocus() {
	            console.log('focus: ');

	            this.isDidInputMoney = false;

	            if (this.$store.state.commApply.comsAmt != '') {
	                if (this.$store.state.commApply.comsAmt == 0) {
	                    this.$store.state.commApply.comsAmt = '';
	                } else {

	                    //                        this.inputMoney = this.$store.state.commApply.comsAmt
	                    var num = String(Number(this.$store.state.commApply.comsAmt).toFixed(2));
	                    var re = /(-?\d+)(\d{3})/;
	                    while (re.test(num)) {
	                        num = num.replace(re, "$1,$2");
	                    }
	                    this.formatMoney = num;

	                    console.log('333333333: ', this.inputMoney);
	                }
	            } else {
	                //如果聚焦, 就清空
	                this.inputMoney = '';
	                this.$store.state.commApply.comsAmt = '';
	            }
	        },

	        inputMoneyBlur: function inputMoneyBlur() {
	            console.log('blur: ');

	            this.isDidInputMoney = true;
	            //
	            if (this.$store.state.commApply.comsAmt != '') {
	                if (this.$store.state.commApply.comsAmt == 0) {
	                    this.$store.state.commApply.comsAmt = '';
	                } else {
	                    var num = String(Number(this.$store.state.commApply.comsAmt).toFixed(2));
	                    var re = /(-?\d+)(\d{3})/;
	                    while (re.test(num)) {
	                        num = num.replace(re, "$1,$2");
	                    }
	                    this.formatMoney = num;
	                }
	            } else {
	                this.formatMoney = '';
	            }
	        },
	        comsAmt: function comsAmt(e) {
	            console.log('input: ', e.value, this.inputMoney, e.value.substring(e.value.length - 1, e.value.length)

	            // 注意: 安卓端bug - 多走一遍该监听
	            );if (e.value == '' || this.isDidInputMoney) {
	                this.$store.state.commApply.comsAmt = this.inputMoney;
	                return;
	            }

	            var lastChar = e.value.substring(e.value.length - 1, e.value.length);
	            if (isNaN(lastChar)) {
	                //                    this.disableInput = true
	                //                    Vue.TipHelper.showHub('1', '请输入数字')
	                //                    this.disableInput = false;
	            }

	            e.value = e.value.replace(/\D/g, '0' // 非字符限制

	            );if (e.value.substring(0, 1) == 0) {
	                e.value = 1;
	            }

	            this.$store.state.commApply.comsAmt = this.inputMoney = e.value;
	        },
	        didInputMoney: function didInputMoney(e) {
	            console.log('change: ', e.value);
	            if (this.$store.state.commApply.comsAmt == '') {
	                e.value = '';
	            } else {}
	        },

	        onShowKeyBoard: function onShowKeyBoard(e) {
	            if (this.$store.state.env.platform == 'android') {
	                this.isShowKeyBorad = true;
	                //                    dom.scrollToElement(e.target, {})
	            }
	        },
	        onHideKeyBoard: function onHideKeyBoard() {
	            this.isShowKeyBorad = false;
	        },

	        // 滚动(这里没有用到)
	        goto: function goto(e) {

	            //                const dom = weex.requireModule('dom')
	            //                const el = this.$refs[e];
	            //                dom.scrollToElement(el, {})
	        }
	    },
	    computed: {
	        isClick: function isClick() {

	            if (this.$store.state.commApply.comsAmt && this.$store.state.commApply.houseHoldAddress && this.area != '请选择地区' && this.$store.state.commApply.isUpload && this.$store.state.commApply.isUploadconfirm) {
	                return true;
	            } else {
	                return false;
	            }
	        },
	        autoInputStyle: function autoInputStyle() {
	            var _$store$state$env = this.$store.state.env,
	                platform = _$store$state$env.platform,
	                scale = _$store$state$env.scale;

	            // 做适配

	            if (platform == 'iOS') {
	                return {
	                    paddingTop: 10 / scale * 2,
	                    paddingBottom: 10 / scale * 2
	                };
	            } else {
	                return {
	                    paddingTop: 10,
	                    paddingBottom: 20
	                };
	            }
	        }
	    },
	    created: function created() {},
	    mounted: function mounted() {

	        var that = this;
	        that.$store.dispatch('COMMISSION_INIT_COMMISSIONINFO', {
	            callback: function callback(r) {
	                // 带入金额, 并格式化
	                if (that.$store.state.commApply.comsAmt != '') {
	                    that.isDidInputMoney = true;
	                    var num = String(Number(that.$store.state.commApply.comsAmt).toFixed(2));
	                    var re = /(-?\d+)(\d{3})/;
	                    while (re.test(num)) {
	                        num = num.replace(re, "$1,$2");
	                    }
	                    that.formatMoney = num;
	                    that.inputMoney = that.$store.state.commApply.comsAmt;

	                    console.log('callback: ', that.$store.state.commApply.comsAmt, that.inputMoney);
	                }
	                // 带入物业地址
	                if (that.$store.state.commApply.shanghaiArea != '') {
	                    that.area = '上海 ' + that.$store.state.commApply.shanghaiArea;
	                    that.areastyle.color = "#303030";
	                }
	            }
	        });
	    },

	    watch: {
	        inputMoney: function inputMoney(newV, oldV) {
	            console.log('watch: ', newV, oldV);
	        }
	    }

	};

/***/ }),
/* 128 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(129)
	)

	/* script */
	__vue_exports__ = __webpack_require__(130)

	/* template */
	var __vue_template__ = __webpack_require__(131)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/itView.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-382d74b2"
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
/* 129 */
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
/* 130 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _ccImage = __webpack_require__(48);

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
/* 131 */
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
/* 132 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(133)
	)

	/* script */
	__vue_exports__ = __webpack_require__(134)

	/* template */
	var __vue_template__ = __webpack_require__(135)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/lch/textareamoudle.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-521935ff"
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
/* 133 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF",
	    "paddingLeft": 30,
	    "paddingRight": 40,
	    "paddingTop": 30,
	    "paddingBottom": 30,
	    "placeholderColor": "red"
	  },
	  "area": {
	    "fontSize": 28,
	    "color": "#303030",
	    "lineHeight": 40,
	    "placeholderColor": "#c5c5c5"
	  }
	}

/***/ }),
/* 134 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _props;

	var _ccImage = __webpack_require__(48);

	var _ccImage2 = _interopRequireDefault(_ccImage);

	var _ccLine = __webpack_require__(91);

	var _ccLine2 = _interopRequireDefault(_ccLine);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; } //
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	    components: { ccImage: _ccImage2.default, ccLine: _ccLine2.default },
	    props: (_props = {
	        //文本域里的内容
	        areatext: {
	            type: String,
	            default: ''
	        },
	        //placeholder的值
	        placeholder: {
	            type: String,
	            default: ''
	        },
	        //最大可输入文字
	        maxlength: {
	            type: String,
	            default: ''
	        },
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
	        }
	    }, _defineProperty(_props, 'placeholder', {
	        type: String,
	        default: '请输入...'
	    }), _defineProperty(_props, 'textValue', {
	        type: String,
	        default: ''
	    }), _defineProperty(_props, 'isLineShow', {
	        type: Boolean,
	        default: true
	    }), _defineProperty(_props, 'isHintShow', {
	        type: Boolean,
	        default: false
	    }), _defineProperty(_props, 'maxLength', {
	        type: Number
	    }), _defineProperty(_props, 'onClick', {
	        type: Function
	    }), _defineProperty(_props, 'onChange', {
	        type: Function
	    }), _defineProperty(_props, 'isCheckRes', { // '1',  '0'
	        type: String,
	        default: ''
	    }), _defineProperty(_props, 'onCheckYes', {
	        type: Function
	    }), _defineProperty(_props, 'onCheckNo', {
	        type: Function
	    }), _props),
	    data: function data() {
	        return {
	            placeholder: '5455454'
	        };
	    },

	    methods: {},
	    created: function created() {},

	    watch: {
	        // num:function() {
	        //     var num;
	        //       num = Number(this.servicecharge).toFixed(2).toString().split(".");  
	        //       num[0] = num[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");  
	        //       this.servicecharge= num.join(".");
	        //       return this.servicecharge;                   
	        // }

	    },
	    mounted: function mounted() {},
	    update: function update() {}
	};

/***/ }),
/* 135 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('textarea', {
	    staticClass: ["area"],
	    attrs: {
	      "rows": "1",
	      "placeholder": _vm.placeholder,
	      "maxlength": _vm.maxlength,
	      "value": (_vm.areatext)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.areatext = $event.target.attr.value
	      }, _vm.onChange],
	      "focus": _vm.onFocus
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 136 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(137)
	)

	/* script */
	__vue_exports__ = __webpack_require__(138)

	/* template */
	var __vue_template__ = __webpack_require__(139)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/common/components/cc/ccInput.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-45358310"
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
/* 137 */
/***/ (function(module, exports) {

	module.exports = {
	  "wrapper": {
	    "backgroundColor": "#FFFFFF",
	    "paddingLeft": 28,
	    "paddingRight": 28
	  },
	  "textarea": {
	    "paddingTop": 20,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "color": "#303030",
	    "placeholderColor": "#c5c5c5"
	  },
	  "textarea-tel": {
	    "height": 60,
	    "paddingTop": 20,
	    "fontFamily": "'PingFangSC-Regular'",
	    "fontSize": 28,
	    "justifyContent": "center",
	    "alignItems": "center",
	    "color": "#303030",
	    "placeholderColor": "#c5c5c5"
	  },
	  "textarea-tel-right": {
	    "textAlign": "right"
	  }
	}

/***/ }),
/* 138 */
/***/ (function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = _defineProperty({
	    props: {
	        isRight: {
	            type: Boolean,
	            default: false
	        },
	        inputType: {
	            type: String,
	            default: 'text'
	        },
	        inputValue: {
	            type: String,
	            default: ''
	        },
	        placeholder: {
	            type: String,
	            default: ''
	        },
	        maxLength: {
	            type: Number,
	            default: 50
	        },
	        onInput: {
	            type: Function
	        },
	        onChange: {
	            type: Function
	        },
	        onFocus: {
	            type: Function
	        },
	        onBlur: {
	            type: Function
	        },
	        onReturn: {
	            type: Function
	        }
	    },
	    components: {},
	    data: function data() {
	        return {
	            rows: 1,
	            fontSize: 14
	        };
	    },

	    methods: {
	        // 没有这个属性
	        onkeyup: function onkeyup(e) {
	            if (e.value.substring(0, 1) == 0) {
	                e.value = 1;
	            } else {
	                e.value = e.value.replace(/\D/g, '0' // 非字符限制
	                );
	            }
	        },
	        getStrLeng: function getStrLeng(str) {
	            var realLength = 0;
	            var len = str.length;
	            var charCode = -1;
	            for (var i = 0; i < len; i++) {
	                charCode = str.charCodeAt(i);
	                if (charCode >= 0 && charCode <= 128) {
	                    realLength += 1;
	                } else {
	                    // 如果是中文则长度加2
	                    realLength += 2;
	                }
	            }
	            return realLength;
	        }
	    },
	    computed: {
	        //            autoStyle: function () {
	        //                return {height: 30 * this.rows}
	        //            }
	    },
	    mounted: function mounted() {},

	    watch: {
	        inputValue: function inputValue(newV, oldV) {

	            var singleRowLength = Math.round((750 - 28 * 2) / this.fontSize) - 2;
	            var inputLength = newV.replace(/[\u0391-\uFFE5]/g, "aa").length;
	            this.rows = parseInt(inputLength / singleRowLength) + 1;

	            if (this.$store.state.os == 'iOS') {
	                var oldInputLength = oldV.replace(/[\u0391-\uFFE5]/g, "aa").length;
	                var oldRows = parseInt(oldInputLength / singleRowLength) + 1;
	                if (this.rows - oldRows > 1) {
	                    // 粘贴了大量字
	                    this.rows = this.rows - oldRows;
	                }
	            }

	            console.log('length:', this.rows, oldRows);
	        }
	    },

	    created: function created() {}
	}, 'mounted', function mounted() {});

/***/ }),
/* 139 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [(_vm.inputType == 'text') ? _c('textarea', {
	    staticClass: ["textarea"],
	    attrs: {
	      "maxlength": _vm.maxLength,
	      "placeholder": _vm.placeholder,
	      "rows": _vm.rows,
	      "returnKeyType": "done",
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onInput],
	      "focus": _vm.onFocus,
	      "blur": _vm.onBlur
	    }
	  }) : _c('textarea', {
	    staticClass: ["textarea-tel"],
	    class: ['textarea-tel' + (_vm.isRight == true ? '-right' : '')],
	    attrs: {
	      "type": "tel",
	      "maxlength": _vm.maxLength,
	      "placeholder": _vm.placeholder,
	      "rows": 1,
	      "returnKeyType": "done",
	      "value": (_vm.inputValue)
	    },
	    on: {
	      "input": [function($event) {
	        _vm.inputValue = $event.target.attr.value
	      }, _vm.onInput],
	      "focus": _vm.onFocus,
	      "blur": _vm.onBlur,
	      "change": _vm.onChange
	    }
	  })], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 140 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroll"]
	  }, [_c('div', [_c('div', {
	    staticClass: ["protocol"]
	  }, [_c('div', {
	    staticClass: ["protocolcontain"]
	  }, [_c('text', {
	    staticClass: ["protocolleft"]
	  }, [_vm._v("居间服务协议")]), (this.$store.state.commApply.isUpload) ? _c('text', {
	    staticClass: ["alreadyupload"]
	  }, [_vm._v("已上传")]) : _vm._e(), _c('ccImage', {
	    staticClass: ["uploadedicon"],
	    attrs: {
	      "src": this.$store.state.commApply.isUpload ? 'WXLocal/wx_camera_dark' : 'WXLocal/wx_camera_light',
	      "onClick": _vm.uploadimg
	    }
	  })], 1)]), _c('div', {
	    staticClass: ["protocol"]
	  }, [_c('div', {
	    staticClass: ["protocolcontain"]
	  }, [_c('text', {
	    staticClass: ["protocolleft"]
	  }, [_vm._v("佣金确认书")]), (this.$store.state.commApply.isUploadconfirm) ? _c('text', {
	    staticClass: ["alreadyupload"]
	  }, [_vm._v("已上传")]) : _vm._e(), _c('ccImage', {
	    staticClass: ["uploadedicon"],
	    attrs: {
	      "src": this.$store.state.commApply.isUploadconfirm ? 'WXLocal/wx_camera_dark' : 'WXLocal/wx_camera_light',
	      "onClick": _vm.uploadconfirm
	    }
	  })], 1)]), _c('div', {
	    staticClass: ["commission"]
	  }, [_c('div', {
	    staticClass: ["commissioncontain"]
	  }, [_c('text', {
	    staticClass: ["star"]
	  }, [_vm._v("*")]), _c('text', {
	    staticClass: ["starcharacter"]
	  }, [_vm._v("实际佣金金额")]), _c('input', {
	    staticClass: ["practicalnumber"],
	    attrs: {
	      "type": "tel",
	      "disabled": _vm.disableInput,
	      "placeholder": "如实填写有利于审批通过",
	      "maxlength": _vm.isDidInputMoney ? 20 : 10,
	      "value": (_vm.isDidInputMoney ? _vm.formatMoney : _vm.inputMoney)
	    },
	    on: {
	      "focus": _vm.inputMoneyFocus,
	      "blur": _vm.inputMoneyBlur,
	      "input": [function($event) {
	        _vm.isDidInputMoney ? _vm.formatMoney : _vm.inputMoney = $event.target.attr.value
	      }, _vm.comsAmt],
	      "change": _vm.didInputMoney
	    }
	  })])]), _c('div', {
	    staticClass: ["address"]
	  }, [_c('div', {
	    staticClass: ["addresscontain"]
	  }, [_c('text', {
	    staticClass: ["star"]
	  }, [_vm._v("*")]), _c('text', {
	    staticClass: ["starcharacter"]
	  }, [_vm._v("交易房屋物业地址")]), _c('div', {
	    staticClass: ["rightchoosearea"],
	    on: {
	      "click": _vm.choosearea
	    }
	  }, [_c('text', {
	    staticClass: ["chooseaddress"],
	    style: _vm.areastyle
	  }, [_vm._v(_vm._s(_vm.area))]), _c('ccImage', {
	    staticClass: ["arrow-icon"],
	    attrs: {
	      "src": 'WXLocal/wx_arrow'
	    }
	  })], 1)])]), _c('ccInput', {
	    staticClass: ["autoInput"],
	    style: _vm.autoInputStyle,
	    attrs: {
	      "inputValue": this.$store.state.commApply.houseHoldAddress,
	      "placeholder": '输入填写具体地址，具体至门牌号 (120字以内)',
	      "maxLength": 120,
	      "onInput": _vm.textwrap
	    }
	  }), _vm._m(0), _c('ccInput', {
	    staticClass: ["autoInput"],
	    style: _vm.autoInputStyle,
	    attrs: {
	      "inputValue": this.$store.state.commApply.mailAddress,
	      "placeholder": '输入填写常用通讯地址，具体至门牌号 (120字以内)',
	      "maxLength": 120,
	      "onInput": _vm.mailAddress,
	      "onFocus": _vm.onShowKeyBoard,
	      "onBlur": _vm.onHideKeyBoard
	    }
	  }), _vm._m(1), _c('ccInput', {
	    staticClass: ["autoInput"],
	    style: _vm.autoInputStyle,
	    attrs: {
	      "inputValue": this.$store.state.commApply.workAddress,
	      "placeholder": '输入工作单位地址，具体至门牌号 (120字以内)',
	      "maxLength": 120,
	      "onInput": _vm.workAddress,
	      "onFocus": _vm.onShowKeyBoard,
	      "onBlur": _vm.onHideKeyBoard
	    }
	  }), _vm._m(2), _c('ccInput', {
	    staticClass: ["autoInputPhone"],
	    attrs: {
	      "inputType": 'tel',
	      "inputValue": this.$store.state.commApply.workPhone,
	      "placeholder": '请输入工作单位联系电话 (最长不超过15位)',
	      "maxLength": 15,
	      "onInput": _vm.workPhone,
	      "onFocus": _vm.onShowKeyBoard,
	      "onBlur": _vm.onHideKeyBoard
	    }
	  }), _vm._m(3), _c('ccInput', {
	    staticClass: ["autoInput"],
	    style: _vm.autoInputStyle,
	    attrs: {
	      "inputValue": this.$store.state.commApply.workName,
	      "placeholder": '输入工作单位名称 (60字以内)',
	      "maxLength": 60,
	      "onInput": _vm.workName,
	      "onFocus": _vm.onShowKeyBoard,
	      "onBlur": _vm.onHideKeyBoard
	    }
	  })], 1), _c('div', {
	    style: {
	      height: this.$store.state.env.scale == 3 ? 250 : 200
	    }
	  }), _c('ccButton', {
	    staticClass: ["btn"],
	    attrs: {
	      "title": '确认提交',
	      "onClick": _vm.submlitinfor,
	      "isClick": _vm.isClick
	    }
	  })], 1)])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["supplementaryinfor"]
	  }, [_c('text', {
	    staticClass: ["supplementarychar"]
	  }, [_vm._v("补充以下信息可以加大您的分期申请通过率哦")])])
	},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["borderline"]
	  }, [_c('div', {
	    staticClass: ["linecontain"]
	  })])
	},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["borderline"]
	  }, [_c('div', {
	    staticClass: ["linecontain"]
	  })])
	},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["borderline"]
	  }, [_c('div', {
	    staticClass: ["linecontain"]
	  })])
	}]}
	module.exports.render._withStripped = true

/***/ }),
/* 141 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(142)
	)

	/* script */
	__vue_exports__ = __webpack_require__(143)

	/* template */
	var __vue_template__ = __webpack_require__(144)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commissioninstallment/choosearea.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-39175512"
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
/* 142 */
/***/ (function(module, exports) {

	module.exports = {
	  "declare": {
	    "height": 70,
	    "backgroundColor": "#f4f4f4",
	    "flexDirection": "row",
	    "alignItems": "center",
	    "paddingLeft": 25
	  },
	  "declaretext": {
	    "color": "#9a9a9a",
	    "fontSize": 24
	  },
	  "areacell": {
	    "height": 88
	  },
	  "contain": {
	    "height": 88,
	    "marginLeft": 25,
	    "flexDirection": "row",
	    "justifyContent": "space-between",
	    "alignItems": "center",
	    "borderBottomWidth": 1,
	    "borderBottomColor": "#f5f5f5",
	    "borderBottomStyle": "solid"
	  },
	  "areaname": {
	    "color": "#323232",
	    "fontSize": 28
	  },
	  "areaicon": {
	    "width": 14,
	    "height": 28,
	    "marginRight": 26
	  }
	}

/***/ }),
/* 143 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	   value: true
	});

	var _itView = __webpack_require__(128);

	var _itView2 = _interopRequireDefault(_itView);

	var _hint = __webpack_require__(116);

	var _hint2 = _interopRequireDefault(_hint);

	var _infoCell = __webpack_require__(88);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _ccButton = __webpack_require__(104);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	var _ccImage = __webpack_require__(48);

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

	exports.default = {
	   components: { itView: _itView2.default, hint: _hint2.default, infoCell: _infoCell2.default, ccButton: _ccButton2.default, ccImage: _ccImage2.default },
	   data: function data() {
	      return {
	         areas: []
	      };
	   },

	   methods: {
	      clickarea: function clickarea(d) {
	         eventModule.wxFirePushCallback('channel_pushChooseArea', { result: d });
	      }
	      // reload:function() {
	      //   window.location.reload();
	      // }
	      //             clickCommit: function () {
	      //                 if (!this.isClick) {
	      //                     return;
	      //                 }
	      // //                this.$router.push('identity')
	      //                 Vue.NaviHelper.push('root')

	      //             },
	      //             changeBankCard(isCreditCard) {
	      //                 console.log('changeBankCard:')
	      //                 this.isCreditCard = !this.isCreditCard
	      //             },
	      //             pushBankListPage() {
	      //                 console.log('pushBankListPage:')
	      //             },
	      //             inputCreditCardNumber(e) {
	      //                 console.log('inputCreditCardNumber:', e.value)
	      //             },
	      //             inputTelNumber(e) {
	      //                 console.log('inputTelNumber:', e.value)
	      //             }
	   },
	   created: function created() {},

	   computed: {},
	   mounted: function mounted() {

	      this.$store.dispatch('COMMISSION_SELECT_ADDRESS');
	      this.areas = this.$store.state.commApply.area;
	   }
	};

/***/ }),
/* 144 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_vm._m(0), _c('list', {
	    staticClass: ["list"]
	  }, _vm._l((this.$store.state.commApply.area), function(area, index) {
	    return _c('cell', {
	      staticClass: ["areacell"],
	      appendAsTree: true,
	      attrs: {
	        "append": "tree"
	      }
	    }, [_c('div', {
	      staticClass: ["contain"],
	      on: {
	        "click": function($event) {
	          _vm.clickarea({
	            shanghaiarea: area
	          })
	        }
	      }
	    }, [_c('div', [_c('text', {
	      staticClass: ["areaname"]
	    }, [_vm._v(_vm._s(area))])]), _c('div', [_c('ccImage', {
	      staticClass: ["areaicon"],
	      attrs: {
	        "src": 'WXLocal/wx_arrow'
	      }
	    })], 1)])])
	  }))])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["declare"]
	  }, [_c('text', {
	    staticClass: ["declaretext"]
	  }, [_vm._v("佣金分期现只支持上海地区")])])
	}]}
	module.exports.render._withStripped = true

/***/ }),
/* 145 */
/***/ (function(module, exports, __webpack_require__) {

	var __vue_exports__, __vue_options__
	var __vue_styles__ = []

	/* styles */
	__vue_styles__.push(__webpack_require__(146)
	)

	/* script */
	__vue_exports__ = __webpack_require__(147)

	/* template */
	var __vue_template__ = __webpack_require__(148)
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
	__vue_options__.__file = "/Users/x298017064010/Desktop/aopai/proj/ajqh_weex_new/ajqh_weex_new/src/commission/views/commissioninstallment/waitreview.vue"
	__vue_options__.render = __vue_template__.render
	__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
	__vue_options__._scopeId = "data-v-6d1e9b0a"
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
/* 146 */
/***/ (function(module, exports) {

	module.exports = {
	  "wait": {
	    "height": 270,
	    "marginTop": 165,
	    "flexDirection": "row",
	    "justifyContent": "center"
	  },
	  "waiticon": {
	    "width": 270,
	    "height": 270
	  },
	  "failicon": {
	    "width": 270,
	    "height": 270
	  },
	  "ineffectiveness": {
	    "marginTop": 50
	  },
	  "applyfail": {
	    "marginTop": 50
	  },
	  "firstline": {
	    "fontSize": 30,
	    "lineHeight": 42,
	    "color": "#303030",
	    "textAlign": "center",
	    "marginBottom": 24
	  },
	  "secondline": {
	    "fontSize": 28,
	    "lineHeight": 46,
	    "color": "#7e7e7e",
	    "marginLeft": 40,
	    "marginRight": 40,
	    "textAlign": "center"
	  },
	  "thirdline": {
	    "fontSize": 30,
	    "color": "#7e7e7e",
	    "textAlign": "center"
	  },
	  "refreshbutton": {
	    "width": 650,
	    "height": 90,
	    "borderRadius": 5,
	    "position": "fixed",
	    "left": 50,
	    "bottom": 30,
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "backgroundColor": "#e2262a"
	  },
	  "refreshtext": {
	    "color": "#ffffff",
	    "fontSize": 32
	  },
	  "reapplybutton": {
	    "width": 650,
	    "height": 90,
	    "borderRadius": 5,
	    "position": "fixed",
	    "left": 50,
	    "bottom": 30,
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "backgroundColor": "#e2262a"
	  },
	  "reapplytext": {
	    "color": "#ffffff",
	    "fontSize": 32
	  },
	  "twentyninebutton": {
	    "width": 650,
	    "height": 90,
	    "borderRadius": 5,
	    "position": "fixed",
	    "left": 50,
	    "bottom": 30,
	    "flexDirection": "row",
	    "justifyContent": "center",
	    "alignItems": "center",
	    "backgroundColor": "#c5c5c5"
	  },
	  "twentyninetext": {
	    "color": "#ffffff",
	    "fontSize": 32
	  }
	}

/***/ }),
/* 147 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _itView = __webpack_require__(128);

	var _itView2 = _interopRequireDefault(_itView);

	var _hint = __webpack_require__(116);

	var _hint2 = _interopRequireDefault(_hint);

	var _infoCell = __webpack_require__(88);

	var _infoCell2 = _interopRequireDefault(_infoCell);

	var _ccButton = __webpack_require__(104);

	var _ccButton2 = _interopRequireDefault(_ccButton);

	var _ccImage = __webpack_require__(48);

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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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
	    components: { itView: _itView2.default, hint: _hint2.default, infoCell: _infoCell2.default, ccButton: _ccButton2.default, ccImage: _ccImage2.default },
	    data: function data() {
	        return {};
	    },

	    methods: {
	        refreshonce: function refreshonce() {
	            var that = this;

	            that.$store.dispatch('COMMISSION_REFRESH_COMSSION', {
	                callback: function callback(r) {
	                    if (that.$store.state.debug) {
	                        // that.$store.state.commApply.auditStatus = '-1'
	                    }

	                    console.log('auditStatus: ', that.auditStatus);

	                    if (that.auditStatus == 2) {
	                        Vue.NaviHelper.render('cashimmediately', '申请分期');
	                    }
	                }
	            });
	        },
	        reapplay: function reapplay() {

	            this.$store.dispatch('COMMISSION_COFIRM_MODIFY_COMSINFO', {
	                callback: function callback(r) {
	                    Vue.NaviHelper.render('commissioninformation', '佣金信息');
	                }
	            });
	        }

	    },
	    created: function created() {},
	    mounted: function mounted() {
	        // 初始化
	        this.$store.dispatch('COMMISSION_REFRESH_COMSSION', {
	            callback: function callback(r) {
	                console.log('auditStatus: ', this.auditStatus);
	            }
	        });
	    },

	    computed: {
	        auditStatus: function auditStatus() {
	            console.log('11111111111111', this.$store.state.commApply.auditStatus, this.$store.state.commApply.lockDays);

	            if (this.$store.state.commApply.auditStatus == '1' || this.$store.state.commApply.auditStatus == '0') {
	                // reload
	                return '1';
	            } else if (this.$store.state.commApply.auditStatus == '-1' && this.$store.state.commApply.lockDays <= 0) {
	                // 电审拒绝， 重新申请资料
	                return '-2';
	            } else if (this.$store.state.commApply.auditStatus == '-1' && this.$store.state.commApply.lockDays > 0) {
	                // 电审拒绝， 进入倒计时
	                return '-1';
	            } else if (this.$store.state.commApply.auditStatus == '2') {
	                // 电神通过， 跳转分期页
	                return '2';
	            }
	        }
	    }
	};

/***/ }),
/* 148 */
/***/ (function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: ["wrapper"]
	  }, [_c('scroller', {
	    staticClass: ["scroll"]
	  }, [_c('div', {
	    staticClass: ["wait"]
	  }, [(_vm.auditStatus == 1) ? _c('ccImage', {
	    staticClass: ["waiticon"],
	    attrs: {
	      "src": 'WXLocal/wx_auth_applying'
	    }
	  }) : _vm._e(), (_vm.auditStatus == -1 || _vm.auditStatus == -2) ? _c('ccImage', {
	    staticClass: ["waiticon"],
	    attrs: {
	      "src": 'WXLocal/wx_auth_fail'
	    }
	  }) : _vm._e()], 1), (_vm.auditStatus == 1) ? _c('div', {
	    staticClass: ["ineffectiveness"]
	  }, [_c('text', {
	    staticClass: ["firstline"]
	  }, [_vm._v("您的申请已提交，请耐心等候。")]), _c('text', {
	    staticClass: ["secondline"]
	  }, [_vm._v("10:00-20:00提交的申请，预计50分钟后可查看结果，如50分钟后无反馈结果，可拨打客服电话。")])]) : (_vm.auditStatus == -1 || _vm.auditStatus == -2) ? _c('div', {
	    staticClass: ["applyfail"]
	  }, [_c('text', {
	    staticClass: ["firstline"]
	  }, [_vm._v("佣金分期申请失效")]), _c('text', {
	    staticClass: ["secondline"]
	  }, [_vm._v("根据您的实际情况，安家趣花暂时无法提供佣金分期服务。")])]) : _vm._e(), (_vm.auditStatus == 1) ? _c('div', {
	    staticClass: ["refreshbutton"],
	    on: {
	      "click": _vm.refreshonce
	    }
	  }, [_c('text', {
	    staticClass: ["refreshtext"]
	  }, [_vm._v("刷新")])]) : (_vm.auditStatus == -1) ? _c('div', {
	    staticClass: ["twentyninebutton"]
	  }, [_c('text', {
	    staticClass: ["twentyninetext"]
	  }, [_vm._v("可在" + _vm._s(this.$store.state.commApply.lockDays) + "天后进行再次申请")])]) : (_vm.auditStatus == -2) ? _c('div', {
	    staticClass: ["reapplybutton"],
	    on: {
	      "click": _vm.reapplay
	    }
	  }, [_c('text', {
	    staticClass: ["reapplytext"]
	  }, [_vm._v("修改资料重新申请")])]) : _vm._e()])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true

/***/ }),
/* 149 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	    value: true
	});

	var _RouterEvent = __webpack_require__(150);

	var _RouterEvent2 = _interopRequireDefault(_RouterEvent);

	var _ParamEvent = __webpack_require__(151);

	var _ParamEvent2 = _interopRequireDefault(_ParamEvent);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	    routerEvent: _RouterEvent2.default,
	    paramEvent: _ParamEvent2.default
	};

/***/ }),
/* 150 */
/***/ (function(module, exports, __webpack_require__) {

	'use strict';

	var _index = __webpack_require__(38);

	var _index2 = _interopRequireDefault(_index);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var globalEvent = weex.requireModule('globalEvent');

	globalEvent.addEventListener("router_banner", function (e) {
	    _index2.default.push('banner');
	    console.log('push banner successed');
	});

	//-----------佣金分期1----------------
	globalEvent.addEventListener("router_comStage", function (e) {
	    _index2.default.push('comStage');
	    console.log('push router_comStage successed');
	});
	globalEvent.addEventListener("router_promoteLimit", function (e) {
	    _index2.default.push('promoteLimit');
	    console.log('push router_promoteLimit successed');
	});
	globalEvent.addEventListener("router_providentFund", function (e) {
	    _index2.default.push('providentFund');
	    console.log('push router_providentFund successed');
	});
	globalEvent.addEventListener("router_security", function (e) {
	    _index2.default.push('security');
	    console.log('push router_security successed');
	});
	globalEvent.addEventListener("router_selectProv", function (e) {
	    _index2.default.push('selectProv');
	    console.log('push router_selectProv successed');
	}

	// ----------佣金分期2-------------
	);globalEvent.addEventListener("router_cashimmediately", function (e) {
	    _index2.default.push('cashimmediately');
	    console.log('push cashimmediately successed');
	});

	// 佣金信息记录
	globalEvent.addEventListener("router_commissioninformation", function (e) {
	    _index2.default.push('commissioninformation');
	    console.log('push commissioninformation successed');
	});
	// 选择地区
	globalEvent.addEventListener("router_choosearea", function (e) {
	    _index2.default.push('choosearea');
	    console.log('push choosearea successed');
	});
	// 等待审核
	globalEvent.addEventListener("router_waitreview", function (e) {
	    _index2.default.push('waitreview');
	    console.log('push waitreview successed');
	});
	// 合同
	globalEvent.addEventListener("router_applicationstagecontract", function (e) {
	    _index2.default.push('applicationstagecontract');
	    console.log('push applicationstagecontract successed');
	});

/***/ }),
/* 151 */
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