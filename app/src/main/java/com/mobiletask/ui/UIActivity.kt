package com.mobiletask.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiletask.R
import com.mobiletask.adapter.AvatarAdapter
import com.mobiletask.adapter.CourseAdapter
import com.mobiletask.adapter.FilterAdapter
import com.mobiletask.databinding.ActivityUiBinding


class UIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUiBinding
    private var imgList = ArrayList<String>()
    private var filterList = ArrayList<String>()
    private var courseList = ArrayList<String>()

    private val scrollInterval = 3000L // Interval in milliseconds (3 seconds)
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var courseAdapter: CourseAdapter

    private val scrollRunnable = object : Runnable {
        override fun run() {
            currentIndex = (currentIndex + 1) % courseAdapter.itemCount
            binding.viewPagerCourses.setCurrentItem(currentIndex, true)
            handler.postDelayed(this, scrollInterval)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui)
        initView()
        setUpListeners()
    }

    private fun initView() {
        imgList.add(
            0,
            "https://s3-alpha-sig.figma.com/img/769d/bc82/a55bea1959a0fa715050ad9a79481283?Expires=1730678400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=fYZcpQ6c6u3ei6Ir41D2TUOBbtDgW3QFqrLFpaajVOd1bUT98eJnmDI97zB3rDRkvfXjBPI7TaUyGTVaCfkIHKIF2xskCV1YIgmzdFKYO168LDtXfWuj6kC6qSm7BzcV8WshHqIrxc2bd0gSxAGoPXUIwAguZ7CU3x~uqMNv-uAinZwhGW33IvT3-HjcuKBe6Y3OfXnYpNlf8zXdXEHjV0CSaeecgXtsAohZn450ScxmFPlciEcOWKj-Doh8RO30lAUJOGkSXLU252ZCYMgh4uOw6NQd4qAm5UuP6VlKANRQsOeSoM1PHbPCCRt0sftOiemD0hj28GHs86Gk2EsFyA__"
        )
        imgList.add(
            1,
            "https://s3-alpha-sig.figma.com/img/6bad/30d4/accf44b34248713b5f617b87cef84150?Expires=1730678400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=RlOavsMieWdEFDB5OO0evKWktALydf-rG1fDPgkVmY2Gz8UhvT-J9EsiirEkNqIIGI~kU6XX-a4EIvPvz5l3T7HpnXDgQNL57mWBigwra7apA4Cbz-yy4mseKG30eSbeHE~BwnZwPOeXoY~~RqeKKdd7kcbe4rKKU4qEgXSv6DjTKr4Ea19LgmVAWF7hpDHBNzGASGENplXTUcMF2yrKfF5Ex621oL0nEBrwI5DCZX~Ox3khGYnEj6~0Ge9hqf9RcnNO1LiMvEeOaYTmMVAsEr7scHkgyILY3JT3s06zy9eBwVdfmavxjTpueYPzOOf1TeXj~nQYReSdtge3JZMjWQ__"
        )
        imgList.add(
            2,
            "https://s3-alpha-sig.figma.com/img/52df/b937/d126ebfa3975d2b835b0f444cb257c42?Expires=1730678400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=LYm~1hpbkfmJ5edViblLcfC8pYg4gb9fa06Aeg8Xdd8h3FaFvYW8kxxWLaLrRkE-p84eTQmND1ZjGoGSnyP8tSWcN311TRHouN7obCLW736ZUvb0huhnQgsSwYSdbTmVlHlz5jbJgWX53WIoppFMvJJmkEA6uus1Hs6KcDSdBb49Cl~NIRv0WW9qtWiNPmHyyHg1Wb1foDBr~i-PBu4YfAT8PSQokt5Qn4FNfj8980aycZ~z9TaLu-ZCQUrKINoSTyFefXso--GfK8mja4BDzY6S8pS15l7tTu-JXq-jHpODhbOLIvvet6la0~STGGGcF3YlwNIF5rSWU9xzHiDA7g__"
        )
        imgList.add(
            3,
            "https://s3-alpha-sig.figma.com/img/77e2/b654/94387eafcf1506faba7e10f1daf9bde5?Expires=1730678400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=MWp6DT0JnP~XawU2om7b6uQY1FifaFVaOVSYXWzBsnV29aHlkJLQyMc5O04zEkZyREt1UHaEScKm10n2WGdpT9XZM1eL4pDWviucUSrY4GsCUoeGwSoHywT6AzYPT38MjzyExonNL0AQ8fTHdwxQ-kB8BPl-ae8k35E-5Or7wXnQfFFC4p-jtFrnieBmNTSx4UQMezA2YdHUcc55okhaskLOeaZn4PGHmpKQVo03L9wo0w6XM1jLF111bGKAUnn9jqOoMfDhuMsJ2i21SPogRy6T71o1c6UTkgGYECB2boAUH9egnxCk6nq0x~zBCkRM0qKeJrp6quQ03aZsK3g8Og__"
        )
        imgList.add(
            4,
            "https://s3-alpha-sig.figma.com/img/6bad/30d4/accf44b34248713b5f617b87cef84150?Expires=1730678400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=RlOavsMieWdEFDB5OO0evKWktALydf-rG1fDPgkVmY2Gz8UhvT-J9EsiirEkNqIIGI~kU6XX-a4EIvPvz5l3T7HpnXDgQNL57mWBigwra7apA4Cbz-yy4mseKG30eSbeHE~BwnZwPOeXoY~~RqeKKdd7kcbe4rKKU4qEgXSv6DjTKr4Ea19LgmVAWF7hpDHBNzGASGENplXTUcMF2yrKfF5Ex621oL0nEBrwI5DCZX~Ox3khGYnEj6~0Ge9hqf9RcnNO1LiMvEeOaYTmMVAsEr7scHkgyILY3JT3s06zy9eBwVdfmavxjTpueYPzOOf1TeXj~nQYReSdtge3JZMjWQ__"
        )
        imgList.add(
            5,
            "https://s3-alpha-sig.figma.com/img/769d/bc82/a55bea1959a0fa715050ad9a79481283?Expires=1730678400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=fYZcpQ6c6u3ei6Ir41D2TUOBbtDgW3QFqrLFpaajVOd1bUT98eJnmDI97zB3rDRkvfXjBPI7TaUyGTVaCfkIHKIF2xskCV1YIgmzdFKYO168LDtXfWuj6kC6qSm7BzcV8WshHqIrxc2bd0gSxAGoPXUIwAguZ7CU3x~uqMNv-uAinZwhGW33IvT3-HjcuKBe6Y3OfXnYpNlf8zXdXEHjV0CSaeecgXtsAohZn450ScxmFPlciEcOWKj-Doh8RO30lAUJOGkSXLU252ZCYMgh4uOw6NQd4qAm5UuP6VlKANRQsOeSoM1PHbPCCRt0sftOiemD0hj28GHs86Gk2EsFyA__"
        )
        binding.rvAvatars.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter: AvatarAdapter by lazy {
            AvatarAdapter(
                imgList,
            )
        }
        binding.rvAvatars.adapter = adapter

        filterList.add(0,"All")
        filterList.add(1,"UI/UX")
        filterList.add(2,"Illustration")
        filterList.add(3,"3D Animation")
        filterList.add(4,"2D Animation")

        binding.rvFilters.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val filterAdapter: FilterAdapter by lazy {
            FilterAdapter(
                filterList,
            )
        }
        binding.rvFilters.adapter = filterAdapter

        courseList.add(0,"Step design sprint for beginner")
        courseList.add(1,"UI/UX Advanced")
        courseList.add(2,"Android Development for beginner")
        courseList.add(3,"3D Animation Advanced")

        courseAdapter = CourseAdapter(courseList)
        binding.viewPagerCourses.adapter = courseAdapter

        // Attach DotsIndicator to ViewPager2
        binding.indicator.attachTo(binding.viewPagerCourses)

        // Start auto-scrolling
        handler.postDelayed(scrollRunnable, scrollInterval)
        /*binding.rvCourses.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val courseAdapter: CourseAdapter by lazy {
            CourseAdapter(
                courseList,
            )
        }
        binding.rvCourses.adapter = courseAdapter*/
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(scrollRunnable)
    }
    private fun setUpListeners() {
        binding.btnBack.setOnClickListener({ onBackPressed() })
    }

}