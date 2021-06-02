package com.example.leitner.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leitner.R
import com.example.leitner.answer.AnswerFragmentArgs
import com.example.leitner.answer.AnswerViewModelFactory
import com.example.leitner.database.QuestionAnswerDatabase
import com.example.leitner.databinding.FragmentAnswerBinding
import com.example.leitner.databinding.FragmentQuestionBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuestionFragment : Fragment() {

    private val questionFragmentArgs by navArgs<QuestionFragmentArgs>()

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
        viewModelFactory = QuestionViewModelFactory(questionFragmentArgs.boxId, datasource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuestionViewModel::class.java)

        _binding = FragmentQuestionBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // observ eventCheckAnswer to navigate to answer fragment
        viewModel.eventCheckAnswer.observe(viewLifecycleOwner, Observer { checkWordFlag ->
            if (checkWordFlag) {
                val id = viewModel.questionAnswer.value?.uniqueId
                if (id != null) {
                    val action = QuestionFragmentDirections.actionQuestionToAnswer(answerIndex = id)
                    findNavController().navigate(action)
                    viewModel.onCheckAnswerComplete()
                }
            }
        })

        selectBox(questionFragmentArgs.boxId)

        setHasOptionsMenu(true);

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_addNewCard -> {
                findNavController().navigate(QuestionFragmentDirections.actionQuestionFragmentToNewCardFragment())
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun selectBox(boxId: Int) {
        when (boxId) {
            1 -> binding.box1.isChecked = true
            2 -> binding.box2.isChecked = true
            3 -> binding.box3.isChecked = true
            4 -> binding.box4.isChecked = true
            5 -> binding.box5.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}