package com.example.leitner.newcard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leitner.R
import com.example.leitner.database.QuestionAnswerDatabase
import com.example.leitner.databinding.FragmentNewCardBinding
import com.example.leitner.databinding.FragmentQuestionBinding
import com.example.leitner.question.QuestionViewModel
import com.example.leitner.question.QuestionViewModelFactory

class NewCardFragment : Fragment() {

    private var _binding: FragmentNewCardBinding? = null
    private lateinit var viewModel: NewCardViewModel
    private lateinit var viewModelFactory: NewCardViewModelFactory

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
        viewModelFactory = NewCardViewModelFactory(datasource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewCardViewModel::class.java)

        _binding = FragmentNewCardBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
    
}