package com.example.retrofifull

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofifull.adapter.RetrofitAdapter
import com.example.retrofifull.api.ApiClient
import com.example.retrofifull.databinding.ActivityMainBinding
import com.example.retrofifull.models.Info
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),RetrofitAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var retrofitAdapter: RetrofitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("raqam",1)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        ApiClient.getApiService().getAllInfo()
            .enqueue(object :Callback<List<Info>>{
                override fun onResponse(p0: Call<List<Info>>, p1: Response<List<Info>>) {
                    if (p1.isSuccessful){
                        retrofitAdapter = RetrofitAdapter(this@MainActivity,p1.body() as ArrayList)
                        binding.rv.adapter = retrofitAdapter
                    }
                }

                override fun onFailure(p0: Call<List<Info>>, p1: Throwable) {

                }
            })


    }

    override fun moreClick(info: Info,position: Int, imageView: ImageView) {
        val menu = PopupMenu(this,imageView)
        menu.inflate(R.menu.my_menu)
        menu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.edit->{
                    val intent = Intent(this,AddActivity::class.java)
                    intent.putExtra("info",info)
                    intent.putExtra("raqam",2)
                    startActivity(intent)
                }
                R.id.delete->{
                    ApiClient.getApiService().deleteInfo(info.id)
                        .enqueue(object:Callback<ResponseBody>{
                            override fun onResponse(
                                p0: Call<ResponseBody>,
                                p1: Response<ResponseBody>
                            ) {
                                if (p1.isSuccessful){
                                    onResume()
                                    Toast.makeText(this@MainActivity, "O'chirildi", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                            }

                        })
                }
            }

            true
        }
        menu.show()
    }
}