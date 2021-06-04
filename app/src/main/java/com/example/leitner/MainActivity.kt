package com.example.leitner

import android.app.AlertDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.leitner.database.*
import com.example.leitner.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var coroutineJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + coroutineJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_addTestData -> {
                insertTempCards()
                return true
            }
            R.id.action_deleteAllData -> {
                deleteCards()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }

    /**
     * add new card to database
     */
//    private fun addNewCard() {
//        val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
//        coroutineScope.launch {
//            insertNewCardToDatabase(datasource)
//        }
//    }
//    private suspend fun insertNewCardToDatabase(datasource: QuestionAnswerDao) {
//        return withContext(Dispatchers.IO) {
//            datasource.insertNewCard(_questions, _answers)
//        }
//    }

    /**
     * add temp data to database
     */
    private fun insertTempCards() {
        val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
        coroutineScope.launch {
            insertTempCardsToDatabase(datasource)
        }
    }
    private suspend fun insertTempCardsToDatabase(datasource: QuestionAnswerDao) {
        return withContext(Dispatchers.IO) {
            datasource.insertTempCards(_questions, _answers)
        }
    }

    /**
     * delete all the cards from database
     */
    private fun deleteCards() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Deletion Alert!")
            setMessage("Do you want to delete all the cards?")
            setPositiveButton("Yes") { dialog, which ->
                val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
                coroutineScope.launch {
                    deleteCardsFromDatabase(datasource)
                }
            }
            setNegativeButton("No") { dia, which ->
                Toast.makeText(applicationContext, "Cancelled!", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }
    private suspend fun deleteCardsFromDatabase(datasource: QuestionAnswerDao) {
        return withContext(Dispatchers.IO) {
            datasource.deleteAll()
        }
    }

}