package com.example.leitner.question

import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leitner.R
import com.example.leitner.database.QuestionAnswerDatabase
import com.example.leitner.databinding.FragmentQuestionBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuestionFragment : Fragment() {

    private val myFragmentArgs by navArgs<QuestionFragmentArgs>()

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
        viewModelFactory = QuestionViewModelFactory(myFragmentArgs.boxId, datasource)
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

        selectBox(myFragmentArgs.boxId)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_addTestData -> {
                viewModel.insertTempCards()
                return true
            }
            R.id.action_deleteAllData -> {
                deleteCards()
                return true
            }
            R.id.action_addNewCard -> {
                findNavController().navigate(
                    QuestionFragmentDirections.
                    actionQuestionFragmentToNewCardFragment(editAddType = "add"))
                return true
            }
            R.id.action_editCard -> {
                val id = viewModel.questionAnswer.value?.uniqueId
                if (id != null) {
                    findNavController().navigate(
                        QuestionFragmentDirections.actionQuestionFragmentToNewCardFragment(
                            editAddType = "edit",
                            cardId = id,
                            title = "Edit Card"
                        )
                    )
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * delete all the cards from database
     */
    private fun deleteCards() {
        val builder = AlertDialog.Builder(requireActivity())
        with(builder) {
            setTitle("Deletion Alert!")
            setMessage("Do you want to delete all the cards?")
            setPositiveButton("Yes") { dialog, which ->
                viewModel.deleteCards()
                Toast.makeText(requireActivity(), "Deleted!", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("No") { dia, which ->
                Toast.makeText(requireActivity(), "Cancelled!", Toast.LENGTH_SHORT).show()
            }
            show()
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