package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import kotlinx.android.synthetic.main.list_item.*

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        val application = requireNotNull(activity).application

        binding.lifecycleOwner = this

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        val viewModelFactory = DetailViewModelFactory(asteroid, application)

        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)


        binding.asteroid = viewModel

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
            binding.helpButton.contentDescription =
                context?.getString(R.string.astronomica_unit_explanation)
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
