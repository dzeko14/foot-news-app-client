package my.dzeko.footapp.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import my.dzeko.footapp.R
import my.dzeko.footapp.presenter.SplashScreenPresenter
import my.dzeko.footapp.view.interfaces.SplashScreenView
import javax.inject.Inject



class SplashScreenFragment : DaggerFragment(), SplashScreenView {

    @Inject lateinit var mPresenter: SplashScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unsubscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onFragmentStarted()
    }

    override fun navigateToListFragment() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.enter_scale_animation)
        animation.setAnimationListener(object : OnAnimationEndListener() {
            override fun onAnimationEnd(animation: Animation?) {
                SplashScreenFragmentDirections.actionSplashScreenFragmentToNewsListFragment()
                NavHostFragment.findNavController(this@SplashScreenFragment)
                    .navigate(R.id.action_splashScreenFragment_to_newsListFragment)
            }
        })
        view?.startAnimation(animation)

    }

    private abstract class OnAnimationEndListener : Animation.AnimationListener {

        override fun onAnimationRepeat(animation: Animation?) {
            //
        }

        override fun onAnimationStart(animation: Animation?) {
            //
        }
    }
}
