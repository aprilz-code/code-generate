import Vue from 'vue'
import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { parseTime, resetForm, handleTree, addBeginAndEndTime, divide} from "@/utils/ruoyi";

// 默认点击背景不关闭弹窗
// Element.Dialog.props.closeOnClickModal.default = false
Vue.use(Element)
Vue.use(directive)
Vue.use(plugins)


Vue.prototype.parseTime = parseTime
Vue.prototype.handleTree = handleTree

Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    render: h => h(App)
})
