import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'

router.beforeEach(function(to,from,next){
    let admins = sessionStorage.getItem('admins');
    if(!(to.path=='/'||to.path=='/login')){
        if(admins==null){
            router.push('/login');
        }
    }
    next();
});

createApp(App).use(store).use(router).use(ElementPlus).mount('#app')
