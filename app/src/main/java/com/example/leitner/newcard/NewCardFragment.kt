package com.example.leitner.newcard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.example.leitner.R
import com.example.leitner.database.QuestionAnswerDatabase
import com.example.leitner.databinding.FragmentNewCardBinding

class NewCardFragment : Fragment() {

    private val myFragmentArgs by navArgs<NewCardFragmentArgs>()
    private lateinit var viewModel: NewCardViewModel
    private lateinit var viewModelFactory: NewCardViewModelFactory
    private var _binding: FragmentNewCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
        viewModelFactory = NewCardViewModelFactory(datasource, myFragmentArgs.editAddType, myFragmentArgs.cardId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewCardViewModel::class.java)

        _binding = FragmentNewCardBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        //to be used in the click event
        //binding.buttonAdd.tag = myFragmentArgs.editAddType
        //val carId =  myFragmentArgs.cardId
        if (myFragmentArgs.editAddType == "edit") {
            binding.buttonAdd.setText("Edit Card")
        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}