package com.example.leitner.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.leitner.R
import com.example.leitner.databinding.FragmentQuestionBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private lateinit var viewModel: QuestionViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            var ans: String = "deliberately created rather than arising naturally or spontaneously.\n" +
                "created or arranged in a way that seems artificial and unrealistic.\n" +
                "\"the ending of the novel is too pat and contrived\""

            val action = QuestionFragmentDirections.actionQuestionToAnswer(ans)
            findNavController().navigate(action)
            //findNavController().navigate(R.id.action_Question_to_Answer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}