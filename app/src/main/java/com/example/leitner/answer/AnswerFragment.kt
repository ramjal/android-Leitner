package com.example.leitner.answer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leitner.R
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDatabase
import com.example.leitner.databinding.FragmentAnswerBinding
import com.example.leitner.question.QuestionViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AnswerFragment : Fragment() {

    private val answerFragmentArgs by navArgs<AnswerFragmentArgs>()

    private var _binding: FragmentAnswerBinding? = null
    private lateinit var viewModel: AnswerViewModel
    private lateinit var viewModelFactory: AnswerViewModelFactory

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application

        val datasource = QuestionAnswerDatabase.getInstance(application).questionAnswerDao
        _binding = FragmentAnswerBinding.inflate(inflater, container, false)
        viewModelFactory = AnswerViewModelFactory(answerFragmentArgs.answerIndex, datasource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AnswerViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Navigates back to question when button is pressed
        viewModel.eventNexWord.observe(viewLifecycleOwner, Observer { nextWord ->
            if (nextWord) {
                findNavController().navigate(R.id.action_Answer_to_Question)
                viewModel.onNextWordComplete()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}