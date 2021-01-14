package com.f3401pal.accuweather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.f3401pal.accuweather.R
import com.f3401pal.accuweather.databinding.ActivityMainBinding
import com.f3401pal.accuweather.databinding.ItemForecastItemBinding
import com.f3401pal.accuweather.databinding.ItemNewsItemBinding
import com.f3401pal.accuweather.domain.model.DailyWeather
import com.f3401pal.accuweather.domain.model.NewsItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val forecastAdapter = ForecastAdapter(emptyList())
        binding.forecastList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.forecastList.adapter = forecastAdapter

        val newsAdapter = NewsArticleAdapter()
        binding.newsList.adapter = newsAdapter

        binding.searchText.setOnEditorActionListener { textView, actionId, _ ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if(textView.text.isNotEmpty()) viewModel.loadAll(textView.text.toString())
                    true
                }
                else -> false
            }
        }
        viewModel.error.observe(this) {
            AlertDialog.Builder(this)
                .setMessage(it)
                .setNeutralButton(android.R.string.ok) { dialog, _ ->
                    binding.searchText.text?.clear()
                    dialog.dismiss()
                }
                .show()
        }

        viewModel.currentWeather.observe(this) {
            binding.locationTextView.text = it.location
            binding.currentTempTextView.text = getString(R.string.current_temp, it.temp)
            binding.highLowTempTextView.text = getString(R.string.high_low_temp, it.maxTemp, it.minTemp)
            binding.weatherIcon.load(it.iconUrl)
        }
        viewModel.weatherForecast.observe(this) {
            binding.forecastDaysTextView.text = resources.getQuantityString(R.plurals.next_x_days, it.numDays, it.numDays)
            forecastAdapter.update(it.weatherForecast)
        }
        viewModel.topHeadline.observe(this) {
            newsAdapter.setNewsArticles(it)
        }
    }

}

private class ForecastAdapter(private var items: List<DailyWeather>) : RecyclerView.Adapter<ForecastItemViewHolder>() {

    fun update(items: List<DailyWeather>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ForecastItemViewHolder(ItemForecastItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}

private class ForecastItemViewHolder(private val binding: ItemForecastItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val res = itemView.context.resources

    fun bind(item: DailyWeather) {
        binding.highLowTempTextView.text = res.getString(R.string.temp, item.temp)
        binding.weatherIcon.load(item.iconUrl)
    }
}

private class NewsArticleAdapter() : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsItems: List<NewsItem> = emptyList()

    fun setNewsArticles(value: List<NewsItem>) {
        newsItems = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ItemNewsItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsItems[position])
    }

    override fun getItemCount(): Int = newsItems.size
}

private class NewsViewHolder(private val binding: ItemNewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val res = itemView.context.resources

    fun bind(item: NewsItem) {
        binding.apply {
            newsItemTitle.text = item.title
            newsItemSource.text = res.getString(R.string.news_source, item.source)
            newsItemImage.load(item.imageUrl)
        }
    }
}