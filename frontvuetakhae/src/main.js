import Vue from "vue";
import App from "./App.vue";
import router from "./router";
// 새로 추가한 것
// BootstrapVue + BootstrapVueIcons
import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
// fontawesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUserSecret } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

// Cookies
import VueCookies from "vue-cookies";

import vuetify from './plugins/vuetify';
Vue.use(VueCookies);
library.add(faUserSecret);

Vue.component("font-awesome-icon", FontAwesomeIcon);

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  vuetify,
  render: (h) => h(App)
}).$mount("#app");
