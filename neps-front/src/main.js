import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'font-awesome/css/font-awesome.min.css'

router.beforeEach(function(to,from,next){
    let supervisor = sessionStorage.getItem('supervisor');
    if(to.path=='/selectGrid'||to.path=='/selectAqi'||to.path=='/feedbackList'){
        if(supervisor==null){
            router.push('/login');
        }
    }
    next();
});

createApp(App).use(router).use(ElementPlus).mount('#app')
