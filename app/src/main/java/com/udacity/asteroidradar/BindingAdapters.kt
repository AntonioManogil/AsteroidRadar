package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.adapters.AsteroidItemAdapter
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.ImageOfDay

@BindingAdapter("imageUrl")
fun imageUrl(imgView: ImageView, imageOfDay: ImageOfDay?){
    imageOfDay?.let {
        if(imageOfDay.media_type == "image") {
            Picasso.with(imgView.context).load(imageOfDay.url).into(imgView)
        }
    }
}

@BindingAdapter("listData")
fun listData(recyclerView: RecyclerView, data: List<Asteroid>?){
    data?.let{
        val adapter = recyclerView.adapter as AsteroidItemAdapter
        adapter.submitList(data)
    }
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, data: List<Asteroid>?){
    view.visibility = if(data != null)  View.GONE else View.VISIBLE
}
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
