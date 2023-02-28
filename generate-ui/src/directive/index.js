import clipboard from './module/clipboard'

// Vue：自定义指令：https://v2.cn.vuejs.org/v2/guide/custom-directive.html
const install = function(Vue) {

  Vue.directive('clipboard', clipboard)

}

if (window.Vue) {
  Vue.use(install); // eslint-disable-line
}

export default install
