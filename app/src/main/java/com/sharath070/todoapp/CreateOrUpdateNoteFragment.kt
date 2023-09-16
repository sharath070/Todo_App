package com.sharath070.todoapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sharath070.todoapp.databinding.FragmentCreateOrUpdateNoteBinding
import com.sharath070.todoapp.db.Todo
import com.sharath070.todoapp.viewModel.TodoViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CreateOrUpdateNoteFragment : Fragment() {

    private var _binding: FragmentCreateOrUpdateNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateOrUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var todoViewModel: TodoViewModel

    private val args: CreateOrUpdateNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        val todo = args.currentTodo
        binding.etTitle.setText(todo.title)
        binding.etNote.setText(todo.note)

        binding.submitOrUpdate.setOnClickListener {

            if (TextUtils.isEmpty(todo.title)) {
                insertDataToDatabase()
            } else {
                updateDataToDatabase(todo.id)
            }
        }

    }

    private fun updateDataToDatabase(id: Int) {
        val title = binding.etTitle.text.toString()
        val note = binding.etNote.text.toString()

        if (inputCheck(title, note)) {
            val todo = Todo(id, title, note)
            todoViewModel.update(todo)
            Toast.makeText(
                requireContext(),
                "Successfully updated!",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_createOrUpdateNoteFragment_to_homeFragment)

        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all fields.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun insertDataToDatabase() {
        val title = binding.etTitle.text.toString()
        val note = binding.etNote.text.toString()

        if (inputCheck(title, note)) {
            val todo = Todo(0, title, note)
            todoViewModel.insert(todo)
            Toast.makeText(
                requireContext(),
                "Successfully added!",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_createOrUpdateNoteFragment_to_homeFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all fields.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun inputCheck(title: String, note: String): Boolean {
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(note))
    }


}