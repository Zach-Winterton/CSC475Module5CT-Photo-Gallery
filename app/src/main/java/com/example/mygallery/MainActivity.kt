package com.example.mygallery


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import java.io.File

private const val REQUEST_PERMISSION = 100

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if permissions are granted
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions
            val permissionsToRequest = mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionsToRequest.add(Manifest.permission.ACCESS_MEDIA_LOCATION)
            }
            requestPermissions(permissionsToRequest.toTypedArray(), REQUEST_PERMISSION)
        } else {
            // Permissions are granted, proceed with the app
            initRecyclerView()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && (grantResults.size == 1 || grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                // Permissions are granted, proceed with the app
                initRecyclerView()
            } else {
                // Permissions are denied, show a toast message
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.imagegallery)
        recyclerView.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(applicationContext, 3)
        recyclerView.layoutManager = layoutManager

        val createLists = prepareData()
        val adapter = MyAdapter(createLists) // Pass only the data list
        recyclerView.adapter = adapter
    }

    private fun prepareData(): ArrayList<CreateList> {
        val theimage = ArrayList<CreateList>()
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.path ?: return theimage // Return empty list if path is null
        val f = File(path)
        val files = f.listFiles() ?: return theimage // Return empty list if files is null

        for (i in files.indices) {
            val createList = CreateList()
            createList.imageTitle = files[i].name
            theimage.add(createList)
        }
        return theimage
    }
}