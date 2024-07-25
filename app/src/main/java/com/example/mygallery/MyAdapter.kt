package com.example.mygallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Environment
import com.bumptech.glide.Glide


class MyAdapter(private val galleryList: ArrayList<CreateList>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cell_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.title.text = galleryList[i].imageTitle

        val imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/" + galleryList[i].imageTitle

        Glide.with(viewHolder.itemView.context)
            .load(imagePath)
            .into(viewHolder.imageView)
    }

    override fun getItemCount(): Int {
        return galleryList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val imageView: ImageView = view.findViewById(R.id.imageView) // Added imageView property
    }
}