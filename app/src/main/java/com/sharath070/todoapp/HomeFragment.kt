package com.sharath070.todoapp

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharath070.todoapp.adapter.RecyclerViewAdapter
import com.sharath070.todoapp.databinding.FragmentHomeBinding
import com.sharath070.todoapp.db.Todo
import com.sharath070.todoapp.viewModel.TodoViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private lateinit var viewModel: TodoViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        val adapter = RecyclerViewAdapter()
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val todo = adapter.todoList[viewHolder.adapterPosition]
                    if (direction == ItemTouchHelper.LEFT) {
                        viewModel.delete(todo)
                        Toast.makeText(
                            requireContext(),
                            "Deleted Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        val action = HomeFragmentDirections
                            .actionHomeFragmentToCreateOrUpdateNoteFragment(todo)
                        findNavController().navigate(action)
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    RecyclerViewSwipeDecorator.Builder(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.delete
                            )
                        )
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.update
                            )
                        )
                        .addSwipeRightActionIcon(R.drawable.ic_update)
                        .create()
                        .decorate()

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }


            }).attachToRecyclerView(recyclerView)

        }


        viewModel.todos.observe(viewLifecycleOwner) {
            adapter.setData(it)
            if (it.isEmpty()) {
                binding.tvBackground.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.tvBackground.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }

        }

        binding.fabAddTodo.setOnClickListener {
            val todo = Todo(0, "", "")
            val action = HomeFragmentDirections
                .actionHomeFragmentToCreateOrUpdateNoteFragment(todo)
            findNavController().navigate(action)
        }

    }
}