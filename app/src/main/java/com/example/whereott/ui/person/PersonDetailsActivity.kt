package com.example.whereott.ui.person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.whereott.MainActivity.Companion.MOVIE_BACKDROP
import com.example.whereott.MainActivity.Companion.MOVIE_ID
import com.example.whereott.MainActivity.Companion.MOVIE_OVERVIEW
import com.example.whereott.MainActivity.Companion.MOVIE_POSTER
import com.example.whereott.MainActivity.Companion.MOVIE_RATING
import com.example.whereott.MainActivity.Companion.MOVIE_RELEASE_DATE
import com.example.whereott.MainActivity.Companion.MOVIE_TITLE
import com.example.whereott.MainActivity.Companion.TYPE
import com.example.whereott.R
import com.example.whereott.common.CastAdapter
import com.example.whereott.common.MoviesRepository
import com.example.whereott.common.PersonRepository
import com.example.whereott.common.ProviderAdapter
import com.example.whereott.common.TV
import com.example.whereott.databinding.ActivityPersonDetailsBinding

class PersonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonDetailsBinding

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    private lateinit var castAdapter: CastAdapter
    private lateinit var providerAdapter: ProviderAdapter
    private var castPage = 1
    private var personId: Long = -1L
    private var type: String = ""
    private var tvId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        // 출연진 정보를 위한 RecyclerView 초기화
//        val castRecyclerView: RecyclerView = findViewById(R.id.cast_recycler_view)
//        val castLayoutManager = LinearLayoutManager(this).apply {
//            orientation = LinearLayoutManager.HORIZONTAL
//        }
//        castRecyclerView.layoutManager = castLayoutManager
//        castAdapter = CastAdapter(mutableListOf())
//        castRecyclerView.adapter = castAdapter

        // Provider 정보를 위한 RecyclerView 초기화
        val providersRecyclerView: RecyclerView = findViewById(R.id.provider_recycler_view)
        val providersLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        providersRecyclerView.layoutManager = providersLayoutManager
        providerAdapter = ProviderAdapter(mutableListOf())
        providersRecyclerView.adapter = providerAdapter

        val extras = intent.extras

        if (extras != null) {
            personId = extras.getLong(MOVIE_ID)
            type = extras.getString(TYPE).toString()



            // 출연진 정보 가져오기
//            getPersonDetail(personId)

            personDetails(extras)

        } else {
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 뒤로가기 버튼 클릭 시
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun personDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }
//
//    private fun getPersonDetail(personId: Long) {
////        val movieId = intent.getLongExtra(MOVIE_ID, -1) // 영화의 ID를 가져옵니다.
//
//        if (personId != -1L) {
//            val personRepository = PersonRepository
//            personRepository.getMovieCast(personId,
//                onSuccess = { cast ->
//                    castAdapter.appendCast(cast)
//                },
//                onError = {
//                    // 오류 처리
//                }
//            )
//        }
//    }

}