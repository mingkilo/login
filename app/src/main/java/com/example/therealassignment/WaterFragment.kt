package com.example.therealassignment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WaterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WaterFragment : Fragment() {

    // progress bar
    private var prog = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_water, container, false)
        val spinner = view.findViewById<Spinner>(R.id.water_spinner)
        val addButton = view.findViewById<Button>(R.id.sleep_strButton)
        val progressBar = view.findViewById<ProgressBar>(R.id.sleep_progressBar)
        val userAmountText = view.findViewById<TextView>(R.id.sleep_time)
        val waterAmountText = view.findViewById<TextView>(R.id.water_waterAmount)

        //store the data to sharePreferences
        val waterSharedPreferences = activity?.getSharedPreferences("waterPreferences", Context.MODE_PRIVATE)
        val waterEditor = waterSharedPreferences?.edit()

        // spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.waterAmount_selection,
            R.layout.spinner_style
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.select_dialog_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                waterAmountText.text = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                waterAmountText.text = "0"
            }
        }

        // progress bar
        addButton.setOnClickListener {
            val addWaterAmount = waterAmountText.text.toString().split(" ")[0].toInt()
            val averageAmount = (addWaterAmount.toFloat() / 3700) * 100
            var userAmount = waterSharedPreferences?.getString("water_amount", null)?.toInt() ?:0

            if (prog < 100) {
                prog += averageAmount.toInt()
                if (prog > 100)
                    prog = 100
                progressBar.progress = prog
            }

            userAmount += addWaterAmount
            userAmountText.text = userAmount.toString()

            waterEditor?.apply{
                putString("water_amount", userAmount.toString())
                putString("water_amount", userAmount.toString())
                apply()
            }
        }



        return view
    }
}


// TODO: Rename and change types of parameters
/* default setting
private var param1: String? = null
private var param2: String? = null


arguments?.let {
    param1 = it.getString(ARG_PARAM1)
    param2 = it.getString(ARG_PARAM2)


override fun onCreateView(
inflater: LayoutInflater, container: ViewGroup?,
savedInstanceState: Bundle?
): View? {
// Inflate the layout for this fragment
return inflater.inflate(R.layout.fragment_water, container, false)
}

companion object {
/**
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment WaterFragment.
 */
// TODO: Rename and change types and number of parameters
@JvmStatic
fun newInstance(param1: String, param2: String) =
    WaterFragment().apply {
        arguments = Bundle().apply {
            putString(ARG_PARAM1, param1)
            putString(ARG_PARAM2, param2)
        }
    }
}*/
