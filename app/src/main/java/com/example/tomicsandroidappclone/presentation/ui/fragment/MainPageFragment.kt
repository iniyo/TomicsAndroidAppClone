package com.example.tomicsandroidappclone.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomicsandroidappclone.R
import com.example.tomicsandroidappclone.databinding.FragmentMainPageBinding
import com.example.tomicsandroidappclone.presentation.ui.adapter.MainPageAdapter
import com.example.tomicsandroidappclone.presentation.ui.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding

    // lateinit과 lazy의 공통점 : ?일수 없다, 나중에 값을 초기화 한다.
    // lateinit과 lazy의 차이점 : late는 var로만 by lazy는 val로만 선언 된다. ()
    // 즉, 초기화 이후 값이 변하는 유무에 따라 사용하며 구분하면 lateinit: 값이 바뀔때, by lazy: 읽기 전용일때
    private val viewModel: MainFragmentViewModel by lazy { ViewModelProvider(this)[MainFragmentViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }

    }
    companion object {
        fun newInstance() = MainPageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_page, container, false)
        binding.mainFragmentViewModel = viewModel

        // 멍청하게도 fetchWebtoons에 webtoonsInfo에 대한 초기화를 안 해두고 webtoonsInfo에 observer를 달았다.
        // webtoonsInfo의 observer가 동작하지 않는 이유를 장장 1시간 30분 동안 찾았다.
        // coroutine으로 작성했으니까 비동기 처리를 해야 한다. 이 점 유의하고 코딩하자..
        viewModel.fetchWebtoons() // webtoon
        /*viewModel.getSeviceAndUpdateDayByWebtoon("kakao",  "mon")*/
        binding.run {
            Log.d("TAG", "binding run")
            viewModel.webtoonsInfo.observe(viewLifecycleOwner) { data ->
                Log.d("TAG", "binding observer1" + data[0].title)
                Log.d("TAG", "binding observer2" + data[0].service)
                // adapter 초기화는 data를 받아오고 실행되어야 한다. 따라서 couroutine이후에 adapter 초기화 코드를 실행.
                // 문제점 = 받아오는 속도가 비교적 느려 보인다. (느리다.)
                setAdapter()
            }
        }
        return binding.root
    }
    private fun init(){

        /*val tagList = resources.getStringArray(R.array.tag_list)
        viewModel.setWebtton(tagList)*/

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setAdapter() {
        // binding.root.context : 말 그대로 root view의 context(맥락)
        // context는 LifeCycle과 연결되어 있고(!) Singleton임. (실행 중 하나의 객체만 가짐)
        // (!) ApplicationContext, ActivityContext, FragmentContext 는 이름에 있는 LifeCycle을 가짐.
        val webtoons = viewModel.getWebtoons()
        Log.d("TAG", "initAdapter: " + webtoons[0].title)
        val mainPageAdapter = context?.let { MainPageAdapter(webtoons, it) }

        binding.rvMainPage.apply {
            Log.d("TAG", "initAdapter rvMain " + webtoons[0].title)
            this.adapter = mainPageAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }

    }

}


