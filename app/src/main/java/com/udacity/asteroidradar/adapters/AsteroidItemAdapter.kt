package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.domain.Asteroid

class AsteroidItemAdapter(private val onClickListener: OnClickListener):
  ListAdapter<Asteroid, AsteroidItemAdapter.AsteroidViewHolder>(DiffCallback){
  companion object DiffCallback: DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
      return oldItem.codename == newItem.codename
    }
  }

  class OnClickListener(val clickListener: (asteroid: Asteroid)-> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
  }

  class AsteroidViewHolder(private var binding: AsteroidItemBinding):
    RecyclerView.ViewHolder(binding.root){

    fun bind(asteroid: Asteroid){
      binding.asteroid = asteroid
      binding.executePendingBindings()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
    return AsteroidViewHolder(AsteroidItemBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
    val asteroid = getItem(position)
    holder.bind(asteroid)
    holder.itemView.setOnClickListener {
      onClickListener.onClick(asteroid)
    }
  }
}