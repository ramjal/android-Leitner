package com.example.leitner.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.leitner.answer.AnswerViewModelFactory
import com.example.leitner.database.QuestionAnswerDatabase
import com.example.leitner.databinding.FragmentAnswerBinding
import com.example.leitner.databinding.FragmentQuestionBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private lateinit var viewModel: QuestionViewModel
    private lateinit var viewModelFactory: QuestionViewModelFactory

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
        viewModelFactory = QuestionViewModelFactory(datasource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(QuestionViewModel::class.java)

        _binding = FragmentQuestionBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Navigates answer fragment to check the answer
        viewModel.eventCheckAnswer.observe(viewLifecycleOwner, Observer { checkWordFlag ->
            if (checkWordFlag) {
                val action = QuestionFragmentDirections.actionQuestionToAnswer(viewModel.currIndex.value!!.minus(1))
                findNavController().navigate(action)
                viewModel.onCheckAnswerComplete()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}