package io.raveerocks.dessertpusher

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import io.raveerocks.dessertpusher.databinding.ActivityMainBinding
import timber.log.Timber

private const val KEY_REVENUE = "key_revenue"
private const val KEY_DESSERTS_SOLD = "desserts_sold"
private const val KEY_DESSERT_TIMER = "dessert_timer"


class MainActivity : AppCompatActivity() {

    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private lateinit var dessertTimer: DessertTimer
    private lateinit var binding: ActivityMainBinding
    private var revenue = 0
    private var dessertsSold = 0

    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    private fun onDessertClicked() {
        revenue += currentDessert.price
        dessertsSold++
        binding.revenue = revenue
        binding.amountSold = dessertsSold
        showCurrentDessert()
        Timber.i("onDessertClicked called")
    }

    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            else break
        }
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
        Timber.i("showCurrentDessert called")
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder(this)
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
        Timber.i("onShare called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }
        dessertTimer = DessertTimer(this.lifecycle)
        if(savedInstanceState!=null){
            revenue = savedInstanceState.getInt(KEY_REVENUE)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERTS_SOLD)
            dessertTimer.secondsCount = savedInstanceState.getInt(KEY_DESSERT_TIMER)
        }
        binding.revenue = revenue
        binding.amountSold = dessertsSold
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    override fun onStart(){
        super.onStart()
        Timber.i("onStart called")

    }

    override fun  onResume(){
        super.onResume()
        Timber.i("onResume called")
    }

    override fun  onPause(){
        super.onPause()
        Timber.i("onPause called")
    }

    override fun  onStop(){
        super.onStop()
        Timber.i("onStop called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Timber.i("onDestroy called")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        Timber.i("onCreateOptionsMenu called")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        Timber.i("onOptionsItemSelected called")
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_REVENUE,revenue)
        outState.putInt(KEY_DESSERTS_SOLD,dessertsSold)
        outState.putInt(KEY_DESSERT_TIMER,dessertTimer.secondsCount)
        Timber.i("onSaveInstanceState called")
    }
}