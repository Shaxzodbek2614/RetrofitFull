package com.example.retrofifull

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofifull.api.ApiClient
import com.example.retrofifull.databinding.ActivityAddBinding
import com.example.retrofifull.models.Info
import com.example.retrofifull.models.PostRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

private const val TAG = "AddActivity"
class AddActivity : AppCompatActivity() {
    lateinit var info: Info
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val raqam = intent.getIntExtra("raqam",1)
        if (raqam==1){
            addInfo()
        }else if (raqam==2){
            editInfo()
        }

        

    }

    private fun editInfo() {
        info = intent.getSerializableExtra("info") as Info
        binding.sarlavha.setText(info.sarlavha)
        binding.batafsil.setText(info.batafsil)
        binding.zarurlik.setText(info.zarurlik)
        binding.oxirgiMuddat.setText(info.oxirgi_muddat)
        binding.btnSave.setOnClickListener {
            val info1 = PostRequest(
                false,
                binding.batafsil.text.toString(),
                binding.oxirgiMuddat.text.toString(),
                binding.sarlavha.text.toString(),
                binding.zarurlik.text.toString()
            )
            ApiClient.getApiService().updateInfo(info.id,info1)
                .enqueue(object :Callback<Info>{
                    override fun onResponse(p0: Call<Info>, p1: Response<Info>) {
                        if (p1.isSuccessful) {
                            Toast.makeText(this@AddActivity, "Tahrirlandi", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }else{
                            Toast.makeText(this@AddActivity, "${p1.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(p0: Call<Info>, p1: Throwable) {
                        Toast.makeText(this@AddActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        
    }

    private fun addInfo() {
        binding.btnSave.setOnClickListener {
            val info = PostRequest(
                false,
                binding.batafsil.text.toString(),
                binding.oxirgiMuddat.text.toString(),
                binding.sarlavha.text.toString(),
                binding.zarurlik.text.toString()
            )

            Log.d(TAG, "addInfo: $info")
            
            ApiClient.getApiService().addInfo(info)
                .enqueue(object :Callback<Info>{
                    override fun onResponse(p0: Call<Info>, p1: Response<Info>) {
                        if (p1.isSuccessful){
                            Toast.makeText(this@AddActivity, "Saqlandi", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(
                                this@AddActivity,
                                p1.errorBody()?.string(),
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d(TAG, "onResponse: ${p1.errorBody()?.string()}")

                        }
                    }

                    override fun onFailure(p0: Call<Info>, p1: Throwable) {
                        Toast.makeText(this@AddActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

}