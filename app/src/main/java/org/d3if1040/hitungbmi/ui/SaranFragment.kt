package org.d3if1040.hitungbmi.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if1040.hitungbmi.R
import org.d3if1040.hitungbmi.data.KategoriBmi
import org.d3if1040.hitungbmi.databinding.FragmentSaranBinding

class SaranFragment : Fragment() {

    private val args: SaranFragmentArgs by navArgs()
    private lateinit var binding : FragmentSaranBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        updateUI(args.kategori, args.berat, args.tinggi, args.gender)
        binding.shareButton.setOnClickListener{ shareData() }
        return binding.root
    }

    private fun shareData() {
        val gender = if (args.gender)
            getString(R.string.gender_pria)
        else
            getString(R.string.gender_wanita)
        val kategori = when(args.kategori){
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.GEMUK -> R.string.gemuk
            KategoriBmi.IDEAL -> R.string.ideal
        }

        val message = getString(R.string.bagikan_template,
            args.berat,
            args.tinggi,
            gender,
            args.bmi.toString(),
            getString(kategori)
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if(shareIntent.resolveActivity(requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }
    }

    private fun updateUI(
        kategori: KategoriBmi,
        berat: String,
        tinggi: String,
        gender: Boolean
    ){
        val kelamin = if(gender) "Pria" else "Wanita"
        binding.jenisKelamin.text = getString(R.string.gender_x, kelamin)
        binding.tinggiBadan.text = getString(R.string.tinggi_x, tinggi)
        binding.beratBadan.text = getString(R.string.berat_x, berat)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when(kategori){
            KategoriBmi.KURUS -> {
                actionBar?.title = getString(R.string.judul_kurus)
                binding.imageView.setImageResource(R.drawable.kurus)
                binding.textView.text = getString(R.string.saran_kurus)
            }
            KategoriBmi.IDEAL -> {
                actionBar?.title = getString(R.string.judul_ideal)
                binding.imageView.setImageResource(R.drawable.ideal)
                binding.textView.text = getString(R.string.saran_ideal)
            }
            KategoriBmi.GEMUK -> {
                actionBar?.title = getString(R.string.judul_gemuk)
                binding.imageView.setImageResource(R.drawable.gemuk)
                binding.textView.text = getString(R.string.saran_gemuk)
            }
        }
    }
}