package com.example.leitner.answer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leitner.R
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

        _binding = FragmentAnswerBinding.inflate(inflater, container, false)

        viewModelFactory = AnswerViewModelFactory(answerFragmentArgs.answer)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AnswerViewModel::class.java)
        //Log.i("GameFragment", "Called ViewModelProvider to create the GameViewModel")

        binding.viewModel = viewModel


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_Answer_to_Question)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}