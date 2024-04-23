package com.example.kotlin_zhcs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.kotlin_zhcs.databinding.ActivityMainBinding


//TODO 全部服务，地铁
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //设置ID全局获取
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 隐藏标题栏
         supportActionBar?.hide()

        //设置动画
        binding.navView.animation = App.getAnimationBottomAndTop()

        //获取导航器
        //val navcontroller = findNavController(this，R.id.nav_host_fragment)
        //使用navController 设置导航监听，设置导航设置，设置启动页面都可以。
        //我门一般都会使用监听当前在那个fragment或者重新其实初始化的fragment
        //fragment中导航直接获取 findNavController即可
        //！！但要特别注意:fragment 的name属性必须是androidx.navigation.fragment.NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment)   //这是fragment标签

        //如果要自定义哪些目标被视为顶级目标，则可以将一组目标ID传递给构造函数
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_poverty,
            R.id.navigation_news,
            R.id.navigation_notifications
        ))

        /*这两个联动了软件上方的名字，每次点击导航栏，那个很粗的框框名字也会跟着变化，这里不能在Xml文件中将标题栏隐藏起来，只能使用代码的方式隐藏*/
        /*setupActionBarWithNavController(navController, appBarConfiguration)设置 ActionBar 以使用 NavController。
        它启用在按下操作栏中的“向上”按钮时导航到上一个目标的默认行为。该参数是一个可选配置，用于确定导航图中的顶级目标。
        它有助于根据当前目的地正确处理向上按钮导航。appBarConfiguration

        binding.navView.setupWithNavController(navController)将 NavigationView 配置为与 NavController 一起使用。这
        意味着当单击 NavigationView 中的菜单项时，它将导航到导航图中指定的相应目标。
        这两行对于在应用程序中集成 Navigation 组件都很重要。第一行确保 ActionBar 的正确行为，而第二行允许从 NavigationView 进行导航。*/
//        setupActionBarWithNavController(navController, appBarConfiguration)   //使用这个关联不能在AndroidManifest中关闭导航栏了
        binding.navView.setupWithNavController(navController)
        /*请注意 menu文件中每一项(item)的id 和 navigation 中每一项(fragment)的id必须一致 */
        /*感觉这个有点想ViewPager绑定TabLayout一样，都是setupWithXxx 他这个括号里面是fragment,而那个里面是ViewPager*/

    }
}






















